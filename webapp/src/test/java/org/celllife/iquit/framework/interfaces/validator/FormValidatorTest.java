package org.celllife.iquit.framework.interfaces.validator;

import java.util.HashMap;

import junit.framework.Assert;

import org.drools.runtime.StatelessKnowledgeSession;
import org.junit.Test;


public class FormValidatorTest {


	@Test(expected=org.celllife.iquit.framework.interfaces.validator.FormValidationException.class)
	public void testYearWithLetters() throws Exception {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("year", "ABCD");
		FormValidator.validate("testformvalidator", params);
	}

	@Test(expected=org.celllife.iquit.framework.interfaces.validator.FormValidationException.class)
	public void testTooShortYear() throws Exception {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("year", "12");
		FormValidator.validate("testformvalidator", params);
	}
	
	@Test
	public void testValidYear() throws Exception {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("year", "1234");
		FormValidator.validate("testformvalidator", params);
		// if no exception is thrown, we are OK
	}

	@Test(expected=org.celllife.iquit.framework.interfaces.validator.FormValidationException.class)
	public void testUnder18() throws Exception {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("year", "2008");
		FormValidator.validate("testformvalidator", params);
	}
	
	@Test
	public void testCache() throws Exception {
		StatelessKnowledgeSession session1 = FormValidator.getRuleSession("testformvalidator");
		StatelessKnowledgeSession session2 = FormValidator.getRuleSession("testformvalidator");
		Assert.assertNotNull(session1);
		Assert.assertEquals(session1, session2);
		Assert.assertTrue(session1 == session2);
	}

	@Test
	public void testInvalidSessionCache() throws Exception {
		StatelessKnowledgeSession session = FormValidator.getRuleSession("XYZ");
		Assert.assertNull(session);
	}
}
