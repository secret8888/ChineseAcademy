package com.stroke.academy.common.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AES256 {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static String password = "ChinaStroke/2015-06-09/v1.0/Yang";

    /**
     * 根据密码加密内容
     *
     * @param content
     * @param password
     * @return
     */
    public static byte[] encrypt(String content, String password) {
        try {
            SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            final byte[] iv = new byte[16];
            Arrays.fill(iv, (byte) 0x00);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
            byte[] result = cipher.doFinal(byteContent);

            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密内容
     *
     * @param content
     * @return
     */
    public static String encrypt(String content) {
        // TODO Auto-generated method stub
        byte[] encryptResult = encrypt(content, password);
        String encryptResultStr = parseByte2HexStr(encryptResult);
        return encryptResultStr;

    }

    /**
     * 解密内容
     * @param content
     * @return
     */
    public static String decrypt(String content) {
        byte[] decryptFrom = parseHexStr2Byte(content);
        byte[] decryptResult = decrypt(decryptFrom, password);
        return new String(decryptResult);
    }

    /**
     * 根据密码解密
     * @param content
     * @param password
     * @return
     */
    public static byte[] decrypt(byte[] content, String password) {
        try {

            SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            final byte[] iv = new byte[16];
            Arrays.fill(iv, (byte) 0x00);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);

            byte[] result = cipher.doFinal(content);
            return result; // 加密  
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String parseByte2HexStr(byte buf[]) {
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

    public static byte[] parseHexStr2Byte(String hexStr) {
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

    public static void test() {
        //String content = "{\"username\":\"18618321612\", \"password\":\"coder\"}";
        String content = "{\"userId\":\"1\"}";

        System.out.println("加密前" + content);
        byte[] encryptResult = encrypt(content, password);
        String encryptResultStr = parseByte2HexStr(encryptResult);
        System.out.println("加密后" + encryptResultStr);

//        byte[] decryptFrom = parseHexStr2Byte();
//        byte[] decryptResult = decrypt(decryptFrom, password);
        System.out.println("解密后" + decrypt("7CFD230EC89D25CDC187D032B7BF2B192380E5145B200609D5BC51C411673A16372F856135F4C229D69A92AD546EC4D588F095ABC60E7269A8E3B2176DD089632BAC23744596D288DA9E3C7D2D54E02AD1DD7F217167F09460E97A3422AAA67F4050FD129E4FA273F929CEC95B3E585952A701372DAE59DBD831EE595A877BF946A0FBB6DA7F15FC57DDE0B83BD464553FFE0FBCCBF0135A6D6740AA925AF8C9D6A59AC11CC630917BA8F02FAEF3E5C64AF54E280F96B5DDC29D4FC08A33ED44F02DCC66E862215A688084E6E4A09C6344E997A1A2B25591F471F2F3F0487A8B440B8AC371358B3D419151FBEEFB21B73D068C7958516E6DBB510A3F5F092A9E4C8458EE0B98A3449AC9802C53B6EB21F2D96D4ADF69A728438F9DDE58E79BAC836D5432A7DD7590878EBF31A66ADAA6DC135973785C5B84A663F12E9A969F744F38F543AB4B59496954A23047A1F2FC901789DC8CA9FFA238DAA8C61C0D8184646823301916707192661BC9478801D9EBACF9CD432EA909E55FD921A87AE6D50FF12756C61281ACB79D3AD479AF4CCCACC28EE70DB270B6DC74C139BD50429DE3AEA8F2EAD3E635032FD6FA7110B9077C5E46DDFA7D17CF9C8290F4AF188C53E569D170D58D48B77AE372799B4708DB2D7EC4BD1138976AA3C626A20C2E096913A6B5F9961E1FB2996EC666A9DD3C7FEECD67C3D7A9D8F2CEF5E420C5ACC5B3BABB232510194B28EC62B6D6F7BB77C3999D33865E9AF1BB8ED685972376774521832A944F9F6D67EFCDE5D77A50F26F194FF30374238D56BCF4EB8BE11863C545B164A6FE1C28246ACF1D7E623D15443EEB33B096A5B6EF1167CD6EFD7847F1"));
    }
}
