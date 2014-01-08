package org.celllife.iquit.application.campaignselector;

import java.util.Map;

/**
 * Service which facilitates selecting an SMS campaign
 */
public interface CampaignSelectorService {

	/**
	 * Given some parameters (e.g. answers to a questionnaire) use business rules to select to which campaign the user should be assigned.
	 * Note: should specific which rules should be used in order to make this more generic
	 * 
	 * @param parameters Map of data to be used by the rules in order to select the campaign.
	 * @return Integer Communicate campaign identifier (could return a more complex object)
	 */
	Integer selectCampaign(Map<String, Object> parameters);
}
