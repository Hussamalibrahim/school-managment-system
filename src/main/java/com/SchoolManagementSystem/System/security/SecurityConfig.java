package com.SchoolManagementSystem.System.security;

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


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/auth/login").permitAll()
                                .requestMatchers("/api/auth/principle-register").permitAll()
                                .requestMatchers("/api/auth/deactivate-account").hasRole("PRINCIPAL")
                                .requestMatchers("/api/auth/activate-account").hasRole("PRINCIPAL")
                                .requestMatchers("/api/principal/**").hasRole("PRINCIPAL")

                                .requestMatchers(HttpMethod.GET, "/api/files/download/**").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/files/**").authenticated()

                                .requestMatchers(HttpMethod.POST, "/api/files/upload/student").hasRole("SECRETARY")
                                .requestMatchers(HttpMethod.POST, "/api/files/upload/guardian").hasRole("SECRETARY")
                                .requestMatchers(HttpMethod.POST, "/api/files/upload/user").hasRole("PRINCIPAL")
                                .requestMatchers(HttpMethod.DELETE, "/api/files/**").hasRole("SECRETARY")
                                .requestMatchers(HttpMethod.GET, "/api/files/owner").hasRole("SECRETARY")

                                .requestMatchers(HttpMethod.POST,"/api/schedules/extra/**").hasRole("PRINCIPAL")
                                .requestMatchers(HttpMethod.POST,"/api/schedules/class/**").hasRole("PRINCIPAL")

                                .requestMatchers(HttpMethod.POST,"/api/subjects/**").hasRole("PRINCIPAL")
                                .requestMatchers(HttpMethod.PUT,"/api/subjects/**").hasRole("PRINCIPAL")
                                .requestMatchers(HttpMethod.DELETE,"/api/subjects/**").hasRole("PRINCIPAL")
                                .requestMatchers(HttpMethod.GET, "/api/subjects/search/**").hasRole("PRINCIPAL")
                                .requestMatchers(HttpMethod.GET, "/api/subjects/**").authenticated()

                                .requestMatchers(HttpMethod.POST,"/api/teacher-subjects/connect/*/*").hasRole("PRINCIPAL")

                                .requestMatchers(HttpMethod.PUT,"/api/schedules/**").hasRole("PRINCIPAL")

                                .requestMatchers(HttpMethod.GET,"/api/schedules/**").authenticated()

                                .requestMatchers(HttpMethod.GET, "/api/classes/student/**").hasRole("SECRETARY")
                                .requestMatchers(HttpMethod.GET, "/api/classes/**").hasRole("PRINCIPAL")
                                .requestMatchers(HttpMethod.POST,"/api/classes/**").hasRole("PRINCIPAL")

                                .requestMatchers(HttpMethod.GET,"/api/students/me-attendance").hasRole("STUDENT")
                                .requestMatchers(HttpMethod.GET,"/api/students/me").hasRole("STUDENT")
                                .requestMatchers(HttpMethod.GET,"/api/students/me-subject").hasRole("STUDENT")
                                .requestMatchers("/api/students/**").hasRole("SECRETARY")


                                .requestMatchers(HttpMethod.GET, "/api/teacher").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/teacher/my-students").hasAnyRole("TEACHER", "SECRETARY")
                                .requestMatchers(HttpMethod.GET, "/api/teacher/teacher/*/students").hasAnyRole("TEACHER", "SECRETARY")
                                .requestMatchers(HttpMethod.GET, "/api/teacher/my-schedule").hasRole("TEACHER")
                                .requestMatchers(HttpMethod.GET, "/api/teacher/class/*").hasRole("TEACHER")

                                .requestMatchers(HttpMethod.GET, "/api/teacher/me").hasRole("TEACHER")

                                .requestMatchers(HttpMethod.POST, "/api/student-guardian/connect/*/*").hasRole("SECRETARY")
                                .requestMatchers(HttpMethod.GET,"/api/student-guardian/student/**").hasRole("SECRETARY")


                                .requestMatchers(HttpMethod.GET,"/api/student-guardian/guardian/**").hasAnyRole("SECRETARY","GUARDIAN")
                                //only guardian can see his/her sons

                                .requestMatchers(HttpMethod.POST, "/api/guardians/**").hasRole("SECRETARY")
                                .requestMatchers(HttpMethod.PUT, "/api/guardians/**").hasRole("SECRETARY")
                                .requestMatchers(HttpMethod.DELETE, "/api/guardians/**").hasRole("SECRETARY")
                                .requestMatchers(HttpMethod.GET,"/api/guardians/me").hasRole("GUARDIAN")
                                .requestMatchers(HttpMethod.GET, "/api/guardians/**").hasRole("SECRETARY")

                                .requestMatchers("/api/secretary/**").hasRole("SECRETARY")

                                .requestMatchers(HttpMethod.GET,"/api/attendance/**").hasRole("SECRETARY")
                                .requestMatchers(HttpMethod.PUT,"/api/attendance/**").hasRole("SECRETARY")
                                .requestMatchers(HttpMethod.POST,"/api/attendance/**").hasRole("SECRETARY")
                                .requestMatchers(HttpMethod.DELETE,"/api/attendance/**").hasRole("SECRETARY")


                                .requestMatchers(HttpMethod.GET, "/api/school/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/school/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/school/**").hasRole("PRINCIPAL")
//                      .requestMatchers(HttpMethod.DELETE, "/api/school/**").hasRole("PRINCIPAL")
                                .anyRequest().authenticated()
                )
                .sessionManagement(s ->
                        s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
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