<?php

namespace App\Http\Controllers;

use App\Http\Requests\UploadBookCoverRequest;
use App\Http\Resources\FileResource;
use App\Services\FileService;
use Exception;

class FileController extends Controller
{
    protected FileService $fileService;

    public function __construct(FileService $fileService)
    {
        $this->fileService = $fileService;
    }

    public function uploadCover(UploadBookCoverRequest $request, $id)
    {
        try {
            $file = $this->fileService->uploadBookCover($id, $request->file('cover'));
            return response()->json([
                'message' => 'تم رفع الغلاف بنجاح',
                'file' => new FileResource($file)
            ], 201);
        } catch (Exception $e) {
            return response()->json(['message' => $e->getMessage()], 400);
        }
    }
}
