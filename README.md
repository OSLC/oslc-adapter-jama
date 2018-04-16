## Jama OSLC Adapter ##

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
- Support for syncing OSLC adapter with Jama REST API during launch of OSLC adapter as well as through periodic syncing based on configuration setting
- Support for configuration of adapter (port number, Jama instance to retrieve data from, time between periodic syncs)
- Creation of OSLC adapter client in Java to test each OSLC adapter service
- Creation of HTML/JavaScript client to test OSLC UI Preview of requirement resources
- Support for hosting Jama RDF vocabulary
- Support for Jama-specific RDF namespace



### Installation Instructions ###

**Prerequisites**

- Install Maven v3.5 or higher from [Apache Maven Project](https://maven.apache.org/download.cgi?Preferred=ftp%3A%2F%2Fmirror.reverse.net%2Fpub%2Fapache%2F) 
- Set/update environment variables M2_HOME and MAVEN_HOME pointing to your Maven folder
- Append Maven bin folder to PATH environment variable
- In command prompt, check version with following command:
`mvn --version`
- Install Java JDK 8 from [Oracle](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- Make sure that the environment variable JAVA\_HOME is pointing to the JDK and not the JRE folder. JAVA\_HOME should be pointing for example to *C:\Program Files\Java\jdk1.8.0_02*. Instructions to set JAVA\_HOME depending of your OS are [here](https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t)


**Running the Jama OSLC adapter**

- Download this zip file [koneksys-oslc-adapter-jama.zip](./koneksys-oslc-adapter-jama.zip)
- Unzip it
- Open a terminal (command window) and navigate to the root folder containing the *pom.xml* file of the unzipped file 
- **Set your your Jama subdomain, username and password** in *config.properties* located in the root folder
- Run the adapter with this command `mvn clean install wildfly:run`
- Perform `CTRL+C` to stop the adapter

### Getting Started ###

Explore OSLC Jama resources by viewing them in HTML in your browser. Navigate through OSLC resources as described in the [OSLC Core Specification](http://open-services.net/bin/view/Main/OslcCoreSpecification) 

![](http://open-services.net/pub/Main/OSLCCoreSpecDRAFT/oslc-core-overview.png)


Start with the **Service Provider Catalog** resource (entry point resource) at [http://localhost:8080/jama-oslc-adapter/services/serviceProviderCatalog](http://localhost:8080/jama-oslc-adapter/services/serviceProviderCatalog). It will show you the Jama projects exposed by the adapter. The ServiceProviderCatalog has links to ServiceProvider resources, one for each Jama project containing an item of type Stakeholder requirement (item type id = 45).

Click on a **ServiceProvider resource** (e.g. [http://localhost:8080/jama-oslc-adapter/services/serviceProvider/Semiconductor_Sample_Set](http://localhost:8080/jama-oslc-adapter/services/serviceProvider/Semiconductor_Sample_Set)). It describes a Jama project. A ServiceProvider resource has links to **Service** resources. In the case of the Jama adapter, a Service resource has links to a **CreationFactory** and a **QueryCapability** resource. As the ServiceProvider resource contains the inline representation of the Service, CreationFRactory and QueryCapability resources, the HTML representation of the ServiceProvider directly resource displays  the details of the CreationFaactory and QueryCapability resources. The inline representation of linked resources in the ServiceProvider resource is visible when viewing the RDF representation of the ServiceProvider resource. 

Click on a **Jama Requirements Query Capability** (e.g. [http://localhost:8080/jama-oslc-adapter/services/Semiconductor_Sample_Set/requirement](http://localhost:8080/jama-oslc-adapter/services/Semiconductor_Sample_Set/requirement)). It will display all requirements contained in a a Jama project. Note: it will only display requirements of type "Stakeholder Requirement". Additional requirements types can be exposed by the adapter through additional item type-specific query capabilities or through a single query capability exposing all requirements as items with different types. The **Jama Requirements Creation Factory** resource is primarily used by applications using the adapter as a client. The Creation Factory resource therefore does not require an HTML representation. As a reminder, all HTML representations of resources provided by the adapter are optional. Only the RDF representation of resources exposed by the adapter is mandatory.  

Click on an individual Jama Requirement **resource** (e.g. [http://localhost:8080/jama-oslc-adapter/services/Semiconductor_Sample_Set/requirement/3919](http://localhost:8080/jama-oslc-adapter/services/Semiconductor_Sample_Set/requirement/3919)). It will display the main attributes of a Jama requirement. 

Access this [web page](http://localhost:8080/jama-oslc-adapter/uipreview) hosted by the adapter to test OSLC UI Preview. Hover over the link to a Jama requirement to see a UI preview window pop up. The UI Preview window can contain custom information and be formatted in various ways. The window size (height, width, and small vs large preview) information is defined according to the OSLC standard.

### Testing the adapter manually using curl or a REST client ###

- Install curl (Instructions for Windows can be found [here](http://www.oracle.com/webfolder/technetwork/tutorials/obe/cloud/13_2/messagingservice/files/installing_curl_command_line_tool_on_windows.html))
- Run following curl commands in the command window, or equivalent commands in your REST client (e.g. [Postman](https://www.getpostman.com/)), to get the RDF representation of resources exposed by the adapter
 
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




### Testing the adapter automatically ###

- Run as Java application the class [JamaAdapterDiscoveryClient.java](./src/main/java/com/jama/oslc/client/JamaAdapterDiscoveryClient.java). It will print our in the console a summarized representation of the resources of the OSLC adapter discovered by the client using only the Service Provider Catalog resource as entry point
- Run as Java application the class [JamaAdapterPOSTClient.java](./src/main/java/com/jama/oslc/client/JamaAdapterPOSTClient.java) to test adding a new Requirement to Jama. 

### Open-Source License ###

A few files are not released under the MIT license but under the Eclipse Distribution and Eclipse Public licenses. 