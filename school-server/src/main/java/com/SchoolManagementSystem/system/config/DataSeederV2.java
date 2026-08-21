package com.SchoolManagementSystem.system.config;

import com.SchoolManagementSystem.system.entity.Auth.AuthUser;
import com.SchoolManagementSystem.system.entity.academic.*;
import com.SchoolManagementSystem.system.entity.communication.Announcement;
import com.SchoolManagementSystem.system.entity.communication.AnnouncementTarget;
import com.SchoolManagementSystem.system.entity.enumeration.*;
import com.SchoolManagementSystem.system.entity.school.AcademicYear;
import com.SchoolManagementSystem.system.entity.school.School;
import com.SchoolManagementSystem.system.entity.student.*;
import com.SchoolManagementSystem.system.entity.user.Guardian;
import com.SchoolManagementSystem.system.entity.user.Teacher;
import com.SchoolManagementSystem.system.entity.finance.*;

import com.SchoolManagementSystem.system.repository.communication.AnnouncementRepository;
import com.SchoolManagementSystem.system.repository.communication.AnnouncementTargetRepository;
import com.SchoolManagementSystem.system.repository.finance.*;
import com.SchoolManagementSystem.system.repository.academic.*;
import com.SchoolManagementSystem.system.repository.auth.AuthUserRepository;
import com.SchoolManagementSystem.system.repository.school.AcademicYearRepository;
import com.SchoolManagementSystem.system.repository.school.SchoolRepository;
import com.SchoolManagementSystem.system.repository.student.*;
import com.SchoolManagementSystem.system.repository.user.GuardianRepository;
import com.SchoolManagementSystem.system.repository.user.TeacherRepository;

import com.SchoolManagementSystem.system.service.school.AcademicYearService;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Component
@Profile("seed")
@RequiredArgsConstructor
public class DataSeederV2 implements CommandLineRunner {


    private final SchoolRepository schoolRepository;

    private final AcademicYearRepository academicYearRepository;
    private final AcademicYearService academicYearService;
    private final SemesterRepository semesterRepository;
    private final FeeStructureRepository feeStructureRepository;

    private final FeeRepository feeRepository;

    private final DiscountRepository discountRepository;

    private final FeePaymentRepository feePaymentRepository;
    private final SchoolClassRepository schoolClassRepository;

    private final SubjectRepository subjectRepository;

    private final TeacherRepository teacherRepository;

    private final TeacherSubjectRepository teacherSubjectRepository;

    private final ClassScheduleRepository classScheduleRepository;

    private final StudentRepository studentRepository;

    private final GuardianRepository guardianRepository;

    private final StudentGuardianRepository studentGuardianRepository;

    private final AuthUserRepository authUserRepository;

    private final EducationRecordRepository educationRecordRepository;

    private final SemesterResultRepository semesterResultRepository;
    private final AssessmentRepository assessmentRepository;

    private final ExamResultRepository examResultRepository;
    private final AssessmentResultRepository assessmentResultRepository;
    private final AnnouncementRepository announcementRepository;

    private final AnnouncementTargetRepository announcementTargetRepository;
    private final AttendanceRepository attendanceRepository;

    private final WarningRepository warningRepository;


    private final PasswordEncoder passwordEncoder;


    private School school;


    private final Map<String, AcademicYear> academicYears = new HashMap<>();

    private final Map<String, Semester> semesters = new HashMap<>();

    private final Map<String, SchoolClass> classes = new HashMap<>();

    private final Map<String, Subject> subjects = new HashMap<>();

    private final Map<Integer, Teacher> teachers = new HashMap<>();

    private final Map<Integer, Student> students = new HashMap<>();

    private final Map<Integer, Guardian> guardians = new HashMap<>();


