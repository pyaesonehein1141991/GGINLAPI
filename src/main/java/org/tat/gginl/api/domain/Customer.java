package org.tat.gginl.api.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.tat.gginl.api.common.BankBranch;
import org.tat.gginl.api.common.Branch;
import org.tat.gginl.api.common.ContentInfo;
import org.tat.gginl.api.common.Country;
import org.tat.gginl.api.common.CustomerInfoStatus;
import org.tat.gginl.api.common.FamilyInfo;
import org.tat.gginl.api.common.IdType;
import org.tat.gginl.api.common.Industry;
import org.tat.gginl.api.common.Name;
import org.tat.gginl.api.common.Occupation;
import org.tat.gginl.api.common.OfficeAddress;
import org.tat.gginl.api.common.PermanentAddress;
import org.tat.gginl.api.common.Qualification;
import org.tat.gginl.api.common.Religion;
import org.tat.gginl.api.common.ResidentAddress;
import org.tat.gginl.api.common.UserRecorder;
import org.tat.gginl.api.common.enumdata.Gender;
import org.tat.gginl.api.common.enumdata.MaritalStatus;
import org.tat.gginl.api.common.enumdata.PassportType;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String initialId;
	private String fatherName;
	@Transient
	private String stateCode;
	@Transient
	private String townshipCode;
	@Transient
	private String idConditionType;
	@Transient
	private String idNo;
	private String fullIdNo;
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	@Temporal(TemporalType.TIMESTAMP)
	private Date activedDate;
	private String labourNo;
	private String birthMark;
	private String salary;
	private int closedPolicy;
	private int activePolicy;
	private String bankAccountNo;

	private double height;
	private double weight;
	private String placeOfBirth;

	@Enumerated(value = EnumType.STRING)
	private Gender gender;

	@Enumerated(value = EnumType.STRING)
	private IdType idType;

	@Enumerated(value = EnumType.STRING)
	private MaritalStatus maritalStatus;

	@Enumerated(value = EnumType.STRING)
	private PassportType passportType;

	@Embedded
	private OfficeAddress officeAddress;

	@Embedded
	private PermanentAddress permanentAddress;

	@Embedded
	private ResidentAddress residentAddress;

	@Embedded
	private ContentInfo contentInfo;

	@Embedded
	private Name name;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "CUSTOMER_FAMILY_LINK", joinColumns = @JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID"))
	private List<FamilyInfo> familyInfo;

	@OneToOne
	@JoinColumn(name = "BRANCHID", referencedColumnName = "ID")
	private Branch branch;

	@OneToOne
	@JoinColumn(name = "QUALIFICATIONID", referencedColumnName = "ID")
	private Qualification qualification;

	@OneToOne
	@JoinColumn(name = "BANKBRANCHID", referencedColumnName = "ID")
	private BankBranch bankBranch;

	@OneToOne
	@JoinColumn(name = "RELIGIONID", referencedColumnName = "ID")
	private Religion religion;

	@OneToOne
	@JoinColumn(name = "OCCUPATIONID", referencedColumnName = "ID")
	private Occupation occupation;

	@OneToOne
	@JoinColumn(name = "INDURSTRYID", referencedColumnName = "ID")
	private Industry industry;

	@OneToOne
	@JoinColumn(name = "NATIONALITYID", referencedColumnName = "ID")
	private Country country;


	@JsonBackReference
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
	@JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<CustomerInfoStatus> customerStatusList;

	@Embedded
	private UserRecorder recorder;
	
//	private boolean isExisting;

	@Version
	private int version;

	public Customer(String id) {
		this.id = id;
	}
	
	public String getFullName() {
		String result = "";
		if (name != null) {
			if (initialId != null && !initialId.isEmpty()) {
				result = initialId;
			}
			if (name.getFirstName() != null && !name.getFirstName().isEmpty()) {
				result = result.trim() + " " + name.getFirstName();
			}
			if (name.getMiddleName() != null && !name.getMiddleName().isEmpty()) {
				result = result.trim() + " " + name.getMiddleName();
			}
			if (name.getLastName() != null && !name.getLastName().isEmpty()) {
				result = result.trim() + " " + name.getLastName();
			}
		}
		return result;
	}
	
	public String getFullAddress() {
		String result = "";
		if (residentAddress != null) {
			if (residentAddress.getResidentAddress() != null && !residentAddress.getResidentAddress().isEmpty()) {
				result = result + residentAddress.getResidentAddress();
			}
			if (residentAddress.getTownship() != null && !residentAddress.getTownship().getFullTownShip().isEmpty()) {
				result = result + ", " + residentAddress.getTownship().getFullTownShip();
			}
		}
		return result;
	}
	public String getFullIdNoForView() {
		if (fullIdNo == null || fullIdNo.isEmpty())
			return "Still Applying";
		return fullIdNo;
	}

}
