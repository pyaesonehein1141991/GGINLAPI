package org.tat.gginl.api.domains.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tat.gginl.api.domains.Agent;
import org.tat.gginl.api.domains.repository.AgentRepository;

@Service
public class AgentService {
	
	@Autowired
	private AgentRepository repository;
	
	@Transactional
	public List<Agent> findAll(){
		return repository.findAll();
	}

}
