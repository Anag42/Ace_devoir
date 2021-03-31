package com.cigma.ace.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cigma.ace.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	Boolean existsByTitle(String string);
	@Query(value = "SELECT count(cp)>0 FROM CartProduct cp WHERE product_id = :id")
	Boolean presentInCart(@Param("id") Long id);
	List<Product> findByTitleIgnoreCaseContaining(String title);
}
