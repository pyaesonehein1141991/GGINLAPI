package org.tat.gginl.api.dto.groupFarmerDTO;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.tat.gginl.api.common.Gender;
import org.tat.gginl.api.common.IdType;

import lombok.Data;

@Data
public class GroupFarmerProposalInsuredPersonDTO {
	
	@NotBlank(message = "InitialId is mandatory")
	private String initialId;

	@NotBlank(message = "BPMS InsuredPersonId is mandatory")
	private String bpmsInsuredPersonId;

	@NotNull(message = "proposedSumInsured is mandatory")
	private double proposedSumInsured;

	@NotNull(message = "proposedPremium is mandatory")
	private double proposedPremium;

	@NotNull(message = "approvedSumInsured is mandatory")
	private double approvedSumInsured;

	@NotNull(message = "approvedPremium is mandatory")
	private double approvedPremium;

	@NotNull(message = "basicTermPremium is mandatory")
	private double basicTermPremium;

	@NotNull(message = "idType is mandatory")
	private IdType idType;

	private String idNo;

	@NotBlank(message = "fatherName is mandatory")
	private String fatherName;

	@NotNull(message = "startDate is mandatory")
	private Date startDate;

	@NotNull(message = "endDate is mandatory")
	private Date endDate;

	@NotNull(message = "dateOfBirth is mandatory")
	private Date dateOfBirth;


	@NotNull(message = "gender is mandatory")
	private Gender gender;

	@NotNull(message = "residentAddress is mandatory")
	private String residentAddress;
	

	@NotNull(message = "firstName is mandatory")
	private String firstName;

	private String middleName;

	private String lastName;

	@NotBlank(message = "occupationID is mandatory")
	private String occupationID;

	private String customerID;

	@NotBlank(message = "townshipId is mandatory")
	private String townshipId;
	
	@NotNull(message = "insuredPersonBeneficiariesList is mandatory")
	private List<GroupFarmerProposalInsuredPersonBeneficiariesDTO> insuredPersonBeneficiariesList;

}
