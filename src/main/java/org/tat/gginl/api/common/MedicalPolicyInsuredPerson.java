package org.tat.gginl.api.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.ace.insurance.common.CommonCreateAndUpateMarks;
import org.ace.insurance.common.TableName;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.interfaces.IInsuredItem;
import org.ace.insurance.medical.claim.ClaimStatus;
import org.ace.insurance.medical.proposal.MedicalPersonHistoryRecord;
import org.ace.insurance.medical.proposal.MedicalProposalInsuredPerson;
import org.ace.insurance.medical.proposal.MedicalProposalInsuredPersonAddOn;
import org.ace.insurance.medical.proposal.MedicalProposalInsuredPersonAttachment;
import org.ace.insurance.medical.proposal.MedicalProposalInsuredPersonBeneficiaries;
import org.ace.insurance.medical.proposal.MedicalProposalInsuredPersonKeyFactorValue;
import org.ace.insurance.product.Product;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.occupation.Occupation;
import org.ace.insurance.system.common.relationship.RelationShip;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.MEDICALPOLICYINSUREDPERSON)
@TableGenerator(name = "MPOLINSP_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "MPOLINSP_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "MedicalPolicyInsuredPerson.findAll", query = "SELECT s FROM MedicalPolicyInsuredPerson s "),
		@NamedQuery(name = "MedicalPolicyInsuredPerson.findById", query = "SELECT s FROM MedicalPolicyInsuredPerson s WHERE s.id = :id"),
		@NamedQuery(name = "MedicalPolicyInsuredPerson.updateClaimStatus", query = "UPDATE MedicalPolicyInsuredPerson p SET p.claimStatus = :claimStatus WHERE p.id = :id") })
@EntityListeners(IDInterceptor.class)
public class MedicalPolicyInsuredPerson implements IInsuredItem, Serializable {
	private static final long serialVersionUID = -2214779925313647081L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MPOLINSP_GEN")
	private String id;
	@Column(name = "PERIODOFMONTH")
	private int periodMonth;
	@Column(name = "AGE")
	private int age;
	private int paymentTerm;
	private double premium;
	private double addOnPremium;
	private double operationAmount;
	private double disabilityAmount;
	private boolean actived;
	@Column(name = "HOSPITALDAYCOUNT")
	private int hosp_day_count;
	private boolean death;
	private int unit;
	private int basicPlusUnit;
	private double basicPlusPremium;
	private double totalNcbPremium;
	private double totalDiscountPremium;
	@Column(name = "BASICTERMPREMIUM")
	private double basicTermPremium;
	@Column(name = "ADDONTERMPREMIUM")
	private double addonTermPremium;
	@Column(name = "INPERSONCODENO")
	private String insPersonCodeNo;
	@Column(name = "INPERSONGROUPCODENO")
	private String inPersonGroupCodeNo;

	@Column(name = "BASICPLUSTERMPREMIUM")
	private double basicPlusTermPremium;

	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Temporal(TemporalType.DATE)
	private Date endDate;

