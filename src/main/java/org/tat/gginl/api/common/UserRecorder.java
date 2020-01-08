package org.tat.gginl.api.common;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Embeddable
public class UserRecorder implements Serializable {

	private static final long serialVersionUID = 1L;
	private String createdUserId;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	private String updatedUserId;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

}
