package com.SchoolManagementSystem.system.security.service;


import com.SchoolManagementSystem.system.entity.AuthUser;
import com.SchoolManagementSystem.system.entity.school.School;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.business.ValidationException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.repository.school.SchoolRepository;
import com.SchoolManagementSystem.system.security.AuthUserRepository;
import com.SchoolManagementSystem.system.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AuthUserRepository repo;
    private final SchoolRepository schoolRepository;
    private final AuthUserRepository authUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        throw new ValidationException(ErrorCode.USE_TENANT_LOGIN);
    }

    public UserDetails loadUserByUsernameAndSchool(
            String email,
            String schoolCode) {


        School school = schoolRepository.findByCode(schoolCode)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.SCHOOL_NOT_FOUND));


        AuthUser user = authUserRepository.findByEmailAndSchoolId(email, school.getId())
                        .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        if (!Boolean.TRUE.equals(user.getEnabled())) {
            throw new ValidationException(ErrorCode.ACCOUNT_NOT_ACTIVATED_YET);
        }

        return new UserPrincipal(user);
    }
    public UserDetails loadUserByUsernameAndSchool(
            String email,
            Long schoolId) {

        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SCHOOL_NOT_FOUND));


        AuthUser user = authUserRepository.findByEmailAndSchoolId(email, school.getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        if (!Boolean.TRUE.equals(user.getEnabled())) {
            throw new ValidationException(ErrorCode.ACCOUNT_NOT_ACTIVATED_YET);
        }

        return new UserPrincipal(user);
    }

    public UserDetails loadByRefId(Long id) {
        AuthUser user = authUserRepository.findAuthUserByRefId(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        if (!Boolean.TRUE.equals(user.getEnabled())) {
            throw new ValidationException(ErrorCode.ACCOUNT_NOT_ACTIVATED_YET);
        }

        return new UserPrincipal(user);
    }
}

