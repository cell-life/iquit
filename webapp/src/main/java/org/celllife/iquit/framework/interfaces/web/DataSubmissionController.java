package org.celllife.iquit.framework.interfaces.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.celllife.iquit.application.capture.CaptureService;
import org.celllife.iquit.application.domain.capture.CaptureContext;
import org.celllife.iquit.framework.interfaces.validator.FormValidationException;
import org.celllife.iquit.framework.interfaces.validator.FormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DataSubmissionController {
	
	private static Logger log = LoggerFactory.getLogger(DataSubmissionController.class);

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
	
	private static final String VALIDATION_RULE_SET = "signupformvalidator";

	@ResponseBody
	@RequestMapping(value = "/service/iquit-form", method = RequestMethod.POST)
	public ModelAndView submitForm(HttpServletRequest request, HttpServletResponse response) throws Exception {

		@SuppressWarnings("unchecked")
		Map<String, String[]> params = request.getParameterMap();
		
		Map<String, String> convertedParams = convertParameters(params);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "iQuit");
		map.put("heading", "Thanks");
		map.put("msg", "You have been successfully added to the iQuit campaign.");
		map.put("back", request.getHeader("Referer"));
		
		try {
			// validate form
			FormValidator.validate(VALIDATION_RULE_SET, convertedParams);

			// send to capture
			CaptureContext context = new CaptureContext(signupStudyId, signupFormId, signupFormVersionName,
					signupFormVersionId, signupFormVersionBinding);
			captureService.sendDataToCapture(context, convertedParams);

			
		} catch (FormValidationException e) {
			log.error("Validation error for data '"+params+"' : "+e.getMessage());
			//response.sendError(400, e.getMessage());
			map.put("heading", "Error");
			map.put("msg", e.getMessage());
		}
				
		return new ModelAndView("forms/result", map);
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
