package org.celllife.iquit.application.capture;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringEscapeUtils;
import org.celllife.iquit.application.domain.capture.CaptureContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CaptureServiceImpl implements CaptureService {

	private static Logger log = LoggerFactory.getLogger(CaptureServiceImpl.class);

	@Value("${capture.baseurl}")
	String captureBaseUrl;

	@Value("${capture.username}")
	String captureUser;

	@Value("${capture.password}")
	String capturePassword;

	@Async("defaultTaskExecutor")
	public void sendDataToCapture(CaptureContext captureContext, Map<String, String> parameters) throws Exception {

		String xml = createRequestXml(captureContext, parameters);
		if (log.isDebugEnabled()) {
			log.debug("XML to submit to Capture " + xml);
		}

		String captureUrl = createCaptureSubmitUrl(captureBaseUrl, captureContext);

		sendToCapture(captureUrl, xml);
	}

	int sendToCapture(String captureUrl, String xml) throws IOException {
		URL url = new URL(captureUrl);

		DataOutputStream dataOutputStream = null;
		try {
			if (log.isDebugEnabled()) {
				log.debug("Sending data to the Capture server '" + captureUrl + "' as user '" + captureUser + "'");
			}
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Content-Type", "application/xml");
			String encodedString = new String(Base64.encodeBase64((captureUser + ":" + capturePassword)
					.getBytes("UTF-8")), "UTF-8");
			httpURLConnection.setRequestProperty("Authorization", "Basic " + encodedString);
			httpURLConnection.setDoOutput(true);

			dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
			dataOutputStream.writeBytes(xml);
			dataOutputStream.flush();

			int responseCode = httpURLConnection.getResponseCode();

			if (responseCode != 200) {
				throw new RuntimeException("Capture server returned response code '" + responseCode + "'");
			} else {
				log.debug("Data submitted successfully to the Capture server.");
			}

			return responseCode;

		} catch (Exception e) {
			log.error("Could not submit data to the Capture server. URL='" + captureUrl + "' username='" + captureUser
					+ "'", e);
			log.error("XML submitted: " + xml);
			throw e;
		} finally {
			if (dataOutputStream != null) {
				dataOutputStream.close();
			}
		}
	}

	String createCaptureSubmitUrl(String baseUrl, CaptureContext captureContext) {
		StringBuilder submitUrl = new StringBuilder(baseUrl);
		if (!baseUrl.endsWith("/")) {
			submitUrl.append("/");
		}
		submitUrl.append("studies/");
		submitUrl.append(captureContext.getStudyId());
		submitUrl.append("/forms/");
		submitUrl.append(captureContext.getFormId());
		submitUrl.append("/versions/");
		submitUrl.append(captureContext.getFormVersionId());
		submitUrl.append("/data");
		return submitUrl.toString();
	}

	String createRequestXml(CaptureContext captureContext, Map<String, String> parameterMap) {

		StringBuilder xml = new StringBuilder();

		xml.append("<FormData><data>");
		xml.append(StringEscapeUtils.escapeHtml(convertToDataXml(captureContext, parameterMap)));
		xml.append("</data></FormData>");

		return xml.toString();
	}
	
	String convertToDataXml(CaptureContext captureContext, Map<String, String> parameterMap) {
		StringBuilder dataXml = new StringBuilder();
		
		dataXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		
		dataXml.append("<");
		dataXml.append(captureContext.getFormVersionBinding());
		dataXml.append(" xmlns=\"http://www.w3.org/2002/xforms\" " + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"");
		dataXml.append(" name=\"");
		dataXml.append(captureContext.getFormVersionName());
		dataXml.append("\" id=\"");
		dataXml.append(captureContext.getFormVersionId());
		dataXml.append("\" formKey=\"");
		dataXml.append(captureContext.getFormVersionBinding());
		dataXml.append("\">");

		for (String parameter : parameterMap.keySet()) {
			String value = parameterMap.get(parameter);
			if (value != null && !value.trim().equals("")) {
				dataXml.append("<" + parameter + ">");
				dataXml.append(value);
				dataXml.append("</" + parameter + ">");
			}
		}

		dataXml.append("</");
		dataXml.append(captureContext.getFormVersionBinding());
		dataXml.append(">");
		
		return dataXml.toString();
	}
}