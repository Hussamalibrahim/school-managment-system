package com.SchoolManagementSystem.system.service.academic.impl;

import com.SchoolManagementSystem.system.dto.academic.SemesterResultDto;
import com.SchoolManagementSystem.system.entity.academic.*;
import com.SchoolManagementSystem.system.entity.enumeration.ExamCategory;
import com.SchoolManagementSystem.system.entity.enumeration.SemesterName;
import com.SchoolManagementSystem.system.entity.school.AcademicYear;
import com.SchoolManagementSystem.system.entity.student.Student;
import com.SchoolManagementSystem.system.entity.student.StudentGuardian;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.business.ValidationException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.mapper.academic.SemesterResultMapper;
import com.SchoolManagementSystem.system.repository.academic.*;
import com.SchoolManagementSystem.system.repository.school.AcademicYearRepository;
import com.SchoolManagementSystem.system.repository.student.StudentGuardianRepository;
import com.SchoolManagementSystem.system.repository.student.StudentRepository;
import com.SchoolManagementSystem.system.service.academic.SemesterResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SemesterResultServiceImpl implements SemesterResultService {

    private static final double CONTINUOUS_WEIGHT = 0.40;
    private static final double EXAM_WEIGHT = 0.60;

    private final SemesterRepository semesterRepository;
    private final StudentGuardianRepository studentGuardianRepository;
    private final AssessmentRepository assessmentRepository;
    private final AssessmentResultRepository assessmentResultRepository;
    private final ExamRepository examRepository;
    private final ExamResultRepository examResultRepository;
    private final SemesterResultRepository semesterResultRepository;
    private final StudentRepository studentRepository;
    private final AcademicYearRepository academicYearRepository;

    @Override
    public void finalizeSemester(SemesterName semesterName) {

        AcademicYear academicYear = academicYearRepository.findByCurrentYearTrue()
                        .orElseThrow(() -> new NotFoundException(ErrorCode.ACADEMIC_YEAR_NOT_FOUNT));

        Semester semester = semesterRepository.findByAcademicYearIdAndSemesterName(
                                academicYear.getId(),
                                semesterName)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.SEMESTER_NOT_FOUND));

        List<Assessment> assessments = assessmentRepository.findBySemesterId(semester.getId());

        List<Exam> exams = examRepository.findBySemesterId(semester.getId());

        if (exams.isEmpty()) {
            throw new ValidationException(ErrorCode.NO_EXAMS_FOUND);
        }

        List<Exam> finalExams = exams.stream()
                .filter(exam -> exam.getCategory() == ExamCategory.FINAL)
                .toList();

        List<Exam> makeupExams = exams.stream()
                .filter(exam -> exam.getCategory() == ExamCategory.MAKEUP)
                .toList();

        Map<Long, List<AssessmentResult>> assessmentResults =
                assessmentResultRepository.findByAssessmentSemesterId(semester.getId())
                        .stream()
                        .collect(Collectors.groupingBy(
                                result -> result.getStudent().getId()
                        ));

        Map<Long, List<ExamResult>> finalExamResults = examResultRepository.findByExamSemesterId(semester.getId())
                        .stream()
                        .filter(result ->
                                result.getExam().getCategory() == ExamCategory.FINAL)
                        .collect(Collectors.groupingBy(
                                result -> result.getStudent().getId()
                        ));

        Map<Long, List<ExamResult>> makeupResults =
                examResultRepository.findByExamSemesterId(semester.getId())
                        .stream()
                        .filter(result ->
                                result.getExam().getCategory() == ExamCategory.MAKEUP)
                        .collect(Collectors.groupingBy(
                                result -> result.getStudent().getId()
                        ));

        Map<ResultKey, SemesterResult> existingResults =
                semesterResultRepository.findBySemesterId(semester.getId())
                        .stream()
                        .collect(Collectors.toMap(
                                result -> new ResultKey(
                                        result.getStudent().getId(),
                                        result.getSubject().getId()
                                ),
                                result -> result
                        ));

        Set<Long> studentIds = new HashSet<>();

        studentIds.addAll(assessmentResults.keySet());
        studentIds.addAll(finalExamResults.keySet());
        studentIds.addAll(makeupResults.keySet());

        List<Student> students =
                studentRepository.findAllById(studentIds);

        Map<Long, Student> studentMap = students.stream()
                .collect(Collectors.toMap(
                        Student::getId,
                        student -> student
                ));

        List<SemesterResult> results = new ArrayList<>();

        for (Long studentId : studentIds) {

            Student student = studentMap.get(studentId);

            if (student == null) {
                continue;
            }

            List<AssessmentResult> studentAssessments =
                    assessmentResults.getOrDefault(
                            studentId,
                            Collections.emptyList()
                    );

            Map<Long, List<AssessmentResult>> assessmentsBySubject =
                    studentAssessments.stream()
                            .collect(Collectors.groupingBy(
                                    result ->
                                            result.getAssessment()
                                                    .getClassSchedule()
                                                    .getSubject()
                                                    .getId()
                            ));

            List<ExamResult> studentFinalExams =
                    finalExamResults.getOrDefault(
                            studentId,
                            Collections.emptyList()
                    );

            List<ExamResult> studentMakeups =
                    makeupResults.getOrDefault(
                            studentId,
                            Collections.emptyList()
                    );

            for (ExamResult finalExamResult : studentFinalExams) {

                Long subjectId =
                        finalExamResult
                                .getExam()
                                .getSubject()
                                .getId();

                double continuousAverage =
                        calculateContinuousAverage(
                                assessmentsBySubject
                                        .getOrDefault(
                                                subjectId,
                                                Collections.emptyList())
                        );

                double examScore = finalExamResult.getScore();

                Optional<ExamResult> makeup = findMakeup(studentMakeups, subjectId);

                if (makeup.isPresent()) {
                    examScore = makeup.get().getScore();
                }

                double finalScore = continuousAverage * CONTINUOUS_WEIGHT + examScore * EXAM_WEIGHT;

                Subject subject = finalExamResult.getExam().getSubject();

                ResultKey key = new ResultKey(studentId, subjectId);

                SemesterResult semesterResult =
                        existingResults.get(key);

                if (semesterResult == null) {

                    semesterResult = new SemesterResult();

                    semesterResult.setStudent(student);
                    semesterResult.setSemester(semester);
                    semesterResult.setSubject(subject);
                }

                semesterResult.setContinuousAverage(round(continuousAverage));
                semesterResult.setExamScore(round(examScore));
                semesterResult.setFinalScore(round(finalScore));

                results.add(semesterResult);
            }
        }

        semesterResultRepository.saveAll(results);
    }

    private double calculateContinuousAverage(
            List<AssessmentResult> results) {

        if (results.isEmpty()) {
            return 0.0;
        }

        return results.stream()
                .mapToDouble(AssessmentResult::getScore)
                .average()
                .orElse(0.0);
    }

    private Optional<ExamResult> findMakeup(List<ExamResult> makeups, Long subjectId) {

        return makeups.stream().filter(result -> result.getExam()
                                .getSubject()
                                .getId()
                                .equals(subjectId))
                .findFirst();
    }

    private double round(double value) {

        return Math.round(value * 100.0) / 100.0;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SemesterResultDto> getByStudent(Long studentId, Long semesterId) {

        if (!studentRepository.existsById(studentId)) {
            throw new NotFoundException(ErrorCode.STUDENT_NOT_FOUND);
        }

        return semesterResultRepository.findByStudentIdAndSemesterId(studentId, semesterId)
                .stream()
                .map(SemesterResultMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SemesterResultDto> getBySemester(Long semesterId) {

        return semesterResultRepository.findBySemesterId(semesterId)
                .stream()
                .map(SemesterResultMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SemesterResultDto getById(Long id) {

        SemesterResult result =
                semesterResultRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.SEMESTER_RESULT_NOT_FOUND));

        return SemesterResultMapper.toDto(result);
    }

    private record ResultKey(Long studentId, Long subjectId) {
    }
    @Override
    @Transactional(readOnly = true)
    public List<SemesterResultDto> getGuardianChildrenResults(
            Long guardianId,
            Long semesterId
    ) {

        return studentGuardianRepository
                .findByGuardianId(guardianId)
                .stream()
                .map(StudentGuardian::getStudent)
                .flatMap(student ->
                        semesterResultRepository
                                .findByStudentIdAndSemesterId(
                                        student.getId(),
                                        semesterId
                                )
                                .stream()
                )
                .map(SemesterResultMapper::toDto)
                .toList();
    }
}