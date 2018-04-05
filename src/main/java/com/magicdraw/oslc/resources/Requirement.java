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
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;



import org.eclipse.lyo.oslc4j.core.annotation.OslcDescription;
import org.eclipse.lyo.oslc4j.core.annotation.OslcName;
import org.eclipse.lyo.oslc4j.core.annotation.OslcOccurs;
import org.eclipse.lyo.oslc4j.core.annotation.OslcPropertyDefinition;
import org.eclipse.lyo.oslc4j.core.annotation.OslcRange;
import org.eclipse.lyo.oslc4j.core.annotation.OslcReadOnly;
import org.eclipse.lyo.oslc4j.core.annotation.OslcRepresentation;
import org.eclipse.lyo.oslc4j.core.annotation.OslcTitle;
import org.eclipse.lyo.oslc4j.core.annotation.OslcValueType;
import org.eclipse.lyo.oslc4j.core.model.AbstractResource;
import org.eclipse.lyo.oslc4j.core.model.Link;
import org.eclipse.lyo.oslc4j.core.model.Occurs;
import org.eclipse.lyo.oslc4j.core.model.OslcConstants;
import org.eclipse.lyo.oslc4j.core.model.Representation;
import org.eclipse.lyo.oslc4j.core.model.ValueType;

public class Requirement extends AbstractResource {

	public Requirement()
	           throws URISyntaxException
	    {
	        super();

	        rdfTypes.add(new URI(Constants.TYPE_REQUIREMENT));
	    }

	    public Requirement(final URI about)
	           throws URISyntaxException
	    {
	        super(about);

	        rdfTypes.add(new URI(Constants.TYPE_REQUIREMENT));
	    }
	
	
	// OSLC Core: Common Properties
	private String title;
	private String description;
	private String identifier;
	private String shortTitle;
	private Date created;
	private Date modified;

	private final Set<String> subjects = new TreeSet<String>();
	private final Set<URI> rdfTypes = new TreeSet<URI>();

	// Relationship properties: This grouping of properties are used to identify
	// relationships between resources managed by other OSLC Service Providers
	private final Set<Link> elaboratedBy = new HashSet<Link>();
	private final Set<Link> elaborates = new HashSet<Link>();
	private final Set<Link> specifiedBy = new HashSet<Link>();
	private final Set<Link> specifies = new HashSet<Link>();
	private final Set<Link> affectedBy = new HashSet<Link>();
	private final Set<Link> trackedBy = new HashSet<Link>();
	private final Set<Link> implementedBy = new HashSet<Link>();
	private final Set<Link> validatedBy = new HashSet<Link>();
	private final Set<Link> satisfiedBy = new HashSet<Link>();
	private final Set<Link> satisfies = new HashSet<Link>();
	private final Set<Link> decomposedBy = new HashSet<Link>();
	private final Set<Link> decomposes = new HashSet<Link>();
	private final Set<Link> constrainedBy = new HashSet<Link>();
	private final Set<Link> constrains = new HashSet<Link>();

	@OslcDescription("Title (reference: Dublin Core) or often a single line summary of the resource represented as rich text in XHTML content.")
	@OslcOccurs(Occurs.ExactlyOne)
	@OslcPropertyDefinition(OslcConstants.DCTERMS_NAMESPACE + "title")
	@OslcTitle("Title")
	@OslcValueType(ValueType.XMLLiteral)
	public String getTitle() {
		return title;
	}

	@OslcDescription("Descriptive text (reference: Dublin Core) about resource represented as rich text in XHTML content.")
	@OslcPropertyDefinition(OslcConstants.DCTERMS_NAMESPACE + "description")
	@OslcTitle("Description")
	@OslcValueType(ValueType.XMLLiteral)
	public String getDescription() {
		return description;
	}

