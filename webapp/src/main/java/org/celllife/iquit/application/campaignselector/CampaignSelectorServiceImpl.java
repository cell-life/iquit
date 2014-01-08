package org.celllife.iquit.application.campaignselector;

import java.util.Map;

import org.celllife.iquit.application.domain.campaignselector.RulesContext;
import org.drools.runtime.StatelessKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CampaignSelectorServiceImpl implements CampaignSelectorService {
	
	@Autowired
	@Qualifier("ksession1")
	StatelessKnowledgeSession ksession;

	@Override
	public Integer selectCampaign(Map<String, Object> parameters) {
		RulesContext bob = new RulesContext(parameters);
		ksession.execute(bob);
		return (Integer)bob.getReturnValue();
	}

}
