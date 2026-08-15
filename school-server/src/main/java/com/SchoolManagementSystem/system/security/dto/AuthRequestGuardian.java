package com.SchoolManagementSystem.system.security.dto;

public record AuthRequestGuardian (
      String nationalId,
    String firstName,
    String lastName,
    String phone,
    String email,
    String address,
    String status,
    String occupation){
}
