package com.SchoolManagementSystem.system.service.auth;

import com.SchoolManagementSystem.system.dto.auth.request.AdminLoginRequest;
import com.SchoolManagementSystem.system.dto.auth.response.AdminAuthResponse;

public interface AdminAuthService {
     AdminAuthResponse login(AdminLoginRequest request);
}
