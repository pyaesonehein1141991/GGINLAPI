package org.tat.gginl.api.controller.groupFarmer;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.gginl.api.domains.LifePolicy;
import org.tat.gginl.api.domains.services.LifeProposalService;
import org.tat.gginl.api.dto.ResponseDTO;
import org.tat.gginl.api.dto.groupFarmerDTO.GroupFarmerProposalDTO;
import org.tat.gginl.api.dto.groupFarmerDTO.GroupFarmerResponseDTO;

@RestController
@RequestMapping("/groupfarmer")
public class GroupFarmerController {

	@Autowired
	private LifeProposalService lifeProposalService;

	@PostMapping("/submitproposal")
	public ResponseDTO<Object> submitproposal(@Valid @RequestBody GroupFarmerProposalDTO groupFarmerProposalDTO) {
		List<LifePolicy> policyList = new ArrayList<>();
		
	
		
		// create farmer proposal
		policyList = lifeProposalService.createGroupFarmerProposalToPolicy(groupFarmerProposalDTO);

		// create response object

		List<GroupFarmerResponseDTO> responseList = new ArrayList<>();

		policyList.forEach(policy -> {
			GroupFarmerResponseDTO dto = GroupFarmerResponseDTO.builder()
					.bpmsInsuredPersonId(policy.getPolicyInsuredPersonList().get(0).getBpmsInsuredPersonId())
					.policyNo(policy.getPolicyNo())
					.build();

			responseList.add(dto);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().responseStatus("Success!").responseBody(responseList)
				.build();

		return responseDTO;
	}

}
