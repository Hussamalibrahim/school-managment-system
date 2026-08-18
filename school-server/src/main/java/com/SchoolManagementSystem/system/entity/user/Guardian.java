package com.SchoolManagementSystem.system.entity.user;

import com.SchoolManagementSystem.system.entity.finance.Donation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "guardians")
@Getter
@Setter
@NoArgsConstructor
public class Guardian extends BaseUser
{
    private String occupation;

    @OneToMany(mappedBy = "guardian")
    private List<Donation> donations;
}