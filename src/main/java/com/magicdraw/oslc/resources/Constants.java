/*******************************************************************************
 * Copyright (c) 2012, 2014 IBM Corporation.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompanies this distribution.
 *  
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *
 *     Russell Boykin       - initial API and implementation
 *     Alberto Giammaria    - initial API and implementation
 *     Chris Peters         - initial API and implementation
 *     Gianluca Bernardini  - initial API and implementation
 *     Michael Fiedler      - Bugzilla adpater implementations
 *     
 * Modifications performed by:    
 *     Axel Reichwein		- implementation for MagicDraw adapter
 *     (axel.reichwein@koneksys.com) 
 *******************************************************************************/
package com.magicdraw.oslc.resources;


import org.eclipse.lyo.oslc4j.core.model.OslcConstants;

public interface Constants
{
		
	public static String CHANGE_MANAGEMENT_DOMAIN                    = "http://open-services.net/ns/cm#";
    public static String CHANGE_MANAGEMENT_NAMESPACE                 = "http://open-services.net/ns/cm#";
    public static String CHANGE_MANAGEMENT_NAMESPACE_PREFIX          = "oslc_cm";
    public static String FOAF_NAMESPACE                              = "http://xmlns.com/foaf/0.1/";
    public static String FOAF_NAMESPACE_PREFIX                       = "foaf";
    public static String QUALITY_MANAGEMENT_NAMESPACE                = "http://open-services.net/ns/qm#";
    public static String QUALITY_MANAGEMENT_PREFIX                   = "oslc_qm";
    public static String REQUIREMENTS_MANAGEMENT_NAMESPACE           = "http://open-services.net/ns/rm#";
    public static String REQUIREMENTS_MANAGEMENT_PREFIX              = "oslc_rm";
    public static String SOFTWARE_CONFIGURATION_MANAGEMENT_NAMESPACE = "http://open-services.net/ns/scm#";
    public static String SOFTWARE_CONFIGURATION_MANAGEMENT_PREFIX    = "oslc_scm";

    public static String MBSE_PREFIX							 = "mbse";
    public static String MBSE_NAMESPACE							 = "http://eclipse.org/MBSE/";
    
    public static String SYSML_PREFIX								 = "sysml";
    public static String SYSML_NAMESPACE							 = "http://localhost:8080/oslc4jmagicdraw/services/sysml-rdfvocabulary#";
    
    public static String SYSML_MODEL_PREFIX                    		 = "sysml_model";
    public static String SYSML_PACKAGE_PREFIX                    	 = "sysml_package";
    public static String SYSML_REQUIREMENT_PREFIX                    = "sysml_requirement";
    public static String SYSML_BLOCK_PREFIX                    		 = "sysml_block";
    public static String SYSML_INTERFACEBLOCK_PREFIX                 = "sysml_interfaceblock";
    public static String SYSML_ASSOCIATIONBLOCK_PREFIX               = "sysml_associationblock";
    public static String SYSML_PARTPROPERTY_PREFIX                   = "sysml_partproperty";
    public static String SYSML_REFERENCEPROPERTY_PREFIX              = "sysml_referenceproperty";
    public static String SYSML_FLOWPROPERTY_PREFIX              	 = "sysml_flowproperty";
    public static String SYSML_VALUEPROPERTY_PREFIX              	 = "sysml_valueproperty";
    public static String SYSML_ASSOCIATION_PREFIX              		 = "sysml_association";
    public static String SYSML_NAMEDELEMENT_PREFIX              	 = "sysml_namedelement";
    public static String SYSML_MULTIPLICITYELEMENT_PREFIX            = "sysml_multiplicityelement";
    public static String SYSML_PORT_PREFIX            				 = "sysml_port";
    public static String SYSML_PROXYPORT_PREFIX            			 = "sysml_proxyport";
    public static String SYSML_FULLPORT_PREFIX            			 = "sysml_fullport";
    public static String SYSML_BLOCKDIAGRAM_PREFIX            		 = "sysml_blockdiagram";
    public static String SYSML_INTERNALBLOCKDIAGRAM_PREFIX           = "sysml_internalblockdiagram";
    public static String SYSML_CONNECTOR_PREFIX           			 = "sysml_connector";
    public static String SYSML_CONNECTOREND_PREFIX           		 = "sysml_connectorend";
    public static String SYSML_ITEMFLOW_PREFIX           		 	 = "sysml_itemflow";
    public static String SYSML_OWNEDELEMENT_PREFIX           		 = "sysml_ownedelement";
    public static String SYSML_QUANTITYKIND_PREFIX           		 = "sysml_quantitykind";
    public static String SYSML_TYPE_PREFIX           				 = "sysml_type";
    public static String SYSML_UNIT_PREFIX           				 = "sysml_unit";
    public static String SYSML_VALUETYPE_PREFIX           			 = "sysml_valuetype";
    public static String SYSML_PROPERTY_PREFIX           			 = "sysml_property";
    
