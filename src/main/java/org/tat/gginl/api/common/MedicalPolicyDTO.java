package org.tat.gginl.api.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.tat.gginl.api.domains.Agent;
import org.tat.gginl.api.domains.Branch;
import org.tat.gginl.api.domains.Customer;
import org.tat.gginl.api.domains.CustomerDTO;
import org.tat.gginl.api.domains.MedicalPolicyAttachment;
import org.tat.gginl.api.domains.Organization;
import org.tat.gginl.api.domains.PaymentType;
import org.tat.gginl.api.domains.SaleMan;
import org.tat.gginl.api.domains.SalePoint;
import org.tat.gginl.api.domains.User;


public class MedicalPolicyDTO extends CommonDTO {

	private boolean exitsEntity;
	private boolean nilExcess;
	private boolean delFlag;

	private int lastPaymentTerm;
	private int printCount;

	private int version;
	private double totalDiscountAmount;
	private double standardExcess;
	private String policyNo;
	private String id;
	private Date commenmanceDate;
	private Date activedPolicyStartDate;
	private Date activedPolicyEndDate;
	private PolicyStatus policyStatus;
	private Customer customer;
	private Customer referral;
	private Organization organization;
	private User approvedBy;
	private Branch branch;
	private PaymentType paymentType;
	private Agent agent;
	private SaleMan saleMan;
	private MedProDTO medProDTO;
	private List<MedicalPolicyInsuredPersonDTO> policyInsuredPersonDtoList;
	private List<MedicalPolicyAttachment> attachmentList;
	private double paidOperationAmount;
	private double paidMedicationAmount;
	private SalePoint salePoint;
	private CustomerType customerType;
	private HealthType healthType;
	private ProposalType proposalType;
	private boolean isCorporate;
	private CustomerDTO customerDTO;
	private CustomerDTO referralDTO;

	public MedicalPolicyDTO() {

	}

	public MedicalPolicyDTO(MedicalPolicyDTO medicalPolicyDTO) {
		this.nilExcess = medicalPolicyDTO.isNilExcess();
		this.delFlag = medicalPolicyDTO.isDelFlag();
		this.lastPaymentTerm = medicalPolicyDTO.getLastPaymentTerm();
		this.printCount = medicalPolicyDTO.getPrintCount();
		this.version = medicalPolicyDTO.getVersion();
		this.totalDiscountAmount = medicalPolicyDTO.getTotalDiscountAmount();
		this.standardExcess = medicalPolicyDTO.getStandardExcess();
		this.policyNo = medicalPolicyDTO.getPolicyNo();
		this.id = medicalPolicyDTO.getId();
		this.commenmanceDate = medicalPolicyDTO.getCommenmanceDate();
		this.activedPolicyStartDate = medicalPolicyDTO.getActivedPolicyStartDate();
		this.activedPolicyEndDate = medicalPolicyDTO.getActivedPolicyEndDate();
		this.policyStatus = medicalPolicyDTO.getPolicyStatus();
		this.customer = medicalPolicyDTO.getCustomer();
		this.referral = medicalPolicyDTO.getReferral();
		this.organization = medicalPolicyDTO.getOrganization();
		this.approvedBy = medicalPolicyDTO.getApprovedBy();
		this.branch = medicalPolicyDTO.getBranch();
		this.paymentType = medicalPolicyDTO.getPaymentType();
		this.agent = medicalPolicyDTO.getAgent();
		this.saleMan = medicalPolicyDTO.getSaleMan();
		this.attachmentList = medicalPolicyDTO.getAttachmentList();
		this.exitsEntity = medicalPolicyDTO.isExitsEntity();
		this.medProDTO = medicalPolicyDTO.getMedProDTO();
		this.paidMedicationAmount = medicalPolicyDTO.getPaidMedicationAmount();
		this.paidOperationAmount = medicalPolicyDTO.getPaidOperationAmount();
		for (MedicalPolicyInsuredPersonDTO personDTO : medicalPolicyDTO.getPolicyInsuredPersonDtoList()) {
			addPolicyInsuredPersonDTO(personDTO);
		}
		this.salePoint = medicalPolicyDTO.getSalePoint();
		this.customerType = medicalPolicyDTO.getCustomerType();
		this.healthType = medicalPolicyDTO.getHealthType();
		this.proposalType = medicalPolicyDTO.getProposalType();
		this.isCorporate = medicalPolicyDTO.isCorporate;
	}

	public void addPolicyInsuredPersonDTO(MedicalPolicyInsuredPersonDTO policyInsuredPerson) {
		getPolicyInsuredPersonDtoList().add(policyInsuredPerson);
	}

	public MedProDTO getMedProDTO() {
		return medProDTO;
	}

	public void setMedProDTO(MedProDTO medProDTO) {
		this.medProDTO = medProDTO;
	}