    @Override
    @Transactional
    public void run(String... args) {


        System.out.println("====================================");
        System.out.println(" SERA SCHOOL SEED STARTED ");
        System.out.println("====================================");


        if (schoolRepository.findByCode("SERA").isPresent()) {

            System.out.println("SERA already exists");
            return;
        }


        createSchool();


        /*
         * مهم:
         * السنة الأكاديمية أولاً
         * ثم الفصول
         */
        createAcademicYears();
        createNextAcademicYearData();

        createClasses();


        createSubjects();


        createTeachers();


        assignTeacherSubjects();


        createSchedules();


        createStudents();


        createGuardians();


        createStudentGuardianRelations();


        createEducationRecords();


        /*
         * الفصل الحالي الثاني
         * نخزن نتائج الفصل الأول
         */
        createSemesterResults();
        createExamResults();
        createAssessmentResults();


        createAttendance();


        createWarnings();

        createFinanceData();
        createAnnouncements();
        System.out.println("====================================");
        System.out.println(" SERA SCHOOL SEED FINISHED ");
        System.out.println("====================================");

    }
    // =========================================================
// SCHOOL
// =========================================================

    private void createSchool() {


        School school = new School();


        school.setName(
                "Sera School"
        );


        school.setCode(
                "SERA"
        );


        school.setAddress(
                "Damascus"
        );


        school.setPhone(
                "0110000000"
        );


        school.setEnabled(
                true
        );


        school.setSchoolType(
                SchoolType.PUBLIC
        );


        school.setEducationStages(
                Set.of(
                        EducationStage.MIDDLE
                )
        );


        school.setSemesterName(
                SemesterName.SECOND
        );


        this.school =
                schoolRepository.save(
                        school
                );
    }


// =========================================================
// ACADEMIC YEARS + SEMESTERS
// =========================================================

    private void createAcademicYears() {


        AcademicYear oldYear =
                new AcademicYear();


        oldYear.setSchool(
                school
        );


        oldYear.setName(
                "2024-2025"
        );


        oldYear.setStartDate(
                LocalDate.of(
                        2024,
                        9,
                        1
                )
        );


        oldYear.setEndDate(
                LocalDate.of(
                        2025,
                        6,
                        30
                )
        );


        oldYear.setCurrentYear(
                false
        );


        academicYears.put(
                oldYear.getName(),
                academicYearRepository.save(oldYear)
        );


        AcademicYear current =
                new AcademicYear();


        current.setSchool(
                school
        );


        current.setName(
                "2025-2026"
        );


        current.setStartDate(
                LocalDate.of(
                        2025,
                        9,
                        1
                )
        );


        current.setEndDate(
                LocalDate.of(
                        2026,
                        6,
                        30
                )
        );


        current.setCurrentYear(
                true
        );


        AcademicYear saved =
                academicYearRepository.save(
                        current
                );


        academicYears.put(
                current.getName(),
                saved
        );


        Semester first =
                new Semester();


        first.setSchool(
                school
        );


        first.setAcademicYear(
                saved
        );


        first.setSemesterName(
                SemesterName.FIRST
        );


        first.setStartDate(
                LocalDate.of(
                        2025,
                        9,
                        1
                )
        );


        first.setEndDate(
                LocalDate.of(
                        2026,
                        1,
                        31
                )
        );


        Semester second =
                new Semester();


        second.setSchool(
                school
        );


        second.setAcademicYear(
                saved
        );


        second.setSemesterName(
                SemesterName.SECOND
        );


        second.setStartDate(
                LocalDate.of(
                        2026,
                        2,
                        1
                )
        );


        second.setEndDate(
                LocalDate.of(
                        2026,
                        6,
                        30
                )
        );


        first =
                semesterRepository.save(
                        first
                );


        second =
                semesterRepository.save(
                        second
                );


        semesters.put(
                "FIRST",
                first
        );


        semesters.put(
                "SECOND",
                second
        );
    }


// =========================================================
// CLASSES
// =========================================================

    private void createClasses() {


        createGradeClasses(
                GradeLevel.GRADE_7
        );


        createGradeClasses(
                GradeLevel.GRADE_8
        );


        createGradeClasses(
                GradeLevel.GRADE_9
        );

    }


