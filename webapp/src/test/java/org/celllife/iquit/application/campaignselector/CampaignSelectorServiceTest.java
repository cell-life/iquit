package org.celllife.iquit.application.campaignselector;

import java.util.HashMap;

import junit.framework.Assert;

import org.celllife.iquit.test.TestConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class CampaignSelectorServiceTest {

	@Autowired
	CampaignSelectorService cService;
	
	@Test
	public void testMaleDrinkerCampaign() throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("gender", "male");
		parameters.put("drinks", "yes");
		Integer campaign = cService.selectCampaign(parameters);
		Assert.assertEquals(new Integer(1), campaign);
	}

	@Test
	public void testMaleNonDrinkerCampaign() throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("gender", "male");
		parameters.put("drinks", "no");
		Integer campaign = cService.selectCampaign(parameters);
		Assert.assertEquals(new Integer(2), campaign);
	}

	@Test
	public void testFemaleDrinkerCampaign() throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("gender", "female");
		parameters.put("drinks", "yes");
		Integer campaign = cService.selectCampaign(parameters);
		Assert.assertEquals(new Integer(3), campaign);
	}

	@Test
	public void testFemaleNonDrinkerCampaign() throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("gender", "female");
		parameters.put("drinks", "no");
		Integer campaign = cService.selectCampaign(parameters);
		Assert.assertEquals(new Integer(4), campaign);
	}
}
