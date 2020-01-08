package org.tat.gginl.api.common;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import com.ace.demoapi.common.UserRecorder;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Township implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TOWNSHIP_GEN")
	private String id;

	private String name;
	private String shortName;
	private String code;
	private String description;

	@OneToOne
	@JoinColumn(name = "DISTRICTID", referencedColumnName = "ID")
	private District district;

	@JsonIgnore
	@Embedded
	private UserRecorder recorder;

	@JsonIgnore
	@Version
	private int version;

	public String getFullTownShip() {
		String fullAddress = name;
		if (district != null && !district.getFullDistrict().isEmpty()) {
			fullAddress = name + ", " + district.getFullDistrict();
		}
		return fullAddress;
	}

}
