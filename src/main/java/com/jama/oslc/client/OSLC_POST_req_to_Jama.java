/*
 * MIT License
 * 
 * Copyright 2018 Koneksys
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * */
package com.jama.oslc.client;

import java.io.StringWriter;
import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import org.eclipse.lyo.oslc4j.core.model.OslcMediaType;
import org.eclipse.lyo.oslc4j.provider.jena.JenaModelHelper;

import com.hp.hpl.jena.rdf.model.Model;
import com.jama.oslc.model.Requirement;

public class OSLC_POST_req_to_Jama {

	public static void main(String[] args) {

		
		
		try {

			
			Requirement newRequirementToAdd = new Requirement();
			newRequirementToAdd.setAbout(URI.create("http://localhost:8080/jama-oslc-adapter/services/Semiconductor_Sample_Set/requirement/9999"));
			newRequirementToAdd.setTitle("New Jama Requirement X6");
			newRequirementToAdd.setIdentifier("9999");
			newRequirementToAdd.setDescription("Description of new Jama Requirement X");
			newRequirementToAdd.setParentID("569");
			
			Object[] objects = new Object[1];
			objects[0] = newRequirementToAdd;
			Model modelOfRequirement = JenaModelHelper.createJenaModel(objects);
			StringWriter out = new StringWriter(); 
			modelOfRequirement.write(out, "RDF/XML");						
			String requirementInRDFXML = out.toString();
			
			System.out.println(requirementInRDFXML);
			
			Client client = ClientBuilder.newClient();
//			WebTarget myResource = client.target("https://koneksys.jamacloud.com/rest/latest/items/6840");
			
			WebTarget myResource = client.target("http://localhost:8080/jama-oslc-adapter/services/Semiconductor_Sample_Set/requirement");
			
			
//			Invocation.Builder builder = myResource.request(MediaType.TEXT_PLAIN);
			
			
//			String response = myResource.request(MediaType.APPLICATION_JSON)
//			        .header("Authorization", "Basic aGVjdG9yOjRnMWwzazF0Y2gzbg==")
//			        .header("Content-Type", OslcMediaType.APPLICATION_RDF_XML)
//			        .header("Accept", OslcMediaType.APPLICATION_RDF_XML)
//			        .get(String.class);
			
			
			
			
			String response = myResource.request(OslcMediaType.APPLICATION_RDF_XML)
			        .post(Entity.entity(requirementInRDFXML, OslcMediaType.APPLICATION_RDF_XML)
                            , String.class);

			System.out.println(response);
			
		  } catch (Exception e) {

			e.printStackTrace();

		  }

		
		
		
		
		

	}

}
