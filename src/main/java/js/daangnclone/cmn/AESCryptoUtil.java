package js.daangnclone.cmn;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class AESCryptoUtil {

    private AESCryptoUtil() {
    }

    /**
     * 키반환
     */
    public static SecretKey getKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        return keyGenerator.generateKey();
    }

    /**
     * 초기화 벡터 반환
     */
    public static IvParameterSpec getIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static String encrypt(String target) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, AESCryptoUtil.getKey(), AESCryptoUtil.getIv());
        byte[] encrypted = cipher.doFinal(target.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.getEncoder().encode(encrypted));
    }

    public static String decrypt(String encryptedTarget) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, AESCryptoUtil.getKey(), AESCryptoUtil.getIv());
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedTarget));
        return new String(decrypted, StandardCharsets.UTF_8);
    }
}