    private void createGradeClasses(
            GradeLevel gradeLevel
    ) {


        String[] sections =
                {
                        "A",
                        "B",
                        "C",
                        "D"
                };


        for (String section : sections) {


            SchoolClass schoolClass =
                    new SchoolClass();


            schoolClass.setSchool(
                    school
            );


            schoolClass.setGradeLevel(
                    gradeLevel
            );


            schoolClass.setSection(
                    section
            );


            schoolClass.setLocation(
                    "Building "
                            + gradeLevel.getLevel()
                            +
                            section
            );


            schoolClass.setCapacity(
                    35
            );


            SchoolClass saved =
                    schoolClassRepository.save(
                            schoolClass
                    );


            classes.put(
                    gradeLevel.name()
                            + "_"
                            + section,
                    saved
            );
        }
    }
    // =========================================================
// SUBJECTS
// =========================================================

    private void createSubjects() {


        createSubject(
                "Arabic",
                "اللغة العربية",
                7
        );

        createSubject(
                "English",
                "اللغة الإنكليزية",
                7
        );

        createSubject(
                "Math",
                "الرياضيات",
                7
        );

        createSubject(
                "Physics",
                "الفيزياء",
                7
        );

        createSubject(
                "Chemistry",
                "الكيمياء",
                7
        );

        createSubject(
                "Biology",
                "الأحياء",
                7
        );


        createSubject(
                "Arabic",
                "اللغة العربية",
                8
        );

        createSubject(
                "English",
                "اللغة الإنكليزية",
                8
        );

        createSubject(
                "Math",
                "الرياضيات",
                8
        );

        createSubject(
                "Physics",
                "الفيزياء",
                8
        );

        createSubject(
                "Chemistry",
                "الكيمياء",
                8
        );

        createSubject(
                "Biology",
                "الأحياء",
                8
        );


        createSubject(
                "Arabic",
                "اللغة العربية",
                9
        );

        createSubject(
                "English",
                "اللغة الإنكليزية",
                9
        );

        createSubject(
                "Math",
                "الرياضيات",
                9
        );

        createSubject(
                "Physics",
                "الفيزياء",
                9
        );

        createSubject(
                "Chemistry",
                "الكيمياء",
                9
        );

        createSubject(
                "Biology",
                "الأحياء",
                9
        );
    }


    private void createSubject(
            String code,
            String name,
            int grade
    ) {


        Subject subject =
                new Subject();


        subject.setSchool(
                school
        );


        subject.setName(
                name
        );


        subject.setGradeLevel(
                GradeLevel.values()[grade + 5]
        );


        subject.setSemesterName(
                SemesterName.FIRST
        );


        Subject saved =
                subjectRepository.save(
                        subject
                );


        subjects.put(
                code + "_" + grade,
                saved
        );
    }


// =========================================================
// TEACHERS
// =========================================================

    private void createTeachers() {


        for (int i = 1; i <= 18; i++) {


            Teacher teacher =
                    new Teacher();


            teacher.setSchool(
                    school
            );


            teacher.setFirstName(
                    "Teacher" + i
            );


            teacher.setLastName(
                    "Sera"
            );


            teacher.setNationalId(
                    "T-NID-" + i
            );


            teacher.setPhone(
                    "09900000" + i
            );


            teacher.setAddress(
                    "Damascus"
            );


            teacher.setHireDate(
                    LocalDate.of(
                            2020,
                            9,
                            1
                    )
            );


            teacher.setSpecialization(
                    "Teacher specialization " + i
            );


            Teacher saved =
                    teacherRepository.save(
                            teacher
                    );


            teachers.put(
                    i,
                    saved
            );


            createTeacherAccount(
                    saved,
                    i
            );
        }
    }


// =========================================================
// TEACHER AUTH
// =========================================================

    private void createTeacherAccount(
            Teacher teacher,
            int number
    ) {


        AuthUser user =
                new AuthUser();


        user.setSchool(
                school
        );


        user.setEmail(
                "teacher"
                        + number
                        +
                        "@test.com"
        );


        user.setPassword(
                passwordEncoder.encode(
                        "1234"
                )
        );


        user.setRole(
                Role.TEACHER
        );


        user.setRefId(
                teacher.getId()
        );


        user.setEnabled(
                true
        );


        authUserRepository.save(
                user
        );
    }


// =========================================================
// TEACHER SUBJECTS
// =========================================================

