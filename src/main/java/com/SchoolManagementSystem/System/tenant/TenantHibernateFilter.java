package com.SchoolManagementSystem.System.tenant;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Component;


@Component
public class TenantHibernateFilter {

    @PersistenceContext
    private EntityManager entityManager;

    public void enable() {
        Session session =
                entityManager.unwrap(Session.class);

        session.enableFilter("schoolFilter")
                .setParameter("schoolId", TenantContext.getSchoolId());
    }

    public void disable(){

        Session session = entityManager.unwrap(Session.class);

        session.disableFilter("schoolFilter");
    }
}