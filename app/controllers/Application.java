package controllers;

import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;

import play.mvc.Controller;
import org.apache.commons.codec.binary.Base64;

public class Application extends Controller {
	
	private static final char[] KEY_PASS = "notasecret".toCharArray();
	
	private static final String PRIVATE_KEY = System.getenv("PRIVATE_KEY");
	
	private static String encodeBase64(byte[] rawData) {
		String data = Base64.encodeBase64URLSafeString(rawData);
		return data.replaceAll("\n", "");
	}
	
	private static byte[] decodeBase64(String data) {
		return Base64.decodeBase64(data);
	}
	
	public static void sign(String data) throws Exception {
		if (PRIVATE_KEY == null) {
			notFound();
		}
		byte[] keyData = decodeBase64(PRIVATE_KEY);
		KeyStore keystore = KeyStore.getInstance("PKCS12");
		keystore.load(new ByteArrayInputStream(keyData), KEY_PASS);
		PrivateKey privateKey = (PrivateKey)keystore.getKey("privatekey", KEY_PASS);
		
		Signature signature = Signature.getInstance("SHA256withRSA");
		signature.initSign(privateKey);
		signature.update(data.getBytes("utf-8"));
		
		String signed = encodeBase64(signature.sign());
		renderText(signed);
	}

}