package com.xunmeng.xmlyloginsimulation;


import java.util.Map;

public class Constant {
    public static final String GET_URL = "http://mobile.ximalaya.com/passport/token/login";
    public static final String POST_URL = "http://mobile.ximalaya.com/passport-sign-mobile/v3/signin/password";
    public static final String COOKIE2 = "$version=1";
    public static final String ACCEPT = "*/*";
    public static final String USER_AGENT = "ting_6.5.48(Nexus+5,Android19)";
    public static final String HOST = "mobile.ximalaya.com";
    public static final String ACCEPT_ENCODING = "gzip";
    public static final String CONNECTION = "keep-alive";
    public static final String CONTENT_TYPE = "application/json; charset=utf-8";
    public static final String CONTENT_LENGTH = "349";
    public static final String XD1 = "56khveko2sX8eSRdnXBz7WSB1kJgShAx1wQdJuNPDb8tpGwi87rjCFJyjWkc7TWTzTGyCkpSEq4LF1Oiuizf9hMBxhFyxPytycYjzERZN/fkhl7kJTqEq1i+fmqMnVeRQX6Rtc8CgMqYv78d370qaJypQx7J+Kb+So0he0z+h3I=";
    public static final String XD2 = "56khveko2sX8eSRdnXBz7WSB1kJgShAx1wQdJuNPDb8tpGwi87rjCFJyjWkc7TWTzTGyCkpSEq4LF1Oiuizf9hMBxhFyxPytycYjzERZN/fkhl7kJTqEq1i+fmqMnVeRQX6Rtc8CgMqYv78d370qaLPTtg3c9EZpLDRDtKXjVk8=";
    public static boolean en = false;

    public static String Cookie() {
        StringBuilder strb = new StringBuilder();
        strb.append("1&_device" + "=" + "android&036cb05f-0a52-3f82-9810-c26afa5147c3&6.5.48" + ";");
        strb.append("channel" + "=" + "and-a1" + ";");
        strb.append("impl" + "=" + "com.ximalaya.ting.android" + ";");
        strb.append("osversion" + "=" + "19" + ";");
        strb.append("device_model" + "=" + "Nexus+5" + ";");
        strb.append("XUM" + "=" + "iMnQ5VSO" + ";");
        strb.append("XIM" + "=" + "1417f51e6a842" + ";");
        strb.append("c-oper" + "=" + "%E4%B8%AD%E5%9B%BD%E7%94%B5%E4%BF%A1" + ";");
        strb.append("net-mode" + "=" + "WIFI" + ";");
        strb.append("res" + "=" + "1080%2C1776" + ";");
        strb.append("NSUP" + "=" + "42f13081%2C42017238%2C" + System.currentTimeMillis() + ";");
        strb.append("AID" + "=" + "jueedzP3ZQU=" + ";");
        strb.append("manufacturer" + "=" + "LGE" + ";");
        // 可有可无这个判断；一样的也没关系
        if (en = false) {
            strb.append("XD" + "=" + XD1 + ";");
            en = true;
        } else {
            strb.append("XD" + "=" + XD2 + ";");
        }
        strb.append("umid" + "=" + "7fea1d71d916c7c38ab561cab79d2c0" + ";");
        strb.append("xm_grade" + "=" + "0" + ";");
        strb.append("domain" + "=" + ".ximalaya.com" + ";");
        strb.append("path" + "=" + "/" + ";");

        return strb.toString();
    }

    // Signatrue数据
    public static String getSignatrue(String arg1, String arg2, String arg3) throws Exception {
        StringBuilder strsign = new StringBuilder();
        strsign.append("account=" + arg1);
        strsign.append("&channel=and-a1");
        strsign.append("&device=android");
        strsign.append("&deviceid=036cb05f-0a52-3f82-9810-c26afa5147c3");
        strsign.append("&impl=com.ximalaya.ting.android");
        strsign.append("&nonce=" + arg3);
        strsign.append("&password=" + arg2);
        strsign.append("&version=6.5.48");
        strsign.append("&23627d1451047b8d257a96af5db359538f081d651df75b4aa169508547208159");

        return AlgorithmUtils.MD5encrypt(strsign.toString());
    }

    // 只是最终body提交的数据
    public static String fomat(Map map) throws Exception {

        String result = "{\"account\":\"" + map.get("account")
                + "\",\"nonce\":\"" + map.get("nonce")
                + "\",\"password\":\"" + map.get("password")
                + "\",\"signature\":\"" + map.get("signature") + "\"}";

        System.out.println("RSA加密前的数据" + result);
        String data = AlgorithmUtils.RSADemo(result);
        System.out.println("RSA加密后的数据" + data);

        return data;
    }

}
