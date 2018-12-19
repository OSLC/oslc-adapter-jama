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

package com.jama.oslc.resources.discovery;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.eclipse.lyo.oslc4j.core.annotation.OslcService;
import org.eclipse.lyo.oslc4j.core.exception.OslcCoreApplicationException;
import org.eclipse.lyo.oslc4j.core.model.OslcConstants;
import org.eclipse.lyo.oslc4j.core.model.OslcMediaType;
import org.eclipse.lyo.oslc4j.core.model.ResourceShape;
import org.eclipse.lyo.oslc4j.core.model.ResourceShapeFactory;

import com.jama.oslc.model.Namespace;

@OslcService(com.magicdraw.oslc.resources.Constants.CHANGE_MANAGEMENT_DOMAIN)
@Path(OslcConstants.PATH_RESOURCE_SHAPES)
public class ResourceShapeResource {

	@GET
	@Path("{resourceType}")
	@Produces({OslcMediaType.APPLICATION_RDF_XML})
	public Response getResourceShape(@PathParam("resourceType") String resourceType) {
		ResourceShape shape;
		Class<?> resourceClass;
		Response.ResponseBuilder builder;
		String className = "com.jama.oslc.model." + resourceType;
		try {
			resourceClass = Class.forName(className);
			shape = ResourceShapeFactory.createResourceShape(
					Namespace.RESOURCES.substring(0, Namespace.RESOURCES.length()-1),
					OslcConstants.PATH_RESOURCE_SHAPES,
					resourceType,
					resourceClass);
			builder = Response.ok(shape);
		} catch (ClassNotFoundException ex) {
			builder = Response.status(HttpServletResponse.SC_NOT_FOUND);
		} catch (OslcCoreApplicationException | URISyntaxException ex) {			
			builder = Response.serverError();
		}
		return builder.build();
	}

}
