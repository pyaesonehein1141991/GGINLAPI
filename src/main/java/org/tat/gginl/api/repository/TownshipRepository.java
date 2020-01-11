package org.tat.gginl.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tat.gginl.api.domains.Township;

public interface TownshipRepository extends JpaRepository<Township, String>{

}
