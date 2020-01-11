package org.tat.gginl.api.dto.groupFarmerDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupFarmerResponseDTO {
	
	private String bpmsInsuredPersonId;
	private String policyNo;

}
