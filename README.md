## Jama OSLC API ##

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)


<a title="By Jama Software (crunchbase) [Public domain], via Wikimedia Commons" href="https://commons.wikimedia.org/wiki/File%3AJama-Logo.png"><img width="100" height="100" alt="Jama-Logo" src="https://upload.wikimedia.org/wikipedia/commons/f/f0/Jama-Logo.png" /></a>
<pre>     </pre>
<a title="OSLC" href="http://oslc.co/"><img height="90" src="images/OSLC_logo-Extended-LT.png" /></a>

### Summary ###

- Producer and Consumer of Jama Stakeholder Requirements
- Producer of requirements according to OSLC Requirements Management (RM)

### Features ###

- Support for exposing resources of type OSLC Service Provider Catalog, OSLC Service Provider, OSLC Service, OSLC Query Capability, OSLC Resource Shape, OSLC Query Resource, OSLC Resource, OSLC ResponseInfo
- Support for OSLC Core v2 Specification
- HTTP GET services for reading resources (no services to delete, update, create resources)
- Support for describing requirement resources in compliance with OSLC Requirements Management specification
- Support for describing Jama resources as documented online (https://dev.jamasoftware.com/rest#docs)
- Support for RDF representation of resources
- Support for UI Preview of requirement resources
- Support for exposing change events according to OSLC Tracked Resource Set (but only fake change events, not real ones occurring really in Jama)
- Support for syncing OSLC API with Jama REST API during launch of OSLC API as well as through periodic syncing based on configuration setting
- Support for configuration of OSLC API (port number, Jama instance to retrieve data from, time between periodic syncs)
- Creation of OSLC API client in Java to test each OSLC API service
- Creation of HTML/JavaScript client to test OSLC UI Preview of requirement resources
- Support for hosting Jama RDF vocabulary
- Support for Jama-specific RDF namespace
- Support for 3-legged OAuth v1.0 authentication in the same style as for IBM CLM applications as described by Michael Fiedler in this [video](https://www.youtube.com/watch?v=kcEjftQA-LU)
- Support for rootservices in the same style as for IBM Jazz applications

### Installation Instructions ###

**Prerequisites**

- Install Maven v3.5 or higher from [Apache Maven Project](https://maven.apache.org/download.cgi?Preferred=ftp%3A%2F%2Fmirror.reverse.net%2Fpub%2Fapache%2F) 
- Set/update environment variables M2_HOME and MAVEN_HOME pointing to your Maven folder
- Append Maven bin folder to PATH environment variable
- In command prompt, check version with following command:
`mvn --version`
- Install Java JDK 8 from [Oracle](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- Make sure that the environment variable JAVA\_HOME is pointing to the JDK and not the JRE folder. JAVA\_HOME should be pointing for example to *C:\Program Files\Java\jdk1.8.0_02*. Instructions to set JAVA\_HOME depending of your OS are [here](https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t)
- If you choose to run the OSLC API on a different port than 8080, change the port in in [config.properties](src/main/resources/config.properties) and in [pom.xml](pom.xml) in `<server.port>8080</server.port>`


**Configuration to enable OAuth (optional)**

- Skip this section if you want to launch the OSLC API without OAuth authentication
- **Choose to enable OAuth authentication (e.g. isOauthEnabled = true)** in [config.properties](src/main/resources/config.properties) 
- OSLC Jama API uses its own consumerstore. You can use an existing one by downloading on your machine [jamaOAuthStore.xml](jamaOAuthStore.xml). This consumerstore already contains a consumer named *magicdraw* having *testkey* as key and *testsecret* as secret. The secret is encrypted in *jamaOAuthStore.xml*. 
- If you prefer to create a new consumerstore with different consumer credentials, you can modify and run [ConsumerStoreCreator.java](src\main\java\com\jama\oslc\web\ConsumerStoreCreator.java) 
- **Specify the location of the file-based consumerstore as URI** (e.g. *localConsumerStoreLocation = file:/C:/Users/.../git/oslc-adapter-jama2/jamaOAuthStore.xml*) in [config.properties](src/main/resources/config.properties)
-  By default, the consumerstore used by the OSLC API is located at A Java application can be used to add/remove consumers to the OSLC Jama API consumerstore. 
-  Keep in mind that the OSLC Jama API uses the credentials of a regular Jama user account to communicate with the Jama REST API. 
- Download the Wildfly server, for example from [http://download.jboss.org/wildfly/10.1.0.Final/wildfly-10.1.0.Final.zip](http://download.jboss.org/wildfly/10.1.0.Final/wildfly-10.1.0.Final.zip). It will be necessary to host the OSLC Jama API on a standalone Wildfly server, instead of a Wildfly server launched by Maven, in order to avoid this [error](https://stackoverflow.com/questions/25644007/request-io-undertow-servlet-spec-httpservletrequestimpl-was-not-original-or-a-wr). 
- Unzip the zip file containing Wildfly. 
- In the standalone/configuration/standalone.xml file change the servlet-container XML element so that it has the attribute *allow-non-standard-wrappers="true"*.
 

**Running the Jama OSLC API**

- Download this zip file [koneksys-oslc-adapter-jama.zip](https://github.com/OSLC/oslc-adapter-jama/archive/master.zip)
- Unzip it
- Open a terminal (command window) and navigate to the root folder containing the *pom.xml* file of the unzipped file 
- **Set your your Jama subdomain, username and password** in [config.properties](src/main/resources/config.properties). Keep in mind that the OSLC Jama API uses the credentials of a regular Jama user account to communicate with the Jama REST API. 
- **Choose to enable or disable OAuth authentication** (e.g. isOauthEnabled = false) in [config.properties](src/main/resources/config.properties) 
- **If OAuth is disabled**, run the OSLC API by opening a command prompt, change your directory to the root directory of the Jama OSLC API, the one containing the pom.xml file, and then run this command `mvn clean install wildfly:run`
- **If OAuth is enabled**, first make sure that you have set up a management user account for Wildfly. You can create one by running the *adduser* script (e.g. by running the *{Wildfly root}/bin/add-user* batch file on Windows), launch the standalone Wildfly server (e.g. by running the *{Wildfly root}/bin/standalone* batch file on Windows), compile the Maven project of the Jama OSLC API  using a command prompt as described in the step before if OAuth is disabled but with this command `mvn clean install`, verify that the war file has been created at *target\jama-oslc-adapter.war*, open in your browser this page [http://localhost:9990/console/App.html#standalone-deployments](http://localhost:9990/console/App.html#standalone-deployments) by signing in with your Wildfly management user account, click the *Add* button and select the *target\jama-oslc-adapter.war* file. Wildly will then host the Jama OSLC API
- Perform `CTRL+C` to stop the OSLC API

### Getting Started ###

Explore OSLC Jama resources by viewing them in HTML in your browser. Navigate through OSLC resources as described in the [OSLC Core Specification](http://open-services.net/bin/view/Main/OslcCoreSpecification) 

![](http://open-services.net/pub/Main/OSLCCoreSpecDRAFT/oslc-core-overview.png)


**If OAuth is enabled**, access the rootservices resource at [http://localhost:8080/jama-oslc-adapter/services/rootservices](http://localhost:8080/jama-oslc-adapter/services/rootservices). If using your browser, you will download a file named *rootservices.rdf*. This document will show the *ServiceProviderCatalog URL*, *oauthRequestTokenUrl*, *oauthUserAuthorizationUrl*, and *oauthAccessTokenUrl*, as shown below. 

    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <ns1:Description xmlns:ns1="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns2="http://purl.org/dc/terms/" xmlns:ns8="http://open-services.net/xmlns/cm/1.0/" xmlns:ns9="http://jazz.net/xmlns/prod/jazz/jfs/1.0/" xmlns:ns10="http://jazz.net/ns/ui#" xmlns:ns11="http://jazz.net/xmlns/prod/jazz/calm/1.0/" xmlns:ns12="http://xmlns.com/foaf/0.1/" ns1:about="http://localhost:8080/jama-oslc-adapter/services//rootservices">
    <ns2:title>OSLC Adapter/Jira Root Services</ns2:title>
    <ns8:cmServiceProviders ns1:resource="http://localhost:8080/jama-oslc-adapter/services//catalog/singleton"/>
    <ns9:oauthRequestTokenUrl ns1:resource="http://localhost:8080/jama-oslc-adapter/services//oauth/requestToken"/>
    <ns9:oauthUserAuthorizationUrl ns1:resource="http://localhost:8080/jama-oslc-adapter/services//oauth/authorize"/>
    <ns9:oauthAccessTokenUrl ns1:resource="http://localhost:8080/jama-oslc-adapter/services//oauth/accessToken"/>
    <ns9:oauthRealmName>Jama</ns9:oauthRealmName>
    <ns9:oauthRequestConsumerKeyUrl ns1:resource="http://localhost:8080/jama-oslc-adapter/services//oauth/requestKey"/>
    </ns1:Description>

Specify in [OAuthClient.java](src\main\java\com\jama\oslc\client\OAuthClient.java) your consumer name, key and secret, as well as oauthRequestTokenUrl, oauthUserAuthorizationUrl, and oauthAccessTokenUrl. Run  [OAuthClient.java](src\main\java\com\jama\oslc\client\OAuthClient.java) to get a requesttoken. The OAuthClient will then print in the console the following message:

    > log4j:WARN No appenders could be found for logger (org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager).
    > log4j:WARN Please initialize the log4j system properly.
    > log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
    > Enter this URL in a browser and run again: http://localhost:8080/jama-oslc-adapter/services/oauth/authorize?oauth_token=bfca9c38-78b3-4848-bfb7-694401e5cc96
    > org.eclipse.lyo.client.oslc.OAuthRedirectException
    > 	at org.eclipse.lyo.client.oslc.OslcOAuthClient.getResourceInternal(OslcOAuthClient.java:203)
    > 	at org.eclipse.lyo.client.oslc.OslcOAuthClient.getResource(OslcOAuthClient.java:113)
    > 	at com.jama.oslc.client.OAuthClient.main(OAuthClient.java:28)

Follow the instruction printed in the console: 
> Enter this URL in a browser and run again: http://localhost:8080/jama-oslc-adapter/services/oauth/authorize?oauth_token=bfca9c38-78b3-4848-bfb7-694401e5cc96
> 

Your url to authorize your token  will be different so please use the url as printed in your console and do not copy the url in this readme. 

After entering the authorization url in a browser, you will see a login screen where you will be asked to provide your Jama user account credentials to authorize a third-party application to access your OSLC Jama API data on your behalf. 
<p>
  <img src="images/authorizationscreen.png"/>
  
</p>

You can reuse the Jama user account credentials defined in [config.properties](src/main/resources/config.properties). If your credentials are valid, you should see this message in the browser: *Request authorized*. Then you can access all resources of the OSLC Jama API as if OAuth was disabled. 

Start with the **Service Provider Catalog** resource (entry point resource) at [http://localhost:8080/jama-oslc-adapter/services/serviceProviderCatalog](http://localhost:8080/jama-oslc-adapter/services/serviceProviderCatalog). It will show you the Jama projects exposed by the OSLC API. The ServiceProviderCatalog has links to ServiceProvider resources, one for each Jama project containing an item of type Stakeholder requirement (item type id = 45).

Click on a **ServiceProvider resource** (e.g. [http://localhost:8080/jama-oslc-adapter/services/serviceProvider/Semiconductor_Sample_Set](http://localhost:8080/jama-oslc-adapter/services/serviceProvider/Semiconductor_Sample_Set)). It describes a Jama project. A ServiceProvider resource has links to **Service** resources. In the case of the Jama OSLC API, a Service resource has links to a **CreationFactory** and a **QueryCapability** resource. As the ServiceProvider resource contains the inline representation of the Service, CreationFRactory and QueryCapability resources, the HTML representation of the ServiceProvider directly resource displays  the details of the CreationFaactory and QueryCapability resources. The inline representation of linked resources in the ServiceProvider resource is visible when viewing the RDF representation of the ServiceProvider resource. 

Click on a **Jama Requirements Query Capability** (e.g. [http://localhost:8080/jama-oslc-adapter/services/Semiconductor_Sample_Set/requirement](http://localhost:8080/jama-oslc-adapter/services/Semiconductor_Sample_Set/requirement)). It will display all requirements contained in a a Jama project. Note: it will only display requirements of type "Stakeholder Requirement". Additional requirements types can be exposed by the OSLC API through additional item type-specific query capabilities or through a single query capability exposing all requirements as items with different types. The **Jama Requirements Creation Factory** resource is primarily used by applications using the OSLC API as a client. The Creation Factory resource therefore does not require an HTML representation. As a reminder, all HTML representations of resources provided by the OSLC API are optional. Only the RDF representation of resources exposed by the OSLC API is mandatory.  

Click on an individual Jama Requirement **resource** (e.g. [http://localhost:8080/jama-oslc-adapter/services/Semiconductor_Sample_Set/requirement/3919](http://localhost:8080/jama-oslc-adapter/services/Semiconductor_Sample_Set/requirement/3919)). It will display the main attributes of a Jama requirement. 

Access this [web page](http://localhost:8080/jama-oslc-adapter/uipreview) hosted by the OSLC API to test the OSLC UI Preview. Hover over the link to a Jama requirement to see a UI preview window pop up. The UI Preview window can contain custom information and be formatted in various ways. The window size (height, width, and small vs large preview) information is defined according to the OSLC standard.

### Testing the OSLC API manually using curl or a REST client ###

- Install curl (Instructions for Windows can be found [here](http://www.oracle.com/webfolder/technetwork/tutorials/obe/cloud/13_2/messagingservice/files/installing_curl_command_line_tool_on_windows.html))
- Run following curl commands in the command window, or equivalent commands in your REST client (e.g. [Postman](https://www.getpostman.com/)), to get the RDF representation of resources exposed by the OSLC API
 
*Service Provider Catalog*

`curl -H Accept:application/rdf+xml http://localhost:8080/jama-oslc-adapter/services/serviceProviderCatalog`

*Example of Service Provider*

`curl -H Accept:application/rdf+xml http://localhost:8080/jama-oslc-adapter/services/serviceProvider/Semiconductor_Sample_Set`

*Example of Query Resource*

`curl -H Accept:application/rdf+xml http://localhost:8080/jama-oslc-adapter/services/Semiconductor_Sample_Set/requirement`

*Example of Resource*

`curl -H Accept:application/rdf+xml http://localhost:8080/jama-oslc-adapter/services/Semiconductor_Sample_Set/requirement/3919`

*Tracked Resource Set (TRS)*

`curl -H Accept:application/rdf+xml http://localhost:8080/jama-oslc-adapter/services/trs`

*TRS Base*

`curl -H Accept:application/rdf+xml http://localhost:8080/jama-oslc-adapter/services/trs/base`

*Example of TRS ChangeLog*

`curl -H Accept:application/rdf+xml http://localhost:8080/jama-oslc-adapter/services/trs/changeLog`

*Jama RDF Vocabulary*

`curl -H Accept:application/rdf+xml http://localhost:8080/jama-oslc-adapter/services/vocabulary`

*Jama Requirement Resource Shape*

`curl -H Accept:application/rdf+xml http://localhost:8080/jama-oslc-adapter/services/resourceShapes/Requirement`

*Example of Jama Requirement Creation Factory*

`curl -X POST -H Accept:application/rdf+xml -H Content-Type:application/rdf+xml http://localhost:8080/jama-oslc-adapter/services/Semiconductor_Sample_Set/requirement` 

with this body


``<rdf:RDF``
    ``xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"``
    ``xmlns:dcterms="http://purl.org/dc/terms/"``
    ``xmlns:oslc="http://open-services.net/ns/core#"``
    ``xmlns:item="http://localhost:8080/jama-oslc-adapter/vocabulary/Requirement#"``
    ``xmlns:oslc_rm="http://open-services.net/ns/rm#"``
    ``xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"``
    ``xmlns:jama="http://localhost:8080/jama-oslc-adapter/vocabulary/" > ``
  ``<rdf:Description rdf:about="http://localhost:8080/jama-oslc-adapter/services/Semiconductor_Sample_Set/requirement/9999">``
    ``<dcterms:title rdf:parseType="Literal">New Jama Requirement X</dcterms:title>``
    ``<rdf:type rdf:resource="http://open-services.net/ns/rm#Requirement"/>``
    ``<dcterms:identifier>9999</dcterms:identifier>``
    ``<dcterms:description rdf:parseType="Literal">Description of new Jama Requirement X</dcterms:description>``
    ``<jama:requirement_parentID rdf:parseType="Literal">569</jama:requirement_parentID>``
    ``<rdf:type rdf:resource="http://localhost:8080/jama-oslc-adapter/vocabulary/Requirement"/>``
  ``</rdf:Description>``
``</rdf:RDF>``




### Testing the OSLC API automatically ###

- Run as Java application the class [JamaAdapterDiscoveryClient.java](./src/main/java/com/jama/oslc/client/JamaAdapterDiscoveryClient.java). It will print our in the console a summarized representation of the resources of the OSLC API discovered by the client using only the Service Provider Catalog resource as entry point
- Run as Java application the class [JamaAdapterPOSTClient.java](./src/main/java/com/jama/oslc/client/JamaAdapterPOSTClient.java) to test adding a new Requirement to Jama. 

### Open-Source License ###

A few files are not released under the MIT license but under the Eclipse Distribution and Eclipse Public licenses. 

### More Documentation ###

- [Review of OSLC API features with examples](./documentation/OSLC_Jama_Adapter_Features.pdf)
- [Integration between Jama and MagicDraw using OSLC](./documentation/OSLC-Based_Integration_Between_Jama_and_MagicDraw.pdf) 

