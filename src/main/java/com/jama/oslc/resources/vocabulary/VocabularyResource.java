package com.jama.oslc.resources.vocabulary;

import java.net.URISyntaxException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.lyo.oslc4j.core.annotation.OslcService;
import org.eclipse.lyo.oslc4j.core.model.OslcMediaType;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import com.jama.oslc.model.trs.TRSConstants;
import com.jama.oslc.model.trs.TrackedResourceSet;

@Path("vocabulary")
@OslcService("http://jamacloud.com/vocabulary")
public class VocabularyResource {

	
	@GET
	@Produces({ OslcMediaType.TEXT_TURTLE, OslcMediaType.APPLICATION_RDF_XML,
			OslcMediaType.APPLICATION_XML, OslcMediaType.APPLICATION_JSON })
	public void getRDFTrackedResourceSet(@Context ServletContext context) throws URISyntaxException {				
		HttpServletRequest request = ResteasyProviderFactory.getContextData(HttpServletRequest.class);
        HttpServletResponse response = ResteasyProviderFactory.getContextData(HttpServletResponse.class);						
		RequestDispatcher rd = request
				.getRequestDispatcher("/rdfvocabulary/jamaRDFVocabulary.rdf");		
		
		try {
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebApplicationException(e);
		}
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public void getHtmlTrackedResourceSet(@Context ServletContext context) throws URISyntaxException {
		HttpServletRequest request = ResteasyProviderFactory.getContextData(HttpServletRequest.class);
        HttpServletResponse response = ResteasyProviderFactory.getContextData(HttpServletResponse.class);						
		RequestDispatcher rd = request
				.getRequestDispatcher("/rdfvocabulary/jamaRDFVocabulary.jsp");		
		
		try {
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebApplicationException(e);
		}
		
	}
	
}