	@OslcDescription("A unique identifier for a resource. Assigned by the service provider when a resource is created. Not intended for end-user display.")
	@OslcOccurs(Occurs.ExactlyOne)
	@OslcPropertyDefinition(OslcConstants.DCTERMS_NAMESPACE + "identifier")
	@OslcReadOnly
	@OslcTitle("Identifier")
	public String getIdentifier() {
		return identifier;
	}

	@OslcDescription("Short name identifying a resource, often used as an abbreviated identifier for presentation to end-users.")
	@OslcPropertyDefinition(OslcConstants.OSLC_CORE_NAMESPACE + "shortTitle")
	@OslcTitle("Short Title")
	@OslcValueType(ValueType.XMLLiteral)
	public String getShortTitle() {
		return shortTitle;
	}

	@OslcDescription("Timestamp of resource creation.")
	@OslcPropertyDefinition(OslcConstants.DCTERMS_NAMESPACE + "created")
	@OslcReadOnly
	@OslcTitle("Created")
	public Date getCreated() {
		return created;
	}

	@OslcDescription("Timestamp last latest resource modification.")
	@OslcPropertyDefinition(OslcConstants.DCTERMS_NAMESPACE + "modified")
	@OslcReadOnly
	@OslcTitle("Modified")
	public Date getModified() {
		return modified;
	}

	@OslcDescription("Tag or keyword for a resource. Each occurrence of a dcterms:subject property denotes an additional tag for the resource.")
	@OslcName("subject")
	@OslcPropertyDefinition(OslcConstants.DCTERMS_NAMESPACE + "subject")
	@OslcReadOnly(false)
	@OslcTitle("Subjects")
	public String[] getSubjects() {
		return subjects.toArray(new String[subjects.size()]);
	}

	

	

	@OslcDescription("The resource type URIs.")
	@OslcName("type")
	@OslcPropertyDefinition(OslcConstants.RDF_NAMESPACE + "type")
	@OslcTitle("Types")
	public URI[] getRdfTypes() {
		return rdfTypes.toArray(new URI[rdfTypes.size()]);
	}

	@OslcDescription("The subject is elaborated by the object.")
	@OslcName("elaboratedBy")
	@OslcPropertyDefinition(Constants.REQUIREMENTS_MANAGEMENT_NAMESPACE
			+ "elaboratedBy")
	@OslcReadOnly(false)
	@OslcTitle("Elaborated By")
	public Link[] getElaboratedBy() {
		return elaboratedBy.toArray(new Link[elaboratedBy.size()]);
	}
	
	@OslcDescription("The object is elaborated by the subject.")
	@OslcName("elaborates")
	@OslcPropertyDefinition(Constants.REQUIREMENTS_MANAGEMENT_NAMESPACE
			+ "elaborates")
	@OslcReadOnly(false)
	@OslcTitle("Elaborates")
	public Link[] getElaborates() {
		return elaborates.toArray(new Link[elaborates.size()]);
	}
	
	@OslcDescription("The subject is specified by the object.")
	@OslcName("specifiedBy")
	@OslcPropertyDefinition(Constants.REQUIREMENTS_MANAGEMENT_NAMESPACE
			+ "specifiedBy")
	@OslcReadOnly(false)
	@OslcTitle("SpecifiedBy")
	public Link[] getSpecifiedBy() {
		return specifiedBy.toArray(new Link[specifiedBy.size()]);
	}
	
	@OslcDescription("The object is specified by the subject.")
	@OslcName("specifies")
	@OslcPropertyDefinition(Constants.REQUIREMENTS_MANAGEMENT_NAMESPACE
			+ "specifies")
	@OslcReadOnly(false)
	@OslcTitle("Specifies")
	public Link[] getSpecifies() {
		return specifies.toArray(new Link[specifies.size()]);
	}
	
