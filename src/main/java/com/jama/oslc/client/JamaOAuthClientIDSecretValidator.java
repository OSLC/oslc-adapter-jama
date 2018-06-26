package com.jama.oslc.client;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.wink.client.ClientResponse;
import org.eclipse.lyo.client.oslc.OSLCConstants;
import org.eclipse.lyo.client.oslc.OslcOAuthClient;

import com.jama.oslc.model.Constants;
import com.jama.oslc.web.AdapterInitializer;

import net.oauth.OAuthException;

public class JamaOAuthClientIDSecretValidator {

	public static void main(String[] args) {
		
		
		try {
			
			String clientID = "uz2nblihwtxvvmj";
			String secret = "xfnkf29fhadb0r70qgb8f4a8c";
			
		    String url = "https://koneksys.jamacloud.com/rest/oauth/token";
		 
		    URL obj = new URL(url);
		    HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
		 
		    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		    conn.setDoOutput(true);
		 
		    conn.setRequestMethod("POST");
		 
//		    String userpass = "uz2nblihwtxvvmj:xfnkf29fhadb0r70qgb8f4a8c";
		    String userpass = clientID + ":" + secret;
		    String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes("UTF-8"));
		    conn.setRequestProperty ("Authorization", basicAuth);
		 
		   
		    OutputStream os = conn.getOutputStream();
		    OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");    
		    osw.write("grant_type=client_credentials");
		    osw.flush();
		    osw.close();
		    os.close();  //don't forget to close the OutputStream
		 
		  //read the inputstream and print it
		    String result;
		    BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
		    ByteArrayOutputStream buf = new ByteArrayOutputStream();
		    int result2 = bis.read();
		    while(result2 != -1) {
		        buf.write((byte) result2);
		        result2 = bis.read();
		    }
		    result = buf.toString();
		    System.out.println(result); 
		    
		    boolean isJamaClientIDAndSecretOK = false;
		    if(result != null ) {
		    	if(result.startsWith("{\"access_token\"")) {
		    		isJamaClientIDAndSecretOK = true;
		    	}
		    }
		    System.out.println(isJamaClientIDAndSecretOK); 
		 
		    } catch (Exception e) {
		    e.printStackTrace();
		    }
		 
		  }
		
		
	

}
