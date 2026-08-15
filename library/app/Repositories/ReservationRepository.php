<?php

namespace App\Repositories;

use App\Models\Reservation;
use App\Repositories\Interfaces\ReservationRepositoryInterface;
use Illuminate\Database\Eloquent\Collection;

class ReservationRepository extends BaseRepository implements ReservationRepositoryInterface
{
    public function __construct(Reservation $model)
    {
        parent::__construct($model);
    }

    public function getByStatus(string $status): Collection
    {
        return $this->model->where('status', $status)->with('book')->get();
    }

    public function getStudentReservations(int $studentId): Collection
    {
        return $this->model->where('student_id', $studentId)->with('book')->get();
    }

    public function getPendingReservationForStudentAndBook(int $studentId, int $bookId)
    {
        return $this->model->where('student_id', $studentId)
            ->where('book_id', $bookId)
            ->where('status', 'pending')
            ->first();
    }
}
