package org.tat.gginl.api.domains.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tat.gginl.api.domains.Product;

public interface ProductRepository extends JpaRepository<Product, String>{
	
	
	@Query(value = "SELECT * FROM Product",nativeQuery = true)
	List<Object[]> findAllNativeObject();
	
	@Query(value = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Product'",nativeQuery = true)
	List<Object> findAllColumnName();

}
