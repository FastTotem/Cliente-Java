package slack;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class OauthCript {

    private static final String CHAVE_SECRETA = "ChaveSecreta1234";
    private static final String valorCriptografado = "6sdqgvcz72DZ4VrIcUkGb+08g8ifYNtROsLuXyfmwhXHgB17qPVL11+ims1A97uAQz4S+O7B7S95O/7m3DLDKw==";

    public OauthCript() {
    }

    private static SecretKey getChaveSecreta() {
        return new SecretKeySpec(CHAVE_SECRETA.getBytes(), "AES");
    }

    public static String getOauthToken() {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, getChaveSecreta());
            byte[] valorBytes = cipher.doFinal(Base64.getDecoder().decode(valorCriptografado));
            return new String(valorBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}