	@Enumerated(EnumType.STRING)
	private ClaimStatus claimStatus;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RELATIONSHIPID", referencedColumnName = "ID")
	private RelationShip relationship;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTID", referencedColumnName = "ID")
	private Product product;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID")
	private Customer customer;

	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEDICALPOLICYID", referencedColumnName = "ID")
	private MedicalPolicy medicalPolicy;*/

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "MEDICALPOLICYINSUREDPERSONGUARDIANID", referencedColumnName = "ID")
	private MedicalPolicyInsuredPersonGuardian guardian;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "HOLDERID", referencedColumnName = "ID")
	private List<MedicalPolicyInsuredPersonAttachment> attachmentList;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "POLICYINSUREDPERSONID", referencedColumnName = "ID")
	private List<MedicalPolicyInsuredPersonAddOn> policyInsuredPersonAddOnList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "INSUREDPERSONID", referencedColumnName = "ID")
	private List<MedicalPersonHistoryRecord> medicalPersonHistoryRecordList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "MEDICALPOLICYINSUREDPERSONID", referencedColumnName = "ID")
	private List<MedicalPolicyInsuredPersonKeyFactorValue> policyInsuredPersonkeyFactorValueList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "POLICYINSUREDPERSONID", referencedColumnName = "ID")
	private List<MedicalPolicyInsuredPersonBeneficiaries> policyInsuredPersonBeneficiariesList;

	@Version
	private int version;

	@Embedded
	private CommonCreateAndUpateMarks commonCreateAndUpateMarks;

	public MedicalPolicyInsuredPerson() {
	}

	public MedicalPolicyInsuredPerson(MedicalProposalInsuredPerson insuredPerson) {
		this.dateOfBirth = insuredPerson.getCustomer().getDateOfBirth();
		this.customer = insuredPerson.getCustomer();
		this.periodMonth = insuredPerson.getPeriodMonth();
		this.startDate = insuredPerson.getStartDate();
		this.endDate = insuredPerson.getEndDate();
		this.product = insuredPerson.getProduct();
		this.premium = insuredPerson.getProposedPremium();
		this.addOnPremium = insuredPerson.getAddOnPremium();
		this.relationship = insuredPerson.getRelationship();
		this.insPersonCodeNo = insuredPerson.getInsPersonCodeNo();
		this.inPersonGroupCodeNo = insuredPerson.getInPersonGroupCodeNo();
		this.basicPlusUnit = insuredPerson.getBasicPlusUnit();
		this.basicPlusPremium = insuredPerson.getBasicPlusPremium();
		this.totalNcbPremium = insuredPerson.getTotalNcbPremium();
		this.age = Utils.getAgeForNextYear(insuredPerson.getCustomer().getDateOfBirth());
		this.unit = insuredPerson.getUnit();
		this.basicTermPremium = insuredPerson.getBasicTermPremium();
		this.addonTermPremium = insuredPerson.getAddOnTermPremium();

		if (insuredPerson.getGuardian() != null) {
			this.guardian = new MedicalPolicyInsuredPersonGuardian(insuredPerson.getGuardian());
		}
		for (MedicalProposalInsuredPersonAttachment attachment : insuredPerson.getAttachmentList()) {
			addAttachment(new MedicalPolicyInsuredPersonAttachment(attachment));
		}

		for (MedicalProposalInsuredPersonAttachment attachment : insuredPerson.getAttachmentList()) {
			addAttachment(new MedicalPolicyInsuredPersonAttachment(attachment));
		}

		for (MedicalProposalInsuredPersonKeyFactorValue keyFactorValue : insuredPerson.getKeyFactorValueList()) {
			addPolicyInsuredPersonKeyFactorValue(new MedicalPolicyInsuredPersonKeyFactorValue(keyFactorValue));
		}

		for (MedicalProposalInsuredPersonBeneficiaries insuredPersonBeneficiaries : insuredPerson.getInsuredPersonBeneficiariesList()) {
			addInsuredPersonBeneficiaries(new MedicalPolicyInsuredPersonBeneficiaries(insuredPersonBeneficiaries));
		}

		for (MedicalPersonHistoryRecord record : insuredPerson.getMedicalPersonHistoryRecordList()) {
			addHistoryRecord(new MedicalPersonHistoryRecord(record));
		}

		for (MedicalProposalInsuredPersonAddOn addOn : insuredPerson.getInsuredPersonAddOnList()) {
			addInsuredPersonAddOn(new MedicalPolicyInsuredPersonAddOn(addOn));
		}
	}

	public double getOperationAmount() {
		return operationAmount;
	}

	public void setOperationAmount(double operationAmount) {
		this.operationAmount = operationAmount;
	}

	public double getDisabilityAmount() {
		return disabilityAmount;
	}

	public void setDisabilityAmount(double disabilityAmount) {
		this.disabilityAmount = disabilityAmount;
	}

	public int getHosp_day_count() {
		return hosp_day_count;
	}

	public void setHosp_day_count(int hosp_day_count) {
		this.hosp_day_count = hosp_day_count;
	}

	public boolean isActived() {
		return actived;
	}

	public void setActived(boolean actived) {
		this.actived = actived;
	}

	public String getFullName() {
		return customer.getFullName();
	}

	public MedicalPolicyInsuredPersonGuardian getGuardian() {
		return guardian;
	}

	public void setGuardian(MedicalPolicyInsuredPersonGuardian guardian) {
		this.guardian = guardian;
	}

	public boolean isDeath() {
		return death;
	}

	public void setDeath(boolean death) {
		this.death = death;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInsPersonCodeNo() {
		return insPersonCodeNo;
	}

	public void setInsPersonCodeNo(String insPersonCodeNo) {
		this.insPersonCodeNo = insPersonCodeNo;
	}

	public String getInPersonGroupCodeNo() {
		return inPersonGroupCodeNo;
	}

	public void setInPersonGroupCodeNo(String inPersonGroupCodeNo) {
		this.inPersonGroupCodeNo = inPersonGroupCodeNo;
	}

	public int getPeriodMonth() {
		return periodMonth;
	}

	public void setPeriodMonth(int periodMonth) {
		this.periodMonth = periodMonth;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(int paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public ClaimStatus getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(ClaimStatus claimStatus) {
		this.claimStatus = claimStatus;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public void setAddOnPremium(double addOnPremium) {
		this.addOnPremium = addOnPremium;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public RelationShip getRelationship() {
		return relationship;
	}

	public void setRelationship(RelationShip relationship) {
		this.relationship = relationship;
	}

	public List<MedicalPolicyInsuredPersonAttachment> getAttachmentList() {
		if (attachmentList == null) {
			attachmentList = new ArrayList<MedicalPolicyInsuredPersonAttachment>();
		}
		return attachmentList;
	}

	public void setAttachmentList(List<MedicalPolicyInsuredPersonAttachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public List<MedicalPolicyInsuredPersonAddOn> getPolicyInsuredPersonAddOnList() {
		if (policyInsuredPersonAddOnList == null) {
			policyInsuredPersonAddOnList = new ArrayList<MedicalPolicyInsuredPersonAddOn>();
		}
		return policyInsuredPersonAddOnList;
	}

	public void setPolicyInsuredPersonAddOnList(List<MedicalPolicyInsuredPersonAddOn> policyInsuredPersonAddOnList) {
		this.policyInsuredPersonAddOnList = policyInsuredPersonAddOnList;
	}

	public List<MedicalPolicyInsuredPersonKeyFactorValue> getPolicyInsuredPersonkeyFactorValueList() {
		if (policyInsuredPersonkeyFactorValueList == null) {
			policyInsuredPersonkeyFactorValueList = new ArrayList<MedicalPolicyInsuredPersonKeyFactorValue>();
		}
		return policyInsuredPersonkeyFactorValueList;
	}

	public void setPolicyInsuredPersonkeyFactorValueList(List<MedicalPolicyInsuredPersonKeyFactorValue> policyInsuredPersonkeyFactorValueList) {
		this.policyInsuredPersonkeyFactorValueList = policyInsuredPersonkeyFactorValueList;
	}

	public List<MedicalPolicyInsuredPersonBeneficiaries> getPolicyInsuredPersonBeneficiariesList() {
		if (this.policyInsuredPersonBeneficiariesList == null) {
			this.policyInsuredPersonBeneficiariesList = new ArrayList<MedicalPolicyInsuredPersonBeneficiaries>();
		}
		return this.policyInsuredPersonBeneficiariesList;

	}

	public void setPolicyInsuredPersonBeneficiariesList(List<MedicalPolicyInsuredPersonBeneficiaries> policyInsuredPersonBeneficiariesList) {
		this.policyInsuredPersonBeneficiariesList = policyInsuredPersonBeneficiariesList;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public List<MedicalPersonHistoryRecord> getMedicalPersonHistoryRecordList() {
		if (medicalPersonHistoryRecordList == null) {
			medicalPersonHistoryRecordList = new ArrayList<MedicalPersonHistoryRecord>();
		}
		return medicalPersonHistoryRecordList;
	}

	public void setMedicalPersonHistoryRecordList(List<MedicalPersonHistoryRecord> medicalPersonHistoryRecordList) {
		this.medicalPersonHistoryRecordList = medicalPersonHistoryRecordList;
	}

	public void addHistoryRecord(MedicalPersonHistoryRecord record) {
		getMedicalPersonHistoryRecordList().add(record);
	}

	public void addAttachment(MedicalPolicyInsuredPersonAttachment attachment) {
		getAttachmentList().add(attachment);
	}

	public void addInsuredPersonAddOn(MedicalPolicyInsuredPersonAddOn policyInsuredPersonAddOn) {
		getPolicyInsuredPersonAddOnList().add(policyInsuredPersonAddOn);
	}

	public void addPolicyInsuredPersonKeyFactorValue(MedicalPolicyInsuredPersonKeyFactorValue keyFactorValue) {
		getPolicyInsuredPersonkeyFactorValueList().add(keyFactorValue);
	}

	public void addInsuredPersonBeneficiaries(MedicalPolicyInsuredPersonBeneficiaries policyInsuredPersonBeneficiaries) {
		getPolicyInsuredPersonBeneficiariesList().add(policyInsuredPersonBeneficiaries);
	}

	public double getAddOnPremium() {
		return addOnPremium;
	}

	public int getPeriodYears() {
		return periodMonth / 12;
	}

	public CommonCreateAndUpateMarks getCommonCreateAndUpateMarks() {
		return commonCreateAndUpateMarks;
	}

	public void setCommonCreateAndUpateMarks(CommonCreateAndUpateMarks commonCreateAndUpateMarks) {
		this.commonCreateAndUpateMarks = commonCreateAndUpateMarks;
	}

	public String getFatherName() {
		return this.customer.getFatherName();
	}

	public String getFullIdNo() {
		return this.customer.getFullIdNo();
	}

	public Occupation getOccupation() {
		return this.customer.getOccupation();
	}

	public String getFullAddress() {
		return this.customer.getFullAddress();
	}


	public String getGuardionName() {
		if (this.guardian != null) {
			return this.guardian.getCustomer().getFullName();
		} else {
			return "";
		}
	}

	public String getGuardionNRC() {
		if (this.guardian != null) {
			return this.guardian.getCustomer().getFullIdNo();
		} else {
			return "";
		}
	}

	public String getGuardionRelation() {
		if (this.guardian != null) {
			return this.guardian.getRelationship().getName();
		} else {
			return "";
		}
	}

	public int getTotalUnit() {
		int result = 0;
		for (MedicalPolicyInsuredPersonAddOn addOn : getPolicyInsuredPersonAddOnList()) {
			result += addOn.getUnit();
		}
		return result + getBasicPlusUnit() + getUnit();
	}

	public double getTotalPremium() {
		double result = getAddOnPremium() + getBasicPlusPremium() + getPremium() - getTotalNcbPremium();
		return result;
	}

	public int getBasicPlusUnit() {
		return basicPlusUnit;
	}

	public void setBasicPlusUnit(int basicPlusUnit) {
		this.basicPlusUnit = basicPlusUnit;
	}

	public double getBasicPlusPremium() {
		return basicPlusPremium;
	}

	public void setBasicPlusPremium(double basicPlusPremium) {
		this.basicPlusPremium = basicPlusPremium;
	}

	public double getTotalNcbPremium() {
		return totalNcbPremium;
	}

	public void setTotalNcbPremium(double totalNcbPremium) {
		this.totalNcbPremium = totalNcbPremium;
	}

	public double getTotalDiscountPremium() {
		return totalDiscountPremium;
	}

	public void setTotalDiscountPremium(double totalDiscountPremium) {
		this.totalDiscountPremium = totalDiscountPremium;
	}

	public double getBasicTermPremium() {
		return basicTermPremium;
	}

	public void setBasicTermPremium(double basicTermPremium) {
		this.basicTermPremium = basicTermPremium;
	}

	public double getAddonTermPremium() {
		return addonTermPremium;
	}

	public void setAddonTermPremium(double addonTermPremium) {
		this.addonTermPremium = addonTermPremium;
	}

	public double getBasicPlusTermPremium() {
		return basicPlusTermPremium;
	}

	public void setBasicPlusTermPremium(double basicPlusTermPremium) {
		this.basicPlusTermPremium = basicPlusTermPremium;
	}

	

	public double getTotalTermPremium() {
		return getBasicTermPremium() + getBasicPlusTermPremium() + getAddonTermPremium() - getTotalNcbPremium();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (actived ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(addOnPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + age;
		temp = Double.doubleToLongBits(addonTermPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(basicPlusPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(basicPlusTermPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + basicPlusUnit;
		temp = Double.doubleToLongBits(basicTermPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((claimStatus == null) ? 0 : claimStatus.hashCode());
		result = prime * result + ((commonCreateAndUpateMarks == null) ? 0 : commonCreateAndUpateMarks.hashCode());
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + (death ? 1231 : 1237);
		temp = Double.doubleToLongBits(disabilityAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((guardian == null) ? 0 : guardian.hashCode());
		result = prime * result + hosp_day_count;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inPersonGroupCodeNo == null) ? 0 : inPersonGroupCodeNo.hashCode());
		result = prime * result + ((insPersonCodeNo == null) ? 0 : insPersonCodeNo.hashCode());
		temp = Double.doubleToLongBits(operationAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + paymentTerm;
		result = prime * result + periodMonth;
		temp = Double.doubleToLongBits(premium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((relationship == null) ? 0 : relationship.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		temp = Double.doubleToLongBits(totalDiscountPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(totalNcbPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + unit;
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
		MedicalPolicyInsuredPerson other = (MedicalPolicyInsuredPerson) obj;
		if (actived != other.actived)
			return false;
		if (Double.doubleToLongBits(addOnPremium) != Double.doubleToLongBits(other.addOnPremium))
			return false;
		if (age != other.age)
			return false;
		if (Double.doubleToLongBits(addonTermPremium) != Double.doubleToLongBits(other.addonTermPremium))
			return false;
		if (Double.doubleToLongBits(basicPlusPremium) != Double.doubleToLongBits(other.basicPlusPremium))
			return false;
		if (Double.doubleToLongBits(basicPlusTermPremium) != Double.doubleToLongBits(other.basicPlusTermPremium))
			return false;
		if (basicPlusUnit != other.basicPlusUnit)
			return false;
		if (Double.doubleToLongBits(basicTermPremium) != Double.doubleToLongBits(other.basicTermPremium))
			return false;
		if (claimStatus != other.claimStatus)
			return false;
		if (commonCreateAndUpateMarks == null) {
			if (other.commonCreateAndUpateMarks != null)
				return false;
		} else if (!commonCreateAndUpateMarks.equals(other.commonCreateAndUpateMarks))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (death != other.death)
			return false;
		if (Double.doubleToLongBits(disabilityAmount) != Double.doubleToLongBits(other.disabilityAmount))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (guardian == null) {
			if (other.guardian != null)
				return false;
		} else if (!guardian.equals(other.guardian))
			return false;
		if (hosp_day_count != other.hosp_day_count)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (inPersonGroupCodeNo == null) {
			if (other.inPersonGroupCodeNo != null)
				return false;
		} else if (!inPersonGroupCodeNo.equals(other.inPersonGroupCodeNo))
			return false;
		if (insPersonCodeNo == null) {
			if (other.insPersonCodeNo != null)
				return false;
		} else if (!insPersonCodeNo.equals(other.insPersonCodeNo))
			return false;
		if (Double.doubleToLongBits(operationAmount) != Double.doubleToLongBits(other.operationAmount))
			return false;
		if (paymentTerm != other.paymentTerm)
			return false;
		if (periodMonth != other.periodMonth)
			return false;
		if (Double.doubleToLongBits(premium) != Double.doubleToLongBits(other.premium))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (relationship == null) {
			if (other.relationship != null)
				return false;
		} else if (!relationship.equals(other.relationship))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (Double.doubleToLongBits(totalDiscountPremium) != Double.doubleToLongBits(other.totalDiscountPremium))
			return false;
		if (Double.doubleToLongBits(totalNcbPremium) != Double.doubleToLongBits(other.totalNcbPremium))
			return false;
		if (unit != other.unit)
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	@Override
	public String getPrefix() {
		return null;
	}

	@Override
	public double getSumInsured() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAddOnSumInsure() {
		return 0;
	}

	@Override
	public double getAddOnTermPremium() {
		return addonTermPremium;
	}

}
