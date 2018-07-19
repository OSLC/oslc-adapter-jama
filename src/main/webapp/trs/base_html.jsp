<!DOCTYPE html>


<%@ page contentType="text/html" language="java" pageEncoding="UTF-8" %>
<%@ page import="org.eclipse.lyo.oslc4j.core.model.ServiceProviderCatalog" %>
<%@ page import="org.eclipse.lyo.oslc4j.core.model.ServiceProvider" %>
<%@ page import="org.eclipse.lyo.oslc4j.core.model.Link" %>
<%@ page import="java.net.URI" %>
<%@ page import="com.jama.oslc.model.trs.TrackedResourceSet" %>
<%@ page import="com.jama.oslc.model.trs.Base" %>
<%@ page import="com.jama.oslc.model.trs.ChangeLog" %>
<%@ page import="com.jama.oslc.model.trs.ChangeEvent" %>
<%@ page import="java.util.Collection" %>
<%


Base base = (Base)request.getAttribute("base");

%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>Jama TRS</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/simple.css">
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700'
	rel='stylesheet' type='text/css'>
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/100px_white-oslc-favicon.ico">


</head>
<body onload="">

	<!-- header -->
	<p id="title">Jama Base Resources</p>

	<!-- main content -->
	<div id="main-body">
		
		<!-- oslc logo and adapter details -->
		<a id="oslclogo" href="http://open-services.net/" target="_blank"><img
			src="<%=request.getContextPath()%>/images/oslcLg.png"></a>
		
		<br>

		<!-- resource type and name -->
		<h1><span id="metainfo">Base Resources</span></h1>
		<br>

		
<h1><small>Base</small> </h1>
			
			
			<% if(base.getMembers().size() > 0) { %>
				<h2><small>Members</small></h2>		
				
				
				<% for (URI memberURI : base.getMembers()) { %>
           <p><a href="<%= memberURI %>">
                    	<%= memberURI %></a></p>
				<% } %>
				<% } %>
				
			
			

	<!-- footer -->
	<p id="footer">OSLC Jama Adapter 1.0</p>
	 
</body>
</html>



























