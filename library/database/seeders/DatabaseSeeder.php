<?php

namespace Database\Seeders;

use App\Models\Borrow;
use App\Models\Librarian;
use App\Models\Library;
use App\Models\LibraryBook;
use Illuminate\Database\Seeder;

class DatabaseSeeder extends Seeder
{
    public function run(): void
    {
        $librarian = Librarian::factory()->create([
            'name' => 'Test Librarian',
            'email' => 'librarian@example.com',
        ]);

        $library = Library::factory()->create([
            'librarian_id' => $librarian->id,
        ]);

        $books = LibraryBook::factory(20)->create([
            'library_id' => $library->id,
        ]);

        foreach (range(1, 10) as $studentId) {
            Borrow::factory(rand(1, 5))->create([
                'student_id' => $studentId,
                'book_id' => $books->random()->id,
            ]);
        }
    }
}
