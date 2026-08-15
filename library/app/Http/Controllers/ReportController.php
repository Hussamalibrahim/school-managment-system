<?php

namespace App\Http\Controllers;

use App\Http\Resources\BookResource;
use App\Http\Resources\BorrowResource;
use App\Services\ReportService;
use Illuminate\Http\Request;


class ReportController extends Controller
{
    protected ReportService $reportService;

    public function __construct(ReportService $reportService)
    {
        $this->reportService = $reportService;
    }

    public function books()
    {
        return BookResource::collection($this->reportService->getAllBooks());
    }

    public function mostBorrowedBooks()
    {
        return BookResource::collection($this->reportService->getMostBorrowedBooks());
    }

    public function currentlyBorrowed()
    {
        return BookResource::collection($this->reportService->getCurrentlyBorrowedBooks());
    }

    public function overdueBooks()
    {
        return BookResource::collection($this->reportService->getOverdueBooks());
    }

    public function studentHistory($studentId)
    {
        return BorrowResource::collection($this->reportService->getStudentHistory($studentId));
    }

    public function reservationStats()
    {
        return response()->json($this->reportService->getReservationStats());
    }
}
