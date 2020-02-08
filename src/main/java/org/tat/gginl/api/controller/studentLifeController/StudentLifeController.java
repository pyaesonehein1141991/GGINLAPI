package org.tat.gginl.api.controller.studentLifeController;

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
import org.tat.gginl.api.dto.studentLifeDTO.StudentLifeProposalDTO;
import org.tat.gginl.api.dto.studentLifeDTO.StudentLifeReponseDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/studnetlife")
@Api(tags = "StudnetLife Proposal")
public class StudentLifeController {
	
	@Autowired
	private LifeProposalService lifeProposalService;

	@PostMapping("/submitproposal")

	@ApiResponses(value = { 
			@ApiResponse(code = 400, message = "Something went wrong"), 
			@ApiResponse(code = 403, message = "Access denied"), 
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	public ResponseDTO<Object> submitproposal(@Valid @RequestBody StudentLifeProposalDTO studentLifeProposalDTO) {
		List<LifePolicy> policyList = new ArrayList<>();

		// create farmer proposal
		policyList = lifeProposalService.createStudentLifeProposalToPolicy(studentLifeProposalDTO);

		// create response object
		List<StudentLifeReponseDTO> responseList = new ArrayList<>();

		policyList.forEach(policy -> {
			StudentLifeReponseDTO dto = StudentLifeReponseDTO.builder()
					.bpmsInsuredPersonId(policy.getPolicyInsuredPersonList().get(0).getBpmsInsuredPersonId())
					.policyNo(policy.getPolicyNo()).build();

			responseList.add(dto);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().responseStatus("Success!").responseBody(responseList)
				.build();

		return responseDTO;
	}

}
