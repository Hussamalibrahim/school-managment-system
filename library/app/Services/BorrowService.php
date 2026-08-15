<?php

namespace App\Services;

use App\Repositories\Interfaces\BookRepositoryInterface;
use App\Repositories\Interfaces\BorrowRepositoryInterface;
use App\Repositories\Interfaces\StudentRepositoryInterface;
use Exception;
use Illuminate\Support\Carbon;

class BorrowService
{
    protected BorrowRepositoryInterface $borrowRepository;
    protected StudentRepositoryInterface $studentRepository;
    protected BookRepositoryInterface $bookRepository;

    public function __construct(
        BorrowRepositoryInterface $borrowRepository,
        StudentRepositoryInterface $studentRepository,
        BookRepositoryInterface $bookRepository
    ) {
        $this->borrowRepository = $borrowRepository;
        $this->studentRepository = $studentRepository;
        $this->bookRepository = $bookRepository;
    }

    public function getAllBorrows()
    {
        return $this->borrowRepository->all();
    }

    public function borrowBook(array $data)
    {
        // Student data lives in the Spring Boot school-core service. We only
        // validate that the id resolves to a real student; we don't store a
        // local copy beyond the foreign id.
        $student = $this->studentRepository->find((int) $data['student_id']);
        if (!$student) {
            throw new Exception("الطالب غير موجود (تعذر التحقق منه عبر خدمة الطلاب)");
        }

        $book = $this->bookRepository->find($data['book_id']);
        if (!$book) {
            throw new Exception("الكتاب غير موجود");
        }

        $activeBorrow = $this->borrowRepository->getActiveBorrowForStudentAndBook($student['id'], $book->id);
        if ($activeBorrow) {
            throw new Exception("لا يمكن استعارة نفس الكتاب مرتين لنفس الطالب إذا كانت هناك إعارة مفتوحة.");
        }

        $borrowData = [
            'student_id' => $student['id'],
            'book_id' => $book->id,
            'borrow_date' => Carbon::today()->toDateString(),
            'due_date' => Carbon::today()->addDays(14)->toDateString(),
            'return_date' => null,
        ];

        return $this->borrowRepository->create($borrowData);
    }

    public function returnBook(int $id)
    {
        $borrow = $this->borrowRepository->find($id);

        if (!$borrow) {
            throw new Exception("سجل الإعارة غير موجود");
        }

        if ($borrow->return_date !== null) {
            throw new Exception("تم إرجاع هذا الكتاب مسبقاً");
        }

        $this->borrowRepository->update($id, [
            'return_date' => Carbon::today()->toDateString()
        ]);

        return $this->borrowRepository->find($id);
    }

    public function getActiveBorrows()
    {
        return $this->borrowRepository->getActiveBorrows();
    }

    public function getReturnedBorrows()
    {
        return $this->borrowRepository->getReturnedBorrows();
    }

    public function getOverdueBorrows()
    {
        return $this->borrowRepository->getOverdueBorrows();
    }
}