    private void assignTeacherSubjects() {


        int teacherIndex = 1;


        for (Subject subject : subjects.values()) {


            TeacherSubject teacherSubject =
                    new TeacherSubject();


            teacherSubject.setSchool(
                    school
            );


            teacherSubject.setTeacher(
                    teachers.get(
                            teacherIndex
                    )
            );


            teacherSubject.setSubject(
                    subject
            );


            teacherSubjectRepository.save(
                    teacherSubject
            );


            teacherIndex++;


            if (teacherIndex > teachers.size()) {

                teacherIndex = 1;
            }
        }
    }
    // =========================================================
// CLASS SCHEDULE
// =========================================================

    private void createSchedules() {


        List<DayOfWeek> days =
                List.of(
                        DayOfWeek.SUNDAY,
                        DayOfWeek.MONDAY,
                        DayOfWeek.TUESDAY,
                        DayOfWeek.WEDNESDAY,
                        DayOfWeek.THURSDAY
                );


        Map<Long, Set<String>> teacherBusy =
                new HashMap<>();


        teachers.values()
                .forEach(t ->
                        teacherBusy.put(
                                t.getId(),
                                new HashSet<>()
                        )
                );


        int teacherIndex = 1;


        for (SchoolClass schoolClass : classes.values()) {


            List<Subject> classSubjects =
                    subjects.values()
                            .stream()
                            .filter(subject ->
                                    subject.getGradeLevel()
                                            ==
                                            schoolClass.getGradeLevel()
                            )
                            .toList();


            int subjectIndex = 0;


            for (DayOfWeek day : days) {


                for (int period = 1;
                     period <= 6;
                     period++) {


                    Subject subject =
                            classSubjects.get(
                                    subjectIndex
                                            %
                                            classSubjects.size()
                            );


                    Teacher teacher =
                            findAvailableTeacher(
                                    teacherBusy,
                                    day,
                                    period,
                                    teacherIndex
                            );


                    ClassSchedule schedule =
                            new ClassSchedule();


                    schedule.setSchool(
                            school
                    );


                    schedule.setSchoolClass(
                            schoolClass
                    );


                    schedule.setSubject(
                            subject
                    );


                    schedule.setTeacher(
                            teacher
                    );


                    schedule.setDayOfWeek(
                            day
                    );


                    schedule.setPeriodNumber(
                            PeriodNumber.values()
                                    [period - 1]
                    );


                    classScheduleRepository.save(
                            schedule
                    );


                    teacherBusy
                            .get(teacher.getId())
                            .add(
                                    day.name()
                                            +
                                            "-"
                                            +
                                            period
                            );


                    subjectIndex++;


                    teacherIndex++;


                    if (teacherIndex > teachers.size()) {

                        teacherIndex = 1;
                    }
                }
            }
        }
    }


// =========================================================
// FIND FREE TEACHER
// =========================================================

    private Teacher findAvailableTeacher(
            Map<Long, Set<String>> teacherBusy,
            DayOfWeek day,
            int period,
            int start
    ) {


        String key =
                day.name()
                        +
                        "-"
                        +
                        period;


        for (int i = 0;
             i < teachers.size();
             i++) {


            int index =
                    ((start + i - 1)
                            %
                            teachers.size())
                            + 1;


            Teacher teacher =
                    teachers.get(index);


            if (!teacherBusy
                    .get(teacher.getId())
                    .contains(key)) {


                return teacher;
            }
        }


        throw new RuntimeException(
                "No free teacher found"
        );
    }
    // =========================================================
// STUDENTS
// =========================================================

    private void createStudents() {


        int counter = 1;


        List<SchoolClass> classList =
                new ArrayList<>(
                        classes.values()
                );


        for (SchoolClass schoolClass : classList) {


            for (int i = 1; i <= 15; i++) {


                Student student =
                        new Student();


                student.setSchool(
                        school
                );


                student.setStudentSchoolClass(
                        schoolClass
                );


                student.setRegistrationNumber(
                        "SERA-"
                                +
                                counter
                );


                student.setFirstName(
                        "Student"
                                +
                                counter
                );


                student.setLastName(
                        "Sera"
                );


                student.setGender(
                        counter % 2 == 0
                                ?
                                Gender.MALE
                                :
                                Gender.FEMALE
                );


                student.setGradeLevel(
                        schoolClass
                                .getGradeLevel()
                );


                student.setDateOfBirth(
                        LocalDate.of(
                                2011,
                                1 + (counter % 12),
                                1 + (counter % 20)
                        )
                );


                student.setAddress(
                        "Damascus"
                );


                student.setPhone(
                        "098000"
                                +
                                counter
                );


                student.setEnrollmentDate(
                        LocalDate.of(
                                2025,
                                9,
                                1
                        )
                );


                Student saved =
                        studentRepository.save(
                                student
                        );


                students.put(
                        counter,
                        saved
                );


                createStudentAccount(
                        saved,
                        counter
                );


                counter++;

            }
        }
    }


// =========================================================
// STUDENT AUTH
// =========================================================

