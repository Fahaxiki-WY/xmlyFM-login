package com.xunmeng.xmlyloginsimulation;


import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

// 这个类是用来做数据的加密
public class AlgorithmUtils {

    // MD5算法
    public static String MD5encrypt(String arg) throws Exception {
        String input = arg;
        // 算法
        String algorithm = "MD5";
        // 获取数字摘要对象
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        // 消息数字摘要
        byte[] digest = messageDigest.digest(input.getBytes());

        // 创建对象用来拼接
        String result = "";

        for (byte b : digest) {
            // 转成 16进制
            String temp = Integer.toHexString(b & 0xff);
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            result += temp;
        }
        System.out.println("MD5加密后：" + result);
        return result;
    }

    // RSA算法
    public static String RSADemo(String arg1) throws Exception {
        String key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCVhaR3Or7suUlwHUl2Ly36uVmboZ3+HhovogDjLgRE9CbaUokS2eqGaVFfbxAUxFThNDuXq/fBD+SdUgppmcZrIw4HMMP4AtE2qJJQH/KxPWmbXH7Lv+9CisNtPYOlvWJ/GHRqf9x3TBKjjeJ2CjuVxlPBDX63+Ecil2JR9klVawIDAQAB";
        // 获取密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        // 构建密钥规范 进行Base64解码
        X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.decode(key, 0));
        // 生成公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(spec);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        // 进行加密
        byte[] data = arg1.getBytes("utf-8");
        System.out.println("加密后数据的大小" + data.length);
        // 自己的一些处理：分段加密
        ByteArrayOutputStream bop = new ByteArrayOutputStream();

        byte[] bytes1 = cipher.doFinal(data, 0, 117);
        bop.write(bytes1);
        byte[] bytes2 = cipher.doFinal(data, 117, 46);
        bop.write(bytes2);

        String result = Base64.encodeToString(bop.toByteArray(), 0);
        return result;
    }

}
