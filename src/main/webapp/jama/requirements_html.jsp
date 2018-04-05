<!DOCTYPE html>

<%@ page contentType="text/html" language="java" pageEncoding="UTF-8"%>
<%@ page
	import="org.eclipse.lyo.oslc4j.core.model.ServiceProviderCatalog"%>
<%@ page import="org.eclipse.lyo.oslc4j.core.model.ServiceProvider"%>
<%@ page import="org.eclipse.lyo.oslc4j.core.model.Link"%>
<%@ page import="java.net.URI"%>
<%@ page import="java.util.List" %>
<%@ page import="org.eclipse.lyo.oslc4j.core.model.AbstractResource"%>
<%@ page import="com.jama.oslc.model.Requirement"%>
<%
List<AbstractResource> elements = (List<AbstractResource>) request.getAttribute("elements");
String requestURL = (String)request.getAttribute("requestURL");
String modelName = (String) request.getAttribute("modelName");
%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>Jama OSLC Adapter: Jama Requirements</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/simple.css">
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700'
	rel='stylesheet' type='text/css'>
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/100px_white-oslc-favicon.ico">


</head>
<body onload="">

	<!-- header -->
	<p id="title">Jama OSLC Adapter: Jama Requirements</p>

	<!-- main content -->
	<div id="main-body">
		
		<!-- oslc logo and adapter details -->
		<a id="oslclogo" href="http://open-services.net/" target="_blank"><img
			src="<%=request.getContextPath()%>/images/oslcLg.png"></a>
		
		<br>

		<!-- resource type and name -->
		<h1><span id="metainfo">Jama Requirements </span><%= modelName %></h1>
		<br>

		<!-- resource attributes and relationships -->			
		<% Object[] elementsArray =  elements.toArray();  %>
		<% int elementsSize =  elements.size();  %>
		<% int i =  0;  %>
		<% if( elementsSize > 0) {  %>
		<p><span id="metainfo">Requirements</span></p>
		<table>
			
			<tr>
    			<th>ID</th>
    			<th>Title</th> 

  			</tr>
			
			
			
			<tr>
				<% while(elementsSize > 0) {;  %>
				<% AbstractResource element = (AbstractResource)elementsArray[i]; %>
				<% Requirement requirement = (Requirement)element; %>
				<td><a href="<%= element.getAbout() %>"> <%=requirement.getIdentifier()%></a></td>
				<td><a href="<%= element.getAbout() %>"> <%=requirement.getTitle()%></a></td>
				<%i++;%>
				<!-- change here maximum number of cells to be displayed in each table row -->
				
			</tr>
			
				
				<%elementsSize--;%>
				<% };  %>
			
		</table>
		<% } %>
										

	</div>


	<!-- footer -->
	<p id="footer">OSLC Jama Adapter 1.0</p>
	 
</body>
</html>


<!-- Java functions -->
<%!
	public String getElementQualifiedName(URI uri){
		String[] names = uri.getPath().split("/");
	    String last_name = names[names.length - 1]; 	    	
	    return last_name; 
	}
%>