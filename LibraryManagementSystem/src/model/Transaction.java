package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Tracks each book issue/return transaction.
 * Fine is Rs. 5 per day for late returns.
 */
public class Transaction {

    private static final double FINE_PER_DAY = 5.0; // fine rate in rupees per day
    private static final int LOAN_PERIOD_DAYS = 14;  // standard loan period

    private int transactionId;
    private int bookId;
    private int userId;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double fineAmount;
    private boolean isReturned;

    // Constructor used when issuing a book
    public Transaction(int transactionId, int bookId, int userId) {
        this.transactionId = transactionId;
        this.bookId = bookId;
        this.userId = userId;
        this.issueDate = LocalDate.now();
        this.dueDate = issueDate.plusDays(LOAN_PERIOD_DAYS);
        this.returnDate = null;
        this.fineAmount = 0.0;
        this.isReturned = false;
    }

    /**
     * Called when the user returns the book.
     * Calculates fine if returned after due date.
     */
    public void markReturned() {
        this.returnDate = LocalDate.now();
        this.isReturned = true;

        // check if the book is returned late
        if (returnDate.isAfter(dueDate)) {
            long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
            this.fineAmount = daysLate * FINE_PER_DAY;
        }
    }

    // --- Getters ---

    public int getTransactionId() {
        return transactionId;
    }

    public int getBookId() {
        return bookId;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public boolean isReturned() {
        return isReturned;
    }

    // Display transaction info
    @Override
    public String toString() {
        String returnInfo = isReturned
            ? String.format("Returned: %s | Fine: Rs. %.2f", returnDate, fineAmount)
            : "Status: Not Returned";

        return String.format(
            "Txn[ID: %d | Book: %d | User: %d | Issued: %s | Due: %s | %s]",
            transactionId, bookId, userId, issueDate, dueDate, returnInfo
        );
    }
}
