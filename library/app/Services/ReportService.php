<?php

namespace App\Services;

use App\Repositories\Interfaces\BookRepositoryInterface;
use App\Repositories\Interfaces\BorrowRepositoryInterface;
use App\Repositories\Interfaces\ReservationRepositoryInterface;

class ReportService
{
    protected BookRepositoryInterface $bookRepository;
    protected BorrowRepositoryInterface $borrowRepository;
    protected ReservationRepositoryInterface $reservationRepository;

    public function __construct(
        BookRepositoryInterface $bookRepository,
        BorrowRepositoryInterface $borrowRepository,
        ReservationRepositoryInterface $reservationRepository,
    )
    
    {
        $this->bookRepository = $bookRepository;
        $this->borrowRepository = $borrowRepository;
        $this->reservationRepository = $reservationRepository;
    }

    public function getAllBooks()
    {
        return $this->bookRepository->all();
    }

    public function getMostBorrowedBooks()
    {
        return $this->bookRepository->getMostBorrowedBooks();
    }

    public function getCurrentlyBorrowedBooks()
    {
        return $this->borrowRepository->getCurrentlyBorrowedBooks();
    }

    public function getOverdueBooks()
    {
        return $this->borrowRepository->getOverdueBorrows()->pluck('book')->unique('id')->values();
    }

    public function getStudentHistory(int $studentId)
    {
        return $this->borrowRepository->getStudentHistory($studentId);
    }

    public function getReservationStats(): array
    {
        return [
            'pending' => $this->reservationRepository->getByStatus('pending')->count(),
            'fulfilled' => $this->reservationRepository->getByStatus('fulfilled')->count(),
            'cancelled' => $this->reservationRepository->getByStatus('cancelled')->count(),
            'expired' => $this->reservationRepository->getByStatus('expired')->count(),
        ];
    }

}
