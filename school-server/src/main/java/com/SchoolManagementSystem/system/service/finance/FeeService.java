package com.SchoolManagementSystem.system.service.finance;

import com.SchoolManagementSystem.system.entity.enumeration.FeeStatus;
import com.SchoolManagementSystem.system.entity.finance.Fee;
import com.SchoolManagementSystem.system.entity.finance.FeeStructure;
import com.SchoolManagementSystem.system.entity.student.Student;
import com.SchoolManagementSystem.system.service.CrudService;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface FeeService {
    void createFeesForStudent(Student student);
    void applyFeeStructureToStudents(FeeStructure feeStructure);

}
