package org.tat.gginl.api.domains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.tat.gginl.api.common.FormatID;
import org.tat.gginl.api.common.IEntity;
import org.tat.gginl.api.common.LifeClaimRole;
import org.tat.gginl.api.common.TableName;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */

@Entity
@Table(name=TableName.LIFECLAIM)
@TableGenerator(name = "LIFECLAIM_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LIFECLAIM_GEN", allocationSize = 10)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "CLAIMROLE", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = LifeClaimRole.CLAIM )
@Access(AccessType.FIELD)
@NamedQueries(value = {
		@NamedQuery(name = "Claim.findAll", query = "SELECT c FROM LifeClaim c "),
		@NamedQuery(name = "Claim.findById", query = "SELECT c FROM LifeClaim c WHERE c.id = :id"),
		@NamedQuery(name = "Claim.findByClaimRequestId", query = "SELECT c FROM LifeClaim c WHERE c.claimRequestId = :id"),
		@NamedQuery(name = "Claim.updateServiceCharges", query = "UPDATE LifeClaim c SET c.serviceCharges = :serviceCharges WHERE c.claimRequestId = :id")})
public class LifeClaim implements Serializable, IEntity{

	private static final long serialVersionUID = -2562764770209474910L;
	
	@Transient
	private String id;
	@Transient
	private String prefix;
	
	@Column(name = "CLAIMREQUESTID")
	private String claimRequestId;
	
	@Column(name = "SERVICECHARGES")
	private double serviceCharges;
	
