package org.celllife.iquit.framework.interfaces.web;

import java.io.BufferedReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.celllife.iquit.application.CaptureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DataSubmissionController {

    @Autowired
    CaptureService captureService;

    @ResponseBody
    @RequestMapping(value = "/service/iquit-form", method = RequestMethod.POST)
    public void submitForm(HttpServletRequest request, HttpServletResponse response) throws Exception {

        BufferedReader reader = request.getReader();
        String line = "";
        String data = "";

        while ((line = reader.readLine()) != null) {
            data = data + line;
        }

        Map<String, List<String>> parameterMap = convertToParameters(data);
        captureService.sendDataToCapture(parameterMap);

    }

    /**
     * Converts form data to parameter map.
     * Source: http://stackoverflow.com/questions/5902090/how-to-extract-parameters-from-a-given-url
     *
     * @param formData The parameters form the html form submission eg. gender=female&name=john
     * @return Map of parameters.
     */
    private Map<String, List<String>> convertToParameters(String formData) {

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
