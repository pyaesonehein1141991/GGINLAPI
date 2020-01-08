package org.tat.gginl.api.common;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.ace.demoapi.modal.Industry;
import com.ace.demoapi.modal.Occupation;
import com.ace.demoapi.modal.RelationShip;

import lombok.Data;

@Data
@Embeddable
public class FamilyInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String initialId;
	private String idNo;
	@Transient
	private String stateCode;
	@Transient
	private String townshipCode;
	@Transient
	private String idConditionType;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATEOFBIRTH")
	private Date dateOfBirth;

	@Embedded
	private Name name;

	@Enumerated(value = EnumType.STRING)
	private IdType idType;

	@OneToOne
	@JoinColumn(name = "RELATIONSHIPID", referencedColumnName = "ID")
	private RelationShip relationShip;

	@OneToOne
	@JoinColumn(name = "INDUSTRYID", referencedColumnName = "ID")
	private Industry industry;

	@OneToOne
	@JoinColumn(name = "OCCUPATIONID", referencedColumnName = "ID")
	private Occupation occupation;
	@Transient
	private String tempId;

	public FamilyInfo() {
		tempId = System.nanoTime() + "";
	}

	public Name getName() {
		if (this.name == null) {
			this.name = new Name();
		}
		return this.name;
	}

	public RelationShip getRelationShip() {
		if (relationShip == null) {
			return new RelationShip();
		} else {
			return this.relationShip;
		}
	}

}