    private void createStudentAccount(
            Student student,
            int number
    ) {


        AuthUser user =
                new AuthUser();


        user.setSchool(
                school
        );


        user.setEmail(
                "student"
                        +
                        number
                        +
                        "@test.com"
        );


        user.setPassword(
                passwordEncoder.encode(
                        "1234"
                )
        );


        user.setRole(
                Role.STUDENT
        );


        user.setRefId(
                student.getId()
        );


        user.setEnabled(
                true
        );


        authUserRepository.save(
                user
        );
    }
    // =========================================================
// GUARDIANS
// =========================================================

    private void createGuardians() {


        int guardianCounter = 1;


        /*
         * أول 30 طالب:
         * كل 3 طلاب لهم نفس ولي
         * (إخوة)
         */

        for (int i = 1; i <= 30; i += 3) {


            Guardian guardian =
                    createGuardian(
                            guardianCounter++
                    );


            guardians.put(
                    guardianCounter - 1,
                    guardian
            );


            for (int j = 0; j < 3; j++) {


                Student student =
                        students.get(
                                i + j
                        );


                if (student != null) {

                    createStudentGuardian(
                            student,
                            guardian,
                            j == 0
                    );
                }
            }
        }



        /*
         * بقية الطلاب:
         * ولي خاص لكل طالب
         */


        for (int i = 31;
             i <= students.size();
             i++) {


            Guardian guardian =
                    createGuardian(
                            guardianCounter++
                    );


            guardians.put(
                    guardianCounter - 1,
                    guardian
            );


            createStudentGuardian(
                    students.get(i),
                    guardian,
                    true
            );
        }
    }


// =========================================================
// CREATE GUARDIAN
// =========================================================

    private Guardian createGuardian(
            int number
    ) {


        Guardian guardian =
                new Guardian();


        guardian.setSchool(
                school
        );


        guardian.setFirstName(
                "Guardian"
                        +
                        number
        );


        guardian.setLastName(
                "Sera"
        );


        guardian.setNationalId(
                "G-NID-"
                        +
                        number
        );


        guardian.setPhone(
                "097000"
                        +
                        number
        );


        guardian.setAddress(
                "Damascus"
        );


        guardian.setOccupation(
                "Parent"
        );


        Guardian saved =
                guardianRepository.save(
                        guardian
                );


        createGuardianAccount(
                saved,
                number
        );


        return saved;
    }


// =========================================================
// GUARDIAN AUTH
// =========================================================

    private void createGuardianAccount(
            Guardian guardian,
            int number
    ) {


        AuthUser user =
                new AuthUser();


        user.setSchool(
                school
        );


        user.setEmail(
                "guardian"
                        +
                        number
                        +
                        "@test.com"
        );


        user.setPassword(
                passwordEncoder.encode(
                        "1234"
                )
        );


        user.setRole(
                Role.GUARDIAN
        );


        user.setRefId(
                guardian.getId()
        );


        user.setEnabled(
                true
        );


        authUserRepository.save(
                user
        );
    }


// =========================================================
// STUDENT GUARDIAN RELATION
// =========================================================

    private void createStudentGuardian(
            Student student,
            Guardian guardian,
            boolean primary
    ) {


        StudentGuardian relation =
                new StudentGuardian();


        relation.setSchool(
                school
        );


        relation.setStudent(
                student
        );


        relation.setGuardian(
                guardian
        );


        relation.setPrimaryGuardian(
                primary
        );


        studentGuardianRepository.save(
                relation
        );
    }
    // =========================================================
// EDUCATION RECORDS
// =========================================================

