package com.example.prostykod;

import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordManagerTest {
    @Test
    public void test() {
        PasswordManager passwordManager = new PasswordManager();

        assertEquals("1111111111111111", passwordManager.preparePassword("1111111111111111"));
        assertEquals("1111111111111111", passwordManager.preparePassword("11111111111111112222"));
        assertEquals("password11111111", passwordManager.preparePassword("password"));
    }

}