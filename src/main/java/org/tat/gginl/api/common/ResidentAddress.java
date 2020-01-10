package org.tat.gginl.api.common;

	import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.tat.gginl.api.domains.Township;




	@Embeddable
	public class ResidentAddress implements Serializable {
		private static final long serialVersionUID = -2074848703209463245L;
		private String residentAddress;
		@OneToOne
		@JoinColumn(name = "RESIDENTTOWNSHIPID", referencedColumnName = "ID")
		private Township township;

		public ResidentAddress() {
		}

		public ResidentAddress(ResidentAddress residentAddress) {
			this.residentAddress = residentAddress.getResidentAddress();
			this.township = residentAddress.getTownship();
		}

		public String getResidentAddress() {
			return residentAddress;
		}

		public void setResidentAddress(String residentAddress) {
			this.residentAddress = residentAddress;
		}

		public Township getTownship() {
			return this.township;
		}

		public void setTownship(Township township) {
			this.township = township;
		}

		public String getFullResidentAddress() {
			if (residentAddress == null || township == null) {
				return "";
			}

			return residentAddress + ", " + township.getFullTownShip();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((residentAddress == null) ? 0 : residentAddress.hashCode());
			result = prime * result + ((township == null) ? 0 : township.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ResidentAddress other = (ResidentAddress) obj;
			if (residentAddress == null) {
				if (other.residentAddress != null)
					return false;
			} else if (!residentAddress.equals(other.residentAddress))
				return false;
			if (township == null) {
				if (other.township != null)
					return false;
			} else if (!township.equals(other.township))
				return false;
			return true;
		}

	

}
