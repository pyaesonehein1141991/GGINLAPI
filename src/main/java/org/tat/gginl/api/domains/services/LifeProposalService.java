package org.tat.gginl.api.domains.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tat.gginl.api.common.COACode;
import org.tat.gginl.api.common.CommonCreateAndUpateMarks;
import org.tat.gginl.api.common.Name;
import org.tat.gginl.api.common.ResidentAddress;
import org.tat.gginl.api.common.TLFBuilder;
import org.tat.gginl.api.common.TranCode;
import org.tat.gginl.api.common.Utils;
import org.tat.gginl.api.common.emumdata.AgentCommissionEntryType;
import org.tat.gginl.api.common.emumdata.DoubleEntry;
import org.tat.gginl.api.common.emumdata.PaymentChannel;
import org.tat.gginl.api.common.emumdata.PolicyReferenceType;
import org.tat.gginl.api.common.emumdata.ProposalType;
import org.tat.gginl.api.common.emumdata.Status;
import org.tat.gginl.api.domains.Agent;
import org.tat.gginl.api.domains.AgentCommission;
import org.tat.gginl.api.domains.Branch;
import org.tat.gginl.api.domains.Customer;
import org.tat.gginl.api.domains.InsuredPersonBeneficiaries;
import org.tat.gginl.api.domains.LifePolicy;
import org.tat.gginl.api.domains.LifeProposal;
import org.tat.gginl.api.domains.Occupation;
import org.tat.gginl.api.domains.Organization;
import org.tat.gginl.api.domains.Payment;
import org.tat.gginl.api.domains.PaymentType;
import org.tat.gginl.api.domains.Product;
import org.tat.gginl.api.domains.ProposalInsuredPerson;
import org.tat.gginl.api.domains.RelationShip;
import org.tat.gginl.api.domains.SaleMan;
import org.tat.gginl.api.domains.SalePoint;
import org.tat.gginl.api.domains.TLF;
import org.tat.gginl.api.domains.Township;
import org.tat.gginl.api.domains.repository.AgentCommissionRepository;
import org.tat.gginl.api.domains.repository.AgentRepository;
import org.tat.gginl.api.domains.repository.BranchRepository;
import org.tat.gginl.api.domains.repository.CustomerRepository;
import org.tat.gginl.api.domains.repository.LifePolicyRepository;
import org.tat.gginl.api.domains.repository.LifeProposalRepository;
import org.tat.gginl.api.domains.repository.OccupationRepository;
import org.tat.gginl.api.domains.repository.OrganizationRepository;
import org.tat.gginl.api.domains.repository.PaymentRepository;
import org.tat.gginl.api.domains.repository.PaymentTypeRepository;
import org.tat.gginl.api.domains.repository.ProductRepository;
import org.tat.gginl.api.domains.repository.RelationshipRepository;
import org.tat.gginl.api.domains.repository.SaleManRepository;
import org.tat.gginl.api.domains.repository.SalePointRepository;
import org.tat.gginl.api.domains.repository.TLFRepository;
import org.tat.gginl.api.domains.repository.TownshipRepository;
import org.tat.gginl.api.dto.groupFarmerDTO.GroupFarmerProposalDTO;
import org.tat.gginl.api.dto.groupFarmerDTO.GroupFarmerProposalInsuredPersonBeneficiariesDTO;
import org.tat.gginl.api.dto.groupFarmerDTO.GroupFarmerProposalInsuredPersonDTO;

@Service
public class LifeProposalService {

	@Autowired
	private LifeProposalRepository lifeProposalRepo;

	@Autowired
	private LifePolicyRepository lifePolicyRepo;

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

	@Autowired
	private RelationshipRepository relationshipRepo;

	@Autowired
	private ICustomIdGenerator customIdRepo;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private TLFRepository tlfRepository;

	@Autowired
	private AgentCommissionRepository agentCommissionRepo;

