package com.SchoolManagementSystem.System.config;

import com.SchoolManagementSystem.System.entity.AuthUser;
import com.SchoolManagementSystem.System.entity.academic.SchoolClass;
import com.SchoolManagementSystem.System.entity.academic.Subject;
import com.SchoolManagementSystem.System.entity.academic.TeacherSubject;
import com.SchoolManagementSystem.System.entity.enumeration.*;
import com.SchoolManagementSystem.System.entity.school.School;
import com.SchoolManagementSystem.System.entity.student.Attendance;
import com.SchoolManagementSystem.System.entity.student.Student;
import com.SchoolManagementSystem.System.entity.student.StudentGuardian;
import com.SchoolManagementSystem.System.entity.user.*;
import com.SchoolManagementSystem.System.mapper.academic.SchoolClassMapper;
import com.SchoolManagementSystem.System.repository.academic.SchoolClassRepository;
import com.SchoolManagementSystem.System.repository.academic.SubjectRepository;
import com.SchoolManagementSystem.System.repository.academic.TeacherSubjectRepository;
import com.SchoolManagementSystem.System.repository.school.SchoolRepository;
import com.SchoolManagementSystem.System.repository.student.AttendanceRepository;
import com.SchoolManagementSystem.System.repository.student.StudentGuardianRepository;
import com.SchoolManagementSystem.System.repository.student.StudentRepository;
import com.SchoolManagementSystem.System.repository.user.*;
import com.SchoolManagementSystem.System.security.AuthUserRepository;
import com.SchoolManagementSystem.System.service.academic.SchoolClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DataSeeder implements CommandLineRunner {

    private final SchoolRepository schoolRepository;

    private final PrincipalRepository principalRepository;
    private final TeacherRepository teacherRepository;
    private final SecretaryRepository secretaryRepository;
    private final LibrarianRepository librarianRepository;

    private final AuthUserRepository authUserRepository;
    private final SubjectRepository subjectRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SchoolClassService schoolClassService;
    private final TeacherSubjectRepository teacherSubjectRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final GuardianRepository guardianRepository;
    private final StudentGuardianRepository studentGuardianRepository;
    private final AttendanceRepository attendanceRepository;

    @Override
    public void run(String... args) {

        if (schoolRepository.count() > 0) {
            log.info("Database already seeded");
            return;
        }

        /*
         * SCHOOL
         */

        School school = new School();

        school.setName("Al Noor Private School");
        school.setAddress("Damascus");
        school.setPhone("0115555555");

        school.setSchoolType(
                SchoolType.PRIVATE
        );

        school.setSemesterName(
                SemesterName.FIRST
        );

        school.setEducationStages(
                Set.of(
                        EducationStage.ELEMENTARY,
                        EducationStage.MIDDLE
                )
        );

        school = schoolRepository.save(
                school
        );

        /*
         * PRINCIPAL
         */

        Principal principal = new Principal();

        principal.setSchool(
                school
        );

        principal.setNationalId(
                "1000001"
        );

        principal.setFirstName(
                "Ahmad"
        );

        principal.setLastName(
                "Ali"
        );

        principal.setPhone(
                "0991111111"
        );

        principal.setAddress(
                "Damascus"
        );

        principal = principalRepository.save(
                principal
        );

        createAuth(
                "principal",
                "admin123",
                Role.PRINCIPAL,
                principal.getId()
        );

        /*
         * SECRETARY
         */

        Secretary secretary = new Secretary();

        secretary.setSchool(
                school
        );

        secretary.setNationalId(
                "1000002"
        );

        secretary.setFirstName(
                "Sara"
        );

        secretary.setLastName(
                "Hassan"
        );

        secretary.setPhone(
                "0992222222"
        );

        secretary.setAddress(
                "Damascus"
        );

        secretary = secretaryRepository.save(
                secretary
        );

        createAuth(
                "secretary",
                "123456",
                Role.SECRETARY,
                secretary.getId()
        );

        /*
         * LIBRARIAN
         */

        Librarian librarian = new Librarian();

        librarian.setSchool(
                school
        );

        librarian.setNationalId(
                "1000003"
        );

        librarian.setFirstName(
                "Omar"
        );

        librarian.setLastName(
                "Khaled"
        );

        librarian.setPhone(
                "0993333333"
        );

        librarian.setAddress(
                "Damascus"
        );

        librarian = librarianRepository.save(
                librarian
        );

        createAuth(
                "librarian",
                "123456",
                Role.LIBRARIAN,
                librarian.getId()
        );

        /*
         * TEACHERS
         */

        createTeacher(
                school,
                "2000001", "Mohammad", "Saleh", "Math");

        createTeacher(
                school,
                "2000002",
                "Lina",
                "Ahmad",
                "Arabic"
        );

        createTeacher(
                school,
                "2000003",
                "Khaled",
                "Omar",
                "Physics"
        );

        createTeacher(
                school,
                "2000004",
                "Nour",
                "Ali",
                "English"
        );

        seedAcademicData(
                school
        );

        seedStudents(
                school
        );

        seedAttendance();

        log.info(
                "Database seeded successfully"
        );
    }

    private void createTeacher(
            School school,
            String nationalId,
            String firstName,
            String lastName,
            String specialization
    ) {

        Teacher teacher = new Teacher();

        teacher.setSchool(
                school
        );

        teacher.setNationalId(
                nationalId
        );

        teacher.setFirstName(
                firstName
        );

        teacher.setLastName(
                lastName
        );

        teacher.setPhone(
                "099" + nationalId
        );

        teacher.setAddress(
                "Damascus"
        );

        teacher.setSpecialization(
                specialization
        );

        teacher = teacherRepository.save(
                teacher
        );

        createAuth(
                firstName.toLowerCase(),
                "123456",
                Role.TEACHER,
                teacher.getId()
        );
    }

    private void createAuth(
            String email,
            String password,
            Role role,
            Long refId
    ) {

        AuthUser user = new AuthUser();

        user.setEmail(
                email
        );

        user.setPassword(
                passwordEncoder.encode(
                        password
                )
        );

        user.setRole(
                role
        );

        user.setRefId(
                refId
        );

        user.setEnabled(
                true
        );

        authUserRepository.save(
                user
        );
    }

    private void seedAcademicData(School school) {

        /*
         * SUBJECTS
         */

        Subject math = createSubject(
                "Mathematics",
                GradeLevel.GRADE_10
        );

        Subject arabic = createSubject(
                "Arabic",
                GradeLevel.GRADE_10
        );

        Subject english = createSubject(
                "English",
                GradeLevel.GRADE_10
        );

        Subject physics = createSubject(
                "Physics",
                GradeLevel.GRADE_10
        );

        Subject chemistry = createSubject(
                "Chemistry",
                GradeLevel.GRADE_10
        );

        /*
         * CLASSES
         */

        createClass(
                school,
                GradeLevel.GRADE_10,
                "A"
        );

        createClass(
                school,
                GradeLevel.GRADE_10,
                "B"
        );

        createClass(
                school,
                GradeLevel.GRADE_11,
                "A"
        );

        /*
         * TEACHER SUBJECTS
         */

        Teacher mathTeacher = teacherRepository
                .findByNationalId("2000001")
                .orElseThrow();

        Teacher arabicTeacher = teacherRepository
                .findByNationalId("2000002")
                .orElseThrow();

        Teacher physicsTeacher = teacherRepository
                .findByNationalId("2000003")
                .orElseThrow();

        connectTeacherSubject(
                mathTeacher,
                math
        );

        connectTeacherSubject(
                arabicTeacher,
                arabic
        );

        connectTeacherSubject(
                physicsTeacher,
                physics
        );

    }

    private Subject createSubject(
            String name,
            GradeLevel gradeLevel
    ) {

        Subject subject = new Subject();

        subject.setName(
                name
        );

        subject.setGradeLevel(
                gradeLevel
        );

        subject.setSemesterName(
                SemesterName.FIRST
        );

        return subjectRepository.save(
                subject
        );
    }

    private SchoolClass createClass(
            School school,
            GradeLevel gradeLevel,
            String section) {

        SchoolClass schoolClass = new SchoolClass();

        schoolClass.setSchool(school);

        schoolClass.setGradeLevel(gradeLevel);

        schoolClass.setSection(section);

        schoolClass.setLocation("Room " + section);

        schoolClass.setCapacity(30);

        return SchoolClassMapper.toEntity(schoolClassService.save(SchoolClassMapper.toDto(schoolClass)));
    }

    private void connectTeacherSubject(
            Teacher teacher,
            Subject subject
    ) {

        TeacherSubject teacherSubject = new TeacherSubject();

        teacherSubject.setTeacher(
                teacher
        );

        teacherSubject.setSubject(
                subject
        );

        teacherSubjectRepository.save(
                teacherSubject
        );
    }

    private void seedStudents(School school) {

        SchoolClass class10A = schoolClassRepository.findAll()
                .stream()
                .filter(c ->
                        c.getSection().equals("A")
                                && c.getGradeLevel() == GradeLevel.GRADE_10
                )
                .findFirst()
                .orElseThrow();

        SchoolClass class10B = schoolClassRepository.findAll()
                .stream()
                .filter(c ->
                        c.getSection().equals("B")
                                && c.getGradeLevel() == GradeLevel.GRADE_10
                )
                .findFirst()
                .orElseThrow();

        Student ahmad = createStudent(
                school,
                class10A,
                "ST1001",
                "Ahmad",
                "Ali",
                Gender.MALE
        );

        Student mohammed = createStudent(
                school,
                class10A,
                "ST1002",
                "Mohammed",
                "Hassan",
                Gender.MALE
        );

        Student sara = createStudent(
                school,
                class10A,
                "ST1003",
                "Sara",
                "Omar",
                Gender.FEMALE
        );

        Student lina = createStudent(
                school,
                class10B,
                "ST1004",
                "Lina",
                "Khaled",
                Gender.FEMALE
        );

        Student yousef = createStudent(
                school,
                class10B,
                "ST1005",
                "Yousef",
                "Mahmoud",
                Gender.MALE
        );

        seedGuardians(
                school,
                ahmad,
                mohammed,
                sara,
                lina,
                yousef
        );
    }

    private Student createStudent(
            School school,
            SchoolClass schoolClass,
            String registration,
            String firstName,
            String lastName,
            Gender gender
    ) {

        Student student = new Student();

        student.setSchool(
                school
        );

        student.setStudentSchoolClass(
                schoolClass
        );

        student.setRegistrationNumber(
                registration
        );

        student.setFirstName(
                firstName
        );

        student.setLastName(
                lastName
        );

        student.setGender(
                gender
        );

        student.setGradeLevel(
                schoolClass.getGradeLevel()
        );

        student.setDateOfBirth(
                LocalDate.of(
                        2010,
                        5,
                        10
                )
        );

        student.setAddress(
                "Damascus"
        );

        student.setPhone(
                "0999999999"
        );

        student.setEnrollmentDate(
                LocalDate.now()
        );

        student = studentRepository.save(
                student
        );

        createAuth(
                registration.toLowerCase(),
                "123456",
                Role.STUDENT,
                student.getId()
        );

        return student;
    }

    private void seedGuardians(
            School school,
            Student ahmad,
            Student mohammed,
            Student sara,
            Student lina,
            Student yousef
    ) {

        Guardian father = createGuardian(
                school,
                "900001",
                "Ali",
                "Ahmad",
                "Father"
        );

        Guardian mother = createGuardian(
                school,
                "900002",
                "Mona",
                "Ahmad",
                "Mother"
        );

        Guardian khaled = createGuardian(
                school,
                "900003",
                "Khaled",
                "Omar",
                "Engineer"
        );

        Guardian saraMother = createGuardian(
                school,
                "900004",
                "Huda",
                "Ali",
                "Teacher"
        );

        /*
         * Ahmad
         */

        connectGuardian(
                ahmad,
                father,
                true
        );

        connectGuardian(
                ahmad,
                mother,
                false
        );

        /*
         * Mohammed
         * without guardian
         */

        /*
         * Sara
         */

        connectGuardian(
                sara,
                khaled,
                true
        );

        /*
         * Lina
         */

        connectGuardian(
                lina,
                saraMother,
                true
        );

        /*
         * Guardian Accounts
         */

        createAuth(
                "guardian1",
                "123456",
                Role.GUARDIAN,
                father.getId()
        );

        createAuth(
                "guardian2",
                "123456",
                Role.GUARDIAN,
                mother.getId()
        );

        createAuth(
                "guardian3",
                "123456",
                Role.GUARDIAN,
                khaled.getId()
        );

        createAuth(
                "guardian4",
                "123456",
                Role.GUARDIAN,
                saraMother.getId()
        );
    }

    private Guardian createGuardian(
            School school,
            String nationalId,
            String firstName,
            String lastName,
            String occupation
    ) {

        Guardian guardian = new Guardian();

        guardian.setSchool(
                school
        );

        guardian.setNationalId(
                nationalId
        );

        guardian.setFirstName(
                firstName
        );

        guardian.setLastName(
                lastName
        );

        guardian.setPhone(
                "0988888888"
        );

        guardian.setAddress(
                "Damascus"
        );

        guardian.setOccupation(
                occupation
        );

        return guardianRepository.save(
                guardian
        );
    }

    private void connectGuardian(
            Student student,
            Guardian guardian,
            boolean primary
    ) {

        StudentGuardian relation = new StudentGuardian();

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

    private void seedAttendance() {

        List<Student> students = studentRepository.findAll();

        for (Student student : students) {

            Attendance attendance = new Attendance();

            attendance.setStudent(
                    student
            );

            attendance.setAttendanceDate(
                    LocalDate.now()
            );

            attendance.setAttendanceStatus(
                    AttendanceStatus.PRESENT
            );

            attendanceRepository.save(
                    attendance
            );

            Attendance oldAttendance = new Attendance();

            oldAttendance.setStudent(
                    student
            );

            oldAttendance.setAttendanceDate(
                    LocalDate.now().minusDays(1)
            );

            oldAttendance.setAttendanceStatus(
                    AttendanceStatus.ABSENT
            );

            attendanceRepository.save(
                    oldAttendance
            );
        }
    }
}