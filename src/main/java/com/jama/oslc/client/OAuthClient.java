package com.jama.oslc.client;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.wink.client.ClientResponse;
import org.eclipse.lyo.client.oslc.OSLCConstants;
import org.eclipse.lyo.client.oslc.OslcOAuthClient;

import net.oauth.OAuthException;

public class OAuthClient {

	public static void main(String[] args) {
		String baseUrl = "http://localhost:8080/jama-oslc-adapter/services/";		
		String consumerKey = "testkey";
		String consumerSecret = "testsecret";
		String oauthRealmName = "Jama";
		
		OslcOAuthClient oslcOAuthClient = new OslcOAuthClient(baseUrl + "oauth/requestToken", 
				baseUrl + "oauth/authorize", 
				baseUrl + "oauth/accessToken", consumerKey, consumerSecret, oauthRealmName);

		try {
			ClientResponse clientResponse = oslcOAuthClient.getResource("http://localhost:8080/jama-oslc-adapter/services/serviceProviderCatalog", OSLCConstants.CT_RDF);
			System.out.println(clientResponse.getStatusCode());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
