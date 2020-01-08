package org.tat.gginl.api.common;


import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Data;


@Embeddable
@Data
public class NcbRate implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Enumerated(EnumType.STRING)
	private NcbYear ncbYear;
	private float ncbPercentage;
	
	
}
