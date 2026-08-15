<?php

namespace App\Http\Controllers;

use App\Http\Resources\StudentResource;
use App\Repositories\Interfaces\StudentRepositoryInterface;
use Illuminate\Http\Request;

/**
 * Thin proxy to the Spring Boot school-core service, so the librarian
 * frontend only needs one base URL + one auth token (this service's),
 * not a second set of credentials for school-core.
 */
class StudentController extends Controller
{
    protected StudentRepositoryInterface $studentRepository;

    public function __construct(StudentRepositoryInterface $studentRepository)
    {
        $this->studentRepository = $studentRepository;
    }

    public function search(Request $request)
    {
        $request->validate([
            'query' => ['required', 'string', 'min:1'],
        ]);

        $students = $this->studentRepository->search($request->input('query'));

        return StudentResource::collection(collect($students));
    }

    public function show($id)
    {
        $student = $this->studentRepository->find((int) $id);

        if (!$student) {
            return response()->json(['message' => 'الطالب غير موجود'], 404);
        }

        return new StudentResource($student);
    }
}
