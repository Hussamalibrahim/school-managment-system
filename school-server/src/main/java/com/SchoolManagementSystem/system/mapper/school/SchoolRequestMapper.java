package com.SchoolManagementSystem.system.mapper.school;

import com.SchoolManagementSystem.system.dto.school.SchoolRequestDto;
import com.SchoolManagementSystem.system.entity.school.School;
import com.SchoolManagementSystem.system.entity.school.SchoolRequest;

public final class SchoolRequestMapper {
    private SchoolRequestMapper(){}

    public static SchoolRequestDto toDto(SchoolRequest request) {

        School school = request.getSchool();

        return new SchoolRequestDto(
                request.getId(),
                school.getId(),
                school.getName(),
                school.getCode(),
                school.getSchoolType(),
                school.getEducationStages(),
                request.getStatus(),
                request.getRejectionReason(),
                request.getReviewedAt()
        );
    }
}
