package org.tat.gginl.api.common;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class ContentInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String phone;
	private String fax;
	private String mobile;
	private String email;

	public String getPhoneOrMoblieNo() {
		if (phone == null && mobile == null) {
			return "-";
		} else {
			if (phone.isEmpty() || phone == "") {
				if (mobile != null && !mobile.isEmpty()) {
					return mobile;
				}
				return "-";
			} else
				return phone;
		}
	}

	

}
