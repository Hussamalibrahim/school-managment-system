<?php

namespace App\Http\Controllers;

use App\Http\Requests\StoreBookRequest;
use App\Http\Requests\UpdateBookRequest;
use App\Http\Resources\BookResource;
use App\Services\BookService;
use Illuminate\Http\Request;

class BookController extends Controller
{
    protected BookService $bookService;

    public function __construct(BookService $bookService)
    {
        $this->bookService = $bookService;
    }

    public function index(Request $request)
    {
        $filters = $request->only(['title', 'author', 'isbn', 'category']);
        $sort = $request->input('sort', 'created_at');
        $perPage = $request->input('per_page', 15);

        $books = $this->bookService->getAllBooks($filters, $sort, $perPage);

        return BookResource::collection($books);
    }

    public function show($id)
    {
        $book = $this->bookService->getBookById($id);

        if (!$book) {
            return response()->json(['message' => 'الكتاب غير موجود'], 404);
        }

        return new BookResource($book);
    }

    public function store(StoreBookRequest $request)
    {
        $book = $this->bookService->createBook($request->validated());

        return response()->json([
            'message' => 'تمت إضافة الكتاب بنجاح',
            'book' => new BookResource($book)
        ], 201);
    }

    public function update(UpdateBookRequest $request, $id)
    {
        $book = $this->bookService->updateBook($id, $request->validated());

        if (!$book) {
            return response()->json(['message' => 'الكتاب غير موجود'], 404);
        }

        return response()->json([
            'message' => 'تم تحديث بيانات الكتاب بنجاح',
            'book' => new BookResource($book)
        ]);
    }

    public function destroy($id)
    {
        $deleted = $this->bookService->deleteBook($id);

        if (!$deleted) {
            return response()->json(['message' => 'الكتاب غير موجود'], 404);
        }

        return response()->json(['message' => 'تم حذف الكتاب بنجاح']);
    }
}
