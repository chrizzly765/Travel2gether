package de.traveltogether.servercommunication;

import android.util.Base64;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Created by Anna-Lena on 29.05.2016.
 */
public class HashFactory {
    private static Random RANDOM = new SecureRandom();
    private static int iterations = 10000;
    private static int keyLength = 256;

    public static String getNextSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return convertByteToString(salt);
    }

    public static String hashPassword(char[] password, String salt) {

        try {
            String s = "";
            for (char c:password) {
                s+= c;

            }
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] saltBytes = salt.getBytes();
            PBEKeySpec spec = new PBEKeySpec(password, saltBytes, iterations, keyLength);
            SecretKey key = skf.generateSecret(spec);
            byte[] res = key.getEncoded();
            String str = convertByteToString(res);
            return str.replace("\u003d", "").replace("\\n", "").replace("\\u003d", "");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static String convertByteToString(byte[] b) {
        String s = "";
        try {
            String str = Base64.encodeToString(b, 0);
            return str;//new String(b);
        } catch (Exception e) {

        }
        return "";
    }
}
//        for (int i = 0; i < b.length; i++) {
//            //s += Byte.toString(b[i]);
//            s+= new String(new byte[]{b[i]}, "Cp1252");
//        }
//        return s;