    private void createEducationRecords() {


        AcademicYear currentYear =
                academicYears.get(
                        "2025-2026"
                );


        for (Student student : students.values()) {


            EducationRecord record =
                    new EducationRecord();


            record.setSchool(
                    school
            );


            record.setStudent(
                    student
            );


            record.setAcademicYear(
                    currentYear
            );


            record.setGradeLevel(
                    student.getGradeLevel()
            );


            /*
             * 80% نجاح
             * 20% رسوب
             */

            boolean passed =
                    student.getId() % 5 != 0;


            record.setPassed(
                    passed
            );


            if (passed) {


                record.setFinalAverage(
                        70.0
                                +
                                (student.getId() % 25)
                );


                record.setNotes(
                        "Passed"
                );


            } else {


                record.setFinalAverage(
                        35.0
                                +
                                (student.getId() % 20)
                );


                record.setNotes(
                        "Failed"
                );

            }


            long absence =
                    attendanceRepository
                            .countByStudentIdAndAttendanceStatus(
                                    student.getId(),
                                    AttendanceStatus.ABSENT
                            );


            record.setAbsenceDays(
                    (int) absence
            );


            record.setRegisteredNextYear(
                    false
            );


            educationRecordRepository.save(
                    record
            );
        }
    }

    // =========================================================
// SEMESTER RESULTS
// =========================================================

    private void createSemesterResults() {


        Semester firstSemester =
                semesters.get(
                        "FIRST"
                );


        for (Student student : students.values()) {


            List<Subject> studentSubjects =
                    subjects.values()
                            .stream()
                            .filter(subject ->
                                    subject.getGradeLevel()
                                            ==
                                            student.getGradeLevel()
                            )
                            .toList();


            for (Subject subject :
                    studentSubjects) {


                SemesterResult result =
                        new SemesterResult();


                result.setSchool(
                        school
                );


                result.setStudent(
                        student
                );


                result.setSemester(
                        firstSemester
                );


                result.setSubject(
                        subject
                );


                double continuous =
                        40
                                +
                                (student.getId() % 50);


                double exam =
                        40
                                +
                                (student.getId() % 50);


                result.setContinuousAverage(
                        continuous
                );


                result.setExamScore(
                        exam
                );


                result.setFinalScore(
                        continuous * 0.4
                                +
                                exam * 0.6
                );


                semesterResultRepository.save(
                        result
                );
            }
        }
    }
    // =========================================================
// ASSESSMENTS + ASSESSMENT RESULTS
// =========================================================

    private void createAssessmentResults() {


        Semester firstSemester =
                semesters.get(
                        "FIRST"
                );


        List<ContinuousCategory> categories =
                List.of(
                        ContinuousCategory.HOMEWORK,
                        ContinuousCategory.QUIZ,
                        ContinuousCategory.PARTICIPATION
                );


        for (SchoolClass schoolClass :
                classes.values()) {


            List<ClassSchedule> schedules =
                    classScheduleRepository
                            .findClassScheduleBySchoolClass_Id(
                                    schoolClass.getId()
                            );


            List<Student> classStudents =
                    studentRepository
                            .findByStudentSchoolClass_Id(
                                    schoolClass.getId()
                            );


            for (ClassSchedule schedule :
                    schedules) {


                if (schedule.getSubject() == null
                        ||
                        schedule.getTeacher() == null) {

                    continue;
                }


                for (ContinuousCategory category :
                        categories) {


                    Assessment assessment =
                            new Assessment();


                    assessment.setSchool(
                            school
                    );


                    assessment.setClassSchedule(
                            schedule
                    );


                    assessment.setSemester(
                            firstSemester
                    );


                    assessment.setTeacher(
                            schedule.getTeacher()
                    );


                    assessment.setCategory(
                            category
                    );


                    assessment.setAssessmentDate(
                            LocalDate.of(
                                    2025,
                                    11,
                                    10
                            )
                    );


                    Assessment saved =
                            assessmentRepository.save(
                                    assessment
                            );


                    for (Student student :
                            classStudents) {


                        AssessmentResult result =
                                new AssessmentResult();


                        result.setSchool(
                                school
                        );


                        result.setStudent(
                                student
                        );


                        result.setAssessment(
                                saved
                        );


                        double score;


                        if (student.getId() % 5 == 0) {


                            score =
                                    20
                                            +
                                            (student.getId() % 40);


                        } else {


                            score =
                                    60
                                            +
                                            (student.getId() % 40);

                        }


                        result.setScore(
                                score
                        );


                        assessmentResultRepository.save(
                                result
                        );
                    }
                }
            }
        }
    }
    // =========================================================
// FINANCE
// =========================================================

