package org.celllife.iquit.application;

import java.util.List;
import java.util.Map;

public interface CaptureService {

    /**
     * Sends the paramters in the map to Capture.
     * @param parameters Parameters as submitted via html form.
     * @throws Exception
     */
    public void sendDataToCapture(Map<String, List<String>> parameters) throws Exception;

}
