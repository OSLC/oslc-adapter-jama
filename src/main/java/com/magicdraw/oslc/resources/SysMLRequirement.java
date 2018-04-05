/*********************************************************************************************
 * Copyright (c) 2014 Model-Based Systems Engineering Center, Georgia Institute of Technology.
 *                         http://www.mbse.gatech.edu/
 *                  http://www.mbsec.gatech.edu/research/oslc
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Eclipse Distribution License v. 1.0 which accompanies this distribution.
 *
 *  The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 *  and the Eclipse Distribution License is available at
 *  http://www.eclipse.org/org/documents/edl-v10.php.
 *
 *  Contributors:
 *
 *	   Axel Reichwein, Koneksys (axel.reichwein@koneksys.com)		
 *******************************************************************************************/
package com.magicdraw.oslc.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.lyo.oslc4j.core.annotation.OslcDescription;
import org.eclipse.lyo.oslc4j.core.annotation.OslcName;
import org.eclipse.lyo.oslc4j.core.annotation.OslcOccurs;
import org.eclipse.lyo.oslc4j.core.annotation.OslcNamespace;
import org.eclipse.lyo.oslc4j.core.annotation.OslcReadOnly;
import org.eclipse.lyo.oslc4j.core.annotation.OslcPropertyDefinition;
import org.eclipse.lyo.oslc4j.core.annotation.OslcRange;
import org.eclipse.lyo.oslc4j.core.annotation.OslcRepresentation;
import org.eclipse.lyo.oslc4j.core.annotation.OslcResourceShape;
import org.eclipse.lyo.oslc4j.core.annotation.OslcTitle;
import org.eclipse.lyo.oslc4j.core.annotation.OslcValueType;
import org.eclipse.lyo.oslc4j.core.model.AbstractResource;
import org.eclipse.lyo.oslc4j.core.model.OslcConstants;
import org.eclipse.lyo.oslc4j.core.model.Occurs;
import org.eclipse.lyo.oslc4j.core.model.Representation;
import org.eclipse.lyo.oslc4j.core.model.ValueType;
import org.eclipse.lyo.oslc4j.core.model.Link;

@OslcNamespace(Constants.SYSML_NAMESPACE)
@OslcName("Requirement")
@OslcResourceShape(title = "Requirement Resource Shape", describes = Constants.TYPE_SYSML_REQUIREMENT)
public class SysMLRequirement extends Requirement{

	public SysMLRequirement() throws URISyntaxException {
		super();
	}
	public SysMLRequirement(URI about) throws URISyntaxException {
		super(about);
	}

	private String name;

	public void setName(String name) {
		this.name = name;
	}

	@OslcDescription("Description of NamedElement::name TBD")
	@OslcName("name")
	@OslcOccurs(Occurs.ZeroOrOne)
	@OslcPropertyDefinition("http://localhost:8080/oslc4jmagicdraw/services/sysml-rdfvocabulary#NamedElement_name")
	@OslcTitle("name")
	@OslcValueType(ValueType.XMLLiteral)
	public String getName() {
		 return name;
	}
	private String hyperlink;

	public void setHyperlink(String hyperlink) {
		this.hyperlink = hyperlink;
	}

	@OslcDescription("Description of Requirement::hyperlink TBD")
	@OslcName("hyperlink")
	@OslcOccurs(Occurs.ZeroOrOne)
	@OslcPropertyDefinition("http://localhost:8080/oslc4jmagicdraw/services/sysml-rdfvocabulary#Requirement_hyperlink")
	@OslcTitle("hyperlink")
	@OslcValueType(ValueType.XMLLiteral)
	public String getHyperlink() {
		 return hyperlink;
	}
	// ********* master *********
	private URI master;

	public void setMaster(final URI master) {
		this.master = master;
	}

	@OslcDescription("Description of Requirement::master TBD")
	@OslcName("master")
	@OslcPropertyDefinition("http://localhost:8080/oslc4jmagicdraw/services/sysml-rdfvocabulary#Requirement_master")
	@OslcTitle("master")
	@OslcRange("http://localhost:8080/oslc4jmagicdraw/services/sysml-rdfvocabulary#Requirement")
	public URI  getMaster() {
		 return master;
	}