    private void createFinanceData() {


        Semester secondSemester =
                semesters.get(
                        "SECOND"
                );


        Map<GradeLevel, BigDecimal> fees =
                Map.of(
                        GradeLevel.GRADE_7,
                        new BigDecimal("500000"),

                        GradeLevel.GRADE_8,
                        new BigDecimal("600000"),

                        GradeLevel.GRADE_9,
                        new BigDecimal("700000")
                );


        Map<GradeLevel, FeeStructure> structures =
                new HashMap<>();


        /*
         * إنشاء هيكل الأقساط
         */

        for (Map.Entry<GradeLevel, BigDecimal> entry :
                fees.entrySet()) {


            FeeStructure structure =
                    new FeeStructure();


            structure.setSchool(
                    school
            );


            structure.setSemester(
                    secondSemester
            );


            structure.setGradeLevel(
                    entry.getKey()
            );


            structure.setFeeName(
                    "Annual Tuition "
                            +
                            entry.getKey().name()
            );


            structure.setFeeType(
                    FeeType.TUITION
            );


            structure.setAmount(
                    entry.getValue()
            );


            structure.setDueDate(
                    LocalDate.of(
                            2026,
                            3,
                            1
                    )
            );


            structure.setActive(
                    true
            );


            FeeStructure saved =
                    feeStructureRepository.save(
                            structure
                    );


            structures.put(
                    entry.getKey(),
                    saved
            );
        }



        /*
         * إنشاء أقساط الطلاب
         */


        for (Student student :
                students.values()) {


            FeeStructure structure =
                    structures.get(
                            student.getGradeLevel()
                    );


            Fee fee =
                    new Fee();


            fee.setSchool(
                    school
            );


            fee.setStudent(
                    student
            );


            fee.setFeeStructure(
                    structure
            );


            fee.setAmount(
                    structure.getAmount()
            );


            fee.setDueDate(
                    structure.getDueDate()
            );


            Fee savedFee =
                    feeRepository.save(
                            fee
                    );



            /*
             * خصم لأربعة طلاب
             */

            if (
                    student.getId() == 1
                            ||
                            student.getId() == 25
                            ||
                            student.getId() == 50
                            ||
                            student.getId() == 100
            ) {


                Discount discount =
                        new Discount();


                discount.setName(
                        "Special Discount"
                );


                discount.setDiscountType(
                        DiscountType.PERCENTAGE
                );


                discount.setValue(
                        new BigDecimal("20")
                );


                discount.setReason(
                        "Academic / Social discount"
                );


                discount.setFee(
                        savedFee
                );


                discount.setSchool(
                        school
                );


                discountRepository.save(
                        discount
                );


                savedFee.setDiscount(
                        discount
                );
            }



            /*
             * بعض الطلاب دفعوا
             */

            if (student.getId() % 3 == 0) {


                FeePayment payment =
                        new FeePayment();


                payment.setSchool(
                        school
                );


                payment.setFee(
                        savedFee
                );


                payment.setAmount(
                        savedFee.getAmount()
                                .divide(
                                        new BigDecimal("2"),
                                        2,
                                        RoundingMode.HALF_UP
                                )
                );


                payment.setPaymentDate(
                        LocalDate.of(
                                        2026,
                                        2,
                                        15
                                )
                                .atStartOfDay()
                );


                payment.setPaymentMethod(
                        PaymentMethod.CASH
                );


                payment.setReceiptNumber(
                        "REC-"
                                +
                                student.getId()
                );


                feePaymentRepository.save(
                        payment
                );
            }
        }
    }
    // =========================================================
// ANNOUNCEMENTS
// =========================================================

