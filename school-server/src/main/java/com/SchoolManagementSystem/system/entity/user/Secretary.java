package com.SchoolManagementSystem.system.entity.user;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "secretaries")
@NoArgsConstructor
public class Secretary extends BaseUser
{
}