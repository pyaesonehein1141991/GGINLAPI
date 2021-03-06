package org.tat.gginl.api.domains.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tat.gginl.api.common.COACode;
import org.tat.gginl.api.common.CommonCreateAndUpateMarks;
import org.tat.gginl.api.common.DateUtils;
import org.tat.gginl.api.common.Name;
import org.tat.gginl.api.common.PolicyInsuredPerson;
import org.tat.gginl.api.common.PolicyInsuredPersonBeneficiaries;
import org.tat.gginl.api.common.ResidentAddress;
import org.tat.gginl.api.common.TLFBuilder;
import org.tat.gginl.api.common.TranCode;
import org.tat.gginl.api.common.Utils;
import org.tat.gginl.api.common.emumdata.AgentCommissionEntryType;
import org.tat.gginl.api.common.emumdata.DoubleEntry;
import org.tat.gginl.api.common.emumdata.Gender;
import org.tat.gginl.api.common.emumdata.IdType;
import org.tat.gginl.api.common.emumdata.PaymentChannel;
import org.tat.gginl.api.common.emumdata.PolicyReferenceType;
import org.tat.gginl.api.common.emumdata.ProposalType;
import org.tat.gginl.api.common.emumdata.Status;
import org.tat.gginl.api.domains.Agent;
import org.tat.gginl.api.domains.AgentCommission;
import org.tat.gginl.api.domains.Bank;
import org.tat.gginl.api.domains.Branch;
import org.tat.gginl.api.domains.Customer;
import org.tat.gginl.api.domains.GroupFarmerProposal;
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
import org.tat.gginl.api.domains.repository.BankRepository;
import org.tat.gginl.api.domains.repository.BranchRepository;
import org.tat.gginl.api.domains.repository.CustomerRepository;
import org.tat.gginl.api.domains.repository.GroupFarmerRepository;
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
import org.tat.gginl.api.dto.groupFarmerDTO.FarmerProposalDTO;
import org.tat.gginl.api.dto.groupFarmerDTO.GroupFarmerProposalInsuredPersonBeneficiariesDTO;
import org.tat.gginl.api.dto.groupFarmerDTO.GroupFarmerProposalInsuredPersonDTO;
import org.tat.gginl.api.dto.studentLifeDTO.StudentLifeProposalDTO;
import org.tat.gginl.api.dto.studentLifeDTO.StudentLifeProposalInsuredPersonDTO;

@Service
public class LifeProposalService {

  Logger logger = LoggerFactory.getLogger(this.getClass());
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

  @Autowired
  private BankRepository bankRepository;

  @Autowired
  private GroupFarmerRepository groupFarmerRepository;

  @Value("${farmerProductId}")
  private String productId;

  @Transactional(propagation = Propagation.REQUIRED)
  public List<LifePolicy> createGroupFarmerProposalToPolicy(
      FarmerProposalDTO groupFarmerProposalDTO) {
    try {
      GroupFarmerProposal groupFarmerProposal = createGroupFarmerProposal(groupFarmerProposalDTO);
      groupFarmerProposal = groupFarmerRepository.save(groupFarmerProposal);

      List<LifeProposal> farmerProposalList =
          convertGroupFarmerProposalDTOToProposal(groupFarmerProposalDTO);
      for (LifeProposal proposal : farmerProposalList) {
        proposal.setGroupFarmerProposal(groupFarmerProposal);
      }

      // convert lifeproposal to lifepolicy
      List<LifePolicy> policyList = convertGroupFarmerProposalToPolicy(farmerProposalList);

      // create lifepolicy and return policynoList
      policyList = lifePolicyRepo.saveAll(policyList);

      List<Payment> paymentList =
          convertGroupFarmerToPayment(groupFarmerProposal, groupFarmerProposalDTO);
      paymentRepository.saveAll(paymentList);

      if (null != groupFarmerProposalDTO.getAgentID()) {
        List<AgentCommission> agentcommissionList =
            convertGroupFarmerToAgentCommission(groupFarmerProposal);
        CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
        recorder.setCreatedDate(new Date());
        agentcommissionList.forEach(agent -> {
          agent.setRecorder(recorder);
        });
        agentCommissionRepo.saveAll(agentcommissionList);
      }

      List<TLF> tlfList = convertGroupFarmerToTLF(groupFarmerProposal);
      tlfRepository.saveAll(tlfList);
      return policyList;

    } catch (Exception e) {
      logger.error("JOEERROR:" + e.getMessage(), e);
      throw e;
    }

  }

  private GroupFarmerProposal createGroupFarmerProposal(FarmerProposalDTO groupFarmerProposalDTO) {
    try {
      Optional<Branch> branchOptional = branchRepo.findById(groupFarmerProposalDTO.getBranchId());
      Optional<PaymentType> paymentTypeOptional =
          paymentTypeRepo.findById(groupFarmerProposalDTO.getPaymentTypeId());
      Optional<Agent> agentOptional = agentRepo.findById(groupFarmerProposalDTO.getAgentID());
      Optional<SaleMan> saleManOptional =
          saleManRepo.findById(groupFarmerProposalDTO.getSaleManId());
      Optional<Customer> referralOptional =
          customerRepo.findById(groupFarmerProposalDTO.getReferralID());
      Optional<Organization> organizationOptional =
          organizationRepo.findById(groupFarmerProposalDTO.getOrganizationID());
      Optional<SalePoint> salePointOptional =
          salePointRepo.findById(groupFarmerProposalDTO.getSalePointId());

      GroupFarmerProposal groupFarmerProposal = new GroupFarmerProposal();
      groupFarmerProposal.setSubmittedDate(groupFarmerProposalDTO.getSubmittedDate());
      groupFarmerProposal.setProposalType(ProposalType.UNDERWRITING);
      groupFarmerProposal.setEndDate(groupFarmerProposalDTO.getEndDate());
      groupFarmerProposal.setNoOfInsuredPerson(groupFarmerProposalDTO.getNoOfInsuredPerson());
      groupFarmerProposal.setTotalSI(groupFarmerProposalDTO.getTotalSI());
      groupFarmerProposal.setPaymentComplete(true);
      groupFarmerProposal.setProcessComplete(true);
      double totalPremium = (groupFarmerProposal.getTotalSI() / 100) * 1;
      groupFarmerProposal.setPremium(totalPremium);
      if (branchOptional.isPresent()) {
        groupFarmerProposal.setBranch(branchOptional.get());
      }

      if (agentOptional.isPresent()) {
        groupFarmerProposal.setAgent(agentOptional.get());
      }
      groupFarmerProposal.setPaymentType(paymentTypeOptional.get());

      if (saleManOptional.isPresent()) {
        groupFarmerProposal.setSaleMan(saleManOptional.get());
      }
      if (referralOptional.isPresent()) {
        groupFarmerProposal.setReferral(referralOptional.get());
      }
      if (organizationOptional.isPresent()) {
        groupFarmerProposal.setOrganization(organizationOptional.get());
      }
      if (salePointOptional.isPresent()) {
        groupFarmerProposal.setSalePoint(salePointOptional.get());
      }

      CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
      recorder.setCreatedDate(new Date());
      groupFarmerProposal.setCommonCreateAndUpateMarks(recorder);
      String proposalNo = customIdRepo.getNextId("GROUPFARMER_LIFE_PROPOSAL_NO", null);
      groupFarmerProposal.setProposalNo(proposalNo);
      return groupFarmerProposal;
    } catch (Exception e) {
      logger.error("JOEERROR:" + e.getMessage());
      throw e;
    }
  }

