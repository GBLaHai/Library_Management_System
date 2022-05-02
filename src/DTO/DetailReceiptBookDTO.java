/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Manh Hai
 */
public class DetailReceiptBookDTO {
    private int id;
    private int supplier_id;
    private int librarian_id;
    private int book_id;
    private int genre_id;
    private int author_id;
    private int publisher_id;
    private float totalMoney;
    private int totalQuantity;
    private String date;

    public DetailReceiptBookDTO() {
    }

    public DetailReceiptBookDTO(int id, int supplier_id, int librarian_id, int book_id, int genre_id, int author_id, int publisher_id, float totalMoney, int totalQuantity, String date) {
        this.id = id;
        this.supplier_id = supplier_id;
        this.librarian_id = librarian_id;
        this.book_id = book_id;
        this.genre_id = genre_id;
        this.author_id = author_id;
        this.publisher_id = publisher_id;
        this.totalMoney = totalMoney;
        this.totalQuantity = totalQuantity;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public int getLibrarian_id() {
        return librarian_id;
    }

    public void setLibrarian_id(int librarian_id) {
        this.librarian_id = librarian_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public int getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(int publisher_id) {
        this.publisher_id = publisher_id;
    }

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    
}
