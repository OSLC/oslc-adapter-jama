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

package com.jama.oslc.resources;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.jama.oslc.model.Constants;
import com.jama.oslc.model.Namespace;
import com.jama.oslc.model.Requirement;
import com.jama.oslc.web.AdapterInitializer;

import org.apache.commons.codec.binary.Base64;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONException;
import org.apache.wink.json4j.JSONObject;
import org.eclipse.lyo.oslc4j.core.annotation.OslcCreationFactory;
import org.eclipse.lyo.oslc4j.core.annotation.OslcDialog;
import org.eclipse.lyo.oslc4j.core.annotation.OslcQueryCapability;
import org.eclipse.lyo.oslc4j.core.annotation.OslcService;
import org.eclipse.lyo.oslc4j.core.exception.OslcCoreApplicationException;
import org.eclipse.lyo.oslc4j.core.model.OslcMediaType;
import org.eclipse.lyo.oslc4j.core.model.Preview;
import org.eclipse.lyo.oslc4j.core.model.Compact;
import org.eclipse.lyo.oslc4j.core.model.Link;
import org.eclipse.lyo.oslc4j.core.model.OslcConstants;
import org.eclipse.lyo.oslc4j.core.model.ResponseInfoCollection;
import org.eclipse.lyo.oslc4j.core.model.ServiceProviderCatalog;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

@Path("{projectName}/requirement")
@OslcService("http://jamacloud.com/#requirementSpec")
public class RequirementResource {

