package com.audit.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component
public class PvUtils {

	// compress the image bytes before storing it in the database
	    public static byte[] compressBytes(byte[] data) {
	        Deflater deflater = new Deflater();
	        deflater.setInput(data);
	        deflater.finish();
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
	        byte[] buffer = new byte[1024];
	        while (!deflater.finished()) {
	            int count = deflater.deflate(buffer);
	            outputStream.write(buffer, 0, count);
	        }
	        try {
	            outputStream.close();
	        } catch (IOException e) {
	        }
	        return outputStream.toByteArray();
	    }
	    // uncompress the image bytes before returning it to the angular application
	    public static byte[] decompressBytes(byte[] data) {
	        Inflater inflater = new Inflater();
	        inflater.setInput(data);
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
	        byte[] buffer = new byte[1024];
	        try {
	            while (!inflater.finished()) {
	                int count = inflater.inflate(buffer);
	                outputStream.write(buffer, 0, count);
	            }
	            outputStream.close();
	        } catch (IOException ioe) {
	        } catch (DataFormatException e) {
	        }
	        return outputStream.toByteArray();
	    }
	    
	    public static String decrypt(String encryptedText) {
	    	
	    	String secret = "zakoora";

	    	byte[] cipherData = Base64.getDecoder().decode(encryptedText);
	    	byte[] saltData = Arrays.copyOfRange(cipherData, 8, 16);

	    	MessageDigest md5 = null;
			try {
				md5 = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	final byte[][] keyAndIV = GenerateKeyAndIV(32, 16, 1, saltData, secret.getBytes(StandardCharsets.UTF_8), md5);
	    	SecretKeySpec key = new SecretKeySpec(keyAndIV[0], "AES");
	    	IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);

	    	byte[] encrypted = Arrays.copyOfRange(cipherData, 16, cipherData.length);
	    	Cipher aesCBC;
	    	byte[] decryptedData = null;
			try {
				aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
				aesCBC.init(Cipher.DECRYPT_MODE, key, iv);
				decryptedData = aesCBC.doFinal(encrypted);
			} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
				e.printStackTrace();
			}
	    	
			return  new String(decryptedData, StandardCharsets.UTF_8);
	    }

	    /**
	     * Generates a key and an initialization vector (IV) with the given salt and password.
	     * <p>
	     * This method is equivalent to OpenSSL's EVP_BytesToKey function
	     * (see https://github.com/openssl/openssl/blob/master/crypto/evp/evp_key.c).
	     * By default, OpenSSL uses a single iteration, MD5 as the algorithm and UTF-8 encoded password data.
	     * </p>
	     * @param keyLength the length of the generated key (in bytes)
	     * @param ivLength the length of the generated IV (in bytes)
	     * @param iterations the number of digestion rounds 
	     * @param salt the salt data (8 bytes of data or <code>null</code>)
	     * @param password the password data (optional)
	     * @param md the message digest algorithm to use
	     * @return an two-element array with the generated key and IV
	     */
	    public static byte[][] GenerateKeyAndIV(int keyLength, int ivLength, int iterations, byte[] salt, byte[] password, MessageDigest md) {

	        int digestLength = md.getDigestLength();
	        int requiredLength = (keyLength + ivLength + digestLength - 1) / digestLength * digestLength;
	        byte[] generatedData = new byte[requiredLength];
	        int generatedLength = 0;

	        try {
	            md.reset();

	            // Repeat process until sufficient data has been generated
	            while (generatedLength < keyLength + ivLength) {

	                // Digest data (last digest if available, password data, salt if available)
	                if (generatedLength > 0)
	                    md.update(generatedData, generatedLength - digestLength, digestLength);
	                md.update(password);
	                if (salt != null)
	                    md.update(salt, 0, 8);
	                md.digest(generatedData, generatedLength, digestLength);

	                // additional rounds
	                for (int i = 1; i < iterations; i++) {
	                    md.update(generatedData, generatedLength, digestLength);
	                    md.digest(generatedData, generatedLength, digestLength);
	                }

	                generatedLength += digestLength;
	            }

	            // Copy key and IV into separate byte arrays
	            byte[][] result = new byte[2][];
	            result[0] = Arrays.copyOfRange(generatedData, 0, keyLength);
	            if (ivLength > 0)
	                result[1] = Arrays.copyOfRange(generatedData, keyLength, keyLength + ivLength);

	            return result;

	        } catch (DigestException e) {
	            throw new RuntimeException(e);

	        } finally {
	            // Clean out temporary data
	            Arrays.fill(generatedData, (byte)0);
	        }
	    }
}
