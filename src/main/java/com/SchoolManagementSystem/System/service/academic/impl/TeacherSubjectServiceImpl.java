package com.SchoolManagementSystem.System.service.academic.impl;

import com.SchoolManagementSystem.System.dto.academic.TeacherSubjectDto;
import com.SchoolManagementSystem.System.dtoMapper.academic.TeacherSubjectMapper;
import com.SchoolManagementSystem.System.entity.academic.Subject;
import com.SchoolManagementSystem.System.entity.academic.TeacherSubject;
import com.SchoolManagementSystem.System.entity.user.Teacher;
import com.SchoolManagementSystem.System.repository.academic.SubjectRepository;
import com.SchoolManagementSystem.System.repository.academic.TeacherSubjectRepository;
import com.SchoolManagementSystem.System.repository.user.TeacherRepository;
import com.SchoolManagementSystem.System.service.academic.TeacherSubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TeacherSubjectServiceImpl implements TeacherSubjectService {

    private final TeacherSubjectRepository teacherSubjectRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;


    public TeacherSubjectDto connectTeacherToSubject(Long teacherId, Long subjectId)
    {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        TeacherSubject ts = new TeacherSubject();
        ts.setTeacher(teacher);
        ts.setSubject(subject);

        ts = teacherSubjectRepository.save(ts);

        return TeacherSubjectMapper.toDto(ts);
    }

    @Override
    public TeacherSubjectDto save(TeacherSubjectDto dto) {
        return null;
    }

    @Override
    public TeacherSubjectDto update(Long aLong, TeacherSubjectDto dto) {
        return null;
    }

    @Override
    public TeacherSubjectDto getById(Long aLong) {
        return null;
    }

    @Override
    public List<TeacherSubjectDto> getAll() {
        return List.of();
    }

    @Override
    public void delete(Long aLong) {

    }
}