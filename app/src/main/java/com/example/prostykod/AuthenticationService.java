package com.example.prostykod;

import org.apache.commons.codec.digest.DigestUtils;

public class AuthenticationService {

    private static AuthenticationService authenticationService;

    public static AuthenticationService getInstance() {
        if (authenticationService == null) {
            authenticationService = new AuthenticationService();
        }
        return authenticationService;
    }

    private final String username;
    private String encryptedPassword;
    private boolean isAuthenticated = false;

    private AuthenticationService() {
        this.username = "admin";
        this.encryptedPassword = "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918";
    }

    public void authenticate(String username, String password) {
        this.isAuthenticated = this.username.equals(username) && this.encryptedPassword.equals(DigestUtils.sha256Hex(password));
    }

    public void changePassword(String newPassword) {
        this.encryptedPassword = DigestUtils.sha256Hex(newPassword);
    }

    public void logout() {
        this.isAuthenticated = false;
    }

    public boolean getIsAuthenticated() {
        return isAuthenticated;
    }
}
