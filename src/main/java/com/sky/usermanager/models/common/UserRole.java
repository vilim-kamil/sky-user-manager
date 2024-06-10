package com.sky.usermanager.models.common;

/**
 * Basic definition of user roles - this currently only supports single role per user.
 * <p>
 * Note: DEFAULT user role is basically redundant and serves only as a default value in database.
 */
public enum UserRole {
    DEFAULT, ADMIN
}
