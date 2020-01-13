package org.tat.gginl.api.domains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.tat.gginl.api.common.CommonCreateAndUpateMarks;
import org.tat.gginl.api.common.ContentInfo;
import org.tat.gginl.api.common.CustomerDto;
import org.tat.gginl.api.common.FormatID;
import org.tat.gginl.api.common.Gender;
import org.tat.gginl.api.common.IdConditionType;
import org.tat.gginl.api.common.IdType;
import org.tat.gginl.api.common.MaritalStatus;
import org.tat.gginl.api.common.Name;
import org.tat.gginl.api.common.OfficeAddress;
import org.tat.gginl.api.common.PassportType;
import org.tat.gginl.api.common.PermanentAddress;
import org.tat.gginl.api.common.ResidentAddress;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Data
@TableGenerator(name = "CUSTOMER_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "CUSTOMER_GEN", allocationSize = 10)
@Access(value = AccessType.FIELD)
public class Customer implements Serializable {
	private static final long serialVersionUID = -6982490830051621004L;
	@Transient
	private String id;
	
	@Transient
	private String prefix;
	
	private String initialId;
	private String fatherName;
	private String idNo;
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
	@Transient
	private String fullIdNo;
	private double height;
	private double weight;
	private String placeOfBirth;
	private String referenceMobileUserId;

	@Enumerated(value = EnumType.STRING)
	private Gender gender;

	@Enumerated(value = EnumType.STRING)
	private IdConditionType idConditionType;

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

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "CUSTOMER_FAMILY_LINK", joinColumns = @JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID"))
	private List<FamilyInfo> familyInfo;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCHID", referencedColumnName = "ID")
	private Branch branch;

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
	@JoinColumn(name = "OCCUPATIONID", referencedColumnName = "ID")
	private Occupation occupation;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INDURSTRYID", referencedColumnName = "ID")
	private Industry industry;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NATIONALITYID", referencedColumnName = "ID")
	private Country country;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STATECODEID", referencedColumnName = "ID")
	private StateCode stateCode;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TOWNSHIPCODEID", referencedColumnName = "ID")
	private TownshipCode townshipCode;

	
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID")
	private List<CustomerInfoStatus> customerStatusList;

	@Embedded
	private CommonCreateAndUpateMarks recorder;

	@Version
	private int version;

	public Customer() {
	}

	public Customer(CustomerDto customerDto) {
		this.prefix = customerDto.getPrefix();
		this.initialId = customerDto.getInitialId();
		this.fatherName = customerDto.getFatherName();
		this.idNo = customerDto.getIdNo();
		this.dateOfBirth = customerDto.getDateOfBirth();
		this.labourNo = customerDto.getLabourNo();
		this.birthMark = customerDto.getBirthMark();
		this.salary = customerDto.getSalary();
		this.closedPolicy = customerDto.getClosedPolicy();
		this.activePolicy = customerDto.getActivePolicy();
		this.bankAccountNo = customerDto.getBankAccountNo();
		this.gender = customerDto.getGender();
		this.idType = customerDto.getIdType();
		this.maritalStatus = customerDto.getMaritalStatus();
		this.contentInfo = customerDto.getContentInfo() != null ? new ContentInfo(customerDto.getContentInfo()) : null;
		this.name = customerDto.getName() != null ? new Name(customerDto.getName()) : null;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "CUSTOMER_GEN")
	@Access(value = AccessType.PROPERTY)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (id != null) {
			this.id = FormatID.formatId(id, getPrefix(), 10);
		}
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getInitialId() {
		if (initialId == null) {
			initialId = "";
		}
		return initialId;
	}

	public void setInitialId(String initialId) {
		this.initialId = initialId;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getBirthMark() {
		return birthMark;
	}

	public String getSalary() {
		return salary;
	}

	public void setBirthMark(String birthMark) {
		this.birthMark = birthMark;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public int getClosedPolicy() {
		return closedPolicy;
	}

	public void setClosedPolicy(int closedPolicy) {
		this.closedPolicy = closedPolicy;
	}

	public int getActivePolicy() {
		return activePolicy;
	}

	public void setActivePolicy(int activePolicy) {
		this.activePolicy = activePolicy;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public Gender getGender() {
		return this.gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public IdType getIdType() {
		return idType;
	}

	public void setIdType(IdType idType) {
		this.idType = idType;
	}

	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public PassportType getPassportType() {
		return passportType;
	}

	public void setPassportType(PassportType passportType) {
		this.passportType = passportType;
	}

	public OfficeAddress getOfficeAddress() {
		if (this.officeAddress == null) {
			this.officeAddress = new OfficeAddress();
		}
		return this.officeAddress;
	}

	public void setOfficeAddress(OfficeAddress officeAddress) {
		this.officeAddress = officeAddress;
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

	public ContentInfo getContentInfo() {
		if (this.contentInfo == null) {
			this.contentInfo = new ContentInfo();
		}
		return this.contentInfo;
	}

	public void setContentInfo(ContentInfo contentInfo) {
		this.contentInfo = contentInfo;
	}

	public Name getName() {
		if (this.name == null) {
			this.name = new Name();
		}
		return this.name;
	}

	public void setName(Name name) {
		this.name = name;
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

	public Branch getBranch() {
		return this.branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Qualification getQualification() {
		return this.qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public BankBranch getBankBranch() {
		return this.bankBranch;
	}

	public void setBankBranch(BankBranch bankBranch) {
		this.bankBranch = bankBranch;
	}

	public Religion getReligion() {
		return this.religion;
	}

	public void setReligion(Religion religion) {
		this.religion = religion;
	}

	public Occupation getOccupation() {
		return this.occupation;
	}

	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}

	public Industry getIndustry() {
		return this.industry;
	}

	public void setIndustry(Industry industry) {
		this.industry = industry;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Date getActivedDate() {
		return activedDate;
	}

	public void setActivedDate(Date activedDate) {
		this.activedDate = activedDate;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getLabourNo() {
		return labourNo;
	}

	public void setLabourNo(String labourNo) {
		this.labourNo = labourNo;
	}

	public void setFullIdNo(String fullIdNo) {
		this.fullIdNo = fullIdNo;
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

	public List<CustomerInfoStatus> getCustomerStatusList() {
		if (customerStatusList == null) {
			customerStatusList = new ArrayList<CustomerInfoStatus>();
		}
		return customerStatusList;
	}

	public void setCustomerStatusList(List<CustomerInfoStatus> customerStatusList) {
		this.customerStatusList = customerStatusList;
	}

	public void addCustomerInfoStatus(CustomerInfoStatus customerInfoStatus) {
		if (customerStatusList == null) {
			customerStatusList = new ArrayList<CustomerInfoStatus>();
		}
		if (!customerStatusList.contains(customerInfoStatus)) {
			customerStatusList.add(customerInfoStatus);
		}
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	

	public String getReferenceMobileUserId() {
		return referenceMobileUserId;
	}

	public void setReferenceMobileUserId(String referenceMobileUserId) {
		this.referenceMobileUserId = referenceMobileUserId;
	}

	public int getAgeForNextYear() {
		Calendar cal_1 = Calendar.getInstance();
		int currentYear = cal_1.get(Calendar.YEAR);
		Calendar cal_2 = Calendar.getInstance();
		cal_2.setTime(dateOfBirth);
		cal_2.set(Calendar.YEAR, currentYear);
		if (new Date().after(cal_2.getTime())) {
			Calendar cal_3 = Calendar.getInstance();
			cal_3.setTime(dateOfBirth);
			int year_1 = cal_3.get(Calendar.YEAR);
			int year_2 = cal_1.get(Calendar.YEAR) + 1;
			return year_2 - year_1;
		} else {
			Calendar cal_3 = Calendar.getInstance();
			cal_3.setTime(dateOfBirth);
			int year_1 = cal_3.get(Calendar.YEAR);
			int year_2 = cal_1.get(Calendar.YEAR);
			return year_2 - year_1;
		}
	}

	
	@Transient
	public String getFullName() {
		String result = "";
		if (name != null) {
			if (initialId != null && !initialId.isEmpty()) {
				result = initialId + " ";
			}
			if (name.getFirstName() != null && !name.getFirstName().isEmpty()) {
				result = result + name.getFirstName();
			}
			if (name.getMiddleName() != null && !name.getMiddleName().isEmpty()) {
				result = result + " " + name.getMiddleName();
			}
			if (name.getLastName() != null && !name.getLastName().isEmpty()) {
				result = result + " " + name.getLastName();
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
			if (residentAddress.getResidentTownship() != null && !residentAddress.getResidentTownship().getFullTownShip().isEmpty()) {
				result = result + ", " + residentAddress.getResidentTownship().getFullTownShip();
			}
		}
		return result;
	}

	public String getFullOfficeAddress() {
		String result = "";
		if (officeAddress != null) {
			if (officeAddress.getOfficeAddress() != null && !officeAddress.getOfficeAddress().isEmpty()) {
				result = result + officeAddress.getOfficeAddress();
			}
			if (officeAddress.getTownship() != null && officeAddress.getTownship().getProvince() != null) {
				result = result + (result.isEmpty() ? "" : ", ") + officeAddress.getTownship().getFullTownShip();
			}
		}
		return result;
	}

	@Access(value = AccessType.PROPERTY)
	public String getFullIdNo() {
		String result = "";
		if (!IdType.STILL_APPLYING.equals(idType)) {
			if (stateCode != null) {
				result = result + stateCode.getCodeNo() + "/";
			}
			if (townshipCode != null) {
				result = result + townshipCode.getTownshipcodeno();
			}
			if (idConditionType != null) {
				result = result + "(" + idConditionType.getLabel() + ")";
			}
			if (result.isEmpty() && idNo != null) {
				return idNo;
			} else {
				return result + idNo;
			}
		} else
			return null;
	}

	public String setFullIdNo() {
		if (idType.equals(IdType.NRCNO)) {
			fullIdNo = stateCode.getCodeNo() + "/" + townshipCode.getStateCode() + "(" + idConditionType + ")" + idNo;
		} else if (idType.equals(IdType.FRCNO) || idType.equals(IdType.PASSPORTNO)) {
			fullIdNo = idNo;
		}
		return fullIdNo;
	}

	public String getPhoneAndEmail() {
		if (contentInfo != null) {
			return contentInfo.getPhoneOrMoblieNo() + contentInfo.getEmail();
		}
		return "-";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + activePolicy;
		result = prime * result + ((activedDate == null) ? 0 : activedDate.hashCode());
		result = prime * result + ((bankAccountNo == null) ? 0 : bankAccountNo.hashCode());
		result = prime * result + ((bankBranch == null) ? 0 : bankBranch.hashCode());
		result = prime * result + ((birthMark == null) ? 0 : birthMark.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + closedPolicy;
		result = prime * result + ((contentInfo == null) ? 0 : contentInfo.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((customerStatusList == null) ? 0 : customerStatusList.hashCode());
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((familyInfo == null) ? 0 : familyInfo.hashCode());
		result = prime * result + ((fatherName == null) ? 0 : fatherName.hashCode());
		result = prime * result + ((fullIdNo == null) ? 0 : fullIdNo.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		long temp;
		temp = Double.doubleToLongBits(height);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idConditionType == null) ? 0 : idConditionType.hashCode());
		result = prime * result + ((idNo == null) ? 0 : idNo.hashCode());
		result = prime * result + ((idType == null) ? 0 : idType.hashCode());
		result = prime * result + ((industry == null) ? 0 : industry.hashCode());
		result = prime * result + ((initialId == null) ? 0 : initialId.hashCode());
		result = prime * result + ((labourNo == null) ? 0 : labourNo.hashCode());
		result = prime * result + ((maritalStatus == null) ? 0 : maritalStatus.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((occupation == null) ? 0 : occupation.hashCode());
		result = prime * result + ((officeAddress == null) ? 0 : officeAddress.hashCode());
		result = prime * result + ((passportType == null) ? 0 : passportType.hashCode());
		result = prime * result + ((permanentAddress == null) ? 0 : permanentAddress.hashCode());
		result = prime * result + ((placeOfBirth == null) ? 0 : placeOfBirth.hashCode());
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		result = prime * result + ((qualification == null) ? 0 : qualification.hashCode());
		result = prime * result + ((referenceMobileUserId == null) ? 0 : referenceMobileUserId.hashCode());
		result = prime * result + ((religion == null) ? 0 : religion.hashCode());
		result = prime * result + ((residentAddress == null) ? 0 : residentAddress.hashCode());
		result = prime * result + ((salary == null) ? 0 : salary.hashCode());
		result = prime * result + ((stateCode == null) ? 0 : stateCode.hashCode());
		result = prime * result + ((townshipCode == null) ? 0 : townshipCode.hashCode());
		result = prime * result + version;
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Customer other = (Customer) obj;
		if (activePolicy != other.activePolicy)
			return false;
		if (activedDate == null) {
			if (other.activedDate != null)
				return false;
		} else if (!activedDate.equals(other.activedDate))
			return false;
		if (bankAccountNo == null) {
			if (other.bankAccountNo != null)
				return false;
		} else if (!bankAccountNo.equals(other.bankAccountNo))
			return false;
		if (bankBranch == null) {
			if (other.bankBranch != null)
				return false;
		} else if (!bankBranch.equals(other.bankBranch))
			return false;
		if (birthMark == null) {
			if (other.birthMark != null)
				return false;
		} else if (!birthMark.equals(other.birthMark))
			return false;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (closedPolicy != other.closedPolicy)
			return false;
		if (contentInfo == null) {
			if (other.contentInfo != null)
				return false;
		} else if (!contentInfo.equals(other.contentInfo))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (customerStatusList == null) {
			if (other.customerStatusList != null)
				return false;
		} else if (!customerStatusList.equals(other.customerStatusList))
			return false;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (familyInfo == null) {
			if (other.familyInfo != null)
				return false;
		} else if (!familyInfo.equals(other.familyInfo))
			return false;
		if (fatherName == null) {
			if (other.fatherName != null)
				return false;
		} else if (!fatherName.equals(other.fatherName))
			return false;
		if (fullIdNo == null) {
			if (other.fullIdNo != null)
				return false;
		} else if (!fullIdNo.equals(other.fullIdNo))
			return false;
		if (gender != other.gender)
			return false;
		if (Double.doubleToLongBits(height) != Double.doubleToLongBits(other.height))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idConditionType != other.idConditionType)
			return false;
		if (idNo == null) {
			if (other.idNo != null)
				return false;
		} else if (!idNo.equals(other.idNo))
			return false;
		if (idType != other.idType)
			return false;
		if (industry == null) {
			if (other.industry != null)
				return false;
		} else if (!industry.equals(other.industry))
			return false;
		if (initialId == null) {
			if (other.initialId != null)
				return false;
		} else if (!initialId.equals(other.initialId))
			return false;
		if (labourNo == null) {
			if (other.labourNo != null)
				return false;
		} else if (!labourNo.equals(other.labourNo))
			return false;
		if (maritalStatus != other.maritalStatus)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (occupation == null) {
			if (other.occupation != null)
				return false;
		} else if (!occupation.equals(other.occupation))
			return false;
		if (officeAddress == null) {
			if (other.officeAddress != null)
				return false;
		} else if (!officeAddress.equals(other.officeAddress))
			return false;
		if (passportType != other.passportType)
			return false;
		if (permanentAddress == null) {
			if (other.permanentAddress != null)
				return false;
		} else if (!permanentAddress.equals(other.permanentAddress))
			return false;
		if (placeOfBirth == null) {
			if (other.placeOfBirth != null)
				return false;
		} else if (!placeOfBirth.equals(other.placeOfBirth))
			return false;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
			return false;
		if (qualification == null) {
			if (other.qualification != null)
				return false;
		} else if (!qualification.equals(other.qualification))
			return false;
		if (referenceMobileUserId == null) {
			if (other.referenceMobileUserId != null)
				return false;
		} else if (!referenceMobileUserId.equals(other.referenceMobileUserId))
			return false;
		if (religion == null) {
			if (other.religion != null)
				return false;
		} else if (!religion.equals(other.religion))
			return false;
		if (residentAddress == null) {
			if (other.residentAddress != null)
				return false;
		} else if (!residentAddress.equals(other.residentAddress))
			return false;
		if (salary == null) {
			if (other.salary != null)
				return false;
		} else if (!salary.equals(other.salary))
			return false;
		if (stateCode == null) {
			if (other.stateCode != null)
				return false;
		} else if (!stateCode.equals(other.stateCode))
			return false;
		if (townshipCode == null) {
			if (other.townshipCode != null)
				return false;
		} else if (!townshipCode.equals(other.townshipCode))
			return false;
		if (version != other.version)
			return false;
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
			return false;
		return true;
	}

}