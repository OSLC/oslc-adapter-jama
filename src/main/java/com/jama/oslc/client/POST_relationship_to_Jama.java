package com.jama.oslc.client;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONException;
import org.apache.wink.json4j.JSONObject;
import org.eclipse.lyo.oslc4j.core.model.ServiceProvider;
import org.eclipse.lyo.oslc4j.core.model.ServiceProviderFactory;

import com.jama.oslc.model.Constants;
import com.jama.oslc.model.Namespace;
import com.jama.oslc.model.Requirement;


public class POST_relationship_to_Jama {

	
	
	public static void main(String[] args) {
		
		String projectIDForJSON = "15";
		
	
		
		// Convert requirement into JSON for Jama REST API
					String relationshipAsJSON = null;
					
					
					JSONObject jSONObject = new JSONObject();
					try {
						
						jSONObject.put("fromItem", 6843);
						jSONObject.put("toItem", 6841);
						jSONObject.put("relationshipType", 6);
						relationshipAsJSON = jSONObject.toString();

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		
					String username = "hector";
					String password = "4g1l3k1tch3n";
					String jamaInstanceName = "koneksys";
					
					int projectId = 15;

					String usernameAndPassword = username + ":" + password;
					String authorizationHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString( usernameAndPassword.getBytes() );		
					Client client = ClientBuilder.newClient();
		
			WebTarget myResource = client.target("https://" + jamaInstanceName + ".jamacloud.com/rest/latest/relationships");
			String response = myResource.request(MediaType.APPLICATION_JSON)
					.header("Authorization", authorizationHeaderValue)	
			        .header("Content-Type", MediaType.APPLICATION_JSON)
			        .header("Accept", MediaType.APPLICATION_JSON)	        
			        .post(Entity.entity(relationshipAsJSON, MediaType.APPLICATION_JSON)
                            , String.class);		
			
			System.out.println(response);
			
			// from response, read new ID in Jama and update!
//			try {
//			JSONObject obj = new JSONObject(response);
//			JSONObject metaObject = obj.getJSONObject("meta");
//			int jamaID = (Integer) metaObject.get("id");
//			System.out.println(jamaID);
//			
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		
		
		
		

	}

}
