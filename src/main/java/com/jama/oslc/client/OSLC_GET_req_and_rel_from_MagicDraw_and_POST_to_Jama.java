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

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.xml.datatype.DatatypeConfigurationException;

import org.apache.commons.io.IOUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.eclipse.lyo.oslc4j.core.exception.OslcCoreApplicationException;
import org.eclipse.lyo.oslc4j.core.model.Link;
import org.eclipse.lyo.oslc4j.core.model.OslcMediaType;
import org.eclipse.lyo.oslc4j.provider.jena.JenaModelHelper;
import com.jama.oslc.model.Requirement;
import com.magicdraw.oslc.resources.SysMLRequirement;

public class OSLC_GET_req_and_rel_from_MagicDraw_and_POST_to_Jama {

	public static void main(String[] args) {

		try {
			String requirementIdentifier = "newRequirement141";
			
			Client client = ClientBuilder.newClient();

			WebTarget myResource = client.target(
					"http://localhost:8181/oslc4jmagicdraw/services/SUV_Example/requirements/" + requirementIdentifier);
			String response = myResource.request(OslcMediaType.APPLICATION_RDF_XML).get(String.class);

			System.out.println(response);

			// read MagicDraw requirement as POJO
			Model mdReqModel = ModelFactory.createDefaultModel();
			mdReqModel.read(IOUtils.toInputStream(response, "UTF-8"), null);
			Resource sysmlReqResource = mdReqModel.getResource(
					"http://localhost:8181/oslc4jmagicdraw/services/SUV_Example/requirements/" + requirementIdentifier);
			SysMLRequirement sysMLRequirement = (SysMLRequirement) JenaModelHelper.fromJenaResource(sysmlReqResource,
					SysMLRequirement.class);
			System.out.println("SysML Requirement Identifier: " + sysMLRequirement.getIdentifier());
			
			// creating Jama requirement POJO from MagicDraw requirement POJO
			Requirement newRequirementToAdd = new Requirement();
			newRequirementToAdd.setAbout(URI.create("http://localhost:8080/jama-oslc-adapter/services/Semiconductor_Sample_Set/requirement/" + sysMLRequirement.getIdentifier()));
			newRequirementToAdd.setTitle(sysMLRequirement.getIdentifier());
			newRequirementToAdd.setIdentifier(sysMLRequirement.getIdentifier());
			newRequirementToAdd.setDescription(sysMLRequirement.getDescription());
			newRequirementToAdd.setParentID("569");	// this is hard coded
			
			
			Link[] derivedFromLinksArray = sysMLRequirement.getDerivedFromElements();
			
			// create corresponding link array with Jama Req URLs
			Link[] derivedFromLinksArray2 = new Link[derivedFromLinksArray.length];
			int arrayIndex = 0;
			for (Link link : derivedFromLinksArray) {
				URI uri = link.getValue();
				
				// get related SysML requirement as OSLC POJO
				WebTarget myResource2 = client.target(
						uri.toString());
				String response2 = myResource2.request(OslcMediaType.APPLICATION_RDF_XML).get(String.class);
				
				Model mdReqModel2 = ModelFactory.createDefaultModel();
				mdReqModel2.read(IOUtils.toInputStream(response2, "UTF-8"), null);
				Resource sysmlReqResource2 = mdReqModel2.getResource(
						uri.toString());
				SysMLRequirement sysMLRequirement2 = (SysMLRequirement) JenaModelHelper.fromJenaResource(sysmlReqResource2,
						SysMLRequirement.class);
				
				// creating Jama requirement POJO from MagicDraw requirement POJO
				Requirement newRequirementToAdd2 = new Requirement();
				newRequirementToAdd2.setAbout(URI.create("http://localhost:8080/jama-oslc-adapter/services/Semiconductor_Sample_Set/requirement/" + sysMLRequirement2.getIdentifier()));
				newRequirementToAdd2.setTitle(sysMLRequirement2.getIdentifier());
				newRequirementToAdd2.setIdentifier(sysMLRequirement2.getIdentifier());
				newRequirementToAdd2.setDescription(sysMLRequirement2.getDescription());
				newRequirementToAdd2.setParentID("569");	// this is hard coded
				
				Object[] objects2 = new Object[1];
				objects2[0] = newRequirementToAdd2;
				Model modelOfRequirement2 = JenaModelHelper.createJenaModel(objects2);
				StringWriter out2 = new StringWriter(); 
				modelOfRequirement2.write(out2, "RDF/XML");						
				String requirementInRDFXML2 = out2.toString();
				
				
				// first add related reqs in Jama
				Client jamaClient2 = ClientBuilder.newClient();			
				WebTarget jamaReqResource2 = jamaClient2.target("http://localhost:8080/jama-oslc-adapter/services/Semiconductor_Sample_Set/requirement");
				String jamaPOSTResponse2 = jamaReqResource2.request(OslcMediaType.APPLICATION_RDF_XML)
				        .post(Entity.entity(requirementInRDFXML2, OslcMediaType.APPLICATION_RDF_XML)
	                            , String.class);

				System.out.println("Jama POST Response2: " + jamaPOSTResponse2);
				
				// get jamaID of just created Jama requirement
				// convert response in RDF/XML into POJO
				// read new URI of resource
				
				// read MagicDraw requirement as POJO
				Model mdReqModel3 = ModelFactory.createDefaultModel();
				mdReqModel3.read(IOUtils.toInputStream(jamaPOSTResponse2, "UTF-8"), null);
				Iterator subjectIT = mdReqModel3.listSubjects();
				Resource newResource = (Resource) subjectIT.next();
								
				System.out.println("New Jama Requirement URI: " + newResource.asResource().getURI());
				derivedFromLinksArray2[arrayIndex] = new Link(URI.create(newResource.asResource().getURI()));
				arrayIndex++;
			}
			
			newRequirementToAdd.setDerivedFrom(derivedFromLinksArray2);
			
			
			Object[] objects = new Object[1];
			objects[0] = newRequirementToAdd;
			Model modelOfRequirement = JenaModelHelper.createJenaModel(objects);
			StringWriter out = new StringWriter(); 
			modelOfRequirement.write(out, "RDF/XML");						
			String requirementInRDFXML = out.toString();
			
			System.out.println(requirementInRDFXML);
			
			Client jamaClient = ClientBuilder.newClient();			
			WebTarget jamaReqResource = jamaClient.target("http://localhost:8080/jama-oslc-adapter/services/Semiconductor_Sample_Set/requirement");
			String jamaPOSTResponse = jamaReqResource.request(OslcMediaType.APPLICATION_RDF_XML)
			        .post(Entity.entity(requirementInRDFXML, OslcMediaType.APPLICATION_RDF_XML)
                            , String.class);

			System.out.println("Jama POST Response: " + jamaPOSTResponse);
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OslcCoreApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
