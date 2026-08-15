<?php

namespace App\Http\Resources;

use App\Services\StudentServiceClient;
use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class BorrowResource extends JsonResource
{
    public function toArray(Request $request): array
    {
        // Student data is fetched (cache-backed) from the school-core
        // microservice at read time. If the remote service is unreachable
        // we still return the borrow record with student=null rather than
        // failing the whole response.
        $student = app(StudentServiceClient::class)->find($this->student_id);

        return [
            'id' => $this->id,
            'student_id' => $this->student_id,
            'student' => $student ? new StudentResource($student) : null,
            'book' => new BookResource($this->whenLoaded('book')),
            'borrow_date' => $this->borrow_date,
            'due_date' => $this->due_date,
            'return_date' => $this->return_date,
            'is_overdue' => $this->return_date === null && $this->due_date < now()->toDateString(),
            'created_at' => $this->created_at,
            'updated_at' => $this->updated_at,
        ];
    }
}
