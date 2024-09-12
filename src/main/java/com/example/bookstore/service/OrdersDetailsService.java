package com.example.bookstore.service;

import com.example.bookstore.dto.BooksOrders;
import com.example.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrdersDetailsService {
    List<Book> findBooksByOrderId(int id);

    Integer findBooksOrderQuantity(int orderId, int bookId);

    Page<BooksOrders> convertListToPage(List<BooksOrders> booksOrdersList, Pageable pageable);

    List<BooksOrders> createBooksOrdersList(List<BooksOrders> booksOrdersList, int id);

    void RemoveBookFromOrder(int bookId);

    boolean checkOrder(List<BooksOrders> booksOrdersList);
}
