<?php

namespace App\Services;

use App\Repositories\Interfaces\BookRepositoryInterface;
use Illuminate\Contracts\Pagination\LengthAwarePaginator;

class BookService
{
    protected BookRepositoryInterface $bookRepository;

    public function __construct(BookRepositoryInterface $bookRepository)
    {
        $this->bookRepository = $bookRepository;
    }

    public function getAllBooks(array $filters, string $sort, int $perPage = 15): LengthAwarePaginator
    {
        return $this->bookRepository->searchAndPaginate($filters, $sort, $perPage);
    }

    public function getBookById(int $id)
    {
        return $this->bookRepository->find($id);
    }

    public function createBook(array $data)
    {
        return $this->bookRepository->create($data);
    }

    public function updateBook(int $id, array $data)
    {
        $this->bookRepository->update($id, $data);
        return $this->bookRepository->find($id);
    }

    public function deleteBook(int $id)
    {
        return $this->bookRepository->delete($id);
    }
}
