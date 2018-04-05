<!DOCTYPE html>


<%@ page contentType="text/html" language="java" pageEncoding="UTF-8"%>
<%@ page
	import="org.eclipse.lyo.oslc4j.core.model.ServiceProviderCatalog"%>
<%@ page import="org.eclipse.lyo.oslc4j.core.model.ServiceProvider"%>
<%@ page import="org.eclipse.lyo.oslc4j.core.model.Link"%>
<%@ page import="java.net.URI"%>
<%@ page
	import="com.jama.oslc.model.Requirement"%>
<%

Requirement Requirement = (Requirement)request.getAttribute("requirement");
String requestURL = (String)request.getAttribute("requestURL");
%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>Jama OSLC Adapter: Jama Requirement</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/simple.css">
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700'
	rel='stylesheet' type='text/css'>
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/100px_white-oslc-favicon.ico">


</head>
<body onload="">

	<!-- header -->
	<p id="title">Jama OSLC Adapter: Jama Requirement</p>

	<!-- main content -->
	<div id="main-body">
		
		<!-- oslc logo and adapter details -->
		<a id="oslclogo" href="http://open-services.net/" target="_blank"><img
			src="<%=request.getContextPath()%>/images/oslcLg.png"></a>
		
		<br>

		<!-- resource type and name -->
		<h1><span id="metainfo">Jama Requirement </span><%= Requirement.getTitle() %></h1>
		<br>

		<!-- resource attributes and relationships -->
		<p><span id="metainfo">Identifier</span>: <%= Requirement.getIdentifier() %></p>
		<p><span id="metainfo">Description</span>: <%= Requirement.getDescription() %></p>
		<p><span id="metainfo">Document Key</span>: <%= Requirement.getDocumentKey() %></p>
		<p><span id="metainfo">Global ID</span>: <%= Requirement.getGlobalId() %></p>
		<p><span id="metainfo">Project</span>: <%= Requirement.getProject() %></p>
		<p><span id="metainfo">Created</span>: <%= Requirement.getCreated() %></p>
		<p><span id="metainfo">Modified</span>: <%= Requirement.getModified() %></p>
		<p><span id="metainfo">Parent ID</span>: <%= Requirement.getParentID() %></p>
		

		<% if( Requirement.getDerivedFrom().length > 0) {  %>
		<p><span id="metainfo">DerivedFrom</span></p>
			<% for (Link link : Requirement.getDerivedFrom()) {  %>				
				<p><a href="<%= link.getValue() %>"> <%=getElementQualifiedName(link.getValue())%></a></p>				
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

<%!
	public String getElementQualifiedName(URI uri){
		String[] names = uri.getPath().split("/");
	    String last_name = names[names.length - 1]; 	    	
	    return last_name; 
	}
%>