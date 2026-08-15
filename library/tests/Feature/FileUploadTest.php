<?php

namespace Tests\Feature;

use App\Models\Librarian;
use App\Models\LibraryBook;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Illuminate\Http\UploadedFile;
use Illuminate\Support\Facades\Storage;
use Laravel\Sanctum\Sanctum;
use Tests\TestCase;

class FileUploadTest extends TestCase
{
    use RefreshDatabase;

    protected function setUp(): void
    {
        parent::setUp();
        Sanctum::actingAs(Librarian::factory()->create(), ['*']);
    }

    public function test_can_upload_book_cover()
    {
        Storage::fake('public');
        
        $book = LibraryBook::factory()->create();
        $file = UploadedFile::fake()->image('cover.jpg');

        $response = $this->postJson("/api/books/{$book->id}/upload-cover", [
            'cover' => $file,
        ]);

        $response->assertStatus(201);
        
        $this->assertDatabaseHas('files', [
            'owner_type' => get_class($book),
            'owner_id' => $book->id,
            'file_name' => 'cover.jpg',
        ]);
    }
}
