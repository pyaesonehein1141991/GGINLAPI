/***************************************************************************************
\ * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.tat.gginl.api.common;

public enum PaymentChannel {
	CASHED("Cash"), TRANSFER("Transfer"), CHEQUE("Cheque / Payment Order"), SUNDRY("Receivable");

	private String label;

	private PaymentChannel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}