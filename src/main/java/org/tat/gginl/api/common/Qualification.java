package org.tat.gginl.api.common;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import lombok.Data;

@Data
@Entity
public class Qualification implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "QUALIFICATION_GEN")
	private String id;

	private String name;
	private String description;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

}
