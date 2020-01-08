package org.tat.gginl.api.common.enumdata;

public enum MaritalStatus {
	MARRIED("Married"), SINGLE("Single"), DIVORCED("Divorced");
	private String label;

	private MaritalStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
