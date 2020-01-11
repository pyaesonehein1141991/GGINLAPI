package org.tat.gginl.api.domains.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tat.gginl.api.common.Name;
import org.tat.gginl.api.common.ResidentAddress;
import org.tat.gginl.api.domains.Agent;
import org.tat.gginl.api.domains.Branch;
import org.tat.gginl.api.domains.Customer;
import org.tat.gginl.api.domains.LifePolicy;
import org.tat.gginl.api.domains.LifeProposal;
import org.tat.gginl.api.domains.Occupation;
import org.tat.gginl.api.domains.Organization;
import org.tat.gginl.api.domains.PaymentType;
import org.tat.gginl.api.domains.Product;
import org.tat.gginl.api.domains.ProposalInsuredPerson;
import org.tat.gginl.api.domains.SaleMan;
import org.tat.gginl.api.domains.SalePoint;
import org.tat.gginl.api.domains.Township;
import org.tat.gginl.api.domains.repository.AgentRepository;
import org.tat.gginl.api.domains.repository.BranchRepository;
import org.tat.gginl.api.domains.repository.CustomerRepository;
import org.tat.gginl.api.domains.repository.LifeProposalRepository;
import org.tat.gginl.api.domains.repository.OccupationRepository;
import org.tat.gginl.api.domains.repository.OrganizationRepository;
import org.tat.gginl.api.domains.repository.PaymentTypeRepository;
import org.tat.gginl.api.domains.repository.ProductRepository;
import org.tat.gginl.api.domains.repository.SaleManRepository;
import org.tat.gginl.api.domains.repository.SalePointRepository;
import org.tat.gginl.api.domains.repository.TownshipRepository;
import org.tat.gginl.api.dto.groupFarmerDTO.GroupFarmerProposalDTO;
import org.tat.gginl.api.dto.groupFarmerDTO.GroupFarmerProposalInsuredPersonDTO;

@Service
public class LifeProposalService {
	
	@Autowired
	private LifeProposalRepository lifeProposalRepo;
	
	@Autowired
	private BranchRepository branchRepo;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private OrganizationRepository organizationRepo;
	
	@Autowired
	private PaymentTypeRepository paymentTypeRepo;
	
	@Autowired
	private AgentRepository agentRepo;
	
	@Autowired
	private SaleManRepository saleManRepo;
	
	@Autowired
	private SalePointRepository salePointRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private TownshipRepository townshipRepo;
	
	@Autowired
	private OccupationRepository occupationRepo;
	
	@Value("${farmerProductId}")
	private String productId;
	
	public List<String> createGroupFarmerProposalToPolicy(GroupFarmerProposalDTO groupFarmerProposalDTO){
		
		
		
		//convert groupFarmerProposalDTO to lifeproposal
		List<LifeProposal> farmerProposalList = convertGroupFarmerProposalDTOToProposal(groupFarmerProposalDTO);
		
		//convert lifeproposal to lifepolicy
		List<LifePolicy> policyList = convertGroupFarmerProposalToPolicy(farmerProposalList);
		
		//create lifepolicy and return policynoList
		
		//carete payment process
		
		
		
		return new ArrayList<>();
	}
	
	
	private List<LifeProposal> convertGroupFarmerProposalDTOToProposal(GroupFarmerProposalDTO groupFarmerProposalDTO){
		
		Optional<Branch> branchOptional = branchRepo.findById(groupFarmerProposalDTO.getBranchId());
		Optional<Customer> referralOptional = customerRepo.findById(groupFarmerProposalDTO.getReferralID());
		Optional<Organization> organizationOptional = organizationRepo.findById(groupFarmerProposalDTO.getOrganizationID());
		Optional<PaymentType> paymentTypeOptional = paymentTypeRepo.findById(groupFarmerProposalDTO.getPaymentTypeId());
		Optional<Agent> agentOptional = agentRepo.findById(groupFarmerProposalDTO.getAgentID());
		Optional<SaleMan> saleManOptional = saleManRepo.findById(groupFarmerProposalDTO.getSaleManId());
		Optional<SalePoint> salePointOptional = salePointRepo.findById(groupFarmerProposalDTO.getSalePointId());
		
		List<LifeProposal> lifeProposalList = new ArrayList<>();
		groupFarmerProposalDTO.getProposalInsuredPersonList().forEach(insuredPerson->{
			LifeProposal lifeProposal = new LifeProposal();
			lifeProposal.setSubmittedDate(groupFarmerProposalDTO.getSubmittedDate());
			lifeProposal.setBranch(branchOptional.get());
			lifeProposal.setReferral(referralOptional.get());
			lifeProposal.setOrganization(organizationOptional.get());
			lifeProposal.setPaymentType(paymentTypeOptional.get());
			lifeProposal.setAgent(agentOptional.get());
			lifeProposal.setSaleMan(saleManOptional.get());
			lifeProposal.setSalePoint(salePointOptional.get());
			lifeProposal.getProposalInsuredPersonList().add(createInsuredPerson(insuredPerson));
			
			lifeProposalList.add(lifeProposal);
			
		});
		
		return new ArrayList<>();
	}
	
	private List<LifePolicy> convertGroupFarmerProposalToPolicy(List<LifeProposal> farmerProposalList){
		
		
		return new ArrayList<>();
	}
	
	private ProposalInsuredPerson createInsuredPerson(GroupFarmerProposalInsuredPersonDTO dto) {
		Optional<Product> productOptional = productRepo.findById(productId);
		Optional<Township> townshipOptional = townshipRepo.findById(dto.getTownshipId());
		Optional<Occupation> occupationOptional = occupationRepo.findById(dto.getOccupationID());
		Optional<Customer> customerOptional = customerRepo.findById(dto.getCustomerID());
		
		ResidentAddress residentAddress = new ResidentAddress();
		residentAddress.setResidentAddress(dto.getResidentAddress());
		residentAddress.setResidentTownship(townshipOptional.get());
		
		Name name = new Name();
		name.setFirstName(dto.getFirstName());
		name.setMiddleName(dto.getMiddleName());
		name.setLastName(dto.getLastName());
		
		
		ProposalInsuredPerson insuredPerson = new ProposalInsuredPerson();
		insuredPerson.setProduct(productOptional.get());
		insuredPerson.setInitialId(dto.getInitialId());
		insuredPerson.setBpmsInsuredPersonId(dto.getBpmsInsuredPersonId());
		insuredPerson.setProposedSumInsured(dto.getProposedSumInsured());
		insuredPerson.setProposedPremium(dto.getProposedPremium());
		insuredPerson.setApprovedSumInsured(dto.getApprovedSumInsured());
		insuredPerson.setApprovedPremium(dto.getApprovedPremium());
		insuredPerson.setBasicTermPremium(dto.getBasicTermPremium());
		insuredPerson.setIdType(dto.getIdType());
		insuredPerson.setIdNo(dto.getIdNo());
		insuredPerson.setFatherName(dto.getFatherName());
		insuredPerson.setStartDate(dto.getStartDate());
		insuredPerson.setEndDate(dto.getEndDate());
		insuredPerson.setDateOfBirth(dto.getDateOfBirth());
		insuredPerson.setGender(dto.getGender());
		insuredPerson.setResidentAddress(residentAddress);
		insuredPerson.setName(name);
		insuredPerson.setOccupation(occupationOptional.get());
		insuredPerson.setCustomer(customerOptional.get());
		
		//TODO create beneficiery
		
		return insuredPerson;
	}

}
