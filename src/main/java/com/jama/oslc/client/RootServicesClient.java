package com.jama.oslc.client;

import java.io.InputStream;

import org.apache.wink.client.ClientResponse;
import org.eclipse.lyo.client.exception.ResourceNotFoundException;
import org.eclipse.lyo.client.oslc.OSLCConstants;
import org.eclipse.lyo.client.oslc.OslcClient;
import org.eclipse.lyo.client.oslc.jazz.JazzRootServicesHelper;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.jama.oslc.model.Constants;
import com.jama.oslc.web.AdapterInitializer;

import org.eclipse.lyo.client.exception.RootServicesException;

public class RootServicesClient {

	static String CM_ROOTSERVICES_CATALOG_PROP = "cmServiceProviders";
	static String QM_ROOTSERVICES_CATALOG_PROP = "qmServiceProviders";
	static String RM_ROOTSERVICES_CATALOG_PROP = "rmServiceProviders";
	static String AM_ROOTSERVICES_CATALOG_PROP = "amServiceProviders";
	static String AUTO_ROOTSERVICES_CATALOG_PROP = "autoServiceProviders";

	//OAuth entries
	static String OAUTH_REQUEST_TOKEN_URL = "oauthRequestTokenUrl";
	static String OAUTH_USER_AUTH_URL     = "oauthUserAuthorizationUrl";
	static String OAUTH_ACCESS_TOKEN_URL  = "oauthAccessTokenUrl";
	static String OAUTH_REALM_NAME        = "oauthRealmName";

	//https://jazz.net/wiki/bin/view/Main/RootServicesSpecAddendum2
	static String OAUTH_REQUEST_CONSUMER_KEY_URL = "oauthRequestConsumerKeyUrl";
	static String OAUTH_APPROVAL_MODULE_URL = "oauthApprovalModuleUrl";
	
	static String RTC_CM = "http://jazz.net/xmlns/prod/jazz/rtc/cm/1.0/";
	static String JFS = "http://jazz.net/xmlns/prod/jazz/jfs/1.0/";
	static String JD = "http://jazz.net/xmlns/prod/jazz/discovery/1.0/";
	static String JP06 = "http://jazz.net/xmlns/prod/jazz/process/0.6/";
	
	public static final String JFS_NAMESPACE = "http://jazz.net/xmlns/prod/jazz/jfs/1.0/";
	public static final String JD_NAMESPACE = "http://jazz.net/xmlns/prod/jazz/discovery/1.0/";
	
	static String baseUrl = "http://localhost:" + "8080" + "/jama-oslc-adapter/services/";
	
	public static void main(String[] args) {
		String rootServicesUrl = "http://localhost:" + "8080" + "/jama-oslc-adapter/services/" + "rootservices";
		
//		String catalogUrl;
		try {
			OslcClient rootServicesClient = new OslcClient();
			ClientResponse response = rootServicesClient.getResource(rootServicesUrl,OSLCConstants.CT_RDF);
			InputStream is = response.getEntity(InputStream.class);
			Model  rdfModel = ModelFactory.createDefaultModel();
			rdfModel.read(is, rootServicesUrl);

			//get the catalog URL
//			catalogUrl = getRootServicesProperty(rdfModel, OSLCConstants.OSLC_RM, RM_ROOTSERVICES_CATALOG_PROP);
//			System.out.println("catalogUrl: " + catalogUrl);
			
			//get the OAuth URLs
			String requestTokenUrl = getRootServicesProperty(rdfModel, JFS_NAMESPACE, OAUTH_REQUEST_TOKEN_URL);
			String authorizationTokenUrl = getRootServicesProperty(rdfModel, JFS_NAMESPACE, OAUTH_USER_AUTH_URL);
			String accessTokenUrl = getRootServicesProperty(rdfModel, JFS_NAMESPACE, OAUTH_ACCESS_TOKEN_URL);
			System.out.println("requestTokenUrl: " + requestTokenUrl);
			System.out.println("authorizationTokenUrl: " + authorizationTokenUrl);
			System.out.println("accessTokenUrl: " + accessTokenUrl);
			try { // Following field is optional, try to get it, if not found ignore exception because it will use the default
				String authorizationRealm = getRootServicesProperty(rdfModel, JFS_NAMESPACE, OAUTH_REALM_NAME);
			} catch (ResourceNotFoundException e) {
				System.err.println(String.format("OAuth authorization realm not found in rootservices <%s>", rootServicesUrl));
			}

			try {
				String requestConsumerKeyUrl = getRootServicesProperty(rdfModel, JFS_NAMESPACE, OAUTH_REQUEST_CONSUMER_KEY_URL);
			} catch (ResourceNotFoundException e) {
				System.err.println(String.format("OAuth request consumer key URL not found in rootservices <%s>", rootServicesUrl));
			}

//			try {
//				String consumerApprovalUrl = getRootServicesProperty(rdfModel, JFS_NAMESPACE, OAUTH_APPROVAL_MODULE_URL);
//			} catch (ResourceNotFoundException e) {
//				System.err.println(String.format("OAuth approval module URL not found in rootservices <%s>", rootServicesUrl));
//			}
		} catch (Exception e) {
			System.err.println("Caught Exception: " + e.getMessage());;
			
		}

	}
	
	private static String getRootServicesProperty(Model rdfModel, String namespace, String predicate) throws ResourceNotFoundException {
		
		String returnVal = null;

		Property prop = rdfModel.createProperty(namespace, predicate);
		Statement stmt = rdfModel.getProperty((Resource) null, prop);
		if (stmt != null && stmt.getObject() != null)
			returnVal = stmt.getObject().toString();
		if (returnVal == null)
		{
			throw new ResourceNotFoundException(baseUrl, namespace + predicate);
		}
		return returnVal;
	}

}