	@Value("${farmerProductId}")
	private String productId;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicy> createGroupFarmerProposalToPolicy(GroupFarmerProposalDTO groupFarmerProposalDTO) {
		// convert groupFarmerProposalDTO to lifeproposal
		List<LifeProposal> farmerProposalList = convertGroupFarmerProposalDTOToProposal(groupFarmerProposalDTO);

		// convert lifeproposal to lifepolicy
		List<LifePolicy> policyList = convertGroupFarmerProposalToPolicy(farmerProposalList);

		// create lifepolicy and return policynoList
		policyList = lifePolicyRepo.saveAll(policyList);

		List<Payment> paymentList = convertGroupFarmerPolicyToPayment(policyList);
		paymentRepository.saveAll(paymentList);

		CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
		recorder.setCreatedDate(new Date());

		if (null != groupFarmerProposalDTO.getAgentID()) {

			List<AgentCommission> agentcommissionList = convertGroupFarmerPolicyToAgentCommission(policyList);
			agentCommissionRepo.saveAll(agentcommissionList);

		}

		List<TLF> tlfList = convertGroupFarmerPolicyToTLF(policyList);
		tlfRepository.saveAll(tlfList);
		return policyList;
	}

	private List<LifeProposal> convertGroupFarmerProposalDTOToProposal(GroupFarmerProposalDTO groupFarmerProposalDTO) {

		Optional<Branch> branchOptional = branchRepo.findById(groupFarmerProposalDTO.getBranchId());
		Optional<Customer> referralOptional = customerRepo.findById(groupFarmerProposalDTO.getReferralID());
		Optional<Organization> organizationOptional = organizationRepo
				.findById(groupFarmerProposalDTO.getOrganizationID());
		Optional<PaymentType> paymentTypeOptional = paymentTypeRepo.findById(groupFarmerProposalDTO.getPaymentTypeId());
		Optional<Agent> agentOptional = agentRepo.findById(groupFarmerProposalDTO.getAgentID());
		Optional<SaleMan> saleManOptional = saleManRepo.findById(groupFarmerProposalDTO.getSaleManId());
		Optional<SalePoint> salePointOptional = salePointRepo.findById(groupFarmerProposalDTO.getSalePointId());

		List<LifeProposal> lifeProposalList = new ArrayList<>();
		groupFarmerProposalDTO.getProposalInsuredPersonList().forEach(insuredPerson -> {

			LifeProposal lifeProposal = new LifeProposal();
			lifeProposal.setProposalType(ProposalType.UNDERWRITING);
			lifeProposal.setSubmittedDate(groupFarmerProposalDTO.getSubmittedDate());
			lifeProposal.setBranch(branchOptional.get());
			lifeProposal.setReferral(referralOptional.get());
			lifeProposal.setOrganization(organizationOptional.get());
			lifeProposal.setPaymentType(paymentTypeOptional.get());
			lifeProposal.setAgent(agentOptional.get());
			lifeProposal.setSaleMan(saleManOptional.get());
			lifeProposal.setSalePoint(salePointOptional.get());
			lifeProposal.getProposalInsuredPersonList().add(createInsuredPerson(insuredPerson));
			String proposalNo = customIdRepo.getNextId("FARMER_LIFE_PROPOSAL_NO", null);
			lifeProposal.setProposalNo(proposalNo);
			lifeProposal.setPrefix("ISLIF001");
			lifeProposalList.add(lifeProposal);

		});

		return lifeProposalList;
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

		String insPersonCodeNo = customIdRepo.getNextId("LIFE_INSUREDPERSON_CODENO_ID_GEN", null);
		insuredPerson.setInsPersonCodeNo(insPersonCodeNo);
		insuredPerson.setPrefix("ISLIF008");
		dto.getInsuredPersonBeneficiariesList().forEach(beneficiary -> {
			insuredPerson.getInsuredPersonBeneficiariesList().add(createInsuredPersonBeneficiareis(beneficiary));
		});

		return insuredPerson;
	}

