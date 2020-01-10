package org.tat.gginl.api.common;


	import java.io.Serializable;

	import javax.xml.bind.annotation.XmlAccessType;
	import javax.xml.bind.annotation.XmlAccessorType;
	import javax.xml.bind.annotation.XmlElement;
	import javax.xml.bind.annotation.XmlRootElement;


	public class PermanentAddressDto implements Serializable {
	
		private static final long serialVersionUID = 1L;

		private String permanentAddress;

		private String townshipId;

		public PermanentAddressDto() {
		}

		public String getPermanentAddress() {
			return permanentAddress;
		}

		public void setPermanentAddress(String permanentAddress) {
			this.permanentAddress = permanentAddress;
		}

		public String getTownshipId() {
			return townshipId;
		}

		public void setTownshipId(String townshipId) {
			this.townshipId = townshipId;
		}

		public void getFullTownShip() {

		}


}
