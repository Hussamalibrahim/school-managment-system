DROP ALL OBJECTS;

CREATE TABLE schools
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(255),
    address         VARCHAR(255),
    phone           VARCHAR(50),
    logo_path       VARCHAR(255),
    school_category VARCHAR(50)
);

CREATE TABLE auth_users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    role     VARCHAR(50),
    ref_id   BIGINT,
    enabled  BOOLEAN DEFAULT TRUE
);


CREATE TABLE principals
(
    id          BIGINT PRIMARY KEY,
    school_id   BIGINT,
    national_id VARCHAR(100),
    first_name  VARCHAR(100),
    last_name   VARCHAR(100),
    phone       VARCHAR(50),
    email       VARCHAR(255),
    address     VARCHAR(255),
    status      VARCHAR(50),
    hire_date   DATE,
    CONSTRAINT fk_principal_school FOREIGN KEY (school_id) REFERENCES schools (id)
);

CREATE TABLE teachers
(
    id             BIGINT PRIMARY KEY,
    school_id      BIGINT,
    national_id    VARCHAR(100),
    first_name     VARCHAR(100),
    last_name      VARCHAR(100),
    phone          VARCHAR(50),
    email          VARCHAR(255),
    address        VARCHAR(255),
    status         VARCHAR(50),
    hire_date      DATE,
    specialization VARCHAR(100),
    CONSTRAINT fk_teacher_school FOREIGN KEY (school_id) REFERENCES schools (id)
);

CREATE TABLE secretaries
(
    id          BIGINT PRIMARY KEY,
    school_id   BIGINT,
    national_id VARCHAR(100),
    first_name  VARCHAR(100),
    last_name   VARCHAR(100),
    phone       VARCHAR(50),
    email       VARCHAR(255),
    address     VARCHAR(255),
    status      VARCHAR(50),
    hire_date   DATE,
    CONSTRAINT fk_secretary_school FOREIGN KEY (school_id) REFERENCES schools (id)
);

CREATE TABLE librarians
(
    id          BIGINT PRIMARY KEY,
    school_id   BIGINT,
    national_id VARCHAR(100),
    first_name  VARCHAR(100),
    last_name   VARCHAR(100),
    phone       VARCHAR(50),
    email       VARCHAR(255),
    address     VARCHAR(255),
    status      VARCHAR(50),
    hire_date   DATE,
    CONSTRAINT fk_librarian_school FOREIGN KEY (school_id) REFERENCES schools (id)
);

CREATE TABLE guardians
(
    id          BIGINT PRIMARY KEY,
    school_id   BIGINT,
    national_id VARCHAR(100),
    first_name  VARCHAR(100),
    last_name   VARCHAR(100),
    phone       VARCHAR(50),
    email       VARCHAR(255),
    address     VARCHAR(255),
    status      VARCHAR(50),
    hire_date   DATE,
    occupation  VARCHAR(100),
    CONSTRAINT fk_guardian_school FOREIGN KEY (school_id) REFERENCES schools (id)
);


CREATE TABLE school_class
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    school_id   BIGINT,
    grade_level VARCHAR(50),
    section     VARCHAR(10),
    location    VARCHAR(255),
    capacity    INT,
    CONSTRAINT fk_class_school FOREIGN KEY (school_id) REFERENCES schools (id)
);

CREATE TABLE students
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    school_id           BIGINT,
    school_class_id     BIGINT,
    registration_number VARCHAR(100) NOT NULL,
    first_name          VARCHAR(100),
    last_name           VARCHAR(100),
    gender              VARCHAR(20),
    grade_level         VARCHAR(50),
    date_of_birth       DATE,
    address             VARCHAR(255),
    status              VARCHAR(50),
    enrollment_date     DATE,
    phone               VARCHAR(50),
    notes               TEXT,
    CONSTRAINT fk_student_school FOREIGN KEY (school_id) REFERENCES schools (id),
    CONSTRAINT fk_student_class FOREIGN KEY (school_class_id) REFERENCES school_class (id)
);

CREATE TABLE student_guardians
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id       BIGINT,
    guardian_id      BIGINT,
    primary_guardian BOOLEAN,
    CONSTRAINT fk_sg_student FOREIGN KEY (student_id) REFERENCES students (id),
    CONSTRAINT fk_sg_guardian FOREIGN KEY (guardian_id) REFERENCES guardians (id)
);

CREATE TABLE attendance
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id        BIGINT,
    attendance_date   DATE,
    attendance_status VARCHAR(20),
    CONSTRAINT fk_att_student FOREIGN KEY (student_id) REFERENCES students (id)
);

CREATE TABLE warnings
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id   BIGINT,
    warning_date DATE,
    reason       VARCHAR(255),
    CONSTRAINT fk_warning_student FOREIGN KEY (student_id) REFERENCES students (id)
);


CREATE TABLE academic_years
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    school_id    BIGINT,
    name         VARCHAR(100),
    start_date   DATE,
    end_date     DATE,
    current_year BOOLEAN,
    CONSTRAINT fk_year_school FOREIGN KEY (school_id) REFERENCES schools (id)
);

