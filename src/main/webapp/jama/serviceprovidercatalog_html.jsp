<!DOCTYPE html>


<%@ page contentType="text/html" language="java" pageEncoding="UTF-8"%>
<%@ page
	import="org.eclipse.lyo.oslc4j.core.model.ServiceProviderCatalog"%>
<%@ page import="org.eclipse.lyo.oslc4j.core.model.ServiceProvider"%>
<%@ page import="org.eclipse.lyo.oslc4j.core.model.Link"%>
<%@ page import="java.net.URI"%>
<%

ServiceProviderCatalog element = (ServiceProviderCatalog)request.getAttribute("catalog");
%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>Jama OSLC Adapter: Service Provider Catalog</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/simple.css">
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700'
	rel='stylesheet' type='text/css'>
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/100px_white-oslc-favicon.ico">


</head>
<body onload="">

	<!-- header -->
	<p id="title">Jama OSLC Adapter: Service Provider Catalog</p>

	<!-- main content -->
	<div id="main-body">
		
		<!-- oslc logo and adapter details -->
		<a id="oslclogo" href="http://open-services.net/" target="_blank"><img
			src="<%=request.getContextPath()%>/images/oslcLg.png"></a>
		
		<br>

		<!-- resource type and name -->
		<h1><span id="metainfo">Service Provider Catalog </span>Jama</h1>
		<br>

		<!-- resource attributes and relationships -->								
		<% ServiceProvider[] serviceProviders =  element.getServiceProviders();  %>
		<% int serviceProvidersSize =  serviceProviders.length;  %>
		<% int i =  0;  %>
		<% if( serviceProvidersSize > 0) {  %>
		<p><span id="metainfo">Service Providers</span></p>
		<table>
			<tr>
				<% while(serviceProvidersSize > 0) {;  %>
				<% ServiceProvider serviceProvider = serviceProviders[i]; %>
				<td><a href="<%= serviceProvider.getAbout() %>"> <%=serviceProvider.getTitle()%></a></td>
				<%i++;%>
				<!-- change here maximum number of cells to be displayed in each table row -->
				<% if( i % 3 == 0) {  %>
			</tr>
			<tr>
				<% }  %>
				<%serviceProvidersSize--;%>
				<% };  %>
			</tr>
		</table>
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