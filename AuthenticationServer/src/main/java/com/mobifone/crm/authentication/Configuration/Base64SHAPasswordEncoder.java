package com.mobifone.crm.authentication.Configuration;

import org.springframework.security.crypto.password.PasswordEncoder;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;

public class Base64SHAPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        String result = "";
        try {
            result = encrypt(rawPassword.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        //sondt18_20200807_Comment check password DaoAuthenticationProvider.additionalAuthenticationChecks
        //Bypass password khi dang nhap bang account LDAP
        return true;
//        boolean result = false;
//        try {
//            result = encrypt(rawPassword.toString()).equals(encodedPassword);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
    }

    private static String encrypt(String strValue) throws Exception {
        return encrypt(strValue.getBytes(), "SHA");
    }

    private static String encrypt(byte[] btValue, String strAlgorithm) throws Exception {
        BASE64Encoder enc = new BASE64Encoder();
        MessageDigest md = MessageDigest.getInstance(strAlgorithm);
        return enc.encodeBuffer(md.digest(btValue));
    }
}
