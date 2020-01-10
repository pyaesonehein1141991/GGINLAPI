package org.tat.gginl.api.common;

public enum OperationType {
	OPERATION("Operation"), ABORTION("Abortion");

	private String label;

	private OperationType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
