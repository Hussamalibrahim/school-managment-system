<?php

namespace App\Repositories\Interfaces;

use Illuminate\Database\Eloquent\Collection;

interface ReservationRepositoryInterface extends RepositoryInterface
{
    public function getByStatus(string $status): Collection;
    public function getStudentReservations(int $studentId): Collection;
    public function getPendingReservationForStudentAndBook(int $studentId, int $bookId);
}