	public boolean isNilExcess() {
		return nilExcess;
	}

	public void setNilExcess(boolean nilExcess) {
		this.nilExcess = nilExcess;
	}

	public boolean isDelFlag() {
		return delFlag;
	}

	public void setDelFlag(boolean delFlag) {
		this.delFlag = delFlag;
	}

	public int getLastPaymentTerm() {
		return lastPaymentTerm;
	}

	public void setLastPaymentTerm(int lastPaymentTerm) {
		this.lastPaymentTerm = lastPaymentTerm;
	}

	public int getPrintCount() {
		return printCount;
	}

	public void setPrintCount(int printCount) {
		this.printCount = printCount;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public double getTotalDiscountAmount() {
		return totalDiscountAmount;
	}

	public void setTotalDiscountAmount(double totalDiscountAmount) {
		this.totalDiscountAmount = totalDiscountAmount;
	}

	public double getStandardExcess() {
		return standardExcess;
	}

	public void setStandardExcess(double standardExcess) {
		this.standardExcess = standardExcess;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCommenmanceDate() {
		return commenmanceDate;
	}

	public void setCommenmanceDate(Date commenmanceDate) {
		this.commenmanceDate = commenmanceDate;
	}

	public Date getActivedPolicyStartDate() {
		return activedPolicyStartDate;
	}

	public void setActivedPolicyStartDate(Date activedPolicyStartDate) {
		this.activedPolicyStartDate = activedPolicyStartDate;
	}

	public Date getActivedPolicyEndDate() {
		return activedPolicyEndDate;
	}

	public void setActivedPolicyEndDate(Date activedPolicyEndDate) {
		this.activedPolicyEndDate = activedPolicyEndDate;
	}

	public PolicyStatus getPolicyStatus() {
		return policyStatus;
	}

	public void setPolicyStatus(PolicyStatus policyStatus) {
		this.policyStatus = policyStatus;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public CustomerDTO getCustomerDTO() {
		return customerDTO;
	}

	public void setCustomerDTO(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}

	public Customer getReferral() {
		return referral;
	}

	public void setReferral(Customer referral) {
		this.referral = referral;
	}

	public CustomerDTO getReferralDTO() {
		return referralDTO;
	}

	public void setReferralDTO(CustomerDTO referralDTO) {
		this.referralDTO = referralDTO;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public User getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(User approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public SaleMan getSaleMan() {
		return saleMan;
	}

	public void setSaleMan(SaleMan saleMan) {
		this.saleMan = saleMan;
	}

	public List<MedicalPolicyInsuredPersonDTO> getPolicyInsuredPersonDtoList() {
		if (policyInsuredPersonDtoList == null) {
			policyInsuredPersonDtoList = new ArrayList<MedicalPolicyInsuredPersonDTO>();
		}
		return policyInsuredPersonDtoList;
	}

	public void setPolicyInsuredPersonDtoList(List<MedicalPolicyInsuredPersonDTO> policyInsuredPersonDtoList) {
		this.policyInsuredPersonDtoList = policyInsuredPersonDtoList;
	}

	public boolean isExitsEntity() {
		return exitsEntity;
	}

	public void setExitsEntity(boolean exitsEntity) {
		this.exitsEntity = exitsEntity;
	}

	public List<MedicalPolicyAttachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<MedicalPolicyAttachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public void addMedicalPolicyAttachment(MedicalPolicyAttachment medicalPolicyAttachment) {
		if (this.attachmentList == null) {
			this.attachmentList = new ArrayList<MedicalPolicyAttachment>();
		}
		this.attachmentList.add(medicalPolicyAttachment);
	}

	public HealthType getHealthType() {
		return healthType;
	}

	public void setHealthType(HealthType healthType) {
		this.healthType = healthType;
	}

	public ProposalType getProposalType() {
		return proposalType;
	}

	public void setProposalType(ProposalType proposalType) {
		this.proposalType = proposalType;
	}

	public boolean isCorporate() {
		return isCorporate;
	}

	public void setCorporate(boolean isCorporate) {
		this.isCorporate = isCorporate;
	}

	public String getCustomerName() {
		if (customer != null) {
			return customer.getFullName();
		}
		if (organization != null) {
			return organization.getName();
		}
		return null;
	}

	public String getCustomerAddress() {
		if (customer != null) {
			return customer.getFullAddress();
		}
		if (organization != null) {
			return organization.getFullAddress();
		}
		return null;
	}

	public String getSalePersonName() {
		if (agent != null) {
			return agent.getFullName();
		} else if (saleMan != null) {
			return saleMan.getFullName();
		} else if (referral != null) {
			return referral.getFullName();
		}
		return null;
	}

	public double getPremium() {
		double result = 0.0;
		for (MedicalPolicyInsuredPersonDTO personDTO : policyInsuredPersonDtoList) {
			result += personDTO.getPremium();
		}
		return result;
	}

	public double getPaidOperationAmount() {
		return paidOperationAmount;
	}

	public void setPaidOperationAmount(double paidOperationAmount) {
		this.paidOperationAmount = paidOperationAmount;
	}

	public double getPaidMedicationAmount() {
		return paidMedicationAmount;
	}

	public void setPaidMedicationAmount(double paidMedicationAmount) {
		this.paidMedicationAmount = paidMedicationAmount;
	}

	public SalePoint getSalePoint() {
		return salePoint;
	}

	public void setSalePoint(SalePoint salePoint) {
		this.salePoint = salePoint;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((activedPolicyEndDate == null) ? 0 : activedPolicyEndDate.hashCode());
		result = prime * result + ((activedPolicyStartDate == null) ? 0 : activedPolicyStartDate.hashCode());
		result = prime * result + ((agent == null) ? 0 : agent.hashCode());
		result = prime * result + ((approvedBy == null) ? 0 : approvedBy.hashCode());
		result = prime * result + ((attachmentList == null) ? 0 : attachmentList.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((commenmanceDate == null) ? 0 : commenmanceDate.hashCode());
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + (delFlag ? 1231 : 1237);
		result = prime * result + (exitsEntity ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + lastPaymentTerm;
		result = prime * result + ((medProDTO == null) ? 0 : medProDTO.hashCode());
		result = prime * result + (nilExcess ? 1231 : 1237);
		result = prime * result + ((organization == null) ? 0 : organization.hashCode());
		long temp;
		temp = Double.doubleToLongBits(paidMedicationAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(paidOperationAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((paymentType == null) ? 0 : paymentType.hashCode());
		result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
		result = prime * result + ((policyStatus == null) ? 0 : policyStatus.hashCode());
		result = prime * result + printCount;
		result = prime * result + ((referral == null) ? 0 : referral.hashCode());
		result = prime * result + ((saleMan == null) ? 0 : saleMan.hashCode());
		temp = Double.doubleToLongBits(standardExcess);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(totalDiscountAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + version;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MedicalPolicyDTO other = (MedicalPolicyDTO) obj;
		if (activedPolicyEndDate == null) {
			if (other.activedPolicyEndDate != null)
				return false;
		} else if (!activedPolicyEndDate.equals(other.activedPolicyEndDate))
			return false;
		if (activedPolicyStartDate == null) {
			if (other.activedPolicyStartDate != null)
				return false;
		} else if (!activedPolicyStartDate.equals(other.activedPolicyStartDate))
			return false;
		if (agent == null) {
			if (other.agent != null)
				return false;
		} else if (!agent.equals(other.agent))
			return false;
		if (approvedBy == null) {
			if (other.approvedBy != null)
				return false;
		} else if (!approvedBy.equals(other.approvedBy))
			return false;
		if (attachmentList == null) {
			if (other.attachmentList != null)
				return false;
		} else if (!attachmentList.equals(other.attachmentList))
			return false;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (commenmanceDate == null) {
			if (other.commenmanceDate != null)
				return false;
		} else if (!commenmanceDate.equals(other.commenmanceDate))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;

		if (delFlag != other.delFlag)
			return false;
		if (exitsEntity != other.exitsEntity)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastPaymentTerm != other.lastPaymentTerm)
			return false;
		if (medProDTO == null) {
			if (other.medProDTO != null)
				return false;
		} else if (!medProDTO.equals(other.medProDTO))
			return false;
		if (nilExcess != other.nilExcess)
			return false;
		if (organization == null) {
			if (other.organization != null)
				return false;
		} else if (!organization.equals(other.organization))
			return false;
		if (Double.doubleToLongBits(paidMedicationAmount) != Double.doubleToLongBits(other.paidMedicationAmount))
			return false;
		if (Double.doubleToLongBits(paidOperationAmount) != Double.doubleToLongBits(other.paidOperationAmount))
			return false;
		if (paymentType == null) {
			if (other.paymentType != null)
				return false;
		} else if (!paymentType.equals(other.paymentType))
			return false;
		if (policyNo == null) {
			if (other.policyNo != null)
				return false;
		} else if (!policyNo.equals(other.policyNo))
			return false;
		if (policyStatus != other.policyStatus)
			return false;
		if (printCount != other.printCount)
			return false;
		if (referral == null) {
			if (other.referral != null)
				return false;
		} else if (!referral.equals(other.referral))
			return false;
		if (saleMan == null) {
			if (other.saleMan != null)
				return false;
		} else if (!saleMan.equals(other.saleMan))
			return false;
		if (Double.doubleToLongBits(standardExcess) != Double.doubleToLongBits(other.standardExcess))
			return false;
		if (Double.doubleToLongBits(totalDiscountAmount) != Double.doubleToLongBits(other.totalDiscountAmount))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
