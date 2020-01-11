package org.tat.gginl.api.domains.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tat.gginl.api.domains.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {
	List<Customer> findByRecorderCreatedDateBetweenOrRecorderUpdatedDateBetween(Date createdDate,Date updatedDate,Date createdDate1,Date updatedDate1);

}