    private void createAnnouncements() {


        createAnnouncement(
                "School announcement",
                "Welcome to SERA School",
                AnnouncementTargetType.ALL,
                null,
                null
        );


        createAnnouncement(
                "Student announcement",
                "Exam schedule has been published",
                AnnouncementTargetType.ROLE,
                Role.STUDENT,
                null
        );


        createAnnouncement(
                "Guardian announcement",
                "Parents meeting next week",
                AnnouncementTargetType.ROLE,
                Role.GUARDIAN,
                null
        );


        createAnnouncement(
                "Teacher announcement",
                "Teacher meeting tomorrow",
                AnnouncementTargetType.ROLE,
                Role.TEACHER,
                null
        );



        /*
         * إعلان لطالب محدد
         */

        Student student =
                students.get(1);


        createAnnouncement(
                "Student personal notice",
                "Your documents need update",
                AnnouncementTargetType.STUDENT,
                null,
                student.getId()
        );

    }


// =========================================================
// CREATE ANNOUNCEMENT
// =========================================================

    private void createAnnouncement(
            String title,
            String content,
            AnnouncementTargetType type,
            Role role,
            Long targetId
    ) {


        Announcement announcement =
                new Announcement();


        announcement.setSchool(
                school
        );


        announcement.setTitle(
                title
        );


        announcement.setContent(
                content
        );


        announcement.setActive(
                true
        );


        Announcement saved =
                announcementRepository.save(
                        announcement
                );


        AnnouncementTarget target =
                new AnnouncementTarget();


        target.setSchool(
                school
        );


        target.setAnnouncement(
                saved
        );


        target.setType(
                type
        );


        target.setTargetRole(
                role
        );


        target.setTargetId(
                targetId
        );


        announcementTargetRepository.save(
                target
        );
    }
    // =========================================================
// NEXT ACADEMIC YEAR TEST DATA
// =========================================================

    private void createNextAcademicYearData() {


        AcademicYear nextYear =
                new AcademicYear();


        nextYear.setSchool(
                school
        );


        nextYear.setName(
                "2026-2027"
        );


        nextYear.setStartDate(
                LocalDate.of(
                        2026,
                        9,
                        1
                )
        );


        nextYear.setEndDate(
                LocalDate.of(
                        2027,
                        6,
                        30
                )
        );


        nextYear.setCurrentYear(
                false
        );


        academicYearRepository.save(
                nextYear
        );
    }

    private void createStudentGuardianRelations() {

        for (int i = 1; i <= students.size(); i++) {

            Student student = students.get(i);

            Guardian guardian =
                    guardians.get(i);

            if (guardian == null) {
                guardian =
                        createGuardian(i);

                guardians.put(
                        i,
                        guardian
                );
            }

            createStudentGuardian(
                    student,
                    guardian,
                    true
            );
        }
    }

    private void createExamResults() {

        for (Student student : students.values()) {

            ExamResult result =
                    new ExamResult();

            result.setSchool(
                    school
            );

            result.setStudent(
                    student
            );

            result.setScore(
                    60.0 + (student.getId() % 40)
            );

            examResultRepository.save(
                    result
            );
        }
    }

    private void createAttendance() {

        for (Student student : students.values()) {

            if (student.getId() % 5 == 0) {

                Attendance attendance =
                        new Attendance();

                attendance.setSchool(
                        school
                );

                attendance.setStudent(
                        student
                );

                attendance.setAttendanceDate(
                        LocalDate.of(
                                2026,
                                3,
                                1
                        )
                );

                attendance.setAttendanceStatus(
                        AttendanceStatus.ABSENT
                );

                attendanceRepository.save(
                        attendance
                );
            }
        }
    }

    private void createWarnings() {

        for (int i = 1; i <= 10; i++) {

            Warning warning =
                    new Warning();

            warning.setSchool(
                    school
            );

            warning.setStudent(
                    students.get(i)
            );

            warning.setReason(
                    WarningReason.ACADEMIC
            );

            warning.setMessage(
                    "Low performance"
            );

            warning.setWarningDate(
                    LocalDate.now()
            );

            warningRepository.save(
                    warning
            );
        }
    }
}