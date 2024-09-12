package com.example.bookstore.dao;

import java.util.List;
import java.util.Optional;

import com.example.bookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bookstore.entity.Order;
import com.example.bookstore.entity.Order.Status;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Integer> {

	@Query("SELECT o FROM Order o JOIN FETCH o.orderDetails od JOIN FETCH od.book WHERE o.user.id = :userId")
	List<Order> findOrdersByUserId(Integer userId);

	@Query("SELECT o FROM Order o JOIN FETCH o.orderDetails od JOIN FETCH od.book WHERE o.user.id = :userId AND o.status = :status")
	List<Order> findOrdersByUserIdAndStatus(Integer userId, Status status);

	@Query("SELECT o FROM Order o JOIN FETCH o.orderDetails od JOIN FETCH od.book WHERE o.id = :orderId")
	Optional<Order> findOrderByIdWithDetails(Integer orderId);

    @Modifying()
    @Query("UPDATE Order o set o.status= 'COMPLETED' WHERE o.id = :orderId")
    void changeStatusToCompleted(@Param("orderId") Integer orderId);

    @Modifying()
    @Query("UPDATE Order o set o.status= 'REJECTED' WHERE o.id = :orderId")
    void changeStatusToCancelled(@Param("orderId") Integer orderId);

    @Query("SELECT o.user FROM Order o WHERE o.id = :orderId")
    User findUserByOrderId(@Param("orderId") int orderId);

    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE o.status = 'COMPLETED'")
    Double findTotalPriceByStatusCompleted();

    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = 'COMPLETED'")
    int countOrdersByStatusCompleted();

    @Query("SELECT SUM(od.quantity) FROM Order o JOIN o.orderDetails od WHERE o.status = 'COMPLETED'")
    int countBooksSold();

    @Query(value = "SELECT DISTINCT MONTH(o.order_date) FROM orders o ORDER BY MONTH(o.order_date) DESC LIMIT 4", nativeQuery = true)
    List<Integer> findRecentOrderMonths();

    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE MONTH(o.orderDate) = :month AND o.status = 'COMPLETED'")
    Double findTotalPriceByMonth(@Param("month") int month);

    @Query("SELECT COUNT(o) FROM Order o WHERE MONTH(o.orderDate) = :month AND o.status = 'COMPLETED'")
    Integer countCompletedOrdersByMonth(@Param("month") int month);
}
