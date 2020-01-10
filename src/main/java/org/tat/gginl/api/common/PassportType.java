package org.tat.gginl.api.common;

	public enum PassportType {
		WORKING("Working"), VISITING("Visiting"), OTHERS("Others");

		private String label;

		private PassportType(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}
	}


