<?php

namespace App\Http\Resources;

use App\Services\StudentServiceClient;
use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class ReservationResource extends JsonResource
{
    public function toArray(Request $request): array
    {
        $student = app(StudentServiceClient::class)->find($this->student_id);

        return [
            'id' => $this->id,
            'student_id' => $this->student_id,
            'student' => $student ? new StudentResource($student) : null,
            'book' => new BookResource($this->whenLoaded('book')),
            'reservation_date' => $this->reservation_date,
            'status' => $this->status,
            'created_at' => $this->created_at,
            'updated_at' => $this->updated_at,
        ];
    }
}
