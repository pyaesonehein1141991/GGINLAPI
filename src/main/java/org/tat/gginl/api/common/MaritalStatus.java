package org.tat.gginl.api.common;

	import javax.xml.bind.annotation.XmlEnum;
	import javax.xml.bind.annotation.XmlType;

	@XmlType(name = "maritalStatus")
	@XmlEnum
	public enum MaritalStatus {
		MARRIED("Married"), SINGLE("Single"), DIVORCED("Divorced"), WIDOWED("Widowed");

		private String label;

		private MaritalStatus(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}
	}


