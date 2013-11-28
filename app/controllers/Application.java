package controllers;

import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;

import play.mvc.Controller;
import org.apache.commons.codec.binary.Base64;

public class Application extends Controller {
	
	private static final PrivateKey PRIVATE_KEY;
	
	static {
		String pass = System.getenv("KEY_PASSPHRASE");
		if (pass == null) {
			pass = "notasecret";
		}
		String keyData = System.getenv("PRIVATE_KEY");
		PrivateKey key = null;
		if (keyData != null) {
			try {
				KeyStore keystore = KeyStore.getInstance("PKCS12");
				keystore.load(new ByteArrayInputStream(decodeBase64(keyData)), pass.toCharArray());
				key = (PrivateKey)keystore.getKey("privatekey", pass.toCharArray());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		PRIVATE_KEY = key;
	}
	
	private static String encodeBase64(byte[] rawData) {
		String data = Base64.encodeBase64URLSafeString(rawData);
		return data.replaceAll("\n", "");
	}
	
	private static byte[] decodeBase64(String data) {
		return Base64.decodeBase64(data);
	}
	
	public static void index() {
		renderText(PRIVATE_KEY == null ? "It doesn't work!" : "It works!");
	}
	
	public static void sign(String data) throws Exception {
		if (PRIVATE_KEY == null) {
			notFound();
		}
		Signature signature = Signature.getInstance("SHA256withRSA");
		signature.initSign(PRIVATE_KEY);
		signature.update(data.getBytes("utf-8"));
		
		String signed = encodeBase64(signature.sign());
		renderText(signed);
	}

}