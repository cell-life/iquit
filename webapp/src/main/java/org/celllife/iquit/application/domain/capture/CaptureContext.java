package org.celllife.iquit.application.domain.capture;

import java.io.Serializable;

/**
 * Stores all the relevant information needed to submit data to capture.
 * 
 * This includes the information about the FormDefVersion, the Form and the
 * associated Study.
 */
public class CaptureContext implements Serializable {

	private static final long serialVersionUID = -1483066301300412317L;

	private String studyId;
	private String formId;
	private String formVersionName;
	private String formVersionId;
	private String formVersionBinding;
	
	public CaptureContext() {
		
	}

	public CaptureContext(String studyId, String formId, String formVersionName, String formVersionId,
			String formVersionBinding) {
		this.studyId = studyId;
		this.formId = formId;
		this.formVersionName = formVersionName;
		this.formVersionId = formVersionId;
		this.formVersionBinding = formVersionBinding;
	}

	public CaptureContext(String formVersionName, String formVersionId, String formVersionBinding) {
		this.formVersionName = formVersionName;
		this.formVersionId = formVersionId;
		this.formVersionBinding = formVersionBinding;
	}

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getFormVersionName() {
		return formVersionName;
	}

	public void setFormVersionName(String formVersionName) {
		this.formVersionName = formVersionName;
	}

	public String getFormVersionId() {
		return formVersionId;
	}

	public void setFormVersionId(String formVersionId) {
		this.formVersionId = formVersionId;
	}

	public String getFormVersionBinding() {
		return formVersionBinding;
	}

	public void setFormVersionBinding(String formVersionBinding) {
		this.formVersionBinding = formVersionBinding;
	}
}
