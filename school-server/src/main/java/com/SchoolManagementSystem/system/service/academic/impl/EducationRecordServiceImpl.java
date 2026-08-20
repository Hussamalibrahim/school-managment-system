package com.SchoolManagementSystem.system.service.academic.impl;

import com.SchoolManagementSystem.system.dto.academic.EducationRecordDto;
import com.SchoolManagementSystem.system.dto.academic.StudentEducationHistoryDto;
import com.SchoolManagementSystem.system.dto.academic.response.*;
import com.SchoolManagementSystem.system.entity.academic.*;
import com.SchoolManagementSystem.system.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.system.entity.enumeration.SemesterName;
import com.SchoolManagementSystem.system.entity.school.AcademicYear;
import com.SchoolManagementSystem.system.entity.student.Student;
import com.SchoolManagementSystem.system.entity.student.StudentGuardian;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.business.ValidationException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.mapper.academic.EducationRecordMapper;
import com.SchoolManagementSystem.system.repository.academic.*;
import com.SchoolManagementSystem.system.repository.school.AcademicYearRepository;
import com.SchoolManagementSystem.system.repository.student.AttendanceRepository;
import com.SchoolManagementSystem.system.repository.student.StudentGuardianRepository;
import com.SchoolManagementSystem.system.repository.student.StudentRepository;
import com.SchoolManagementSystem.system.service.academic.EducationRecordService;
import com.SchoolManagementSystem.system.utils.GradeLevelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EducationRecordServiceImpl implements EducationRecordService {

    private final EducationRecordRepository educationRecordRepository;
    private final StudentRepository studentRepository;
    private final AcademicYearRepository academicYearRepository;

    private final AssessmentResultRepository assessmentResultRepository;
    private final StudentGuardianRepository studentGuardianRepository;
    private final AssessmentRepository assessmentRepository;

    private final AttendanceRepository attendanceRepository;

    private final ExamRepository examRepository;
    private final ExamResultRepository examResultRepository;

    private final SemesterRepository semesterRepository;
    private final SchoolClassRepository schoolClassRepository;


    @Override
    public EducationRecordDto getById(Long id) {

        EducationRecord record =
                educationRecordRepository.findById(id)
                        .orElseThrow(() ->
                                new NotFoundException(
                                        ErrorCode.EDUCATION_RECORD_NOT_FOUND
                                ));

        return EducationRecordMapper.toDto(record);
    }


    @Override
    public List<EducationRecordDto> getStudentRecords(
            Long studentId
    ) {

        if (!studentRepository.existsById(studentId)) {
            throw new NotFoundException(
                    ErrorCode.STUDENT_NOT_FOUND
            );
        }

        return educationRecordRepository
                .findByStudentIdOrderByAcademicYearStartDateDesc(
                        studentId
                )
                .stream()
                .map(EducationRecordMapper::toDto)
                .toList();
    }


    @Override
    public List<EducationRecordDto> getPassedStudents(
            Long academicYearId
    ) {

        validateAcademicYear(academicYearId);

        return educationRecordRepository
                .findByAcademicYearIdAndPassedTrueOrderByFinalAverageDesc(
                        academicYearId
                )
                .stream()
                .map(EducationRecordMapper::toDto)
                .toList();
    }


    @Override
    public List<EducationRecordDto> getFailedStudents(
            Long academicYearId
    ) {

        validateAcademicYear(academicYearId);

        return educationRecordRepository
                .findByAcademicYearIdAndPassedFalseOrderByFinalAverageDesc(
                        academicYearId
                )
                .stream()
                .map(EducationRecordMapper::toDto)
                .toList();
    }


    @Override
    public AcademicYearStatisticsDto getAcademicYearStatistics(
            Long academicYearId
    ) {

        AcademicYear academicYear =
                validateAcademicYear(academicYearId);

        List<EducationRecord> records =
                educationRecordRepository
                        .findByAcademicYearIdOrderByFinalAverageDesc(
                                academicYearId
                        );

        long total = records.size();

        long passed =
                records.stream()
                        .filter(record ->
                                Boolean.TRUE.equals(
                                        record.getPassed()
                                ))
                        .count();

        long failed =
                records.stream()
                        .filter(record ->
                                Boolean.FALSE.equals(
                                        record.getPassed()
                                ))
                        .count();

        double average =
                records.stream()
                        .map(EducationRecord::getFinalAverage)
                        .filter(Objects::nonNull)
                        .mapToDouble(Double::doubleValue)
                        .average()
                        .orElse(0.0);

        double successRate =
                total == 0
                        ? 0
                        : (passed * 100.0) / total;

        double failureRate =
                total == 0
                        ? 0
                        : (failed * 100.0) / total;

        return new AcademicYearStatisticsDto(
                academicYear.getId(),
                academicYear.getName(),
                total,
                passed,
                failed,
                average,
                successRate,
                failureRate
        );
    }


    @Override
    public List<TopStudentDto> getTopStudents(
            Long academicYearId,
            int limit
    ) {

        validateAcademicYear(academicYearId);

        if (limit <= 0) {
            limit = 7;
        }

        limit = Math.min(limit, 100);

        List<EducationRecord> records =
                educationRecordRepository
                        .findByAcademicYearIdOrderByFinalAverageDesc(
                                academicYearId
                        );

        List<EducationRecord> topRecords =
                records.stream()
                        .filter(record ->
                                record.getFinalAverage() != null
                        )
                        .limit(limit)
                        .toList();

        List<TopStudentDto> result =
                new ArrayList<>();

        int rank = 1;

        for (EducationRecord record : topRecords) {

            Student student =
                    record.getStudent();

            result.add(
                    new TopStudentDto(
                            rank++,
                            student.getId(),
                            student.getFirstName()
                                    + " "
                                    + student.getLastName(),
                            record.getFinalAverage()
                    )
            );
        }

        return result;
    }


    private AcademicYear validateAcademicYear(
            Long academicYearId
    ) {

        return academicYearRepository
                .findById(academicYearId)
                .orElseThrow(() ->
                        new NotFoundException(
                                ErrorCode.ACADEMIC_YEAR_NOT_FOUNT
                        ));
    }
    @Override
    @Transactional
    public void generateEducationRecords(
            Long academicYearId
    ) {

        AcademicYear academicYear =
                validateAcademicYear(academicYearId);

        List<Student> students =
                studentRepository.findAll();

        for (Student student : students) {

            if (student.getStudentSchoolClass() == null) {
                continue;
            }

            EducationRecord record =
                    educationRecordRepository
                            .findByStudentIdAndAcademicYearId(
                                    student.getId(),
                                    academicYearId
                            )
                            .orElseGet(() -> {

                                EducationRecord newRecord =
                                        new EducationRecord();

                                newRecord.setStudent(student);

                                newRecord.setAcademicYear(
                                        academicYear
                                );

                                /*
                                 * IMPORTANT:
                                 * Save the student's class at
                                 * the time of this academic year.
                                 */
                                newRecord.setSchoolClass(
                                        student.getStudentSchoolClass()
                                );

                                newRecord.setGradeLevel(
                                        student.getGradeLevel()
                                );

                                newRecord.setRegisteredNextYear(
                                        false
                                );

                                return newRecord;
                            });


            StudentAnnualResult result =
                    calculateStudentAnnualResult(
                            student.getId(),
                            academicYearId
                    );


            if (result == null) {
                continue;
            }


            int absenceDays =
                    calculateAbsenceDays(
                            student,
                            academicYear
                    );


            record.setFinalAverage(
                    result.average()
            );

            record.setAbsenceDays(
                    absenceDays
            );


            /*
             * Student fails if he has more than
             * 3 failed subjects.
             *
             * 0,1,2,3 -> passed
             * 4+      -> failed
             */
            record.setPassed(
                    result.failedSubjects() <= 3
            );


            educationRecordRepository.save(record);
        }
    }


    private StudentAnnualResult calculateStudentAnnualResult(
            Long studentId,
            Long academicYearId
    ) {

        Set<Long> subjectIds =
                new HashSet<>();


        assessmentResultRepository
                .findByStudentIdAndAcademicYearId(
                        studentId,
                        academicYearId
                )
                .forEach(result ->
                        subjectIds.add(
                                result.getAssessment()
                                        .getClassSchedule()
                                        .getSubject()
                                        .getId()
                        )
                );


        examResultRepository
                .findByStudentIdAndAcademicYearId(
                        studentId,
                        academicYearId
                )
                .forEach(result ->
                        subjectIds.add(
                                result.getExam()
                                        .getSubject()
                                        .getId()
                        )
                );


        if (subjectIds.isEmpty()) {
            return null;
        }


        List<Double> subjectAverages =
                new ArrayList<>();

        int failedSubjects = 0;


        for (Long subjectId : subjectIds) {

            Double subjectAverage =
                    calculateSubjectAnnualAverage(
                            studentId,
                            subjectId,
                            academicYearId
                    );


            if (subjectAverage == null) {
                continue;
            }


            subjectAverages.add(
                    subjectAverage
            );


            if (subjectAverage < 50) {
                failedSubjects++;
            }
        }


        if (subjectAverages.isEmpty()) {
            return null;
        }


        Double finalAverage =
                subjectAverages.stream()
                        .mapToDouble(Double::doubleValue)
                        .average()
                        .orElse(0.0);


        return new StudentAnnualResult(
                finalAverage,
                failedSubjects
        );
    }


    private Double calculateSubjectAnnualAverage(
            Long studentId,
            Long subjectId,
            Long academicYearId
    ) {

        Semester first =
                semesterRepository
                        .findByAcademicYearIdAndSemesterName(
                                academicYearId,
                                SemesterName.FIRST
                        )
                        .orElseThrow(() ->
                                new NotFoundException(
                                        ErrorCode.SEMESTER_NOT_FOUND
                                ));


        Semester second =
                semesterRepository
                        .findByAcademicYearIdAndSemesterName(
                                academicYearId,
                                SemesterName.SECOND
                        )
                        .orElseThrow(() ->
                                new NotFoundException(
                                        ErrorCode.SEMESTER_NOT_FOUND
                                ));


        Double firstAverage =
                calculateSubjectSemesterAverage(
                        studentId,
                        subjectId,
                        first.getId()
                );


        Double secondAverage =
                calculateSubjectSemesterAverage(
                        studentId,
                        subjectId,
                        second.getId()
                );


        if (firstAverage == null &&
                secondAverage == null) {

            return null;
        }


        if (firstAverage == null) {
            return secondAverage;
        }


        if (secondAverage == null) {
            return firstAverage;
        }


        return (
                firstAverage + secondAverage
        ) / 2;
    }


    private Double calculateSubjectSemesterAverage(
            Long studentId,
            Long subjectId,
            Long semesterId
    ) {

        List<AssessmentResult> assessments =
                assessmentResultRepository
                        .findByStudentAndSemesterAndSubject(
                                studentId,
                                semesterId,
                                subjectId
                        );


        List<ExamResult> exams =
                examResultRepository
                        .findByStudentAndSemesterAndSubject(
                                studentId,
                                semesterId,
                                subjectId
                        );


        boolean hasAssessments =
                !assessments.isEmpty();

        boolean hasExams =
                !exams.isEmpty();


        if (!hasAssessments && !hasExams) {
            return null;
        }


        double assessmentAverage =
                assessments.stream()
                        .mapToDouble(
                                AssessmentResult::getScore
                        )
                        .average()
                        .orElse(0);


        double examAverage =
                exams.stream()
                        .mapToDouble(
                                ExamResult::getScore
                        )
                        .average()
                        .orElse(0);


        /*
         * Current grading rule:
         *
         * Assessment = 30%
         * Exam       = 70%
         */
        if (hasAssessments && hasExams) {

            return assessmentAverage * 0.30
                    + examAverage * 0.70;
        }


        return hasAssessments
                ? assessmentAverage
                : examAverage;
    }


    private int calculateAbsenceDays(
            Student student,
            AcademicYear academicYear
    ) {

        return (int)
                attendanceRepository.countAbsentDays(
                        student.getId(),
                        academicYear.getStartDate(),
                        academicYear.getEndDate()
                );
    }
    @Override
    @Transactional
    public void promoteStudents(
            Long academicYearId
    ) {

        validateAcademicYear(academicYearId);


        List<EducationRecord> records =
                educationRecordRepository
                        .findByAcademicYearId(
                                academicYearId
                        );


        for (EducationRecord record : records) {

            /*
             * Failed students stay in their
             * current class.
             */
            if (!Boolean.TRUE.equals(
                    record.getPassed()
            )) {
                continue;
            }


            Student student =
                    record.getStudent();


            SchoolClass targetClass =
                    findNextClass(
                            record.getSchoolClass()
                    );


            /*
             * No next grade in this school.
             *
             * Example:
             * Grade 12 -> no Grade 13
             */
            if (targetClass == null) {

                record.setRegisteredNextYear(
                        false
                );

                continue;
            }


            student.setStudentSchoolClass(
                    targetClass
            );

            student.setGradeLevel(
                    targetClass.getGradeLevel()
            );


            studentRepository.save(student);


            record.setRegisteredNextYear(
                    true
            );
        }
    }


    private SchoolClass findNextClass(
            SchoolClass currentClass
    ) {

        if (currentClass == null ||
                currentClass.getGradeLevel() == null) {

            return null;
        }


        GradeLevel nextGrade =
                GradeLevelUtil.next(
                        currentClass.getGradeLevel()
                );


        if (nextGrade == null) {
            return null;
        }


        /*
         * First choice:
         * same section in the next grade.
         */
        Optional<SchoolClass> sameSection =
                schoolClassRepository
                        .findByGradeLevelAndSection(
                                nextGrade,
                                currentClass.getSection()
                        );


        if (sameSection.isPresent()) {

            SchoolClass target =
                    sameSection.get();

            if (hasCapacity(target)) {
                return target;
            }
        }


        /*
         * Same section doesn't exist
         * or is full.
         *
         * Choose the class with the
         * smallest number of students.
         */
        return schoolClassRepository
                .findByGradeLevel(nextGrade)
                .stream()
                .filter(this::hasCapacity)
                .min(
                        Comparator.comparingInt(
                                c -> c.getStudents() == null
                                        ? 0
                                        : c.getStudents().size()
                        )
                )
                .orElse(null);
    }


    private boolean hasCapacity(
            SchoolClass schoolClass
    ) {

        if (schoolClass.getCapacity() == null) {
            return true;
        }


        int currentStudents =
                schoolClass.getStudents() == null
                        ? 0
                        : schoolClass.getStudents().size();


        return currentStudents <
                schoolClass.getCapacity();
    }
    @Override
    @Transactional
    public void registerNextYear(
            Long educationRecordId,
            Long targetClassId
    ) {

        EducationRecord record =
                educationRecordRepository
                        .findById(educationRecordId)
                        .orElseThrow(() ->
                                new NotFoundException(
                                        ErrorCode.EDUCATION_RECORD_NOT_FOUND
                                ));


        if (!Boolean.TRUE.equals(
                record.getPassed()
        )) {

            throw new ValidationException(
                    ErrorCode.STUDENT_NOT_PASSED
            );
        }


        SchoolClass targetClass =
                schoolClassRepository
                        .findById(targetClassId)
                        .orElseThrow(() ->
                                new NotFoundException(
                                        ErrorCode.SCHOOL_CLASS_NOT_FOUND
                                ));


        if (!hasCapacity(targetClass)) {

            throw new ValidationException(ErrorCode.SCHOOL_CLASS_FULL);
        }


        Student student =
                record.getStudent();


        student.setStudentSchoolClass(
                targetClass
        );

        student.setGradeLevel(
                targetClass.getGradeLevel()
        );


        studentRepository.save(student);


        record.setRegisteredNextYear(
                true
        );
    }
    @Override
    public List<StudentEducationHistoryDto> getStudentHistory(
            Long studentId
    ) {

        if (!studentRepository.existsById(studentId)) {

            throw new NotFoundException(
                    ErrorCode.STUDENT_NOT_FOUND
            );
        }


        return educationRecordRepository
                .findByStudentIdOrderByAcademicYearStartDateDesc(
                        studentId
                )
                .stream()
                .map(
                        EducationRecordMapper::toHistoryDto
                )
                .toList();
    }
    @Override
    public Double getAcademicYearAverage(
            Long academicYearId
    ) {

        validateAcademicYear(
                academicYearId
        );


        List<EducationRecord> records =
                educationRecordRepository
                        .findByAcademicYearId(
                                academicYearId
                        );


        return records.stream()
                .map(EducationRecord::getFinalAverage)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }
    @Override
    public StudentAcademicStatisticsDto getStudentStatistics(
            Long studentId,
            Long academicYearId
    ) {

        Student student =
                studentRepository.findById(studentId)
                        .orElseThrow(() ->
                                new NotFoundException(
                                        ErrorCode.STUDENT_NOT_FOUND
                                ));


        validateAcademicYear(
                academicYearId
        );


        Set<Long> subjectIds =
                new HashSet<>();


        assessmentResultRepository
                .findByStudentId(studentId)
                .stream()
                .filter(result ->
                        result.getAssessment()
                                .getSemester()
                                .getAcademicYear()
                                .getId()
                                .equals(academicYearId)
                )
                .forEach(result ->
                        subjectIds.add(
                                result.getAssessment()
                                        .getClassSchedule()
                                        .getSubject()
                                        .getId()
                        )
                );


        examResultRepository
                .findByStudentId(studentId)
                .stream()
                .filter(result ->
                        result.getExam()
                                .getSemester()
                                .getAcademicYear()
                                .getId()
                                .equals(academicYearId)
                )
                .forEach(result ->
                        subjectIds.add(
                                result.getExam()
                                        .getSubject()
                                        .getId()
                        )
                );


        List<SubjectAcademicStatisticsDto> subjects =
                new ArrayList<>();


        for (Long subjectId : subjectIds) {

            Subject subject =
                    findSubject(
                            studentId,
                            subjectId
                    );


            Double average =
                    calculateSubjectAnnualAverage(
                            studentId,
                            subjectId,
                            academicYearId
                    );


            if (average == null) {
                continue;
            }


            boolean passed =
                    average >= 50;


            subjects.add(
                    new SubjectAcademicStatisticsDto(
                            subject.getId(),
                            subject.getName(),
                            average,
                            passed
                    )
            );
        }


        long passedSubjects =
                subjects.stream()
                        .filter(
                                SubjectAcademicStatisticsDto::passed
                        )
                        .count();


        long failedSubjects =
                subjects.size()
                        - passedSubjects;


        double average =
                subjects.stream()
                        .mapToDouble(
                                SubjectAcademicStatisticsDto::average
                        )
                        .average()
                        .orElse(0);


        return new StudentAcademicStatisticsDto(
                student.getId(),
                student.getFirstName()
                        + " "
                        + student.getLastName(),
                academicYearId,
                average,
                passedSubjects,
                failedSubjects,
                subjects
        );
    }


    private Subject findSubject(
            Long studentId,
            Long subjectId
    ) {

        Optional<Subject> fromAssessment =
                assessmentResultRepository
                        .findByStudentId(studentId)
                        .stream()
                        .filter(result ->
                                result.getAssessment()
                                        .getClassSchedule()
                                        .getSubject()
                                        .getId()
                                        .equals(subjectId)
                        )
                        .map(result ->
                                result.getAssessment()
                                        .getClassSchedule()
                                        .getSubject()
                        )
                        .findFirst();


        if (fromAssessment.isPresent()) {
            return fromAssessment.get();
        }


        return examResultRepository
                .findByStudentId(studentId)
                .stream()
                .filter(result ->
                        result.getExam()
                                .getSubject()
                                .getId()
                                .equals(subjectId)
                )
                .map(result ->
                        result.getExam()
                                .getSubject()
                )
                .findFirst()
                .orElseThrow(() ->
                        new NotFoundException(
                                ErrorCode.SUBJECT_NOT_FOUND
                        ));
    }
    @Override
    public List<SubjectStatisticsDto> getSubjectStatistics(
            Long academicYearId
    ) {

        validateAcademicYear(
                academicYearId
        );


        Set<Subject> subjects =
                new HashSet<>();


        subjects.addAll(
                assessmentRepository
                        .findSubjectsByAcademicYearId(
                                academicYearId
                        )
        );


        subjects.addAll(
                examRepository
                        .findSubjectsByAcademicYearId(
                                academicYearId
                        )
        );


        List<SubjectStatisticsDto> result =
                new ArrayList<>();


        List<Student> students =
                studentRepository.findAll();


        for (Subject subject : subjects) {

            List<Double> averages =
                    new ArrayList<>();


            for (Student student : students) {

                Double average =
                        calculateSubjectAnnualAverage(
                                student.getId(),
                                subject.getId(),
                                academicYearId
                        );


                if (average != null) {
                    averages.add(average);
                }
            }


            if (averages.isEmpty()) {
                continue;
            }


            long passed =
                    averages.stream()
                            .filter(avg -> avg >= 50)
                            .count();


            long failed =
                    averages.size() - passed;


            double average =
                    averages.stream()
                            .mapToDouble(
                                    Double::doubleValue
                            )
                            .average()
                            .orElse(0);


            double successRate =
                    (passed * 100.0)
                            / averages.size();


            result.add(
                    new SubjectStatisticsDto(
                            subject.getId(),
                            subject.getName(),
                            average,
                            passed,
                            failed,
                            successRate
                    )
            );
        }


        return result;
    }


    private record StudentAnnualResult(
            Double average,
            int failedSubjects
    ) {
    }
    @Override
    public StudentYearStatisticsDto getStudentYearStatistics(
            Long studentId,
            Long academicYearId
    ) {

        Student student =
                studentRepository.findById(studentId)
                        .orElseThrow(() ->
                                new NotFoundException(
                                        ErrorCode.STUDENT_NOT_FOUND
                                ));

        validateAcademicYear(academicYearId);

        EducationRecord record =
                educationRecordRepository
                        .findByStudentIdAndAcademicYearId(
                                studentId,
                                academicYearId
                        )
                        .orElseThrow(() ->
                                new NotFoundException(
                                        ErrorCode.EDUCATION_RECORD_NOT_FOUND
                                ));

        return new StudentYearStatisticsDto(
                student.getId(),
                student.getFirstName() + " " + student.getLastName(),
                academicYearId,
                record.getFinalAverage(),
                countPassedSubjects(
                        studentId,
                        academicYearId
                ),
                countFailedSubjects(
                        studentId,
                        academicYearId
                )
        );
    }
    private int countPassedSubjects(
            Long studentId,
            Long academicYearId
    ) {

        return getStudentSubjectAverages(
                studentId,
                academicYearId
        ).stream()
                .filter(average -> average >= 50)
                .toList()
                .size();
    }


    private int countFailedSubjects(
            Long studentId,
            Long academicYearId
    ) {

        return getStudentSubjectAverages(
                studentId,
                academicYearId
        ).stream()
                .filter(average -> average < 50)
                .toList()
                .size();
    }
    private List<Double> getStudentSubjectAverages(
            Long studentId,
            Long academicYearId
    ) {

        Set<Long> subjectIds = new HashSet<>();

        assessmentResultRepository
                .findByStudentId(studentId)
                .stream()
                .filter(result ->
                        result.getAssessment()
                                .getSemester()
                                .getAcademicYear()
                                .getId()
                                .equals(academicYearId)
                )
                .forEach(result ->
                        subjectIds.add(
                                result.getAssessment()
                                        .getClassSchedule()
                                        .getSubject()
                                        .getId()
                        )
                );

        examResultRepository
                .findByStudentId(studentId)
                .stream()
                .filter(result ->
                        result.getExam()
                                .getSemester()
                                .getAcademicYear()
                                .getId()
                                .equals(academicYearId)
                )
                .forEach(result ->
                        subjectIds.add(
                                result.getExam()
                                        .getSubject()
                                        .getId()
                        )
                );

        List<Double> averages = new ArrayList<>();

        for (Long subjectId : subjectIds) {

            Double average =
                    calculateSubjectAnnualAverage(
                            studentId,
                            subjectId,
                            academicYearId
                    );

            if (average != null) {
                averages.add(average);
            }
        }

        return averages;
    }
    @Override
    @Transactional(readOnly = true)
    public List<StudentEducationHistoryDto> getGuardianChildrenHistory(
            Long guardianId
    ) {

        List<StudentGuardian> relations =
                studentGuardianRepository.findByGuardianId(
                        guardianId
                );

        return relations.stream()
                .map(StudentGuardian::getStudent)
                .flatMap(student ->
                        educationRecordRepository
                                .findByStudentIdOrderByAcademicYearStartDateDesc(
                                        student.getId()
                                )
                                .stream()
                )
                .map(EducationRecordMapper::toHistoryDto)
                .toList();
    }
}