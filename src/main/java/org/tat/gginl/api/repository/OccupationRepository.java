package org.tat.gginl.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tat.gginl.api.domains.Occupation;

public interface OccupationRepository extends JpaRepository<Occupation, String>{

}
