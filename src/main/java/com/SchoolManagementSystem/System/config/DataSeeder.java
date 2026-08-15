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
import com.SchoolManagementSystem.System.tenant.TenantContext;
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
            log.info(">>> Database already contains schools. Skipping Master Seeder.");
            return;
        }

        log.info("==================================================================");
        log.info(">>> STARTING ULTRA-RICH MASTER DATABASE SEEDING (MULTI-TENANT) <<<");
        log.info("==================================================================");

        // ==========================================
        // TENANT 1: Al Noor International Academy (الرئيسية)
        // ==========================================
        School s1 = new School();
        s1.setName("مدرسة النور الدولية النموذجية");
        s1.setCode("al-noor-academy");
        s1.setAddress("دمشق، المزة - الحي الدبلوماسي والتعليمي");
        s1.setPhone("0116655440");
        s1.setSchoolType(SchoolType.PRIVATE);
        s1.setSemesterName(SemesterName.FIRST);
        s1.setEducationStages(Set.of(EducationStage.ELEMENTARY, EducationStage.MIDDLE, EducationStage.HIGH));
        s1 = schoolRepository.save(s1);

        seedFullSchoolData(s1);

        // ==========================================
        // TENANT 2: Al-Amal Modern School (مدرسة الأمل الحديثة)
        // ==========================================
        School s2 = new School();
        s2.setName("مدرسة الأمل الحديثة");
        s2.setCode("al-amal-modern-school");
        s2.setAddress("دمشق، الشعلان - شارع المتنبي");
        s2.setPhone("0113322110");
        s2.setSchoolType(SchoolType.PRIVATE);
        s2.setSemesterName(SemesterName.FIRST);
        s2.setEducationStages(Set.of(EducationStage.MIDDLE, EducationStage.HIGH));
        s2 = schoolRepository.save(s2);
        seedSecondarySchoolData(s2, "alamal");

        // ==========================================
        // TENANT 3: Damascus Excellence High School (ثانوية المتفوقين)
        // ==========================================
        School s3 = new School();
        s3.setName("ثانوية دمشق للمتفوقين الأولى");
        s3.setCode("damascus-excellence-high");
        s3.setAddress("دمشق، كفرسوسة - مجمع المدارس النموذجية");
        s3.setPhone("0112114477");
        s3.setSchoolType(SchoolType.PUBLIC);
        s3.setSemesterName(SemesterName.FIRST);
        s3.setEducationStages(Set.of(EducationStage.HIGH));
        s3 = schoolRepository.save(s3);
        seedSecondarySchoolData(s3, "excellence");

        log.info("==================================================================");
        log.info(">>> MASTER DATABASE SEEDING COMPLETED! ALL MODULES POPULATED! <<<");
        log.info("==================================================================");
    }

    private void seedFullSchoolData(School school) {
        TenantContext.set(school.getId(), school.getCode());

        try {
            // 1. Academic Years & Semesters
            AcademicYear yearPast = new AcademicYear();
            yearPast.setSchool(school);
            yearPast.setName("2024-2025");
            yearPast.setStartDate(LocalDate.of(2024, 9, 1));
            yearPast.setEndDate(LocalDate.of(2025, 6, 30));
            yearPast.setCurrentYear(false);
            academicYearRepository.save(yearPast);

            AcademicYear yearCurrent = new AcademicYear();
            yearCurrent.setSchool(school);
            yearCurrent.setName("2025-2026");
            yearCurrent.setStartDate(LocalDate.of(2025, 9, 1));
            yearCurrent.setEndDate(LocalDate.of(2026, 6, 30));
            yearCurrent.setCurrentYear(true);
            yearCurrent = academicYearRepository.save(yearCurrent);

            Semester sem1 = new Semester();
            sem1.setSchool(school);
            sem1.setAcademicYear(yearCurrent);
            sem1.setSemesterName(SemesterName.FIRST);
            sem1.setStartDate(LocalDate.of(2025, 9, 1));
            sem1.setEndDate(LocalDate.of(2026, 1, 31));
            sem1 = semesterRepository.save(sem1);

            Semester sem2 = new Semester();
            sem2.setSchool(school);
            sem2.setAcademicYear(yearCurrent);
            sem2.setSemesterName(SemesterName.SECOND);
            sem2.setStartDate(LocalDate.of(2026, 2, 1));
            sem2.setEndDate(LocalDate.of(2026, 6, 30));
            semesterRepository.save(sem2);

            // 2. School Staff
            Principal principal = createPrincipal(school, "1000001", "محمد", "الخطيب", "0991111111", "admin", "principal");
            Secretary secretary = createSecretary(school, "1000002", "سارة", "الأحمد", "0992222222", "secretary");
            Librarian librarian = createLibrarian(school, "1000003", "عمر", "خالد", "0993333333", "librarian");

            // 3. Teachers (12 Teachers)
            Map<String, Teacher> teachers = new HashMap<>();
            teachers.put("math1", createTeacher(school, "2000001", "محمد", "صالح", "الرياضيات العامة والجبر", "teacher1", "mohammad"));
            teachers.put("arabic", createTeacher(school, "2000002", "لينا", "أحمد", "اللغة العربية وآدابها", "teacher2", "lina"));
            teachers.put("physics", createTeacher(school, "2000003", "خالد", "عمر", "الفيزياء والميكانيك", "teacher3", "khaled"));
            teachers.put("english", createTeacher(school, "2000004", "نور", "علي", "اللغة الإنجليزية والأدب", "teacher4", "nour"));
            teachers.put("chemistry", createTeacher(school, "2000005", "باسل", "القاسم", "الكيمياء العامة والعضوية", "teacher5", "basil"));
            teachers.put("history", createTeacher(school, "2000006", "هدى", "منصور", "التاريخ والدراسات الاجتماعية", "teacher6", "huda"));
            teachers.put("biology", createTeacher(school, "2000007", "سامر", "النجار", "علم الأحياء والعلوم الطبيعية", "teacher7", "samer"));
            teachers.put("it", createTeacher(school, "2000008", "ريم", "الحسين", "تقنية المعلومات والبرمجة", "teacher8", "reem"));
            teachers.put("french", createTeacher(school, "2000009", "نادين", "حداد", "اللغة الفرنسية", "teacher9", "nadine"));
            teachers.put("math2", createTeacher(school, "2000010", "أنس", "البيطار", "الرياضيات المتقدمة والتفاضل", "teacher10", "anas"));
            teachers.put("geography", createTeacher(school, "2000011", "فادي", "سليمان", "الجغرافيا والبيئة", "teacher11", "fadi"));
            teachers.put("islamic", createTeacher(school, "2000012", "عبد الرحمن", "الشامي", "التربية الإسلامية والفكر", "teacher12", "abdul"));

            // 4. Classes (7 Classes)
            Map<String, SchoolClass> classes = new HashMap<>();
            classes.put("9A", createClass(school, GradeLevel.GRADE_9, "A", "مبنى ب - قاعة 101", 30));
            classes.put("10A", createClass(school, GradeLevel.GRADE_10, "A", "مبنى أ - قاعة 201", 30));
            classes.put("10B", createClass(school, GradeLevel.GRADE_10, "B", "مبنى أ - قاعة 202", 30));
            classes.put("11A", createClass(school, GradeLevel.GRADE_11, "A", "مبنى أ - قاعة 301", 30));
            classes.put("11B", createClass(school, GradeLevel.GRADE_11, "B", "مبنى أ - قاعة 302", 30));
            classes.put("12A", createClass(school, GradeLevel.GRADE_12, "A", "مبنى المتفوقين - قاعة 401", 25));
            classes.put("12B", createClass(school, GradeLevel.GRADE_12, "B", "مبنى المتفوقين - قاعة 402", 25));

            // 5. Subjects (18 Subjects)
            Map<String, Subject> subs = new HashMap<>();
            subs.put("math10", createSubject(school, "الرياضيات 10", GradeLevel.GRADE_10, SemesterName.FIRST));
            subs.put("phys10", createSubject(school, "الفيزياء 10", GradeLevel.GRADE_10, SemesterName.FIRST));
            subs.put("chem10", createSubject(school, "الكيمياء 10", GradeLevel.GRADE_10, SemesterName.FIRST));
            subs.put("bio10", createSubject(school, "علم الأحياء 10", GradeLevel.GRADE_10, SemesterName.FIRST));
            subs.put("arab10", createSubject(school, "اللغة العربية 10", GradeLevel.GRADE_10, SemesterName.FIRST));
            subs.put("eng10", createSubject(school, "اللغة الإنجليزية 10", GradeLevel.GRADE_10, SemesterName.FIRST));
            subs.put("hist10", createSubject(school, "التاريخ الحديث 10", GradeLevel.GRADE_10, SemesterName.FIRST));
            subs.put("cs10", createSubject(school, "المعلوماتية والبرمجة 10", GradeLevel.GRADE_10, SemesterName.FIRST));

            subs.put("math11", createSubject(school, "الرياضيات التحليلية 11", GradeLevel.GRADE_11, SemesterName.FIRST));
            subs.put("phys11", createSubject(school, "الفيزياء المتقدمة 11", GradeLevel.GRADE_11, SemesterName.FIRST));
            subs.put("chem11", createSubject(school, "الكيمياء الحركية 11", GradeLevel.GRADE_11, SemesterName.FIRST));
            subs.put("arab11", createSubject(school, "الأدب العربي 11", GradeLevel.GRADE_11, SemesterName.FIRST));
            subs.put("eng11", createSubject(school, "اللغة الإنجليزية التخصصية 11", GradeLevel.GRADE_11, SemesterName.FIRST));

            subs.put("math12", createSubject(school, "التحليل والرياضيات البحتة 12", GradeLevel.GRADE_12, SemesterName.FIRST));
            subs.put("phys12", createSubject(school, "الفيزياء النووية والكهربائية 12", GradeLevel.GRADE_12, SemesterName.FIRST));
            subs.put("chem12", createSubject(school, "الكيمياء العضوية 12", GradeLevel.GRADE_12, SemesterName.FIRST));
            subs.put("arab12", createSubject(school, "اللغة العربية والبلاغة 12", GradeLevel.GRADE_12, SemesterName.FIRST));
            subs.put("eng12", createSubject(school, "اللغة الإنجليزية للدراسات الأكاديمية 12", GradeLevel.GRADE_12, SemesterName.FIRST));

            // 6. Teacher-Subject Assignments
            assignTeacherSubject(school, teachers.get("math1"), subs.get("math10"));
            assignTeacherSubject(school, teachers.get("math2"), subs.get("math11"));
            assignTeacherSubject(school, teachers.get("math2"), subs.get("math12"));
            assignTeacherSubject(school, teachers.get("physics"), subs.get("phys10"));
            assignTeacherSubject(school, teachers.get("physics"), subs.get("phys11"));
            assignTeacherSubject(school, teachers.get("physics"), subs.get("phys12"));
            assignTeacherSubject(school, teachers.get("chemistry"), subs.get("chem10"));
            assignTeacherSubject(school, teachers.get("chemistry"), subs.get("chem11"));
            assignTeacherSubject(school, teachers.get("chemistry"), subs.get("chem12"));
            assignTeacherSubject(school, teachers.get("arabic"), subs.get("arab10"));
            assignTeacherSubject(school, teachers.get("arabic"), subs.get("arab11"));
            assignTeacherSubject(school, teachers.get("arabic"), subs.get("arab12"));
            assignTeacherSubject(school, teachers.get("english"), subs.get("eng10"));
            assignTeacherSubject(school, teachers.get("english"), subs.get("eng11"));
            assignTeacherSubject(school, teachers.get("english"), subs.get("eng12"));
            assignTeacherSubject(school, teachers.get("biology"), subs.get("bio10"));
            assignTeacherSubject(school, teachers.get("history"), subs.get("hist10"));
            assignTeacherSubject(school, teachers.get("it"), subs.get("cs10"));

            // 7. Full Weekly Schedules (10-A, 10-B, 11-A, 12-A)
            List<ClassSchedule> sched10A = seedClassSchedule(school, classes.get("10A"), teachers, subs);
            seedClassSchedule(school, classes.get("10B"), teachers, subs);
            seedClassSchedule(school, classes.get("11A"), teachers, subs);
            seedClassSchedule(school, classes.get("12A"), teachers, subs);

            // 8. Students (20 Students)
            List<Student> students = new ArrayList<>();
            // 10-A
            students.add(createStudent(school, classes.get("10A"), "ST1001", "أحمد", "علي", Gender.MALE, "student1", "st1001"));
            students.add(createStudent(school, classes.get("10A"), "ST1002", "محمد", "حسن", Gender.MALE, "student2", "st1002"));
            students.add(createStudent(school, classes.get("10A"), "ST1003", "سارة", "عمر", Gender.FEMALE, "student3", "st1003"));
            students.add(createStudent(school, classes.get("10A"), "ST1004", "نور", "الهدى", Gender.FEMALE, "student4", "st1004"));
            // 10-B
            students.add(createStudent(school, classes.get("10B"), "ST1005", "لينا", "خالد", Gender.FEMALE, "student5", "st1005"));
            students.add(createStudent(school, classes.get("10B"), "ST1006", "يوسف", "محمود", Gender.MALE, "student6", "st1006"));
            students.add(createStudent(school, classes.get("10B"), "ST1007", "كريم", "سامي", Gender.MALE, "student7", "st1007"));
            students.add(createStudent(school, classes.get("10B"), "ST1008", "مريم", "إسماعيل", Gender.FEMALE, "student8", "st1008"));
            // 11-A
            students.add(createStudent(school, classes.get("11A"), "ST1101", "زيد", "المصري", Gender.MALE, "student9", "st1101"));
            students.add(createStudent(school, classes.get("11A"), "ST1102", "مايا", "الحلبي", Gender.FEMALE, "student10", "st1102"));
            students.add(createStudent(school, classes.get("11A"), "ST1103", "عمر", "العطار", Gender.MALE, "student11", "st1103"));
            students.add(createStudent(school, classes.get("11A"), "ST1104", "رنيم", "القصار", Gender.FEMALE, "student12", "st1104"));
            // 11-B
            students.add(createStudent(school, classes.get("11B"), "ST1105", "فراس", "الطباع", Gender.MALE, "student13", "st1105"));
            students.add(createStudent(school, classes.get("11B"), "ST1106", "جود", "مراد", Gender.FEMALE, "student14", "st1106"));
            // 12-A (Baccalaureate)
            students.add(createStudent(school, classes.get("12A"), "ST1201", "طارق", "الخطيب", Gender.MALE, "student15", "st1201"));
            students.add(createStudent(school, classes.get("12A"), "ST1202", "سلمى", "النجار", Gender.FEMALE, "student16", "st1202"));
            students.add(createStudent(school, classes.get("12A"), "ST1203", "حمزة", "الدقر", Gender.MALE, "student17", "st1203"));
            students.add(createStudent(school, classes.get("12A"), "ST1204", "شهد", "الجزائري", Gender.FEMALE, "student18", "st1204"));
            // 12-B
            students.add(createStudent(school, classes.get("12B"), "ST1205", "بشار", "عثمان", Gender.MALE, "student19", "st1205"));
            students.add(createStudent(school, classes.get("12B"), "ST1206", "هلا", "شمس الدين", Gender.FEMALE, "student20", "st1206"));

            // 9. Guardians (8 Guardians linked to students)
            Guardian g1 = createGuardian(school, "900001", "علي", "أحمد", "مهندس مدني استشاري", "guardian1", "parent1");
            linkGuardian(school, students.get(0), g1, true); // أحمد علي
            linkGuardian(school, students.get(8), g1, true); // زيد المصري

            Guardian g2 = createGuardian(school, "900002", "منى", "إبراهيم", "طبيبة أطفال واستشارية", "guardian2", "parent2");
            linkGuardian(school, students.get(2), g2, true); // سارة عمر
            linkGuardian(school, students.get(11), g2, true); // رنيم القصار

            Guardian g3 = createGuardian(school, "900003", "خالد", "المنصور", "مستشار قانوني ومحامٍ", "guardian3", "parent3");
            linkGuardian(school, students.get(4), g3, true); // لينا خالد

            Guardian g4 = createGuardian(school, "900004", "محمود", "القدسي", "رجل أعمال ومدير شركات", "guardian4", "parent4");
            linkGuardian(school, students.get(5), g4, true); // يوسف محمود

            Guardian g5 = createGuardian(school, "900005", "سامي", "الخطيب", "أستاذ جامعي في الهندسة", "guardian5", "parent5");
            linkGuardian(school, students.get(14), g5, true); // طارق الخطيب

            Guardian g6 = createGuardian(school, "900006", "هشام", "الحلبي", "صيدلاني وخبير أدوية", "guardian6", "parent6");
            linkGuardian(school, students.get(9), g6, true); // مايا الحلبي

            Guardian g7 = createGuardian(school, "900007", "كمال", "النجار", "خبير برمجيات وأنظمة ذكاء", "guardian7", "parent7");
            linkGuardian(school, students.get(15), g7, true); // سلمى النجار

            Guardian g8 = createGuardian(school, "900008", "عصام", "الطباع", "مهندس عمارة وتخطيط", "guardian8", "parent8");
            linkGuardian(school, students.get(12), g8, true); // فراس الطباع

            // 10. Multi-day Attendance Records (100+ records)
            seedDetailedAttendance(school, students);

            // 11. Continuous Assessments & Results
            seedAssessments(school, sched10A, sem1, teachers, students);

            // 12. Official Exams & Results
            seedExams(school, classes, subs, sem1, students);

            // 13. Warnings & Disciplinary
            seedWarnings(school, students);

            // 14. Financial System (Fees, Discounts, Student Discounts, Payments)
            seedFinancials(school, yearCurrent, classes, students);

            // 15. Library Catalog & Borrow Records
            seedLibraryData(school, students);

            // 16. Announcements
            seedAnnouncementsData(school);

        } finally {
            TenantContext.clear();
        }
    }

    private void seedSecondarySchoolData(School school, String prefix) {
        TenantContext.set(school.getId(), school.getCode());

        try {
            AcademicYear year = new AcademicYear();
            year.setSchool(school);
            year.setName("2025-2026");
            year.setStartDate(LocalDate.of(2025, 9, 1));
            year.setEndDate(LocalDate.of(2026, 6, 30));
            year.setCurrentYear(true);
            year = academicYearRepository.save(year);

            Semester sem = new Semester();
            sem.setSchool(school);
            sem.setAcademicYear(year);
            sem.setSemesterName(SemesterName.FIRST);
            sem.setStartDate(LocalDate.of(2025, 9, 1));
            sem.setEndDate(LocalDate.of(2026, 1, 31));
            semesterRepository.save(sem);

            createPrincipal(school, prefix + "_101", "أحمد", "العلي", "0994444444", prefix + "_admin", prefix + "_principal");
            createSecretary(school, prefix + "_102", "سناء", "مراد", "0995555555", prefix + "_sec");

            SchoolClass c1 = createClass(school, GradeLevel.GRADE_10, "A", "القاعة 101", 30);
            SchoolClass c2 = createClass(school, GradeLevel.GRADE_11, "A", "القاعة 201", 30);

            Subject sub1 = createSubject(school, "الرياضيات العامة", GradeLevel.GRADE_10, SemesterName.FIRST);
            Subject sub2 = createSubject(school, "الفيزياء المتقدمة", GradeLevel.GRADE_11, SemesterName.FIRST);

            Teacher t1 = createTeacher(school, prefix + "_201", "ماهر", "اليوسف", "الرياضيات", prefix + "_t1", null);
            Teacher t2 = createTeacher(school, prefix + "_202", "سعاد", "الخيمي", "الفيزياء", prefix + "_t2", null);

            assignTeacherSubject(school, t1, sub1);
            assignTeacherSubject(school, t2, sub2);

            Student st1 = createStudent(school, c1, prefix.toUpperCase() + "_101", "وسيم", "الدين", Gender.MALE, prefix + "_st1", null);
            Student st2 = createStudent(school, c2, prefix.toUpperCase() + "_201", "ديما", "الشعار", Gender.FEMALE, prefix + "_st2", null);

            Guardian g = createGuardian(school, prefix + "_901", "مأمون", "الدين", "تاجر", prefix + "_g1", null);
            linkGuardian(school, st1, g, true);

        } finally {
            TenantContext.clear();
        }
    }

    // ==========================================
    // Helper Creation Methods
    // ==========================================
    private Principal createPrincipal(School school, String nationalId, String first, String last, String phone, String email1, String email2) {
        Principal p = new Principal();
        p.setSchool(school);
        p.setNationalId(nationalId);
        p.setFirstName(first);
        p.setLastName(last);
        p.setPhone(phone);
        p.setAddress("دمشق");
        p.setHireDate(LocalDate.of(2018, 8, 1));
        p = principalRepository.save(p);

        createAuth(school, email1, "123456", Role.PRINCIPAL, p.getId());
        if (email2 != null && !email2.equals(email1)) {
            createAuth(school, email2, "123456", Role.PRINCIPAL, p.getId());
        }
        return p;
    }

    private Secretary createSecretary(School school, String nationalId, String first, String last, String phone, String email) {
        Secretary s = new Secretary();
        s.setSchool(school);
        s.setNationalId(nationalId);
        s.setFirstName(first);
        s.setLastName(last);
        s.setPhone(phone);
        s.setAddress("دمشق");
        s.setHireDate(LocalDate.of(2020, 9, 1));
        s = secretaryRepository.save(s);

        createAuth(school, email, "123456", Role.SECRETARY, s.getId());
        return s;
    }

    private Librarian createLibrarian(School school, String nationalId, String first, String last, String phone, String email) {
        Librarian l = new Librarian();
        l.setSchool(school);
        l.setNationalId(nationalId);
        l.setFirstName(first);
        l.setLastName(last);
        l.setPhone(phone);
        l.setAddress("دمشق");
        l.setHireDate(LocalDate.of(2021, 9, 1));
        l = librarianRepository.save(l);

        createAuth(school, email, "123456", Role.LIBRARIAN, l.getId());
        return l;
    }

    private Teacher createTeacher(School school, String nationalId, String first, String last, String spec, String email1, String email2) {
        Teacher t = new Teacher();
        t.setSchool(school);
        t.setNationalId(nationalId);
        t.setFirstName(first);
        t.setLastName(last);
        t.setPhone("099" + nationalId);
        t.setAddress("دمشق");
        t.setSpecialization(spec);
        t.setHireDate(LocalDate.of(2021, 9, 1));
        t = teacherRepository.save(t);

        createAuth(school, email1, "123456", Role.TEACHER, t.getId());
        if (email2 != null && !email2.equals(email1)) {
            createAuth(school, email2, "123456", Role.TEACHER, t.getId());
        }
        return t;
    }

    private SchoolClass createClass(School school, GradeLevel level, String section, String location, int capacity) {
        SchoolClass sc = new SchoolClass();
        sc.setSchool(school);
        sc.setGradeLevel(level);
        sc.setSection(section);
        sc.setLocation(location);
        sc.setCapacity(capacity);
        return schoolClassRepository.save(sc);
    }

    private Subject createSubject(School school, String name, GradeLevel level, SemesterName semester) {
        Subject s = new Subject();
        s.setSchool(school);
        s.setName(name);
        s.setGradeLevel(level);
        s.setSemesterName(semester);
        return subjectRepository.save(s);
    }

    private void assignTeacherSubject(School school, Teacher teacher, Subject subject) {
        TeacherSubject ts = new TeacherSubject();
        ts.setSchool(school);
        ts.setTeacher(teacher);
        ts.setSubject(subject);
        teacherSubjectRepository.save(ts);
    }

    private List<ClassSchedule> seedClassSchedule(School school, SchoolClass sc, Map<String, Teacher> teachers, Map<String, Subject> subs) {
        List<ClassSchedule> list = new ArrayList<>();
        list.add(createSchedule(school, sc, DayOfWeek.SUNDAY, PeriodNumber.PERIOD_1, teachers.get("math1"), subs.get("math10")));
        list.add(createSchedule(school, sc, DayOfWeek.SUNDAY, PeriodNumber.PERIOD_2, teachers.get("physics"), subs.get("phys10")));
        list.add(createSchedule(school, sc, DayOfWeek.SUNDAY, PeriodNumber.PERIOD_3, teachers.get("arabic"), subs.get("arab10")));
        list.add(createSchedule(school, sc, DayOfWeek.MONDAY, PeriodNumber.PERIOD_1, teachers.get("chemistry"), subs.get("chem10")));
        list.add(createSchedule(school, sc, DayOfWeek.MONDAY, PeriodNumber.PERIOD_2, teachers.get("english"), subs.get("eng10")));
        list.add(createSchedule(school, sc, DayOfWeek.TUESDAY, PeriodNumber.PERIOD_1, teachers.get("biology"), subs.get("bio10")));
        list.add(createSchedule(school, sc, DayOfWeek.TUESDAY, PeriodNumber.PERIOD_2, teachers.get("history"), subs.get("hist10")));
        list.add(createSchedule(school, sc, DayOfWeek.WEDNESDAY, PeriodNumber.PERIOD_1, teachers.get("it"), subs.get("cs10")));
        list.add(createSchedule(school, sc, DayOfWeek.WEDNESDAY, PeriodNumber.PERIOD_2, teachers.get("physics"), subs.get("phys10")));
        list.add(createSchedule(school, sc, DayOfWeek.THURSDAY, PeriodNumber.PERIOD_1, teachers.get("arabic"), subs.get("arab10")));
        list.add(createSchedule(school, sc, DayOfWeek.THURSDAY, PeriodNumber.PERIOD_2, teachers.get("math1"), subs.get("math10")));
        return list;
    }

    private ClassSchedule createSchedule(School school, SchoolClass sc, DayOfWeek day, PeriodNumber period, Teacher teacher, Subject subject) {
        ClassSchedule cs = new ClassSchedule();
        cs.setSchool(school);
        cs.setSchoolClass(sc);
        cs.setDayOfWeek(day);
        cs.setPeriodNumber(period);
        cs.setTeacher(teacher);
        cs.setSubject(subject);
        return classScheduleRepository.save(cs);
    }

    private Student createStudent(School school, SchoolClass sc, String reg, String first, String last, Gender gender, String email1, String email2) {
        Student st = new Student();
        st.setSchool(school);
        st.setStudentSchoolClass(sc);
        st.setRegistrationNumber(reg);
        st.setFirstName(first);
        st.setLastName(last);
        st.setGender(gender);
        st.setGradeLevel(sc.getGradeLevel());
        st.setDateOfBirth(LocalDate.of(2008, 5, 20));
        st.setAddress("دمشق");
        st.setPhone("099" + reg);
        st.setEnrollmentDate(LocalDate.of(2025, 9, 1));
        st = studentRepository.save(st);

        createAuth(school, email1, "123456", Role.STUDENT, st.getId());
        if (email2 != null && !email2.equals(email1)) {
            createAuth(school, email2, "123456", Role.STUDENT, st.getId());
        }
        return st;
    }

    private Guardian createGuardian(School school, String nationalId, String first, String last, String occupation, String email1, String email2) {
        Guardian g = new Guardian();
        g.setSchool(school);
        g.setNationalId(nationalId);
        g.setFirstName(first);
        g.setLastName(last);
        g.setPhone("098" + nationalId);
        g.setAddress("دمشق");
        g.setOccupation(occupation);
        g = guardianRepository.save(g);

        createAuth(school, email1, "123456", Role.GUARDIAN, g.getId());
        if (email2 != null && !email2.equals(email1)) {
            createAuth(school, email2, "123456", Role.GUARDIAN, g.getId());
        }
        return g;
    }

    private void linkGuardian(School school, Student student, Guardian guardian, boolean primary) {
        StudentGuardian sg = new StudentGuardian();
        sg.setSchool(school);
        sg.setStudent(student);
        sg.setGuardian(guardian);
        sg.setPrimaryGuardian(primary);
        studentGuardianRepository.save(sg);
    }

    private void seedDetailedAttendance(School school, List<Student> students) {
        LocalDate today = LocalDate.now();
        for (int day = 0; day < 10; day++) {
            LocalDate date = today.minusDays(day);
            for (int sIdx = 0; sIdx < students.size(); sIdx++) {
                Student student = students.get(sIdx);
                Attendance a = new Attendance();
                a.setSchool(school);
                a.setStudent(student);
                a.setAttendanceDate(date);

                if (day == 1 && sIdx == 1) {
                    a.setAttendanceStatus(AttendanceStatus.ABSENT);
                } else if (day == 2 && sIdx == 5) {
                    a.setAttendanceStatus(AttendanceStatus.LATE);
                } else if (day == 4 && sIdx == 7) {
                    a.setAttendanceStatus(AttendanceStatus.EXCUSED);
                } else if (day == 6 && sIdx == 3) {
                    a.setAttendanceStatus(AttendanceStatus.ABSENT);
                } else {
                    a.setAttendanceStatus(AttendanceStatus.PRESENT);
                }
                attendanceRepository.save(a);
            }
        }
    }

    private void seedAssessments(School school, List<ClassSchedule> sched, Semester sem, Map<String, Teacher> teachers, List<Student> students) {
        if (sched == null || sched.isEmpty()) return;

        // 1. Math Quiz 1
        Assessment a1 = new Assessment();
        a1.setSchool(school);
        a1.setClassSchedule(sched.get(0));
        a1.setSemester(sem);
        a1.setTeacher(teachers.get("math1"));
        a1.setName("مذاكرة الجبر والدوال الرياضية الأولى");
        a1.setCategory(ContinuousCategory.QUIZ);
        a1.setMaxScore(20.0);
        a1.setWeight(10.0);
        a1.setAssessmentDate(LocalDate.now().minusDays(14));
        a1 = assessmentRepository.save(a1);

        for (int i = 0; i < 4; i++) {
            createAssessmentScore(school, students.get(i), a1, 17.5 + (i * 0.5));
        }

        // 2. Physics Lab Experiment
        Assessment a2 = new Assessment();
        a2.setSchool(school);
        a2.setClassSchedule(sched.get(1));
        a2.setSemester(sem);
        a2.setTeacher(teachers.get("physics"));
        a2.setName("تقرير التجربة المخبرية في الميكانيك");
        a2.setCategory(ContinuousCategory.PROJECT);
        a2.setMaxScore(25.0);
        a2.setWeight(15.0);
        a2.setAssessmentDate(LocalDate.now().minusDays(8));
        a2 = assessmentRepository.save(a2);

        for (int i = 0; i < 4; i++) {
            createAssessmentScore(school, students.get(i), a2, 22.0 + (i * 0.7));
        }

        // 3. Arabic Poetry Recitation
        Assessment a3 = new Assessment();
        a3.setSchool(school);
        a3.setClassSchedule(sched.get(2));
        a3.setSemester(sem);
        a3.setTeacher(teachers.get("arabic"));
        a3.setName("اختبار الإلقاء الشعري والقواعد النحوية");
        a3.setCategory(ContinuousCategory.ORAL_TEST);
        a3.setMaxScore(15.0);
        a3.setWeight(10.0);
        a3.setAssessmentDate(LocalDate.now().minusDays(4));
        a3 = assessmentRepository.save(a3);

        for (int i = 0; i < 4; i++) {
            createAssessmentScore(school, students.get(i), a3, 14.0 + (i * 0.2));
        }
    }

    private void createAssessmentScore(School school, Student student, Assessment assessment, Double score) {
        AssessmentResult ar = new AssessmentResult();
        ar.setSchool(school);
        ar.setStudent(student);
        ar.setAssessment(assessment);
        ar.setScore(Math.min(score, assessment.getMaxScore()));
        assessmentResultRepository.save(ar);
    }

    private void seedExams(School school, Map<String, SchoolClass> classes, Map<String, Subject> subs, Semester sem, List<Student> students) {
        // Midterm Exam: Mathematics
        Exam eMath = new Exam();
        eMath.setSchool(school);
        eMath.setSchoolClass(classes.get("10A"));
        eMath.setSubject(subs.get("math10"));
        eMath.setSemester(sem);
        eMath.setCategory(ExamCategory.MIDTERM);
        eMath.setMaxScore(100.0);
        eMath.setWeight(40.0);
        eMath.setExamDateTime(LocalDateTime.now().minusDays(18).withHour(9).withMinute(0));
        eMath.setDurationMinutes(90);
        eMath = examRepository.save(eMath);

        createExamScore(school, eMath, students.get(0), 96.5);
        createExamScore(school, eMath, students.get(1), 88.0);
        createExamScore(school, eMath, students.get(2), 94.0);
        createExamScore(school, eMath, students.get(3), 91.5);

        // Midterm Exam: Physics
        Exam ePhys = new Exam();
        ePhys.setSchool(school);
        ePhys.setSchoolClass(classes.get("10A"));
        ePhys.setSubject(subs.get("phys10"));
        ePhys.setSemester(sem);
        ePhys.setCategory(ExamCategory.MIDTERM);
        ePhys.setMaxScore(100.0);
        ePhys.setWeight(40.0);
        ePhys.setExamDateTime(LocalDateTime.now().minusDays(15).withHour(10).withMinute(30));
        ePhys.setDurationMinutes(90);
        ePhys = examRepository.save(ePhys);

        createExamScore(school, ePhys, students.get(0), 93.0);
        createExamScore(school, ePhys, students.get(1), 82.5);
        createExamScore(school, ePhys, students.get(2), 98.0);
        createExamScore(school, ePhys, students.get(3), 89.0);

        // Midterm Exam: Arabic
        Exam eArab = new Exam();
        eArab.setSchool(school);
        eArab.setSchoolClass(classes.get("10A"));
        eArab.setSubject(subs.get("arab10"));
        eArab.setSemester(sem);
        eArab.setCategory(ExamCategory.MIDTERM);
        eArab.setMaxScore(100.0);
        eArab.setWeight(40.0);
        eArab.setExamDateTime(LocalDateTime.now().minusDays(12).withHour(9).withMinute(0));
        eArab.setDurationMinutes(90);
        eArab = examRepository.save(eArab);

        createExamScore(school, eArab, students.get(0), 90.0);
        createExamScore(school, eArab, students.get(1), 85.0);
        createExamScore(school, eArab, students.get(2), 97.0);
        createExamScore(school, eArab, students.get(3), 92.0);
    }

    private void createExamScore(School school, Exam exam, Student student, Double score) {
        ExamResult er = new ExamResult();
        er.setSchool(school);
        er.setExam(exam);
        er.setStudent(student);
        er.setScore(score);
        examResultRepository.save(er);
    }

    private void seedWarnings(School school, List<Student> students) {
        Warning w1 = new Warning();
        w1.setSchool(school);
        w1.setStudent(students.get(1)); // محمد حسن
        w1.setWarningDate(LocalDate.now().minusDays(10));
        w1.setReason("تكرار التأخر الصباحي غير المبرر عن طابور الصباح والحصة الأولى.");
        warningRepository.save(w1);

        Warning w2 = new Warning();
        w2.setSchool(school);
        w2.setStudent(students.get(5)); // يوسف محمود
        w2.setWarningDate(LocalDate.now().minusDays(5));
        w2.setReason("عدم تسليم مشروع المختبر العلمي للفيزياء في الموعد المحدد.");
        warningRepository.save(w2);

        Warning w3 = new Warning();
        w3.setSchool(school);
        w3.setStudent(students.get(12)); // فراس الطباع
        w3.setWarningDate(LocalDate.now().minusDays(2));
        w3.setReason("استخدام الهاتف المحمول داخل الحصة الدراسية بدون إذن المعلم.");
        warningRepository.save(w3);
    }

    private void seedFinancials(School school, AcademicYear year, Map<String, SchoolClass> classes, List<Student> students) {
        // Fee Types
        FeeType ftTuition = new FeeType();
        ftTuition.setName("القسط الدراسي السنوي الأساسي");
        ftTuition = feeTypeRepository.save(ftTuition);

        FeeType ftBus = new FeeType();
        ftBus.setName("اشتراك خدمة النقل المدرسي (الباص)");
        ftBus = feeTypeRepository.save(ftBus);

        FeeType ftLab = new FeeType();
        ftLab.setName("رسوم المختبرات العلمية والحاسوب");
        ftLab = feeTypeRepository.save(ftLab);

        // Class Fees
        createClassFee(classes.get("10A"), year, ftTuition, 3500.0);
        createClassFee(classes.get("10A"), year, ftLab, 350.0);
        createClassFee(classes.get("11A"), year, ftTuition, 4000.0);
        createClassFee(classes.get("12A"), year, ftTuition, 4500.0);

        // Discounts
        Discount d1 = new Discount();
        d1.setName("منحة التفوق الدراسي والأكاديمي");
        d1.setPercentage(15.0);
        d1.setReason("الترتيب ضمن أوائل المدرسة في العام السابق");
        d1 = discountRepository.save(d1);

        Discount d2 = new Discount();
        d2.setName("حسم الإخوة والعائلة");
        d2.setPercentage(10.0);
        d2.setReason("تسجيل أكثر من طالب من نفس الأسرة");
        d2 = discountRepository.save(d2);

        // Assign Discounts
        StudentDiscount sd1 = new StudentDiscount();
        sd1.setStudent(students.get(0)); // أحمد علي
        sd1.setDiscount(d1);
        studentDiscountRepository.save(sd1);

        StudentDiscount sd2 = new StudentDiscount();
        sd2.setStudent(students.get(8)); // زيد المصري
        sd2.setDiscount(d2);
        studentDiscountRepository.save(sd2);

        // Payments
        createPayment(students.get(0), 1750.0, LocalDate.of(2025, 9, 5), "الدفعة الأولى من القسط السنوي - سند قبض رقم 10421");
        createPayment(students.get(0), 1000.0, LocalDate.of(2025, 11, 10), "الدفعة الثانية من القسط السنوي - سند قبض رقم 10892");
        createPayment(students.get(1), 1900.0, LocalDate.of(2025, 9, 6), "القسط الأول مع رسوم المختبر - سند قبض رقم 10455");
        createPayment(students.get(2), 2000.0, LocalDate.of(2025, 9, 8), "تسديد كامل الفصل الأول - سند قبض رقم 10512");
        createPayment(students.get(4), 1800.0, LocalDate.of(2025, 9, 10), "دفعة تسجيل أولى - سند قبض رقم 10590");
        createPayment(students.get(8), 1600.0, LocalDate.of(2025, 9, 12), "القسط الأول مع حسم الإخوة - سند قبض رقم 10633");
        createPayment(students.get(14), 2250.0, LocalDate.of(2025, 9, 15), "دفعة البكالوريا الأولى - سند قبض رقم 10740");
    }

    private void createClassFee(SchoolClass sc, AcademicYear year, FeeType type, Double amount) {
        ClassFee cf = new ClassFee();
        cf.setSchoolClass(sc);
        cf.setAcademicYear(year);
        cf.setFeeType(type);
        cf.setAmount(amount);
        classFeeRepository.save(cf);
    }

    private void createPayment(Student student, Double amount, LocalDate date, String notes) {
        Payment p = new Payment();
        p.setStudent(student);
        p.setAmount(amount);
        p.setPaymentDate(date);
        p.setNotes(notes);
        paymentRepository.save(p);
    }

    private void seedLibraryData(School school, List<Student> students) {
        Library lib = new Library();
        lib.setSchool(school);
        lib = libraryRepository.save(lib);

        LibraryBook b1 = createBook(lib, "حساب التفاضل والتكامل المتقدم (Calculus)", "جيمس ستيوارت", "978-1285741550", "الرياضيات", "المرجع الشامل في التحليل والرياضيات الجامعية.");
        LibraryBook b2 = createBook(lib, "أساسيات الفيزياء العامة (Halliday & Resnick)", "ديفيد هاليداي", "978-1118230718", "الفيزياء", "الكتاب المرجعي الكلاسيكي في الميكانيك والكهرباء والضوء.");
        LibraryBook b3 = createBook(lib, "الكيمياء العضوية التركيبية", "روبرت موريسون", "978-0136436690", "الكيمياء", "مرجع أساسي في التفاعلات العضوية والمركبات الكيميائية.");
        LibraryBook b4 = createBook(lib, "مقدمة في الخوارزميات وهياكل البيانات", "توماس كورمن", "978-0262033848", "المعلوماتية", "أهم مرجع عالمي في تحليل الخوارزميات وهندسة البرمجيات.");
        LibraryBook b5 = createBook(lib, "ديوان المتنبي مع الشرح والتحليل", "أبو الطيب المتنبي", "978-9953880123", "الأدب العربي", "روائع الشعر العربي الكلاسيكي ودراسات بلاغية.");
        LibraryBook b6 = createBook(lib, "تاريخ الحضارات والنهضة الإنسانية", "ول ديورانت", "978-9953250412", "التاريخ", "موسوعة تاريخية شاملة لتطور الفكر البشري.");

        // Borrow Records
        Borrow br1 = new Borrow();
        br1.setStudent(students.get(0)); // أحمد علي
        br1.setBook(b1);
        br1.setBorrowDate(LocalDate.now().minusDays(14));
        br1.setDueDate(LocalDate.now().plusDays(14));
        br1.setStatus(BorrowStatus.BORROWED);
        borrowRepository.save(br1);

        Borrow br2 = new Borrow();
        br2.setStudent(students.get(2)); // سارة عمر
        br2.setBook(b5);
        br2.setBorrowDate(LocalDate.now().minusDays(20));
        br2.setDueDate(LocalDate.now().minusDays(6));
        br2.setReturnDate(LocalDate.now().minusDays(7));
        br2.setStatus(BorrowStatus.RETURNED);
        borrowRepository.save(br2);

        Borrow br3 = new Borrow();
        br3.setStudent(students.get(8)); // زيد المصري
        br3.setBook(b4);
        br3.setBorrowDate(LocalDate.now().minusDays(25));
        br3.setDueDate(LocalDate.now().minusDays(5));
        br3.setStatus(BorrowStatus.LATE);
        borrowRepository.save(br3);
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

    private void seedAnnouncementsData(School school) {
        createAnnouncement(
                school,
                "افتتاح العام الدراسي 2025-2026 والترحيب بالطلبة الجدد",
                "ترحب إدارة المدرسة بجميع أبنائنا الطلبة وأولياء الأمور الكرام في عام دراسي واعد بالتميز والنجاح. يرجى الالتزام بالزي المدرسي ومواعيد الحصص المقررة.",
                UserType.STUDENT,
                AnnouncementStatus.PUBLISHED,
                LocalDate.now().minusDays(25)
        );

        createAnnouncement(
                school,
                "صدور الجداول الرسمية لامتحانات منتصف الفصل الدراسي الأول",
                "تم اعتماد ونشر جدول امتحانات منتصف الفصل الأول لكافة المراحل والصفوف، ويمكن للطلبة وأولياء الأمور الاطلاع عليها من خلال التطبيق.",
                UserType.STUDENT,
                AnnouncementStatus.PUBLISHED,
                LocalDate.now().minusDays(16)
        );

        createAnnouncement(
                school,
                "دعوة لحضور الاجتماع الدوري الأول لأولياء الأمور",
                "يسر إدارة المدرسة دعوة السادة أولياء الأمور لحضور اللقاء التربوي الأول مع الكادر التدريسي لمناقشة المستوى الأكاديمي للطلبة يوم الخميس القادم الساعة 4:00 عصراً.",
                UserType.GUARDIAN,
                AnnouncementStatus.PUBLISHED,
                LocalDate.now().minusDays(9)
        );

        createAnnouncement(
                school,
                "فتح باب التسجيل في أولمبياد العلوم والروبوت والرياضيات",
                "نعلن عن بدء التسجيل في المسابقة السنوية للابتكار العلمي والبرمجة والروبوت لطلبة المرحلتين الإعدادية والثانوية. للمشاركة يرجى مراجعة معلم المعلوماتية.",
                UserType.STUDENT,
                AnnouncementStatus.PUBLISHED,
                LocalDate.now().minusDays(3)
        );
    }

    private void createAnnouncement(School school, String title, String content, UserType type, AnnouncementStatus status, LocalDate date) {
        Announcement a = new Announcement();
        a.setSchool(school);
        a.setTitle(title);
        a.setContent(content);
        a.setUserType(type);
        a.setStatus(status);
        a.setPublishDate(date);
        announcementRepository.save(a);
    }

    private void createAuth(School school, String email, String password, Role role, Long refId) {
        AuthUser u = new AuthUser();
        u.setSchool(school);
        u.setEmail(email);
        u.setPassword(passwordEncoder.encode(password));
        u.setRole(role);
        u.setRefId(refId);
        u.setEnabled(true);
        authUserRepository.save(u);
    }
}