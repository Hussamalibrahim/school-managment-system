package com.SchoolManagementSystem.System.security;

import com.SchoolManagementSystem.System.exception.security.JwtAuthenticationEntryPoint;
import com.SchoolManagementSystem.System.security.jwt.JwtFilter;
import com.SchoolManagementSystem.System.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // =================== Public Endpoints ===================
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/principle-register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/school/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/school/**").permitAll()

                        // =================== Account Activation/Deactivation ===================
                        .requestMatchers("/api/auth/deactivate-account").hasRole("PRINCIPAL")
                        .requestMatchers("/api/auth/activate-account").hasRole("PRINCIPAL")

                        // =================== Principal Only ===================
                        .requestMatchers("/api/principal/**").hasRole("PRINCIPAL")
                        .requestMatchers("/api/semesters/**").hasRole("PRINCIPAL")
                        .requestMatchers(HttpMethod.PUT, "/api/school/**").hasRole("PRINCIPAL")
                        .requestMatchers(HttpMethod.POST, "/api/files/upload/user").hasRole("PRINCIPAL")

                        // =================== Files ===================
                        .requestMatchers(HttpMethod.GET, "/api/files/download/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/files/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/files/upload/student").hasAnyRole("SECRETARY", "PRINCIPAL")
                        .requestMatchers(HttpMethod.POST, "/api/files/upload/guardian").hasAnyRole("SECRETARY", "PRINCIPAL")
                        .requestMatchers(HttpMethod.DELETE, "/api/files/**").hasAnyRole("SECRETARY", "PRINCIPAL")
                        .requestMatchers(HttpMethod.GET, "/api/files/owner").hasAnyRole("SECRETARY", "PRINCIPAL")

                        // =================== Schedules ===================
                        .requestMatchers(HttpMethod.GET, "/api/schedules/my-schedule").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/schedules/teacher/**").hasAnyRole("TEACHER", "PRINCIPAL", "SECRETARY")
                        .requestMatchers(HttpMethod.GET, "/api/schedules/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/schedules/**").hasRole("PRINCIPAL")
                        .requestMatchers(HttpMethod.PUT, "/api/schedules/**").hasRole("PRINCIPAL")
                        .requestMatchers(HttpMethod.DELETE, "/api/schedules/**").hasRole("PRINCIPAL")

                        // =================== Classes & Subjects ===================
                        .requestMatchers(HttpMethod.GET, "/api/classes/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/classes/**").hasAnyRole("PRINCIPAL", "SECRETARY")
                        .requestMatchers(HttpMethod.PUT, "/api/classes/**").hasAnyRole("PRINCIPAL", "SECRETARY")
                        .requestMatchers(HttpMethod.DELETE, "/api/classes/**").hasRole("PRINCIPAL")

                        .requestMatchers(HttpMethod.GET, "/api/subjects/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/subjects/**").hasRole("PRINCIPAL")
                        .requestMatchers(HttpMethod.PUT, "/api/subjects/**").hasRole("PRINCIPAL")
                        .requestMatchers(HttpMethod.DELETE, "/api/subjects/**").hasRole("PRINCIPAL")

                        .requestMatchers(HttpMethod.GET, "/api/teacher-subjects/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/teacher-subjects/**").hasRole("PRINCIPAL")
                        .requestMatchers(HttpMethod.DELETE, "/api/teacher-subjects/**").hasRole("PRINCIPAL")

                        // =================== Students ===================
                        .requestMatchers(HttpMethod.GET, "/api/students/me").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/students/me-subject").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/students/me-attendance").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/students/attendance/student/*/statistics").hasAnyRole("GUARDIAN", "STUDENT", "PRINCIPAL", "SECRETARY", "TEACHER")
                        .requestMatchers(HttpMethod.GET, "/api/students/attendance/guardian/**").hasRole("GUARDIAN")
                        .requestMatchers(HttpMethod.GET, "/api/students/**").hasAnyRole("SECRETARY", "PRINCIPAL", "TEACHER")
                        .requestMatchers("/api/students/**").hasAnyRole("SECRETARY", "PRINCIPAL")

                        // =================== Teachers ===================
                        .requestMatchers(HttpMethod.GET, "/api/teacher/me").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.GET, "/api/teacher/my-students").hasAnyRole("TEACHER", "SECRETARY", "PRINCIPAL")
                        .requestMatchers(HttpMethod.GET, "/api/teacher/my-schedule").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.GET, "/api/teacher/class/*").hasAnyRole("TEACHER", "PRINCIPAL", "SECRETARY")
                        .requestMatchers(HttpMethod.GET, "/api/teacher/**").authenticated()

                        // =================== Guardians & Relations ===================
                        .requestMatchers(HttpMethod.GET, "/api/guardians/me").hasRole("GUARDIAN")
                        .requestMatchers(HttpMethod.GET, "/api/student-guardian/guardian/me").hasRole("GUARDIAN")
                        .requestMatchers(HttpMethod.GET, "/api/student-guardian/guardian/**").hasAnyRole("SECRETARY", "GUARDIAN", "PRINCIPAL")
                        .requestMatchers(HttpMethod.GET, "/api/student-guardian/**").hasAnyRole("SECRETARY", "PRINCIPAL")
                        .requestMatchers("/api/student-guardian/**").hasAnyRole("SECRETARY", "PRINCIPAL")
                        .requestMatchers(HttpMethod.GET, "/api/guardians/**").hasAnyRole("SECRETARY", "PRINCIPAL")
                        .requestMatchers("/api/guardians/**").hasAnyRole("SECRETARY", "PRINCIPAL")

                        // =================== Secretary ===================
                        .requestMatchers("/api/secretary/**").hasAnyRole("SECRETARY", "PRINCIPAL")

                        // =================== Attendance ===================
                        .requestMatchers(HttpMethod.GET, "/api/attendance/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/attendance/**").hasAnyRole("SECRETARY", "TEACHER", "PRINCIPAL")
                        .requestMatchers(HttpMethod.PUT, "/api/attendance/**").hasAnyRole("SECRETARY", "TEACHER", "PRINCIPAL")
                        .requestMatchers(HttpMethod.DELETE, "/api/attendance/**").hasAnyRole("SECRETARY", "PRINCIPAL")

                        // =================== Assessments ===================
                        .requestMatchers(HttpMethod.GET, "/api/assessments/me").hasAnyRole("PRINCIPAL", "TEACHER")
                        .requestMatchers(HttpMethod.GET, "/api/assessments/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/assessments/**").hasAnyRole("PRINCIPAL", "TEACHER")
                        .requestMatchers(HttpMethod.PUT, "/api/assessments/**").hasAnyRole("PRINCIPAL", "TEACHER")
                        .requestMatchers(HttpMethod.DELETE, "/api/assessments/**").hasRole("PRINCIPAL")

                        // =================== Assessment Results ===================
                        .requestMatchers(HttpMethod.GET, "/api/assessment-results/me").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/assessment-results/guardian/**").hasRole("GUARDIAN")
                        .requestMatchers(HttpMethod.GET, "/api/assessment-results/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/assessment-results/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.PUT, "/api/assessment-results/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.DELETE, "/api/assessment-results/**").hasRole("PRINCIPAL")

                        // =================== Exams ===================
                        .requestMatchers(HttpMethod.GET, "/api/exams/my-class").hasAnyRole("STUDENT", "GUARDIAN")
                        .requestMatchers(HttpMethod.GET, "/api/exams/teacher").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.GET, "/api/exams/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/exams/**").hasRole("PRINCIPAL")
                        .requestMatchers(HttpMethod.PUT, "/api/exams/**").hasRole("PRINCIPAL")
                        .requestMatchers(HttpMethod.DELETE, "/api/exams/**").hasRole("PRINCIPAL")

                        // =================== Exam Results ===================
                        .requestMatchers(HttpMethod.GET, "/api/exam-results/me").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/exam-results/guardian/**").hasRole("GUARDIAN")
                        .requestMatchers(HttpMethod.GET, "/api/exam-results/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/exam-results/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.PUT, "/api/exam-results/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.DELETE, "/api/exam-results/**").hasAnyRole("PRINCIPAL", "TEACHER")

                        // =================== Warnings ===================
                        .requestMatchers(HttpMethod.GET, "/api/warnings/me").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/warnings/guardian/**").hasRole("GUARDIAN")
                        .requestMatchers(HttpMethod.GET, "/api/warnings/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/warnings/**").hasAnyRole("PRINCIPAL", "SECRETARY", "TEACHER")
                        .requestMatchers(HttpMethod.PUT, "/api/warnings/**").hasAnyRole("PRINCIPAL", "SECRETARY", "TEACHER")
                        .requestMatchers(HttpMethod.DELETE, "/api/warnings/**").hasAnyRole("PRINCIPAL", "SECRETARY")

                        // =================== Finance ===================
                        .requestMatchers(HttpMethod.GET, "/api/payments/me").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/payments/guardian/**").hasRole("GUARDIAN")
                        .requestMatchers(HttpMethod.GET, "/api/payments/**").hasAnyRole("SECRETARY", "PRINCIPAL")
                        .requestMatchers(HttpMethod.POST, "/api/payments/**").hasAnyRole("SECRETARY", "PRINCIPAL")
                        .requestMatchers(HttpMethod.PUT, "/api/payments/**").hasAnyRole("SECRETARY", "PRINCIPAL")
                        .requestMatchers(HttpMethod.DELETE, "/api/payments/**").hasAnyRole("SECRETARY", "PRINCIPAL")

                        .requestMatchers(HttpMethod.GET, "/api/class-fees/**", "/api/fee-types/**", "/api/discounts/**", "/api/student-discounts/**").authenticated()
                        .requestMatchers("/api/class-fees/**", "/api/fee-types/**", "/api/discounts/**", "/api/student-discounts/**").hasAnyRole("SECRETARY", "PRINCIPAL")

                        // =================== Library ===================
                        .requestMatchers(HttpMethod.GET, "/api/book/**", "/api/borrowed-book/**", "/api/library/**").authenticated()
                        .requestMatchers("/api/book/**", "/api/borrowed-book/**", "/api/library/**").hasAnyRole("LIBRARIAN", "PRINCIPAL", "SECRETARY")

                        // =================== Announcements & Notifications ===================
                        .requestMatchers(HttpMethod.GET, "/api/announcements/**", "/api/notifications/**").authenticated()
                        .requestMatchers("/api/announcements/**", "/api/notifications/**").hasAnyRole("PRINCIPAL", "SECRETARY", "TEACHER")

                        // =================== Fallback ===================
                        .anyRequest().authenticated()
                )
                .sessionManagement(s ->
                        s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).exceptionHandling(ex ->
                        ex.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}