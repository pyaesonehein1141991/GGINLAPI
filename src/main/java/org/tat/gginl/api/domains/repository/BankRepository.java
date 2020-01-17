package org.tat.gginl.api.domains.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tat.gginl.api.domains.Bank;

public interface BankRepository extends JpaRepository<Bank,String> {

}
