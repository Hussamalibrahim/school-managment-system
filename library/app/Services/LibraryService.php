<?php

namespace App\Services;

use App\Repositories\Interfaces\LibraryRepositoryInterface;

class LibraryService
{
    protected LibraryRepositoryInterface $libraryRepository;

    public function __construct(LibraryRepositoryInterface $libraryRepository)
    {
        $this->libraryRepository = $libraryRepository;
    }

    public function getAllLibraries()
    {
        return $this->libraryRepository->all();
    }

    public function getLibraryById(int $id)
    {
        return $this->libraryRepository->find($id);
    }

    public function createLibrary(array $data)
    {
        return $this->libraryRepository->create($data);
    }

    public function updateLibrary(int $id, array $data)
    {
        $this->libraryRepository->update($id, $data);
        return $this->libraryRepository->find($id);
    }

    public function deleteLibrary(int $id)
    {
        return $this->libraryRepository->delete($id);
    }
}
