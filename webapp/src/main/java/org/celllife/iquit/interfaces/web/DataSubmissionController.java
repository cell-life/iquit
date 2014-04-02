package org.celllife.iquit.interfaces.web;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.celllife.iquit.application.campaignselector.CampaignSelectorService;
import org.celllife.iquit.application.capture.CaptureService;
import org.celllife.iquit.application.domain.capture.CaptureContext;
import org.celllife.iquit.framework.interfaces.validator.FormValidationException;
import org.celllife.iquit.framework.interfaces.validator.FormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DataSubmissionController {
	
	private static Logger log = LoggerFactory.getLogger(DataSubmissionController.class);
	
	@Autowired
	CampaignSelectorService campaignSelectorService;

	@Autowired
	CaptureService captureService;
	
	// signup form details
	@Autowired()
	@Qualifier("signupFormContext")
	CaptureContext signupFormContext;
	
	// optout form details
	@Autowired()
	@Qualifier("optoutFormContext")
	CaptureContext optoutFormContext;
	
	private static final String SIGNUP_VALIDATION_RULE_SET = "signupformvalidator";
	
	private static final String OPTOUT_VALIDATION_RULE_SET = "optoutformvalidator";

	@ResponseBody
	@RequestMapping(value = "/service/iquit-form", method = RequestMethod.POST)
	public ModelAndView submitSignupForm(HttpServletRequest request, HttpServletResponse response) throws Exception {

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
			FormValidator.validate(SIGNUP_VALIDATION_RULE_SET, convertedParams);

			// send to capture
			captureService.sendDataToCapture(signupFormContext, convertedParams);
			
			// get the msisdn
			String msisdn = convertedParams.get("msisdn");
			
			// get the start date
			Date startDate = getStartDate(convertedParams.get("quit_date"));
			
			// add to communicate campaign
			campaignSelectorService.addToCampaign(msisdn, convertedParams, startDate);

			
		} catch (FormValidationException e) {
			log.error("Validation error for data '"+params+"' : "+e.getMessage());
			//response.sendError(400, e.getMessage());
			map.put("heading", "Error");
			map.put("msg", e.getMessage());
		}
				
		return new ModelAndView("forms/result", map);
	}

	@ResponseBody
	@RequestMapping(value = "/service/optout-form", method = RequestMethod.POST)
	public ModelAndView submitOptOutForm(HttpServletRequest request, HttpServletResponse response) throws Exception {

		@SuppressWarnings("unchecked")
		Map<String, String[]> params = request.getParameterMap();
		
		Map<String, String> convertedParams = convertParameters(params);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "iQuit");
		map.put("heading", "Thanks");
		map.put("msg", "You have been successfully removed from the iQuit campaign.");
		map.put("back", request.getHeader("Referer"));
		
		try {
			// validate form
			FormValidator.validate(OPTOUT_VALIDATION_RULE_SET, convertedParams);
		
			// send to capture
			captureService.sendDataToCapture(optoutFormContext, convertedParams);
			
			// get the msisdn
			String msisdn = convertedParams.get("msisdn");

			// remove from all known communicate campaigns
			campaignSelectorService.removeFromCampaigns(msisdn);

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

	private Date getStartDate(String quitDateParameter) {
		Date startDate = new Date();
		try {
			String days = quitDateParameter.replace("in", "").replace("days", "");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, Integer.parseInt(days));
			startDate = cal.getTime();
		} catch (NumberFormatException e) {
			log.error("Could not convert "+quitDateParameter+" to a number", e);
		}
		return startDate;
	}
}
