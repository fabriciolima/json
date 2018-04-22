package main;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Testes {

	public static void main(String[] args) throws Exception {

		
		//byte[] raw = ...; // 32 bytes in size for a 256 bit key
		Key key = new javax.crypto.spec.SecretKeySpec("123456789012345678901234567890as".getBytes(), "AES/CBC/PKCS5Padding")
		
		
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encrypted = cipher.doFinal("dfgdf".getBytes());
		System.out.println(encrypted);
		
		
		

		System.out.println(Util.decrypt(encrypted.toString()));
		

	
	}

}
