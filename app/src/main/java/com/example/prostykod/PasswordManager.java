package com.example.prostykod;

class PasswordManager {


    // TODO - zastosowac jakis algorytm key stretching
    public String preparePassword(String password) {
        if (password.length() == 16) {
            return password;
        }
        if (password.length() > 16) {
            return password.substring(0, 16);
        }
        return password + "1111111111111111".substring(password.length());
    }
}
