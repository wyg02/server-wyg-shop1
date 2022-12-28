package com.bwie.utils;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 军哥
 * @version 1.0
 * @description: 腾讯直播工具类
 * @date 2022/8/26 18:25
 */

public class TxLiveUtils {
    public static void main(String[] args) {
        System.out.println(getSafeUrl("txrtmp", "11212122", 1469762325L));
    }

    private static final char[] DIGITS_LOWER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /*
     * KEY+ streamName + txTime
     */
    private static String getSafeUrl(String key, String streamName, long txTime) {
        String input = new StringBuilder().
                append(key).
                append(streamName).
                append(Long.toHexString(txTime).toUpperCase()).toString();

        String txSecret = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            txSecret  = byteArrayToHexString(
                    messageDigest.digest(input.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return txSecret == null ? "" :
                new StringBuilder().
                        append("txSecret=").
                        append(txSecret).
                        append("&").
                        append("txTime=").
                        append(Long.toHexString(txTime).toUpperCase()).
                        toString();
    }

    private static String byteArrayToHexString(byte[] data) {
        char[] out = new char[data.length << 1];

        for (int i = 0, j = 0; i < data.length; i++) {
            out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0x0F & data[i]];
        }
        return new String(out);
    }

    /**
     * @description 生成推流地址
     * @author 军哥
     * @date 2022/8/26 18:31
     * @version 1.0
     */

    public static String makePushUrl(String streamName, Integer minutes) {

        Long lTime = System.currentTimeMillis()/1000L + 60*minutes;

        // webrtc://live.shenmazong.com/live/36301?txSecret=134321418f9cfc673c9bfc141c0ecf03&txTime=6309C85A
        String safeUrl = getSafeUrl("58a6b8cd7e90ac348a9768387d82222f", streamName, lTime);
        String url = "webrtc://live.shenmazong.com/live/" + streamName + "?" + safeUrl;

        return url;
    }

    public static String makePushUrl(String streamName, Date closeTime) {
        Long lTime = closeTime.getTime() / 1000;

        // webrtc://live.shenmazong.com/live/36301?txSecret=134321418f9cfc673c9bfc141c0ecf03&txTime=6309C85A
        String safeUrl = getSafeUrl("58a6b8cd7e90ac348a9768387d82222f", streamName, lTime);
        String url = "webrtc://live.shenmazong.com/live/" + streamName + "?" + safeUrl;

        return url;
    }

    /**
     * @description 获取直播地址
     * @author 军哥
     * @date 2022/8/27 14:22
     * @version 1.0
     */
    public static String makePlayUrl(String roomNo) {
        // http://play.shenmazong.com/live/32301.m3u8
        return "http://play.shenmazong.com/live/" + roomNo + ".m3u8";
    }
}
