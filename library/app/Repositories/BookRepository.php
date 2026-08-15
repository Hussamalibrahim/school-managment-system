<?php

namespace App\Repositories;

use App\Models\LibraryBook;
use App\Repositories\Interfaces\BookRepositoryInterface;
use Illuminate\Contracts\Pagination\LengthAwarePaginator;
use Illuminate\Support\Facades\DB;

class BookRepository extends BaseRepository implements BookRepositoryInterface
{
    public function __construct(LibraryBook $model)
    {
        parent::__construct($model);
    }

    public function searchAndPaginate(array $filters, string $sort, int $perPage = 15): LengthAwarePaginator
    {
        $query = $this->model->newQuery();

        if (isset($filters['title'])) {
            $query->where('title', 'like', '%' . $filters['title'] . '%');
        }
        if (isset($filters['author'])) {
            $query->where('author', 'like', '%' . $filters['author'] . '%');
        }
        if (isset($filters['isbn'])) {
            $query->where('isbn', 'like', '%' . $filters['isbn'] . '%');
        }
        if (isset($filters['category'])) {
            $query->where('category', 'like', '%' . $filters['category'] . '%');
        }

        $sortField = $sort === 'title' ? 'title' : 'created_at';
        $query->orderBy($sortField, 'asc');

        return $query->paginate($perPage);
    }

    public function getMostBorrowedBooks(int $limit = 10)
    {
        return $this->model->withCount('borrows')
            ->orderByDesc('borrows_count')
            ->take($limit)
            ->get();
    }
}
