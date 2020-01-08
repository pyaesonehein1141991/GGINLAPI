package org.tat.gginl.api.common.enumdata;




public enum ClaimStatus {
	ACTIVE("ACTIVE"), CLOSED("CLOSED"), REOPEN("REOPEN");
	private String label;

	private ClaimStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}

