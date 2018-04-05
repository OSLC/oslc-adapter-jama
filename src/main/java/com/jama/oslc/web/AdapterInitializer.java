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
package com.jama.oslc.web;

import com.jama.oslc.model.Constants;
import com.jama.oslc.model.Namespace;
import com.jama.oslc.model.Requirement;
import com.jama.oslc.model.trs.Base;
import com.jama.oslc.model.trs.ChangeEvent;
import com.jama.oslc.model.trs.ChangeLog;
import com.jama.oslc.model.trs.TRSConstants;
import com.jama.oslc.model.trs.TrackedResourceSet;
import com.jama.oslc.resources.RequirementResource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Base64;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONException;
import org.apache.wink.json4j.JSONObject;
import org.eclipse.lyo.oslc4j.core.exception.OslcCoreApplicationException;
import org.eclipse.lyo.oslc4j.core.model.Link;
import org.eclipse.lyo.oslc4j.core.model.OslcConstants;
import org.eclipse.lyo.oslc4j.core.model.Publisher;
import org.eclipse.lyo.oslc4j.core.model.Service;
import org.eclipse.lyo.oslc4j.core.model.ServiceProvider;
import org.eclipse.lyo.oslc4j.core.model.ServiceProviderCatalog;
import org.eclipse.lyo.oslc4j.core.model.ServiceProviderFactory;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

public class AdapterInitializer implements ServletContextListener {

	public static Map<String, String> projectIdAndNameMap = null;
	static String warConfigFilePath = "../config.properties";
	static String localConfigFilePath = "config.properties";
	static String configFilePath = null;

	public static String portNumber = null;
	static int delayInSecondsBetweenDataRefresh = 100000;

	public static String jamaInstanceName = null;

	public static String username = null;
	public static String password = null;

	public static Map<Integer, Collection<Integer>> reqId_DerivedFromRelationsMap = null;
	public static Map<Integer, URI> reqIDRequirementMap = null;

	private static ServiceProviderCatalog getServiceProviderCatalog() throws URISyntaxException {
		String path = Constants.RESOURCES + OslcConstants.PATH_SERVICE_PROVIDER_CATALOG;
		ServiceProviderCatalog catalog = new ServiceProviderCatalog();
		Publisher publisher = new Publisher("Jama", "com.jama.oslc.adapter");
		catalog.setTitle("Jama OSLC Adapter Service Provider Catalog");
		catalog.setDescription("Jama OSLC Adapter Service Provider Catalog");
		catalog.setPublisher(publisher);
		catalog.setAbout(new URI(path));
		return catalog;
	}

	private static List<URI> getProviderDomains(ServiceProvider provider) {
		List<URI> domains = new ArrayList<>();
		Service[] services = provider.getServices();
		for (Service service : services) {
			domains.add(service.getDomain());
		}
		return domains;
	}

	private static void registryServiceProvider(ServiceProviderCatalog catalog, String identifier,
			ServiceProvider provider) throws URISyntaxException {
		String path = Constants.RESOURCES + OslcConstants.PATH_SERVICE_PROVIDER;
		provider.setIdentifier(identifier);
		provider.setAbout(new URI(path + "/" + identifier));
		catalog.addDomains(getProviderDomains(provider));
		catalog.addServiceProvider(provider);
	}

