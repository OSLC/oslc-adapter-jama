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


public class POST_requirement_to_Jama {

	
	
	public static void main(String[] args) {
		
		String projectIDForJSON = "15";
		
		String reqIdentifier = "newRequirementX200";
		// create new requirement
		Requirement requirement = new Requirement();
		requirement.setAbout(URI.create("http://localhost:8080/jama-oslc-adapter/services/Semiconductor_Sample_Set/requirement/" + reqIdentifier));
		requirement.setTitle(reqIdentifier);
		requirement.setIdentifier(reqIdentifier);
		requirement.setDescription(reqIdentifier);
		requirement.setParentID("569");	// this is hard coded
		
		// Convert requirement into JSON for Jama REST API
					String requirementAsJSON = null;
					
					
					JSONObject jSONObject = new JSONObject();
					try {
						if(requirement.getIdentifier() != null){
							try {
								jSONObject.put("id", Integer.valueOf(requirement.getIdentifier()));
							}
							catch(IllegalArgumentException e) {
								// requirement identifier may not always be an integer!
								// jama requirement identifiers are integers
								// but requirements for example from MagicDraw may have a different identfier
								// in which case let Jama create a new identifier
							}
							
						}
						jSONObject.put("itemType", 45);
						jSONObject.put("project", Integer.valueOf(projectIDForJSON));
						
						JSONObject fieldsObject = new JSONObject();
						jSONObject.put("fields", fieldsObject);
						
						JSONObject locationObject = new JSONObject();
						jSONObject.put("location", locationObject);
						
						JSONObject parentObject = new JSONObject();
						locationObject.put("parent", parentObject);
						
						if(requirement.getParentID() != null){
							parentObject.put("item", Integer.valueOf(requirement.getParentID()));
						}
						
						if(requirement.getDescription() != null){
							fieldsObject.put("description", requirement.getDescription());
						}	
						
						if(requirement.getTitle() != null){
							fieldsObject.put("name", requirement.getTitle());
						}
						
						requirementAsJSON = jSONObject.toString();
						
						
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
		
			WebTarget myResource = client.target("https://" + jamaInstanceName + ".jamacloud.com/rest/latest/items");
			String response = myResource.request(MediaType.APPLICATION_JSON)
					.header("Authorization", authorizationHeaderValue)	
			        .header("Content-Type", MediaType.APPLICATION_JSON)
			        .header("Accept", MediaType.APPLICATION_JSON)	        
			        .post(Entity.entity(requirementAsJSON, MediaType.APPLICATION_JSON)
                            , String.class);		
			
			System.out.println(response);
			
			// from response, read new ID in Jama and update!
			try {
			JSONObject obj = new JSONObject(response);
			JSONObject metaObject = obj.getJSONObject("meta");
			int jamaID = (Integer) metaObject.get("id");
			System.out.println(jamaID);
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
		

	}

}
