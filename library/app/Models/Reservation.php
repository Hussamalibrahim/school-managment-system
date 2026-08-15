<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Reservation extends Model
{
    use HasFactory;

    protected $fillable = [
        'student_id',
        'book_id',
        'reservation_date',
        'status',
    ];

    // student_id is a remote reference (school-core microservice) - resolve
    // via App\Services\StudentServiceClient, not an Eloquent relation.

    public function book()
    {
        return $this->belongsTo(LibraryBook::class, 'book_id');
    }
}
