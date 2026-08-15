package com.SchoolManagementSystem.system.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "librarians")
@Getter
@Setter
@NoArgsConstructor
public class Librarian extends BaseUser
{
}