    public static String SYSML_MODEL_NAMESPACE                    	 = SYSML_NAMESPACE + "Model/";
    public static String SYSML_PACKAGE_NAMESPACE                     = SYSML_NAMESPACE + "Package/";
    public static String SYSML_REQUIREMENT_NAMESPACE                 = SYSML_NAMESPACE + "Requirement/";
    public static String SYSML_BLOCK_NAMESPACE                    	 = SYSML_NAMESPACE + "Block/";
    public static String SYSML_INTERFACEBLOCK_NAMESPACE              = SYSML_NAMESPACE + "InterfaceBlock/";
    public static String SYSML_ASSOCIATIONBLOCK_NAMESPACE            = SYSML_NAMESPACE + "AssociationBlock/";
    public static String SYSML_PARTPROPERTY_NAMESPACE                = SYSML_NAMESPACE + "PartProperty/";
    public static String SYSML_REFERENCEPROPERTY_NAMESPACE           = SYSML_NAMESPACE + "ReferenceProperty/";
    public static String SYSML_FLOWPROPERTY_NAMESPACE           	 = SYSML_NAMESPACE + "FlowProperty/";
    public static String SYSML_VALUEPROPERTY_NAMESPACE           	 = SYSML_NAMESPACE + "ValueProperty/";
    public static String SYSML_ASSOCIATION_NAMESPACE             	 = SYSML_NAMESPACE + "Association/";
    public static String SYSML_NAMEDELEMENT_NAMESPACE              	 = SYSML_NAMESPACE + "NamedElement/";
    public static String SYSML_MULTIPLICITYELEMENT_NAMESPACE         = SYSML_NAMESPACE + "MultiplicityElement/";
    public static String SYSML_PORT_NAMESPACE         				 = SYSML_NAMESPACE + "Port/";
    public static String SYSML_PROXYPORT_NAMESPACE         			 = SYSML_NAMESPACE + "ProxyPort/";
    public static String SYSML_FULLPORT_NAMESPACE         			 = SYSML_NAMESPACE + "FullPort/";
    public static String SYSML_BLOCKDIAGRAM_NAMESPACE         		 = SYSML_NAMESPACE + "BlockDiagram/";
    public static String SYSML_INTERNALBLOCKDIAGRAM_NAMESPACE        = SYSML_NAMESPACE + "InternalBlockDiagram/";
    public static String SYSML_CONNECTOR_NAMESPACE        			 = SYSML_NAMESPACE + "Connector/";
    public static String SYSML_CONNECTOREND_NAMESPACE        		 = SYSML_NAMESPACE + "ConnectorEnd/";
    public static String SYSML_ITEMFLOW_NAMESPACE        			 = SYSML_NAMESPACE + "ItemFlow/";
    public static String SYSML_OWNEDELEMENT_NAMESPACE        		 = SYSML_NAMESPACE + "OwnedElement/";
    public static String SYSML_QUANTITYKIND_NAMESPACE        		 = SYSML_NAMESPACE + "QuantityKind/";
    public static String SYSML_TYPE_NAMESPACE        				 = SYSML_NAMESPACE + "Type/";
    public static String SYSML_UNIT_NAMESPACE        				 = SYSML_NAMESPACE + "Unit/";
    public static String SYSML_VALUETYPE_NAMESPACE        			 = SYSML_NAMESPACE + "ValueType/";
    public static String SYSML_PROPERTY_NAMESPACE        			 = SYSML_NAMESPACE + "Property/";
    
    
    public static String TYPE_SYSML_MODEL                    		 = SYSML_NAMESPACE + "Model";
    public static String TYPE_SYSML_PACKAGE                    		 = SYSML_NAMESPACE + "Package";
    public static String TYPE_SYSML_REQUIREMENT                    	 = SYSML_NAMESPACE + "Requirement";
    public static String TYPE_SYSML_BLOCK                   		 = SYSML_NAMESPACE + "Block";
    public static String TYPE_SYSML_ASSOCIATIONBLOCK               	 = SYSML_NAMESPACE + "AssociationBlock";
    public static String TYPE_SYSML_PARTPROPERTY                  	 = SYSML_NAMESPACE + "PartProperty";
    public static String TYPE_SYSML_REFERENCEPROPERTY              	 = SYSML_NAMESPACE + "ReferenceProperty";
    public static String TYPE_SYSML_ASSOCIATION						 = SYSML_NAMESPACE + "Association";    
    public static String TYPE_SYSML_CONNECTOR    					 = SYSML_NAMESPACE + "Connector";
    public static String TYPE_SYSML_CONNECTOREND    				 = SYSML_NAMESPACE + "ConnectorEnd";
    public static String TYPE_SYSML_PORT    				 		 = SYSML_NAMESPACE + "Port";
    public static String TYPE_SYSML_PROXYPORT    				 	 = SYSML_NAMESPACE + "ProxyPort";
    public static String TYPE_SYSML_FULLPORT    				     = SYSML_NAMESPACE + "FullPort";       
    public static String TYPE_SYSML_INTERFACEBLOCK			 		 = SYSML_NAMESPACE + "InterfaceBlock";
    public static String TYPE_SYSML_FLOWPROPERTY					 = SYSML_NAMESPACE + "FlowProperty";
    public static String TYPE_SYSML_BOUNDREFERENCE					 = SYSML_NAMESPACE + "BoundReference";
    public static String TYPE_SYSML_ITEMFLOW						 = SYSML_NAMESPACE + "ItemFlow";       
    public static String TYPE_SYSML_QUANTITYKIND					 = SYSML_NAMESPACE + "QuantityKind";
    public static String TYPE_SYSML_TYPE							 = SYSML_NAMESPACE + "Type";
    public static String TYPE_SYSML_UNIT							 = SYSML_NAMESPACE + "Unit";
    public static String TYPE_SYSML_VALUEPROPERTY					 = SYSML_NAMESPACE + "ValueProperty";
    public static String TYPE_SYSML_VALUETYPE						 = SYSML_NAMESPACE + "ValueType";
    public static String TYPE_SYSML_BLOCKDIAGRAM					 = SYSML_NAMESPACE + "BlockDiagram";
    public static String TYPE_SYSML_INTERNALBLOCKDIAGRAM			 = SYSML_NAMESPACE + "InternalBlockDiagram";
    public static String TYPE_SYSML_OWNEDELEMENT					 = SYSML_NAMESPACE + "OwnedElement";
    
    
    public static String SYSML_MODEL_DOMAIN                    		 = "http://omg.org/formal/2012-06-01-sysml/model/rdf#";
    public static String SYSML_PACKAGE_DOMAIN                    	 = "http://omg.org/formal/2012-06-01-sysml/package/rdf#";
    public static String SYSML_REQUIREMENT_DOMAIN                    = "http://omg.org/formal/2012-06-01-sysml/requirement/rdf#";
    public static String SYSML_BLOCK_DOMAIN                    		 = "http://omg.org/formal/2012-06-01-sysml/block/rdf#";
    public static String SYSML_ASSOCIATIONBLOCK_DOMAIN               = "http://omg.org/formal/2012-06-01-sysml/associationblock/rdf#";
    public static String SYSML_PARTPROPERTY_DOMAIN                   = "http://omg.org/formal/2012-06-01-sysml/partproperty/rdf#";
    public static String SYSML_REFERENCEPROPERTY_DOMAIN              = "http://omg.org/formal/2012-06-01-sysml/referenceproperty/rdf#";
    public static String SYSML_CONNECTOR_DOMAIN              		 = "http://omg.org/formal/2012-06-01-sysml/connector/rdf#";
    public static String SYSML_CONNECTOREND_DOMAIN              	 = "http://omg.org/formal/2012-06-01-sysml/connectorend/rdf#";
    public static String SYSML_PORT_DOMAIN              	 		 = "http://omg.org/formal/2012-06-01-sysml/port/rdf#";
    public static String SYSML_PROXYPORT_DOMAIN              	 	 = "http://omg.org/formal/2012-06-01-sysml/proxyport/rdf#";
    public static String SYSML_FULLPORT_DOMAIN              	 	 = "http://omg.org/formal/2012-06-01-sysml/fullport/rdf#";
    public static String SYSML_INTERFACEBLOCK_DOMAIN				 = "http://omg.org/formal/2012-06-01-sysml/interfaceblock/rdf#";
    public static String SYSML_FLOWPROPERTY_DOMAIN				 	 = "http://omg.org/formal/2012-06-01-sysml/flowproperty/rdf#";
    public static String SYSML_ITEMFLOW_DOMAIN				 	 	 = "http://omg.org/formal/2012-06-01-sysml/itemflow/rdf#";
    public static String SYSML_VALUEPROPERTY_DOMAIN				 	 = "http://omg.org/formal/2012-06-01-sysml/valueproperty/rdf#";
    public static String SYSML_VALUETYPE_DOMAIN				 	 	 = "http://omg.org/formal/2012-06-01-sysml/valuetype/rdf#";
    public static String SYSML_BLOCKDIAGRAM_DOMAIN					 = "http://omg.org/formal/2012-06-01-sysml/blockdiagram/rdf#";
    public static String SYSML_INTERNALBLOCKDIAGRAM_DOMAIN			 = "http://omg.org/formal/2012-06-01-sysml/internalblockdiagram/rdf#";
    

