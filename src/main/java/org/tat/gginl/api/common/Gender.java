package org.tat.gginl.api.common;
	import javax.xml.bind.annotation.XmlEnum;
	import javax.xml.bind.annotation.XmlType;

	@XmlType(name = "gender")
	@XmlEnum
	public enum Gender {
		FEMALE("Female"), MALE("Male");

		private String label;

		private Gender(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}
	}

