package com.jama.oslc.resources.oauth;

/*
* Copyright (C) 2015 Ericsson AB. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions
* are met:
*
* 1. Redistributions of source code must retain the above copyright
*    notice, this list of conditions and the following disclaimer.
* 2. Redistributions in binary form must reproduce the above copyright
*    notice, this list of conditions and the following disclaimer
*    in the documentation and/or other materials provided with the
*    distribution.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
* "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
* LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
* A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
* OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
* SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
* LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
* THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
* OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.eclipse.lyo.oslc4j.core.model.OslcMediaType;

import com.jama.oslc.model.Constants;



/**
 * A services for rootservices
 *
 */
@Path("rootservices")
public class RootServicesService {
   
   @Context
   private HttpServletRequest httpServletRequest;
  
   @GET
   public Response rootservices() {

//      String jamaServiceBase = ServiceHelper.getOslcBaseUri(httpServletRequest);
      String jamaServiceBase = Constants.RESOURCES;

      StringBuilder builder = new StringBuilder();

      builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
      builder.append("<rdf:Description \n");
      builder.append("xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" ");
      builder.append("xmlns:dc=\"http://purl.org/dc/terms/\" ");
      builder.append("xmlns:oslc_cm=\"http://open-services.net/xmlns/cm/1.0/\" ");
      builder.append("xmlns:oslc_rm=\"http://open-services.net/xmlns/rm/1.0/\" ");
      builder.append("xmlns:oslc_am=\"http://open-services.net/ns/am#/\" ");
      builder.append("xmlns:jfs=\"http://jazz.net/xmlns/prod/jazz/jfs/1.0/\" ");
      builder.append(" rdf:about=\"" + jamaServiceBase + "rootservices\">\n");
      
      builder.append("    <dc:title>Jama OSLC Adapter Root Services</dc:title>\n");
      builder.append("    <oslc_cm:cmServiceProviders rdf:resource=\"" + jamaServiceBase + "serviceProviderCatalog\"/>\n");
      builder.append("    <oslc_rm:cmServiceProviders rdf:resource=\"" + jamaServiceBase + "serviceProviderCatalog\"/>\n");
      builder.append("    <oslc_am:cmServiceProviders rdf:resource=\"" + jamaServiceBase + "serviceProviderCatalog\"/>\n");
      builder.append("    <jfs:oauthRequestTokenUrl rdf:resource=\"" + jamaServiceBase + "oauth/requestToken\"/>\n");
      builder.append("    <jfs:oauthUserAuthorizationUrl rdf:resource=\"" + jamaServiceBase + "oauth/authorize\"/>\n");
      builder.append("    <jfs:oauthAccessTokenUrl rdf:resource=\"" + jamaServiceBase + "oauth/accessToken\"/>\n");
      builder.append("    <jfs:oauthRealmName>Jama</jfs:oauthRealmName>\n");
      builder.append("    <jfs:oauthDomain>" + jamaServiceBase + "/</jfs:oauthDomain>\n");

      builder.append("    <jfs:oauthRequestConsumerKeyUrl rdf:resource=\"" + jamaServiceBase + "oauth/requestKey\"/>\n");
      builder.append("    <jfs:oauthApprovalModuleUrl rdf:resource=\"" + jamaServiceBase + "oauth/approveKey\"/>\n");
      builder.append("</rdf:Description>");
      
      String responseBody = builder.toString();
      
      return Response.ok().entity(responseBody).header("max-age", 0).header("pragma", "no-cache")
          .header("Cache-Control", "no-cache").header("OSLC-Core-Version", 2.0)
          .header("Content-Length", responseBody.getBytes().length).type(OslcMediaType.APPLICATION_RDF_XML)
          .build();
   }
}
