package com.lib.fastkit.utils.rsa;

import java.security.KeyFactory;
import java.security.SecureRandom;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class RsaAndAesUtils {

    private static String PUBLIC_KEY = "305C300D06092A864886F70D0101010500034B003048024100929090148308257F4C079CEB25A2B5F268DED4718B30E3CC6FD5CFD4BAAD7732189AE706C6D78D4C31CE8917A4073EECE8EF3381D50BBF1A202FA406E2CF65AF0203010001";

//    public static void main(String[] args) {
//        //加密前：{sex=man, name=deluyi}
//        //加密后：{data=683837E48B2805F1E4C7DCC0082F04E19F73F943F4C8165621D335E3D1712B38490D334831925F28A285656EA27936E5, aes_key=6902340BB4F2DC528C3A8E3C51BFED328DFBAFDE1179AB12AD63B71536BAF6F7315FD38890B7F859FD781A8E8F025E538BA6EC76F7C4DA1AE906B125FFB06ED9}
//        System.out.println(paramsFormat("{\"requestType\":\"REG_SEND_CODE\",\"mobile\":\"13540354597\"}"));
//    }

    /**
     * aes加密
     *
     * @param content
     * @return
     */
    public static Map<String, byte[]> aesEncrypt(String content) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(new SecureRandom());
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(byteContent);
            Map<String, byte[]> dataMap = new HashMap<>();
            // 秘钥
            dataMap.put("key", key.getEncoded());
            // 加密过的数据
            dataMap.put("data", result);
            return dataMap;
        } catch (Exception e) {
            // 加密错误
            return null;
        }
    }

    /**
     * rsa加密
     *
     * @param keyBt
     * @return
     */
    public static String rsaEncrypt(byte[] keyBt) {
        try {
            RSAPublicKey publicKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(parseHexStr2Byte(PUBLIC_KEY)));
            // RSA加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return parseByte2HexStr(cipher.doFinal(keyBt));
        } catch (Exception e) {
            return null;
        }

    }

    //格式化参数
    public static Map<String, String> paramsFormat(String params) {
        //1：先用aes加密，返回aes秘钥和加密过的数据
        Map<String, byte[]> result = aesEncrypt(params);
        if (result == null) {
            return null;
        }
        //2：把aes秘钥加密
        byte[] keyBt = result.get("key");
        String aesKey = rsaEncrypt(keyBt);
        //3：把二进制数组转成16进制字符串
        String data = parseByte2HexStr(result.get("data"));
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("aes_key", aesKey);
        dataMap.put("data", data);
        return dataMap;
    }

    /**
     * 16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(final String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 将二进制转换16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(final byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

}
