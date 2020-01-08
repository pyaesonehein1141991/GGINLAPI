package org.tat.gginl.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tat.gginl.api.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String>{

}
