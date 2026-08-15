<?php

namespace Database\Factories;

use App\Models\Librarian;
use Illuminate\Database\Eloquent\Factories\Factory;

class LibraryFactory extends Factory
{
    public function definition(): array
    {
        return [
            'name' => fake()->company() . ' Library',
            'librarian_id' => Librarian::factory(),
        ];
    }
}
