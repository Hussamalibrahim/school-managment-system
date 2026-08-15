<?php

namespace App\Services;

use Illuminate\Support\Facades\Cache;
use Illuminate\Support\Facades\Http;
use Illuminate\Support\Facades\Log;

/**
 * Talks to the Spring Boot "school-core" microservice to read student data.
 *
 * This service NEVER writes student data and never stores a full local copy.
 * It only caches short-lived, read-only lookups so the library service can
 * keep working (briefly) even if the school-core service is slow/unreachable.
 */
class StudentServiceClient
{
    protected string $baseUrl;
    protected ?string $token;
    protected int $timeout;
    protected int $cacheTtl;

    public function __construct()
    {
        $this->baseUrl = rtrim(config('services.school_api.url'), '/');
        $this->token = config('services.school_api.token');
        $this->timeout = (int) config('services.school_api.timeout', 3);
        $this->cacheTtl = (int) config('services.school_api.cache_ttl', 60);
    }

    protected function client()
    {
        return Http::baseUrl($this->baseUrl)
            ->withToken($this->token)
            ->acceptJson()
            ->timeout($this->timeout)
            ->retry(2, 200); // 2 retries, 200ms apart, for transient network blips
    }

    /**
     * Fetch a single student by id.
     * Returns null if the student doesn't exist OR the remote service failed.
     * Callers must treat null as "cannot confirm this student" either way.
     */
    public function find(int $studentId): ?array
    {
        return Cache::remember(
            "school_api:student:{$studentId}",
            $this->cacheTtl,
            function () use ($studentId) {
                try {
                    $response = $this->client()->get("/api/students/{$studentId}");

                    if ($response->status() === 404) {
                        return null;
                    }

                    if ($response->failed()) {
                        Log::warning('school_api.find_failed', [
                            'student_id' => $studentId,
                            'status' => $response->status(),
                        ]);
                        return null;
                    }

                    return $response->json();
                } catch (\Throwable $e) {
                    Log::error('school_api.unreachable', [
                        'student_id' => $studentId,
                        'error' => $e->getMessage(),
                    ]);
                    return null;
                }
            }
        );
    }

    /**
     * Search students by name or registration number.
     * Proxies the librarian's search UI to school-core so the frontend
     * only ever has to talk to this library service.
     */
    public function search(string $query): array
    {
        try {
            $response = $this->client()->get('/api/students/search', ['query' => $query]);

            if ($response->failed()) {
                Log::warning('school_api.search_failed', [
                    'query' => $query,
                    'status' => $response->status(),
                ]);
                return [];
            }

            return $response->json() ?? [];
        } catch (\Throwable $e) {
            Log::error('school_api.unreachable', [
                'query' => $query,
                'error' => $e->getMessage(),
            ]);
            return [];
        }
    }

    /**
     * True/false existence check without caring about the payload.
     */
    public function exists(int $studentId): bool
    {
        return $this->find($studentId) !== null;
    }

    public function forget(int $studentId): void
    {
        Cache::forget("school_api:student:{$studentId}");
    }
}
