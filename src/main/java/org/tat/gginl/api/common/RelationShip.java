package org.tat.gginl.api.common;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import com.ace.demoapi.common.UserRecorder;


import lombok.Data;
@Data
@Entity
public class RelationShip {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "RELATIONSHIP_GEN")
	private String id;
	private String name;
	private String description;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	

}
