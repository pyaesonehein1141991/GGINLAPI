package org.tat.gginl.api.common;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

import com.ace.demoapi.common.UserRecorder;

import lombok.Data;

@Data
@Entity
public class Industry implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String name;
	private String description;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

}
