<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Borrow extends Model
{
    use HasFactory;

    protected $fillable = [
        'student_id',
        'book_id',
        'borrow_date',
        'due_date',
        'return_date',
    ];

    // NOTE: there is intentionally no belongsTo(Student::class) here.
    // Students live in the Spring Boot school-core service, not in this
    // database, so `student_id` is a plain foreign reference (no local FK
    // constraint). Use App\Services\StudentServiceClient to resolve it.

    public function book()
    {
        return $this->belongsTo(LibraryBook::class, 'book_id');
    }
}
