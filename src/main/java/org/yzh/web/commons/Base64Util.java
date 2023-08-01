package org.yzh.web.commons;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64Util {
    private static final Base64.Decoder decoder = Base64.getDecoder();
    private static final Base64.Encoder encoder = Base64.getEncoder();

    public static String base64Encode(String text) {
        try {
            byte[] textByte = text.getBytes("UTF-8");
            String encodedText = encoder.encodeToString(textByte);
            //System.out.println(encodedText);
            return encodedText;
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error :" + e.getMessage());
        }
        return "error";
    }

    public static String base64Decode(String encodedText) {
        try {
            String text = new String(decoder.decode(encodedText), "UTF-8");
            //System.out.println(text);
            return text;
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error :" + e.getMessage());
        }
        return "error";
    }
}