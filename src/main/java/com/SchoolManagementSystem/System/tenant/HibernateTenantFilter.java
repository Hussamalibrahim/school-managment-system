package com.SchoolManagementSystem.System.tenant;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Component;


@Component
public class HibernateTenantFilter {
    @PersistenceContext
    private EntityManager entityManager;

    public void enable(){

        Long schoolId = TenantContext.getSchoolId();

        if(schoolId == null)
            return;
        Session session = entityManager.unwrap(Session.class);

        session.enableFilter("schoolFilter").setParameter("schoolId", schoolId);
    }

    public void disable(){
        Session session = entityManager.unwrap(Session.class);

        session.disableFilter("schoolFilter");
    }
}