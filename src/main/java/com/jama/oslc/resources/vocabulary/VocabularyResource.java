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

import com.jama.oslc.model.Constants;
import com.jama.oslc.model.trs.TRSConstants;
import com.jama.oslc.model.trs.TrackedResourceSet;

@Path("vocabulary")
@OslcService("http://jamacloud.com/vocabulary")
public class VocabularyResource {

	
	@GET
	@Produces({ OslcMediaType.TEXT_TURTLE, OslcMediaType.APPLICATION_RDF_XML,
			OslcMediaType.APPLICATION_XML, OslcMediaType.APPLICATION_JSON })
	public Response getRDFTrackedResourceSet(@Context ServletContext context) throws URISyntaxException {				

		String jamaVocabPath = Constants.VOCABULARY;

	      StringBuilder builder = new StringBuilder();

	      builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
	      builder.append("<rdf:RDF\n");
	      builder.append("	xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n");
	      builder.append("	xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n");
	      builder.append("	xmlns:dcterms=\"http://purl.org/dc/terms/\"\n");
	      builder.append("	xmlns:jama=\"" + jamaVocabPath + "\">\n");
	      builder.append("	<rdfs:Class rdf:about=\"jama:Requirement\">\n");
	      builder.append("		<rdfs:label xml:lang=\"en-GB\">Requirement</rdfs:label>\n");
	      builder.append("		<rdfs:isDefinedBy rdf:resource=\"" + jamaVocabPath + "\"/>\n");
	      builder.append("		<dcterms:issued>2018-01-01</dcterms:issued>\n");
	      builder.append("	</rdfs:Class>\n");
	      builder.append("	<rdf:Property rdf:about=\"jama:requirement_documentKey\">\n");
	      builder.append("		<rdfs:label xml:lang=\"en-GB\">DocumentKey</rdfs:label>\n");
	      builder.append("		<rdfs:isDefinedBy rdf:resource=\"" + jamaVocabPath + "\"/>\n");
	      builder.append("		<dcterms:issued>2018-01-01</dcterms:issued>\n");
	      builder.append("	</rdf:Property>\n");
	      builder.append("	<rdf:Property rdf:about=\"jama:requirement_globalId\">\n");
	      builder.append("		<rdfs:label xml:lang=\"en-GB\">GlobalId</rdfs:label>\n");
	      builder.append("		<rdfs:isDefinedBy rdf:resource=\"" + jamaVocabPath + "\"/>\n");
	      builder.append("		<dcterms:issued>2018-01-01</dcterms:issued>\n");
	      builder.append("	</rdf:Property>\n");
	      builder.append("	<rdf:Property rdf:about=\"jama:requirement_project\">\n");
	      builder.append("		<rdfs:label xml:lang=\"en-GB\">Project</rdfs:label>\n");
	      builder.append("		<rdfs:isDefinedBy rdf:resource=\"" + jamaVocabPath + "\"/>\n");
	      builder.append("		<dcterms:issued>2018-01-01</dcterms:issued>\n");
	      builder.append("	</rdf:Property>\n");
	      builder.append("</rdf:RDF>");

	      
	      String responseBody = builder.toString();
	      
	      return Response.ok().entity(responseBody).header("max-age", 0).header("pragma", "no-cache")
	          .header("Cache-Control", "no-cache").header("OSLC-Core-Version", 2.0)
	          .header("Content-Length", responseBody.getBytes().length).type(OslcMediaType.APPLICATION_RDF_XML)
	          .build();
		
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public void getHtmlTrackedResourceSet(@Context ServletContext context) throws URISyntaxException {
		HttpServletRequest request = ResteasyProviderFactory.getContextData(HttpServletRequest.class);
        HttpServletResponse response = ResteasyProviderFactory.getContextData(HttpServletResponse.class);		
        
        context.setAttribute("OSLC_VOCAB", Constants.VOCABULARY);
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
