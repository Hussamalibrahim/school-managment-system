<?php

namespace App\Services;

use App\Repositories\Interfaces\LibrarianRepositoryInterface;
use Illuminate\Support\Facades\Hash;
use Illuminate\Validation\ValidationException;

class AuthService
{
    protected LibrarianRepositoryInterface $librarianRepository;

    public function __construct(LibrarianRepositoryInterface $librarianRepository)
    {
        $this->librarianRepository = $librarianRepository;
    }

    public function login(array $credentials)
    {
        $librarian = $this->librarianRepository->findByEmail($credentials['email']);

        if (!$librarian || !Hash::check($credentials['password'], $librarian->password)) {
            throw ValidationException::withMessages([
                'email' => ['البيانات المدخلة غير صحيحة.'],
            ]);
        }

        $token = $librarian->createToken('auth_token')->plainTextToken;

        return [
            'librarian' => $librarian,
            'token' => $token,
        ];
    }

    public function logout($librarian)
    {
        $librarian->currentAccessToken()->delete();
        return true;
    }
}
