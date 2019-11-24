package com.example.prostykod;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


class EncryptDecryptService {

    private static EncryptDecryptService encryptDecryptService;

    public static EncryptDecryptService getInstance() {
        if (encryptDecryptService == null) {
            encryptDecryptService = new EncryptDecryptService();
        }
        return encryptDecryptService;
    }

    private PasswordManager passwordManager;

    public EncryptDecryptService() {
        this.passwordManager = new PasswordManager();
    }

    public byte[] encrypt(String message, String password) {

        try {
            // Generating IV.
            int ivSize = 16;
            byte[] iv = new byte[ivSize];
            SecureRandom random = new SecureRandom();
            random.nextBytes(iv);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            // Hashing key.
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(password.getBytes("UTF-8"));
            byte[] keyBytes = new byte[16];
            System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

            // Encrypt.
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(message.getBytes());

            // Combine IV and encrypted part.
            byte[] encryptedIVAndText = new byte[ivSize + encrypted.length];
            System.arraycopy(iv, 0, encryptedIVAndText, 0, ivSize);
            System.arraycopy(encrypted, 0, encryptedIVAndText, ivSize, encrypted.length);

            return encryptedIVAndText;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt(byte[] data, String password) {
        int ivSize = 16;
        int keySize = 16;

        try {
            // Extract IV.
            byte[] iv = new byte[ivSize];
            System.arraycopy(data, 0, iv, 0, iv.length);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            // Extract encrypted part.
            int encryptedSize = data.length - ivSize;
            byte[] encryptedBytes = new byte[encryptedSize];
            System.arraycopy(data, ivSize, encryptedBytes, 0, encryptedSize);

            // Hash key.
            byte[] keyBytes = new byte[keySize];
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            System.arraycopy(md.digest(), 0, keyBytes, 0, keyBytes.length);
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

            // Decrypt.
            Cipher cipherDecrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decrypted = cipherDecrypt.doFinal(encryptedBytes);

            return new String(decrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static class PasswordManager {
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
}
