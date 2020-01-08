package org.tat.gginl.api.common;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class District {

	@Id
	private String id;
	private String name;
	private String code;
	private String description;

	@OneToOne
	@JoinColumn(name = "PROVINCEID", referencedColumnName = "ID")
	private Province province;

	@JsonIgnore
	@Embedded
	private UserRecorder recorder;

	@JsonIgnore
	@Version
	private int version;

	public String getFullDistrict() {
		return name + "," + province.getName();
	}

}
