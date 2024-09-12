package com.example.bookstore.dto;

import com.example.bookstore.entity.Book;

public class BooksOrders {
    private Book book;
    private int orderQuantity;
    private String status;

    public BooksOrders(Book book, int orderQuantity) {
        this.book = book;
        this.orderQuantity = orderQuantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
