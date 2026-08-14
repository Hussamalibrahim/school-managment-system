package com.SchoolManagementSystem.System.tenant;

import com.SchoolManagementSystem.System.entity.SchoolEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Setter;

public class TenantEntityListener {
    @Setter
    private static TenantSchoolProvider provider;


    @PrePersist
    public void beforeCreate(Object entity){
        if(entity instanceof SchoolEntity schoolEntity){

            if(TenantContext.getSchoolId() == null){
                return;
            }
            schoolEntity.setSchool(provider.getCurrentSchool());
        }
    }


    @PreUpdate
    public void beforeUpdate(Object entity){

        if(entity instanceof SchoolEntity schoolEntity){
            if(TenantContext.getSchoolId() == null){
                return;
            }
            schoolEntity.setSchool(provider.getCurrentSchool());
        }
    }
}