<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('borrows', function (Blueprint $table) {
            $table->id();

            // No FK constraint: student_id references a record owned by the
            // Spring Boot school-core microservice, not a table in this
            // database. Existence is checked at write-time via
            // App\Services\StudentServiceClient, not via the database.
            $table->unsignedBigInteger('student_id');
            $table->index('student_id');

            $table->foreignId('book_id')->constrained('library_books')->onDelete('cascade');
            $table->date('borrow_date');
            $table->date('due_date');
            $table->date('return_date')->nullable();
            $table->timestamps();
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('borrows');
    }
};
