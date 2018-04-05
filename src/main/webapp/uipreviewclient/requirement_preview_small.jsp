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

<style type="text/css">
body {
	background: #FFFFFF;
	padding: 0;
}

td {
	padding-right: 5px;
	min-width: 175px;
}

th {
	padding-right: 5px;
	text-align: right;
}
</style>
</head>
<body>
	
	
	
	<small><span id="metainfo">Jama Requirement</small>
	<br>
	<small><span id="metainfo">Title: </span><%= Requirement.getTitle() %></small>
	<br>
	<!-- resource attributes and relationships -->
		<small>Identifier: <%= Requirement.getIdentifier() %></small>
		<br>
		<small><span id="metainfo">Description</span>: <%= Requirement.getDescription() %></small>
		<br>
		
		
		
</body>
</html>