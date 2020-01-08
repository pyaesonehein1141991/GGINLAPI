package org.tat.gginl.api.common;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ace.demoapi.common.ContentInfo;
import com.ace.demoapi.common.PermanentAddress;
import com.ace.demoapi.common.UserRecorder;

import lombok.Data;

@Data
@Entity
public class Organization implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	@Column(name = "CODE_NO")
	private String codeNo;
	@Column(name = "NAME")
	private String name;
	@Column(name = "REG_NO")
	private String regNo;
	@Column(name = "OWNER_NAME")
	private String OwnerName;
	private int activePolicy;
	@Temporal(TemporalType.TIMESTAMP)
	private Date activedDate;

	@Embedded
	@AttributeOverride(name = "permanentAddress", column = @Column(name = "ADDRESS"))
	@AssociationOverride(name = "township", joinColumns = @JoinColumn(name = "TOWNSHIP_ID"))
	private PermanentAddress address;

	@Embedded
	private ContentInfo contentInfo;

	@OneToOne
	@JoinColumn(name = "BRANCHID", referencedColumnName = "ID")
	private Branch branch;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public String getFullAddress() {
		String fullAddress = "";
		if (address != null) {
			String townShip = address.getTownship() == null ? "" : address.getTownship().getFullTownShip();
			fullAddress = address.getPermanentAddress() + ", " + townShip;
		}
		return fullAddress;
	}
	

	public String getOwnerNameForView() {
		if (OwnerName == null || OwnerName.isEmpty())
			return "-";
		return OwnerName;
	}

}