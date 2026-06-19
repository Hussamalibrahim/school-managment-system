package com.SchoolManagementSystem.System.dto.request;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;

public record CreateClassRequest (Long id,

                                  Long classId,

                                  Long subjectId,

                                  Long teacherId,

                                  DayOfWeek dayOfWeek,

                                  Integer periodNumber,

                                  LocalTime startTime,

                                  LocalTime endTime

) implements Serializable {
}
