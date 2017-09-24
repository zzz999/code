package com.htsec.security;


import com.htsec.commons.utils.CodeHelper;
import net.sf.json.JSONObject;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64Encoder;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;

import static org.hsqldb.HsqlDateTime.e;

/**
 *
 * @author ngh
 * AES128 算法
 *
 * CBC 模式
 *
 * PKCS7Padding 填充模式
 *
 * CBC模式需要添加一个参数iv
 *
 * 介于java 不支持PKCS7Padding，只支持PKCS5Padding 但是PKCS7Padding 和 PKCS5Padding 没有什么区别
 * 要实现在java端用PKCS7Padding填充，需要用到bouncycastle组件来实现
 */

public class AESUtil {
        // 算法名称
        final String KEY_ALGORITHM = "AES";
        // 加解密算法/模式/填充方式
        final String algorithmStr = "AES/CBC/PKCS7Padding";
        private static final String KEY="Bernard1";

        //

        private SecretKeySpec key;
        private Cipher cipher;
        boolean isInited = false;
        byte[] iv = { 0x30, 0x31, 0x30, 0x32, 0x30, 0x33, 0x30, 0x34, 0x30, 0x35, 0x30, 0x36, 0x30, 0x37, 0x30, 0x38 };

        public void init(byte[] keyBytes) {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            if(isInited==true){
                return;
            }
            int base = 16;
            if (keyBytes.length % base != 0) {
                int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
                keyBytes = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            // 转化成JAVA的密钥格式
            key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
            try {
                // 初始化cipher
                cipher = Cipher.getInstance(algorithmStr, "BC");
                isInited =true;
            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        /**
         * 加密方法
         *
         * @param content
         *            要加密的字符串
         * @return
         */
        public synchronized String encrypt(String content) throws UnsupportedEncodingException {
            byte[] encryptedText = null;
            init(KEY.getBytes("UTF-8"));
          //  System.out.println("IV：" + new String(iv));
            try {
                cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
                encryptedText = cipher.doFinal(content.getBytes("UTF-8"));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return Base64encode(encryptedText);
        }

    /**
     * base64加密
     */
    public static String Base64encode(byte[] str) throws UnsupportedEncodingException {
       Base64.Encoder encoder = Base64.getEncoder();
       return encoder.encodeToString(str);
    }

    /** 
     * base64 解码
     * @param str 
     * @return 
     */

    public static byte[] Base64decode(String str) throws UnsupportedEncodingException {
        Base64.Decoder decoder=Base64.getDecoder();
        return  decoder.decode(str);
    }

     /**
      * 解密方法
      *
      * @param encryptedData
      *            要解密的字符串

      * @return
      */

     public  synchronized String decrypt(String encryptedData) throws UnsupportedEncodingException {
            byte[] encryptedText = null;
          init(KEY.getBytes("UTF-8"));
           // System.out.println("IV：" + new String(iv));
            try {
                cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
                encryptedText = cipher.doFinal(Base64decode(encryptedData));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return new String(encryptedText,"UTF-8");
     }

    public static void main(String[] args) throws UnsupportedEncodingException {
        AESUtil aes = new AESUtil();
//   加解密 密钥
        String key ="Bernard1";
        //byte[] keybytes = { 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38 };
        byte[] keybytes = new byte[0];
        try {
            keybytes = key.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JSONObject json = new JSONObject();
        json.put("account","40119420");
        json.put("range","week");
        String content = json.toString();
        // 加密字符串
      System.out.println("加密前的：" + content);
       // System.out.println("加密密钥：" + new String(keybytes));
        // 加密方法
        long a =System.currentTimeMillis();
        for (int i =0;i<1;i++){
            String enc = aes.encrypt(content);
            System.out.println("加密后的内容：" + enc);
            System.out.println(CodeHelper.encode(enc));
            // 解密方法
           String dec = aes.decrypt(enc);
            System.out.println("解密后的内容：" + dec);
        }
        System.out.println((1000000+0.0)/(System.currentTimeMillis()-a));

    }



}