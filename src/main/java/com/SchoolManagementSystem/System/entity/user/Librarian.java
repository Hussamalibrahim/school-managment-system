package com.SchoolManagementSystem.System.entity.user;

import com.SchoolManagementSystem.System.entity.BaseEntity;
import com.SchoolManagementSystem.System.entity.school.School;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "librarians")
@Getter
@Setter
@NoArgsConstructor
public class Librarian extends BaseUser
{
}