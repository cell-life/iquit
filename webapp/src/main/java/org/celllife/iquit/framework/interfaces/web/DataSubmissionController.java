package org.celllife.iquit.framework.interfaces.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.celllife.iquit.application.capture.CaptureService;
import org.celllife.iquit.application.domain.capture.CaptureContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DataSubmissionController {

	@Autowired
	CaptureService captureService;

	@Value("${capture.signup.study.id}")
	String signupStudyId;

	@Value("${capture.signup.form.id}")
	String signupFormId;

	@Value("${capture.signup.form.version.name}")
	String signupFormVersionName;

	@Value("${capture.signup.form.version.id}")
	String signupFormVersionId;

	@Value("${capture.signup.form.version.binding}")
	String signupFormVersionBinding;

	@ResponseBody
	@RequestMapping(value = "/service/iquit-form", method = RequestMethod.POST)
	public void submitForm(HttpServletRequest request, HttpServletResponse response) throws Exception {

		@SuppressWarnings("unchecked")
		Map<String, String[]> params = request.getParameterMap();

		CaptureContext context = new CaptureContext(signupStudyId, signupFormId, signupFormVersionName,
				signupFormVersionId, signupFormVersionBinding);

		captureService.sendDataToCapture(context, convertParameters(params));
	}
	
	private Map<String, String> convertParameters(Map<String, String[]> params) {
		HashMap<String, String> newParams = new HashMap<String, String>();
		for (String key : params.keySet()) {
			String[] values = params.get(key);
			if (values != null) {
				String value = StringUtils.join(values, ' ');
				newParams.put(key, value);
			}
		}
		return newParams;
	}
}
