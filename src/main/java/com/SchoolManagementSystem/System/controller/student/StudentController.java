package com.SchoolManagementSystem.System.controller.student;

import com.SchoolManagementSystem.System.controller.BaseCrudController;
import com.SchoolManagementSystem.System.dto.student.StudentDto;
import com.SchoolManagementSystem.System.service.student.StudentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController extends BaseCrudController<StudentDto>
{

    public StudentController(StudentService service) {
        super(service);
    }

    @PutMapping("/{studentId}/assign-class/{classId}")
    public StudentDto assignClass(
            @PathVariable Long studentId,
            @PathVariable Long classId)
    {
        return ((StudentService) service)
                .assignClass(studentId, classId);
    }

}