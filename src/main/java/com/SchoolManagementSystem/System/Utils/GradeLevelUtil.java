package com.SchoolManagementSystem.System.Utils;

import com.SchoolManagementSystem.System.entity.enumeration.EducationStage;
import com.SchoolManagementSystem.System.entity.enumeration.GradeLevel;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public final class GradeLevelUtil {


    public static List<GradeLevel> getByStages(Set<EducationStage> stages) {
        return Arrays.stream(GradeLevel.values())
                .filter(g -> stages.contains(g.getLevel()))
                .toList();
    }
}
