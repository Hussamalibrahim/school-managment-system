<?php

namespace Tests\Feature;

use App\Models\Borrow;
use App\Models\Librarian;
use App\Models\LibraryBook;
use App\Models\Student;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Laravel\Sanctum\Sanctum;
use Tests\TestCase;

class BorrowTest extends TestCase
{
    use RefreshDatabase;

    protected function setUp(): void
    {
        parent::setUp();
        Sanctum::actingAs(Librarian::factory()->create(), ['*']);
    }

    public function test_can_borrow_book()
    {
        $student = Student::factory()->create();
        $book = LibraryBook::factory()->create();

        $response = $this->postJson('/api/borrows', [
            'student_id' => $student->id,
            'book_id' => $book->id,
        ]);

        $response->assertStatus(201)
                 ->assertJsonPath('message', 'تم استعارة الكتاب بنجاح');
                 
        $this->assertDatabaseHas('borrows', [
            'student_id' => $student->id,
            'book_id' => $book->id,
            'return_date' => null
        ]);
    }

    public function test_cannot_borrow_same_book_twice_if_active()
    {
        $student = Student::factory()->create();
        $book = LibraryBook::factory()->create();

        Borrow::factory()->create([
            'student_id' => $student->id,
            'book_id' => $book->id,
            'return_date' => null,
        ]);

        $response = $this->postJson('/api/borrows', [
            'student_id' => $student->id,
            'book_id' => $book->id,
        ]);

        $response->assertStatus(400);
    }
}
