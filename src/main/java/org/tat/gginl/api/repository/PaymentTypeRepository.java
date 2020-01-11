package org.tat.gginl.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tat.gginl.api.domains.PaymentType;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, String>{

}
