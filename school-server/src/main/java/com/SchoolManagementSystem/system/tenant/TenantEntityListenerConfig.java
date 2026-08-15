package com.SchoolManagementSystem.system.tenant;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
public class TenantEntityListenerConfig {

    private final TenantSchoolProvider provider;

    @Bean
    public TenantEntityListener tenantEntityListener(){

        TenantEntityListener listener = new TenantEntityListener();

        TenantEntityListener.setProvider(provider);

        return listener;
    }

}