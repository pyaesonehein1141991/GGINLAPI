/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.tat.gginl.api.domains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.tat.gginl.api.common.ContentInfo;
import org.tat.gginl.api.common.FormatID;
import org.tat.gginl.api.common.Gender;
import org.tat.gginl.api.common.IdConditionType;
import org.tat.gginl.api.common.IdType;
import org.tat.gginl.api.common.MaritalStatus;
import org.tat.gginl.api.common.Name;
import org.tat.gginl.api.common.PermanentAddress;
import org.tat.gginl.api.common.ProductGroupType;
import org.tat.gginl.api.common.ResidentAddress;

import lombok.Data;


@Entity
@Data
public class Agent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	private String id;
	@Transient
	private String prefix;
	private String codeNo;
	private String liscenseNo;
	private String initialId;
	private String idNo;
	private String fatherName;
	private String birthMark;
	private String accountNo;
	private String remark;
	private String training;
	private String outstandingEvent;
	@Embedded
	private Name name;

	@Enumerated(value = EnumType.STRING)
	private Gender gender;

	@Enumerated(value = EnumType.STRING)
	private MaritalStatus maritalStatus;

	@Temporal(TemporalType.DATE)
	private Date appointedDate;

	@ElementCollection
	@CollectionTable(name = "AGENT_FAMILY_LINK", joinColumns = @JoinColumn(name = "AGENTID", referencedColumnName = "ID"))
	private List<FamilyInfo> familyInfo;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QUALIFICATIONID", referencedColumnName = "ID")
	private Qualification qualification;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANKBRANCHID", referencedColumnName = "ID")
	private BankBranch bankBranch;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RELIGIONID", referencedColumnName = "ID")
	private Religion religion;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NATIONALITYID", referencedColumnName = "ID")
	private Country country;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORGANIZATIONID", referencedColumnName = "ID")
	private Organization organization;

	@Embedded
	private PermanentAddress permanentAddress;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "agent", orphanRemoval = true)
	private AgentAttachment attachment;

	@Embedded
	private ResidentAddress residentAddress;

	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	@Enumerated(value = EnumType.STRING)
	private IdType idType;

	@Column(name = "GROUPTYPE")
	@Enumerated(value = EnumType.STRING)
	private ProductGroupType groupType;

	@Embedded
	private ContentInfo contentInfo;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCHID", referencedColumnName = "ID")
	private Branch branch;

	@Version
	private int version;

	@Transient
	private StateCode stateCode;

	@Transient
	private TownshipCode townshipCode;

	@Transient
	private IdConditionType idConditionType;

	@Transient
	private String fullIdNo;

	public Agent() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AGENT_GEN")
	@Access(value = AccessType.PROPERTY)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (id != null) {
			this.id = FormatID.formatId(id, getPrefix(), 10);
		}
	}

	public String getCodeNo() {
		return this.codeNo;
	}

	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

	public String getLiscenseNo() {
		return this.liscenseNo;
	}

	public void setLiscenseNo(String liscenseNo) {
		this.liscenseNo = liscenseNo;
	}

	public ContentInfo getContentInfo() {
		if (this.contentInfo == null) {
			this.contentInfo = new ContentInfo();
		}
		return this.contentInfo;
	}

	public void setContentInfo(ContentInfo contentInfo) {
		this.contentInfo = contentInfo;
	}

	public Branch getBranch() {
		return this.branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getInitialId() {
		return initialId;
	}

	public void setInitialId(String initialId) {
		this.initialId = initialId;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public IdType getIdType() {
		return idType;
	}

	public void setIdType(IdType idType) {
		this.idType = idType;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getFullName() {
		return initialId + name.getFullName();
	}

	public String getFullAddress() {
		String result = "";
		if (residentAddress.getResidentAddress() != null && !residentAddress.getResidentAddress().isEmpty()) {
			result = result + residentAddress.getResidentAddress();
		}
		if (residentAddress.getTownship() != null && !residentAddress.getTownship().getFullTownShip().isEmpty()) {
			result = result + ", " + residentAddress.getTownship().getFullTownShip();
		}
		return result;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getBirthMark() {
		return birthMark;
	}

	public void setBirthMark(String birthMark) {
		this.birthMark = birthMark;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTraining() {
		return training;
	}

	public void setTraining(String training) {
		this.training = training;
	}

	public String getOutstandingEvent() {
		return outstandingEvent;
	}

	public void setOutstandingEvent(String outstandingEvent) {
		this.outstandingEvent = outstandingEvent;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Date getAppointedDate() {
		return appointedDate;
	}

	public void setAppointedDate(Date appointedDate) {
		this.appointedDate = appointedDate;
	}

	public List<FamilyInfo> getFamilyInfo() {
		if (this.familyInfo == null) {
			this.familyInfo = new ArrayList<FamilyInfo>();
		}
		return this.familyInfo;
	}

	public void setFamilyInfo(List<FamilyInfo> familyInfo) {
		this.familyInfo = familyInfo;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public BankBranch getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(BankBranch bankBranch) {
		this.bankBranch = bankBranch;
	}

	public Religion getReligion() {
		return religion;
	}

	public void setReligion(Religion religion) {
		this.religion = religion;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public ProductGroupType getGroupType() {
		return groupType;
	}

	public void setGroupType(ProductGroupType groupType) {
		this.groupType = groupType;
	}

	public AgentAttachment getAttachment() {
		return attachment;
	}

	public void setAttachment(AgentAttachment attachment) {
		this.attachment = attachment;
		attachment.setAgent(this);
	}

	public PermanentAddress getPermanentAddress() {
		if (this.permanentAddress == null) {
			this.permanentAddress = new PermanentAddress();
		}
		return permanentAddress;
	}

	public void setPermanentAddress(PermanentAddress permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public ResidentAddress getResidentAddress() {
		if (this.residentAddress == null) {
			this.residentAddress = new ResidentAddress();
		}
		return this.residentAddress;
	}

	public void setResidentAddress(ResidentAddress residentAddress) {
		this.residentAddress = residentAddress;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public StateCode getStateCode() {
		return stateCode;
	}

	public void setStateCode(StateCode stateCode) {
		this.stateCode = stateCode;
	}

	public TownshipCode getTownshipCode() {
		return townshipCode;
	}

	public void setTownshipCode(TownshipCode townshipCode) {
		this.townshipCode = townshipCode;
	}

	public IdConditionType getIdConditionType() {
		return idConditionType;
	}

	public void setIdConditionType(IdConditionType idConditionType) {
		this.idConditionType = idConditionType;
	}

	public String getFullIdNo() {
		return fullIdNo;
	}

	public void setFullIdNo(String fullIdNo) {
		this.fullIdNo = fullIdNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNo == null) ? 0 : accountNo.hashCode());
		result = prime * result + ((appointedDate == null) ? 0 : appointedDate.hashCode());
		result = prime * result + ((birthMark == null) ? 0 : birthMark.hashCode());
		result = prime * result + ((codeNo == null) ? 0 : codeNo.hashCode());
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((fatherName == null) ? 0 : fatherName.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((groupType == null) ? 0 : groupType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idNo == null) ? 0 : idNo.hashCode());
		result = prime * result + ((idType == null) ? 0 : idType.hashCode());
		result = prime * result + ((initialId == null) ? 0 : initialId.hashCode());
		result = prime * result + ((liscenseNo == null) ? 0 : liscenseNo.hashCode());
		result = prime * result + ((maritalStatus == null) ? 0 : maritalStatus.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((outstandingEvent == null) ? 0 : outstandingEvent.hashCode());
		result = prime * result + ((permanentAddress == null) ? 0 : permanentAddress.hashCode());
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((residentAddress == null) ? 0 : residentAddress.hashCode());
		result = prime * result + ((training == null) ? 0 : training.hashCode());
		result = prime * result + version;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agent other = (Agent) obj;
		if (accountNo == null) {
			if (other.accountNo != null)
				return false;
		} else if (!accountNo.equals(other.accountNo))
			return false;
		if (appointedDate == null) {
			if (other.appointedDate != null)
				return false;
		} else if (!appointedDate.equals(other.appointedDate))
			return false;
		if (birthMark == null) {
			if (other.birthMark != null)
				return false;
		} else if (!birthMark.equals(other.birthMark))
			return false;
		if (codeNo == null) {
			if (other.codeNo != null)
				return false;
		} else if (!codeNo.equals(other.codeNo))
			return false;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (fatherName == null) {
			if (other.fatherName != null)
				return false;
		} else if (!fatherName.equals(other.fatherName))
			return false;
		if (gender != other.gender)
			return false;
		if (groupType != other.groupType)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idNo == null) {
			if (other.idNo != null)
				return false;
		} else if (!idNo.equals(other.idNo))
			return false;
		if (idType != other.idType)
			return false;
		if (initialId == null) {
			if (other.initialId != null)
				return false;
		} else if (!initialId.equals(other.initialId))
			return false;
		if (liscenseNo == null) {
			if (other.liscenseNo != null)
				return false;
		} else if (!liscenseNo.equals(other.liscenseNo))
			return false;
		if (maritalStatus != other.maritalStatus)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (outstandingEvent == null) {
			if (other.outstandingEvent != null)
				return false;
		} else if (!outstandingEvent.equals(other.outstandingEvent))
			return false;
		if (permanentAddress == null) {
			if (other.permanentAddress != null)
				return false;
		} else if (!permanentAddress.equals(other.permanentAddress))
			return false;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (residentAddress == null) {
			if (other.residentAddress != null)
				return false;
		} else if (!residentAddress.equals(other.residentAddress))
			return false;
		if (training == null) {
			if (other.training != null)
				return false;
		} else if (!training.equals(other.training))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}