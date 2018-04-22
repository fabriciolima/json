package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import main.entidade.Cliente;


public class Util {
	private static String secret = "123";
    
    public static String decrypt(String cipherText) throws Exception 
    {
		
		byte[] cipherData = Base64.getDecoder().decode(cipherText);
		byte[] saltData = Arrays.copyOfRange(cipherData, 8, 16);

		MessageDigest md5 = MessageDigest.getInstance("MD5");
		final byte[][] keyAndIV = GenerateKeyAndIV(32, 16, 1, saltData, secret.getBytes(StandardCharsets.UTF_8), md5);
		SecretKeySpec key = new SecretKeySpec(keyAndIV[0], "AES");
		IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);

		byte[] encrypted = Arrays.copyOfRange(cipherData, 16, cipherData.length);
		Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
		aesCBC.init(Cipher.DECRYPT_MODE, key, iv);
		byte[] decryptedData = aesCBC.doFinal(encrypted);
		String decryptedText = new String(decryptedData, StandardCharsets.UTF_8);

		return decryptedText;
    }
    
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

	    } catch (Exception e) {
	        throw new RuntimeException(e);

	    } finally {
	        // Clean out temporary data
	        Arrays.fill(generatedData, (byte)0);
	    }
	}

	public String getUrlCapa(String restUrl) throws Exception {		//="http://ip.jsontest.com/";//"http://54.94.219.84:8080/weplay/json/jogo/nome?nome=wild";
    
	
		String retorno = null;
		 System.setProperty("http.proxyHost", "proxy");
		 System.setProperty("http.proxyPort", "8080");
		 System.setProperty("http.proxyUser", "f092-4");
         System.setProperty("http.proxyPassword", "q1w2e3r4");

		
//		 String encoded = new String(Base64.encodeBase64(("f092-4:q1w2e3r4").getBytes()));
//		 con.setRequestProperty("Proxy-Authorization", "Basic " + encoded);
//		 Authenticator.setDefault(new ProxyAuth("f092-4","q1w2e3r4"));
		//
		System.setProperty("java.net.useSystemProxies", "true");

		String userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.91 Safari/537.36";
		restUrl = "http://www.trocajogo.com.br/api/SearchGames?rows=10&page=1&termo=".concat(restUrl.replaceAll(" ", "+"));
		URL url = new URL(restUrl);
		HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
		httpcon.addRequestProperty("User-Agent", userAgent);

		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String line = in.readLine();
		//line = "{\"result\":true,\"data\":[{\"ImagemCapa\":\"20121203173020_ps3_far-cry-3.jpg\",\"Level\":0,\"Ranking\":0,\"Nome\":\"Far Cry 3\",\"Desejos\":4898,\"Ofertas\":2580,\"Jogo\":{\"Nome\":\"Far Cry 3\",\"Permalink\":\"far-cry-3\"},\"Plataforma\":{\"Nome\":\"Playstation 3\",\"Permalink\":\"ps3\",\"Logotipo\":null,\"Jogos\":null}},{\"ImagemCapa\":\"20121203173029_x360_far-cry-3.jpg\",\"Level\":0,\"Ranking\":0,\"Nome\":\"Far Cry 3\",\"Desejos\":2597,\"Ofertas\":1486,\"Jogo\":{\"Nome\":\"Far Cry 3\",\"Permalink\":\"far-cry-3\"},\"Plataforma\":{\"Nome\":\"XBOX 360\",\"Permalink\":\"xbox360\",\"Logotipo\":null,\"Jogos\":null}}],\"total\":2}";

		JsonParser parser = new JsonParser();
		// JsonElement element = parser.parse(line);
		// JsonObject obj = element.getAsJsonObject(); //since you know it's a
		// JsonObject
		// Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();//will return
		// members of your object
		// for (Map.Entry<String, JsonElement> entry: entries) {
		// System.out.println(entry.getKey());
		// System.out.println(entry.getValue());
		// }

		JsonObject jObj = (JsonObject) parser.parse(line);
		JsonArray data = jObj.getAsJsonArray("data");
		Integer desejo = -1;
		for(JsonElement d: data){
			Integer novoDesejo = d.getAsJsonObject().get("Desejos").getAsInt();
			if(desejo < novoDesejo) {
				retorno = d.getAsJsonObject().get("ImagemCapa").getAsString();
				desejo = novoDesejo;
				System.out.println(d.getAsJsonObject().get("Nome").getAsString() + " - " + retorno);
			}
		};

		return retorno;

	}
	
	public static void sendMail(String to, String link) {

	      String from = "donotreply@weplay.com";
	      String host = "localhost";
	      Properties properties = System.getProperties();
	      properties.setProperty("mail.smtp.host", host);
	      Session session = Session.getDefaultInstance(properties);

	      try {
	         MimeMessage message = new MimeMessage(session);

	         message.setFrom(new InternetAddress(from));
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	         message.setSubject("Confirmation instructions");

	         message.setText("Uma nova conta de usuário foi criada no WePlay. Para ativar sua conta clique no link abaixo:\n" + 
	         		"\n" + 
	         		"http://54.94.219.84:8080/weplay/json/teste" +//link 
	         		"\n\n" + 
	         		"Caso esteja tendo dificuldades com o link, você também pode copiá-lo e colá-lo no seu navegador de internet.\n" + 
	         		"\n" + 
	         		"Atenciosamente,\n" + 
	         		"\n" + 
	         		"Equipe do WePlay\n" + 
	         		"\n" + 
	         		"");

	         Transport.send(message);

	      } catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	   }

	public static String crypt(String text) throws Exception 
    {
		return text;
    }

	public static Long decryptToken(String token) {
		return Long.decode(token);
	}
    
	public static void relembrarPassord(Cliente c) {

	      String from = "donotreply@weplay.com";
	      String host = "localhost";
	      Properties properties = System.getProperties();
	      properties.setProperty("mail.smtp.host", host);
	      Session session = Session.getDefaultInstance(properties);

	      try {
	         MimeMessage message = new MimeMessage(session);

	         message.setFrom(new InternetAddress(from));
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(c.getEmail()));

	         message.setSubject("Confirmation instructions");

	         message.setText("Alguem esta tentando entrar com essa conta. "
	         		+ "Foi definido um novo password: " + c.getNovoPassword()
	         		+" Para validar o novo password no link abaixo:\n" + 
	         		"\n" + 
	         		"http://54.94.219.84:8080/weplay/json/teste" +//link 
	         		"\n\n" + 
	         		"Caso esteja tendo dificuldades com o link, você também pode copiá-lo e colá-lo no seu navegador de internet.\n" + 
	         		"\n" + 
	         		"Atenciosamente,\n" + 
	         		"\n" + 
	         		"Equipe do WePlay\n" + 
	         		"\n" + 
	         		"");

	         Transport.send(message);

	      } catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	   }


	

}
