package com.SchoolManagementSystem.system.service.academic.impl;

import com.SchoolManagementSystem.system.dto.academic.AssessmentResultDto;
import com.SchoolManagementSystem.system.dto.academic.request.AssessmentResultRequest;
import com.SchoolManagementSystem.system.dto.academic.request.StudentAssessmentResultRequest;
import com.SchoolManagementSystem.system.entity.academic.Assessment;
import com.SchoolManagementSystem.system.entity.academic.AssessmentResult;
import com.SchoolManagementSystem.system.entity.enumeration.Role;
import com.SchoolManagementSystem.system.entity.student.Student;
import com.SchoolManagementSystem.system.entity.student.StudentGuardian;
import com.SchoolManagementSystem.system.exception.business.AlreadyExistsException;
import com.SchoolManagementSystem.system.exception.business.AuthenticationException;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.business.ValidationException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.mapper.academic.AssessmentResultMapper;
import com.SchoolManagementSystem.system.repository.academic.AssessmentRepository;
import com.SchoolManagementSystem.system.repository.academic.AssessmentResultRepository;
import com.SchoolManagementSystem.system.repository.academic.TeacherSubjectRepository;
import com.SchoolManagementSystem.system.repository.student.StudentGuardianRepository;
import com.SchoolManagementSystem.system.repository.student.StudentRepository;
import com.SchoolManagementSystem.system.security.UserPrincipal;
import com.SchoolManagementSystem.system.service.academic.AssessmentResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AssessmentResultServiceImpl
        implements AssessmentResultService {

    private final AssessmentRepository assessmentRepository;
    private final StudentGuardianRepository studentGuardianRepository;
    private final AssessmentResultRepository assessmentResultRepository;
    private final StudentRepository studentRepository;
    private final TeacherSubjectRepository teacherSubjectRepository;

    @Override
    @Transactional(readOnly = true)
    public AssessmentResultDto getById(Long id) {

        return assessmentResultRepository.findById(id)
                .map(AssessmentResultMapper::toDto)
                .orElseThrow(() ->
                        new NotFoundException(ErrorCode.ASSESSMENT_RESULT_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssessmentResultDto> getAssessmentResults(Long assessmentId) {

        if (!assessmentRepository.existsById(assessmentId)) {
            throw new NotFoundException(ErrorCode.ASSESSMENT_NOT_FOUND);
        }

        return assessmentResultRepository.findByAssessmentId(assessmentId)
                .stream()
                .map(AssessmentResultMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssessmentResultDto> getStudentResults(Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new NotFoundException(ErrorCode.STUDENT_NOT_FOUND);
        }

        return assessmentResultRepository.findByStudentId(studentId)
                .stream()
                .map(AssessmentResultMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {

        AssessmentResult result = assessmentResultRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(ErrorCode.ASSESSMENT_RESULT_NOT_FOUND));

        assessmentResultRepository.delete(result);
    }

    @Override
    public void saveResults(UserPrincipal user, AssessmentResultRequest request) {
        if (request.results() == null || request.results().isEmpty()) {
            throw new ValidationException(ErrorCode.STUDENTS_REQUIRED);
        }
        Assessment assessment = getAssessment(request.assessmentId());

        validatePermission(assessment, user);

        validateDuplicateStudents(request.results());

        validateScores(assessment, request.results());

        Map<Long, Student> students = loadStudents(request.results());

        validateStudentsBelongToClass(assessment, students);

        List<AssessmentResult> existing =
                assessmentResultRepository.findByAssessmentId(assessment.getId());

        Set<Long> existingStudentIds =
                existing.stream()
                        .map(r -> r.getStudent().getId())
                        .collect(Collectors.toSet());

        List<AssessmentResult> results = new ArrayList<>();

        for (StudentAssessmentResultRequest dto : request.results()) {

            if (existingStudentIds.contains(dto.studentId())) {
                throw new AlreadyExistsException(ErrorCode.STUDENT_ALREADY_HAVE_MARK);
            }

            AssessmentResult result = new AssessmentResult();

            result.setAssessment(assessment);
            result.setStudent(students.get(dto.studentId()));
            result.setScore(dto.score());

            results.add(result);
        }

        assessmentResultRepository.saveAll(results);
    }



    @Override
    public void updateResults(UserPrincipal user, AssessmentResultRequest request) {
        if (request.results().isEmpty()) {
            throw new ValidationException(ErrorCode.STUDENTS_REQUIRED);
        }

        Assessment assessment = getAssessment(request.assessmentId());

        validatePermission(assessment, user);

        validateDuplicateStudents(request.results());

        validateScores(assessment, request.results());

        List<AssessmentResult> existing =
                assessmentResultRepository.findByAssessmentId(assessment.getId());

        Map<Long, AssessmentResult> resultMap =
                existing.stream()
                        .collect(Collectors.toMap(
                                r -> r.getStudent().getId(),
                                Function.identity()));

        List<AssessmentResult> updated = new ArrayList<>();

        for (StudentAssessmentResultRequest dto : request.results()) {

            AssessmentResult result = resultMap.get(dto.studentId());

            if (result == null) {
                throw new NotFoundException(ErrorCode.ASSESSMENT_RESULT_NOT_FOUND);
            }

            result.setScore(dto.score());

            updated.add(result);
        }

        assessmentResultRepository.saveAll(updated);
    }

    private Assessment getAssessment(Long assessmentId) {

        return assessmentRepository.findById(assessmentId)
                .orElseThrow(() ->
                        new NotFoundException(ErrorCode.ASSESSMENT_NOT_FOUND));
    }

    private Map<Long, Student> loadStudents(
            List<StudentAssessmentResultRequest> requests) {

        Set<Long> ids =
                requests.stream()
                        .map(StudentAssessmentResultRequest::studentId)
                        .collect(Collectors.toSet());

        List<Student> students = studentRepository.findAllById(ids);

        if (students.size() != ids.size()) {
            throw new NotFoundException(ErrorCode.STUDENT_NOT_FOUND);
        }

        return students.stream()
                .collect(Collectors.toMap(
                        Student::getId,
                        Function.identity()));
    }

    private void validateDuplicateStudents(
            List<StudentAssessmentResultRequest> requests) {

        Set<Long> ids = new HashSet<>();

        for (StudentAssessmentResultRequest dto : requests) {

            if (!ids.add(dto.studentId())) {
                throw new ValidationException(ErrorCode.DUPLICATE_STUDENT);
            }
        }
    }

    private void validateScores(
            Assessment assessment,
            List<StudentAssessmentResultRequest> requests) {

        for (StudentAssessmentResultRequest dto : requests) {

            if (dto.score() == null) {
                throw new ValidationException(ErrorCode.SCORE_CANT_BE_NULL);
            }
        }
    }
    private void validateStudentsBelongToClass(
            Assessment assessment,
            Map<Long, Student> students) {

        Long classId = assessment.getClassSchedule()
                .getSchoolClass()
                .getId();

        for (Student student : students.values()) {

            if (!student.getStudentSchoolClass().getId().equals(classId)) {
                throw new ValidationException(
                        ErrorCode.STUDENT_NOT_IN_CLASS
                );
            }
        }
    }

    private void validatePermission(
            Assessment assessment,
            UserPrincipal user) {

        if (user.getRole() == Role.PRINCIPAL) {
            return;
        }

        if (user.getRole() != Role.TEACHER) {
            throw new AuthenticationException(ErrorCode.UNAUTHORIZED);
        }

        if (!assessment.getClassSchedule().getTeacher().getId()
                .equals(user.getRefId())) {
            throw new AuthenticationException(ErrorCode.UNAUTHORIZED);
        }
    }
    @Override
    @Transactional(readOnly = true)
    public List<AssessmentResultDto> getGuardianChildrenResults(
            Long guardianId
    ) {

        return studentGuardianRepository
                .findByGuardianId(guardianId)
                .stream()
                .map(StudentGuardian::getStudent)
                .flatMap(student ->
                        assessmentResultRepository
                                .findByStudentId(student.getId())
                                .stream()
                )
                .map(AssessmentResultMapper::toDto)
                .toList();
    }
}