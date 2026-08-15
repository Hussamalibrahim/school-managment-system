package com.SchoolManagementSystem.system.service.academic.impl;

import com.SchoolManagementSystem.system.dto.academic.SubjectDto;
import com.SchoolManagementSystem.system.dto.academic.TeacherSubjectDto;
import com.SchoolManagementSystem.system.dto.user.TeacherDto;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.mapper.academic.SubjectMapper;
import com.SchoolManagementSystem.system.mapper.academic.TeacherSubjectMapper;
import com.SchoolManagementSystem.system.entity.academic.Subject;
import com.SchoolManagementSystem.system.entity.academic.TeacherSubject;
import com.SchoolManagementSystem.system.entity.user.Teacher;
import com.SchoolManagementSystem.system.mapper.user.TeacherMapper;
import com.SchoolManagementSystem.system.repository.academic.SubjectRepository;
import com.SchoolManagementSystem.system.repository.academic.TeacherSubjectRepository;
import com.SchoolManagementSystem.system.repository.user.TeacherRepository;
import com.SchoolManagementSystem.system.service.academic.TeacherSubjectService;
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


    @Transactional
    public TeacherSubjectDto connectTeacherToSubject(Long teacherId, Long subjectId)
    {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() ->
                        new NotFoundException(ErrorCode.TEACHER_NOT_FOUND));

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() ->
                        new NotFoundException(ErrorCode.SUBJECT_NOT_FOUND));

        TeacherSubject ts = new TeacherSubject();
        ts.setTeacher(teacher);
        ts.setSubject(subject);

        ts = teacherSubjectRepository.save(ts);

        return TeacherSubjectMapper.toDto(ts);
    }

    @Override
    public List<SubjectDto> getSubjectByTeacherId(Long teacherId) {

        if (!teacherRepository.existsById(teacherId)) {
            throw new NotFoundException(ErrorCode.TEACHER_NOT_FOUND);
        }

        return teacherSubjectRepository.findByTeacher_Id(teacherId)
                .stream()
                .map(TeacherSubject::getSubject)
                .map(SubjectMapper::toDto)
                .toList();
    }

    @Override
    public List<TeacherDto> getTeacherBySubjectId(Long subjectId) {

        if (!subjectRepository.existsById(subjectId)) {
            throw new NotFoundException(ErrorCode.SUBJECT_NOT_FOUND);
        }

        return teacherSubjectRepository.findBySubject_Id(subjectId)
                .stream()
                .map(TeacherSubject::getTeacher)
                .map(TeacherMapper::toDto)
                .toList();
    }
}