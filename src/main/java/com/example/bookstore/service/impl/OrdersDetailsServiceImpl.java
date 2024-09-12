package com.example.bookstore.service.impl;

import com.example.bookstore.dao.OrdersDetailsRepository;
import com.example.bookstore.dto.BooksOrders;
import com.example.bookstore.entity.Book;
import com.example.bookstore.service.OrdersDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrdersDetailsServiceImpl implements OrdersDetailsService {

    @Autowired
    private OrdersDetailsRepository ordersDetailsRepository;

    @Override
    public List<Book> findBooksByOrderId(int id) {
        return ordersDetailsRepository.findBooksByOrderId(id);
    }

    @Override
    public Integer findBooksOrderQuantity(int orderId, int bookId) {
        return ordersDetailsRepository.findBooksOrderQuantity(orderId, bookId);
    }

    @Override
    public Page<BooksOrders> convertListToPage(List<BooksOrders> booksOrdersList, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), booksOrdersList.size());
        List<BooksOrders> subList = booksOrdersList.subList(start, end);
        return new PageImpl<>(subList, pageable, booksOrdersList.size());
    }

    @Override
    public List<BooksOrders> createBooksOrdersList(List<BooksOrders> booksOrdersList, int id) {
        List<Book> books = ordersDetailsRepository.findBooksByOrderId(id);
        for (Book book : books) {
            Integer booksOrderQuantity = ordersDetailsRepository.findBooksOrderQuantity(id, book.getId());

            BooksOrders booksOrders = new BooksOrders(book, booksOrderQuantity);

            if (booksOrderQuantity <= book.getStockQuantity()) {
                booksOrders.setStatus("Enough");
            } else {
                booksOrders.setStatus("Not Enough");
            }

            booksOrdersList.add(booksOrders);
        }
        return booksOrdersList;
    }

    @Override
    public void RemoveBookFromOrder(int bookId) {
        ordersDetailsRepository.removeByBookId(bookId);
    }

    @Override
    public boolean checkOrder(List<BooksOrders> booksOrdersList) {
        boolean check=true;
        for(BooksOrders booksOrders: booksOrdersList) {
            if(booksOrders.getStatus().equals("Not Enough")) {
                check=false;
                return check;
            }
        }
        return check;
    }
}
