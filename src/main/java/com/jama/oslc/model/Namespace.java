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
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.lyo.oslc4j.core.annotation.OslcNamespaceDefinition;
import org.eclipse.lyo.oslc4j.core.annotation.OslcPropertyDefinition;
import org.eclipse.lyo.oslc4j.core.annotation.OslcSchema;
import org.eclipse.lyo.oslc4j.core.model.PrefixDefinition;

import com.jama.oslc.web.AdapterInitializer;

public interface Namespace {

    static Map<String, Method> getResourcePropertiesGetters(Class<?> resourceClass)
            throws IntrospectionException {
        Method getter;
        PropertyDescriptor[] properties;
        OslcPropertyDefinition definition;
        Map<String, Method> getters = new HashMap<>();
        properties = Introspector.getBeanInfo(resourceClass).getPropertyDescriptors();
        for(PropertyDescriptor property : properties) {
            getter = property.getReadMethod();
            definition = getter.getAnnotation(OslcPropertyDefinition.class);
            if (definition != null) {
                getters.put(definition.value(), getter);
            }
        }
        return getters;
    }

    static Map<String, String> getKnownPrefixes(Class<?> packageClass) {
        Map<String, String> prefixes = new HashMap<>();
        Package model = packageClass.getPackage();
        OslcSchema schema = model.getAnnotation(OslcSchema.class);
        for(OslcNamespaceDefinition namespace : schema.value()) {
            prefixes.put(namespace.prefix(), namespace.namespaceURI());
        }
        return prefixes;
    }

    static PrefixDefinition[] getKnownPrefixDefinitions(Class<?> packageClass)
            throws URISyntaxException {
        Package model = packageClass.getPackage();
        OslcSchema schema = model.getAnnotation(OslcSchema.class);
        OslcNamespaceDefinition[] namespaces = schema.value();
        PrefixDefinition definitions[] = new PrefixDefinition[namespaces.length];
        for(int i = 0; i<namespaces.length; i++) {
            definitions[i] = new PrefixDefinition();
            definitions[i].setPrefix(namespaces[i].prefix());
            definitions[i].setPrefixBase(new URI(namespaces[i].namespaceURI()));
        }
        return definitions;
    }

    Map<String, String> PREFIXES = getKnownPrefixes(Namespace.class);
    
//    String CONTEXT = "http://localhost:8080/jama-oslc-adapter/";
//    final String CONTEXT = "http://" + AdapterInitializer.domain + ":8080/jama-oslc-adapter/";
    String CONTEXT = "http://" + Constants.domain +":"+ Constants.portNumber +"/jama-oslc-adapter/";
    String RESOURCES = CONTEXT + "services/";
    String VOCABULARY = CONTEXT + "vocabulary/";
    
}
