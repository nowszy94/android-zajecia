package com.example.prostykod;
import java.security.MessageDigest;
import java.security.SecureRandom;

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

    private SecretKeySpec hashKey(String password) throws Exception {
        byte[] keyBytes = new byte[16];
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(password.getBytes());
        System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
        return new SecretKeySpec(keyBytes, "AES");
    }

    public byte[] encrypt(String message, String password) {

        try {
            // Wektor inicjalizacyjny.
            int ivSize = 16;
            byte[] iv = new byte[ivSize];
            new SecureRandom().nextBytes(iv);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            // Hashowanie klucza
            SecretKeySpec secretKeySpec = hashKey(password);

            // Szyfrowanie
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(message.getBytes());

            // Scalenie wektora inicjalizacyjnego z zaszyfrowana wiadomoscia
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

        try {
            // Wyciągnięcie wektora inicjalizacyjny.
            int ivSize = 16;
            byte[] iv = new byte[ivSize];
            System.arraycopy(data, 0, iv, 0, iv.length);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            int encryptedSize = data.length - ivSize;
            byte[] encryptedBytes = new byte[encryptedSize];
            System.arraycopy(data, ivSize, encryptedBytes, 0, encryptedSize);

            // Hash key.
            SecretKeySpec secretKeySpec = hashKey(password);

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
}
