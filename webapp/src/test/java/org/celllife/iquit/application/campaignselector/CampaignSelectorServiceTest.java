package org.celllife.iquit.application.campaignselector;

import java.util.Date;
import java.util.HashMap;

import junit.framework.Assert;

import org.celllife.iquit.test.TestConfiguration;
import org.drools.runtime.StatelessKnowledgeSession;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class CampaignSelectorServiceTest {

	@Autowired
	CampaignSelectorService cService;
	
	@Autowired
	@Qualifier("ksession1")
	StatelessKnowledgeSession ksession;

	@Test
	@Ignore("integration test")
	public void testAddToAndRemoveFromCampaign() throws Exception {
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("gender", "female");
		parameters.put("drinks", "no");
		cService.addToCampaign("27768198075", parameters, new Date());
		cService.removeFromCampaigns("27768198075");
	}
	
	@Test
	public void testMaleDrinkerCampaign() throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("gender", "male");
		parameters.put("drinks", "yes");
		CampaignSelectorServiceImpl cServiceImpl = new CampaignSelectorServiceImpl();
		cServiceImpl.setKsession(ksession);
		String campaign = cServiceImpl.runRules(parameters);
		Assert.assertEquals("male_drinks", campaign);
	}

	@Test
	public void testMaleNonDrinkerCampaign() throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("gender", "male");
		parameters.put("drinks", "no");
		CampaignSelectorServiceImpl cServiceImpl = new CampaignSelectorServiceImpl();
		cServiceImpl.setKsession(ksession);
		String campaign = cServiceImpl.runRules(parameters);
		Assert.assertEquals("no_drinks", campaign);
	}

	@Test
	public void testFemaleDrinkerCampaign() throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("gender", "female");
		parameters.put("drinks", "yes");
		CampaignSelectorServiceImpl cServiceImpl = new CampaignSelectorServiceImpl();
		cServiceImpl.setKsession(ksession);
		String campaign = cServiceImpl.runRules(parameters);
		Assert.assertEquals("female_drinks", campaign);
	}

	@Test
	public void testFemaleNonDrinkerCampaign() throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("gender", "female");
		parameters.put("drinks", "no");
		CampaignSelectorServiceImpl cServiceImpl = new CampaignSelectorServiceImpl();
		cServiceImpl.setKsession(ksession);
		String campaign = cServiceImpl.runRules(parameters);
		Assert.assertEquals("no_drinks", campaign);
	}
}
