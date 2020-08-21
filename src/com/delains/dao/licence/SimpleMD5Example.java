package com.delains.dao.licence;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

public class SimpleMD5Example {
    public static void main(String[] args){

        String phone = "0771348205";
        LocalDate date= LocalDate.now();
        String dateStr = phone + "2018-03-01";
        String passwordToHash = "2018-03-18";
        String generatedPassword = null;
        String generatedPassword2 = null;

        try {
            //Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            MessageDigest md2 = MessageDigest.getInstance("MD5");

            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            md2.update(dateStr.getBytes());

            //Get the hash's byte
            byte[] bytes = md.digest();
            byte[] bytes2 = md2.digest();

            //This bytes[] has bytes in decimal format;

            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();

            StringBuilder sb2 = new StringBuilder();

            for (int i = 0; i<bytes.length; i++){

                sb.append(Integer.toString((bytes[i]&0xff) + 0*100,16 ).substring(1));
                sb2.append(Integer.toString((bytes2[i]&0xff) + 0*100,16 ).substring(1));

            }

            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
            generatedPassword2 = sb2.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

}
}