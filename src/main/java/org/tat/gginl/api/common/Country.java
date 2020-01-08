package org.tat.gginl.api.common;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import com.ace.demoapi.common.UserRecorder;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Country implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "COUNTRY_GEN")
	private String id;
	private String name;
	private String description;
	private String code;

	@JsonIgnore
	@Embedded
	private UserRecorder recorder;

	@JsonIgnore
	@Version
	private int version;

}
