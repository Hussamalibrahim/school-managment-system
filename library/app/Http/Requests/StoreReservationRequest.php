<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class StoreReservationRequest extends FormRequest
{
    public function authorize(): bool
    {
        return true;
    }

    public function rules(): array
    {
        return [
            // Student existence is checked against the school-core service
            // in ReservationService::reserveBook(), not via `exists:`.
            'student_id' => ['required', 'integer', 'min:1'],
            'book_id' => ['required', 'exists:library_books,id'],
        ];
    }
}
