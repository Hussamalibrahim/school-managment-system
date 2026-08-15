<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('reservations', function (Blueprint $table) {
            $table->id();

            // No local FK: student lives in the school-core microservice.
            $table->unsignedBigInteger('student_id');
            $table->index('student_id');

            $table->foreignId('book_id')->constrained('library_books')->onDelete('cascade');
            $table->date('reservation_date');
            $table->enum('status', ['pending', 'fulfilled', 'cancelled', 'expired'])->default('pending');
            $table->timestamps();
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('reservations');
    }
};
