package org.tat.gginl.api.dto.groupFarmerDTO;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.tat.gginl.api.configuration.DateHandler;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GroupFarmerProposalDTO {
	@ApiModelProperty(position = 0, example = "2019-12-16", required = true)
	@NotNull(message = "SubmittedDate is mandatory")
	@JsonDeserialize(using = DateHandler.class)
	private Date submittedDate;

	@ApiModelProperty(position = 3, example = "BANCH00000000000000129032018", required = true)
	@NotBlank(message = "branchId is mandatory")
	private String branchId;

	@ApiModelProperty(position = 5, example = "ISSYS001000005575112092016")
	private String referralID;

	@ApiModelProperty(position = 6, example = "ISSYS033000009784102042019")
	private String organizationID;

	@ApiModelProperty(position = 7, example = "ISSYS0090001000000000429032013", required = true)
	@NotBlank(message = "paymentTypeId is mandatory")
	private String paymentTypeId;

	@ApiModelProperty(position = 8, example = "ISSYS002000009755110052019", required = true)
	@NotBlank(message = "agentID is mandatory")
	private String agentID;

	@ApiModelProperty(position = 9, example = "ISSYS022000009634116052019")
	private String saleManId;

	@Valid
	@ApiModelProperty(position = 10, required = true)
	@NotNull(message = "proposalInsuredPersonList is mandatory")
	private List<GroupFarmerProposalInsuredPersonDTO> proposalInsuredPersonList;

	@ApiModelProperty(position = 11, example = "11", required = true)
	@NotBlank(message = "salePointId is mandatory")
	private String salePointId;
	
	@ApiModelProperty(position = 12, example = "TRF", required = true)
	private String paymentChannel;
	
	@ApiModelProperty(position = 13, example = "ISSYS010005000000021118072016", required = true)
	private String toBank;
	
	@ApiModelProperty(position = 14, example = "ISSYS0100001000000000713032013", required = true)
	private String fromBank;
	
	@ApiModelProperty(position = 15, example = "CH34345345", required = true)
	private String chequeNo;
	
	@ApiModelProperty(position = 16, example = "1234", required = true)
	@NotBlank(message = "userId is mandatory")
	private String userId;


}
