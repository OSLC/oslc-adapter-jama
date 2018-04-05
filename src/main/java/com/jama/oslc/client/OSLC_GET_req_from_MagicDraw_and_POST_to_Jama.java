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

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.xml.datatype.DatatypeConfigurationException;

import org.apache.commons.io.IOUtils;
import org.eclipse.lyo.oslc4j.core.exception.OslcCoreApplicationException;
import org.eclipse.lyo.oslc4j.core.model.CreationFactory;
import org.eclipse.lyo.oslc4j.core.model.OslcMediaType;
import org.eclipse.lyo.oslc4j.core.model.QueryCapability;
import org.eclipse.lyo.oslc4j.core.model.Service;
import org.eclipse.lyo.oslc4j.core.model.ServiceProvider;
import org.eclipse.lyo.oslc4j.core.model.ServiceProviderCatalog;
import org.eclipse.lyo.oslc4j.provider.jena.JenaModelHelper;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.jama.oslc.model.Requirement;
import com.magicdraw.oslc.resources.SysMLRequirement;

public class OSLC_GET_req_from_MagicDraw_and_POST_to_Jama {

	public static void main(String[] args) {

		try {
			String requirementIdentifier = "newRequirement41";
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
