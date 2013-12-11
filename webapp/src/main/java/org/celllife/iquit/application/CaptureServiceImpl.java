package org.celllife.iquit.application;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Service
public class CaptureServiceImpl implements CaptureService {

    private static Logger log = LoggerFactory.getLogger(CaptureServiceImpl.class);

    @Value("${capture.url}")
    String captureUrl;

    @Value("${capture.username}")
    String captureUser;

    @Value("${capture.password}")
    String capturePassword;

    public void sendDataToCapture(Map<String, List<String>> parameters) throws Exception {

        Properties prop = new Properties();

        URL url = new URL(captureUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/xml");
        String encodedString = new String(Base64.encodeBase64((captureUser + ":" + capturePassword).getBytes("UTF-8")), "UTF-8");
        httpURLConnection.setRequestProperty("Authorization", "Basic " + encodedString);
        httpURLConnection.setDoOutput(true);

        DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
        dataOutputStream.writeBytes(convertToXml(parameters));
        dataOutputStream.flush();
        dataOutputStream.close();

        int responseCode = httpURLConnection.getResponseCode();

        if (responseCode != 200) {
            log.error("Could not submit data to the Capture server.");
        }
    }

    private String convertToXml(Map<String, List<String>> parameterMap) {

        String xmlString = "<FormData><data>" + StringEscapeUtils.escapeHtml("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

        xmlString = xmlString.concat(StringEscapeUtils.escapeHtml("<example_study_iquit_form_v1 " +
                "xmlns=\"http://www.w3.org/2002/xforms\" " +
                "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
                "name=\"iQuit Form_v1\" " +
                "id=\"2\" " +
                "formKey=\"example_study_iquit_form_v1\">"));

        for (String parameter : parameterMap.keySet()) {

            if (parameterMap.get(parameter).get(0) != "") {

                xmlString = xmlString.concat(StringEscapeUtils.escapeHtml("<" + parameter + " " +
                        "xmlns=\"http://www.w3.org/2002/xforms\">"));

                for (String value : parameterMap.get(parameter))    {
                    xmlString = xmlString + StringEscapeUtils.escapeHtml(value + " ");
                }

                xmlString = xmlString + StringEscapeUtils.escapeHtml("</" + parameter + ">");
            }

        }

        xmlString = xmlString.concat(StringEscapeUtils.escapeHtml("</example_study_iquit_form_v1>") +
                "</data></FormData>");

        return xmlString;

    }

}
