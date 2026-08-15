<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class StoreBorrowRequest extends FormRequest
{
    public function authorize(): bool
    {
        return true;
    }

    public function rules(): array
    {
        return [
            // NOTE: student existence is NOT validated with `exists:` here
            // because students live in the Spring Boot school-core service,
            // not in this database. BorrowService::borrowBook() confirms the
            // student id via StudentServiceClient before creating the record.
            'student_id' => ['required', 'integer', 'min:1'],
            'book_id' => ['required', 'exists:library_books,id'],
        ];
    }
}
