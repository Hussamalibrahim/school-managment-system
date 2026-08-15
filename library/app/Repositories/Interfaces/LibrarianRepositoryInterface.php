<?php

namespace App\Repositories\Interfaces;

interface LibrarianRepositoryInterface extends RepositoryInterface
{
    public function findByEmail(string $email);
}
