package com.example.bookstore.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookstore.entity.CartDetail;

@Repository
public interface CartDetailsRepository extends JpaRepository<CartDetail, Integer> {
	List<CartDetail> findByCartId(int cartId);

	CartDetail findByCartIdAndBookId(int cartId, int bookId);

	@Modifying
	@Query("DELETE FROM CartDetail cd WHERE cd.id = :cartDetailId")
	void deleteCartDetailById(@Param("cartDetailId") int cartDetailId);
	
	@Modifying
	@Query("DELETE FROM CartDetail cd WHERE cd.cart.id = :cartId")
	void deleteByCartId(@Param("cartId") int cartId);
}