CREATE TABLE semesters
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    academic_year_id BIGINT,
    name             VARCHAR(100),
    start_date       DATE,
    end_date         DATE,
    CONSTRAINT fk_sem_year FOREIGN KEY (academic_year_id) REFERENCES academic_years (id)
);

CREATE TABLE subjects
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    school_id   BIGINT,
    name        VARCHAR(255),
    description TEXT,
    CONSTRAINT fk_subject_school FOREIGN KEY (school_id) REFERENCES schools (id)
);

CREATE TABLE teacher_subjects
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    teacher_id BIGINT,
    subject_id BIGINT,
    CONSTRAINT fk_ts_teacher FOREIGN KEY (teacher_id) REFERENCES teachers (id),
    CONSTRAINT fk_ts_subject FOREIGN KEY (subject_id) REFERENCES subjects (id)
);

CREATE TABLE class_schedules
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    class_id      BIGINT,
    subject_id    BIGINT,
    teacher_id    BIGINT,
    day_of_week   VARCHAR(20),
    period_number VARCHAR(20),
    CONSTRAINT fk_schedule_class FOREIGN KEY (class_id) REFERENCES school_class (id),
    CONSTRAINT fk_schedule_subject FOREIGN KEY (subject_id) REFERENCES subjects (id),
    CONSTRAINT fk_schedule_teacher FOREIGN KEY (teacher_id) REFERENCES teachers (id)
);

CREATE TABLE assessments
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    subject_id      BIGINT,
    semester_id     BIGINT,
    teacher_id      BIGINT,
    name            VARCHAR(255),
    category        VARCHAR(50),
    max_score       DOUBLE,
    weight          DOUBLE,
    assessment_date DATE,
    CONSTRAINT fk_ass_subject FOREIGN KEY (subject_id) REFERENCES subjects (id),
    CONSTRAINT fk_ass_semester FOREIGN KEY (semester_id) REFERENCES semesters (id),
    CONSTRAINT fk_ass_teacher FOREIGN KEY (teacher_id) REFERENCES teachers (id)
);

CREATE TABLE assessment_results
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id    BIGINT,
    assessment_id BIGINT,
    score         DOUBLE,
    notes         TEXT,
    CONSTRAINT fk_ar_student FOREIGN KEY (student_id) REFERENCES students (id),
    CONSTRAINT fk_ar_assessment FOREIGN KEY (assessment_id) REFERENCES assessments (id),
    CONSTRAINT uq_student_assessment UNIQUE (student_id, assessment_id)
);

CREATE TABLE education_records
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id       BIGINT,
    academic_year_id BIGINT,
    final_average    DOUBLE,
    absence_days     INT,
    passed           BOOLEAN,
    notes            TEXT,
    CONSTRAINT fk_er_student FOREIGN KEY (student_id) REFERENCES students (id),
    CONSTRAINT fk_er_year FOREIGN KEY (academic_year_id) REFERENCES academic_years (id)
);

CREATE TABLE libraries
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    school_id BIGINT UNIQUE,
    CONSTRAINT fk_library_school FOREIGN KEY (school_id) REFERENCES schools (id)
);

CREATE TABLE library_book
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    library_id  BIGINT,
    title       VARCHAR(255),
    author      VARCHAR(255),
    isbn        VARCHAR(100) UNIQUE,
    category    VARCHAR(100),
    description TEXT,
    CONSTRAINT fk_book_library FOREIGN KEY (library_id) REFERENCES libraries (id)
);

CREATE TABLE borrows
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id  BIGINT,
    book_id     BIGINT,
    borrow_date DATE,
    due_date    DATE,
    return_date DATE,
    status      VARCHAR(50),
    CONSTRAINT fk_borrow_student FOREIGN KEY (student_id) REFERENCES students (id),
    CONSTRAINT fk_borrow_book FOREIGN KEY (book_id) REFERENCES library_book (id)
);


CREATE TABLE payments
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id   BIGINT,
    amount       DOUBLE,
    payment_date DATE,
    notes        TEXT,
    CONSTRAINT fk_payment_student FOREIGN KEY (student_id) REFERENCES students (id)
);

CREATE TABLE discounts
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255),
    percentage DOUBLE,
    reason     TEXT
);

CREATE TABLE student_discounts
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id  BIGINT,
    discount_id BIGINT,
    CONSTRAINT fk_sd_student FOREIGN KEY (student_id) REFERENCES students (id),
    CONSTRAINT fk_sd_discount FOREIGN KEY (discount_id) REFERENCES discounts (id)
);

CREATE TABLE files
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    file_name        VARCHAR(255),
    file_path        VARCHAR(255),
    file_type        VARCHAR(50),
    owner_type       VARCHAR(50),
    owner_id         BIGINT,
    uploaded_by_type VARCHAR(50),
    uploaded_by_id   BIGINT
);

CREATE TABLE announcements
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    title        VARCHAR(255),
    content      TEXT,
    user_type    VARCHAR(50),
    target_id    BIGINT,
    status       VARCHAR(50),
    publish_date DATE
);

CREATE TABLE notifications
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    title             VARCHAR(255),
    message           TEXT,
    user_type         VARCHAR(50),
    target_id         BIGINT,
    is_read           BOOLEAN,
    notification_date DATE
);