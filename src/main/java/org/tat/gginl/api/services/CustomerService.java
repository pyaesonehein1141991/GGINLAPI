package org.tat.gginl.api.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tat.gginl.api.domains.Customer;
import org.tat.gginl.api.repository.CustomerRepository;


@Service
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepository;
	
	public List<Customer> findByRecorderCreatedDateBetweenOrRecorderUpdatedDateBetween(Date createdDate, Date updatedDate){
		return customerRepository.findByRecorderCreatedDateBetweenOrRecorderUpdatedDateBetween(createdDate, updatedDate, createdDate, updatedDate);
	}
}
