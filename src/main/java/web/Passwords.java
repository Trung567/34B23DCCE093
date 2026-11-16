package web;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

public final class Passwords {
    private Passwords() {}
    public static boolean verify(String password,String encoded) {
        try {
            String[] pieces=encoded.split("\\$");
            if(pieces.length!=4 || !pieces[0].equals("pbkdf2")) return false;
            byte[] salt=Base64.getDecoder().decode(pieces[2]);
            byte[] expected=Base64.getDecoder().decode(pieces[3]);
            PBEKeySpec spec=new PBEKeySpec(password.toCharArray(),salt,Integer.parseInt(pieces[1]),expected.length*8);
            byte[] actual=SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
            spec.clearPassword(); return MessageDigest.isEqual(actual,expected);
        } catch(Exception e) { return false; }
    }
}