	@GET
	@Produces(value = { MediaType.TEXT_HTML })
	public void getHtmlRequirements(@Context ServletContext context, @PathParam("projectName") final String projectName,
			@QueryParam("oslc.where") String where, @QueryParam("oslc.select") String select) {
		HttpServletRequest request = ResteasyProviderFactory.getContextData(HttpServletRequest.class);
		HttpServletResponse response = ResteasyProviderFactory.getContextData(HttpServletResponse.class);

		try {
			Map<URI, Requirement> requirements = (Map<URI, Requirement>) context.getAttribute("OSLC_REQUIREMENTS");

			Collection<Requirement> requirementsOfProject = new ArrayList<Requirement>();
			for (Requirement requirement : requirements.values()) {
				if (requirement.getProject().equals(projectName)) {
					requirementsOfProject.add(requirement);
				}
			}
			Collection<Requirement> results = filterResources(requirementsOfProject, where);

			request.setAttribute("elements", results);
			request.setAttribute("modelName", projectName);
			request.getRequestDispatcher("/jama/requirements_html.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebApplicationException(e);
		}
	}

	@GET
	@Path("{uri}")
	@Produces(MediaType.TEXT_HTML)
	public void getHtmlRequirement(@Context ServletContext context, @PathParam("uri") final String qualifiedName,
			@QueryParam("oslc.properties") final String propertiesString,
			@QueryParam("oslc.prefix") final String prefix) throws URISyntaxException, IOException {
		HttpServletRequest request = ResteasyProviderFactory.getContextData(HttpServletRequest.class);
		HttpServletResponse response = ResteasyProviderFactory.getContextData(HttpServletResponse.class);
		Map<String, Requirement> requirements = (Map<String, Requirement>) context.getAttribute("OSLC_REQUIREMENTS");
//		String requestURL = getURL(request);
		String requestURL = request.getRequestURL().toString();
		Requirement requirementToReturn = requirements.get(requestURL);

		if (requirementToReturn != null) {
			request.setAttribute("requirement", requirementToReturn);
			request.setAttribute("requestURL", requestURL);
			RequestDispatcher rd = request.getRequestDispatcher("/jama/requirement_html.jsp");
			try {
				rd.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				throw new WebApplicationException(e);
			}
		}
	}

	@GET
	@Path("/{id}")
	@Produces(value = { OslcMediaType.APPLICATION_RDF_XML })
	public Response getRDFRequirement(@Context ServletContext context, @PathParam("id") int id)
			throws URISyntaxException, NoSuchAlgorithmException, UnsupportedEncodingException {
		HttpServletRequest request = ResteasyProviderFactory.getContextData(HttpServletRequest.class);
//		String requestURL = request.getURL(request);
		String requestURL = request.getRequestURL().toString();
		Map<String, Requirement> requirements = (Map<String, Requirement>) context.getAttribute("OSLC_REQUIREMENTS");
		Requirement requirementToReturn = requirements.get(requestURL);

		Response.ResponseBuilder builder;
		if (requirementToReturn == null) {
			builder = Response.status(HttpServletResponse.SC_NOT_FOUND);
		} else {
			builder = Response.ok().entity(requirementToReturn).tag(requirementToReturn.getDigestion());
		}
		return builder.build();
	}

	private ResponseInfoCollection<Requirement> getResponseInfo(Collection<Requirement> set, Map<String, Object> props,
			int total) throws URISyntaxException {
		String nextPage = null;
		ResponseInfoCollection<Requirement> response;
		response = new ResponseInfoCollection<>(set, props, total, nextPage);
		response.setAbout(new URI(Constants.urlScheme + "://" + AdapterInitializer.domain + AdapterInitializer.portNumber + "/jama-oslc-adapter/"+ "services/" + "requirement"));
		return response;
	}

	private GenericEntity<ResponseInfoCollection<Requirement>> getGenericEntity(
			ResponseInfoCollection<Requirement> response) {
		GenericEntity<ResponseInfoCollection<Requirement>> entity;
		entity = new GenericEntity<ResponseInfoCollection<Requirement>>(response) {
		};
		return entity;
	}

	private Response.ResponseBuilder error(int code, String requirement) {
		org.eclipse.lyo.oslc4j.core.model.Error error;
		error = new org.eclipse.lyo.oslc4j.core.model.Error();
		error.setMessage(requirement);
		error.setStatusCode(String.valueOf(code));
		return Response.status(code).entity(error);
	}

	private void throwBadRequest(String message, Object... args) {
		Response.ResponseBuilder builder;
		message = MessageFormat.format(message, args);
		builder = error(HttpServletResponse.SC_BAD_REQUEST, message);
		throw new WebApplicationException(builder.build());
	}

	private Map<String, String> getCriteriaProperties(String where) {
		String terms[], strings[], namespace;
		Map<String, String> criteria = new HashMap<>();
		if (where != null && !"".equals(where)) {
			terms = where.split(" ?and ?");
			for (String term : terms) {
				if (!term.contains("=")) {
					throwBadRequest("\"=\" sign is missing in term \"{0}\"", term);
				} else if (term.indexOf("=") == 0) {
					throwBadRequest("identifier is missing in term \"{0}\"", term);
				} else if (term.startsWith("http://")) {
					strings = term.split("=");
					criteria.put(strings[0], strings[1]);
				} else if (!term.contains(":")) {
					throwBadRequest("term \"{0}\" is invalid, only URI or prefixed properties are allowed", term);
				} else if (term.indexOf(":") == 0) {
					throwBadRequest("prefix identifier is missing in term \"{0}\"", term);
				} else {
					strings = term.split("[:=]");
					namespace = Namespace.PREFIXES.get(strings[0]);
					if (namespace == null) {
						throwBadRequest("unknown prefix \"{0}\" at \"{1}\" term", strings[0], term);
					} else {
						criteria.put(namespace + strings[1], strings[2]);
					}
				}
			}
		}
		return criteria;
	}

	private List<String> getSelectedProperties(String select) {
		String terms[], strings[], namespace;
		List<String> properties = new ArrayList<>();
		if (select != null && !"".equals(select)) {
			terms = select.split(",");
			for (String term : terms) {
				if (term.startsWith("http://")) {
					properties.add(term);
				} else if (!term.contains(":")) {
					throwBadRequest("term \"{0}\" is invalid, only URI or prefixed properties are allowed", term);
				} else if (term.indexOf(":") == 0) {
					throwBadRequest("prefix identifier is missing in term \"{0}\"", term);
				} else {
					strings = term.split(":");
					namespace = Namespace.PREFIXES.get(strings[0]);
					if (namespace == null) {
						throwBadRequest("unknown prefix \"{0}\" at \"{1}\" term", strings[0], term);
					} else {
						properties.add(namespace + strings[1]);
					}
				}
			}
		}
		return properties;
	}

	private List<Requirement> filterResources(Collection<Requirement> all, String where)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Iterator<Map.Entry<String, String>> criteria;
		List<Requirement> filtered = new ArrayList<>(all);
		criteria = getCriteriaProperties(where).entrySet().iterator();
		while (!filtered.isEmpty() && criteria.hasNext()) {
			Map.Entry<String, String> condition = criteria.next();
			Method getter = Requirement.PROPERTIES_GETTERS.get(condition.getKey());
			if (getter == null) {
				filtered.clear();
			} else {
				Iterator<Requirement> resources = filtered.iterator();
				while (resources.hasNext()) {
					Requirement resource = resources.next();
					if (!getter.invoke(resource).toString().equals(condition.getValue())) {
						resources.remove();
					}
				}
			}
		}
		return filtered;
	}

