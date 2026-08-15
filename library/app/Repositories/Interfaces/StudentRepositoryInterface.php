<?php

namespace App\Repositories\Interfaces;

/**
 * Students are NOT owned by this service - they live in the Spring Boot
 * school-core microservice. This interface intentionally does NOT extend
 * RepositoryInterface (no create/update/delete: this service is read-only
 * for student data).
 */
interface StudentRepositoryInterface
{
    public function find(int $id): ?array;

    public function search(string $query): array;

    public function exists(int $id): bool;
}