	@OslcDescription("Requirement is affected by a resource, such as a defect or issue.")
	@OslcName("affectedBy")
	@OslcPropertyDefinition(Constants.REQUIREMENTS_MANAGEMENT_NAMESPACE
			+ "affectedBy")
	@OslcReadOnly(false)
	@OslcTitle("Affected By")
	public Link[] getAffectedBy() {
		return affectedBy.toArray(new Link[affectedBy.size()]);
	}
	
	@OslcDescription("Resource, such as a change request, which tracks this requirement.")
	@OslcName("trackedBy")
	@OslcPropertyDefinition(Constants.REQUIREMENTS_MANAGEMENT_NAMESPACE
			+ "trackedBy")
	@OslcReadOnly(false)
	@OslcTitle("Tracked By")
	public Link[] getTrackedBy() {
		return trackedBy.toArray(new Link[trackedBy.size()]);
	}
	
	@OslcDescription("Resource, such as a change request, which implements this requirement.")
	@OslcName("implementedBy")
	@OslcPropertyDefinition(Constants.REQUIREMENTS_MANAGEMENT_NAMESPACE
			+ "implementedBy")
	@OslcReadOnly(false)
	@OslcTitle("Implemented By")
	public Link[] getImplementedBy() {
		return implementedBy.toArray(new Link[implementedBy.size()]);
	}
	
	@OslcDescription("Resource, such as a test case, which validates this requirement.")
	@OslcName("validatedBy")
	@OslcPropertyDefinition(Constants.REQUIREMENTS_MANAGEMENT_NAMESPACE
			+ "validatedBy")
	@OslcReadOnly(false)
	@OslcTitle("Validated By")
	public Link[] getValidatedBy() {
		return validatedBy.toArray(new Link[validatedBy.size()]);
	}
	
	@OslcDescription("The subject is satisfied by the object.")
	@OslcName("satisfiedBy")
	@OslcPropertyDefinition(Constants.REQUIREMENTS_MANAGEMENT_NAMESPACE
			+ "satisfiedBy")
	@OslcReadOnly(false)
	@OslcTitle("Satisfied By")
	public Link[] getSatisfiedBy() {
		return satisfiedBy.toArray(new Link[satisfiedBy.size()]);
	}
	
	@OslcDescription("The object is satisfied by the subject.")
	@OslcName("satisfies")
	@OslcPropertyDefinition(Constants.REQUIREMENTS_MANAGEMENT_NAMESPACE
			+ "satisfies")
	@OslcReadOnly(false)
	@OslcTitle("Satisfies")
	public Link[] getSatisfies() {
		return satisfies.toArray(new Link[satisfies.size()]);
	}
	
	@OslcDescription("The subject is decomposed by the object.")
	@OslcName("decomposedBy")
	@OslcPropertyDefinition(Constants.REQUIREMENTS_MANAGEMENT_NAMESPACE
			+ "decomposedBy")
	@OslcReadOnly(false)
	@OslcTitle("Decomposed By")
	public Link[] getDecomposedBy() {
		return decomposedBy.toArray(new Link[decomposedBy.size()]);
	}
	
	@OslcDescription("The object is decomposed by the subject.")
	@OslcName("decomposes")
	@OslcPropertyDefinition(Constants.REQUIREMENTS_MANAGEMENT_NAMESPACE
			+ "decomposes")
	@OslcReadOnly(false)
	@OslcTitle("Decomposes")
	public Link[] getDecomposes() {
		return decomposes.toArray(new Link[decomposes.size()]);
	}
	
	@OslcDescription("The subject is constrained by the object.")
	@OslcName("constrainedBy")
	@OslcPropertyDefinition(Constants.REQUIREMENTS_MANAGEMENT_NAMESPACE
			+ "constrainedBy")
	@OslcReadOnly(false)
	@OslcTitle("Constrained By")
	public Link[] getConstrainedBy() {
		return constrainedBy.toArray(new Link[constrainedBy.size()]);
	}
	
