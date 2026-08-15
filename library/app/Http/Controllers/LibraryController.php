<?php

namespace App\Http\Controllers;

use App\Http\Requests\StoreLibraryRequest;
use App\Http\Requests\UpdateLibraryRequest;
use App\Http\Resources\LibraryResource;
use App\Services\LibraryService;

class LibraryController extends Controller
{
    protected LibraryService $libraryService;

    public function __construct(LibraryService $libraryService)
    {
        $this->libraryService = $libraryService;
    }

    public function index()
    {
        return LibraryResource::collection($this->libraryService->getAllLibraries());
    }

    public function show($id)
    {
        $library = $this->libraryService->getLibraryById($id);

        if (!$library) {
            return response()->json(['message' => 'المكتبة غير موجودة'], 404);
        }

        return new LibraryResource($library);
    }

    public function store(StoreLibraryRequest $request)
    {
        $library = $this->libraryService->createLibrary($request->validated());

        return response()->json([
            'message' => 'تمت إضافة المكتبة بنجاح',
            'library' => new LibraryResource($library),
        ], 201);
    }

    public function update(UpdateLibraryRequest $request, $id)
    {
        $library = $this->libraryService->updateLibrary($id, $request->validated());

        if (!$library) {
            return response()->json(['message' => 'المكتبة غير موجودة'], 404);
        }

        return response()->json([
            'message' => 'تم تحديث بيانات المكتبة بنجاح',
            'library' => new LibraryResource($library),
        ]);
    }

    public function destroy($id)
    {
        $deleted = $this->libraryService->deleteLibrary($id);

        if (!$deleted) {
            return response()->json(['message' => 'المكتبة غير موجودة'], 404);
        }

        return response()->json(['message' => 'تم حذف المكتبة بنجاح']);
    }
}
