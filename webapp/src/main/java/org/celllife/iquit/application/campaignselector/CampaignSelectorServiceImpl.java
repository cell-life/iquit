package org.celllife.iquit.application.campaignselector;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.celllife.iquit.application.domain.campaignselector.RulesContext;
import org.celllife.mobilisr.api.rest.ContactDto;
import org.celllife.mobilisr.client.MobilisrClient;
import org.celllife.mobilisr.client.exception.RestCommandException;
import org.drools.runtime.StatelessKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CampaignSelectorServiceImpl implements CampaignSelectorService {
	
	private static Logger log = LoggerFactory.getLogger(CampaignSelectorServiceImpl.class);
	
	@Autowired
	@Qualifier("ksession1")
	StatelessKnowledgeSession ksession;
	
	@Autowired
	MobilisrClient communicateClient;

	@Resource
	@Qualifier("campaignMap")
	private Map<String, String> campaignMap;

	@Override
	public Long selectCampaign(Map<String, Object> parameters) {
		String campaign_name = runRules(parameters);
		Long campaignId = Long.parseLong(campaignMap.get(campaign_name.trim()));
		return campaignId;
	}
	
	@Override
	public void addToCampaign(String msisdn, Map<String, String> parameters, Date startDate) {
		Map<String, Object> parameters2 = new HashMap<String, Object>();
		for (String key : parameters.keySet()) {
			String object = parameters.get(key).toString();
			parameters2.put(key, object);
		}
		String campaign_name = runRules(parameters2);
		Long campaignId = Long.parseLong(campaignMap.get(campaign_name.trim()));
		addToCampaign(msisdn, campaignId, startDate);
	}

	@Override
	public void removeFromCampaigns(String msisdn) {
		log.debug("removeFromCampaigns [end]. Removing msisdn '"+msisdn+"' from all known campaigns");
		ContactDto contact = new ContactDto();
		contact.setMsisdn(msisdn);
		for (String campaignId : campaignMap.values()) {
			try {
				Long id = Long.parseLong(campaignId);
				communicateClient.getCampaignService().removeContactFromCampaign(id, contact);
			} catch (RestCommandException e) {
				log.error("Could not send an SMS to '"+msisdn+"'.",e);
			}
		}
		log.debug("removeFromCampaigns [end]. Removed msisdn '"+msisdn+"' from all known campaigns");
	}

	
	String runRules(Map<String, Object> parameters) {
		RulesContext bob = new RulesContext(parameters);
		ksession.execute(bob);
		String campaign_name = (String) bob.getReturnValue();
		return campaign_name;
	}

	void addToCampaign(String msisdn, Long campaignId, Date startDate) {
		log.debug("selectCampaign [end]. Adding msisdn '"+msisdn+"' to campaign '"+campaignId+"'.");
		ContactDto contact = new ContactDto();
		contact.setMsisdn(msisdn);
		contact.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(startDate));
		try {
			communicateClient.getCampaignService().addContactToCampaign(campaignId, contact);
		} catch (RestCommandException e) {
			log.error("Could not send an SMS to '"+msisdn+"'.",e);
		}
		log.debug("selectCampaign [end]. Should have added contact '"+contact+"' to campaign '"+campaignId+"'.");
	}

	public void setCommunicateClient(MobilisrClient communicateClient) {
		this.communicateClient = communicateClient;
	}

	public void setKsession(StatelessKnowledgeSession ksession) {
		this.ksession = ksession;
	}
}