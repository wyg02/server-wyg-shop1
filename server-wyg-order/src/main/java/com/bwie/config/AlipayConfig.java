package com.bwie.config;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @Author Sun
 * @Version 1.0
 * @description: TODO
 * @date 2022/12/16 19:43:58
 */

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2021000122606111";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQDILAM6cbV8N26Xmvx37yl/w1UQ1v8e+fuXcMP5sZNOCsBP63P2equXTZDMU9IkwZozljxMeEuiL3Md7E5/XIUyl15KV74zvaM3T7FXFIoS1B4w4GHheYkdkyBWKddBKwDpcYPXHaXNA/YEyDzfIGqAB1aV3UkNkyGP7a68P/560utEHaY6ZoH1+sPDSPKg9FaeBnKfFpW++bp9qTFb5faLW+wbRtAaorWzMzq+cLotkfqdseeSmrGB9KsOP6wl+LI0Dr7JEKlyRPUQDKgrC9JxQ+vONe76HV7/9OFTL5o7hv1p4tylYrobz8jKpdAHKdAvU4n5gU+RGvNCDa0L6PlFAgMBAAECggEBAJcNV3ZP/mL5NZRkGWar7IYEMfoeM7D9dZTnPIv9K6q1xD/K7twt37SyjLUT/71K94E8Os597J0770PCYc4MVFtEDJxPMDG9RiBkG/AZiMQCm7HIcTSD0SunLby3oD8GI48F4TaAofBer9UWAZFi1ds9LmKy9QCwaaBB/R0DsZaeorNqH2do6gkuHqu3VmBKmzAJ/3zygB076oYkZYW6Ezgt54x4F+dfWZTSooQqQD3k83qbrIxOOsLsN3VRVdOASxNrRzk43jWsENP2Ma+6TWW1i9Q/af5k25dN/EXORETaNz8F/z5ZGIbV01atk+pHA279fWI2ts1YsrLJ4zh0dJUCgYEA9XflYP/NVaCQuzaIShXg7VjUo9lZV7zdguQRgHNkrbUzE1ASecLHDXo3WTEFDwAQuEla3z1zM/h1iUkwWkB9Sc4tZKH2isuXOOF/DLp9RpUbeSx30UT6ShyAj/pw5sgEgVj0/8JrOB5pYJsTFhvg8DGzW9FJXc3NVsTPE3R1b6MCgYEA0MKaWVt05P3f+Upit1bjINP9izcXtH95AX2Srpf+Nmw2i4n1HZ4XJmWidjcUPJ0g2KZetVgluuR3mSado8XSi3Ly7DvmgjDfR/3hDuEmNa7Owh89E7Ey1A7hrqcbpgCTODEO1dRPhJlfymU/dzDIoBpxeLRNaTK9FdfAUjJU4fcCgYEA1uEAASusehq218vi/sesQNmUO2KW3UFNv7kawRFCvvVyIqPjqIcjbN7h+Mf6n6j0+f3s+KN4Z7RGX8mb9EWj56n+/qrxgYq7dGWhrGjMJa99f1O4B4+UcrAEswUPNFc/6mMPBZ0R8uxcXt8hlCcPf/RiCCxrRxxTdnOOun8mFnUCgYEAq1T7zpij8Ih3+KZXLHdwA3JY5Zf6qZXxHwsLhqWR6uKXRaCc/ojQISBK3NDJmBGxcn3gvt6wYJm/mn85476o1PArdTyYK4OmzPQNcOPSMz7bzDBNNUs6FxZ7nJbjtzNDoFuK/G26MW6bUoYUN2NfVR8+v7zix5Lv4uxmmE9coPMCgYEAijBHZ23ky8xMDMeLNgAwyTalFmQQ7gCfr93F6rd8sVbxkp/2KdkFV0xyq/G7PhrRea1l7qN4DKlPQkJHhfOlPPxoEmlWk/zHc5LCVO93pkZl6cu+dzzW88hFV6OkBFK2gxTCpvFQd+dJBXZ/UOQ6HMPG2Tsq4QFsBLHAl/34Ygg=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjuX/yVJwJsuGbF3i88088h0V65o0jjltn/Fj5qlq2plLi5nkOUc4NbMysFOu4jiFK6CMMhMwa02uCYMZpBggXIqUpAE3ilpvXhi0EgCKUAjF7KehIX4AP5B8KtKC+CBTIKR3tJQhTRD3UDvdlx29PUumphuI/sIn5V3FbLIfxgepMkggJUfQQob6S2JzR7ohAdrutnQcD67SeO8JxzSJ9ExtWYS3SFZxgWtfB9ub4xbLv5vI7+DqYKSF/kRtZE8Mjn3ILi+uczRsvlTbu514J5HSzSlyV9aKTtHDnTNnDJuLqQjJog3k8JZNA6jV/8X5VS2dBCY/z73eS7ikdI3xyQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//    public static String return_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";
    public static String return_url = "http://localhost:9013/order/getResult";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}