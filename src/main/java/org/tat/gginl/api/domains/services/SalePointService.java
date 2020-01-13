package org.tat.gginl.api.domains.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.tat.gginl.api.domains.SalePoint;
import org.tat.gginl.api.domains.repository.SalePointRepository;

@Service
public class SalePointService  {
	
	@Autowired
	private SalePointRepository salePointRepository;

	
	@Transactional
	public List<SalePoint> findAll() {
		return salePointRepository.findAll();
	}

	
}
