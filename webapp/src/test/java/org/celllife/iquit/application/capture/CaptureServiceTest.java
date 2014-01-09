package org.celllife.iquit.application.capture;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.celllife.iquit.test.TestConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class CaptureServiceTest {
	
	@Autowired
	CaptureService captureService;

	@Test
	public void testConvertXml() throws Exception {
		CaptureServiceImpl captureServiceImpl = new CaptureServiceImpl();
		Map<String, List<String>> parameterMap = new HashMap<String, List<String>>();
		parameterMap.put("one", Arrays.asList("A", "B", "C"));
		parameterMap.put("two", Arrays.asList("D"));
		String xml = captureServiceImpl.convertToXml(parameterMap);
		Assert.assertEquals("<FormData><data>" +
				"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;" +
				"&lt;example_study_iquit_form_v1 xmlns=&quot;http://www.w3.org/2002/xforms&quot; xmlns:xsd=&quot;http://www.w3.org/2001/XMLSchema&quot; name=&quot;iQuit Form_v1&quot; id=&quot;2&quot; formKey=&quot;example_study_iquit_form_v1&quot;&gt;" +
				"&lt;two&gt;D &lt;/two&gt;" +
				"&lt;one&gt;A B C &lt;/one&gt;" +
				"&lt;/example_study_iquit_form_v1&gt;" +
				"</data></FormData>", xml);
	}
}
