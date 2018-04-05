/*******************************************************************************
 * Copyright (c) 2013 IBM Corporation.
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
 *    Kevin Bauer - Initial implementation 
 *******************************************************************************/
package com.jama.oslc.model.trs;

import java.net.URI;

import org.eclipse.lyo.oslc4j.core.annotation.OslcDescription;
import org.eclipse.lyo.oslc4j.core.annotation.OslcName;
import org.eclipse.lyo.oslc4j.core.annotation.OslcNamespace;
import org.eclipse.lyo.oslc4j.core.annotation.OslcPropertyDefinition;
import org.eclipse.lyo.oslc4j.core.annotation.OslcResourceShape;
import org.eclipse.lyo.oslc4j.core.annotation.OslcTitle;
import org.eclipse.lyo.oslc4j.core.model.AbstractResource;



/**
 * Each entry in a trs:changes list is a Local Resource representing a Change Event with the following properties: 
 */
@OslcNamespace(TRSConstants.TRS_NAMESPACE)
@OslcResourceShape(title = "Change Event", describes = TRSConstants.TRS_TYPE_CHANGE_EVENT) 	// added by axel
public class ChangeEvent extends AbstractResource {
	private URI changed;
	private int order;
	
	public ChangeEvent() {}
		
	/**
	 * @param about
	 * @param changed
	 * @param order
	 */
	public ChangeEvent(URI about, URI changed, int order) {
		super(about);
		this.changed = changed;
		this.order = order;
	}

	/**
	 * @return the changed
	 */
	@OslcName(TRSConstants.TRS_TERM_CHANGED)
	@OslcDescription("The Resource that has changed.")
	@OslcPropertyDefinition(TRSConstants.TRS_CHANGED)
	@OslcTitle("Changed")
	public URI getChanged() {
		return changed;
	}
	
	/**
	 * @param changed the changed to set
	 */
	public void setChanged(URI changed) {
		this.changed = changed;
	}
	
	/**
	 * @return the order
	 */
	@OslcName(TRSConstants.TRS_TERM_ORDER)
	@OslcDescription("The sequence in time of the Change Event. The order of Change Events sorted according to this property is the same as they appear in the trs:changes list.")
	@OslcPropertyDefinition(TRSConstants.TRS_ORDER)
	@OslcTitle("Order")
	public int getOrder() {
		return order;
	}
	
	/**
	 * @param order the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}
}