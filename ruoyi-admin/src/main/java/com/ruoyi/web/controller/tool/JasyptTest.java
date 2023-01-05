package com.ruoyi.web.controller.tool;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.Test;

public class JasyptTest {
    @Test
    public void testEncrypt() throws Exception {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        // 加密的密钥
        standardPBEStringEncryptor.setPassword("jgck");
        String encryptedText = standardPBEStringEncryptor.encrypt("qwer6666");
        System.out.println(encryptedText);
    }

    @Test
    public void testDe() throws Exception {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setPassword("jgck");
        String encryptedText = "vcf9IA4sDgHERKdkMlpI6Gl+xtWK7loC";
        String plainText = standardPBEStringEncryptor.decrypt(encryptedText);
        System.out.println(plainText);
    }
}