  private List<LifeProposal> convertGroupFarmerProposalDTOToProposal(
      FarmerProposalDTO groupFarmerProposalDTO) {

    Optional<Branch> branchOptional = branchRepo.findById(groupFarmerProposalDTO.getBranchId());
    Optional<Customer> referralOptional =
        customerRepo.findById(groupFarmerProposalDTO.getReferralID());
    Optional<Organization> organizationOptional =
        organizationRepo.findById(groupFarmerProposalDTO.getOrganizationID());
    Optional<PaymentType> paymentTypeOptional =
        paymentTypeRepo.findById(groupFarmerProposalDTO.getPaymentTypeId());
    Optional<Agent> agentOptional = agentRepo.findById(groupFarmerProposalDTO.getAgentID());
    Optional<SaleMan> saleManOptional = saleManRepo.findById(groupFarmerProposalDTO.getSaleManId());
    Optional<SalePoint> salePointOptional =
        salePointRepo.findById(groupFarmerProposalDTO.getSalePointId());
    List<LifeProposal> lifeProposalList = new ArrayList<>();
    try {
      groupFarmerProposalDTO.getProposalInsuredPersonList().forEach(insuredPerson -> {
        LifeProposal lifeProposal = new LifeProposal();
        if (groupFarmerProposalDTO.getPaymentChannel().equalsIgnoreCase("TRF")) {
          lifeProposal.setPaymentChannel(PaymentChannel.TRANSFER);
          lifeProposal.setToBank(groupFarmerProposalDTO.getToBank());
          lifeProposal.setFromBank(groupFarmerProposalDTO.getFromBank());
        } else if (groupFarmerProposalDTO.getPaymentChannel().equalsIgnoreCase("CSH")) {
          lifeProposal.setPaymentChannel(PaymentChannel.CASHED);
        } else if (groupFarmerProposalDTO.getPaymentChannel().equalsIgnoreCase("CHQ")) {
          lifeProposal.setPaymentChannel(PaymentChannel.CHEQUE);
          lifeProposal.setChequeNo(groupFarmerProposalDTO.getChequeNo());
          lifeProposal.setToBank(groupFarmerProposalDTO.getToBank());
          lifeProposal.setFromBank(groupFarmerProposalDTO.getFromBank());
        } else if (groupFarmerProposalDTO.getPaymentChannel().equalsIgnoreCase("RCV")) {
          lifeProposal.setPaymentChannel(PaymentChannel.SUNDRY);
          lifeProposal.setToBank(groupFarmerProposalDTO.getToBank());
          lifeProposal.setFromBank(groupFarmerProposalDTO.getFromBank());
        }
        lifeProposal.setComplete(true);

        CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
        recorder.setCreatedDate(new Date());
        lifeProposal.setRecorder(recorder);

        lifeProposal.setProposalType(ProposalType.UNDERWRITING);
        lifeProposal.setSubmittedDate(groupFarmerProposalDTO.getSubmittedDate());
        lifeProposal.setBranch(branchOptional.get());
        if (referralOptional.isPresent()) {
          lifeProposal.setReferral(referralOptional.get());
        }
        if (organizationOptional.isPresent()) {
          lifeProposal.setOrganization(organizationOptional.get());
        }
        if (paymentTypeOptional.isPresent()) {

          lifeProposal.setPaymentType(paymentTypeOptional.get());
        }

        if (agentOptional.isPresent()) {

          lifeProposal.setAgent(agentOptional.get());
        }

        if (saleManOptional.isPresent()) {
          lifeProposal.setSaleMan(saleManOptional.get());
        }
        lifeProposal.setSalePoint(salePointOptional.get());
        lifeProposal.getProposalInsuredPersonList().add(createInsuredPerson(insuredPerson));
        String proposalNo = customIdRepo.getNextId("FARMER_LIFE_PROPOSAL_NO", null);
        lifeProposal.setProposalNo(proposalNo);
        lifeProposal.setPrefix("ISLIF001");
        lifeProposalList.add(lifeProposal);

      });


    } catch (Exception e) {
      logger.error("JOEERROR:" + e.getMessage(), e);
      throw e;
    }


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
    insuredPerson.setIdType(IdType.valueOf(dto.getIdType()));
    insuredPerson.setIdNo(dto.getIdNo());
    insuredPerson.setFatherName(dto.getFatherName());
    insuredPerson.setStartDate(dto.getStartDate());
    insuredPerson.setEndDate(dto.getEndDate());
    insuredPerson.setDateOfBirth(dto.getDateOfBirth());
    insuredPerson.setAge(DateUtils.getAgeForNextYear(dto.getDateOfBirth()));
    insuredPerson.setPeriodMonth(12);
    insuredPerson.setGender(Gender.valueOf(dto.getGender()));
    insuredPerson.setResidentAddress(residentAddress);
    insuredPerson.setName(name);
    CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
    recorder.setCreatedDate(new Date());
    insuredPerson.setRecorder(recorder);
    if (occupationOptional.isPresent()) {
      insuredPerson.setOccupation(occupationOptional.get());
    }
    if (customerOptional.isPresent()) {
      insuredPerson.setCustomer(customerOptional.get());
    } else {
      insuredPerson.setCustomer(createNewCustomer(insuredPerson));
      insuredPerson.setNewCustomer(true);
    }


    String insPersonCodeNo = customIdRepo.getNextId("LIFE_INSUREDPERSON_CODENO_ID_GEN", null);
    insuredPerson.setInsPersonCodeNo(insPersonCodeNo);
    insuredPerson.setPrefix("ISLIF008");
    dto.getInsuredPersonBeneficiariesList().forEach(beneficiary -> {
      insuredPerson.getInsuredPersonBeneficiariesList()
          .add(createInsuredPersonBeneficiareis(beneficiary));
    });



    return insuredPerson;
  }

