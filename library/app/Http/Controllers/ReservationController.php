<?php

namespace App\Http\Controllers;

use App\Http\Requests\StoreReservationRequest;
use App\Http\Resources\ReservationResource;
use App\Services\ReservationService;
use Exception;

class ReservationController extends Controller
{
    protected ReservationService $reservationService;

    public function __construct(ReservationService $reservationService)
    {
        $this->reservationService = $reservationService;
    }

    public function index()
    {
        return ReservationResource::collection($this->reservationService->getAllReservations());
    }

    public function store(StoreReservationRequest $request)
    {
        try {
            $reservation = $this->reservationService->reserveBook($request->validated());
            return response()->json([
                'message' => 'تم حجز الكتاب بنجاح',
                'reservation' => new ReservationResource($reservation),
            ], 201);
        } catch (Exception $e) {
            return response()->json(['message' => $e->getMessage()], 400);
        }
    }

    public function fulfill($id)
    {
        try {
            $reservation = $this->reservationService->fulfillReservation($id);
            return response()->json([
                'message' => 'تم تنفيذ الحجز (تسليم الكتاب)',
                'reservation' => new ReservationResource($reservation),
            ]);
        } catch (Exception $e) {
            return response()->json(['message' => $e->getMessage()], 400);
        }
    }

    public function cancel($id)
    {
        try {
            $reservation = $this->reservationService->cancelReservation($id);
            return response()->json([
                'message' => 'تم إلغاء الحجز',
                'reservation' => new ReservationResource($reservation),
            ]);
        } catch (Exception $e) {
            return response()->json(['message' => $e->getMessage()], 400);
        }
    }

    public function pending()
    {
        return ReservationResource::collection($this->reservationService->getPendingReservations());
    }

    public function studentReservations($studentId)
    {
        return ReservationResource::collection($this->reservationService->getStudentReservations((int) $studentId));
    }
}
