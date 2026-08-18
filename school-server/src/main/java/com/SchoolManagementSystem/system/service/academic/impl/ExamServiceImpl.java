package com.SchoolManagementSystem.system.service.academic.impl;

import com.SchoolManagementSystem.system.dto.academic.ExamDto;
import com.SchoolManagementSystem.system.dto.academic.request.ExamCreateRequest;
import com.SchoolManagementSystem.system.dto.academic.request.ExamUpdateRequest;
import com.SchoolManagementSystem.system.entity.academic.Exam;
import com.SchoolManagementSystem.system.entity.academic.SchoolClass;
import com.SchoolManagementSystem.system.entity.academic.Semester;
import com.SchoolManagementSystem.system.entity.academic.Subject;
import com.SchoolManagementSystem.system.entity.enumeration.Role;
import com.SchoolManagementSystem.system.entity.enumeration.SemesterName;
import com.SchoolManagementSystem.system.entity.student.Student;
import com.SchoolManagementSystem.system.entity.student.StudentGuardian;
import com.SchoolManagementSystem.system.entity.user.Teacher;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.business.ValidationException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.mapper.academic.ExamMapper;
import com.SchoolManagementSystem.system.mapper.academic.SemesterMapper;
import com.SchoolManagementSystem.system.repository.academic.ExamRepository;
import com.SchoolManagementSystem.system.repository.academic.SchoolClassRepository;
import com.SchoolManagementSystem.system.repository.academic.SubjectRepository;
import com.SchoolManagementSystem.system.repository.student.StudentGuardianRepository;
import com.SchoolManagementSystem.system.repository.student.StudentRepository;
import com.SchoolManagementSystem.system.repository.user.TeacherRepository;
import com.SchoolManagementSystem.system.security.UserPrincipal;
import com.SchoolManagementSystem.system.service.academic.ExamService;
import com.SchoolManagementSystem.system.service.academic.SemesterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {
    private final SchoolClassRepository schoolClassRepository;
    private final SubjectRepository subjectRepository;
    private final SemesterService semesterService;
    private final ExamRepository examRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final StudentGuardianRepository studentGuardianRepository;


    @Override
    @Transactional
    public ExamDto save(ExamCreateRequest request, UserPrincipal user) {

        validatePrincipal(user);

        SchoolClass schoolClass = schoolClassRepository.findById(request.schoolClassId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.CLASS_NOT_FOUND));

        Subject subject = subjectRepository.findById(request.subjectId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.SUBJECT_NOT_FOUND));

        Semester semester = SemesterMapper.toEntity(semesterService.getCurrentSemester());

        validateSubjectBelongsToClass(schoolClass, subject);


        if (examRepository.existsBySchoolClassIdAndSubjectIdAndSemesterIdAndCategory(
                schoolClass.getId(),
                subject.getId(),
                semester.getId(),
                request.category())) {
            throw new ValidationException(ErrorCode.EXAM_ALREADY_EXISTS);
        }
        validateExamTimeConflict(
                schoolClass,
                semester,
                request.examDateTime(),
                request.durationMinutes(),
                null
        );

        Exam exam = new Exam();

        ExamMapper.fromCreateRequest(exam, request);

        exam.setSchoolClass(schoolClass);
        exam.setSubject(subject);
        exam.setSemester(semester);

        return ExamMapper.toDto(examRepository.save(exam));
    }
    @Transactional
    @Override
    public ExamDto update(Long id, ExamUpdateRequest request, UserPrincipal user) {

        validatePrincipal(user);

        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EXAM_NOT_FOUND));

        SchoolClass schoolClass = schoolClassRepository.findById(request.schoolClassId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.CLASS_NOT_FOUND));

        Subject subject = subjectRepository.findById(request.subjectId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.SUBJECT_NOT_FOUND));

        validateSubjectBelongsToClass(schoolClass, subject);

        if (examRepository.existsBySchoolClassIdAndSubjectIdAndSemesterIdAndCategoryAndIdNot(
                schoolClass.getId(),
                subject.getId(),
                exam.getSemester().getId(),
                request.category(),
                exam.getId())) {

            throw new ValidationException(ErrorCode.EXAM_ALREADY_EXISTS);
        }

        validateExamTimeConflict(
                schoolClass,
                exam.getSemester(),
                request.examDateTime(), request.durationMinutes(), exam.getId());

        ExamMapper.fromUpdateRequest(exam, request);

        exam.setSchoolClass(schoolClass);
        exam.setSubject(subject);

        return ExamMapper.toDto(
                examRepository.save(exam)
        );
    }
    @Transactional
    @Override
    public void delete(Long id, UserPrincipal user) {

        validatePrincipal(user);

        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EXAM_NOT_FOUND));

        examRepository.delete(exam);
    }

    @Transactional(readOnly = true)
    @Override
    public ExamDto getById(Long id) {

        return examRepository.findById(id)
                .map(ExamMapper::toDto)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EXAM_NOT_FOUND));
    }
    @Transactional(readOnly = true)
    @Override
    public List<ExamDto> getAll() {

        return examRepository.findAll()
                .stream()
                .map(ExamMapper::toDto)
                .toList();
    }
    private void validatePrincipal(UserPrincipal user) {
        if (user.getRole() != Role.PRINCIPAL) {
            throw new ValidationException(ErrorCode.UNAUTHORIZED);
        }
    }
    private void validateSubjectBelongsToClass(SchoolClass schoolClass, Subject subject) {
        if (subject.getGradeLevel() != schoolClass.getGradeLevel()) {

            throw new ValidationException(ErrorCode.SUBJECT_NOT_BELONG_TO_CLASS);
        }
    }
    private void validateExamTimeConflict(
            SchoolClass schoolClass,
            Semester semester,
            LocalDateTime start,
            Integer durationMinutes,
            Long examId
    ) {
        LocalDateTime end = start.plusMinutes(durationMinutes);

        List<Exam> exams = (examId == null)
                ? examRepository.findBySchoolClassIdAndSemesterId(
                schoolClass.getId(),
                semester.getId())
                : examRepository.findBySchoolClassIdAndSemesterIdAndIdNot(
                schoolClass.getId(),
                semester.getId(),
                examId);

        for (Exam exam : exams) {
            LocalDateTime existingStart = exam.getExamDateTime();
            LocalDateTime existingEnd = existingStart.plusMinutes(exam.getDurationMinutes());

            if (start.isBefore(existingEnd) && end.isAfter(existingStart)) {
                throw new ValidationException(ErrorCode.EXAM_TIME_CONFLICT);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDto> getByClass(Long classId, SemesterName semesterName) {

        SchoolClass schoolClass = schoolClassRepository.findById(classId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CLASS_NOT_FOUND));

        Semester semester = SemesterMapper.toEntity(semesterService.getCurrentSemester());

        return examRepository
                .findBySchoolClassIdAndSemesterId(
                        schoolClass.getId(), semester.getId())
                .stream()
                .map(ExamMapper::toDto)
                .toList();
    }
    @Override
    @Transactional(readOnly = true)
    public List<ExamDto> getBySubject(Long subjectId, SemesterName semesterName) {

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SUBJECT_NOT_FOUND));

        Semester semester = SemesterMapper.toEntity(semesterService.getCurrentSemester());

        return examRepository
                .findBySubjectIdAndSemesterId(
                        subject.getId(),
                        semester.getId())
                .stream()
                .map(ExamMapper::toDto)
                .toList();
    }
    @Override
    @Transactional(readOnly = true)
    public List<ExamDto> getTeacherExams(Long classId, UserPrincipal user) {

        Teacher teacher = teacherRepository.findById(user.getRefId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.TEACHER_NOT_FOUND));

        SchoolClass schoolClass = schoolClassRepository.findById(classId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CLASS_NOT_FOUND));

        return examRepository.findBySchoolClassIdAndSubjectTeacherSubjectsTeacherId(
                        schoolClass.getId(),
                        teacher.getId())
                .stream()
                .map(ExamMapper::toDto)
                .toList();
    }
    @Override
    @Transactional(readOnly = true)
    public List<ExamDto> getMyClassExams(UserPrincipal user) {

        Semester semester = SemesterMapper.toEntity(semesterService.getCurrentSemester());

        if (user.getRole() == Role.STUDENT) {

            Student student = studentRepository.findById(user.getRefId())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.STUDENT_NOT_FOUND));

            return examRepository.findBySchoolClassIdAndSemesterId(
                            student.getStudentSchoolClass().getId(),
                            semester.getId())
                    .stream()
                    .map(ExamMapper::toDto)
                    .toList();
        }

        if (user.getRole() == Role.GUARDIAN) {

            List<Student> students = studentGuardianRepository
                    .findByGuardianId(user.getRefId())
                    .stream()
                    .map(StudentGuardian::getStudent)
                    .toList();

            if (students.isEmpty()) {
                throw new ValidationException(ErrorCode.GUARDIAN_HAS_NO_STUDENTS);
            }

            return students.stream()
                    .map(Student::getStudentSchoolClass)
                    .distinct()
                    .flatMap(schoolClass ->
                            examRepository.findBySchoolClassIdAndSemesterId(
                                            schoolClass.getId(),
                                            semester.getId())
                                    .stream())
                    .distinct()
                    .map(ExamMapper::toDto)
                    .toList();
        }

        throw new ValidationException(ErrorCode.UNAUTHORIZED);
    }
}
