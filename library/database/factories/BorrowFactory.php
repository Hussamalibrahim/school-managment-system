<?php

namespace Database\Factories;

use App\Models\LibraryBook;
use Illuminate\Database\Eloquent\Factories\Factory;
use Illuminate\Support\Carbon;

class BorrowFactory extends Factory
{
    public function definition(): array
    {
        return [
            // Students are not local; use a plausible remote id (matches the
            // demo student ids seeded on the school-core / mock side).
            'student_id' => fake()->numberBetween(1, 50),
            'book_id' => LibraryBook::factory(),
            'borrow_date' => Carbon::now()->subDays(rand(1, 30))->toDateString(),
            'due_date' => Carbon::now()->addDays(rand(-10, 14))->toDateString(),
            'return_date' => fake()->boolean(50) ? Carbon::now()->subDays(rand(1, 5))->toDateString() : null,
        ];
    }
}
