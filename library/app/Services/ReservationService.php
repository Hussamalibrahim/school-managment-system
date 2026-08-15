<?php

namespace App\Services;

use App\Repositories\Interfaces\BookRepositoryInterface;
use App\Repositories\Interfaces\ReservationRepositoryInterface;
use App\Repositories\Interfaces\StudentRepositoryInterface;
use Exception;
use Illuminate\Support\Carbon;

class ReservationService
{
    protected ReservationRepositoryInterface $reservationRepository;
    protected StudentRepositoryInterface $studentRepository;
    protected BookRepositoryInterface $bookRepository;

    public function __construct(
        ReservationRepositoryInterface $reservationRepository,
        StudentRepositoryInterface $studentRepository,
        BookRepositoryInterface $bookRepository
    ) {
        $this->reservationRepository = $reservationRepository;
        $this->studentRepository = $studentRepository;
        $this->bookRepository = $bookRepository;
    }

    public function getAllReservations()
    {
        return $this->reservationRepository->all();
    }

    public function reserveBook(array $data)
    {
        $student = $this->studentRepository->find((int) $data['student_id']);
        if (!$student) {
            throw new Exception("الطالب غير موجود (تعذر التحقق منه عبر خدمة الطلاب)");
        }

        $book = $this->bookRepository->find($data['book_id']);
        if (!$book) {
            throw new Exception("الكتاب غير موجود");
        }

        $existing = $this->reservationRepository->getPendingReservationForStudentAndBook($student['id'], $book->id);
        if ($existing) {
            throw new Exception("يوجد حجز قائم بالفعل لنفس الطالب لهذا الكتاب");
        }

        return $this->reservationRepository->create([
            'student_id' => $student['id'],
            'book_id' => $book->id,
            'reservation_date' => Carbon::today()->toDateString(),
            'status' => 'pending',
        ]);
    }

    public function fulfillReservation(int $id)
    {
        return $this->updateStatus($id, 'fulfilled', ['pending']);
    }

    public function cancelReservation(int $id)
    {
        return $this->updateStatus($id, 'cancelled', ['pending']);
    }

    protected function updateStatus(int $id, string $newStatus, array $allowedFrom)
    {
        $reservation = $this->reservationRepository->find($id);

        if (!$reservation) {
            throw new Exception("الحجز غير موجود");
        }

        if (!in_array($reservation->status, $allowedFrom, true)) {
            throw new Exception("لا يمكن تحديث حالة هذا الحجز من '{$reservation->status}' إلى '{$newStatus}'");
        }

        $this->reservationRepository->update($id, ['status' => $newStatus]);

        return $this->reservationRepository->find($id);
    }

    public function getPendingReservations()
    {
        return $this->reservationRepository->getByStatus('pending');
    }

    public function getStudentReservations(int $studentId)
    {
        return $this->reservationRepository->getStudentReservations($studentId);
    }
}
