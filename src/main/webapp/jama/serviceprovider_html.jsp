<!DOCTYPE html>

<%@ page contentType="text/html" language="java" pageEncoding="UTF-8"%>
<%@ page
	import="org.eclipse.lyo.oslc4j.core.model.ServiceProviderCatalog"%>
<%@ page import="org.eclipse.lyo.oslc4j.core.model.ServiceProvider"%>
<%@ page import="org.eclipse.lyo.oslc4j.core.model.Link"%>
<%@ page import="java.net.URI"%>
<%@ page import="org.eclipse.lyo.oslc4j.core.model.Service" %>
<%@ page import="org.eclipse.lyo.oslc4j.core.model.ServiceProvider" %>
<%@ page import="org.eclipse.lyo.oslc4j.core.model.Dialog" %>
<%@ page import="org.eclipse.lyo.oslc4j.core.model.CreationFactory" %>
<%@ page import="org.eclipse.lyo.oslc4j.core.model.ResourceShape" %>
<%@ page import="org.eclipse.lyo.oslc4j.core.model.QueryCapability" %>
<%

ServiceProvider element = (ServiceProvider)request.getAttribute("serviceProvider");
%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>Jama OSLC Adapter: Service Provider</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/simple.css">
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700'
	rel='stylesheet' type='text/css'>
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/100px_white-oslc-favicon.ico">


</head>
<body onload="">

	<!-- header -->
	<p id="title">Jama OSLC Adapter: Service Provider</p>

	<!-- main content -->
	<div id="main-body">
		
		<!-- oslc logo and adapter details -->
		<a id="oslclogo" href="http://open-services.net/" target="_blank"><img
			src="<%=request.getContextPath()%>/images/oslcLg.png"></a>
		
		<br>

		<!-- resource type and name -->
		<h1><span id="metainfo">Service Provider </span><%= element.getTitle() %></h1>				
		<br>
		
		
		<!-- resource attributes and relationships -->
		

		<% if(element.getServices().length > 0) { %>
			<p><span id="metainfo">Query Capabilities</span></p>
				
			<% for (Service service : element.getServices()) { %>								
				<p><a href="<%= service.getQueryCapabilities()[0].getQueryBase() %>">
                    <%= service.getQueryCapabilities()[0].getTitle() %></a></p>
                                       
			<% } %>					
		<% } %>
		
				
		<% if(element.getServices().length > 0) { %>
			<p><span id="metainfo">Creation Factories</span></p>
				
			<% for (Service service : element.getServices()) { %>												
                    
                    <p><a href="<%= service.getCreationFactories()[0].getCreation() %>">
                    <%= service.getCreationFactories()[0].getTitle() %></a></p>
			<% } %>					
		<% } %>	
			
		
										

	</div>


	<!-- footer -->
	<p id="footer">OSLC Jama Adapter 1.0</p>
	 
</body>
</html>


<!-- Java functions -->
<%!
	public String getElementName(URI uri){
		String[] names = uri.getPath().split("::");
	    String last_name = names[names.length - 1]; 	    	
	    return last_name; 
	}
%>