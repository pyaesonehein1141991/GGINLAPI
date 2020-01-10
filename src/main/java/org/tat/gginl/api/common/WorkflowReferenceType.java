package org.tat.gginl.api.common;

public enum WorkflowReferenceType {

	LIFE_PROPOSAL,

	AGENT_COMMISSION,

	LIFE_CLAIM, LIFE_DIS_CLAIM,

	LIFE_COLLECTION,

	COINSURANCE,

	SNAKEBITEPOLICY,

	MEDICAL_POLICY,

	TRAVEL_PROPOSAL,

	MEDICAL_PROPOSAL,

	MEDICAL_RENEWAL_PROPOSAL,

	MEDICAL_CLAIM,

	LIFESURRENDER_PROPOSAL,

	LIFE_PAIDUP_PROPOSAL,

	SHORTTERM_LIFE_PAIDUP_PROPOSAL,

	PA_PROPOSAL,

	PERSON_TRAVEL_PROPOSAL,

	FARMER_PROPOSAL,

	SHORT_ENDOWMENT_LIFE_PROPOSAL,

	SHORTENDOWMENTLIFESURRENDER_PROPOSAL,

	CRITICAL_ILLNESS_PROPOSAL, CRITICAL_ILLNESS_POLICY, MICRO_HEALTH_PROPOSAL, MICRO_HEALTH_POLICY,

	HEALTH_PROPOSAL, HEALTH_POLICY, GROUP_MICROHEALTH, GROUPFARMER_PROPOSAL, STUDENT_LIFE_PROPOSAL;

	public static boolean isMedical(WorkflowReferenceType referenceType) {
		switch (referenceType) {
			case AGENT_COMMISSION:
			case COINSURANCE:
			case FARMER_PROPOSAL:
			case LIFESURRENDER_PROPOSAL:
			case LIFE_CLAIM:
			case LIFE_COLLECTION:
			case LIFE_DIS_CLAIM:
			case LIFE_PAIDUP_PROPOSAL:
			case LIFE_PROPOSAL:
			case PA_PROPOSAL:
			case PERSON_TRAVEL_PROPOSAL:
			case SHORTENDOWMENTLIFESURRENDER_PROPOSAL:
			case SHORTTERM_LIFE_PAIDUP_PROPOSAL:
			case SHORT_ENDOWMENT_LIFE_PROPOSAL:
			case SNAKEBITEPOLICY:
			case TRAVEL_PROPOSAL:
			case GROUPFARMER_PROPOSAL:
			case STUDENT_LIFE_PROPOSAL:
				return false;
			case CRITICAL_ILLNESS_POLICY:
			case CRITICAL_ILLNESS_PROPOSAL:
			case HEALTH_POLICY:
			case HEALTH_PROPOSAL:
			case MEDICAL_CLAIM:
			case MEDICAL_POLICY:
			case MEDICAL_PROPOSAL:
			case MEDICAL_RENEWAL_PROPOSAL:
			case MICRO_HEALTH_POLICY:
			case MICRO_HEALTH_PROPOSAL:
			case GROUP_MICROHEALTH:
				return true;
			default:
				return false;
		}

	}
}
