package org.celllife.iquit.application.domain.campaignselector;

import java.util.Map;

/**
 * Class that contains a parameter map that can be used from a drools rule file and a return value that can be returned
 */
public class RulesContext {
	
	private Map<String, Object> parameters;
	private Object returnValue;
	

	public RulesContext() {
		
	}
	
	public RulesContext(Map<String, Object> parameters) {
		this.parameters = parameters;
	}
	
	/**
	 * Sets the parameters to use while processing rules
	 * @param parameters Map with parameter name/value
	 */
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}
	
	public Object get(String key) {
		return parameters.get(key);
	}

	/**
	 * Sets the result of the rules processing. For example the campaign ID selected 
	 * @param returnValue Object
	 */
	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}
	
	public Object getReturnValue() {
		return returnValue;
	}
}
