package com.sky.usermanager.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {

    @Value("${com.sky.usermanager.security.admin.user}")
    private String adminUser;

    @Value("${com.sky.usermanager.security.admin.password}")
    private String adminPassword;

    public String getAdminUser() {
        return adminUser;
    }

    public String getAdminPassword() {
        return adminPassword;
    }
}
