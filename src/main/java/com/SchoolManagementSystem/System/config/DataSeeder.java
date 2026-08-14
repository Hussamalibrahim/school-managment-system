package com.SchoolManagementSystem.System.config;

import com.SchoolManagementSystem.System.entity.AuthUser;
import com.SchoolManagementSystem.System.entity.academic.Assessment;
import com.SchoolManagementSystem.System.entity.academic.AssessmentResult;
import com.SchoolManagementSystem.System.entity.academic.ClassSchedule;
import com.SchoolManagementSystem.System.entity.academic.Exam;
import com.SchoolManagementSystem.System.entity.academic.ExamResult;
import com.SchoolManagementSystem.System.entity.academic.SchoolClass;
import com.SchoolManagementSystem.System.entity.academic.Subject;
import com.SchoolManagementSystem.System.entity.academic.TeacherSubject;
import com.SchoolManagementSystem.System.entity.academic.Semester;
import com.SchoolManagementSystem.System.entity.communication.Announcement;
import com.SchoolManagementSystem.System.entity.enumeration.AnnouncementStatus;
import com.SchoolManagementSystem.System.entity.enumeration.AttendanceStatus;
import com.SchoolManagementSystem.System.entity.enumeration.BorrowStatus;
import com.SchoolManagementSystem.System.entity.enumeration.ContinuousCategory;
import com.SchoolManagementSystem.System.entity.enumeration.EducationStage;
import com.SchoolManagementSystem.System.entity.enumeration.ExamCategory;
import com.SchoolManagementSystem.System.entity.enumeration.Gender;
import com.SchoolManagementSystem.System.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.System.entity.enumeration.PeriodNumber;
import com.SchoolManagementSystem.System.entity.enumeration.Role;
import com.SchoolManagementSystem.System.entity.enumeration.SchoolType;
import com.SchoolManagementSystem.System.entity.enumeration.SemesterName;
import com.SchoolManagementSystem.System.entity.enumeration.UserType;
import com.SchoolManagementSystem.System.entity.finance.*;
import com.SchoolManagementSystem.System.entity.library.Borrow;
import com.SchoolManagementSystem.System.entity.library.Library;
import com.SchoolManagementSystem.System.entity.library.LibraryBook;
import com.SchoolManagementSystem.System.entity.school.AcademicYear;
import com.SchoolManagementSystem.System.entity.school.School;
import com.SchoolManagementSystem.System.entity.student.Attendance;
import com.SchoolManagementSystem.System.entity.student.Student;
import com.SchoolManagementSystem.System.entity.student.StudentGuardian;
import com.SchoolManagementSystem.System.entity.student.Warning;
import com.SchoolManagementSystem.System.entity.user.*;
import com.SchoolManagementSystem.System.repository.academic.*;
import com.SchoolManagementSystem.System.repository.communication.AnnouncementRepository;
import com.SchoolManagementSystem.System.repository.finance.*;
import com.SchoolManagementSystem.System.repository.library.BorrowRepository;
import com.SchoolManagementSystem.System.repository.library.LibraryBookRepository;
import com.SchoolManagementSystem.System.repository.library.LibraryRepository;
import com.SchoolManagementSystem.System.repository.school.AcademicYearRepository;
import com.SchoolManagementSystem.System.repository.school.SchoolRepository;
import com.SchoolManagementSystem.System.repository.student.AttendanceRepository;
import com.SchoolManagementSystem.System.repository.student.StudentGuardianRepository;
import com.SchoolManagementSystem.System.repository.student.StudentRepository;
import com.SchoolManagementSystem.System.repository.student.WarningRepository;
import com.SchoolManagementSystem.System.repository.user.*;
import com.SchoolManagementSystem.System.security.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DataSeeder implements CommandLineRunner {

    private final SchoolRepository schoolRepository;
    private final AcademicYearRepository academicYearRepository;
    private final SemesterRepository semesterRepository;
    private final PrincipalRepository principalRepository;
    private final SecretaryRepository secretaryRepository;
    private final LibrarianRepository librarianRepository;
    private final TeacherRepository teacherRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherSubjectRepository teacherSubjectRepository;
    private final ClassScheduleRepository classScheduleRepository;
    private final StudentRepository studentRepository;
    private final GuardianRepository guardianRepository;
    private final StudentGuardianRepository studentGuardianRepository;
    private final AttendanceRepository attendanceRepository;
    private final AssessmentRepository assessmentRepository;
    private final AssessmentResultRepository assessmentResultRepository;
    private final ExamRepository examRepository;
    private final ExamResultRepository examResultRepository;
    private final WarningRepository warningRepository;
    private final FeeTypeRepository feeTypeRepository;
    private final ClassFeeRepository classFeeRepository;
    private final DiscountRepository discountRepository;
    private final StudentDiscountRepository studentDiscountRepository;
    private final PaymentRepository paymentRepository;
    private final LibraryRepository libraryRepository;
    private final LibraryBookRepository libraryBookRepository;
    private final BorrowRepository borrowRepository;
    private final AnnouncementRepository announcementRepository;
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (schoolRepository.count() > 0) {
            log.info("Database already seeded. Skipping seeder.");
            return;
        }

        log.info(">>> Starting Master Database Seeding for 5 Core Roles...");

        // 1. School
        School school = seedSchool();

        // 2. Academic Year & Semesters
        AcademicYear academicYear = seedAcademicYear(school);
        Semester semester1 = seedSemester(academicYear, SemesterName.FIRST, LocalDate.of(2025, 9, 1), LocalDate.of(2026, 1, 31));
        Semester semester2 = seedSemester(academicYear, SemesterName.SECOND, LocalDate.of(2026, 2, 1), LocalDate.of(2026, 6, 30));

        // 3. Principal (مدير)
        Principal principal = seedPrincipal(school);

        // 4. Secretary (أمين سر)
        Secretary secretary = seedSecretary(school);

        // 5. Librarian (أمين مكتبة)
        Librarian librarian = seedLibrarian(school);

        // 6. Teachers (معلمون)
        Map<String, Teacher> teachers = seedTeachers(school);

        // 7. Classes (صفوف وشعب)
        Map<String, SchoolClass> classes = seedClasses(school);

        // 8. Subjects (مواد دراسية)
        Map<String, Subject> subjects = seedSubjects();

        // 9. Teacher-Subject Assignments
        seedTeacherSubjects(teachers, subjects);

        // 10. Class Schedules (جدول الحصص الأسبوعي)
        Map<String, List<ClassSchedule>> schedules = seedClassSchedules(classes, teachers, subjects);

        // 11. Students (طلاب)
        List<Student> students = seedStudents(school, classes);

        // 12. Guardians (أولياء أمور) & Linking to Students
        seedGuardians(school, students);

        // 13. Attendance Records (سجل الحضور والغياب)
        seedAttendance(students);

        // 14. Continuous Assessments & Marks (تقييمات وواجبات)
        seedAssessmentsAndResults(schedules, semester1, teachers, students);

        // 15. Official Exams & Results (امتحانات ونتائج)
        seedExamsAndResults(classes, subjects, semester1, students);

        // 16. Warnings & Disciplinary Notes (إنذارات وملاحظات سلوكية)
        seedWarnings(students);

        // 17. Finance (رسوم، خصومات، مدفوعات)
        seedFinance(school, academicYear, classes, students);

        // 18. Library & Borrowing (مكتبة مدرسية وإعارة كتب)
        seedLibrary(school, students);

        // 19. Announcements (إعلانات مدرسية)
        seedAnnouncements();

        log.info(">>> Master Database Seeding Completed Successfully! All 5 roles are ready for testing.");
    }

    // ==========================================
    // 1. School
    // ==========================================
    private School seedSchool() {
        School school = new School();
        school.setName("Al Noor International Academy");
        school.setAddress("Damascus, Mezzeh - Education District");
        school.setPhone("0116655440");
        school.setSchoolType(SchoolType.PRIVATE);
        school.setSemesterName(SemesterName.FIRST);
        school.setEducationStages(Set.of(EducationStage.ELEMENTARY, EducationStage.MIDDLE, EducationStage.HIGH));
        return schoolRepository.save(school);
    }

    // ==========================================
    // 2. Academic Year & Semesters
    // ==========================================
    private AcademicYear seedAcademicYear(School school) {
        AcademicYear year = new AcademicYear();
        year.setSchool(school);
        year.setName("2025-2026");
        year.setStartDate(LocalDate.of(2025, 9, 1));
        year.setEndDate(LocalDate.of(2026, 6, 30));
        year.setCurrentYear(true);
        return academicYearRepository.save(year);
    }

    private Semester seedSemester(AcademicYear academicYear, SemesterName name, LocalDate start, LocalDate end) {
        Semester semester = new Semester();
        semester.setAcademicYear(academicYear);
        semester.setSemesterName(name);
        semester.setStartDate(start);
        semester.setEndDate(end);
        return semesterRepository.save(semester);
    }

    // ==========================================
    // 3. Principal (مدير)
    // ==========================================
    private Principal seedPrincipal(School school) {
        Principal principal = new Principal();
        principal.setSchool(school);
        principal.setNationalId("1000001");
        principal.setFirstName("Mohammad");
        principal.setLastName("Al-Khatib");
        principal.setPhone("0991111111");
        principal.setAddress("Damascus");
        principal.setHireDate(LocalDate.of(2018, 8, 1));
        principal = principalRepository.save(principal);

        createAuth("admin", "admin123", Role.PRINCIPAL, principal.getId());
        createAuth("principal", "123456", Role.PRINCIPAL, principal.getId());
        return principal;
    }

    // ==========================================
    // 4. Secretary (أمين سر)
    // ==========================================
    private Secretary seedSecretary(School school) {
        Secretary secretary = new Secretary();
        secretary.setSchool(school);
        secretary.setNationalId("1000002");
        secretary.setFirstName("Sara");
        secretary.setLastName("Al-Ahmad");
        secretary.setPhone("0992222222");
        secretary.setAddress("Damascus");
        secretary.setHireDate(LocalDate.of(2020, 9, 1));
        secretary = secretaryRepository.save(secretary);

        createAuth("secretary", "123456", Role.SECRETARY, secretary.getId());
        return secretary;
    }

    // ==========================================
    // 5. Librarian (أمين مكتبة)
    // ==========================================
    private Librarian seedLibrarian(School school) {
        Librarian librarian = new Librarian();
        librarian.setSchool(school);
        librarian.setNationalId("1000003");
        librarian.setFirstName("Omar");
        librarian.setLastName("Khaled");
        librarian.setPhone("0993333333");
        librarian.setAddress("Damascus");
        librarian.setHireDate(LocalDate.of(2021, 9, 1));
        librarian = librarianRepository.save(librarian);

        createAuth("librarian", "123456", Role.LIBRARIAN, librarian.getId());
        return librarian;
    }

    // ==========================================
    // 6. Teachers (معلمون)
    // ==========================================
    private Map<String, Teacher> seedTeachers(School school) {
        Map<String, Teacher> map = new HashMap<>();

        map.put("math", createTeacher(school, "2000001", "Mohammad", "Saleh", "Mathematics", "teacher1", "mohammad"));
        map.put("arabic", createTeacher(school, "2000002", "Lina", "Ahmad", "Arabic Language", "teacher2", "lina"));
        map.put("physics", createTeacher(school, "2000003", "Khaled", "Omar", "Physics", "teacher3", "khaled"));
        map.put("english", createTeacher(school, "2000004", "Nour", "Ali", "English Language", "teacher4", "nour"));
        map.put("chemistry", createTeacher(school, "2000005", "Basil", "Al-Qassem", "Chemistry", "teacher5", "basil"));
        map.put("history", createTeacher(school, "2000006", "Huda", "Mansour", "World History", "teacher6", "huda"));

        return map;
    }

    private Teacher createTeacher(School school, String nationalId, String firstName, String lastName,
                                  String specialization, String email1, String email2) {
        Teacher teacher = new Teacher();
        teacher.setSchool(school);
        teacher.setNationalId(nationalId);
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setPhone("099" + nationalId);
        teacher.setAddress("Damascus");
        teacher.setSpecialization(specialization);
        teacher.setHireDate(LocalDate.of(2021, 9, 1));
        teacher = teacherRepository.save(teacher);

        createAuth(email1, "123456", Role.TEACHER, teacher.getId());
        if (email2 != null && !email2.equals(email1)) {
            createAuth(email2, "123456", Role.TEACHER, teacher.getId());
        }
        return teacher;
    }

    // ==========================================
    // 7. Classes (صفوف وشعب)
    // ==========================================
    private Map<String, SchoolClass> seedClasses(School school) {
        Map<String, SchoolClass> map = new HashMap<>();
        map.put("10A", createSchoolClass(school, GradeLevel.GRADE_10, "A", "Room 101", 30));
        map.put("10B", createSchoolClass(school, GradeLevel.GRADE_10, "B", "Room 102", 30));
        map.put("11A", createSchoolClass(school, GradeLevel.GRADE_11, "A", "Room 201", 30));
        map.put("11B", createSchoolClass(school, GradeLevel.GRADE_11, "B", "Room 202", 30));
        map.put("12A", createSchoolClass(school, GradeLevel.GRADE_12, "A", "Room 301", 30));
        return map;
    }

    private SchoolClass createSchoolClass(School school, GradeLevel level, String section, String location, int capacity) {
        SchoolClass sc = new SchoolClass();
        sc.setSchool(school);
        sc.setGradeLevel(level);
        sc.setSection(section);
        sc.setLocation(location);
        sc.setCapacity(capacity);
        return schoolClassRepository.save(sc);
    }

    // ==========================================
    // 8. Subjects (مواد دراسية)
    // ==========================================
    private Map<String, Subject> seedSubjects() {
        Map<String, Subject> map = new HashMap<>();

        // Grade 10
        map.put("math10", createSubject("Mathematics 10", GradeLevel.GRADE_10, SemesterName.FIRST));
        map.put("physics10", createSubject("Physics 10", GradeLevel.GRADE_10, SemesterName.FIRST));
        map.put("chemistry10", createSubject("Chemistry 10", GradeLevel.GRADE_10, SemesterName.FIRST));
        map.put("arabic10", createSubject("Arabic Language 10", GradeLevel.GRADE_10, SemesterName.FIRST));
        map.put("english10", createSubject("English Language 10", GradeLevel.GRADE_10, SemesterName.FIRST));
        map.put("history10", createSubject("World History 10", GradeLevel.GRADE_10, SemesterName.FIRST));

        // Grade 11
        map.put("math11", createSubject("Advanced Mathematics 11", GradeLevel.GRADE_11, SemesterName.FIRST));
        map.put("physics11", createSubject("Physics 11", GradeLevel.GRADE_11, SemesterName.FIRST));
        map.put("chemistry11", createSubject("Chemistry 11", GradeLevel.GRADE_11, SemesterName.FIRST));
        map.put("arabic11", createSubject("Arabic Literature 11", GradeLevel.GRADE_11, SemesterName.FIRST));
        map.put("english11", createSubject("English Literature 11", GradeLevel.GRADE_11, SemesterName.FIRST));

        // Grade 12
        map.put("math12", createSubject("Calculus 12", GradeLevel.GRADE_12, SemesterName.FIRST));
        map.put("physics12", createSubject("Modern Physics 12", GradeLevel.GRADE_12, SemesterName.FIRST));
        map.put("chemistry12", createSubject("Organic Chemistry 12", GradeLevel.GRADE_12, SemesterName.FIRST));
        map.put("english12", createSubject("English for Academic Studies 12", GradeLevel.GRADE_12, SemesterName.FIRST));

        return map;
    }

    private Subject createSubject(String name, GradeLevel gradeLevel, SemesterName semesterName) {
        Subject subject = new Subject();
        subject.setName(name);
        subject.setGradeLevel(gradeLevel);
        subject.setSemesterName(semesterName);
        return subjectRepository.save(subject);
    }

    // ==========================================
    // 9. Teacher-Subject Assignments
    // ==========================================
    private void seedTeacherSubjects(Map<String, Teacher> teachers, Map<String, Subject> subjects) {
        connectTeacherSubject(teachers.get("math"), subjects.get("math10"));
        connectTeacherSubject(teachers.get("math"), subjects.get("math11"));
        connectTeacherSubject(teachers.get("math"), subjects.get("math12"));

        connectTeacherSubject(teachers.get("physics"), subjects.get("physics10"));
        connectTeacherSubject(teachers.get("physics"), subjects.get("physics11"));
        connectTeacherSubject(teachers.get("physics"), subjects.get("physics12"));

        connectTeacherSubject(teachers.get("chemistry"), subjects.get("chemistry10"));
        connectTeacherSubject(teachers.get("chemistry"), subjects.get("chemistry11"));
        connectTeacherSubject(teachers.get("chemistry"), subjects.get("chemistry12"));

        connectTeacherSubject(teachers.get("arabic"), subjects.get("arabic10"));
        connectTeacherSubject(teachers.get("arabic"), subjects.get("arabic11"));

        connectTeacherSubject(teachers.get("english"), subjects.get("english10"));
        connectTeacherSubject(teachers.get("english"), subjects.get("english11"));
        connectTeacherSubject(teachers.get("english"), subjects.get("english12"));

        connectTeacherSubject(teachers.get("history"), subjects.get("history10"));
    }

    private void connectTeacherSubject(Teacher teacher, Subject subject) {
        TeacherSubject ts = new TeacherSubject();
        ts.setTeacher(teacher);
        ts.setSubject(subject);
        teacherSubjectRepository.save(ts);
    }

    // ==========================================
    // 10. Class Schedules (جدول الحصص)
    // ==========================================
    private Map<String, List<ClassSchedule>> seedClassSchedules(
            Map<String, SchoolClass> classes,
            Map<String, Teacher> teachers,
            Map<String, Subject> subjects) {

        Map<String, List<ClassSchedule>> result = new HashMap<>();

        // Weekly schedule for Grade 10-A
        List<ClassSchedule> list10A = new ArrayList<>();
        list10A.add(createSchedule(classes.get("10A"), DayOfWeek.SUNDAY, PeriodNumber.PERIOD_1, teachers.get("math"), subjects.get("math10")));
        list10A.add(createSchedule(classes.get("10A"), DayOfWeek.SUNDAY, PeriodNumber.PERIOD_2, teachers.get("physics"), subjects.get("physics10")));
        list10A.add(createSchedule(classes.get("10A"), DayOfWeek.SUNDAY, PeriodNumber.PERIOD_3, teachers.get("arabic"), subjects.get("arabic10")));
        list10A.add(createSchedule(classes.get("10A"), DayOfWeek.MONDAY, PeriodNumber.PERIOD_1, teachers.get("chemistry"), subjects.get("chemistry10")));
        list10A.add(createSchedule(classes.get("10A"), DayOfWeek.MONDAY, PeriodNumber.PERIOD_2, teachers.get("english"), subjects.get("english10")));
        list10A.add(createSchedule(classes.get("10A"), DayOfWeek.TUESDAY, PeriodNumber.PERIOD_1, teachers.get("math"), subjects.get("math10")));
        list10A.add(createSchedule(classes.get("10A"), DayOfWeek.TUESDAY, PeriodNumber.PERIOD_2, teachers.get("history"), subjects.get("history10")));
        list10A.add(createSchedule(classes.get("10A"), DayOfWeek.WEDNESDAY, PeriodNumber.PERIOD_1, teachers.get("physics"), subjects.get("physics10")));
        list10A.add(createSchedule(classes.get("10A"), DayOfWeek.WEDNESDAY, PeriodNumber.PERIOD_2, teachers.get("english"), subjects.get("english10")));
        list10A.add(createSchedule(classes.get("10A"), DayOfWeek.THURSDAY, PeriodNumber.PERIOD_1, teachers.get("arabic"), subjects.get("arabic10")));
        list10A.add(createSchedule(classes.get("10A"), DayOfWeek.THURSDAY, PeriodNumber.PERIOD_2, teachers.get("chemistry"), subjects.get("chemistry10")));
        result.put("10A", list10A);

        // Weekly schedule for Grade 10-B
        List<ClassSchedule> list10B = new ArrayList<>();
        list10B.add(createSchedule(classes.get("10B"), DayOfWeek.SUNDAY, PeriodNumber.PERIOD_1, teachers.get("arabic"), subjects.get("arabic10")));
        list10B.add(createSchedule(classes.get("10B"), DayOfWeek.SUNDAY, PeriodNumber.PERIOD_2, teachers.get("math"), subjects.get("math10")));
        list10B.add(createSchedule(classes.get("10B"), DayOfWeek.MONDAY, PeriodNumber.PERIOD_1, teachers.get("english"), subjects.get("english10")));
        list10B.add(createSchedule(classes.get("10B"), DayOfWeek.MONDAY, PeriodNumber.PERIOD_2, teachers.get("physics"), subjects.get("physics10")));
        result.put("10B", list10B);

        // Weekly schedule for Grade 11-A
        List<ClassSchedule> list11A = new ArrayList<>();
        list11A.add(createSchedule(classes.get("11A"), DayOfWeek.SUNDAY, PeriodNumber.PERIOD_1, teachers.get("physics"), subjects.get("physics11")));
        list11A.add(createSchedule(classes.get("11A"), DayOfWeek.SUNDAY, PeriodNumber.PERIOD_2, teachers.get("english"), subjects.get("english11")));
        list11A.add(createSchedule(classes.get("11A"), DayOfWeek.MONDAY, PeriodNumber.PERIOD_1, teachers.get("math"), subjects.get("math11")));
        list11A.add(createSchedule(classes.get("11A"), DayOfWeek.MONDAY, PeriodNumber.PERIOD_2, teachers.get("chemistry"), subjects.get("chemistry11")));
        result.put("11A", list11A);

        return result;
    }

    private ClassSchedule createSchedule(SchoolClass schoolClass, DayOfWeek day, PeriodNumber period, Teacher teacher, Subject subject) {
        ClassSchedule cs = new ClassSchedule();
        cs.setSchoolClass(schoolClass);
        cs.setDayOfWeek(day);
        cs.setPeriodNumber(period);
        cs.setTeacher(teacher);
        cs.setSubject(subject);
        return classScheduleRepository.save(cs);
    }

    // ==========================================
    // 11. Students (طلاب)
    // ==========================================
    private List<Student> seedStudents(School school, Map<String, SchoolClass> classes) {
        List<Student> list = new ArrayList<>();

        // Grade 10-A
        list.add(createStudent(school, classes.get("10A"), "ST1001", "Ahmad", "Ali", Gender.MALE, "student1", "st1001"));
        list.add(createStudent(school, classes.get("10A"), "ST1002", "Mohammed", "Hassan", Gender.MALE, "student2", "st1002"));
        list.add(createStudent(school, classes.get("10A"), "ST1003", "Sara", "Omar", Gender.FEMALE, "student3", "st1003"));

        // Grade 10-B
        list.add(createStudent(school, classes.get("10B"), "ST1004", "Lina", "Khaled", Gender.FEMALE, "student4", "st1004"));
        list.add(createStudent(school, classes.get("10B"), "ST1005", "Yousef", "Mahmoud", Gender.MALE, "student5", "st1005"));
        list.add(createStudent(school, classes.get("10B"), "ST1006", "Karim", "Sami", Gender.MALE, "student6", "st1006"));

        // Grade 11-A
        list.add(createStudent(school, classes.get("11A"), "ST1101", "Zaid", "Al-Masri", Gender.MALE, "student7", "st1101"));
        list.add(createStudent(school, classes.get("11A"), "ST1102", "Maya", "Al-Halabi", Gender.FEMALE, "student8", "st1102"));

        // Grade 12-A
        list.add(createStudent(school, classes.get("12A"), "ST1201", "Tarek", "Al-Khatib", Gender.MALE, "student9", "st1201"));
        list.add(createStudent(school, classes.get("12A"), "ST1202", "Salma", "Al-Najjar", Gender.FEMALE, "student10", "st1202"));

        return list;
    }

    private Student createStudent(School school, SchoolClass schoolClass, String reg, String first, String last,
                                  Gender gender, String email1, String email2) {
        Student student = new Student();
        student.setSchool(school);
        student.setStudentSchoolClass(schoolClass);
        student.setRegistrationNumber(reg);
        student.setFirstName(first);
        student.setLastName(last);
        student.setGender(gender);
        student.setGradeLevel(schoolClass.getGradeLevel());
        student.setDateOfBirth(LocalDate.of(2009, 4, 15));
        student.setAddress("Damascus");
        student.setPhone("099" + reg);
        student.setEnrollmentDate(LocalDate.of(2025, 9, 1));
        student = studentRepository.save(student);

        createAuth(email1, "123456", Role.STUDENT, student.getId());
        if (email2 != null && !email2.equals(email1)) {
            createAuth(email2, "123456", Role.STUDENT, student.getId());
        }
        return student;
    }

    // ==========================================
    // 12. Guardians (أولياء أمور)
    // ==========================================
    private void seedGuardians(School school, List<Student> students) {
        // G1: Father of Ahmad Ali (ST1001) & Zaid Al-Masri (ST1101)
        Guardian g1 = createGuardian(school, "900001", "Ali", "Ahmad", "Civil Engineer", "guardian1", "parent1");
        connectGuardian(students.get(0), g1, true); // Ahmad
        connectGuardian(students.get(6), g1, true); // Zaid

        // G2: Mother of Sara Omar (ST1003)
        Guardian g2 = createGuardian(school, "900002", "Mona", "Ibrahim", "Pediatrician", "guardian2", "parent2");
        connectGuardian(students.get(2), g2, true); // Sara

        // G3: Father of Lina Khaled (ST1004)
        Guardian g3 = createGuardian(school, "900003", "Khaled", "Al-Mansoor", "Legal Consultant", "guardian3", "parent3");
        connectGuardian(students.get(3), g3, true); // Lina

        // G4: Father of Yousef Mahmoud (ST1005)
        Guardian g4 = createGuardian(school, "900004", "Mahmoud", "Al-Qudsi", "Businessman", "guardian4", "parent4");
        connectGuardian(students.get(4), g4, true); // Yousef

        // G5: Father of Tarek Al-Khatib (ST1201)
        Guardian g5 = createGuardian(school, "900005", "Sami", "Al-Khatib", "University Professor", "guardian5", "parent5");
        connectGuardian(students.get(8), g5, true); // Tarek
    }

    private Guardian createGuardian(School school, String nationalId, String first, String last, String occupation,
                                    String email1, String email2) {
        Guardian guardian = new Guardian();
        guardian.setSchool(school);
        guardian.setNationalId(nationalId);
        guardian.setFirstName(first);
        guardian.setLastName(last);
        guardian.setPhone("098" + nationalId);
        guardian.setAddress("Damascus");
        guardian.setOccupation(occupation);
        guardian = guardianRepository.save(guardian);

        createAuth(email1, "123456", Role.GUARDIAN, guardian.getId());
        if (email2 != null && !email2.equals(email1)) {
            createAuth(email2, "123456", Role.GUARDIAN, guardian.getId());
        }
        return guardian;
    }

    private void connectGuardian(Student student, Guardian guardian, boolean primary) {
        StudentGuardian relation = new StudentGuardian();
        relation.setStudent(student);
        relation.setGuardian(guardian);
        relation.setPrimaryGuardian(primary);
        studentGuardianRepository.save(relation);
    }

    // ==========================================
    // 13. Attendance (سجل الحضور والغياب)
    // ==========================================
    private void seedAttendance(List<Student> students) {
        LocalDate today = LocalDate.now();

        for (int i = 0; i < 7; i++) {
            LocalDate date = today.minusDays(i);

            for (int sIdx = 0; sIdx < students.size(); sIdx++) {
                Student student = students.get(sIdx);
                Attendance attendance = new Attendance();
                attendance.setStudent(student);
                attendance.setAttendanceDate(date);

                if (i == 1 && sIdx == 1) {
                    attendance.setAttendanceStatus(AttendanceStatus.ABSENT);
                } else if (i == 3 && sIdx == 3) {
                    attendance.setAttendanceStatus(AttendanceStatus.LATE);
                } else if (i == 5 && sIdx == 4) {
                    attendance.setAttendanceStatus(AttendanceStatus.EXCUSED);
                } else {
                    attendance.setAttendanceStatus(AttendanceStatus.PRESENT);
                }

                attendanceRepository.save(attendance);
            }
        }
    }

    // ==========================================
    // 14. Continuous Assessments & Results
    // ==========================================
    private void seedAssessmentsAndResults(
            Map<String, List<ClassSchedule>> schedules,
            Semester semester,
            Map<String, Teacher> teachers,
            List<Student> students) {

        List<ClassSchedule> sched10A = schedules.get("10A");
        if (sched10A == null || sched10A.isEmpty()) return;

        // 1. Math Quiz 1
        Assessment mathQuiz = new Assessment();
        mathQuiz.setClassSchedule(sched10A.get(0));
        mathQuiz.setSemester(semester);
        mathQuiz.setTeacher(teachers.get("math"));
        mathQuiz.setName("Algebra & Functions Quiz");
        mathQuiz.setCategory(ContinuousCategory.QUIZ);
        mathQuiz.setMaxScore(20.0);
        mathQuiz.setWeight(10.0);
        mathQuiz.setAssessmentDate(LocalDate.now().minusDays(10));
        mathQuiz = assessmentRepository.save(mathQuiz);

        // Grade results for 10-A students
        createAssessmentResult(students.get(0), mathQuiz, 19.0);
        createAssessmentResult(students.get(1), mathQuiz, 16.5);
        createAssessmentResult(students.get(2), mathQuiz, 18.0);

        // 2. Physics Lab Project
        Assessment physicsProj = new Assessment();
        physicsProj.setClassSchedule(sched10A.get(1));
        physicsProj.setSemester(semester);
        physicsProj.setTeacher(teachers.get("physics"));
        physicsProj.setName("Mechanics Lab Experiment Report");
        physicsProj.setCategory(ContinuousCategory.PROJECT);
        physicsProj.setMaxScore(25.0);
        physicsProj.setWeight(15.0);
        physicsProj.setAssessmentDate(LocalDate.now().minusDays(5));
        physicsProj = assessmentRepository.save(physicsProj);

        createAssessmentResult(students.get(0), physicsProj, 24.0);
        createAssessmentResult(students.get(1), physicsProj, 21.0);
        createAssessmentResult(students.get(2), physicsProj, 23.5);

        // 3. Arabic Oral Test
        Assessment arabicOral = new Assessment();
        arabicOral.setClassSchedule(sched10A.get(2));
        arabicOral.setSemester(semester);
        arabicOral.setTeacher(teachers.get("arabic"));
        arabicOral.setName("Grammar & Poetry Recitation");
        arabicOral.setCategory(ContinuousCategory.ORAL_TEST);
        arabicOral.setMaxScore(15.0);
        arabicOral.setWeight(10.0);
        arabicOral.setAssessmentDate(LocalDate.now().minusDays(3));
        arabicOral = assessmentRepository.save(arabicOral);

        createAssessmentResult(students.get(0), arabicOral, 14.5);
        createAssessmentResult(students.get(1), arabicOral, 13.0);
        createAssessmentResult(students.get(2), arabicOral, 15.0);
    }

    private void createAssessmentResult(Student student, Assessment assessment, Double score) {
        AssessmentResult result = new AssessmentResult();
        result.setStudent(student);
        result.setAssessment(assessment);
        result.setScore(score);
        assessmentResultRepository.save(result);
    }

    // ==========================================
    // 15. Official Exams & Results
    // ==========================================
    private void seedExamsAndResults(
            Map<String, SchoolClass> classes,
            Map<String, Subject> subjects,
            Semester semester,
            List<Student> students) {

        // Midterm Exam for Math 10 (Class 10-A)
        Exam mathExam = new Exam();
        mathExam.setSchoolClass(classes.get("10A"));
        mathExam.setSubject(subjects.get("math10"));
        mathExam.setSemester(semester);
        mathExam.setCategory(ExamCategory.MIDTERM);
        mathExam.setMaxScore(100.0);
        mathExam.setWeight(40.0);
        mathExam.setExamDateTime(LocalDateTime.now().minusDays(15).withHour(9).withMinute(0));
        mathExam.setDurationMinutes(90);
        mathExam = examRepository.save(mathExam);

        createExamResult(mathExam, students.get(0), 96.0);
        createExamResult(mathExam, students.get(1), 84.5);
        createExamResult(mathExam, students.get(2), 91.0);

        // Midterm Exam for Physics 10 (Class 10-A)
        Exam physicsExam = new Exam();
        physicsExam.setSchoolClass(classes.get("10A"));
        physicsExam.setSubject(subjects.get("physics10"));
        physicsExam.setSemester(semester);
        physicsExam.setCategory(ExamCategory.MIDTERM);
        physicsExam.setMaxScore(100.0);
        physicsExam.setWeight(40.0);
        physicsExam.setExamDateTime(LocalDateTime.now().minusDays(12).withHour(10).withMinute(30));
        physicsExam.setDurationMinutes(90);
        physicsExam = examRepository.save(physicsExam);

        createExamResult(physicsExam, students.get(0), 92.5);
        createExamResult(physicsExam, students.get(1), 78.0);
        createExamResult(physicsExam, students.get(2), 95.0);

        // Midterm Exam for Arabic 10 (Class 10-A)
        Exam arabicExam = new Exam();
        arabicExam.setSchoolClass(classes.get("10A"));
        arabicExam.setSubject(subjects.get("arabic10"));
        arabicExam.setSemester(semester);
        arabicExam.setCategory(ExamCategory.MIDTERM);
        arabicExam.setMaxScore(100.0);
        arabicExam.setWeight(40.0);
        arabicExam.setExamDateTime(LocalDateTime.now().minusDays(10).withHour(9).withMinute(0));
        arabicExam.setDurationMinutes(90);
        arabicExam = examRepository.save(arabicExam);

        createExamResult(arabicExam, students.get(0), 88.0);
        createExamResult(arabicExam, students.get(1), 82.0);
        createExamResult(arabicExam, students.get(2), 97.5);
    }

    private void createExamResult(Exam exam, Student student, Double score) {
        ExamResult er = new ExamResult();
        er.setExam(exam);
        er.setStudent(student);
        er.setScore(score);
        examResultRepository.save(er);
    }

    // ==========================================
    // 16. Warnings & Disciplinary Notes
    // ==========================================
    private void seedWarnings(List<Student> students) {
        Warning w1 = new Warning();
        w1.setStudent(students.get(1)); // Mohammed Hassan
        w1.setWarningDate(LocalDate.now().minusDays(8));
        w1.setReason("Repeated unexcused morning tardiness during first period.");
        warningRepository.save(w1);

        Warning w2 = new Warning();
        w2.setStudent(students.get(4)); // Yousef Mahmoud
        w2.setWarningDate(LocalDate.now().minusDays(4));
        w2.setReason("Failure to submit required weekly Physics laboratory assignment on time.");
        warningRepository.save(w2);
    }

    // ==========================================
    // 17. Finance (رسوم، خصومات، مدفوعات)
    // ==========================================
    private void seedFinance(
            School school,
            AcademicYear academicYear,
            Map<String, SchoolClass> classes,
            List<Student> students) {

        // Fee Types
        FeeType tuitionType = createFeeType("Annual Tuition Fee");
        FeeType busType = createFeeType("Transportation Bus Service");
        FeeType labType = createFeeType("Science & Computer Lab Fee");

        // Class Fees
        createClassFee(classes.get("10A"), academicYear, tuitionType, 3500.0);
        createClassFee(classes.get("10A"), academicYear, labType, 300.0);
        createClassFee(classes.get("11A"), academicYear, tuitionType, 4000.0);
        createClassFee(classes.get("12A"), academicYear, tuitionType, 4500.0);

        // Discounts
        Discount excellenceDiscount = createDiscount("Academic Excellence Merit Scholarship", 15.0, "Top 5% GPA in previous academic year");
        Discount siblingDiscount = createDiscount("Sibling Family Discount", 10.0, "Enrolled with more than one sibling in school");

        // Assign discount to Ahmad Ali (ST1001)
        StudentDiscount sd1 = new StudentDiscount();
        sd1.setStudent(students.get(0));
        sd1.setDiscount(excellenceDiscount);
        studentDiscountRepository.save(sd1);

        // Payments
        createPayment(students.get(0), 1500.0, LocalDate.of(2025, 9, 5), "Tuition Installment #1 - Receipt 10421");
        createPayment(students.get(0), 1000.0, LocalDate.of(2025, 11, 10), "Tuition Installment #2 - Receipt 10892");
        createPayment(students.get(1), 1800.0, LocalDate.of(2025, 9, 6), "Tuition & Lab Fee Payment - Receipt 10455");
        createPayment(students.get(2), 2000.0, LocalDate.of(2025, 9, 8), "First Semester Full Payment - Receipt 10512");
        createPayment(students.get(6), 1500.0, LocalDate.of(2025, 9, 12), "Tuition Installment #1 - Receipt 10633");
    }

    private FeeType createFeeType(String name) {
        FeeType ft = new FeeType();
        ft.setName(name);
        return feeTypeRepository.save(ft);
    }

    private void createClassFee(SchoolClass sc, AcademicYear ay, FeeType ft, Double amount) {
        ClassFee cf = new ClassFee();
        cf.setSchoolClass(sc);
        cf.setAcademicYear(ay);
        cf.setFeeType(ft);
        cf.setAmount(amount);
        classFeeRepository.save(cf);
    }

    private Discount createDiscount(String name, Double percentage, String reason) {
        Discount d = new Discount();
        d.setName(name);
        d.setPercentage(percentage);
        d.setReason(reason);
        return discountRepository.save(d);
    }

    private void createPayment(Student student, Double amount, LocalDate date, String notes) {
        Payment p = new Payment();
        p.setStudent(student);
        p.setAmount(amount);
        p.setPaymentDate(date);
        p.setNotes(notes);
        paymentRepository.save(p);
    }

    // ==========================================
    // 18. Library & Books (مكتبة مدرسية)
    // ==========================================
    private void seedLibrary(School school, List<Student> students) {
        Library library = new Library();
        library.setSchool(school);
        library = libraryRepository.save(library);

        LibraryBook b1 = createBook(library, "Calculus: Early Transcendentals", "James Stewart", "978-1285741550", "Mathematics", "Comprehensive guide to differential and integral calculus.");
        LibraryBook b2 = createBook(library, "Fundamentals of Physics", "David Halliday", "978-1118230718", "Physics", "Standard university & advanced high school physics textbook.");
        LibraryBook b3 = createBook(library, "Clean Code: A Handbook of Agile Craftsmanship", "Robert C. Martin", "978-0132350884", "Computer Science", "Essential principles of writing clean, maintainable software.");
        LibraryBook b4 = createBook(library, "The Old Man and the Sea", "Ernest Hemingway", "978-0684801223", "Literature", "Nobel prize winning classic literature novel.");

        // Borrow Records
        Borrow borrow1 = new Borrow();
        borrow1.setStudent(students.get(0)); // Ahmad
        borrow1.setBook(b1);
        borrow1.setBorrowDate(LocalDate.now().minusDays(14));
        borrow1.setDueDate(LocalDate.now().plusDays(14));
        borrow1.setStatus(BorrowStatus.BORROWED);
        borrowRepository.save(borrow1);

        Borrow borrow2 = new Borrow();
        borrow2.setStudent(students.get(2)); // Sara
        borrow2.setBook(b4);
        borrow2.setBorrowDate(LocalDate.now().minusDays(20));
        borrow2.setDueDate(LocalDate.now().minusDays(6));
        borrow2.setReturnDate(LocalDate.now().minusDays(7));
        borrow2.setStatus(BorrowStatus.RETURNED);
        borrowRepository.save(borrow2);
    }

    private LibraryBook createBook(Library lib, String title, String author, String isbn, String cat, String desc) {
        LibraryBook b = new LibraryBook();
        b.setLibrary(lib);
        b.setTitle(title);
        b.setAuthor(author);
        b.setIsbn(isbn);
        b.setCategory(cat);
        b.setDescription(desc);
        return libraryBookRepository.save(b);
    }

    // ==========================================
    // 19. Announcements (إعلانات مدرسية)
    // ==========================================
    private void seedAnnouncements() {
        createAnnouncement(
                "Welcome to the Academic Year 2025-2026",
                "Dear students, parents, and faculty members: We welcome you to the new academic year filled with ambition and excellence. Please ensure adhering to school schedules and dress code.",
                UserType.STUDENT,
                AnnouncementStatus.PUBLISHED,
                LocalDate.now().minusDays(20)
        );

        createAnnouncement(
                "First Semester Midterm Exam Schedules Released",
                "The examination board has published the official midterm timetable. Students and parents can view schedules through their respective portal dashboards.",
                UserType.STUDENT,
                AnnouncementStatus.PUBLISHED,
                LocalDate.now().minusDays(14)
        );

        createAnnouncement(
                "Annual Parent-Teacher Conference",
                "We cordially invite all parents to attend the first semester parent-teacher conference on Thursday at 4:00 PM in the school auditorium to discuss student academic progress.",
                UserType.GUARDIAN,
                AnnouncementStatus.PUBLISHED,
                LocalDate.now().minusDays(7)
        );

        createAnnouncement(
                "Annual Science and Innovation Olympiad Registration",
                "Registration is now open for students interested in participating in the annual Science, Robotics, and Mathematics Olympiad. Inquire with your science teachers.",
                UserType.STUDENT,
                AnnouncementStatus.PUBLISHED,
                LocalDate.now().minusDays(2)
        );
    }

    private void createAnnouncement(String title, String content, UserType userType, AnnouncementStatus status, LocalDate date) {
        Announcement a = new Announcement();
        a.setTitle(title);
        a.setContent(content);
        a.setUserType(userType);
        a.setStatus(status);
        a.setPublishDate(date);
        announcementRepository.save(a);
    }

    // ==========================================
    // Auth Helper
    // ==========================================
    private void createAuth(String email, String password, Role role, Long refId) {
        AuthUser user = new AuthUser();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        user.setRefId(refId);
        user.setEnabled(true);
        authUserRepository.save(user);
    }
}