package com.SchoolManagementSystem.System.entity.enumeration;

import lombok.Getter;

@Getter
public enum AssessmentCategory {

    // Continuous Assessments
    HOMEWORK(AssessmentType.CONTINUOUS),
    QUIZ(AssessmentType.CONTINUOUS),
    ORAL_TEST(AssessmentType.CONTINUOUS),
    ACTIVITY(AssessmentType.CONTINUOUS),
    PROJECT(AssessmentType.CONTINUOUS),

    // Official Exams
    MONTHLY_EXAM(AssessmentType.EXAM),
    MIDTERM_EXAM(AssessmentType.EXAM),
    FINAL_EXAM(AssessmentType.EXAM),
    MAKEUP_EXAM(AssessmentType.EXAM);

    private final AssessmentType type;

    AssessmentCategory(AssessmentType type) {
        this.type = type;
    }

    public boolean isExam() {
        return type == AssessmentType.EXAM;
    }

    public boolean isContinuous() {
        return type == AssessmentType.CONTINUOUS;
    }
}