	private Map<String, Object> filterProperties(String select) {
		List<String> identifiers;
		Map<String, Object> properties = null;
		if (select != null && !"".equals(select)) {
			properties = new HashMap<>();
			identifiers = getSelectedProperties(select);
			for (String identifier : identifiers) {
				properties.put(identifier, Collections.EMPTY_MAP);
			}
		}
		return properties;
	}

	@GET
	@Produces({ OslcMediaType.APPLICATION_RDF_XML, OslcMediaType.APPLICATION_XML, OslcMediaType.APPLICATION_JSON
			// Add JSON LD mediaType
	})
	@OslcQueryCapability(title = "Jama Requirements Query Capability", resourceTypes = Requirement.TYPE_URI, resourceShape = Requirement.SHAPE_URI)
	public Response getRDFRequirements(@Context ServletContext context,
			@PathParam("projectName") final String projectName, @QueryParam("oslc.where") String where,
			@QueryParam("oslc.select") String select)
			throws URISyntaxException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		HttpServletRequest request = ResteasyProviderFactory.getContextData(HttpServletRequest.class);
		HttpServletResponse response = ResteasyProviderFactory.getContextData(HttpServletResponse.class);
		ResponseInfoCollection<Requirement> response1;
		try {
			Map<URI, Requirement> requirements = (Map<URI, Requirement>) context.getAttribute("OSLC_REQUIREMENTS");
			Collection<Requirement> requirementsOfProject = new ArrayList<Requirement>();
			for (Requirement requirement : requirements.values()) {
				if (requirement.getProject().equals(projectName)) {
					requirementsOfProject.add(requirement);
				}
			}
			Collection<Requirement> results = filterResources(requirementsOfProject, where);
			Map<String, Object> properties = filterProperties(select);
			response1 = getResponseInfo(results, properties, results.size());

		} catch (Exception e) {
			e.printStackTrace();
			throw new WebApplicationException(e);
		}

