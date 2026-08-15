<?php

namespace Tests\Feature;

use App\Models\Borrow;
use App\Models\Librarian;
use App\Models\Student;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Laravel\Sanctum\Sanctum;
use Tests\TestCase;

class ReportTest extends TestCase
{
    use RefreshDatabase;

    protected function setUp(): void
    {
        parent::setUp();
        Sanctum::actingAs(Librarian::factory()->create(), ['*']);
    }

    public function test_can_get_student_history()
    {
        $student = Student::factory()->create();
        Borrow::factory(3)->create(['student_id' => $student->id]);

        $response = $this->getJson("/api/reports/student-history/{$student->id}");

        $response->assertStatus(200)
                 ->assertJsonCount(3, 'data');
    }
}
