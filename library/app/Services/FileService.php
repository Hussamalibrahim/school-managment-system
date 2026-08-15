<?php

namespace App\Services;

use App\Repositories\Interfaces\BookRepositoryInterface;
use App\Repositories\Interfaces\FileRepositoryInterface;
use Exception;
use Illuminate\Http\UploadedFile;
use Illuminate\Support\Carbon;

class FileService
{
    protected FileRepositoryInterface $fileRepository;
    protected BookRepositoryInterface $bookRepository;

    public function __construct(
        FileRepositoryInterface $fileRepository,
        BookRepositoryInterface $bookRepository
    ) {
        $this->fileRepository = $fileRepository;
        $this->bookRepository = $bookRepository;
    }

    public function uploadBookCover(int $bookId, UploadedFile $file)
    {
        $book = $this->bookRepository->find($bookId);
        if (!$book) {
            throw new Exception("الكتاب غير موجود");
        }

        $path = $file->store('covers', 'public');

        $fileData = [
            'owner_type' => get_class($book),
            'owner_id' => $book->id,
            'file_name' => $file->getClientOriginalName(),
            'file_path' => $path,
            'mime_type' => $file->getMimeType(),
            'file_size' => $file->getSize(),
            'uploaded_at' => Carbon::now(),
        ];

        return $this->fileRepository->create($fileData);
    }
}