	@Column(name = "SUBMITTEDDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date submittedDate;

	@Column(name = "CLAIMROLE" , insertable = false , updatable = false)
	private String claimRole;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LIFEPOLICYID", referencedColumnName = "ID")
	private LifePolicy lifePolicy;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCHID", referencedColumnName = "ID")
	private Branch branch;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SALEMANID", referencedColumnName = "ID")
	private SaleMan saleman;

	@OneToOne(cascade = CascadeType.ALL , fetch = FetchType.LAZY, orphanRemoval = true )
	@JoinColumn(name = "SUCCERSSORID", referencedColumnName = "ID")
	private LifeClaimSuccessor successor;
	
	@OneToOne(cascade = CascadeType.ALL , fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "CLAIMINSUREDPERSONID", referencedColumnName = "ID")
	private LifeClaimInsuredPerson claimInsuredPerson;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "lifeClaim", orphanRemoval = true)
	private List<LifeClaimInsuredPersonBeneficiary> claimInsuredPersonBeneficiaryList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "lifeClaim", orphanRemoval = true)
	private List<LifeClaimAttachment> claimAttachmentList;
	
	@Version
	private int version;
	
	private String portalId;

	public LifeClaim(){

	}

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="LIFECLAIM_GEN")
	@Access(AccessType.PROPERTY)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (id != null) {
			this.id = FormatID.formatId(id, getPrefix(), 10);
		}
	}

	public String getClaimRequestId(){
		return claimRequestId;
	}

	public void setClaimRequestId(String claimRequestId){
		this.claimRequestId = claimRequestId;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getClaimRole(){
		return claimRole;
	}

	public void setClaimRole(String claimRole) {
		this.claimRole = claimRole;
	}

	public Date getSubmittedDate(){
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate){
		this.submittedDate = submittedDate;
	}

	public double getServiceCharges(){
		return serviceCharges;
	}

	public void setServiceCharges(double serviceCharges){
		this.serviceCharges = serviceCharges;
	}

	public List<LifeClaimInsuredPersonBeneficiary> getClaimInsuredPersonBeneficiaryList(){
		if (claimInsuredPersonBeneficiaryList == null) {
			claimInsuredPersonBeneficiaryList = new ArrayList<LifeClaimInsuredPersonBeneficiary>();
		}
		return claimInsuredPersonBeneficiaryList;
	}

	public void setClaimInsuredPersonBeneficiaryList(List<LifeClaimInsuredPersonBeneficiary> claimInsuredPersonBeneficiaryList){
		this.claimInsuredPersonBeneficiaryList = claimInsuredPersonBeneficiaryList;
	}

	public List<LifeClaimAttachment> getClaimAttachmentList(){
		return claimAttachmentList;
	}

	public void setClaimAttachmentList(List<LifeClaimAttachment> claimAttachmentList){
		this.claimAttachmentList = claimAttachmentList;
	}

	public LifePolicy getLifePolicy(){
		return lifePolicy;
	}

	public void setLifePolicy(LifePolicy lifePolicy){
		this.lifePolicy = lifePolicy;
	}

	public Branch getBranch(){
		return branch;
	}

	public void setBranch(Branch branch){
		this.branch = branch;
	}

	public LifeClaimSuccessor getSuccessor(){
		return successor;
	}

	public void setSuccessor(LifeClaimSuccessor succesor){
		this.successor = succesor;
	}

	public SaleMan getSaleman(){
		return saleman;
	}

	public void setSaleman(SaleMan saleman){
		this.saleman = saleman;
	}

	public LifeClaimInsuredPerson getClaimInsuredPerson() {
		return claimInsuredPerson;
	}

	public void setClaimInsuredPerson(LifeClaimInsuredPerson claimInsuredPerson) {
		this.claimInsuredPerson = claimInsuredPerson;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public void addClaimAttachment(LifeClaimAttachment claimAttachment) {
		if (this.claimAttachmentList == null) {
			this.claimAttachmentList = new ArrayList<LifeClaimAttachment>();
		}
		claimAttachment.setClaim(this);
		this.claimAttachmentList.add(claimAttachment);
	}

	public void addClaimInsuredPersonBeneficiary(LifeClaimInsuredPersonBeneficiary claimInsuredPersonBeneficiary) {
		if (this.claimInsuredPersonBeneficiaryList == null) {
			this.claimInsuredPersonBeneficiaryList = new ArrayList<LifeClaimInsuredPersonBeneficiary>();
		}
		claimInsuredPersonBeneficiary.setLifeClaim(this);

		boolean newEntity = true;

		if (claimInsuredPersonBeneficiary.getId() != null) {
			for (LifeClaimInsuredPersonBeneficiary cipb :claimInsuredPersonBeneficiaryList) {
				if (cipb.getId() != null) {
					if (cipb.getId().equals(claimInsuredPersonBeneficiary.getId())) {
						cipb.update(claimInsuredPersonBeneficiary);
						newEntity = false;
					}
				}
			}
		}
		if (newEntity) {
			this.claimInsuredPersonBeneficiaryList.add(claimInsuredPersonBeneficiary);
		}
	}

	public LifeClaimInsuredPersonBeneficiary mergeClaimInsuredPersonBeneficiary(LifeClaimInsuredPersonBeneficiary claimInsuredPersonBeneficiary) {
		LifeClaimInsuredPersonBeneficiary result = claimInsuredPersonBeneficiary;

		if (!claimInsuredPersonBeneficiary.isNew()) {
			for (LifeClaimInsuredPersonBeneficiary cipb :claimInsuredPersonBeneficiaryList) {
				if (!cipb.isNew()) {
					if (cipb.getId().equals(claimInsuredPersonBeneficiary.getId())) {
						cipb.update(claimInsuredPersonBeneficiary);
						result = cipb;
					}
				}
			}
		}

		return result;
	}
	
	public void addClaimInsuredPerson(LifeClaimInsuredPerson claimInsuredPerson) {
		claimInsuredPerson.setClaimInsuredPersonLinkClaim(this);
		this.claimInsuredPerson = claimInsuredPerson;
	}

	public void addClaimSuccessor(LifeClaimSuccessor claimSuccessor) {
		claimSuccessor.setLifeClaim(this);
		this.successor = claimSuccessor;
	}
	
	public double getTotalClaimAmount() {
		double amount = 0.0;
		if (LifeClaimBeneficiaryRole.DEATHPERSON.equals(claimInsuredPerson.getBeneficiaryRole())) {
			for (LifeClaimInsuredPersonBeneficiary beneficiary : claimInsuredPersonBeneficiaryList) {
				amount = amount + beneficiary.getClaimAmount();
			}
		} else {
			amount = claimInsuredPerson.getClaimAmount();
		}
		return amount;
	}
	
	public double getTotalDeficitPremium() {
		double amount = 0.0;
		if (LifeClaimBeneficiaryRole.DEATHPERSON.equals(claimInsuredPerson.getBeneficiaryRole())) {
			for (LifeClaimInsuredPersonBeneficiary beneficiary : claimInsuredPersonBeneficiaryList) {
				amount = amount + beneficiary.getDeficitPremium();
			}
		} else {
			amount = claimInsuredPerson.getDeficitPremium();
		}
		return amount;
	}
	
	public double getTotalNetClaimAmount() {
		double amount = 0.0;
		if (LifeClaimBeneficiaryRole.DEATHPERSON.equals(claimInsuredPerson.getBeneficiaryRole())) {
			for (LifeClaimInsuredPersonBeneficiary beneficiary : claimInsuredPersonBeneficiaryList) {
				amount = amount + beneficiary.getNetClaimAmount();
			}
		} else {
			amount = claimInsuredPerson.getNetClaimAmount();
		}
		return amount;
	}
	
	public double getTotalLoanAmount() {
		double amount = 0.0;
		if (LifeClaimBeneficiaryRole.DEATHPERSON.equals(claimInsuredPerson.getBeneficiaryRole())) {
			for (LifeClaimInsuredPersonBeneficiary beneficiary : claimInsuredPersonBeneficiaryList) {
				amount = amount + beneficiary.getLoanAmount();
			}
		} else {
			amount = claimInsuredPerson.getLoanAmount();
		}
		return amount;
	}
	
	public double getTotalLoanInterest() {
		double amount = 0.0;
		if (LifeClaimBeneficiaryRole.DEATHPERSON.equals(claimInsuredPerson.getBeneficiaryRole())) {
			for (LifeClaimInsuredPersonBeneficiary beneficiary : claimInsuredPersonBeneficiaryList) {
				amount = amount + beneficiary.getLoanInterest();
			}
		} else {
			amount = claimInsuredPerson.getLoanInterest();
		}
		return amount;
	}
	
	public double getTotalRenewelAmount() {
		double amount = 0.0;
		if (LifeClaimBeneficiaryRole.DEATHPERSON.equals(claimInsuredPerson.getBeneficiaryRole())) {
			for (LifeClaimInsuredPersonBeneficiary beneficiary : claimInsuredPersonBeneficiaryList) {
				amount = amount + beneficiary.getRenewelAmount();
			}
		} else {
			amount = claimInsuredPerson.getRenewelAmount();
		}
		return amount;
	}
	
	public double getTotalRenewelInterest() {
		double amount = 0.0;
		if (LifeClaimBeneficiaryRole.DEATHPERSON.equals(claimInsuredPerson.getBeneficiaryRole())) {
			for (LifeClaimInsuredPersonBeneficiary beneficiary : claimInsuredPersonBeneficiaryList) {
				amount = amount + beneficiary.getRenewelInterest();
			}
		} else {
			amount = claimInsuredPerson.getRenewelInterest();
		}
		return amount;
	}
	
	/*
	 * public boolean isDeathClaim() { if (this.claimInsuredPerson instanceof
	 * LifeClaimDeathPerson) { return true; } return false; }
	 */

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((claimRequestId == null) ? 0 : claimRequestId.hashCode());
		result = prime * result + ((claimRole == null) ? 0 : claimRole.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((portalId == null) ? 0 : portalId.hashCode());
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		long temp;
		temp = Double.doubleToLongBits(serviceCharges);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((submittedDate == null) ? 0 : submittedDate.hashCode());
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
		LifeClaim other = (LifeClaim) obj;
		if (claimRequestId == null) {
			if (other.claimRequestId != null)
				return false;
		} else if (!claimRequestId.equals(other.claimRequestId))
			return false;
		if (claimRole == null) {
			if (other.claimRole != null)
				return false;
		} else if (!claimRole.equals(other.claimRole))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (portalId == null) {
			if (other.portalId != null)
				return false;
		} else if (!portalId.equals(other.portalId))
			return false;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
			return false;
		if (Double.doubleToLongBits(serviceCharges) != Double.doubleToLongBits(other.serviceCharges))
			return false;
		if (submittedDate == null) {
			if (other.submittedDate != null)
				return false;
		} else if (!submittedDate.equals(other.submittedDate))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
