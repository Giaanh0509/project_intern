package com.example.bookstore.dao;


import com.example.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bookstore.entity.OrderDetail;

import java.util.List;

@Repository
public interface OrdersDetailsRepository extends JpaRepository<OrderDetail, Integer> {
    @Query("SELECT od.book FROM OrderDetail od WHERE od.order.id = :orderId")
    List<Book> findBooksByOrderId(@Param("orderId") int orderId);

    @Query("SELECT od.quantity FROM OrderDetail od WHERE od.order.id = :orderId AND od.book.id = :bookId")
    Integer findBooksOrderQuantity(@Param("orderId") int orderId, @Param("bookId") int bookId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM OrderDetail od WHERE od.book.id = :bookId")
    void removeByBookId(@Param("bookId") int bookId);
}
