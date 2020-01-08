package org.tat.gginl.api.common;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Embeddable
public class ResidentAddress implements Serializable {

	private static final long serialVersionUID = 1L;

	private String residentAddress;
	@OneToOne
	@JoinColumn(name = "RESIDENTTOWNSHIPID", referencedColumnName = "ID")
	private Township township;

	public ResidentAddress() {
		township = new Township();
	}

	public String getFullResidentAddress() {
		if (residentAddress == null || township == null) {
			return "";
		}

		return residentAddress + ", " + township.getFullTownShip();
	}
	
}
