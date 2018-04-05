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

package com.jama.oslc.model;

import java.beans.IntrospectionException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.lyo.client.oslc.resources.RmConstants;
import org.eclipse.lyo.oslc4j.core.annotation.*;
import org.eclipse.lyo.oslc4j.core.model.Link;
import org.eclipse.lyo.oslc4j.core.model.Occurs;
import org.eclipse.lyo.oslc4j.core.model.OslcConstants;
import org.eclipse.lyo.oslc4j.core.model.ValueType;


@OslcNamespace(Namespace.VOCABULARY)
@OslcResourceShape(title = "Jama Requirement Resource Shape", describes = Namespace.VOCABULARY + "Requirement")
public class Requirement extends org.eclipse.lyo.client.oslc.resources.Requirement
{
    public static final String TYPE_URI = Namespace.VOCABULARY + "Requirement";
    public static final String NAMESPACE = TYPE_URI + "#";
    public static final String SHAPE_URI = OslcConstants.PATH_RESOURCE_SHAPES + "/Requirement";
    public static final Map<String, Method> PROPERTIES_GETTERS;

    private String documentKey;

    static {
        try {
            PROPERTIES_GETTERS = Namespace.getResourcePropertiesGetters(Requirement.class);
        } catch (IntrospectionException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public void setDocumentKey(String documentKey) {
        this.documentKey = documentKey;
    }

    @OslcDescription("documentKey (reference: https://dev.jamasoftware.com/rest#operation_getItems) specific parameter available on abstractitems that provides the ability to retrieve an item or list of items by the user known identifier,")
    @OslcOccurs(Occurs.ExactlyOne)
    @OslcPropertyDefinition(Namespace.VOCABULARY + "requirement_documentKey")
    @OslcTitle("documentKey")
    @OslcValueType(ValueType.XMLLiteral)
    public String getDocumentKey() {
        return this.documentKey;
    }
    
    private String globalId;
    
    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    @OslcDescription("globalId")
    @OslcOccurs(Occurs.ExactlyOne)
    @OslcPropertyDefinition(Namespace.VOCABULARY + "requirement_globalId")
    @OslcTitle("globalId")
    @OslcValueType(ValueType.XMLLiteral)
    public String getGlobalId() {
        return this.globalId;
    }
    
    private String project;
    
    public void setProject(String project) {
        this.project = project;
    }

    @OslcDescription("project")
    @OslcOccurs(Occurs.ExactlyOne)
    @OslcPropertyDefinition(Namespace.VOCABULARY + "requirement_project")
    @OslcTitle("project")
    @OslcValueType(ValueType.XMLLiteral)
    public String getProject() {
        return this.project;
    }
    
    private String parentID;
    
    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    @OslcDescription("parentID")
    @OslcOccurs(Occurs.ExactlyOne)
    @OslcPropertyDefinition(Namespace.VOCABULARY + "requirement_parentID")
    @OslcTitle("parentID")
    @OslcValueType(ValueType.XMLLiteral)
    public String getParentID() {
        return this.parentID;
    }

    public String getDigestion()
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String myState = getAbout().toASCIIString();
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(myState.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }
    
    // ********* derivedFrom *********
 	private final HashSet<Link> derivedFrom = new HashSet<Link>();

 	public void setDerivedFrom(final Link[] derivedFrom) {
 		this.derivedFrom.clear();
 		if (derivedFrom != null)
 		{
 			this.derivedFrom.addAll(Arrays.asList(derivedFrom));
 		}
 	}

 	@OslcDescription("derivedFrom")
 	@OslcName("derivedFrom")
 	@OslcOccurs(Occurs.ZeroOrMany)
 	@OslcPropertyDefinition(Namespace.VOCABULARY + "derivedFrom")
 	@OslcTitle("derivedFrom")
 	@OslcReadOnly(false)
 	public Link[]  getDerivedFrom() {
 		 return derivedFrom.toArray(new Link[derivedFrom.size()]);
 	}

}