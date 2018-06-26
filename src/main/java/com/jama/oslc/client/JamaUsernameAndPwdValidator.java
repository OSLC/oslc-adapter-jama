package com.jama.oslc.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class JamaUsernameAndPwdValidator {

	public static void main(String[] args) {
		
//		String username = "username";
//		String password = "password";
		
		try {
			String username = "hector";
			String password = "4g1l3k1tch3n";
			
			String jamaInstanceName = "koneksys";
			String usernameAndPassword = username + ":" + password;
			String authorizationHeaderValue = "Basic "
					+ java.util.Base64.getEncoder().encodeToString(usernameAndPassword.getBytes());
			Client client = ClientBuilder.newClient();
			WebTarget myResource = client.target("https://" + jamaInstanceName + ".jamacloud.com/rest/latest/projects");
			int responseStatus = myResource.request(MediaType.APPLICATION_JSON)
					.header("Authorization", authorizationHeaderValue).get().getStatus();
			System.out.println(responseStatus);
		}
		catch (Exception e) {
			
		}
		

	}

}
