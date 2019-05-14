import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/* Provides methods to encrypt and decrypt strings
 */
public class Encrypt {
	final static String key = "****************"; // 128 bit key
	
	public static void main(String[] args) {
		System.out.println(decrypt(encrypt("Hello World!")));
	}

	public static byte[] encrypt(String text) {
		try {
		    // Create key and cipher
		    Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
		    Cipher cipher = Cipher.getInstance("AES");
		    // encrypt the text
		    cipher.init(Cipher.ENCRYPT_MODE, aesKey);
		    byte[] encrypted = cipher.doFinal(text.getBytes());
		    for (int i = 0; i < encrypted.length; i++) {
		    	System.out.print(encrypted[i] + " ");
		    }
		    System.out.println();
		    return encrypted;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String decrypt(byte[] encrypted) {
		try {
		    // Create key and cipher
		    Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
		    Cipher cipher = Cipher.getInstance("AES");
		    
		    cipher.init(Cipher.DECRYPT_MODE, aesKey);
		    String decrypted = new String(cipher.doFinal(encrypted));
		    
		    return decrypted;
		} catch (Exception e) {
			return "";
		}
	}
}
