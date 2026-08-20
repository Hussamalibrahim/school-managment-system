package com.SchoolManagementSystem.system.utils;

import com.SchoolManagementSystem.system.entity.enumeration.EducationStage;
import com.SchoolManagementSystem.system.entity.enumeration.GradeLevel;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class GradeLevelUtil {


    public static Set<GradeLevel> getByStages(Set<EducationStage> stages) {
        return Arrays.stream(GradeLevel.values())
                .filter(g ->
                        (stages.contains(EducationStage.ELEMENTARY) && g.getLevel() <= 6) ||
                                (stages.contains(EducationStage.MIDDLE) && g.getLevel() >= 7 && g.getLevel() <= 9) ||
                                (stages.contains(EducationStage.HIGH) && g.getLevel() >= 10)
                )
                .collect(Collectors.toSet());
    }
    public static GradeLevel next(GradeLevel current) {

        return switch (current) {
            case GRADE_1 -> GradeLevel.GRADE_2;
            case GRADE_2 -> GradeLevel.GRADE_3;
            case GRADE_3 -> GradeLevel.GRADE_4;
            case GRADE_4 -> GradeLevel.GRADE_5;
            case GRADE_5 -> GradeLevel.GRADE_6;
            case GRADE_6 -> GradeLevel.GRADE_7;
            case GRADE_7 -> GradeLevel.GRADE_8;
            case GRADE_8 -> GradeLevel.GRADE_9;
            case GRADE_9 -> GradeLevel.GRADE_10;
            case GRADE_10 -> GradeLevel.GRADE_11;
            case GRADE_11 -> GradeLevel.GRADE_12;
            case GRADE_12 -> GradeLevel.GRADUATE;


            default -> null;
        };
    }
}
