<?php

namespace Database\Factories;

use App\Models\LibraryBook;
use Illuminate\Database\Eloquent\Factories\Factory;
use Illuminate\Support\Carbon;

class ReservationFactory extends Factory
{
    public function definition(): array
    {
        return [
            'student_id' => fake()->numberBetween(1, 50),
            'book_id' => LibraryBook::factory(),
            'reservation_date' => Carbon::now()->subDays(rand(0, 10))->toDateString(),
            'status' => fake()->randomElement(['pending', 'fulfilled', 'cancelled', 'expired']),
        ];
    }
}
