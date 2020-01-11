package org.tat.gginl.api.domains.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tat.gginl.api.domains.Product;

public interface ProductRepository extends JpaRepository<Product, String>{

}
