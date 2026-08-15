<?php

use App\Http\Controllers\AuthController;
use App\Http\Controllers\BookController;
use App\Http\Controllers\BorrowController;
use App\Http\Controllers\FileController;
use App\Http\Controllers\LibraryController;
use App\Http\Controllers\ReportController;
use App\Http\Controllers\ReservationController;
use App\Http\Controllers\StudentController;
use Illuminate\Support\Facades\Route;

Route::prefix('auth')->group(function () {
    Route::post('login', [AuthController::class, 'login']);
});

Route::middleware('auth:sanctum')->group(function () {

    Route::prefix('auth')->group(function () {
        Route::post('logout', [AuthController::class, 'logout']);
        Route::get('me', [AuthController::class, 'me']);
    });

    // UC-L1: Manage Libraries
    Route::apiResource('libraries', LibraryController::class);

    // UC-L2: Manage Library Books
    Route::apiResource('books', BookController::class);
    Route::post('books/{id}/upload-cover', [FileController::class, 'uploadCover']);

    // Proxy to the Spring Boot school-core service (search/read only).
    // Kept here so the librarian frontend only needs this service's token.
    Route::prefix('students')->group(function () {
        Route::get('search', [StudentController::class, 'search']);
        Route::get('{id}', [StudentController::class, 'show']);
    });

    // UC-L3: Manage Reservations
    Route::prefix('reservations')->group(function () {
        Route::get('/', [ReservationController::class, 'index']);
        Route::post('/', [ReservationController::class, 'store']);
        Route::post('{id}/fulfill', [ReservationController::class, 'fulfill']);
        Route::post('{id}/cancel', [ReservationController::class, 'cancel']);
        Route::get('pending', [ReservationController::class, 'pending']);
        Route::get('student/{studentId}', [ReservationController::class, 'studentReservations']);
    });

    // UC-L4: Manage Borrowing Operations
    Route::prefix('borrows')->group(function () {
        Route::get('/', [BorrowController::class, 'index']);
        Route::post('/', [BorrowController::class, 'store']);
        Route::post('{id}/return', [BorrowController::class, 'returnBook']);
        Route::get('active', [BorrowController::class, 'active']);
        Route::get('returned', [BorrowController::class, 'returned']);
        Route::get('overdue', [BorrowController::class, 'overdue']);
    });

    // UC-L6: View Library Reports
    Route::prefix('reports')->group(function () {
        Route::get('books', [ReportController::class, 'books']);
        Route::get('most-borrowed-books', [ReportController::class, 'mostBorrowedBooks']);
        Route::get('currently-borrowed', [ReportController::class, 'currentlyBorrowed']);
        Route::get('overdue-books', [ReportController::class, 'overdueBooks']);
        Route::get('student-history/{studentId}', [ReportController::class, 'studentHistory']);
        Route::get('reservation-stats', [ReportController::class, 'reservationStats']);
    });

});
