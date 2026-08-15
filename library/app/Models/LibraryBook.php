<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class LibraryBook extends Model
{
    use HasFactory;

    protected $fillable = [
        'library_id',
        'title',
        'author',
        'isbn',
        'category',
    ];

    public function library()
    {
        return $this->belongsTo(Library::class);
    }

    public function borrows()
    {
        return $this->hasMany(Borrow::class, 'book_id');
    }

    public function coverImage()
    {
        return $this->morphOne(File::class, 'owner');
    }
}
