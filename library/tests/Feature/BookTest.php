<?php

namespace Tests\Feature;

use App\Models\Librarian;
use App\Models\Library;
use App\Models\LibraryBook;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Laravel\Sanctum\Sanctum;
use Tests\TestCase;

class BookTest extends TestCase
{
    use RefreshDatabase;

    protected function setUp(): void
    {
        parent::setUp();
        $librarian = Librarian::factory()->create();
        Sanctum::actingAs($librarian, ['*']);
    }

    public function test_can_get_all_books()
    {
        LibraryBook::factory(3)->create();

        $response = $this->getJson('/api/books');

        $response->assertStatus(200)
                 ->assertJsonStructure(['data' => [['id', 'title', 'author']]]);
    }

    public function test_can_create_book()
    {
        $library = Library::factory()->create();

        $response = $this->postJson('/api/books', [
            'title' => 'Test Book',
            'author' => 'Author Name',
            'isbn' => '1234567890123',
            'category' => 'Fiction',
            'library_id' => $library->id,
        ]);

        $response->assertStatus(201)
                 ->assertJsonPath('book.title', 'Test Book');
                 
        $this->assertDatabaseHas('library_books', ['title' => 'Test Book']);
    }
}
