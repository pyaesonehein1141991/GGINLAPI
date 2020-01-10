/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.tat.gginl.api.common;

public enum KeyFactorType {
	REFERENCE("Reference"), FROM_TO("From-To"), FIXED("Fixed"), ENUM("Enum");

	private String label;

	private KeyFactorType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}