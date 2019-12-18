package com.datangwealth.modules.common.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class AppDSAUtil {

	/*
	 * 注： 1、DSA只是一种算法，和RSA不同之处在于它不能用作加密和解密，也不能进行密钥交换，只用于签名,它比RSA要快很多.
	 * 2、密钥限制：Modulus size must range from 512 to 1024 and be a multiple of 64
	 */

	public static final String ALGORITHM = "DSA";
	public static final String base64_privateKey = "MIIBSgIBADCCASsGByqGSM44BAEwggEeAoGBAIlu8xvlIGYZmxf4s8dwh9qCbFsYfDLT+vhak5P9"
			+ "IIFB/5917ihbCnI8kDOXghROyYg12fVx+L5hXjjQ4/Bpm5evGyac682iM5djlAAafuETXqWIhDnG"
			+ "gcsyV4Faq45SI3ysmdnC8K17qoUevYsvX4a7cAO2ABANPX/2t13UvAwVAhUAhNBnF8kFnvga7/MZ"
			+ "2+KLQ2a8uK8CgYAPg8Ka5XzCHYq7LWmzFk9GiyMLFtHfkN3Xqiojo1VXIuC23kyP5i8Nvd0EICGx"
			+ "syvDur4S2JCKkCREwaSmuGnLg5qeFtsD/qFcY5ZDD5UaJSPBfpqzTbFT30Pl4qkMYcZwooIxPDRZ"
			+ "VveeogYoGvsJ9gwGXjdkzVVFIZLSjKLL2gQWAhR7SGPjM2GsrjYrDhVljwfXmcSXPQ==";

	public static final String base64_publicKey = "MIIBtjCCASsGByqGSM44BAEwggEeAoGBAIlu8xvlIGYZmxf4s8dwh9qCbFsYfDLT+vhak5P9IIFB"
			+ "/5917ihbCnI8kDOXghROyYg12fVx+L5hXjjQ4/Bpm5evGyac682iM5djlAAafuETXqWIhDnGgcsy"
			+ "V4Faq45SI3ysmdnC8K17qoUevYsvX4a7cAO2ABANPX/2t13UvAwVAhUAhNBnF8kFnvga7/MZ2+KL"
			+ "Q2a8uK8CgYAPg8Ka5XzCHYq7LWmzFk9GiyMLFtHfkN3Xqiojo1VXIuC23kyP5i8Nvd0EICGxsyvD"
			+ "ur4S2JCKkCREwaSmuGnLg5qeFtsD/qFcY5ZDD5UaJSPBfpqzTbFT30Pl4qkMYcZwooIxPDRZVvee"
			+ "ogYoGvsJ9gwGXjdkzVVFIZLSjKLL2gOBhAACgYBv/ZcOjBiLME7DdizXUAvz14wi0H9Jnl3kN1Iw"
			+ "u3V6ZJG04myYmHlNHLoi1eqdcYGxS1mx8xJzUCQpJFqhWrefBb2dmAWQgXNoNu4ROEISc+suZIdv"
			+ "rJ8R77iUN1jUbS9XeL+4PGYmYxfg9G0xnz/VG/JOPW76LM2K840LMMAWSQ==";

	public static KeyPair generateKeyPair(int keysize) throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM);
		keyPairGen.initialize(keysize);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		return keyPair;
	}

	public static KeyPair generateKeyPair() throws Exception {
		return generateKeyPair(1024);
	}

	public static KeyPair generateKeyPair(int keysize, byte[] seed)
			throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM);
		keyPairGen.initialize(keysize, new SecureRandom(seed));
		KeyPair keyPair = keyPairGen.generateKeyPair();
		return keyPair;
	}

	public static String sign(byte[] content, String base64PriKey)
			throws Exception {

		byte[] key = new BASE64Decoder().decodeBuffer(base64PriKey);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

		Signature signature = Signature.getInstance(keyFactory.getAlgorithm());
		signature.initSign(privateKey);
		signature.update(content);

		return new BASE64Encoder().encode(signature.sign());
	}

	public static boolean verify(byte[] content, String base64PubKey,
			String strSign) throws Exception {

		byte[] key = new BASE64Decoder().decodeBuffer(base64PubKey);
		byte[] sign = new BASE64Decoder().decodeBuffer(strSign);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
		PublicKey publicKey = keyFactory.generatePublic(keySpec);

		Signature signature = Signature.getInstance(keyFactory.getAlgorithm());
		signature.initVerify(publicKey);
		signature.update(content);
		return signature.verify(sign);
	}

//	public static void main(String[] args) throws Exception {
//		// 需要加密的字串
//		String cSrc = "test123";
//		// 生成数字签名
//		long lStart = System.currentTimeMillis();
//		String sign = AppDSAUtil.sign(cSrc.getBytes(), base64_privateKey);
//		System.out.println("生成的数字签名是：" + sign);
//
//		long lUseTime = System.currentTimeMillis() - lStart;
//		System.out.println("加密耗时：" + lUseTime + "毫秒");
//		// 校验数字签名
//		lStart = System.currentTimeMillis();
//		boolean DeString = AppDSAUtil.verify("Tv32JOFKiL1Wu4JY2C36lw==".getBytes(), base64_publicKey, sign);
//		System.out.println(DeString);
//		lUseTime = System.currentTimeMillis() - lStart;
//		System.out.println("解密耗时：" + lUseTime + "毫秒");
//		
//
//	}

}