    public static String  PATH_SYSML_MODEL							 = "model";
    public static String  PATH_SYSML_PACKAGE						 = "package";
    public static String  PATH_SYSML_REQUIREMENT					 = "requirement";
    public static String  PATH_SYSML_BLOCK							 = "block";
    public static String  PATH_SYSML_ASSOCIATIONBLOCK				 = "associationBlock";
    public static String  PATH_SYSML_PARTPROPERTY					 = "partProperty";
    public static String  PATH_SYSML_REFERENCEPROPERTY				 = "referenceProperty";
    public static String  PATH_SYSML_CONNECTOR				         = "connector";
    public static String  PATH_SYSML_CONNECTOREND				     = "connectorEnd";
    public static String  PATH_SYSML_PORT				     		 = "port";
    public static String  PATH_SYSML_PROXYPORT				     	 = "proxyPort";
    public static String  PATH_SYSML_FULLPORT				     	 = "fullPort";
    public static String  PATH_SYSML_INTERFACEBLOCK				     = "interfaceBlock";
    public static String  PATH_SYSML_FLOWPROPERTY				     = "flowProperty";
    public static String  PATH_SYSML_ITEMFLOW				     	 = "itemFlow";
    public static String  PATH_SYSML_VALUEPROPERTY				     = "valueProperty";
    public static String  PATH_SYSML_VALUETYPE				     	 = "valueType";
    public static String  PATH_SYSML_BLOCKDIAGRAM				     = "blockDiagram";
    public static String  PATH_SYSML_INTERNALBLOCKDIAGRAM		 	 = "internalBlockDiagram";
    