	private InsuredPersonBeneficiaries createInsuredPersonBeneficiareis(
			GroupFarmerProposalInsuredPersonBeneficiariesDTO dto) {

		Optional<Township> townshipOptional = townshipRepo.findById(dto.getTownshipId());
		Optional<RelationShip> relationshipOptional = relationshipRepo.findById(dto.getRelationshipID());

		ResidentAddress residentAddress = new ResidentAddress();
		residentAddress.setResidentAddress(dto.getResidentAddress());

		residentAddress.setResidentTownship(townshipOptional.get());

		Name name = new Name();
		name.setFirstName(dto.getFirstName());
		name.setMiddleName(dto.getMiddleName());
		name.setLastName(dto.getLastName());

		InsuredPersonBeneficiaries beneficiary = new InsuredPersonBeneficiaries();
		beneficiary.setInitialId(dto.getInitialId());
		beneficiary.setDateOfBirth(dto.getDob());
		beneficiary.setPercentage(dto.getPercentage());
		beneficiary.setIdType(dto.getIdType());
		beneficiary.setIdNo(dto.getIdNo());
		beneficiary.setGender(dto.getGender());
		beneficiary.setResidentAddress(residentAddress);
		beneficiary.setName(name);
		if (relationshipOptional.isPresent()) {
			beneficiary.setRelationship(relationshipOptional.get());
		}
		String beneficiaryNo = customIdRepo.getNextId("LIFE_BENEFICIARY_ID_GEN", null);
		beneficiary.setBeneficiaryNo(beneficiaryNo);
		beneficiary.setPrefix("ISLIF004");
		return beneficiary;
	}

	private List<LifePolicy> convertGroupFarmerProposalToPolicy(List<LifeProposal> farmerProposalList) {
		List<LifePolicy> policyList = new ArrayList<>();
		farmerProposalList.forEach(proposal -> {
			LifePolicy policy = new LifePolicy(proposal);
			String policyNo = customIdRepo.getNextId("FARMER_LIFE_POLICY_NO", null);
			policy.setPolicyNo(policyNo);
			policy.setActivedPolicyStartDate(policy.getPolicyInsuredPersonList().get(0).getStartDate());
			policy.setActivedPolicyEndDate(policy.getPolicyInsuredPersonList().get(0).getEndDate());
			policyList.add(policy);
		});
		return policyList;
	}

	private List<Payment> convertGroupFarmerPolicyToPayment(List<LifePolicy> farmerPolicyList) {
		List<Payment> paymentList = new ArrayList<Payment>();
		farmerPolicyList.forEach(lifePolicy -> {
			Payment payment = new Payment();
			double rate = 1.0;
			CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
			recorder.setCreatedDate(new Date());
			String receiptNo = customIdRepo.getNextId("CASH_RECEIPT_ID_GEN", null);
			payment.setReceiptNo(receiptNo);
			payment.setPaymentType(lifePolicy.getPaymentType());
			payment.setPaymentChannel(PaymentChannel.CASHED);
			payment.setReferenceType(PolicyReferenceType.LIFE_POLICY);
			payment.setConfirmDate(new Date());
			payment.setPaymentDate(new Date());
			payment.setReferenceNo(lifePolicy.getId());
			payment.setBasicPremium(lifePolicy.getTotalBasicTermPremium());
			payment.setAddOnPremium(lifePolicy.getTotalAddOnTermPremium());
			payment.setFromTerm(1);
			payment.setToTerm(1);
			payment.setCur("KYT");
			payment.setRate(rate);
			payment.setComplete(true);
			payment.setAmount(payment.getNetPremium());
			payment.setHomeAmount(payment.getNetPremium());
			payment.setHomePremium(payment.getBasicPremium());
			payment.setHomeAddOnPremium(payment.getAddOnPremium());
			payment.setCommonCreateAndUpateMarks(recorder);

			paymentList.add(payment);

		});
		return paymentList;
	}

