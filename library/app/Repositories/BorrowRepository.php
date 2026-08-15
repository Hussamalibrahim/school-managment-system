<?php

namespace App\Repositories;

use App\Models\Borrow;
use App\Repositories\Interfaces\BorrowRepositoryInterface;
use Illuminate\Database\Eloquent\Collection;

class BorrowRepository extends BaseRepository implements BorrowRepositoryInterface
{
    public function __construct(Borrow $model)
    {
        parent::__construct($model);
    }

    public function getActiveBorrows(): Collection
    {
        return $this->model->whereNull('return_date')->with('book')->get();
    }

    public function getReturnedBorrows(): Collection
    {
        return $this->model->whereNotNull('return_date')->with('book')->get();
    }

    public function getOverdueBorrows(): Collection
    {
        return $this->model->whereNull('return_date')
            ->where('due_date', '<', now()->toDateString())
            ->with('book')
            ->get();
    }

    public function getStudentHistory(int $studentId): Collection
    {
        return $this->model->where('student_id', $studentId)
            ->with('book')
            ->orderBy('borrow_date', 'desc')
            ->get();
    }

    public function getActiveBorrowForStudentAndBook(int $studentId, int $bookId)
    {
        return $this->model->where('student_id', $studentId)
            ->where('book_id', $bookId)
            ->whereNull('return_date')
            ->first();
    }

    public function getCurrentlyBorrowedBooks(): Collection
    {
        return $this->model->whereNull('return_date')
            ->with('book')
            ->get()
            ->pluck('book')
            ->unique('id');
    }
}
