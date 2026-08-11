package com.SchoolManagementSystem.System.dto.academic.respones;

public record AssessmentResultRepones(Long id,

                                      Long studentId,
                                      String studentName,
                                      Long assessmentId,

                                      Double score) {
}
