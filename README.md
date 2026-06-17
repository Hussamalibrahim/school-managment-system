# School Management System

Backend System built with:

* Java 17
* Spring Boot 3
* Spring Data JPA
* MySQL 8
* Docker

# Requirements

Before running the project, install:

* Docker Desktop

Verify installation:

docker --version
docker compose version

# First Run

Clone repository:

git clone <repository-url>
cd school-management-system

Run application:

docker compose up --build

Application will be available at:

http://localhost:8081


# Stop Application

docker compose down

# Reset Database

WARNING: This deletes all database data.

docker compose down -v

Then:

docker compose up --build

# Environment Variables

Configuration is stored inside:

env
    DB_HOST=mysql-db
    DB_PORT=3306
    DB_NAME=school_management_system
    DB_USER=root
    DB_PASSWORD=root
    
# API Base URL
http://localhost:8081/api
