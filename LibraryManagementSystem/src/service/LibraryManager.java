package service;

import model.Book;
import model.Transaction;
import model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * LibraryManager is the core service class.
 * It manages books, users, and transactions using in-memory ArrayLists.
 */
public class LibraryManager {

    private ArrayList<Book> bookList;
    private ArrayList<User> userList;
    private ArrayList<Transaction> transactionList;

    // counters for auto-incrementing IDs
    private int nextBookId = 1;
    private int nextUserId = 1;
    private int nextTransactionId = 1;

    public LibraryManager() {
        bookList = new ArrayList<>();
        userList = new ArrayList<>();
        transactionList = new ArrayList<>();

        // load some sample/demo data to start with
        loadSampleData();
    }

    // --- Sample Data for demo purposes ---
    private void loadSampleData() {
        addBook("The Pragmatic Programmer", "David Thomas", "Technology");
        addBook("Clean Code", "Robert C. Martin", "Technology");
        addBook("To Kill a Mockingbird", "Harper Lee", "Fiction");
        addBook("1984", "George Orwell", "Dystopian");
        addBook("The Alchemist", "Paulo Coelho", "Philosophy");

        registerUser("Alice Johnson", "alice@email.com", "9876543210");
        registerUser("Bob Smith", "bob@email.com", "9123456789");
    }

    // =============================================
    //              BOOK OPERATIONS
    // =============================================

    /**
     * Adds a new book to the library.
     */
    public void addBook(String title, String author, String genre) {
        Book newBook = new Book(nextBookId++, title, author, genre);
        bookList.add(newBook);
        System.out.println("  >> Book added successfully: [" + newBook.getTitle() + "]");
    }

    /**
     * Removes a book from the library by its ID.
     * A book that is currently issued cannot be removed.
     */
    public boolean removeBook(int bookId) {
        // loop through books to find matching ID
        for (int i = 0; i < bookList.size(); i++) {
            Book book = bookList.get(i);
            if (book.getBookId() == bookId) {
                if (book.isIssued()) {
                    System.out.println("  >> Cannot remove. Book is currently issued to a user.");
                    return false;
                }
                bookList.remove(i);
                System.out.println("  >> Book [" + book.getTitle() + "] removed successfully.");
                return true;
            }
        }
        System.out.println("  >> Book with ID " + bookId + " not found.");
        return false;
    }

    /**
     * Search books by title or author (case-insensitive).
     */
    public List<Book> searchBook(String query) {
        List<Book> results = new ArrayList<>();
        String lowerQuery = query.toLowerCase();

        // loop through all books and check if title or author matches
        for (Book book : bookList) {
            if (book.getTitle().toLowerCase().contains(lowerQuery)
                    || book.getAuthor().toLowerCase().contains(lowerQuery)) {
                results.add(book);
            }
        }
        return results;
    }

    /**
     * Lists all available (not issued) books.
     */
    public List<Book> getAvailableBooks() {
        List<Book> available = new ArrayList<>();
        for (Book book : bookList) {
            if (!book.isIssued()) {
                available.add(book);
            }
        }
        return available;
    }

    /**
     * Returns all books in the library.
     */
    public ArrayList<Book> getAllBooks() {
        return bookList;
    }

    // =============================================
    //              USER OPERATIONS
    // =============================================

    /**
     * Registers a new library user.
     */
    public void registerUser(String name, String email, String phone) {
        User newUser = new User(nextUserId++, name, email, phone);
        userList.add(newUser);
        System.out.println("  >> User registered: [" + newUser.getName() + "]");
    }

    /**
     * Returns all registered users.
     */
    public ArrayList<User> getAllUsers() {
        return userList;
    }

    // =============================================
    //          ISSUE / RETURN OPERATIONS
    // =============================================

    /**
     * Issues a book to a user.
     * Validates that both book and user exist, and the book is available.
     */
    public boolean issueBook(int bookId, int userId) {
        Book targetBook = findBookById(bookId);
        User targetUser = findUserById(userId);

        if (targetBook == null) {
            System.out.println("  >> Book not found with ID: " + bookId);
            return false;
        }
        if (targetUser == null) {
            System.out.println("  >> User not found with ID: " + userId);
            return false;
        }
        if (targetBook.isIssued()) {
            System.out.println("  >> Sorry, this book is already issued to another user.");
            return false;
        }

        // mark the book as issued and create a transaction record
        targetBook.setIssued(true);
        Transaction txn = new Transaction(nextTransactionId++, bookId, userId);
        transactionList.add(txn);

        System.out.println("  >> Book [" + targetBook.getTitle() + "] issued to [" + targetUser.getName() + "].");
        System.out.println("  >> Due Date: " + txn.getDueDate());
        return true;
    }

    /**
     * Returns a book from a user.
     * Calculates fine if returned after due date.
     */
    public boolean returnBook(int bookId, int userId) {
        Book targetBook = findBookById(bookId);

        if (targetBook == null) {
            System.out.println("  >> Book not found with ID: " + bookId);
            return false;
        }
        if (!targetBook.isIssued()) {
            System.out.println("  >> This book was not issued.");
            return false;
        }

        // find the active (not yet returned) transaction for this book and user
        Transaction activeTxn = null;
        for (Transaction txn : transactionList) {
            if (txn.getBookId() == bookId && txn.getUserId() == userId && !txn.isReturned()) {
                activeTxn = txn;
                break;
            }
        }

        if (activeTxn == null) {
            System.out.println("  >> No active transaction found for this book/user combination.");
            return false;
        }

        // process the return and fine
        activeTxn.markReturned();
        targetBook.setIssued(false);

        System.out.println("  >> Book [" + targetBook.getTitle() + "] returned successfully.");
        if (activeTxn.getFineAmount() > 0) {
            System.out.printf("  >> Late return fine: Rs. %.2f (%.0f days overdue)%n",
                activeTxn.getFineAmount(),
                activeTxn.getFineAmount() / 5.0);
        } else {
            System.out.println("  >> Returned on time. No fine.");
        }
        return true;
    }

    /**
     * Displays all transactions (both active and completed).
     */
    public void showAllTransactions() {
        if (transactionList.isEmpty()) {
            System.out.println("  >> No transactions found.");
            return;
        }
        for (Transaction txn : transactionList) {
            System.out.println("  " + txn);
        }
    }

    // =============================================
    //              HELPER METHODS
    // =============================================

    // Find a book by its ID
    private Book findBookById(int bookId) {
        for (Book book : bookList) {
            if (book.getBookId() == bookId) {
                return book;
            }
        }
        return null;
    }

    // Find a user by their ID
    private User findUserById(int userId) {
        for (User user : userList) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }
}
