<?php

namespace App\Http\Controllers;

use App\Http\Requests\StoreBorrowRequest;
use App\Http\Requests\ReturnBorrowRequest;
use App\Http\Resources\BorrowResource;
use App\Services\BorrowService;
use Exception;
use Illuminate\Http\Request;

class BorrowController extends Controller
{
    protected BorrowService $borrowService;

    public function __construct(BorrowService $borrowService)
    {
        $this->borrowService = $borrowService;
    }

    public function index()
    {
        return BorrowResource::collection($this->borrowService->getAllBorrows());
    }

    public function store(StoreBorrowRequest $request)
    {
        try {
            $borrow = $this->borrowService->borrowBook($request->validated());
            return response()->json([
                'message' => 'تم استعارة الكتاب بنجاح',
                'borrow' => new BorrowResource($borrow)
            ], 201);
        } catch (Exception $e) {
            return response()->json(['message' => $e->getMessage()], 400);
        }
    }

    public function returnBook(ReturnBorrowRequest $request, $id)
    {
        try {
            $borrow = $this->borrowService->returnBook($id);
            return response()->json([
                'message' => 'تم إرجاع الكتاب بنجاح',
                'borrow' => new BorrowResource($borrow)
            ]);
        } catch (Exception $e) {
            return response()->json(['message' => $e->getMessage()], 400);
        }
    }

    public function active()
    {
        return BorrowResource::collection($this->borrowService->getActiveBorrows());
    }

    public function returned()
    {
        return BorrowResource::collection($this->borrowService->getReturnedBorrows());
    }

    public function overdue()
    {
        return BorrowResource::collection($this->borrowService->getOverdueBorrows());
    }
}
