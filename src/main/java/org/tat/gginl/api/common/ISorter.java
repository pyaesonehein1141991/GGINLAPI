package org.tat.gginl.api.common;



import java.io.Serializable;

/**
 * @author Thiha Kyaw
 * @Use It is used to sort the entities which have custom registration number
 *      format.
 */
public interface ISorter extends Serializable {
	public String getRegistrationNo();
}
