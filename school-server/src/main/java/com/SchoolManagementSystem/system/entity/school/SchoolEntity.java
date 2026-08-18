package com.SchoolManagementSystem.system.entity.school;


import com.SchoolManagementSystem.system.entity.BaseEntity;
import com.SchoolManagementSystem.system.tenant.TenantEntityListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;



@Getter
@Setter
@MappedSuperclass
@EntityListeners(TenantEntityListener.class)
@FilterDef(
        name = "schoolFilter",
        parameters = @ParamDef(
                name="schoolId",
                type = Long.class
        )
)
@Filter(
        name="schoolFilter",
        condition="school_id = :schoolId"
)
public abstract class SchoolEntity extends BaseEntity {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="school_id",
            nullable=false
    )
    private School school;

}