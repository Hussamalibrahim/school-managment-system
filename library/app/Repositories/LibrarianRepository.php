<?php

namespace App\Repositories;

use App\Models\Librarian;
use App\Repositories\Interfaces\LibrarianRepositoryInterface;

class LibrarianRepository extends BaseRepository implements LibrarianRepositoryInterface
{
    public function __construct(Librarian $model)
    {
        parent::__construct($model);
    }

    public function findByEmail(string $email)
    {
        return $this->model->where('email', $email)->first();
    }
}