		return Response.ok().entity(getGenericEntity(response1)).build();
	}

	/**
	 * OSLC Compact representation of a single Jama requirement
	 *
	 * Contains a reference to the smallPreview method in this class for the preview
	 * document
	 *
	 * @param productId
	 * @param changeRequestId
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws ServletException
	 */
	@GET
	@Path("/{id}")
	@Produces({ OslcMediaType.APPLICATION_X_OSLC_COMPACT_XML })
	public Compact getCompactRequirement(@Context ServletContext context, @PathParam("id") int id,
			@PathParam("projectName") final String projectName)
			throws URISyntaxException, NoSuchAlgorithmException, UnsupportedEncodingException {
		HttpServletRequest request = ResteasyProviderFactory.getContextData(HttpServletRequest.class);
//		String requestURL = getURL(request);
		String requestURL = request.getRequestURL().toString();
		Map<String, Requirement> requirements = (Map<String, Requirement>) context.getAttribute("OSLC_REQUIREMENTS");
		Requirement requirementToReturn = requirements.get(requestURL);

		if (requirementToReturn != null) {
			final Compact compact = new Compact();

			compact.setAbout(URI.create(Constants.RESOURCES + projectName + "/compactRequirement/" + id));
			compact.setTitle(requirementToReturn.getTitle());

			// Create and set attributes for OSLC preview resource
			final Preview smallPreview = new Preview();
			smallPreview.setHintHeight("13em");
			smallPreview.setHintWidth("30em");
			smallPreview
					.setDocument(new URI(Constants.RESOURCES + projectName + "/requirement/" + id + "/smallPreview"));
			compact.setSmallPreview(smallPreview);

			// Use the HTML representation of a change request as the large
			// preview as well
			final Preview largePreview = new Preview();
			largePreview.setHintHeight("30em");
			largePreview.setHintWidth("45em");
			largePreview
					.setDocument(new URI(Constants.RESOURCES + projectName + "/requirement/" + id + "/largePreview"));
			compact.setLargePreview(largePreview);
			return compact;
		}

		throw new WebApplicationException(Status.NOT_FOUND);
	}

	/**
	 * OSLC small preview for a single change request
	 *
	 * Forwards to changerequest_preview_small.jsp to build the html
	 *
	 * @param productId
	 * @param changeRequestId
	 * @throws ServletException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@GET
	@Path("{id}/smallPreview")
	@Produces({ MediaType.TEXT_HTML })
	public void smallPreview(@Context ServletContext context, @PathParam("id") int id,
			@PathParam("projectName") final String projectName)
			throws ServletException, IOException, URISyntaxException {
		HttpServletResponse response = ResteasyProviderFactory.getContextData(HttpServletResponse.class);
		HttpServletRequest request = ResteasyProviderFactory.getContextData(HttpServletRequest.class);
//		String requestURL = getURL(request).replace("/smallPreview", "");
		String requestURL = request.getRequestURL().toString().replace("/smallPreview", "");
		Map<String, Requirement> requirements = (Map<String, Requirement>) context.getAttribute("OSLC_REQUIREMENTS");
		Requirement requirementToReturn = requirements.get(requestURL);
		if (requirementToReturn != null) {
			request.setAttribute("requirement", requirementToReturn);
			request.setAttribute("requestURL", requestURL);
			RequestDispatcher rd = request.getRequestDispatcher("/uipreviewclient/requirement_preview_small.jsp");
			try {
				rd.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				throw new WebApplicationException(e);
			}
		}
		throw new WebApplicationException(Status.NOT_FOUND);

	}

	/**
	 * OSLC large preview for a single change request
	 *
	 * Forwards to changerequest_preview_large.jsp to build the html
	 *
	 * @param productId
	 * @param changeRequestId
	 * @throws ServletException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@GET
	@Path("{id}/largePreview")
	@Produces({ MediaType.TEXT_HTML })
	public void getLargePreview(@Context ServletContext context, @PathParam("id") int id,
			@PathParam("projectName") final String projectName)
			throws ServletException, IOException, URISyntaxException {
		HttpServletResponse response = ResteasyProviderFactory.getContextData(HttpServletResponse.class);
		HttpServletRequest request = ResteasyProviderFactory.getContextData(HttpServletRequest.class);
//		String requestURL = getURL(request);
		String requestURL = request.getRequestURL().toString();
		Map<String, Requirement> requirements = (Map<String, Requirement>) context.getAttribute("OSLC_REQUIREMENTS");
		Requirement requirementToReturn = requirements.get(requestURL);
		if (requirementToReturn != null) {
			request.setAttribute("requirement", requirementToReturn);
			request.setAttribute("requestURL", requestURL);
			RequestDispatcher rd = request.getRequestDispatcher("/uipreviewclient/requirement_preview_large.jsp");
			try {
				rd.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				throw new WebApplicationException(e);
			}
		}

		throw new WebApplicationException(Status.NOT_FOUND);

	}

	@OslcCreationFactory(title = "Jama Requirement Creation Factory", label = "Jama Requirement Creation", resourceTypes = Requirement.TYPE_URI, resourceShapes = Requirement.SHAPE_URI, usages = {
			OslcConstants.OSLC_USAGE_DEFAULT })
	@POST
	@Consumes({ OslcMediaType.APPLICATION_RDF_XML, OslcMediaType.APPLICATION_XML, OslcMediaType.APPLICATION_JSON })
	@Produces({ OslcMediaType.APPLICATION_RDF_XML, OslcMediaType.APPLICATION_XML, OslcMediaType.APPLICATION_JSON })
	public Response addRequirement(@PathParam("projectName") final String projectName, final Requirement requirement,
			@Context ServletContext context) throws IOException, ServletException {
		// get url of requirement to be created
		Map<String, Requirement> requirements = (Map<String, Requirement>) context.getAttribute("OSLC_REQUIREMENTS");
		Requirement requirementToReturn = requirements.get(requirement.getAbout());
		Response.ResponseBuilder builder;
		if (requirementToReturn == null) {

			// get project id
			String projectIDForJSON = null;
			for (String projectID : AdapterInitializer.projectIdAndNameMap.keySet()) {
				if (AdapterInitializer.projectIdAndNameMap.get(projectID).equals(projectName)) {
					projectIDForJSON = projectID;
					break;
				}
			}

			if (projectIDForJSON != null) {
				requirement.setProject(AdapterInitializer.projectIdAndNameMap.get(projectIDForJSON));
			}

			// Convert requirement into JSON for Jama REST API
			String requirementAsJSON = null;

			JSONObject jSONObject = new JSONObject();
			try {
				if (requirement.getIdentifier() != null) {
					try {
						jSONObject.put("id", Integer.valueOf(requirement.getIdentifier()));
					} catch (IllegalArgumentException e) {
						// requirement identifier may not always be an integer!
						// jama requirement identifiers are integers
						// but requirements for example from MagicDraw may have a different identfier
						// in which case let Jama create a new identifier
					}

				}
				jSONObject.put("itemType", 45);
				jSONObject.put("project", Integer.valueOf(projectIDForJSON));

				JSONObject fieldsObject = new JSONObject();
				jSONObject.put("fields", fieldsObject);

				JSONObject locationObject = new JSONObject();
				jSONObject.put("location", locationObject);

				JSONObject parentObject = new JSONObject();
				locationObject.put("parent", parentObject);

				if (requirement.getParentID() != null) {
					parentObject.put("item", Integer.valueOf(requirement.getParentID()));
				}

				if (requirement.getDescription() != null) {
					fieldsObject.put("description", requirement.getDescription());
				}

				if (requirement.getTitle() != null) {
					fieldsObject.put("name", requirement.getTitle());
				}

				requirementAsJSON = jSONObject.toString();

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String usernameAndPassword = AdapterInitializer.username + ":" + AdapterInitializer.password;
			String authorizationHeaderValue = "Basic "
					+ java.util.Base64.getEncoder().encodeToString(usernameAndPassword.getBytes());
			Client client = ClientBuilder.newClient();
			WebTarget myResource = client
					.target("https://" + AdapterInitializer.jamaInstanceName + ".jamacloud.com/rest/latest/items/");
			String response = myResource.request(MediaType.APPLICATION_JSON)
					.header("Authorization", authorizationHeaderValue)
					.header("Content-Type", MediaType.APPLICATION_JSON).header("Accept", MediaType.APPLICATION_JSON)
					.post(Entity.entity(requirementAsJSON, MediaType.APPLICATION_JSON), String.class);

			if (response != null) {

				// from response, read new ID in Jama and update!
				try {
					JSONObject obj = new JSONObject(response);
					JSONObject metaObject = obj.getJSONObject("meta");
					int jamaID = (Integer) metaObject.get("id");
					requirement.setIdentifier(String.valueOf(jamaID));

					// update the URI of the requirement!
					String lastname = getElementQualifiedName(requirement.getAbout());
					String newURIString = requirement.getAbout().toString().replace(lastname, String.valueOf(jamaID));	
					URI newURI = URI.create(newURIString);
					requirement.setAbout(newURI);
					
					requirements.put(requirement.getAbout().toString(), requirement);
					AdapterInitializer.reqIDRequirementMap.put(jamaID, requirement.getAbout());

					// add derivedFrom relationships
					if (requirement.getDerivedFrom() != null) {

						for (Link link : requirement.getDerivedFrom()) {
							// find the jama id of the related item
							URI linkedElementURI = link.getValue();
							int toItem = 0;
							AdapterInitializer.reqIDRequirementMap.values();
							for (int itemID : AdapterInitializer.reqIDRequirementMap.keySet()) {
								URI uri = AdapterInitializer.reqIDRequirementMap.get(itemID);
								if (uri.toString().equals(linkedElementURI.toString())) {
									toItem = itemID;
									break;
								}
							}

							// create POST request to Jama
							if (toItem != 0) {
								String relationshipAsJSON = null;
								JSONObject jSONObject2 = new JSONObject();
								try {

									jSONObject2.put("fromItem", jamaID);
									jSONObject2.put("toItem", toItem);
									jSONObject2.put("relationshipType", 6);
									relationshipAsJSON = jSONObject2.toString();

								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								WebTarget myResource2 = client.target("https://" + AdapterInitializer.jamaInstanceName
										+ ".jamacloud.com/rest/latest/relationships");
								String response2 = myResource2.request(MediaType.APPLICATION_JSON)
										.header("Authorization", authorizationHeaderValue)
										.header("Content-Type", MediaType.APPLICATION_JSON)
										.header("Accept", MediaType.APPLICATION_JSON)
										.post(Entity.entity(relationshipAsJSON, MediaType.APPLICATION_JSON),
												String.class);

								// update map
								Collection<Integer> newRelationships = new ArrayList<Integer>();
								newRelationships.add(toItem);
								AdapterInitializer.reqId_DerivedFromRelationsMap.put(jamaID, newRelationships);
								AdapterInitializer.reqIDRequirementMap.put(jamaID, requirement.getAbout());

							}

						}

					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				builder = Response.created(requirement.getAbout()).entity(requirement);
			} else {
				builder = Response.status(HttpServletResponse.SC_CONFLICT);
			}

		} else {
			builder = Response.status(HttpServletResponse.SC_CONFLICT).entity(requirementToReturn);
		}
		return builder.build();
	}

//	public static String getURL(HttpServletRequest req) {
//
//		String scheme = req.getScheme(); // http
//		String serverName = req.getServerName(); // hostname.com
//		int serverPort = req.getServerPort(); // 80
//		String contextPath = req.getContextPath(); // /mywebapp
//		String servletPath = req.getServletPath(); // /servlet/MyServlet
//		String pathInfo = req.getPathInfo(); // /a/b;c=123
//		String queryString = req.getQueryString(); // d=789
//
//		// Reconstruct original requesting URL
//		StringBuilder url = new StringBuilder();
//		url.append(scheme).append("://").append(serverName);
//
//		if (serverPort != 80 && serverPort != 443) {
//			url.append(":").append(serverPort);
//		}
//
//		url.append(contextPath).append(servletPath);
//
//		if (pathInfo != null) {
//			url.append(pathInfo);
//		}
//		if (queryString != null) {
//			url.append("?").append(queryString);
//		}
//		return url.toString();
//	}
	
	public String getElementQualifiedName(URI uri){
		String[] names = uri.getPath().split("/");
	    String last_name = names[names.length - 1]; 	    	
	    return last_name; 
	}
}
