<?php

namespace App\Http\Resources;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

/**
 * Wraps a raw array coming back from the Spring Boot school-core service
 * (App\Services\StudentServiceClient), NOT an Eloquent model - this service
 * does not store student data.
 *
 * Expected shape from school-core:
 * { id, registrationNumber, fullName, className }
 * Field names are defensive (::class checks) in case the remote contract
 * changes slightly; confirm exact field names with the Spring Boot team.
 */
class StudentResource extends JsonResource
{
    public function toArray(Request $request): array
    {
        $student = $this->resource ?? [];

        if (!is_array($student)) {
            $student = (array) $student;
        }

        return [
            'id' => $student['id'] ?? null,
            'registration_number' => $student['registrationNumber'] ?? $student['registration_number'] ?? null,
            'full_name' => $student['fullName'] ?? $student['full_name'] ?? $student['name'] ?? null,
            'class_name' => $student['className'] ?? $student['class_name'] ?? null,
        ];
    }
}
