package com.SchoolManagementSystem.system.security;

import com.SchoolManagementSystem.system.entity.AuthUser;
import com.SchoolManagementSystem.system.entity.enumeration.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser,Long> {

    Optional<AuthUser> findAuthUserByRefIdAndRole(Long refId, Role role);

    Optional<AuthUser> findByEmail(String email);
    @Query("""
        select u
        from AuthUser u
        join fetch u.school s
        where u.email = :email
        and s.id = :schoolId
    """)
    Optional<AuthUser> findByEmailAndSchoolId(
            String email,
            Long schoolId
    );

    void deleteByRefIdAndRole(Long id, Role role);

    Optional<AuthUser> findAuthUserByRefId(Long id);
}