	// agent Commission
	private List<AgentCommission> convertGroupFarmerPolicyToAgentCommission(List<LifePolicy> farmerPolicyList) {
		List<AgentCommission> agentCommissionList = new ArrayList<AgentCommission>();
		/* get agent commission of each policy */
		farmerPolicyList.forEach(lifePolicy -> {
			Product product = lifePolicy.getPolicyInsuredPersonList().get(0).getProduct();
			double commissionPercent = product.getFirstCommission();
			Payment payment = paymentRepository.findByPaymentReferenceNo(lifePolicy.getId());
			double rate = payment.getRate();
			double firstAgentCommission = lifePolicy.getAgentCommission();
			agentCommissionList.add(new AgentCommission(lifePolicy.getId(), PolicyReferenceType.FARMER_POLICY,
					lifePolicy.getAgent(), firstAgentCommission, new Date(), payment.getReceiptNo(),
					lifePolicy.getTotalTermPremium(), commissionPercent, AgentCommissionEntryType.UNDERWRITING, rate,
					(rate * firstAgentCommission), "KYT", (rate * lifePolicy.getTotalTermPremium())));

		});

		return agentCommissionList;
	}

	private List<TLF> convertGroupFarmerPolicyToTLF(List<LifePolicy> farmerPolicyList) {
		List<TLF> TLFList = new ArrayList<TLF>();
		String accountCode = "Farmer_Premium";
		for (LifePolicy lifePolicy : farmerPolicyList) {
			Payment payment = paymentRepository.findByPaymentReferenceNo(lifePolicy.getId());
			TLF tlf1 = addNewTLF_For_CashDebitForPremium(payment,
					lifePolicy.getCustomer() == null ? lifePolicy.getOrganization().getId()
							: lifePolicy.getCustomer().getId(),
					lifePolicy.getBranch(), payment.getReceiptNo(), false, "KYT", lifePolicy.getSalePoint(),
					lifePolicy.getPolicyNo());
			TLFList.add(tlf1);
			TLF tlf2 = addNewTLF_For_PremiumCredit(payment,
					lifePolicy.getCustomer() == null ? lifePolicy.getOrganization().getId()
							: lifePolicy.getCustomer().getId(),
					lifePolicy.getBranch(), accountCode, payment.getReceiptNo(), false, "KYT",
					lifePolicy.getSalePoint(), lifePolicy.getPolicyNo());
			TLFList.add(tlf2);
			if (lifePolicy.getAgent() != null) {
				double firstAgentCommission = lifePolicy.getAgentCommission();
				AgentCommission ac = new AgentCommission(lifePolicy.getId(), PolicyReferenceType.FARMER_POLICY,
						lifePolicy.getAgent(), firstAgentCommission, new Date());
				TLF tlf3 = addNewTLF_For_AgentCommissionDr(ac, false, lifePolicy.getBranch(), payment, payment.getId(),
						false, "KYT", lifePolicy.getSalePoint(), lifePolicy.getPolicyNo());
				TLFList.add(tlf3);
				TLF tlf4 = addNewTLF_For_AgentCommissionCredit(ac, false, lifePolicy.getBranch(), payment,
						payment.getId(), false, "KYT", lifePolicy.getSalePoint(), lifePolicy.getPolicyNo());
				TLFList.add(tlf4);
			}
		}
		return TLFList;
	}

