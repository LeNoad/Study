package algorithm;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class MD5BruteForce {
    public static String Crypto(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(str.getBytes("UTF-8"));

            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            String hash_text = sb.toString();
            return hash_text;

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void combo(int max, char[] chr, String data, String hashed) {
        if (data.length() == max) {
            if (hashed.trim().equals(Crypto(data)) | hashed.trim().equals(Crypto(data).toUpperCase())) {
                System.out.println("Cracked : " + data);
                System.exit(1);
            }
        } else {
            for (int i = 0; i < chr.length; i++) {
                String old_data = data;
                System.out.println(old_data);
                data += chr[i];
                combo(max, chr, data, hashed);
                data = old_data;
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the hash : ");
        String hashed = sc.nextLine();
        System.out.println("Length : ");
        int len = sc.nextInt();
        sc.close();
        char[] wdlist = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
                't', 'u', 'v', 'w', 'x', 'z', 'y',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
                'V', 'W', 'X', 'Z', 'Y', '/', '.',
                ';', '"', ']', '[', '+', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', ':', '|', ',', '=', '-', '_',
                '!', '@', '#', '$', '%', '^',
                '&', '*', '(', ')', '~', '`', 92, 39 };

        combo(len, wdlist, "", hashed);
    }
}