	// ********* derived *********
	private final Set<Link> derivedElements = new HashSet<Link>();

	public void setDerivedElements(final Link[] derivedElements) {
		this.derivedElements.clear();
		if (derivedElements != null)
		{
			this.derivedElements.addAll(Arrays.asList(derivedElements));
		}
	}

	@OslcDescription("Description of Requirement::derived TBD")
	@OslcName("derived")
	@OslcOccurs(Occurs.ZeroOrMany)
	@OslcPropertyDefinition("http://localhost:8080/oslc4jmagicdraw/services/sysml-rdfvocabulary#Requirement_derived")
	@OslcTitle("derived")
	@OslcReadOnly(false)
	public Link[]  getDerivedElements() {
		 return derivedElements.toArray(new Link[derivedElements.size()]);
	}

	// ********* subRequirement *********
	private final Set<Link> subRequirements = new HashSet<Link>();

	public void setSubRequirements(final Link[] subRequirements) {
		this.subRequirements.clear();
		if (subRequirements != null)
		{
			this.subRequirements.addAll(Arrays.asList(subRequirements));
		}
	}

	@OslcDescription("Description of Requirement::subRequirement TBD")
	@OslcName("subRequirement")
	@OslcOccurs(Occurs.ZeroOrMany)
	@OslcPropertyDefinition("http://localhost:8080/oslc4jmagicdraw/services/sysml-rdfvocabulary#Requirement_subRequirement")
	@OslcTitle("subRequirement")
	@OslcReadOnly(false)
	public Link[]  getSubRequirements() {
		 return subRequirements.toArray(new Link[subRequirements.size()]);
	}

	// ********* derivedFrom *********
	private final Set<Link> derivedFromElements = new HashSet<Link>();

	public void setDerivedFromElements(final Link[] derivedFromElements) {
		this.derivedFromElements.clear();
		if (derivedFromElements != null)
		{
			this.derivedFromElements.addAll(Arrays.asList(derivedFromElements));
		}
	}

	@OslcDescription("Description of Requirement::derivedFrom TBD")
	@OslcName("derivedFrom")
	@OslcOccurs(Occurs.ZeroOrMany)
	@OslcPropertyDefinition("http://localhost:8080/oslc4jmagicdraw/services/sysml-rdfvocabulary#Requirement_derivedFrom")
	@OslcTitle("derivedFrom")
	@OslcReadOnly(false)
	public Link[]  getDerivedFromElements() {
		 return derivedFromElements.toArray(new Link[derivedFromElements.size()]);
	}

	private URI      serviceProvider;

	public void setServiceProvider(final URI serviceProvider)
	{		this.serviceProvider = serviceProvider;
	}

	@OslcDescription("The scope of a resource is a URI for the resource's OSLC Service Provider.")
	@OslcPropertyDefinition(OslcConstants.OSLC_CORE_NAMESPACE + "serviceProvider")
	@OslcRange(OslcConstants.TYPE_SERVICE_PROVIDER)
	@OslcTitle("Service Provider")	
	public URI getServiceProvider()
	{
		return serviceProvider;
	}

	// ********* rdfType *********
	private URI[] rdfTypes = new URI[3];

	public void setRdfTypes(final URI[] rdfTypes) {
		this.rdfTypes = rdfTypes;
	}

	@OslcDescription("Additional resource type URIs ")
	@OslcName("type")
	@OslcOccurs(Occurs.ZeroOrMany)
	@OslcPropertyDefinition(OslcConstants.RDF_NAMESPACE + "type")
	public URI[]  getRdfTypes() {
		 URI[] rdfTypes = {URI.create("http://localhost:8080/oslc4jmagicdraw/services/sysml-rdfvocabulary#NamedElement"), URI.create("http://eclipse.org/MBSE/Requirement")};
		 return rdfTypes;
	}

}