  private Customer createNewCustomer(ProposalInsuredPerson dto) {
    Customer customer = new Customer();
    customer.setInitialId(dto.getInitialId());
    customer.setFatherName(dto.getFatherName());
    customer.setIdNo(dto.getIdNo());
    customer.setDateOfBirth(dto.getDateOfBirth());
    customer.setGender(dto.getGender());
    customer.setIdType(dto.getIdType());
    customer.setResidentAddress(dto.getResidentAddress());
    customer.setName(dto.getName());
    customer.setOccupation(dto.getOccupation());
    customer.setRecorder(dto.getRecorder());

    customer = customerRepo.save(customer);
    return customer;
  }

  private InsuredPersonBeneficiaries createInsuredPersonBeneficiareis(
      GroupFarmerProposalInsuredPersonBeneficiariesDTO dto) {

    Optional<Township> townshipOptional = townshipRepo.findById(dto.getTownshipId());
    Optional<RelationShip> relationshipOptional =
        relationshipRepo.findById(dto.getRelationshipID());

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
    beneficiary.setIdType(IdType.valueOf(dto.getIdType()));
    beneficiary.setIdNo(dto.getIdNo());
    beneficiary.setGender(Gender.valueOf(dto.getGender()));
    beneficiary.setResidentAddress(residentAddress);
    beneficiary.setName(name);
    if (relationshipOptional.isPresent()) {
      beneficiary.setRelationship(relationshipOptional.get());
    }
    String beneficiaryNo = customIdRepo.getNextId("LIFE_BENEFICIARY_ID_GEN", null);
    beneficiary.setBeneficiaryNo(beneficiaryNo);
    beneficiary.setPrefix("ISLIF004");
    CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
    recorder.setCreatedDate(new Date());
    beneficiary.setRecorder(recorder);
    return beneficiary;
  }

  private List<LifePolicy> convertGroupFarmerProposalToPolicy(
      List<LifeProposal> farmerProposalList) {
    List<LifePolicy> policyList = new ArrayList<>();
    farmerProposalList.forEach(proposal -> {
      LifePolicy policy = new LifePolicy(proposal);
      String policyNo = customIdRepo.getNextId("FARMER_LIFE_POLICY_NO", null);
      policy.setPolicyNo(policyNo);
      policy.setFromBank(proposal.getFromBank());
      policy.setToBank(proposal.getToBank());
      policy.setChequeNo(proposal.getChequeNo());
      policy.setPaymentChannel(proposal.getPaymentChannel());
      policy.setActivedPolicyStartDate(policy.getPolicyInsuredPersonList().get(0).getStartDate());
      policy.setActivedPolicyEndDate(policy.getPolicyInsuredPersonList().get(0).getEndDate());
      policy.setCommenmanceDate(proposal.getSubmittedDate());
      policy.setLastPaymentTerm(1);
      CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
      recorder.setCreatedDate(new Date());
      policy.setRecorder(recorder);
      for (PolicyInsuredPerson insuredPerson : policy.getPolicyInsuredPersonList()) {
        insuredPerson.setRecorder(recorder);
        for (PolicyInsuredPersonBeneficiaries beneficiary : insuredPerson
            .getPolicyInsuredPersonBeneficiariesList()) {
          beneficiary.setRecorder(recorder);
        }
      }
      policyList.add(policy);
    });
    return policyList;
  }

