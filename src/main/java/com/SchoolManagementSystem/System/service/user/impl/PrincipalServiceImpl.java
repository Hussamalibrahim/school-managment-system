package com.SchoolManagementSystem.System.service.user.impl;

import com.SchoolManagementSystem.System.dto.request.CreateUserRequest;
import com.SchoolManagementSystem.System.dto.user.PrincipalDto;
import com.SchoolManagementSystem.System.mapper.user.PrincipalMapper;
import com.SchoolManagementSystem.System.entity.AuthUser;
import com.SchoolManagementSystem.System.entity.user.Librarian;
import com.SchoolManagementSystem.System.entity.user.Principal;
import com.SchoolManagementSystem.System.entity.user.Secretary;
import com.SchoolManagementSystem.System.entity.user.Teacher;
import com.SchoolManagementSystem.System.repository.user.LibrarianRepository;
import com.SchoolManagementSystem.System.repository.user.PrincipalRepository;
import com.SchoolManagementSystem.System.repository.user.SecretaryRepository;
import com.SchoolManagementSystem.System.repository.user.TeacherRepository;
import com.SchoolManagementSystem.System.security.AuthUserRepository;
import com.SchoolManagementSystem.System.service.user.PrincipalService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PrincipalServiceImpl implements PrincipalService {

    private final PrincipalRepository repository;
    private final SecretaryRepository secretaryRepository;
    private final LibrarianRepository librarianRepository;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthUserRepository authUserRepository;


    @Override
    public PrincipalDto save(PrincipalDto dto) {
        Principal principal = PrincipalMapper.toEntity(dto);
        principal = repository.save(principal);
        return PrincipalMapper.toDto(principal);
    }

    @Override
    public PrincipalDto update(Long id, PrincipalDto dto) {
        Principal principal = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Principal not found"));

        principal.setNationalId(dto.nationalId());
        principal.setFirstName(dto.firstName());
        principal.setLastName(dto.lastName());
        principal.setPhone(dto.phone());
        principal.setEmail(dto.email());
        principal.setAddress(dto.address());
        principal.setStatus(dto.status());
        principal.setHireDate(dto.hireDate());

        principal = repository.save(principal);
        return PrincipalMapper.toDto(principal);
    }

    @Override
    public PrincipalDto getById(Long id) {
        return repository.findById(id)
                .map(PrincipalMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Principal not found"));
    }

    @Override
    public List<PrincipalDto> getAll() {
        return repository.findAll()
                .stream()
                .map(PrincipalMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void createStaff(CreateUserRequest request) {

        switch (request.role()) {

            case TEACHER -> createTeacher(request);
            case SECRETARY -> createSecretary(request);
            case LIBRARIAN -> createLibrarian(request);

            default -> throw new RuntimeException("Unsupported role");
        }
    }

    private void createTeacher(CreateUserRequest request) {

        Teacher teacher = new Teacher();
        teacher.setNationalId(request.nationalId());
        teacher.setFirstName(request.firstName());
        teacher.setLastName(request.lastName());
        teacher.setPhone(request.phone());
        teacher.setEmail(request.email());
        teacher.setAddress(request.address());
        teacher.setHireDate(request.hireDate());
        teacher.setSpecialization(request.specialization());

        teacher = teacherRepository.save(teacher);

        createAuthUser(request, teacher.getId());
    }

    private void createSecretary(CreateUserRequest request) {

        Secretary s = new Secretary();
        s.setNationalId(request.nationalId());
        s.setFirstName(request.firstName());
        s.setLastName(request.lastName());
        s.setPhone(request.phone());
        s.setEmail(request.email());
        s.setAddress(request.address());
        s.setHireDate(request.hireDate());

        s = secretaryRepository.save(s);

        createAuthUser(request, s.getId());
    }

    private void createLibrarian(CreateUserRequest request) {

        Librarian l = new Librarian();
        l.setNationalId(request.nationalId());
        l.setFirstName(request.firstName());
        l.setLastName(request.lastName());
        l.setPhone(request.phone());
        l.setEmail(request.email());
        l.setAddress(request.address());
        l.setHireDate(request.hireDate());

        l = librarianRepository.save(l);

        createAuthUser(request, l.getId());
    }
    private void createAuthUser(CreateUserRequest request, Long refId) {

        AuthUser authUser = new AuthUser();
        authUser.setEmail(request.email());
        authUser.setPassword(passwordEncoder.encode(request.password()));
        authUser.setRole(request.role());
        authUser.setRefId(refId);

        authUserRepository.save(authUser);
    }
}

