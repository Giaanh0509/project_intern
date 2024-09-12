package com.example.bookstore.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private double price;

    @Column(name = "rating")
    private double rating;

    @Column(name = "stock_quantity")
    private int stockQuantity;

    @Lob
    @Column(name = "image", columnDefinition = "LONGTEXT") // This column now holds the Base64 encoded image data
    private String image;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<BookCategory> bookCategories;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<CartDetail> cartDetails;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<Review> reviews;

    public Book() {
    }

    public Book(String title, double price, double rating, int stockQuantity, Set<BookCategory> bookCategories, String image) {
        this.title = title;
        this.price = price;
        this.rating = rating;
        this.stockQuantity = stockQuantity;
        this.bookCategories = bookCategories;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<BookCategory> getBookCategories() {
        return bookCategories;
    }

    public void setBookCategories(Set<BookCategory> bookCategories) {
        this.bookCategories = bookCategories;
    }

    public Set<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(Set<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Set<CartDetail> getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(Set<CartDetail> cartDetails) {
        this.cartDetails = cartDetails;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", rating=" + rating +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
}
