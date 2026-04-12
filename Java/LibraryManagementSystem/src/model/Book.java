package model;

/**
 * Represents a book in the library.
 * Each book has an ID, title, author, genre, and availability status.
 */
public class Book {

    private int bookId;
    private String title;
    private String author;
    private String genre;
    private boolean isIssued;

    // Constructor to create a new book entry
    public Book(int bookId, String title, String author, String genre) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isIssued = false; // by default, a new book is available
    }

    // --- Getters ---

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public boolean isIssued() {
        return isIssued;
    }

    // --- Setters ---

    public void setIssued(boolean issued) {
        isIssued = issued;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    // Display book info in a readable format
    @Override
    public String toString() {
        String status = isIssued ? "Issued" : "Available";
        return String.format(
            "Book[ID: %d | Title: %-30s | Author: %-20s | Genre: %-15s | Status: %s]",
            bookId, title, author, genre, status
        );
    }
}
