<?php

namespace App\Repositories\Interfaces;

use Illuminate\Contracts\Pagination\LengthAwarePaginator;

interface BookRepositoryInterface extends RepositoryInterface
{
    public function searchAndPaginate(array $filters, string $sort, int $perPage = 15): LengthAwarePaginator;
    public function getMostBorrowedBooks(int $limit = 10);
}
