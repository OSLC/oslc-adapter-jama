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

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.datatype.DatatypeConfigurationException;

import org.eclipse.lyo.oslc4j.core.exception.OslcCoreApplicationException;
import org.eclipse.lyo.oslc4j.core.model.CreationFactory;
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

public class OSLC_JamaAdapterDiscoveryClient {

	public static void main(String[] args) {

		try {

			// retrieve serviceProviderCatalog resource (entry point to discover
			// all services and resources exposed by OSLC adapter)
			// based on one single hard coded URL
			String serviceProviderCatalogURL = "http://localhost:8080/jama-oslc-adapter/services/serviceProviderCatalog";

			Model serviceProviderCatalogModel = ModelFactory.createDefaultModel();
			serviceProviderCatalogModel.read(serviceProviderCatalogURL);
			Resource serviceProviderCatalogResource = serviceProviderCatalogModel
					.getResource(serviceProviderCatalogURL);
			ServiceProviderCatalog serviceProviderCatalog = (ServiceProviderCatalog) JenaModelHelper
					.fromJenaResource(serviceProviderCatalogResource, ServiceProviderCatalog.class);
			System.out.println("*** SERVICE PROVIDER CATALOG ***");
			System.out.println(serviceProviderCatalog.getTitle());
			System.out.println("");

			// retrieve all serviceProviders of serviceProviderCatalog
			for (ServiceProvider serviceProvider : serviceProviderCatalog.getServiceProviders()) {
				System.out.println("");
				System.out.println("*** SERVICE PROVIDER ***");
				System.out.println(serviceProvider.getTitle());

				// retrieve all services
				for (Service service : serviceProvider.getServices()) {
					System.out.println("");
					System.out.println("*** SERVICE ***");
					// System.out.println(service.getAbout());
					// System.out.println(service.getTypes());
					System.out.println("Service Domain: " + service.getDomain());
					for (URI uri : service.getUsages()) {
						System.out.println("Service Usage: " + uri);
					}

					// retrieve all query capabilities
					for (QueryCapability queryCapability : service.getQueryCapabilities()) {
						System.out.println("");
						System.out.println("*** QueryCapability ***");
						System.out.println("QueryCapability QueryBase: " + queryCapability.getQueryBase());
						System.out.println("QueryCapability ResourceShape: " + queryCapability.getResourceShape());
						for (URI uri : queryCapability.getResourceTypes()) {
							System.out.println("QueryCapability ResourceType: " + uri);
						}

						// retrieve all resources returned by query capability
						Model queryCapabilityModel = ModelFactory.createDefaultModel();
						queryCapabilityModel.read(queryCapability.getQueryBase().toString());

						// identify different resource types in queryCapability
						// response
						Property typeProperty = queryCapabilityModel
								.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");

						// get total count of responseInfo
						Property totalCountProperty = queryCapabilityModel
								.createProperty("http://open-services.net/ns/core#totalCount");

						// get members of responseInfo
						Property memberProperty = queryCapabilityModel
								.createProperty("http://www.w3.org/2000/01/rdf-schema#member");

						ResIterator resIterator = queryCapabilityModel.listSubjectsWithProperty(typeProperty);
						while (resIterator.hasNext()) {
							Resource resource = resIterator.next();
							Statement typeStatement = resource.getProperty(typeProperty);
							String resourceType = typeStatement.getObject().asResource().getURI();

							if (resourceType.equals("http://open-services.net/ns/core#ResponseInfo")) {
								System.out.println("");
								System.out.println("*** ResponseInfo ***");
								Statement totalCountStatement = queryCapabilityModel.getProperty(resource,
										totalCountProperty);
								int totalCount = totalCountStatement.getObject().asLiteral().getInt();
								System.out.println("ResponseInfo totalCount: " + totalCount);

								StmtIterator stmtIterator = queryCapabilityModel.listStatements(resource,
										memberProperty, (RDFNode) null);
								while (stmtIterator.hasNext()) {
									Statement statement = stmtIterator.next();
									System.out.println(
											"ResponseInfo member: " + statement.getObject().asResource().getURI());
								}

							}

						}

						ResIterator resIterator2 = queryCapabilityModel.listSubjectsWithProperty(typeProperty);
						while (resIterator2.hasNext()) {
							Resource resource = resIterator2.next();
							Statement typeStatement = resource.getProperty(typeProperty);
							String resourceType = typeStatement.getObject().asResource().getURI();

							if (resourceType.equals("http://localhost:8080/jama-oslc-adapter/vocabulary/Requirement")) {
								Requirement requirement = (Requirement) JenaModelHelper.fromJenaResource(resource,
										Requirement.class);
								System.out.println("");
								System.out.println("*** Jama Requirement ***");
								System.out.println("Identifier: " + requirement.getIdentifier());
								System.out.println("Title: " + requirement.getTitle());
								System.out.println("Description: " + requirement.getDescription());
								System.out.println("DocumentKey: " + requirement.getDocumentKey());
								System.out.println("GlobalId: " + requirement.getGlobalId());
								System.out.println("Project: " + requirement.getProject());
								System.out.println("URI: " + requirement.getAbout());
								System.out.println("Created: " + requirement.getCreated());
								System.out.println("Modified: " + requirement.getModified());

							}

						}

					}
					
					
					// retrieve all creationFactories
					for (CreationFactory creationFactory : service.getCreationFactories()) {
						System.out.println("");
						System.out.println("*** CreationFactory ***");
						System.out.println("CreationFactory Title: " + creationFactory.getTitle());
						System.out.println("CreationFactory Creation URI: " + creationFactory.getCreation());
						for (URI uri : creationFactory.getResourceTypes()) {
							System.out.println("CreationFactory ResourceType: " + uri);
						}
						for (URI uri : creationFactory.getResourceShapes()) {
							System.out.println("CreationFactory ResourceShape: " + uri);
						}
						

						

					}

				}

			}

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
		}

	}

}
