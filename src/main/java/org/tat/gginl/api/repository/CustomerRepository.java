package org.tat.gginl.api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tat.gginl.api.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String>{
	public List<Customer> findByRecorderCreatedDateBetweenOrRecorderUpdatedDateBetween(Date createdDate,Date updatedDate,Date createdDate1,Date updatedDate1);
}
