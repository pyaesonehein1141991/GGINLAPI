package org.tat.gginl.api.common;


public enum ProposalStatus {
	DENY("Deny");

	private String label;

	private ProposalStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}