  private List<Payment> convertGroupFarmerPolicyToPayment(List<LifePolicy> farmerPolicyList) {
    List<Payment> paymentList = new ArrayList<Payment>();

    farmerPolicyList.forEach(lifePolicy -> {
      Optional<Bank> fromBankOptional = Optional.empty();
      Optional<Bank> toBankOptional = Optional.empty();
      if (lifePolicy.getFromBank() != null) {
        fromBankOptional = bankRepository.findById(lifePolicy.getFromBank());
      }
      if (lifePolicy.getToBank() != null) {
        toBankOptional = bankRepository.findById(lifePolicy.getToBank());
      }
      Payment payment = new Payment();
      double rate = 1.0;
      String receiptNo = "";
      CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
      recorder.setCreatedDate(new Date());
      if (PaymentChannel.CASHED.equals(lifePolicy.getPaymentChannel())) {
        receiptNo = customIdRepo.getNextId("CASH_RECEIPT_ID_GEN", null);
      } else if (PaymentChannel.CHEQUE.equals(lifePolicy.getPaymentChannel())) {
        payment.setPO(true);
        receiptNo = customIdRepo.getNextId("CHEQUE_RECEIPT_ID_GEN", null);
      } else if (PaymentChannel.TRANSFER.equals(lifePolicy.getPaymentChannel())) {
        receiptNo = customIdRepo.getNextId("TRANSFER_RECEIPT_ID_GEN", null);
      } else {
        receiptNo = customIdRepo.getNextId("CHEQUE_RECEIPT_ID_GEN", null);
        payment.setPO(true);
      }

      payment.setReceiptNo(receiptNo);
      payment.setChequeNo(lifePolicy.getChequeNo());
      payment.setPaymentType(lifePolicy.getPaymentType());
      payment.setPaymentChannel(lifePolicy.getPaymentChannel());
      payment.setReferenceType(PolicyReferenceType.FARMER_POLICY);
      payment.setConfirmDate(new Date());
      payment.setPaymentDate(new Date());
      if (toBankOptional.isPresent()) {
        payment.setAccountBank(toBankOptional.get());
      }
      if (fromBankOptional.isPresent()) {
        payment.setBank(fromBankOptional.get());
      }
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


  private List<Payment> convertGroupFarmerToPayment(GroupFarmerProposal groupFarmerProposal,
      FarmerProposalDTO farmerProposalDTO) {
    List<Payment> paymentList = new ArrayList<Payment>();
    try {
      Optional<Bank> fromBankOptional = Optional.empty();
      Optional<Bank> toBankOptional = Optional.empty();
      if (farmerProposalDTO.getFromBank() != null) {
        fromBankOptional = bankRepository.findById(farmerProposalDTO.getFromBank());
      }
      if (farmerProposalDTO.getToBank() != null) {
        toBankOptional = bankRepository.findById(farmerProposalDTO.getToBank());
      }
      Payment payment = new Payment();
      double rate = 1.0;
      String receiptNo = "";
      CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
      recorder.setCreatedDate(new Date());
      if (farmerProposalDTO.getPaymentChannel().equalsIgnoreCase("CSH")) {
        receiptNo = customIdRepo.getNextId("CASH_RECEIPT_ID_GEN", null);
        payment.setPaymentChannel(PaymentChannel.CASHED);
      } else if (farmerProposalDTO.getPaymentChannel().equalsIgnoreCase("CHQ")) {
        payment.setPO(true);
        payment.setPaymentChannel(PaymentChannel.CHEQUE);
        receiptNo = customIdRepo.getNextId("CHEQUE_RECEIPT_ID_GEN", null);
        payment.setChequeNo(farmerProposalDTO.getChequeNo());
      } else if (farmerProposalDTO.getPaymentChannel().equalsIgnoreCase("TRF")) {
        payment.setPoNo(farmerProposalDTO.getChequeNo());
        payment.setPaymentChannel(PaymentChannel.TRANSFER);
        receiptNo = customIdRepo.getNextId("TRANSFER_RECEIPT_ID_GEN", null);
      } else if (farmerProposalDTO.getPaymentChannel().equalsIgnoreCase("RCV")) {
        payment.setPaymentChannel(PaymentChannel.SUNDRY);
        receiptNo = customIdRepo.getNextId("CHEQUE_RECEIPT_ID_GEN", null);
        payment.setPO(true);
      }
      payment.setReceiptNo(receiptNo);
      payment.setPaymentType(groupFarmerProposal.getPaymentType());
      payment.setReferenceType(PolicyReferenceType.GROUP_FARMER_PROPOSAL);
      payment.setConfirmDate(new Date());
      payment.setPaymentDate(new Date());
      if (toBankOptional.isPresent()) {
        payment.setAccountBank(toBankOptional.get());
      }
      if (fromBankOptional.isPresent()) {
        payment.setBank(fromBankOptional.get());
      }
      payment.setReferenceNo(groupFarmerProposal.getId());
      payment.setBasicPremium(groupFarmerProposal.getPremium());
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
    } catch (Exception e) {
      e.printStackTrace();
    }
    return paymentList;
  }

  // agent Commission
  private List<AgentCommission> convertGroupFarmerPolicyToAgentCommission(
      List<LifePolicy> farmerPolicyList) {
    List<AgentCommission> agentCommissionList = new ArrayList<AgentCommission>();
    /* get agent commission of each policy */
    farmerPolicyList.forEach(lifePolicy -> {
      Product product = lifePolicy.getPolicyInsuredPersonList().get(0).getProduct();
      double commissionPercent = product.getFirstCommission();
      Payment payment = paymentRepository.findByPaymentReferenceNo(lifePolicy.getId());
      double rate = payment.getRate();
      double firstAgentCommission = lifePolicy.getAgentCommission();
      agentCommissionList.add(new AgentCommission(lifePolicy.getId(),
          PolicyReferenceType.FARMER_POLICY, lifePolicy.getAgent(), firstAgentCommission,
          new Date(), payment.getReceiptNo(), lifePolicy.getTotalTermPremium(), commissionPercent,
          AgentCommissionEntryType.UNDERWRITING, rate, (rate * firstAgentCommission), "KYT",
          (rate * lifePolicy.getTotalTermPremium())));

    });

    return agentCommissionList;
  }

  private List<AgentCommission> convertGroupFarmerToAgentCommission(
      GroupFarmerProposal groupFarmerProposal) {
    List<AgentCommission> agentCommissionList = new ArrayList<AgentCommission>();
    try {
      /* get agent commission of each policy */
      double commissionPercent = 10.0;
      Payment payment = paymentRepository.findByPaymentReferenceNo(groupFarmerProposal.getId());
      double rate = payment.getRate();
      double firstAgentCommission = groupFarmerProposal.getAgentCommission();
      agentCommissionList.add(new AgentCommission(groupFarmerProposal.getId(),
          PolicyReferenceType.GROUP_FARMER_PROPOSAL, groupFarmerProposal.getAgent(),
          firstAgentCommission, new Date(), payment.getReceiptNo(),
          groupFarmerProposal.getPremium(), commissionPercent,
          AgentCommissionEntryType.UNDERWRITING, rate, (rate * firstAgentCommission), "KYT",
          (rate * groupFarmerProposal.getPremium())));
    } catch (Exception e) {
      e.printStackTrace();
    }

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

      if (lifePolicy.getPaymentChannel().equals(PaymentChannel.CHEQUE)
          || lifePolicy.getPaymentChannel().equals(PaymentChannel.SUNDRY)) {
        String customerId = lifePolicy.getCustomer() == null ? lifePolicy.getOrganization().getId()
            : lifePolicy.getCustomer().getId();
        TLF tlf3 =
            addNewTLF_For_PremiumDebitForRCVAndCHQ(payment, customerId, lifePolicy.getBranch(),
                payment.getAccountBank().getAcode(), false, payment.getReceiptNo(), true, false,
                "KYT", lifePolicy.getSalePoint(), lifePolicy.getPolicyNo());
        TLFList.add(tlf3);
        TLF tlf4 = addNewTLF_For_CashCreditForPremiumForRCVAndCHQ(payment, customerId,
            lifePolicy.getBranch(), false, payment.getReceiptNo(), true, false, "KYT",
            lifePolicy.getSalePoint(), lifePolicy.getPolicyNo());
        TLFList.add(tlf4);
      }

      if (lifePolicy.getAgent() != null) {
        double firstAgentCommission = lifePolicy.getAgentCommission();
        AgentCommission ac =
            new AgentCommission(lifePolicy.getId(), PolicyReferenceType.FARMER_POLICY,
                lifePolicy.getAgent(), firstAgentCommission, new Date());
        TLF tlf5 = addNewTLF_For_AgentCommissionDr(ac, false, lifePolicy.getBranch(), payment,
            payment.getId(), false, "KYT", lifePolicy.getSalePoint(), lifePolicy.getPolicyNo());
        TLFList.add(tlf5);
        TLF tlf6 = addNewTLF_For_AgentCommissionCredit(ac, false, lifePolicy.getBranch(), payment,
            payment.getId(), false, "KYT", lifePolicy.getSalePoint(), lifePolicy.getPolicyNo());
        TLFList.add(tlf6);
      }
    }
    return TLFList;
  }

  private List<TLF> convertGroupFarmerToTLF(GroupFarmerProposal proposal) {
    List<TLF> TLFList = new ArrayList<TLF>();
    try {
      String accountCode = "Farmer_Premium";
      Payment payment = paymentRepository.findByPaymentReferenceNo(proposal.getId());

      TLF tlf1 = addNewTLF_For_CashDebitForPremium(payment, proposal.getOrganization().getId(),
          proposal.getBranch(), payment.getReceiptNo(), false, "KYT", proposal.getSalePoint(),
          proposal.getProposalNo());
      TLFList.add(tlf1);
      TLF tlf2 = addNewTLF_For_PremiumCredit(payment, proposal.getOrganization().getId(),
          proposal.getBranch(), accountCode, payment.getReceiptNo(), false, "KYT",
          proposal.getSalePoint(), proposal.getProposalNo());
      TLFList.add(tlf2);

      if (payment.getPaymentChannel().equals(PaymentChannel.CHEQUE)
          || payment.getPaymentChannel().equals(PaymentChannel.SUNDRY)) {
        String customerId = proposal.getOrganization().getId();
        TLF tlf3 = addNewTLF_For_PremiumDebitForRCVAndCHQ(payment, customerId, proposal.getBranch(),
            payment.getAccountBank().getAcode(), false, payment.getReceiptNo(), true, false, "KYT",
            proposal.getSalePoint(), proposal.getProposalNo());
        TLFList.add(tlf3);
        TLF tlf4 = addNewTLF_For_CashCreditForPremiumForRCVAndCHQ(payment, customerId,
            proposal.getBranch(), false, payment.getReceiptNo(), true, false, "KYT",
            proposal.getSalePoint(), proposal.getProposalNo());
        TLFList.add(tlf4);
      }

      if (proposal.getAgent() != null) {
        double firstAgentCommission = proposal.getAgentCommission();
        AgentCommission ac =
            new AgentCommission(proposal.getId(), PolicyReferenceType.GROUP_FARMER_PROPOSAL,
                proposal.getAgent(), firstAgentCommission, new Date());
        TLF tlf5 = addNewTLF_For_AgentCommissionDr(ac, false, proposal.getBranch(), payment,
            payment.getId(), false, "KYT", proposal.getSalePoint(), proposal.getProposalNo());
        TLFList.add(tlf5);
        TLF tlf6 = addNewTLF_For_AgentCommissionCredit(ac, false, proposal.getBranch(), payment,
            payment.getId(), false, "KYT", proposal.getSalePoint(), proposal.getProposalNo());
        TLFList.add(tlf6);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return TLFList;
  }


  public TLF addNewTLF_For_CashDebitForPremium(Payment payment, String customerId, Branch branch,
      String tlfNo, boolean isRenewal, String currencyCode, SalePoint salePoint, String policyNo) {
    TLF tlf = null;
    try {
      CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
      recorder.setCreatedDate(new Date());
      double totalNetPremium = 0;
      double homeAmount = 0;
      String coaCode = null;
      totalNetPremium = payment.getNetPremium();
      homeAmount = totalNetPremium;
      if (PaymentChannel.CASHED.equals(payment.getPaymentChannel())) {
        coaCode = paymentRepository.findCheckOfAccountNameByCode(COACode.CASH,
            branch.getBranchCode(), currencyCode);
      } else if (PaymentChannel.TRANSFER.equals(payment.getPaymentChannel())) {
        coaCode = payment.getAccountBank() == null
            ? paymentRepository.findCheckOfAccountNameByCode(COACode.CHEQUE, branch.getBranchCode(),
                currencyCode)
            : payment.getAccountBank().getAcode();
      } else if (PaymentChannel.CHEQUE.equals(payment.getPaymentChannel())) {
        String coaCodeType = "";
        switch (payment.getReferenceType()) {
          case FARMER_POLICY:
          case GROUP_FARMER_PROPOSAL:
            coaCodeType = COACode.FARMER_PAYMENT_ORDER;
            break;
          default:
            break;
        }
        coaCode = paymentRepository.findCheckOfAccountNameByCode(coaCodeType,
            branch.getBranchCode(), currencyCode);
      } else if (PaymentChannel.SUNDRY.equals(payment.getPaymentChannel())) {
        String coaCodeType = "";
        switch (payment.getReferenceType()) {
          case FARMER_POLICY:
          case GROUP_FARMER_PROPOSAL:
            coaCodeType = COACode.FARMER_SUNDRY;
            break;
          default:
            break;
        }
        coaCode = paymentRepository.findCheckOfAccountNameByCode(coaCodeType,
            branch.getBranchCode(), currencyCode);
      }
      TLFBuilder tlfBuilder =
          new TLFBuilder(DoubleEntry.DEBIT, homeAmount, customerId, branch.getBranchCode(), coaCode,
              tlfNo, getNarrationPremium(payment, isRenewal), payment, isRenewal);
      tlf = tlfBuilder.getTLFInstance();
      tlf.setPolicyNo(policyNo);
      tlf.setSalePoint(salePoint);
      tlf.setCommonCreateAndUpateMarks(recorder);
      tlf.setPaid(true);
      // setIDPrefixForInsert(tlf);
      tlf.setPaymentChannel(payment.getPaymentChannel());

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
      case GROUP_FARMER_PROPOSAL:
      case LIFE_POLICY:
        nrBf.append(" Life Premium ");
        // LifePolicy lifePolicy = lifePolicyRepo.getOne(payment.getReferenceNo());
        GroupFarmerProposal groupFarmerProposal =
            groupFarmerRepository.getOne(payment.getReferenceNo());
        si = groupFarmerProposal.getTotalSI();
        premium = groupFarmerProposal.getPremium();
        customerName = groupFarmerProposal.getOrganization().getName();
        totalInsuredPerson = groupFarmerProposal.getNoOfInsuredPerson();
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
    nrBf.append(" and for total number of insured person ");
    nrBf.append(Integer.toString(totalInsuredPerson));
    nrBf.append(" and the premium amount of ");
    nrBf.append(Utils.getCurrencyFormatString(premium));

    nrBf.append(". ");

    return nrBf.toString();
  }

  public TLF addNewTLF_For_PremiumCredit(Payment payment, String customerId, Branch branch,
      String accountName, String tlfNo, boolean isRenewal, String currenyCode, SalePoint salePoint,
      String policyNo) {
    TLF tlf = null;

    try {
      CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
      recorder.setCreatedDate(new Date());
      double homeAmount = payment.getNetPremium();
      if (isRenewal) {
        homeAmount = payment.getRenewalNetPremium();
      }

      String coaCode = paymentRepository.findCheckOfAccountNameByCode(accountName,
          branch.getBranchCode(), currenyCode);
      TLFBuilder tlfBuilder =
          new TLFBuilder(DoubleEntry.CREDIT, homeAmount, customerId, branch.getBranchCode(),
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

  public TLF addNewTLF_For_AgentCommissionDr(AgentCommission ac, boolean coInsureance,
      Branch branch, Payment payment, String eno, boolean isRenewal, String currencyCode,
      SalePoint salePoint, String policyNo) {
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
        case GROUP_FARMER_PROPOSAL:
        case FARMER_POLICY:
          coaCode = COACode.FARMER_AGENT_COMMISSION;
          break;
        default:
          break;
      }
      String cur = payment.getCur();
      double rate = payment.getRate();
      String narration = getNarrationAgent(payment, ac, isRenewal);

      accountName = paymentRepository.findCheckOfAccountNameByCode(coaCode, branch.getBranchCode(),
          currencyCode);
      ownCommission = Utils.getTwoDecimalPoint(ac.getCommission());

      TLFBuilder tlfBuilder = new TLFBuilder(TranCode.TRDEBIT, Status.TDV, ownCommission,
          ac.getAgent().getId(), branch.getBranchCode(), accountName, receiptNo, narration, eno,
          ac.getReferenceNo(), ac.getReferenceType(), isRenewal, cur, rate);
      tlf = tlfBuilder.getTLFInstance();
      tlf.setPaymentChannel(payment.getPaymentChannel());
      tlf.setSalePoint(salePoint);
      tlf.setPolicyNo(policyNo);
      tlf.setAgentTransaction(true);
      tlf.setPaid(true);
      tlf.setCommonCreateAndUpateMarks(recorder);
      // setIDPrefixForInsert(tlf);
      // paymentDAO.insertTLF(tlf);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return tlf;
  }

  public TLF addNewTLF_For_CashCreditForPremiumForRCVAndCHQ(Payment payment, String customerId,
      Branch branch, boolean isEndorse, String tlfNo, boolean isClearing, boolean isRenewal,
      String currencyCode, SalePoint salePoint, String policyNo) {
    TLF tlf = new TLF();
    try {

      double totalNetPremium = 0;
      double homeAmount = 0;
      String narration = null;
      String coaCode = null;
      String enoNo = payment.getReceiptNo();
      Product product = null;
      CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
      recorder.setCreatedDate(new Date());
      if (isRenewal) {
        totalNetPremium = totalNetPremium + payment.getRenewalNetPremium();
      } else {
        totalNetPremium = totalNetPremium + payment.getNetPremium();
      }

      totalNetPremium = totalNetPremium * (isEndorse ? -1 : 1);

      // TLF Home Amount
      homeAmount = totalNetPremium;

      // TLF COAID
      switch (payment.getPaymentChannel()) {
        case TRANSFER: {
          if (payment.getAccountBank() == null) {
            coaCode = paymentRepository.findCheckOfAccountNameByCode(COACode.CHEQUE,
                branch.getBranchCode(), currencyCode);
          } else {
            coaCode = payment.getAccountBank().getAcode();
          }
        }
          break;
        case CASHED:
          coaCode = paymentRepository.findCheckOfAccountNameByCode(COACode.CASH,
              branch.getBranchCode(), currencyCode);
          break;
        case CHEQUE: {
          String coaCodeType = "";
          switch (payment.getReferenceType()) {
            case GROUP_FARMER_PROPOSAL:
            case FARMER_POLICY:
              coaCodeType = COACode.FARMER_PAYMENT_ORDER;
              break;
            default:
              break;
          }
          coaCode = paymentRepository.findCheckOfAccountNameByCode(coaCodeType,
              branch.getBranchCode(), currencyCode);
        }
          break;
        case SUNDRY: {
          String coaCodeType = "";
          switch (payment.getReferenceType()) {
            case GROUP_FARMER_PROPOSAL:
            case FARMER_POLICY:
              coaCodeType = COACode.FARMER_SUNDRY;
              break;
            default:
              break;
          }
          coaCode = paymentRepository.findCheckOfAccountNameByCode(coaCodeType,
              branch.getBranchCode(), currencyCode);
        }
          break;
      }
      // TLF Narration
      narration = "Cash refund for " + enoNo;
      TLFBuilder tlfBuilder = new TLFBuilder(DoubleEntry.CREDIT, homeAmount, customerId,
          branch.getBranchCode(), coaCode, tlfNo, narration, payment, isRenewal);
      tlf = tlfBuilder.getTLFInstance();
      tlf.setClearing(isClearing);
      tlf.setPaymentChannel(payment.getPaymentChannel());
      tlf.setSalePoint(salePoint);
      tlf.setCommonCreateAndUpateMarks(recorder);
      // tlf.setPolicyNo(policyNo);
      // setIDPrefixForInsert(tlf);
      /// paymentDAO.insertTLF(tlf);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return tlf;
  }

  public TLF addNewTLF_For_PremiumDebitForRCVAndCHQ(Payment payment, String customerId,
      Branch branch, String accountName, boolean isEndorse, String tlfNo, boolean isClearing,
      boolean isRenewal, String currencyCode, SalePoint salePoint, String policyNo) {
    TLF tlf = new TLF();
    try {
      double netPremium = 0.0;
      CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
      recorder.setCreatedDate(new Date());
      if (isRenewal) {
        netPremium = payment.getRenewalNetPremium();
      } else {
        netPremium = payment.getNetPremium();
      }

      netPremium = netPremium * (isEndorse ? -1 : 1);
      double homeAmount = 0;
      // TLF Home Amount
      homeAmount = netPremium;

      // TLF COAID
      String coaCode = "";
      if (payment.getAccountBank() == null) {
        coaCode = paymentRepository.findCheckOfAccountNameByCode(accountName,
            branch.getBranchCode(), currencyCode);
      } else {
        coaCode = payment.getAccountBank().getAcode();
      }

      TLFBuilder tlfBuilder =
          new TLFBuilder(DoubleEntry.DEBIT, homeAmount, customerId, branch.getBranchCode(), coaCode,
              tlfNo, getNarrationPremium(payment, isRenewal), payment, isRenewal);
      tlf = tlfBuilder.getTLFInstance();
      tlf.setPaymentChannel(payment.getPaymentChannel());
      tlf.setSalePoint(salePoint);
      tlf.setClearing(isClearing);
      tlf.setCommonCreateAndUpateMarks(recorder);
      // tlf.setPolicyNo(policyNo);
      // setIDPrefixForInsert(tlf);
      // paymentDAO.insertTLF(tlf);

    } catch (Exception e) {
      e.printStackTrace();;
    }
    return tlf;
  }

  private String getNarrationAgent(Payment payment, AgentCommission agentCommission,
      boolean isRenewal) {
    StringBuffer nrBf = new StringBuffer();
    double commission = 0.0;
    String agentName = "";
    String insuranceName = "";
    // Agent Commission payable for Fire Insurance(Product Name), Received
    // No to Agent Name for commission amount of Amount
    nrBf.append("Agent Commission payable for ");
    switch (payment.getReferenceType()) {
      case GROUP_FARMER_PROPOSAL:
      case FARMER_POLICY:
        insuranceName = "";
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

  public TLF addNewTLF_For_AgentCommissionCredit(AgentCommission ac, boolean coInsureance,
      Branch branch, Payment payment, String eno, boolean isRenewal, String currencyCode,
      SalePoint salePoint, String policyNo) {
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
        case GROUP_FARMER_PROPOSAL:
          coaCode = COACode.FARMER_AGENT_PAYABLE;
          break;
        default:
          break;
      }

      accountName = paymentRepository.findCheckOfAccountNameByCode(coaCode, branch.getBranchCode(),
          currencyCode);
      commission = Utils.getTwoDecimalPoint(ac.getCommission());
      String narration = getNarrationAgent(payment, ac, isRenewal);
      String cur = payment.getCur();
      double rate = payment.getRate();
      TLFBuilder tlfBuilder = new TLFBuilder(TranCode.TRCREDIT, Status.TCV, commission,
          ac.getAgent().getId(), branch.getBranchCode(), accountName, receiptNo, narration, eno,
          ac.getReferenceNo(), ac.getReferenceType(), isRenewal, cur, rate);
      tlf = tlfBuilder.getTLFInstance();
      // setIDPrefixForInsert(tlf);
      tlf.setPaymentChannel(payment.getPaymentChannel());
      tlf.setSalePoint(salePoint);
      tlf.setPolicyNo(policyNo);
      tlf.setPaid(true);
      tlf.setAgentTransaction(true);
      tlf.setCommonCreateAndUpateMarks(recorder);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return tlf;
  }


  /////////////////////////////////////////////////////////////////////////////////////
  // For Student Life

  @Transactional(propagation = Propagation.REQUIRED)
  public List<LifePolicy> createStudentLifeProposalToPolicy(
      StudentLifeProposalDTO studentLifeProposalDTO) {
    // convert groupFarmerProposalDTO to lifeproposal
    List<LifeProposal> studentLifeProposalList =
        convertStudentLifeProposalDTOToProposal(studentLifeProposalDTO);

    // convert lifeproposal to lifepolicy
    List<LifePolicy> policyList = convertStudentLifeProposalToPolicy(studentLifeProposalList);

    // create lifepolicy and return policynoList
    policyList = lifePolicyRepo.saveAll(policyList);

    // create lifepolicy to payment
    List<Payment> paymentList = convertStudentLifePolicyToPayment(policyList);
    paymentRepository.saveAll(paymentList);

    CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
    recorder.setCreatedDate(new Date());

    if (null != studentLifeProposalDTO.getAgentID()) {
      List<AgentCommission> agentcommissionList =
          convertStudentLifePolicyToAgentCommission(policyList);
      agentCommissionRepo.saveAll(agentcommissionList);
    }

    return policyList;
  }



  // Forstudentlife studentlifeDto to proposal
  public List<LifeProposal> convertStudentLifeProposalDTOToProposal(
      StudentLifeProposalDTO studentLifeProposalDTO) {

    Optional<Branch> branchOptional = branchRepo.findById(studentLifeProposalDTO.getBranchId());
    Optional<Customer> referralOptional =
        customerRepo.findById(studentLifeProposalDTO.getReferralID());
    Optional<Organization> organizationOptional =
        organizationRepo.findById(studentLifeProposalDTO.getCustomerID());
    Optional<PaymentType> paymentTypeOptional =
        paymentTypeRepo.findById(studentLifeProposalDTO.getPaymentTypeId());
    Optional<Agent> agentOptional = agentRepo.findById(studentLifeProposalDTO.getAgentID());
    Optional<SaleMan> saleManOptional = saleManRepo.findById(studentLifeProposalDTO.getSaleManId());
    Optional<SalePoint> salePointOptional =
        salePointRepo.findById(studentLifeProposalDTO.getSalePointId());

    List<LifeProposal> lifeProposalList = new ArrayList<>();
    studentLifeProposalDTO.getProposalInsuredPersonList().forEach(insuredPerson -> {

      LifeProposal lifeProposal = new LifeProposal();
      lifeProposal.setProposalType(ProposalType.UNDERWRITING);
      lifeProposal.setSubmittedDate(studentLifeProposalDTO.getSubmittedDate());
      lifeProposal.setBranch(branchOptional.get());
      lifeProposal.setReferral(referralOptional.get());
      lifeProposal.setOrganization(organizationOptional.get());
      lifeProposal.setPaymentType(paymentTypeOptional.get());
      lifeProposal.setAgent(agentOptional.get());
      lifeProposal.setSaleMan(saleManOptional.get());
      lifeProposal.setSalePoint(salePointOptional.get());
      lifeProposal.getProposalInsuredPersonList()
          .add(createInsuredPersonForStudentLife(insuredPerson));
      String proposalNo = customIdRepo.getNextId("STUDENT_LIFE_PROPOSAL_NO_ID_GEN", null);
      lifeProposal.setProposalNo(proposalNo);
      lifeProposal.setPrefix("ISLIF001");
      lifeProposalList.add(lifeProposal);

    });

    return lifeProposalList;
  }


  // ForStudentLife proposal to policy
  private List<LifePolicy> convertStudentLifeProposalToPolicy(
      List<LifeProposal> studentlifeProposalList) {
    List<LifePolicy> policyList = new ArrayList<>();
    studentlifeProposalList.forEach(proposal -> {
      LifePolicy policy = new LifePolicy(proposal);
      String policyNo = customIdRepo.getNextId("STUDENT_LIFE_POLICY_NO", null);
      policy.setPolicyNo(policyNo);
      policy.setActivedPolicyStartDate(policy.getPolicyInsuredPersonList().get(0).getStartDate());
      policy.setActivedPolicyEndDate(policy.getPolicyInsuredPersonList().get(0).getEndDate());
      policyList.add(policy);
    });
    return policyList;
  }


  private ProposalInsuredPerson createInsuredPersonForStudentLife(
      StudentLifeProposalInsuredPersonDTO dto) {
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


    return insuredPerson;
  }

  // for student life payment
  private List<Payment> convertStudentLifePolicyToPayment(List<LifePolicy> studentlifePolicyList) {
    List<Payment> paymentList = new ArrayList<Payment>();

    studentlifePolicyList.forEach(lifePolicy -> {
      Optional<Bank> fromBankOptional = Optional.empty();
      Optional<Bank> toBankOptional = Optional.empty();
      if (lifePolicy.getFromBank() != null) {
        fromBankOptional = bankRepository.findById(lifePolicy.getFromBank());
      }
      if (lifePolicy.getToBank() != null) {
        toBankOptional = bankRepository.findById(lifePolicy.getToBank());
      }
      Payment payment = new Payment();
      double rate = 1.0;
      String receiptNo = "";
      CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
      recorder.setCreatedDate(new Date());
      if (PaymentChannel.CASHED.equals(lifePolicy.getPaymentChannel())) {
        receiptNo = customIdRepo.getNextId("CASH_RECEIPT_ID_GEN", null);
      } else if (PaymentChannel.CHEQUE.equals(lifePolicy.getPaymentChannel())) {
        payment.setPO(true);
        receiptNo = customIdRepo.getNextId("CHEQUE_RECEIPT_ID_GEN", null);
      } else if (PaymentChannel.TRANSFER.equals(lifePolicy.getPaymentChannel())) {
        receiptNo = customIdRepo.getNextId("TRANSFER_RECEIPT_ID_GEN", null);
      } else {
        receiptNo = customIdRepo.getNextId("CHEQUE_RECEIPT_ID_GEN", null);
        payment.setPO(true);
      }

      payment.setReceiptNo(receiptNo);
      payment.setChequeNo(lifePolicy.getChequeNo());
      payment.setPaymentType(lifePolicy.getPaymentType());
      payment.setPaymentChannel(lifePolicy.getPaymentChannel());
      payment.setReferenceType(PolicyReferenceType.FARMER_POLICY);
      payment.setConfirmDate(new Date());
      payment.setPaymentDate(new Date());
      if (toBankOptional.isPresent()) {
        payment.setAccountBank(toBankOptional.get());
      }
      if (fromBankOptional.isPresent()) {
        payment.setBank(fromBankOptional.get());
      }
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
  private List<AgentCommission> convertStudentLifePolicyToAgentCommission(
      List<LifePolicy> studentlifePolicyList) {
    List<AgentCommission> agentCommissionList = new ArrayList<AgentCommission>();
    /* get agent commission of each policy */
    studentlifePolicyList.forEach(lifePolicy -> {
      Product product = lifePolicy.getPolicyInsuredPersonList().get(0).getProduct();
      double commissionPercent = product.getFirstCommission();
      Payment payment = paymentRepository.findByPaymentReferenceNo(lifePolicy.getId());
      double rate = payment.getRate();
      double firstAgentCommission = lifePolicy.getAgentCommission();
      agentCommissionList.add(new AgentCommission(lifePolicy.getId(),
          PolicyReferenceType.STUDENT_LIFE_POLICY, lifePolicy.getAgent(), firstAgentCommission,
          new Date(), payment.getReceiptNo(), lifePolicy.getTotalTermPremium(), commissionPercent,
          AgentCommissionEntryType.UNDERWRITING, rate, (rate * firstAgentCommission), "KYT",
          (rate * lifePolicy.getTotalTermPremium())));

    });

    return agentCommissionList;
  }


}
