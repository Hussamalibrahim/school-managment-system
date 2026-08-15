# Library API

This is a Laravel 12 API Only project for a library system. 

## Clean Architecture
This project follows Clean Architecture and SOLID principles using:
- **Controllers**: Handling HTTP requests and responses.
- **Form Requests**: Request validation.
- **Services**: Business logic.
- **Repositories**: Database interactions.
- **API Resources**: Formatting JSON responses.

## Setup Instructions

1. **Install Dependencies**
   ```bash
   composer install
   ```

2. **Configure Environment**
   Copy `.env.example` to `.env` and set up your database connection.
   ```bash
   cp .env.example .env
   php artisan key:generate
   ```

3. **Run Migrations & Seeders**
   ```bash
   php artisan migrate --seed
   ```

4. **Serve the Application**
   ```bash
   php artisan serve
   ```

## Features
- Authentication (Sanctum for Librarians only).
- Book Management (CRUD, Search, Pagination).
- Borrowing System (Borrow, Return, Tracking active/returned/overdue).
- Reports (Most borrowed, Overdue, Student history).
- File Upload for Book Covers.

## Testing
To run the automated tests:
```bash
php artisan test
```
