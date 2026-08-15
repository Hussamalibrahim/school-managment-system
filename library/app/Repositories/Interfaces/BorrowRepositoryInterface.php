<?php

namespace App\Repositories\Interfaces;

use Illuminate\Database\Eloquent\Collection;

interface BorrowRepositoryInterface extends RepositoryInterface
{
    public function getActiveBorrows(): Collection;
    public function getReturnedBorrows(): Collection;
    public function getOverdueBorrows(): Collection;
    public function getStudentHistory(int $studentId): Collection;
    public function getActiveBorrowForStudentAndBook(int $studentId, int $bookId);
    public function getCurrentlyBorrowedBooks(): Collection;
}
