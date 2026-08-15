package com.SchoolManagementSystem.System.service.academic.impl;

import com.SchoolManagementSystem.System.dto.academic.ExamResultDto;
import com.SchoolManagementSystem.System.dto.academic.request.SaveExamResultsRequest;
import com.SchoolManagementSystem.System.dto.academic.request.StudentExamScoreRequest;
import com.SchoolManagementSystem.System.entity.academic.Exam;
import com.SchoolManagementSystem.System.entity.academic.ExamResult;
import com.SchoolManagementSystem.System.entity.academic.SchoolClass;
import com.SchoolManagementSystem.System.entity.enumeration.Role;
import com.SchoolManagementSystem.System.entity.student.Student;
import com.SchoolManagementSystem.System.exception.business.NotFoundException;
import com.SchoolManagementSystem.System.exception.business.ValidationException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.mapper.academic.ExamResultMapper;
import com.SchoolManagementSystem.System.repository.academic.ExamRepository;
import com.SchoolManagementSystem.System.repository.academic.ExamResultRepository;
import com.SchoolManagementSystem.System.repository.student.StudentRepository;
import com.SchoolManagementSystem.System.security.UserPrincipal;
import com.SchoolManagementSystem.System.service.academic.ExamResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamResultServiceImpl implements ExamResultService {

    private final ExamRepository examRepository;
    private final ExamResultRepository examResultRepository;
    private final StudentRepository studentRepository;

    @Override
    @Transactional(readOnly = true)
    public ExamResultDto getById(UserPrincipal user, Long id) {

        ExamResult examResult = examResultRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EXAM_RESULT_NOT_FOUND));

        if (Role.TEACHER.equals(user.getRole())) {
            validateTeacherExam(examResult.getExam(), user);
        }

        return ExamResultMapper.toDto(examResult);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamResultDto> getByExam(
            Long examId,
            UserPrincipal user
    ) {

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EXAM_NOT_FOUND));

        if (user.getRole() == Role.TEACHER) {
            validateTeacherExam(exam, user);
        }

        return examResultRepository.findByExamId(examId)
                .stream()
                .map(ExamResultMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public List<ExamResultDto> saveResults(
            SaveExamResultsRequest request,
            UserPrincipal user
    ) {

        Exam exam = examRepository.findById(request.examId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.EXAM_NOT_FOUND));

        validateTeacherExam(exam, user);

        List<Long> studentIds = request.results()
                .stream()
                .map(StudentExamScoreRequest::studentId)
                .toList();

        List<Student> students = studentRepository.findAllById(studentIds);

        if (students.size() != studentIds.size()) {
            throw new NotFoundException(ErrorCode.STUDENT_NOT_FOUND);
        }

        List<ExamResult> existingResults =
                examResultRepository.findByExamId(exam.getId());

        Map<Long, Student> studentMap = students.stream()
                .collect(Collectors.toMap(Student::getId, Function.identity()));

        Map<Long, ExamResult> resultMap = existingResults.stream()
                .collect(Collectors.toMap(
                        r -> r.getStudent().getId(),
                        Function.identity()
                ));

        List<ExamResult> resultsToSave = new ArrayList<>();

        for (StudentExamScoreRequest scoreRequest : request.results()) {

            Student student = studentMap.get(scoreRequest.studentId());

            validateStudentBelongsToClass(student, exam.getSchoolClass());

            validateScore(scoreRequest.score(), exam.getMaxScore());

            ExamResult examResult = resultMap.get(student.getId());

            if (examResult == null) {
                examResult = new ExamResult();
                examResult.setExam(exam);
                examResult.setStudent(student);
            }

            examResult.setScore(scoreRequest.score());

            resultsToSave.add(examResult);
        }

        return examResultRepository.saveAll(resultsToSave)
                .stream()
                .map(ExamResultMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void delete(
            Long id,
            UserPrincipal user
    ) {

        ExamResult examResult = examResultRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EXAM_RESULT_NOT_FOUND));

        if (user.getRole() == Role.TEACHER) {
            validateTeacherExam(examResult.getExam(), user);
        }

        examResultRepository.delete(examResult);
    }

    private void validateTeacherExam(
            Exam exam,
            UserPrincipal user) {

        boolean authorized =
                examRepository.existsByIdAndSchoolClassIdAndSubjectTeacherSubjectsTeacherId(
                        exam.getId(),
                        exam.getSchoolClass().getId(),
                        user.getRefId()
                );

        if (!authorized) {
            throw new ValidationException(ErrorCode.UNAUTHORIZED);
        }
    }

    private void validateStudentBelongsToClass(
            Student student,
            SchoolClass schoolClass
    ) {

        if (!student.getStudentSchoolClass().getId().equals(schoolClass.getId())) {
            throw new ValidationException(ErrorCode.STUDENT_NOT_IN_CLASS);
        }
    }

    private void validateScore(
            Double score,
            Double maxScore
    ) {

        if (score == null || score < 0 || score > maxScore) {
            throw new ValidationException(ErrorCode.SCORE_OUT_OF_RANGE);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamResultDto> getAll() {

        return examResultRepository.findAll()
                .stream()
                .map(ExamResultMapper::toDto)
                .toList();
    }
}