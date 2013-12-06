package org.celllife.iquit.framework.interfaces.web;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DataSubmissionController {

    @ResponseBody
    @RequestMapping(value = "/service/iquit-form", method = RequestMethod.POST)
    public void submitForm(HttpServletRequest request, HttpServletResponse response) throws Exception {

        BufferedReader reader = request.getReader();
        String line = "";
        String data = "";

        while ((line = reader.readLine()) != null) {
            data = data + line;
        }

        // TODO: This logic should be stripped out to an application service!
        Map<String, List<String>> parameterMap = convertToParameters(data);
        sendDataToCapture(convertToXml(parameterMap));

    }

    private void sendDataToCapture(String data) throws Exception {

        URL url = new URL("http://localhost:8080/capture/rest/studies/1/forms/2/versions/2/data");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/xml");
        byte[] encoded = Base64.encode("admin:poekie1".getBytes());
        con.setRequestProperty("Authorization", "Basic " + encoded);

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(data);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //print result
        System.out.println(response.toString());

    }


    public String convertToXml(Map<String, List<String>> parameterMap) {

        String xmlString = "<FormData><data>" + StringEscapeUtils.escapeHtml("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

        xmlString = xmlString.concat(StringEscapeUtils.escapeHtml("<example_study_iquit_form_v1 " +
                "xmlns=\"http://www.w3.org/2002/xforms\" " +
                "name=\"iQuit Form_v1\" " +
                "id=\"2\" " +
                "formKey=\"example_study_iquit_form_v1\" " +
                "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"));

        for (String parameter : parameterMap.keySet()) {

            if (parameterMap.get(parameter).get(0) != "") {

                xmlString = xmlString.concat(StringEscapeUtils.escapeHtml("<" + parameter + " " +
                        "xmlns=\"http://www.w3.org/2002/xforms\">" +
                        parameterMap.get(parameter).get(0) +
                        "<\\" + parameter + ">"));
            }

        }

        xmlString.concat(StringEscapeUtils.escapeHtml("/example_study_iquit_form_v1>\n") +
                "</FormData></data>");

        return xmlString;

    }

    /**
     * Converts fromm data to parameter map.
     * Source: http://stackoverflow.com/questions/5902090/how-to-extract-parameters-from-a-given-url
     *
     * @param formData
     * @return Map of parameters.
     */
    public Map<String, List<String>> convertToParameters(String formData) {

        Map<String, List<String>> parameters = new HashMap<String, List<String>>();

        try {
            String query = formData;
            for (String param : query.split("&")) {
                String[] pair = param.split("=");
                String key = URLDecoder.decode(pair[0], "UTF-8");
                String value = "";
                if (pair.length > 1) {
                    value = URLDecoder.decode(pair[1], "UTF-8");
                }

                List<String> values = parameters.get(key);
                if (values == null) {
                    values = new ArrayList<String>();
                    parameters.put(key, values);
                }
                values.add(value);
            }

        } catch (Exception e) {

        }

        return parameters;

    }

}
