package org.tat.gginl.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tat.gginl.api.domains.Agent;

public interface AgentRepository extends JpaRepository<Agent, String>{

}
