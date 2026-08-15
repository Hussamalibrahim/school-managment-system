package com.SchoolManagementSystem.System.utils;

import com.SchoolManagementSystem.System.entity.enumeration.EducationStage;
import com.SchoolManagementSystem.System.entity.enumeration.GradeLevel;

import java.util.Arrays;
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
}
