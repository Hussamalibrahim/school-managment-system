<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Library extends Model
{
    use HasFactory;

    protected $fillable = [
        'name',
        'librarian_id',
    ];

    public function librarian()
    {
        return $this->belongsTo(Librarian::class);
    }

    public function libraryBooks()
    {
        return $this->hasMany(LibraryBook::class);
    }
}
