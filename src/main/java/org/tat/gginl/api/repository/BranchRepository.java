package org.tat.gginl.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tat.gginl.api.domains.Branch;

public interface BranchRepository extends JpaRepository<Branch, String>{

}
