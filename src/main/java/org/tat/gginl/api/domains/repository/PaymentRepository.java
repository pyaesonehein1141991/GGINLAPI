package org.tat.gginl.api.domains.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tat.gginl.api.domains.Payment;

public interface PaymentRepository extends JpaRepository<Payment,String> {

}
