package org.tat.gginl.api.domains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.tat.gginl.api.common.ICD10;
import org.tat.gginl.api.common.MedicalClaim;
import org.tat.gginl.api.common.MedicalClaimRole;
import org.tat.gginl.api.common.TableName;

@Entity
@Table(name = TableName.MEDICATIONCLAIM)
@DiscriminatorValue(value = MedicalClaimRole.MEDICATION_CLAIM)
@NamedQueries(value = { @NamedQuery(name = "MedicationClaim.findAll", query = "SELECT m FROM MedicationClaim m "),
		@NamedQuery(name = "MedicationClaim.findById", query = "SELECT m FROM MedicationClaim m WHERE m.id = :id") })
public class MedicationClaim extends MedicalClaim implements Serializable {

	private static final long serialVersionUID = -958711836726721157L;

	@Column(name = "RECEIVED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date receivedDate;

	@Column(name = "MEDICATIONFEE")
	private double medicationFee;

	@Column(name = "MEDICATION")
	private String medication;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEDICATIONREASONID", referencedColumnName = "ID")
	private ICD10 medicationReason;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "HOLDERID", referencedColumnName = "ID")
	private List<Attachment> attachmentList;

	private int noOfTimes;

	public MedicationClaim() {
		super();
	}

	public double getMedicationFee() {
		return medicationFee;
	}

	public void setMedicationFee(double medicationFee) {
		this.medicationFee = medicationFee;
	}

	public String getMedication() {
		return medication;
	}

	public void setMedication(String medication) {
		this.medication = medication;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public ICD10 getMedicationReason() {
		return medicationReason;
	}

	public void setMedicationReason(ICD10 medicationReason) {
		this.medicationReason = medicationReason;
	}

	public List<Attachment> getAttachmentList() {
		if (attachmentList == null) {
			attachmentList = new ArrayList<Attachment>();
		}
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public void addAttachment(Attachment attachment) {
		if (attachmentList == null) {
			attachmentList = new ArrayList<Attachment>();
		}
		attachmentList.add(attachment);
	}

	public int getNoOfTimes() {
		return noOfTimes;
	}

	public void setNoOfTimes(int noOfTimes) {
		this.noOfTimes = noOfTimes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((medication == null) ? 0 : medication.hashCode());
		long temp;
		temp = Double.doubleToLongBits(medicationFee);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((medicationReason == null) ? 0 : medicationReason.hashCode());
		result = prime * result + noOfTimes;
		result = prime * result + ((receivedDate == null) ? 0 : receivedDate.hashCode());
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
		MedicationClaim other = (MedicationClaim) obj;
		if (medication == null) {
			if (other.medication != null)
				return false;
		} else if (!medication.equals(other.medication))
			return false;
		if (Double.doubleToLongBits(medicationFee) != Double.doubleToLongBits(other.medicationFee))
			return false;
		if (medicationReason == null) {
			if (other.medicationReason != null)
				return false;
		} else if (!medicationReason.equals(other.medicationReason))
			return false;
		if (noOfTimes != other.noOfTimes)
			return false;
		if (receivedDate == null) {
			if (other.receivedDate != null)
				return false;
		} else if (!receivedDate.equals(other.receivedDate))
			return false;
		return true;
	}

}