    public static String CHANGE_REQUEST             = "ChangeRequest";
    public static String TYPE_CHANGE_REQUEST        = CHANGE_MANAGEMENT_NAMESPACE + "ChangeRequest";
    public static String TYPE_CHANGE_SET            = SOFTWARE_CONFIGURATION_MANAGEMENT_NAMESPACE + "ChangeSet";
    public static String TYPE_DISCUSSION            = OslcConstants.OSLC_CORE_NAMESPACE + "Discussion";
    public static String TYPE_PERSON                = FOAF_NAMESPACE + "Person";
    public static String TYPE_REQUIREMENT           = REQUIREMENTS_MANAGEMENT_NAMESPACE + "Requirement";
    public static String TYPE_TEST_CASE             = QUALITY_MANAGEMENT_NAMESPACE + "TestCase";
    public static String TYPE_TEST_EXECUTION_RECORD = QUALITY_MANAGEMENT_NAMESPACE + "TestExecutionRecord";
    public static String TYPE_TEST_PLAN             = QUALITY_MANAGEMENT_NAMESPACE + "TestPlan";
    public static String TYPE_TEST_RESULT           = QUALITY_MANAGEMENT_NAMESPACE + "TestResult";
    public static String TYPE_TEST_SCRIPT           = QUALITY_MANAGEMENT_NAMESPACE + "TestScript";

    

    
    
    public static String PATH_CHANGE_REQUEST = "changeRequest";

    public static String USAGE_LIST = CHANGE_MANAGEMENT_NAMESPACE + "list";
    
    public static final String HDR_OSLC_VERSION = "OSLC-Core-Version";
    
    
}
