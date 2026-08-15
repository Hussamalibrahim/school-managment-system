package com.SchoolManagementSystem.System.security.service;


import com.SchoolManagementSystem.System.entity.AuthUser;
import com.SchoolManagementSystem.System.entity.school.School;
import com.SchoolManagementSystem.System.exception.business.NotFoundException;
import com.SchoolManagementSystem.System.exception.business.ValidationException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.repository.school.SchoolRepository;
import com.SchoolManagementSystem.System.security.AuthUserRepository;
import com.SchoolManagementSystem.System.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
}

