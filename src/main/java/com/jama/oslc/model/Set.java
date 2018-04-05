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
import java.util.Map;

import org.eclipse.lyo.client.oslc.resources.RmConstants;
import org.eclipse.lyo.oslc4j.core.annotation.*;
import org.eclipse.lyo.oslc4j.core.model.AbstractResource;
import org.eclipse.lyo.oslc4j.core.model.Occurs;
import org.eclipse.lyo.oslc4j.core.model.OslcConstants;
import org.eclipse.lyo.oslc4j.core.model.ValueType;


@OslcNamespace(Namespace.VOCABULARY)
@OslcResourceShape(title = "Jama Set Resource Shape", describes = Namespace.VOCABULARY + "Set")
public class Set extends AbstractResource
{
    public static final String TYPE_URI = Namespace.VOCABULARY + "Set";
    public static final String NAMESPACE = TYPE_URI + "#";
    public static final String SHAPE_URI = OslcConstants.PATH_RESOURCE_SHAPES + "/Set";
    public static final Map<String, Method> PROPERTIES_GETTERS;

    

    static {
        try {
            PROPERTIES_GETTERS = Namespace.getResourcePropertiesGetters(Set.class);
        } catch (IntrospectionException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

   
    
    
    
    private String id;
    
    public void setId(String id) {
        this.id = id;
    }

    @OslcDescription("id")
    @OslcOccurs(Occurs.ExactlyOne)
    @OslcPropertyDefinition(Namespace.VOCABULARY + "set_id")
    @OslcTitle("id")
    @OslcValueType(ValueType.XMLLiteral)
    public String getId() {
        return this.id;
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
    
    
    
    private String name;
    
    public void setName(String name) {
        this.name = name;
    }

    @OslcDescription("name")
    @OslcOccurs(Occurs.ExactlyOne)
    @OslcPropertyDefinition(Namespace.VOCABULARY + "set_name")
    @OslcTitle("name")
    @OslcValueType(ValueType.XMLLiteral)
    public String getName() {
        return this.name;
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

}