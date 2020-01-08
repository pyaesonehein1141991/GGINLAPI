package org.tat.gginl.api.common;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Embeddable
public class OfficeAddress implements Serializable {

	private static final long serialVersionUID = 1L;

	private String officeAddress;

	@OneToOne
	@JoinColumn(name = "OFFICETOWNSHIPID", referencedColumnName = "ID")
	private Township township;

}
