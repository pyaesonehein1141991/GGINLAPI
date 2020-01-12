package org.tat.gginl.api.domains.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.tat.gginl.api.common.IDGen;

@NoRepositoryBean
public interface IdGenRepository{
	IDGen getNextId(String generatedItem);

}
