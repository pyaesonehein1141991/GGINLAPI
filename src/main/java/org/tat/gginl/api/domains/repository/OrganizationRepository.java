package org.tat.gginl.api.domains.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tat.gginl.api.domains.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, String>{

}
