package org.tat.gginl.api.dto.groupFarmerDTO;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.tat.gginl.api.common.Gender;
import org.tat.gginl.api.common.IdType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GroupFarmerProposalInsuredPersonBeneficiariesDTO {
	

	@ApiModelProperty(position = 0, example = "U", required = true)
	@NotBlank(message = "InitialId is mandatory")
	private String initialId;
	
	@ApiModelProperty(position = 1, example = "2019-12-16", required = true)
	@NotNull(message = "dob is mandatory")
	private Date dob;
	
	@ApiModelProperty(position = 2, example = "5", required = true)
	@NotNull(message = "percentage is mandatory")
	private float percentage;
	
	@ApiModelProperty(position = 3, example = "NRCNO", required = true)
	@NotBlank(message = "idType is mandatory")
	private IdType idType;
	
	@ApiModelProperty(position = 4, example = "123123123", required = true)
	private String idNo;

	@ApiModelProperty(position = 5, example = "MALE", required = true)
	@NotNull(message = "gender is mandatory")
	private Gender gender;

	@ApiModelProperty(position = 6, example = "Yangon", required = true)
	@NotBlank(message = "residentAddress is mandatory")
	private String residentAddress;
	
	@ApiModelProperty(position =11, example = "ISSYS011000009823001042019", required = true)
	@NotBlank(message = "townshipId is mandatory")
	private String townshipId;
	
	@ApiModelProperty(position = 7, example = "Aung", required = true)
	@NotBlank(message = "firstName is mandatory")
	private String firstName;
	
	@ApiModelProperty(position = 8, example = "Aung", required = true)
	private String middleName;
	
	@ApiModelProperty(position = 9, example = "Aung", required = true)
	private String lastName;
	
	@ApiModelProperty(position = 10, example = "ISSYS012000009552804092019", required = true)
	@NotBlank(message = "relationshipID is mandatory")
	private String relationshipID;

}
