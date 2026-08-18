package com.SchoolManagementSystem.system.mapper.finance;

import com.SchoolManagementSystem.system.dto.finance.request.FeeStructureRequest;
import com.SchoolManagementSystem.system.dto.finance.response.FeeStructureDto;
import com.SchoolManagementSystem.system.entity.finance.FeeStructure;

public final class FeeStructureMapper {

    private FeeStructureMapper() {
    }

    public static FeeStructure toEntity(FeeStructureRequest request) {

        FeeStructure entity = new FeeStructure();

        entity.setFeeName(request.feeName());
        entity.setGradeLevel(request.gradeLevel());
        entity.setFeeType(request.feeType());
        entity.setAmount(request.amount());
        entity.setDueDate(request.dueDate());
        entity.setActive(true);

        return entity;
    }

    public static void updateEntity(FeeStructure entity, FeeStructureRequest request) {

        entity.setFeeName(request.feeName());
        entity.setGradeLevel(request.gradeLevel());
        entity.setFeeType(request.feeType());
        entity.setAmount(request.amount());
        entity.setDueDate(request.dueDate());
    }

    public static FeeStructureDto toDto(FeeStructure entity) {

        return new FeeStructureDto(
                entity.getId(),
                entity.getFeeName(),
                entity.getSemester().getId(),
                entity.getSemester().getSemesterName(),
                entity.getGradeLevel(),
                entity.getFeeType(),
                entity.getAmount(),
                entity.getDueDate(),
                entity.getActive()
        );
    }
}