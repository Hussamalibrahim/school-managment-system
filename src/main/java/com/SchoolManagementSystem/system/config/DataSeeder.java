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
import com.SchoolManagementSystem.System.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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


        School school = createSchool(
                "Al Noor Private School",
                "alnoor"
        );


        seedMainSchool(
                school
        );


        School secondSchool = createSchool(
                "Test School",
                "testschool"
        );


        createOnlyPrincipal(
                secondSchool
        );


        log.info(
                "Database seeded successfully"
        );
    }


    private School createSchool(
            String name,
            String code
    ) {

        School school = new School();

        school.setName(
                name
        );

        school.setCode(
                code
        );

        school.setAddress(
                "Damascus"
        );

        school.setPhone(
                "0115555555"
        );

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


        return schoolRepository.save(
                school
        );
    }


    private void seedMainSchool(
            School school
    ) {
        TenantContext.set(
                school.getId(),
                school.getCode()
        );
        Principal principal = new Principal();

        principal.setSchool(
                school
        );

        principal.setNationalId(
                "1111111111"
        );

        principal.setFirstName(
                "Ahmad"
        );

        principal.setLastName(
                "Ali"
        );

        principal.setPhone(
                "0999999999"
        );

        principal.setAddress(
                "Damascus"
        );

        principal.setHireDate(
                LocalDate.now()
        );


        principal = principalRepository.save(principal);


        createAuth(
                school,
                "principal@alnoor.com",
                "admin123",
                Role.PRINCIPAL,
                principal.getId()
        );


        Secretary secretary = new Secretary();

        secretary.setSchool(
                school
        );

        secretary.setNationalId(
                "2222222222"
        );

        secretary.setFirstName(
                "Mohammad"
        );

        secretary.setLastName(
                "Hassan"
        );

        secretary.setPhone(
                "0988888888"
        );

        secretary.setAddress(
                "Damascus"
        );

        secretary.setHireDate(
                LocalDate.now()
        );


        secretary = secretaryRepository.save(
                secretary
        );


        createAuth(
                school,
                "secretary@alnoor.com",
                "1234",
                Role.SECRETARY,
                secretary.getId()
        );


        Librarian librarian = new Librarian();

        librarian.setSchool(
                school
        );

        librarian.setNationalId(
                "3333333333"
        );

        librarian.setFirstName(
                "Khaled"
        );

        librarian.setLastName(
                "Omar"
        );

        librarian.setPhone(
                "0977777777"
        );

        librarian.setAddress(
                "Damascus"
        );

        librarian.setHireDate(
                LocalDate.now()
        );


        librarian = librarianRepository.save(
                librarian
        );


        createAuth(
                school,
                "librarian@alnoor.com",
                "1234",
                Role.LIBRARIAN,
                librarian.getId()
        );


        Teacher teacher1 = createTeacher(
                school,
                "4444444444",
                "Ali",
                "Ahmad",
                "0966666666"
        );


        Teacher teacher2 = createTeacher(
                school,
                "5555555555",
                "Omar",
                "Khaled",
                "0955555555"
        );


        createAuth(
                school,
                "teacher1@alnoor.com",
                "1234",
                Role.TEACHER,
                teacher1.getId()
        );


        createAuth(
                school,
                "teacher2@alnoor.com",
                "1234",
                Role.TEACHER,
                teacher2.getId()
        );
        Subject math = new Subject();

        math.setSchool(
                school
        );

        math.setName(
                "Mathematics"
        );

        math.setGradeLevel(
                GradeLevel.GRADE_1
        );
        math.setSemesterName(
                SemesterName.FIRST
        );

        math = subjectRepository.save(
                math
        );


        Subject arabic = new Subject();

        arabic.setSchool(
                school
        );

        arabic.setName(
                "Arabic"
        );

        arabic.setGradeLevel(
                GradeLevel.GRADE_1
        );
        arabic.setSemesterName(
                SemesterName.FIRST
        );

        arabic = subjectRepository.save(
                arabic
        );


        TeacherSubject teacherSubject1 =
                new TeacherSubject();

        teacherSubject1.setSchool(
                school
        );

        teacherSubject1.setTeacher(
                teacher1
        );

        teacherSubject1.setSubject(
                math
        );


        teacherSubjectRepository.save(
                teacherSubject1
        );


        TeacherSubject teacherSubject2 =
                new TeacherSubject();

        teacherSubject2.setSchool(
                school
        );

        teacherSubject2.setTeacher(
                teacher2
        );

        teacherSubject2.setSubject(
                arabic
        );


        teacherSubjectRepository.save(
                teacherSubject2
        );


        SchoolClass class10A =
                new SchoolClass();

        class10A.setSchool(
                school
        );

        class10A.setSection(
                "10-A"
        );

        class10A.setGradeLevel(
                GradeLevel.GRADE_1
        );


        class10A =
                schoolClassRepository.save(
                        class10A
                );


        Student student1 =
                createStudent(
                        school,
                        class10A,
                        "600001",
                        "Ali",
                        "Ahmad"
                );


        Student student2 =
                createStudent(
                        school,
                        class10A,
                        "600002",
                        "Omar",
                        "Hassan"
                );


        createAuth(
                school,
                "student1@alnoor.com",
                "1234",
                Role.STUDENT,
                student1.getId()
        );


        createAuth(
                school,
                "student2@alnoor.com",
                "1234",
                Role.STUDENT,
                student2.getId()
        );

        Guardian guardian =
                createGuardian(
                        school,
                        "7777777777",
                        "Father",
                        "Ali"
                );


        StudentGuardian relation =
                new StudentGuardian();

        relation.setSchool(
                school
        );

        relation.setStudent(
                student1
        );

        relation.setGuardian(
                guardian
        );

        relation.setPrimaryGuardian(
                true
        );


        studentGuardianRepository.save(
                relation
        );


        Attendance attendance =
                new Attendance();

        attendance.setSchool(
                school
        );

        attendance.setStudent(
                student1
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
    }


    private void createOnlyPrincipal(
            School school
    ) {
        TenantContext.set(
                school.getId(),
                school.getCode()
        );
        Principal principal =
                new Principal();

        principal.setSchool(
                school
        );

        principal.setNationalId(
                "9999999999"
        );

        principal.setFirstName(
                "Test"
        );

        principal.setLastName(
                "Principal"
        );

        principal.setPhone(
                "0900000000"
        );

        principal.setAddress(
                "Test Address"
        );

        principal.setHireDate(
                LocalDate.now()
        );


        principal =
                principalRepository.save(
                        principal
                );


        createAuth(
                school,
                "principal@testschool.com",
                "admin123",
                Role.PRINCIPAL,
                principal.getId()
        );
    }


    private Teacher createTeacher(
            School school,
            String nationalId,
            String firstName,
            String lastName,
            String phone
    ) {

        Teacher teacher =
                new Teacher();

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
                phone
        );

        teacher.setHireDate(
                LocalDate.now()
        );


        return teacherRepository.save(
                teacher
        );
    }


    private Student createStudent(
            School school,
            SchoolClass schoolClass,
            String registrationNumber,
            String firstName,
            String lastName
    ) {

        Student student =
                new Student();

        student.setSchool(
                school
        );

        student.setStudentSchoolClass(
                schoolClass
        );

        student.setRegistrationNumber(
                registrationNumber
        );

        student.setFirstName(
                firstName
        );

        student.setLastName(
                lastName
        );

        student.setGender(
                Gender.MALE
        );

        student.setGradeLevel(
                GradeLevel.GRADE_1
        );

        student.setDateOfBirth(
                LocalDate.of(
                        2010,
                        1,
                        1
                )
        );


        return studentRepository.save(
                student
        );
    }


    private Guardian createGuardian(
            School school,
            String nationalId,
            String firstName,
            String lastName) {

        Guardian guardian =
                new Guardian();

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


        return guardianRepository.save(
                guardian
        );
    }


    private void createAuth(
            School school,
            String email,
            String password,
            Role role,
            Long refId
    ) {

        AuthUser user =
                new AuthUser();

        user.setSchool(
                school
        );

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

        if(authUserRepository
                .findByEmailAndSchoolId(
                        email,
                        school.getId()
                )
                .isPresent()) {

            return;
        }
        authUserRepository.save(
                user
        );
    }

}