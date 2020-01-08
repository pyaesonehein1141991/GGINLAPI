package org.tat.gginl.api.common;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Embeddable
public class Name {
	private String firstName;
	private String middleName;
	private String lastName;

	@Transient
	public String getFullName() {
		String result = "";
		if (firstName != null && !firstName.isEmpty()) {
			result = result + " " + firstName;
		}
		if (middleName != null && !middleName.isEmpty()) {
			result = result + " " + middleName;
		}
		if (lastName != null && !lastName.isEmpty()) {
			result = result + " " + lastName;
		}
		return result;
	}
	

}