	@OslcDescription("The object is constrained by the subject.")
	@OslcName("constrains")
	@OslcPropertyDefinition(Constants.REQUIREMENTS_MANAGEMENT_NAMESPACE
			+ "constrains")
	@OslcReadOnly(false)
	@OslcTitle("constrains")
	public Link[] getConstrains() {
		return constrains.toArray(new Link[constrains.size()]);
	}
	
	public void setElaboratedBy(final Link[] elaboratedBy)
    {
        this.elaboratedBy.clear();

        if (elaboratedBy != null)
        {
            this.elaboratedBy.addAll(Arrays.asList(elaboratedBy));
        }
    }
	
	public void setElaborates(final Link[] elaborates)
    {
        this.elaborates.clear();

        if (elaborates != null)
        {
            this.elaborates.addAll(Arrays.asList(elaborates));
        }
    }
	
	public void setSpecifiedBy(final Link[] specifiedBy)
    {
        this.specifiedBy.clear();

        if (elaborates != null)
        {
            this.specifiedBy.addAll(Arrays.asList(specifiedBy));
        }
    }
	
	public void setSpecifies(final Link[] specifies)
    {
        this.specifies.clear();

        if (elaborates != null)
        {
            this.specifies.addAll(Arrays.asList(specifies));
        }
    }
	
	public void setAffectedBy(final Link[] affectedBy)
    {
        this.affectedBy.clear();

        if (affectedBy != null)
        {
            this.affectedBy.addAll(Arrays.asList(affectedBy));
        }
    }
	
	public void setTrackedBy(final Link[] trackedBy)
    {
        this.trackedBy.clear();

        if (trackedBy != null)
        {
            this.trackedBy.addAll(Arrays.asList(trackedBy));
        }
    }
	
	public void setImplementedBy(final Link[] implementedBy)
    {
        this.implementedBy.clear();

        if (implementedBy != null)
        {
            this.implementedBy.addAll(Arrays.asList(implementedBy));
        }
    }
	
	public void setValidatedBy(final Link[] validatedBy)
    {
        this.validatedBy.clear();

        if (validatedBy != null)
        {
            this.validatedBy.addAll(Arrays.asList(validatedBy));
        }
    }
	
	public void setSatisfiedBy(final Link[] satisfiedBy)
    {
        this.satisfiedBy.clear();

        if (satisfiedBy != null)
        {
            this.satisfiedBy.addAll(Arrays.asList(satisfiedBy));
        }
    }
	
	public void setSatisfies(final Link[] satisfies)
    {
        this.satisfies.clear();

        if (satisfies != null)
        {
            this.satisfies.addAll(Arrays.asList(satisfies));
        }
    }
	
	public void setDecomposedBy(final Link[] decomposedBy)
    {
        this.decomposedBy.clear();

        if (satisfies != null)
        {
            this.decomposedBy.addAll(Arrays.asList(decomposedBy));
        }
    }
	
	public void setDecomposes(final Link[] decomposes)
    {
        this.decomposes.clear();

        if (decomposes != null)
        {
            this.decomposes.addAll(Arrays.asList(decomposes));
        }
    }
	
	public void setConstrainedBy(final Link[] constrainedBy)
    {
        this.constrainedBy.clear();

        if (constrainedBy != null)
        {
            this.constrainedBy.addAll(Arrays.asList(constrainedBy));
        }
    }
	
	public void setConstrains(final Link[] constrains)
    {
        this.constrains.clear();

        if (constrains != null)
        {
            this.constrains.addAll(Arrays.asList(constrains));
        }
    }
	
	
	public void setSubjects(final String[] subjects)
    {
        this.subjects.clear();

        if (subjects != null)
        {
            this.subjects.addAll(Arrays.asList(subjects));
        }
    }
	
	
	
	public void setRdfTypes(final URI[] rdfTypes)
    {
        this.rdfTypes.clear();

        if (rdfTypes != null)
        {
            this.rdfTypes.addAll(Arrays.asList(rdfTypes));
        }
    }
	
	
	
	
	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}
}
