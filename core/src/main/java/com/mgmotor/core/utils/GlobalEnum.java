package com.mgmotor.core.utils;

/**
 * @author Anomitra
 *
 */
public enum GlobalEnum {
	
	COMPONENT_POLICY_PATH_ROOT_FRAGMENT("/policies/jcr:content/root"),
	TEMPLATE_POLICY_PATH_FRAGMENT("/settings/wcm/policies");
	
	
	private String value;

	private GlobalEnum(String value) {
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}

}
