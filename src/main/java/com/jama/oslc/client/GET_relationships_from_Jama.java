package com.jama.oslc.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.eclipse.lyo.oslc4j.core.model.ServiceProvider;
import org.eclipse.lyo.oslc4j.core.model.ServiceProviderFactory;

import com.jama.oslc.model.Constants;
import com.jama.oslc.model.Namespace;


public class GET_relationships_from_Jama {

	static Map<Integer, Collection<Integer>> reqId_DerivedFromRelationsMap = new HashMap<Integer, Collection<Integer>>();
	
	public static void main(String[] args) {
		String username = "hector";
		String password = "4g1l3k1tch3n";
		String jamaInstanceName = "koneksys";
		
		int projectId = 15;
		int startAt = 0;
		int maxResults = 50;
		
		boolean isPagingNecessary = true;
		
		
		String usernameAndPassword = username + ":" + password;
		String authorizationHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString( usernameAndPassword.getBytes() );		
		Client client = ClientBuilder.newClient();
		
		
		while(isPagingNecessary) {
			WebTarget myResource = client.target("https://" + jamaInstanceName + ".jamacloud.com/rest/latest/relationships?project=" + projectId + "&startAt=" + startAt + "&maxResults=" + maxResults + "");
			String response = myResource.request(MediaType.APPLICATION_JSON)
			        .header("Authorization", authorizationHeaderValue)		        
			        .get(String.class);		
			
			System.out.println(response);
			
			try {
				JSONObject obj = new JSONObject(response);
				JSONObject metaObject = obj.getJSONObject("meta");
				JSONObject pageInfoObject = metaObject.getJSONObject("pageInfo");

				int startIndex = (Integer) pageInfoObject.get("startIndex");
				int resultCount = (Integer) pageInfoObject.get("resultCount");
				int totalResults = (Integer) pageInfoObject.get("totalResults");
				
				System.out.println("startIndex: " + startIndex);
				System.out.println("resultCount: " + resultCount);			
				System.out.println("totalResults: " + totalResults);
				
				JSONArray dataArray = obj.getJSONArray("data");
				
				
				Iterator dataArrayIT = dataArray.iterator();
				while (dataArrayIT.hasNext()) {
					Object object = dataArrayIT.next();
					if (object instanceof JSONObject) {
						JSONObject relatioshipObject = (JSONObject) object;
						
						String relationshipType = relatioshipObject.getString("relationshipType");
						System.out.println(relationshipType);
						
						// derivedFrom requirements
						if(relationshipType.equals("6")) {
							
							
							
							int fromItem = relatioshipObject.getInt("fromItem");
							int toItem = relatioshipObject.getInt("toItem");
//							
							Collection<Integer> relationships = reqId_DerivedFromRelationsMap.get(fromItem);
							if(relationships != null) {
								relationships.add(toItem);
							}
							else {
								Collection<Integer> newRelationships = new ArrayList<Integer>();
								newRelationships.add(toItem);
								reqId_DerivedFromRelationsMap.put(fromItem, newRelationships);
							}
							
							System.out.println(fromItem + " -> " + toItem);

							
						}
						



							
							
					}
				}
				
				
				
				int newStartIndex = startIndex + resultCount;
				
				
				if(newStartIndex >= totalResults) {
					isPagingNecessary = false;
					
				}
				else {
					startAt = newStartIndex;
				}
				

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		System.out.println(reqId_DerivedFromRelationsMap.size());
		
		

	}

}
