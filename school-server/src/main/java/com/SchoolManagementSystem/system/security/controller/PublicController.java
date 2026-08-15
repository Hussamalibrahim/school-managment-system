package com.SchoolManagementSystem.system.security.controller;



import com.SchoolManagementSystem.system.dto.school.request.SchoolRegisterRequest;
import com.SchoolManagementSystem.system.service.school.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class PublicController {

    private final SchoolService schoolService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody SchoolRegisterRequest request){

        schoolService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

}
