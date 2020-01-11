package org.tat.gginl.api.dto.groupFarmerDTO;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class GroupFarmerProposalDTO {
	@NotNull(message = "SubmittedDate is mandatory")
	private Date submittedDate;

	@NotBlank(message = "branchId is mandatory")
	private String branchId;

	private String referralID;

	private String organizationID;

	@NotBlank(message = "paymentTypeId is mandatory")
	private String paymentTypeId;

	@NotBlank(message = "agentID is mandatory")
	private String agentID;

	private String saleManId;

	@NotNull(message = "proposalInsuredPersonList is mandatory")
	private List<GroupFarmerProposalInsuredPersonDTO> proposalInsuredPersonList;

	@NotBlank(message = "salePointId is mandatory")
	private String salePointId;
	
	@NotBlank(message = "userId is mandatory")
	private String userId;

}