	public TLF addNewTLF_For_CashDebitForPremium(Payment payment, String customerId, Branch branch, String tlfNo,
			boolean isRenewal, String currencyCode, SalePoint salePoint, String policyNo) {
		TLF tlf = null;
		try {
			CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
			recorder.setCreatedDate(new Date());
			double totalNetPremium = 0;
			double homeAmount = 0;
			String coaCode = null;
			totalNetPremium = payment.getNetPremium();
			homeAmount = totalNetPremium;
			// TLF COAID
			if (PaymentChannel.CASHED.equals(payment.getPaymentChannel())) {
				coaCode = paymentRepository.findCheckOfAccountNameByCode(COACode.CASH, branch.getBranchCode(),
						currencyCode);

				TLFBuilder tlfBuilder = new TLFBuilder(DoubleEntry.DEBIT, homeAmount, customerId,
						branch.getBranchCode(), coaCode, tlfNo, getNarrationPremium(payment, isRenewal), payment,
						isRenewal);
				tlf = tlfBuilder.getTLFInstance();
				tlf.setPolicyNo(policyNo);
				tlf.setSalePoint(salePoint);
				tlf.setCommonCreateAndUpateMarks(recorder);
				tlf.setPaid(true);
				// setIDPrefixForInsert(tlf);
				tlf.setPaymentChannel(payment.getPaymentChannel());

			}
			// paymentDAO.insertTLF(tlf);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return tlf;
	}

	private String getNarrationPremium(Payment payment, boolean isRenewal) {
		StringBuffer nrBf = new StringBuffer();
		String customerName = "";
		String premiumString = "";
		int totalInsuredPerson = 0;
		double si = 0.0;
		String unit = "";
		double premium = 0.0;

		nrBf.append("Being amount of ");
		switch (payment.getReferenceType()) {
		case FARMER_POLICY:
		case LIFE_POLICY:
			nrBf.append(" Life Premium ");
			LifePolicy lifePolicy = lifePolicyRepo.getOne(payment.getReferenceNo());
			si = lifePolicy.getTotalSumInsured();
			premium = (lifePolicy.isEndorsementApplied()) ? lifePolicy.getTotalEndorsementPremium()
					: lifePolicy.getTotalPremium();
			customerName = lifePolicy.getCustomerName();
			totalInsuredPerson = lifePolicy.getInsuredPersonInfo().size() > 1 ? lifePolicy.getInsuredPersonInfo().size()
					: 0;
			break;
		default:
			break;
		}
		nrBf.append(premiumString);
		nrBf.append(" received by ");
		nrBf.append(payment.getReceiptNo());
		nrBf.append(" from ");
		nrBf.append(customerName);
		nrBf.append(" for Sum Insured ");
		nrBf.append(Utils.getCurrencyFormatString(si));
		nrBf.append(" and the premium amount of ");
		nrBf.append(Utils.getCurrencyFormatString(premium));
		nrBf.append(". ");

		return nrBf.toString();
	}

	public TLF addNewTLF_For_PremiumCredit(Payment payment, String customerId, Branch branch, String accountName,
			String tlfNo, boolean isRenewal, String currenyCode, SalePoint salePoint, String policyNo) {
		TLF tlf = null;

		try {
			CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
			recorder.setCreatedDate(new Date());
			double homeAmount = payment.getNetPremium();
			if (isRenewal) {
				homeAmount = payment.getRenewalNetPremium();
			}

			String coaCode = paymentRepository.findCheckOfAccountNameByCode(accountName, branch.getBranchCode(),
					currenyCode);
			TLFBuilder tlfBuilder = new TLFBuilder(DoubleEntry.CREDIT, homeAmount, customerId, branch.getBranchCode(),
					coaCode, tlfNo, getNarrationPremium(payment, isRenewal), payment, isRenewal);
			tlf = tlfBuilder.getTLFInstance();
			tlf.setPaymentChannel(payment.getPaymentChannel());
			tlf.setSalePoint(salePoint);
			tlf.setPolicyNo(policyNo);
			tlf.setCommonCreateAndUpateMarks(recorder);
			tlf.setPaid(true);
			// setIDPrefixForInsert(tlf);
			/// paymentDAO.insertTLF(tlf);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tlf;
	}

	public TLF addNewTLF_For_AgentCommissionDr(AgentCommission ac, boolean coInsureance, Branch branch, Payment payment,
			String eno, boolean isRenewal, String currencyCode, SalePoint salePoint, String policyNo) {
		TLF tlf = new TLF();
		try {
			CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
			recorder.setCreatedDate(new Date());
			String receiptNo = payment.getReceiptNo();
			String coaCode = null;
			String coaCodeMI = null;
			String accountName = null;
			double ownCommission = 0.0;
			Product product;

			switch (ac.getReferenceType()) {
			case FARMER_POLICY:
				coaCode = COACode.FARMER_AGENT_COMMISSION;
				break;
			default:
				break;
			}
			String cur = payment.getCur();
			double rate = payment.getRate();
			String narration = getNarrationAgent(payment, ac, isRenewal);

			accountName = paymentRepository.findCheckOfAccountNameByCode(coaCode, branch.getBranchCode(), currencyCode);
			ownCommission = Utils.getTwoDecimalPoint(ac.getCommission());

			TLFBuilder tlfBuilder = new TLFBuilder(TranCode.TRDEBIT, Status.TDV, ownCommission, ac.getAgent().getId(),
					branch.getBranchCode(), accountName, receiptNo, narration, eno, ac.getReferenceNo(),
					ac.getReferenceType(), isRenewal, cur, rate);
			tlf = tlfBuilder.getTLFInstance();
			tlf.setPaymentChannel(payment.getPaymentChannel());
			tlf.setSalePoint(salePoint);
			tlf.setPolicyNo(policyNo);
			tlf.setAgentTransaction(true);
			tlf.setCommonCreateAndUpateMarks(recorder);
			// setIDPrefixForInsert(tlf);
			// paymentDAO.insertTLF(tlf);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tlf;
	}

	private String getNarrationAgent(Payment payment, AgentCommission agentCommission, boolean isRenewal) {
		StringBuffer nrBf = new StringBuffer();
		double commission = 0.0;
		String agentName = "";
		String insuranceName = "";
		// Agent Commission payable for Fire Insurance(Product Name), Received
		// No to Agent Name for commission amount of Amount
		nrBf.append("Agent Commission payable for ");
		switch (payment.getReferenceType()) {

		case FARMER_POLICY:
			insuranceName = "Life Insurance, ";
			break;
		default:
			break;
		}
		agentName = agentCommission.getAgent() == null ? "" : agentCommission.getAgent().getFullName();
		commission = agentCommission.getCommission();
		nrBf.append(insuranceName);
		nrBf.append(payment.getReceiptNo());
		nrBf.append(" to ");
		nrBf.append(agentName);
		nrBf.append(" for commission amount of ");
		nrBf.append(Utils.getCurrencyFormatString(commission));
		nrBf.append(".");

		return nrBf.toString();
	}

	public TLF addNewTLF_For_AgentCommissionCredit(AgentCommission ac, boolean coInsureance, Branch branch,
			Payment payment, String eno, boolean isRenewal, String currencyCode, SalePoint salePoint, String policyNo) {
		TLF tlf = new TLF();
		try {
			CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
			recorder.setCreatedDate(new Date());
			String receiptNo = payment.getReceiptNo();
			String coaCode = null;
			String accountName = null;
			double commission = 0.0;

			switch (ac.getReferenceType()) {
			case FARMER_POLICY:
				coaCode = COACode.FARMER_AGENT_PAYABLE;
				break;
			default:
				break;
			}

			accountName = paymentRepository.findCheckOfAccountNameByCode(coaCode, branch.getBranchCode(), currencyCode);
			commission = Utils.getTwoDecimalPoint(ac.getCommission());
			String narration = getNarrationAgent(payment, ac, isRenewal);
			String cur = payment.getCur();
			double rate = payment.getRate();
			TLFBuilder tlfBuilder = new TLFBuilder(TranCode.TRCREDIT, Status.TCV, commission, ac.getAgent().getId(),
					branch.getBranchCode(), accountName, receiptNo, narration, eno, ac.getReferenceNo(),
					ac.getReferenceType(), isRenewal, cur, rate);
			tlf = tlfBuilder.getTLFInstance();
			// setIDPrefixForInsert(tlf);
			tlf.setPaymentChannel(payment.getPaymentChannel());
			tlf.setSalePoint(salePoint);
			tlf.setPolicyNo(policyNo);
			tlf.setAgentTransaction(true);
			tlf.setCommonCreateAndUpateMarks(recorder);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return tlf;
	}

}
