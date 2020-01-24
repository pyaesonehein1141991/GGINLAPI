package org.tat.gginl.api.domains;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.tat.gginl.api.common.BeneficiariesInfoDTO;
import org.tat.gginl.api.common.CommonCreateAndUpateMarks;
import org.tat.gginl.api.common.FormatID;
import org.tat.gginl.api.common.Name;
import org.tat.gginl.api.common.PolicyInsuredPersonBeneficiaries;
import org.tat.gginl.api.common.ResidentAddress;
import org.tat.gginl.api.common.TableName;
import org.tat.gginl.api.common.emumdata.Gender;
import org.tat.gginl.api.common.emumdata.IdType;

@Entity
@Table(name = TableName.INSUREDPERSONBENEFICIARIES)
@TableGenerator(name = "INSUREDPERSONBENEFICIARIES_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "INSUREDPERSONBENEFICIARIES_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "InsuredPersonBeneficiaries.findAll", query = "SELECT p FROM InsuredPersonBeneficiaries p "),
		@NamedQuery(name = "InsuredPersonBeneficiaries.findById", query = "SELECT p FROM InsuredPersonBeneficiaries p WHERE p.id = :id") })
@Access(value = AccessType.FIELD)
public class InsuredPersonBeneficiaries {
	private int age;
	private float percentage;
	@Transient
	private String id;
	private String prefix;
	private String beneficiaryNo;
	private String initialId;
	private String idNo;

	@Enumerated(value = EnumType.STRING)
	private Gender gender;

	@Enumerated(value = EnumType.STRING)
	private IdType idType;

	@Embedded
	private ResidentAddress residentAddress;

	@Embedded
	private Name name;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RELATIONSHIPID", referencedColumnName = "ID")
	private RelationShip relationship;

	
	@Column(name = "DOB")
	private Date dateOfBirth;

	@Version
	private int version;
	
	@Embedded
	private CommonCreateAndUpateMarks recorder;

	public InsuredPersonBeneficiaries() {
	}

	public InsuredPersonBeneficiaries(PolicyInsuredPersonBeneficiaries pinsuredPersonBeneficiaries) {
		this.beneficiaryNo = pinsuredPersonBeneficiaries.getBeneficiaryNo();
		this.age = pinsuredPersonBeneficiaries.getAge();
		this.percentage = pinsuredPersonBeneficiaries.getPercentage();
		this.initialId = pinsuredPersonBeneficiaries.getInitialId();
		this.idNo = pinsuredPersonBeneficiaries.getIdNo();
		this.gender = pinsuredPersonBeneficiaries.getGender();
		this.idType = pinsuredPersonBeneficiaries.getIdType();
		this.residentAddress = pinsuredPersonBeneficiaries.getResidentAddress();
		this.name = pinsuredPersonBeneficiaries.getName();
		this.relationship = pinsuredPersonBeneficiaries.getRelationship();
	}

	public InsuredPersonBeneficiaries(BeneficiariesInfoDTO dto) {
		this.age = dto.getAge();
		this.percentage = dto.getPercentage();
		this.beneficiaryNo = dto.getBeneficiaryNo();
		this.initialId = dto.getInitialId();
		this.idNo = dto.getFullIdNo();
		this.gender = dto.getGender();
		this.idType = dto.getIdType();
		this.residentAddress = dto.getResidentAddress();
		this.name = dto.getName();
		this.relationship = dto.getRelationship();
		if (dto.isExistEntity()) {
			this.id = dto.getTempId();
			this.version = dto.getVersion();
		}
	}

	public InsuredPersonBeneficiaries(String beneficiaryNo, int age, float percentage, String initialId, String idNo, Gender gender, IdType idType, ResidentAddress residentAddress,
			Name name, RelationShip relationship) {
		this.beneficiaryNo = beneficiaryNo;
		this.age = age;
		this.percentage = percentage;
		this.initialId = initialId;
		this.idNo = idNo;
		this.gender = gender;
		this.idType = idType;
		this.residentAddress = residentAddress;
		this.name = name;
		this.relationship = relationship;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "INSUREDPERSONBENEFICIARIES_GEN")
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

	public String getBeneficiaryNo() {
		return beneficiaryNo;
	}

	public void setBeneficiaryNo(String beneficiaryNo) {
		this.beneficiaryNo = beneficiaryNo;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}

	public RelationShip getRelationship() {
		return relationship;
	}

	public void setRelationship(RelationShip relationship) {
		this.relationship = relationship;
	}

	
	public String getInitialId() {
		return initialId;
	}

	public void setInitialId(String initialId) {
		this.initialId = initialId;
	}

	public Gender getGender() {
		return gender;
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

	public ResidentAddress getResidentAddress() {
		return residentAddress;
	}

	public void setResidentAddress(ResidentAddress residentAddress) {
		this.residentAddress = residentAddress;
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

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	
	public CommonCreateAndUpateMarks getRecorder() {
		return recorder;
	}

	public void setRecorder(CommonCreateAndUpateMarks recorder) {
		this.recorder = recorder;
	}

	public String getFullName() {
		String result = "";
		if (name != null) {
			if (initialId != null && !initialId.isEmpty()) {
				result = initialId;
			}
			if (name.getFirstName() != null && !name.getFirstName().isEmpty()) {
				result = result + " " + name.getFirstName();
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((beneficiaryNo == null) ? 0 : beneficiaryNo.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idNo == null) ? 0 : idNo.hashCode());
		result = prime * result + ((idType == null) ? 0 : idType.hashCode());
		result = prime * result + ((initialId == null) ? 0 : initialId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Float.floatToIntBits(percentage);
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		result = prime * result + ((residentAddress == null) ? 0 : residentAddress.hashCode());
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
		InsuredPersonBeneficiaries other = (InsuredPersonBeneficiaries) obj;
		if (age != other.age)
			return false;
		if (beneficiaryNo == null) {
			if (other.beneficiaryNo != null)
				return false;
		} else if (!beneficiaryNo.equals(other.beneficiaryNo))
			return false;
		if (gender != other.gender)
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Float.floatToIntBits(percentage) != Float.floatToIntBits(other.percentage))
			return false;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
			return false;
		if (residentAddress == null) {
			if (other.residentAddress != null)
				return false;
		} else if (!residentAddress.equals(other.residentAddress))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
