package org.tat.gginl.api.domains.repository;

import org.tat.gginl.api.common.IDGen;

public interface IdGenRepository{
	IDGen getNextId(String generatedItem);

}
