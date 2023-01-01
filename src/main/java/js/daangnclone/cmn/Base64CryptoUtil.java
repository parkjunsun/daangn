package js.daangnclone.cmn;


import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Base64CryptoUtil {

    private Base64CryptoUtil() {

    }

    public static String encrypt(String target) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String targetAndNow = target + "|" + now;

        return new String(Base64.encodeBase64(targetAndNow.getBytes()));
    }

    public static String decrypt(String encryptedTarget) {
        return new String(Base64.decodeBase64(encryptedTarget.getBytes()));
    }
}
