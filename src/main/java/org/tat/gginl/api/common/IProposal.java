package org.tat.gginl.api.common;
import java.util.Currency;
import java.util.Date;


public interface IProposal {

	public String getId();

	public Currency getCurrency();

	public String getUserType();

	public Date getStartDate();

	public Date getEndDate();

}