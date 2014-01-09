package org.celllife.iquit.application.capture;

import java.util.List;
import java.util.Map;

import org.celllife.iquit.application.domain.capture.CaptureContext;

public interface CaptureService {

	/**
	 * Sends the parameters in the map to Capture.
	 * 
	 * @param CaptureContext context data with form and study details
	 * @param parameters Parameters as submitted via html form.
	 * @throws Exception
	 */
	public void sendDataToCapture(CaptureContext captureContext, Map<String, List<String>> parameters) throws Exception;

}
