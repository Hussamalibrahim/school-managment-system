<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class File extends Model
{
    use HasFactory;

    protected $fillable = [
        'owner_type',
        'owner_id',
        'file_name',
        'file_path',
        'mime_type',
        'file_size',
        'uploaded_at',
    ];

    public function owner()
    {
        return $this->morphTo();
    }
}
