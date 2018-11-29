<!DOCTYPE html>


<%@ page contentType="text/html" language="java" pageEncoding="UTF-8"%>
<%@ page
	import="org.eclipse.lyo.oslc4j.core.model.ServiceProviderCatalog"%>
<%@ page import="org.eclipse.lyo.oslc4j.core.model.ServiceProvider"%>
<%@ page import="org.eclipse.lyo.oslc4j.core.model.Link"%>
<%@ page import="java.net.URI"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<%
String requestURL = (String)request.getAttribute("requestURL");
%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>Jama RDF Vocabulary</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/simple.css">
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700'
	rel='stylesheet' type='text/css'>
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/100px_white-oslc-favicon.ico">


</head>
<body onload="">

	<!-- header -->
	<p id="title">Jama RDF Vocabulary</p>

	<!-- main content -->
	<div id="main-body">
		
		<!-- oslc logo and adapter details -->
		<a id="oslclogo" href="http://open-services.net/" target="_blank"><img
			src="<%=request.getContextPath()%>/images/oslcLg.png"></a>
		
		<br>

		<!-- resource type and name -->
		<h1><span id="metainfo">Jama RDF Vocabulary </span></h1>
		<br>

		<div> 
		
		
		
		<xmp style="padding-left: 25px;">
<?xml version="1.0" encoding="UTF-8"?>
<rdf:RDF
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
	xmlns:dcterms="http://purl.org/dc/terms/"
	xmlns:jama="${OSLC_VOCAB}">
	<rdfs:Class rdf:about="jama:Requirement">
		<rdfs:label xml:lang="en-GB">Requirement</rdfs:label>
		<rdfs:isDefinedBy rdf:resource="${OSLC_VOCAB}"/>
		<dcterms:issued>2018-01-01</dcterms:issued>
	</rdfs:Class>
	<rdf:Property rdf:about="jama:requirement_documentKey">
		<rdfs:label xml:lang="en-GB">DocumentKey</rdfs:label>
		<rdfs:isDefinedBy rdf:resource="${OSLC_VOCAB}"/>
		<dcterms:issued>2018-01-01</dcterms:issued>
	</rdf:Property>
	<rdf:Property rdf:about="jama:requirement_globalId">
		<rdfs:label xml:lang="en-GB">GlobalId</rdfs:label>
		<rdfs:isDefinedBy rdf:resource="${OSLC_VOCAB}"/>
		<dcterms:issued>2018-01-01</dcterms:issued>
	</rdf:Property>
	<rdf:Property rdf:about="jama:requirement_project">
		<rdfs:label xml:lang="en-GB">Project</rdfs:label>
		<rdfs:isDefinedBy rdf:resource="${OSLC_VOCAB}"/>
		<dcterms:issued>2018-01-01</dcterms:issued>
	</rdf:Property>
</rdf:RDF>
</xmp>
		
		
	</div>


	<!-- footer -->
	<p id="footer">OSLC Jama Adapter 1.0</p>
	 
</body>
</html>







