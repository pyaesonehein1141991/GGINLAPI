package org.tat.gginl.api.dto.studentLifeDTO;



import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentLifeReponseDTO {

	private String bpmsInsuredPersonId;
	private String policyNo;

}
