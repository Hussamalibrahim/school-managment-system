<?php

namespace App\Repositories;

use App\Repositories\Interfaces\StudentRepositoryInterface;
use App\Services\StudentServiceClient;

class StudentRepository implements StudentRepositoryInterface
{
    protected StudentServiceClient $client;

    public function __construct(StudentServiceClient $client)
    {
        $this->client = $client;
    }

    public function find(int $id): ?array
    {
        return $this->client->find($id);
    }

    public function search(string $query): array
    {
        return $this->client->search($query);
    }

    public function exists(int $id): bool
    {
        return $this->client->exists($id);
    }
}
