package com.cigma.ace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cigma.ace.model.CartProduct;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
	@Query(value = "SELECT cp FROM CartProduct cp WHERE cart_id = :cartId AND product_id = :productId")
	<Optional>CartProduct findByCartIdAndProductId(@Param("cartId") Long cartId, @Param("productId") Long productId);
}