	private static Map<String, String> setupSingletonServiceProvider(ServiceProviderCatalog catalog)
			throws OslcCoreApplicationException, URISyntaxException {
		Class<?>[] myServices = { RequirementResource.class };
		Map<String, String> projectIdAndNameMap = new HashMap<String, String>();

		String usernameAndPassword = username + ":" + password;
		String authorizationHeaderValue = "Basic "
				+ java.util.Base64.getEncoder().encodeToString(usernameAndPassword.getBytes());
		Client client = ClientBuilder.newClient();
		WebTarget myResource = client.target("https://" + jamaInstanceName + ".jamacloud.com/rest/latest/projects");
		String response = myResource.request(MediaType.APPLICATION_JSON)
				.header("Authorization", authorizationHeaderValue).get(String.class);

		// for each Jama project, create service Provider resource
		// String auth = username + ":" + password;
		// byte[] encodedAuth =
		// Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
		// ClientRequest request = new ClientRequest("https://" + jamaInstanceName +
		// ".jamacloud.com/rest/latest/projects");
		// request.header("Authorization", "Basic " + basicAuthHeader);

		// ClientResponse<String> response;
		try {
			// response = request.get(String.class);
			// JSONObject obj = new JSONObject(response.getEntity());
			JSONObject obj = new JSONObject(response);
			JSONArray dataArray = obj.getJSONArray("data");

			Iterator dataArrayIT = dataArray.iterator();
			while (dataArrayIT.hasNext()) {
				Object object = dataArrayIT.next();
				if (object instanceof JSONObject) {
					JSONObject projectObject = (JSONObject) object;
					System.out.println(projectObject.getString("id"));

					Object fieldsObject = projectObject.get("fields");
					if (fieldsObject instanceof JSONObject) {
						JSONObject fieldsJSONObject = (JSONObject) fieldsObject;
						System.out.println(fieldsJSONObject.getString("name"));

						String serviceProviderName = fieldsJSONObject.getString("name").replace(" ", "_");
						projectIdAndNameMap.put(projectObject.getString("id"), serviceProviderName);

						Map<String, Object> parameterMap = new HashMap<String, Object>();
						// parameter map captures parameter names used in JAX-RS
						// @Path annotations
						parameterMap.put("projectName", serviceProviderName);

						ServiceProvider provider = ServiceProviderFactory.createServiceProvider(Constants.RESOURCES,
								Constants.CONTEXT, fieldsJSONObject.getString("name"),
								fieldsJSONObject.getString("name"), catalog.getPublisher(), myServices, parameterMap);
						provider.setPrefixDefinitions(Namespace.getKnownPrefixDefinitions(Namespace.class));
						// vocabulary URI cannot end in #
						// else exception because a URI is being created based
						// on this url
						// http://localhost:{portNumber}/jama-oslc-adapter/vocabulary#Requirement#
						registryServiceProvider(catalog, serviceProviderName, provider);

					}
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return projectIdAndNameMap;

	}

	private static Map<String, com.jama.oslc.model.Set> setupSets(Map<String, String> projectIdAndNameMap) {
		Map<String, com.jama.oslc.model.Set> sets = new HashMap<>();
		try {

			// request url
			// https://koneksys.jamacloud.com/rest/latest/items?project=15

			String auth = username + ":" + password;
			byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));

			for (String projectId : projectIdAndNameMap.keySet()) {
				String serviceProviderName = projectIdAndNameMap.get(projectId);

				String usernameAndPassword = username + ":" + password;
				String authorizationHeaderValue = "Basic "
						+ java.util.Base64.getEncoder().encodeToString(usernameAndPassword.getBytes());
				Client client = ClientBuilder.newClient();
				WebTarget myResource = client.target(
						"https://" + jamaInstanceName + ".jamacloud.com/rest/latest/items?project=" + projectId);
				String response = myResource.request(MediaType.APPLICATION_JSON)
						.header("Authorization", authorizationHeaderValue).get(String.class);

				JSONObject obj = new JSONObject(response);
				JSONArray dataArray = obj.getJSONArray("data");

				for (Object o : dataArray) {
					if (o instanceof JSONObject) {
						JSONObject res = (JSONObject) o;
						JSONObject fields = res.getJSONObject("fields");

						String itemType = res.getString("itemType");
						if (itemType.equals("31")) { // item type of sets (parents of requirements)
							com.jama.oslc.model.Set set = new com.jama.oslc.model.Set();
							/*
							 * Set resource URI
							 */
							String id = res.getString("id");
							set.setAbout(new java.net.URI(Constants.RESOURCES + serviceProviderName + "/set/" + id));
							/*
							 * Set identifier
							 */
							set.setId(id);

							/*
							 * Set project
							 */
							set.setProject(serviceProviderName);

							/*
							 * Set title
							 */
							String name = fields.getString("name");
							set.setName(name);

							sets.put(set.getAbout().toString(), set);
						}

					}
				}
			}

		} catch (URISyntaxException ex) {
			throw new ExceptionInInitializerError(ex);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sets;
	}

	private static Map<String, Requirement> setupRequirements(Map<String, String> projectIdAndNameMap) {
		Map<String, Requirement> requirements = new HashMap<>();
		reqIDRequirementMap = new HashMap<Integer, URI>();
		try {

			// first need to get a list of every project id!

			// request url
			// https://koneksys.jamacloud.com/rest/latest/items?project=15

			String auth = username + ":" + password;
			byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));

			for (String projectId : projectIdAndNameMap.keySet()) {
				String serviceProviderName = projectIdAndNameMap.get(projectId);

				String usernameAndPassword = username + ":" + password;
				String authorizationHeaderValue = "Basic "
						+ java.util.Base64.getEncoder().encodeToString(usernameAndPassword.getBytes());
				Client client = ClientBuilder.newClient();
				WebTarget myResource = client.target(
						"https://" + jamaInstanceName + ".jamacloud.com/rest/latest/items?project=" + projectId);
				String response = myResource.request(MediaType.APPLICATION_JSON)
						.header("Authorization", authorizationHeaderValue).get(String.class);

				JSONObject obj = new JSONObject(response);
				JSONArray dataArray = obj.getJSONArray("data");

				for (Object o : dataArray) {
					if (o instanceof JSONObject) {
						JSONObject res = (JSONObject) o;
						JSONObject fields = res.getJSONObject("fields");

						String itemType = res.getString("itemType");
						if (itemType.equals("45")) {
							Requirement requirement = new Requirement();
							/*
							 * Set resource URI
							 */
							String id = res.getString("id");
							requirement.setAbout(
									new java.net.URI(Constants.RESOURCES + serviceProviderName + "/requirement/" + id));
							/*
							 * Set identifier
							 */
							requirement.setIdentifier(id);
							/*
							 * Set documentKey
							 */
							String documentKey = res.getString("documentKey");
							requirement.setDocumentKey(documentKey);
							/*
							 * Set GlobalID
							 */
							String globalId = res.getString("globalId");
							requirement.setGlobalId(globalId);
							/*
							 * Set project
							 */
							requirement.setProject(serviceProviderName);

							/*
							 * Set created
							 */
							String created = res.getString("createdDate");
							requirement.setCreated(getDate(created));
							/*
							 * Set modified
							 */
							String modified = res.getString("modifiedDate");
							requirement.setModified(getDate(modified));
							/*
							 * Set title
							 */
							String title = fields.getString("name");
							requirement.setTitle(title);
							/*
							 * Set description
							 */
							String description = fields.getString("description");
							requirement.setDescription(description);

							/*
							 * Set parentID
							 */

							JSONObject location = res.getJSONObject("location");
							JSONObject parent = location.getJSONObject("parent");
							String parentID = parent.getString("item");
							requirement.setParentID(parentID);

							requirements.put(requirement.getAbout().toString(), requirement);
							reqIDRequirementMap.put(Integer.valueOf(id), requirement.getAbout());
						}

					}
				}
			}

		} catch (URISyntaxException ex) {
			throw new ExceptionInInitializerError(ex);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requirements;
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			loadPropertiesFile();
			readDataPeriodicallyFromJama(sce);
		}		
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static void setupTRSResources(ServletContextEvent sce) {

		Map<String, Requirement> requirements = (Map<String, Requirement>) sce.getServletContext()
				.getAttribute("OSLC_REQUIREMENTS");

		// creation of Base
		Base base = new Base();
		Collection<URI> baseTypes = new ArrayList<URI>();
		baseTypes.add(URI.create("http://www.w3.org/ns/ldp#Container"));
		base.setTypes(baseTypes);
		base.setAbout(URI.create(Constants.CONTEXT + "services/trs/" + TRSConstants.TRS_TERM_BASE));
		List<URI> baseMembers = new ArrayList<URI>();
		for (Requirement requirement : requirements.values()) {
			baseMembers.add(requirement.getAbout());
		}
		base.setMembers(baseMembers);
		sce.getServletContext().setAttribute("OSLC_TRS_BASE", base);

		// creation of changeLog
		int changeLogID = 1;
		ChangeLog changeLog = new ChangeLog();
		// URI changeLogURI = URI
		// .create(Namespace.CONTEXT + "services/trs/" +
		// TRSConstants.TRS_TERM_CHANGE_LOG + "/" + changeLogID);
		URI changeLogURI = URI
				.create(Constants.CONTEXT + "services/trs/" + TRSConstants.TRS_TERM_CHANGE_LOG + changeLogID);
		changeLog.setAbout(changeLogURI);
		URI changeLogTypeURI = URI.create("http://open-services.net/ns/core/trs#ChangeLog");
		Collection<URI> changeLogTypes = new ArrayList<URI>();
		changeLogTypes.add(changeLogTypeURI);
		changeLog.setTypes(changeLogTypes);
		ArrayList<ChangeEvent> changeEvents = new ArrayList<ChangeEvent>();
		changeLog.setChanges(changeEvents);
		sce.getServletContext().setAttribute("OSLC_TRS_CHANGELOG", changeLog);

		// creation of changeEvents
		URI creationChangeEventTypeURI = URI.create("http://open-services.net/ns/core/trs#Creation");
		URI modificationChangeEventTypeURI = URI.create("http://open-services.net/ns/core/trs#Modification");
		URI deletionChangeEventTypeURI = URI.create("http://open-services.net/ns/core/trs#Deletion");
		Requirement[] requirementsArray = (Requirement[]) requirements.values()
				.toArray(new Requirement[requirements.size()]);
		String date = new Date().toString().replace(" ", "");
		Collection<URI> creationChangeEventTypes = new ArrayList<URI>();
		creationChangeEventTypes.add(creationChangeEventTypeURI);
		Collection<URI> deletionChangeEventTypes = new ArrayList<URI>();
		deletionChangeEventTypes.add(deletionChangeEventTypeURI);
		Collection<URI> modificationChangeEventTypes = new ArrayList<URI>();
		modificationChangeEventTypes.add(modificationChangeEventTypeURI);

		// changeEvent 1 (creation change event)
		Requirement req1 = requirementsArray[0];
		int changeEventID = 1;
		ChangeEvent creationChangeEvent = new ChangeEvent();
		creationChangeEvent.setAbout(
				URI.create(Constants.CONTEXT + "services/trs" + "/changeevents/" + date + ":" + changeEventID));
		creationChangeEvent.setTypes(creationChangeEventTypes);
		creationChangeEvent.setOrder(changeEventID);
		creationChangeEvent.setChanged(req1.getAbout());
		changeEvents.add(creationChangeEvent);

		// changeEvent 2 (creation change event)
		changeEventID++;
		Requirement req2 = requirementsArray[1];

		ChangeEvent creationChangeEvent2 = new ChangeEvent();
		creationChangeEvent2.setAbout(
				URI.create(Constants.CONTEXT + "services/trs" + "/changeevents/" + date + ":" + changeEventID));
		creationChangeEvent2.setTypes(creationChangeEventTypes);
		creationChangeEvent2.setOrder(changeEventID);
		creationChangeEvent2.setChanged(req2.getAbout());
		changeEvents.add(creationChangeEvent2);

		// changeEvent 3 (Deletion change event)
		changeEventID++;
		ChangeEvent deletionChangeEvent = new ChangeEvent();
		deletionChangeEvent.setAbout(
				URI.create(Constants.CONTEXT + "services/trs" + "/changeevents/" + date + ":" + changeEventID));

		deletionChangeEvent.setTypes(deletionChangeEventTypes);
		deletionChangeEvent.setOrder(changeEventID);
		deletionChangeEvent.setChanged(URI.create("http://localhost:" + portNumber
				+ "/jama-oslc-adapter/services/Achiever_UAV_Sample_Set/requirement/9008"));
		changeEvents.add(deletionChangeEvent);

		// change event 4
		changeEventID++;
		Requirement req4 = requirementsArray[3];
		ChangeEvent modificationChangeEvent = new ChangeEvent();
		modificationChangeEvent.setAbout(
				URI.create(Constants.CONTEXT + "services/trs" + "/changeevents/" + date + ":" + changeEventID));
		modificationChangeEvent.setTypes(modificationChangeEventTypes);
		modificationChangeEvent.setOrder(changeEventID);
		modificationChangeEvent.setChanged(req4.getAbout());
		changeEvents.add(modificationChangeEvent);

		// creation of TrackedResourceSet trackedResourceSet
		TrackedResourceSet trackedResourceSet = new TrackedResourceSet();
		trackedResourceSet.setAbout(URI.create(Constants.CONTEXT + "services/trs"));//$NON-NLS-1$
		trackedResourceSet.setBase(URI.create(Constants.CONTEXT + "services/trs/" + TRSConstants.TRS_TERM_BASE));//$NON-NLS-1$
		trackedResourceSet.setChangeLog(changeLog);
		sce.getServletContext().setAttribute("OSLC_TRS", trackedResourceSet);

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		sce.getServletContext().removeAttribute("OSLC_CATALOG");
	}

	public static Date getDate(String dateInString) {
		String[] dateInStringSegments = dateInString.split("T");
		String day = dateInStringSegments[0];
		String time = dateInStringSegments[1];
		// String[] timeSegments = time.split(".0");
		// String cleanedUpTime = timeSegments[0];
		String cleanedUpTime = time.replace(".000+0000", "");
		String newTime = day + "/" + cleanedUpTime;
		// SimpleDateFormat formatter = new SimpleDateFormat("EEEE,
		// dd/MM/yyyy/hh:mm:ss");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd/hh:mm:ss");
		Date parsedDate = null;
		try {
			parsedDate = formatter.parse(newTime);
			System.out.println(parsedDate.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parsedDate;
	}

	private static void loadPropertiesFile() {
		Properties prop = new Properties();
		InputStream input = null;

		try {
			// loading properties file
			// input = new FileInputStream("./configuration/config.properties");
			input = new FileInputStream(warConfigFilePath); // for war file
			configFilePath = warConfigFilePath;
		} catch (FileNotFoundException e) {
			try {
				input = new FileInputStream(localConfigFilePath);
				configFilePath = localConfigFilePath;
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // for war file
		}

		// load property file content and convert backslashes into forward
		// slashes
		String str;
		if (input != null) {
			try {
				str = readFile(configFilePath, Charset.defaultCharset());
				prop.load(new StringReader(str.replace("\\", "/")));

				// get the property value

				String delayInSecondsBetweenDataRefreshFromUser = prop.getProperty("delayInSecondsBetweenDataRefresh");
				jamaInstanceName = portNumber = prop.getProperty("jamaInstanceName");
				portNumber = prop.getProperty("portNumber");

				username = prop.getProperty("username");
				password = prop.getProperty("password");

				try {
					delayInSecondsBetweenDataRefresh = Integer.parseInt(delayInSecondsBetweenDataRefreshFromUser);
				} catch (Exception e) {

				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

	}

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	}

	public static void readDataPeriodicallyFromJama(ServletContextEvent sce) {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				readDataFromJama(sce);
			}
		}, 0, delayInSecondsBetweenDataRefresh * 1000);
	}

	public static void readDataFromJama(ServletContextEvent sce) {
		projectIdAndNameMap = null;

		Thread thread = new Thread() {
			ServiceProviderCatalog catalog;

			public void start() {
				// initialize ServiceProviderCatalog and ServiceProvider resources
				try {
					catalog = getServiceProviderCatalog();
					projectIdAndNameMap = setupSingletonServiceProvider(catalog);
					sce.getServletContext().setAttribute("OSLC_CATALOG", catalog);
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OslcCoreApplicationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};
		thread.start();
		try {
			thread.join();
			System.out.println("ServiceProviderCatalog initialized at " + new Date().toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Thread thread2 = new Thread() {
			public void start() {
				// initialize Requirement resources
				Map<String, Requirement> requirements = setupRequirements(projectIdAndNameMap);
				setupRequirementRelationships(requirements, projectIdAndNameMap);

				sce.getServletContext().setAttribute("OSLC_REQUIREMENTS", requirements);
			}

		};
		thread2.start();
		try {
			thread2.join();
			System.out.println("Jama requirements converted into RDF at " + new Date().toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Thread thread4 = new Thread() {
		// public void start() {
		// // initialize Set resources
		// Map<String, com.jama.oslc.model.Set> sets = setupSets(projectIdAndNameMap);
		// sce.getServletContext().setAttribute("OSLC_SETS", sets);
		// }
		// };
		// thread4.start();
		// try {
		// thread4.join();
		// System.out.println("Jama sets converted into RDF at " + new
		// Date().toString());
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		Thread thread3 = new Thread() {
			public void start() {
				// initialize pseudo TRS resources
				setupTRSResources(sce);
			}
		};
		thread3.start();
		try {
			thread3.join();
			System.out.println("Jama requirements change events converted into RDF at " + new Date().toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void setupRequirementRelationships(Map<String, Requirement> requirements,
			Map<String, String> projectIdAndNameMap2) {

		// get all requirement relationships in all projects
		reqId_DerivedFromRelationsMap = new HashMap<Integer, Collection<Integer>>();
		for (String projectId2 : projectIdAndNameMap2.keySet()) {

			int projectId = Integer.valueOf(projectId2);
			int startAt = 0;
			int maxResults = 50;

			boolean isPagingNecessary = true;

			String usernameAndPassword = username + ":" + password;
			String authorizationHeaderValue = "Basic "
					+ java.util.Base64.getEncoder().encodeToString(usernameAndPassword.getBytes());
			Client client = ClientBuilder.newClient();

			while (isPagingNecessary) {
				WebTarget myResource = client
						.target("https://" + jamaInstanceName + ".jamacloud.com/rest/latest/relationships?project="
								+ projectId + "&startAt=" + startAt + "&maxResults=" + maxResults + "");
				String response = myResource.request(MediaType.APPLICATION_JSON)
						.header("Authorization", authorizationHeaderValue).get(String.class);

				System.out.println(response);

				try {
					JSONObject obj = new JSONObject(response);
					JSONObject metaObject = obj.getJSONObject("meta");
					JSONObject pageInfoObject = metaObject.getJSONObject("pageInfo");

					int startIndex = (Integer) pageInfoObject.get("startIndex");
					int resultCount = (Integer) pageInfoObject.get("resultCount");
					int totalResults = (Integer) pageInfoObject.get("totalResults");

					System.out.println("startIndex: " + startIndex);
					System.out.println("resultCount: " + resultCount);
					System.out.println("totalResults: " + totalResults);

					JSONArray dataArray = obj.getJSONArray("data");

					Iterator dataArrayIT = dataArray.iterator();
					while (dataArrayIT.hasNext()) {
						Object object = dataArrayIT.next();
						if (object instanceof JSONObject) {
							JSONObject relatioshipObject = (JSONObject) object;

							String relationshipType = relatioshipObject.getString("relationshipType");
							System.out.println(relationshipType);

							// derivedFrom requirements
							if (relationshipType.equals("6")) {

								int fromItem = relatioshipObject.getInt("fromItem");
								int toItem = relatioshipObject.getInt("toItem");

								Collection<Integer> relationships = reqId_DerivedFromRelationsMap.get(fromItem);
								if (relationships != null) {
									relationships.add(toItem);
								} else {
									Collection<Integer> newRelationships = new ArrayList<Integer>();
									newRelationships.add(toItem);
									reqId_DerivedFromRelationsMap.put(fromItem, newRelationships);
								}

								// System.out.println(fromItem + " -> " + toItem);
							}
						}
					}

					// check if more paging is necessary
					int newStartIndex = startIndex + resultCount;
					if (newStartIndex >= totalResults) {
						isPagingNecessary = false;

					} else {
						startAt = newStartIndex;
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("rels in total " + reqId_DerivedFromRelationsMap.size());

		// update the requirement OSLC POJOs
		for (Requirement requirement : requirements.values()) {
			int reqID = Integer.valueOf(requirement.getIdentifier());
			if (reqId_DerivedFromRelationsMap.containsKey(reqID)) {
				// Link[] derivedFromLinkArray = requirement.getDerivedFrom();

				// counting the number of links
				int linksCount = reqId_DerivedFromRelationsMap.get(reqID).size();

				
				
				
				// creating linksArray

				if (linksCount > 0) {
					
					// get links count to other reqs
					int realLinkCount = 0;
					for (Integer reqID2 : reqId_DerivedFromRelationsMap.get(reqID)) {

						URI linkedElementURI = reqIDRequirementMap.get(reqID2);

						// only derivedFrom relationships to other requirements of type Stakeholder Reqs
						// are captured
						// TODO: support relationships to other item types
						if (linkedElementURI != null) {
							realLinkCount++;
						}

					}
					
					
					if(realLinkCount >0 ) {
						Link[] linksArray = null;
						linksArray = new Link[realLinkCount];

						// populating linksArray
						int linksArrayIndex = 0;
						for (Integer reqID2 : reqId_DerivedFromRelationsMap.get(reqID)) {

							URI linkedElementURI = reqIDRequirementMap.get(reqID2);

							// only derivedFrom relationships to other requirements of type Stakeholder Reqs
							// are captured
							// TODO: support relationships to other item types
							if (linkedElementURI != null) {
								Link link = new Link(linkedElementURI);
								linksArray[linksArrayIndex] = link;
								linksArrayIndex++;
							}

						}
						requirement.setDerivedFrom(linksArray);
					}
					
					
				}

			}

		}

	}
}
