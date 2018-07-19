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


ChangeLog changeLog = (ChangeLog)request.getAttribute("changeLog");

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
	<p id="title">Jama Change Log Events</p>

	<!-- main content -->
	<div id="main-body">
		
		<!-- oslc logo and adapter details -->
		<a id="oslclogo" href="http://open-services.net/" target="_blank"><img
			src="<%=request.getContextPath()%>/images/oslcLg.png"></a>
		
		<br>

		<!-- resource type and name -->
		<h1><span id="metainfo">Change Log Events</span></h1>
		<br>

		
<h1><small>Change Log</small> </h1>
			
			
			<% if(changeLog.getChanges().size() > 0) { %>
				<h2><small>&nbsp;&nbsp;&nbsp;Change Events</small></h2>		
				<% for (ChangeEvent changeEvent : changeLog.getChanges()) { %>	
				<% Collection<URI> changeEventTypes = changeEvent.getTypes(); %>	
					<% String changeEventTypeURI = changeEventTypes.toArray()[0].toString(); %>
					<% String changeEventType = changeEventTypeURI.replace("http://open-services.net/ns/core/trs#",""); %>
					<p><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= changeEventType %></b> Change Event</p>
                    	<p><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Changed</b> Resource <a href="<%= changeEvent.getChanged() %>">
                    	<%= changeEvent.getChanged() %></a></p>
                    	<p><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Timestamp</b> <%= changeEvent.getAbout().toString().replace("http://localhost:8383/oslc4jintegrity/changeevents/", "") %></p>
                    	<p><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Order</b> <%= changeEvent.getOrder() %></p>
				<% } %>
				
			<% } else { %>	
				<h2><small>&nbsp;&nbsp;&nbsp;Change Events: none</small></h2>
			<% } %>
				
			
			

	<!-- footer -->
	<p id="footer">OSLC Jama Adapter 1.0</p>
	 
</body>
</html>



























