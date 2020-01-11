package org.tat.gginl.api.dto.groupFarmerDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.tat.gginl.api.common.Gender;
import org.tat.gginl.api.common.IdType;

import lombok.Data;

@Data
public class GroupFarmerProposalInsuredPersonBeneficiariesDTO {
	
	@NotBlank(message = "InitialId is mandatory")
	private String initialId;
	
	@NotNull(message = "dob is mandatory")
	private String dob;
	
	@NotNull(message = "percentage is mandatory")
	private float percentage;
	
	@NotBlank(message = "idType is mandatory")
	private IdType idType;
	
	private String idNo;

	@NotNull(message = "gender is mandatory")
	private Gender gender;

	@NotBlank(message = "residentAddress is mandatory")
	private String residentAddress;
	
	@NotBlank(message = "townshipId is mandatory")
	private String townshipId;
	
	@NotBlank(message = "firstName is mandatory")
	private String firstName;
	
	private String middleName;
	
	private String lastName;
	
	@NotBlank(message = "relationshipID is mandatory")
	private String relationshipID;

}
