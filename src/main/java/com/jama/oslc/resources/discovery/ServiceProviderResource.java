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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.eclipse.lyo.oslc4j.core.annotation.OslcService;
import org.eclipse.lyo.oslc4j.core.model.OslcConstants;
import org.eclipse.lyo.oslc4j.core.model.OslcMediaType;
import org.eclipse.lyo.oslc4j.core.model.ServiceProvider;
import org.eclipse.lyo.oslc4j.core.model.ServiceProviderCatalog;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import com.magicdraw.oslc.resources.Constants;

@OslcService(Constants.CHANGE_MANAGEMENT_DOMAIN)
@Path(OslcConstants.PATH_SERVICE_PROVIDER)
public class ServiceProviderResource {

	@GET
	@Path("/{identifier}")
	@Produces({ OslcMediaType.APPLICATION_RDF_XML })
	public ServiceProvider getServiceProvider(@Context ServletContext context,
			@PathParam("identifier") String identifier) {
		ServiceProviderCatalog catalog;
		catalog = (ServiceProviderCatalog) context.getAttribute("OSLC_CATALOG");
		for (ServiceProvider provider : catalog.getServiceProviders()) {
			if (identifier.equals(provider.getIdentifier())) {
				return provider;
			}
		}
		throw new WebApplicationException(HttpServletResponse.SC_NOT_FOUND);
	}

	@GET
	@Path("/{identifier}")
	@Produces(value = { MediaType.TEXT_HTML })
	public void renderResource(@Context ServletContext context, @QueryParam("oslc.where") String where,
			@QueryParam("oslc.select") String select, @PathParam("identifier") final String identifier) {
		HttpServletRequest request = ResteasyProviderFactory.getContextData(HttpServletRequest.class);
		HttpServletResponse response = ResteasyProviderFactory.getContextData(HttpServletResponse.class);

		ServiceProviderCatalog serviceProviderCatalog = (ServiceProviderCatalog) context.getAttribute("OSLC_CATALOG");
		String requestURL = request.getRequestURL().toString();
		for (ServiceProvider serviceProvider : serviceProviderCatalog.getServiceProviders()) {
			if (serviceProvider.getAbout().toString().equals(requestURL)) {
				request.setAttribute("serviceProvider", serviceProvider);
//				System.out.println(serviceProvider.getTitle());
//				System.out.println(serviceProvider.getServices());
//				for (Service service : serviceProvider.getServices()) {
//					for (QueryCapability queryCapability : service.getQueryCapabilities()) {
//						System.out.println(queryCapability.getQueryBase());
//						
//					}
//					
//				}
				try {
					request.getRequestDispatcher("/jama/serviceprovider_html.jsp").forward(request, response);
				} catch (Exception e) {
					e.printStackTrace();
					throw new WebApplicationException(e);
				}
				break;
			}
		}

	}

}
