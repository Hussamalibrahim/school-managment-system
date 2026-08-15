<?php

namespace App\Repositories;

use App\Models\Library;
use App\Repositories\Interfaces\LibraryRepositoryInterface;

class LibraryRepository extends BaseRepository implements LibraryRepositoryInterface
{
    public function __construct(Library $model)
    {
        parent::__construct($model);
    }
}
