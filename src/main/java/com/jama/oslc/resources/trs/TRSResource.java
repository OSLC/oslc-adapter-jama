package com.jama.oslc.resources.trs;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.eclipse.lyo.oslc4j.core.annotation.OslcService;
import org.eclipse.lyo.oslc4j.core.model.OslcMediaType;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import com.jama.oslc.model.Requirement;
import com.jama.oslc.model.trs.Base;
import com.jama.oslc.model.trs.ChangeLog;
import com.jama.oslc.model.trs.TRSConstants;
import com.jama.oslc.model.trs.TrackedResourceSet;




/*
 * Added in Lab 1.1, Modified in Lab 1.3.
 */
@Path("trs")
@OslcService(TRSConstants.TRS_TRACKED_RESOURCE_SET)
public class TRSResource {

	@GET
	@Produces({ OslcMediaType.TEXT_TURTLE, OslcMediaType.APPLICATION_RDF_XML,
			OslcMediaType.APPLICATION_XML, OslcMediaType.APPLICATION_JSON })
	public Response getRDFTrackedResourceSet(@Context ServletContext context) throws URISyntaxException {				
		TrackedResourceSet trackedResourceSet = (TrackedResourceSet) context.getAttribute("OSLC_TRS");		
		Response.ResponseBuilder builder;
        if (trackedResourceSet == null) {
            builder = Response.status(HttpServletResponse.SC_NOT_FOUND);
        } else {
            builder = Response.ok().entity(trackedResourceSet);
        }
        return builder.build();
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public void getHtmlTrackedResourceSet(@Context ServletContext context) throws URISyntaxException {
		HttpServletRequest request = ResteasyProviderFactory.getContextData(HttpServletRequest.class);
        HttpServletResponse response = ResteasyProviderFactory.getContextData(HttpServletResponse.class);		
		TrackedResourceSet trackedResourceSet = (TrackedResourceSet) context.getAttribute("OSLC_TRS");
		request.setAttribute("trackedResourceSet",
				trackedResourceSet);
		RequestDispatcher rd = request
				.getRequestDispatcher("/trs/trackedresourceset_html.jsp");
		try {
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebApplicationException(e);
		}
		
	}

	@Path(TRSConstants.TRS_TERM_BASE)
	@GET
	@Produces({ OslcMediaType.TEXT_TURTLE, OslcMediaType.APPLICATION_RDF_XML,
			OslcMediaType.APPLICATION_XML, OslcMediaType.APPLICATION_JSON })
	public Response getRDFBase(@Context ServletContext context) {
		Base base = (Base) context.getAttribute("OSLC_TRS_BASE");		
		Response.ResponseBuilder builder;
        if (base == null) {
            builder = Response.status(HttpServletResponse.SC_NOT_FOUND);
        } else {
            builder = Response.ok().entity(base);
        }
        return builder.build();
	}

	@Path(TRSConstants.TRS_TERM_BASE)
	@GET
	@Produces(MediaType.TEXT_HTML)
	public void getHtmlBase(@Context ServletContext context) {
		HttpServletRequest request = ResteasyProviderFactory.getContextData(HttpServletRequest.class);
        HttpServletResponse response = ResteasyProviderFactory.getContextData(HttpServletResponse.class);		
        Base base = (Base) context.getAttribute("OSLC_TRS_BASE");
		request.setAttribute("base",
				base);
		RequestDispatcher rd = request
				.getRequestDispatcher("/trs/base_html.jsp");
		try {
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebApplicationException(e);
		}
	}

	@Path(TRSConstants.TRS_TERM_CHANGE_LOG)
	@GET
	@Produces({ OslcMediaType.TEXT_TURTLE, OslcMediaType.APPLICATION_RDF_XML,
			OslcMediaType.APPLICATION_XML, OslcMediaType.APPLICATION_JSON })
	public Response getRDFChangeLog(@Context ServletContext context) {
		ChangeLog changeLog = (ChangeLog) context.getAttribute("OSLC_TRS_CHANGELOG");		
		Response.ResponseBuilder builder;
        if (changeLog == null) {
            builder = Response.status(HttpServletResponse.SC_NOT_FOUND);
        } else {
            builder = Response.ok().entity(changeLog);
        }
        return builder.build();
	}
	
//	@Path(TRSConstants.TRS_TERM_CHANGE_LOG +"/{page}")
//	@GET
//	@Produces({ OslcMediaType.TEXT_TURTLE, OslcMediaType.APPLICATION_RDF_XML,
//			OslcMediaType.APPLICATION_XML, OslcMediaType.APPLICATION_JSON })
//	public ChangeLog getChangeLog(@PathParam("page") String pagenum) {
//		return ChangeHistory.getChangeLog(pagenum);
//	}
//
//	@Path(TRSConstants.TRS_TERM_CHANGE_LOG)
//	@GET
//	@Produces(MediaType.TEXT_HTML)
//	public Viewable getHtmlChangeLog() {
//		ChangeLog changeLog = ChangeHistory.getChangeLog();
//		if (changeLog != null) {
//			
//				httpServletRequest.setAttribute("bugzillaUri",
//						"https://landfill.bugzilla.org/bugzilla-4.2-branch/");
//				httpServletRequest.setAttribute("changeLog", changeLog);
//				return new Viewable("/integrity/changelog_html.jsp");
//			
//		}
//		return null;
//	}
//	
//	
//	
	//@Path(TRSConstants.TRS_TERM_CHANGE_LOG +"/{page}")
	@Path(TRSConstants.TRS_TERM_CHANGE_LOG)
	@GET
	@Produces(MediaType.TEXT_HTML)
	public void getHtmlChangeLog(@Context ServletContext context) {
		HttpServletRequest request = ResteasyProviderFactory.getContextData(HttpServletRequest.class);
        HttpServletResponse response = ResteasyProviderFactory.getContextData(HttpServletResponse.class);		
        ChangeLog changeLog = (ChangeLog) context.getAttribute("OSLC_TRS_CHANGELOG");
		request.setAttribute("changeLog",
				changeLog);
		RequestDispatcher rd = request
				.getRequestDispatcher("/trs/changelog_html.jsp");
		try {
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebApplicationException(e);
		}
	}
}
