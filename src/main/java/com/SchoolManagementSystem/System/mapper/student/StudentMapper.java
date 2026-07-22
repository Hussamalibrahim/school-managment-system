package com.SchoolManagementSystem.System.mapper.student;

import com.SchoolManagementSystem.System.dto.student.StudentDto;
import com.SchoolManagementSystem.System.entity.academic.SchoolClass;
import com.SchoolManagementSystem.System.entity.school.School;
import com.SchoolManagementSystem.System.entity.student.Student;
import com.SchoolManagementSystem.System.security.dto.AuthRequestStudent;

public final class StudentMapper {

    private StudentMapper() {}

    public static StudentDto toDto(Student student) {
        if (student == null) return null;

        return new StudentDto(
                student.getId(),
                student.getCreatedAt(),
                student.getUpdatedAt(),
                student.getDeletedAt(),

                student.getSchool() != null ? student.getSchool().getId() : null,
                student.getStudentSchoolClass() != null ? student.getStudentSchoolClass().getId() : null,

                student.getRegistrationNumber(),
                student.getFirstName(),
                student.getLastName(),

                student.getGender(),
                student.getGradeLevel(),

                student.getDateOfBirth(),
                student.getAddress(),
                student.getEnrollmentDate(),
                student.getPhone(),
                student.getNotes()
        );
    }

    public static Student toEntity(StudentDto dto) {
        if (dto == null) return null;

        Student student = new Student();

        student.setId(dto.id());
        student.setCreatedAt(dto.createdAt());
        student.setUpdatedAt(dto.updatedAt());
        student.setDeletedAt(dto.deletedAt());

        if (dto.schoolId() != null) {
            School school = new School();
            school.setId(dto.schoolId());
            student.setSchool(school);
        }

        if (dto.schoolClassId() != null) {
            SchoolClass schoolClass = new SchoolClass();
            schoolClass.setId(dto.schoolClassId());
            student.setStudentSchoolClass(schoolClass);
        }

        student.setRegistrationNumber(dto.registrationNumber());
        student.setFirstName(dto.firstName());
        student.setLastName(dto.lastName());
        student.setGender(dto.gender());
        student.setGradeLevel(dto.gradeLevel());
        student.setDateOfBirth(dto.dateOfBirth());
        student.setAddress(dto.address());
        student.setEnrollmentDate(dto.enrollmentDate());
        student.setPhone(dto.phone());
        student.setNotes(dto.notes());

        return student;
    }
    public static Student fromAuthRequestStudent(AuthRequestStudent authRequestStudent) {
        Student student = new Student();
        student.setRegistrationNumber(authRequestStudent.registrationNumber());
        student.setFirstName(authRequestStudent.firstName());
        student.setLastName(authRequestStudent.lastName());
        student.setGender(authRequestStudent.gender());
        student.setGradeLevel(authRequestStudent.gradeLevel());
        student.setDateOfBirth(authRequestStudent.dateOfBirth());
        student.setAddress(authRequestStudent.address());
        student.setEnrollmentDate(authRequestStudent.enrollmentDate());
        student.setPhone(authRequestStudent.phone());
        student.setNotes(authRequestStudent.notes());
        return student;
    }
    public static void updateEntity(Student student, StudentDto dto) {

        student.setRegistrationNumber(dto.registrationNumber());
        student.setFirstName(dto.firstName());
        student.setLastName(dto.lastName());
        student.setGender(dto.gender());
        student.setDateOfBirth(dto.dateOfBirth());
        student.setAddress(dto.address());
        student.setEnrollmentDate(dto.enrollmentDate());
        student.setPhone(dto.phone());
        student.setNotes(dto.notes());
    }
}