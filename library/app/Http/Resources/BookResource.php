<?php

namespace App\Http\Resources;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class BookResource extends JsonResource
{
    public function toArray(Request $request): array
    {
        return [
            'id' => $this->id,
            'title' => $this->title,
            'author' => $this->author,
            'isbn' => $this->isbn,
            'category' => $this->category,
            'library_id' => $this->library_id,
            'cover' => new FileResource($this->whenLoaded('coverImage')),
            'borrows_count' => $this->whenCounted('borrows'),
            'created_at' => $this->created_at,
            'updated_at' => $this->updated_at,
        ];
    }
}
