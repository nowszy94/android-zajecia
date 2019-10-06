package com.example.prostykod;

public class AuthenticationService {

    private static AuthenticationService authenticationService;

    public static AuthenticationService getInstance() {
        if (authenticationService == null) {
            authenticationService = new AuthenticationService();
        }
        return authenticationService;
    }

    private final String username;
    private String password;
    private boolean isAuthenticated = false;

    private AuthenticationService() {
        this.username = "admin";
        this.password = "admin";
    }

    public void authenticate(String username, String password) {
        this.isAuthenticated = this.username.equals(username) && this.password.equals(password);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void logout() {
        this.isAuthenticated = false;
    }

    public boolean getIsAuthenticated() {
        return isAuthenticated;
    }
}
