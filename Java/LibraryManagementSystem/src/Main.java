import model.Book;
import model.User;
import service.LibraryManager;

import java.util.List;
import java.util.Scanner;

/**
 * Main entry point of the Library Management System.
 * Provides a menu-driven console interface for all operations.
 *
 * Author: Vignesh
 * Project: Library Management System - Virtusa Training
 */
public class Main {

    // shared scanner for all inputs
    static Scanner scanner = new Scanner(System.in);
    static LibraryManager libraryManager = new LibraryManager();

    public static void main(String[] args) {
        printBanner();
        System.out.println("  Sample data loaded. System ready.\n");

        int choice = 0;

        // keep showing menu until user exits
        while (choice != 9) {
            printMenu();
            choice = readInt("  Enter your choice: ");

            switch (choice) {
                case 1:
                    handleAddBook();
                    break;
                case 2:
                    handleRemoveBook();
                    break;
                case 3:
                    handleSearchBook();
                    break;
                case 4:
                    handleListBooks();
                    break;
                case 5:
                    handleRegisterUser();
                    break;
                case 6:
                    handleListUsers();
                    break;
                case 7:
                    handleIssueBook();
                    break;
                case 8:
                    handleReturnBook();
                    break;
                case 9:
                    System.out.println("\n  Thank you for using the Library Management System. Goodbye!");
                    break;
                default:
                    System.out.println("  Invalid choice. Please enter a number between 1 and 9.");
            }
            System.out.println(); // blank line for readability
        }

        scanner.close();
    }

    // =============================================
    //              MENU DISPLAY
    // =============================================

    private static void printBanner() {
        System.out.println("==========================================================");
        System.out.println("         LIBRARY MANAGEMENT SYSTEM");
        System.out.println("         Virtusa Training | Core Java OOP Project");
        System.out.println("==========================================================");
    }

    private static void printMenu() {
        System.out.println("----------------------------------------------------------");
        System.out.println("  MAIN MENU");
        System.out.println("----------------------------------------------------------");
        System.out.println("  1. Add Book");
        System.out.println("  2. Remove Book");
        System.out.println("  3. Search Book (by title or author)");
        System.out.println("  4. List All Books");
        System.out.println("  5. Register New User");
        System.out.println("  6. List All Users");
        System.out.println("  7. Issue Book to User");
        System.out.println("  8. Return Book");
        System.out.println("  9. Exit");
        System.out.println("----------------------------------------------------------");
    }

    // =============================================
    //              BOOK HANDLERS
    // =============================================

    private static void handleAddBook() {
        System.out.println("\n  --- Add New Book ---");
        System.out.print("  Enter book title  : ");
        String title = scanner.nextLine().trim();

        System.out.print("  Enter author name : ");
        String author = scanner.nextLine().trim();

        System.out.print("  Enter genre       : ");
        String genre = scanner.nextLine().trim();

        if (title.isEmpty() || author.isEmpty()) {
            System.out.println("  >> Title and Author cannot be empty.");
            return;
        }

        libraryManager.addBook(title, author, genre);
    }

    private static void handleRemoveBook() {
        System.out.println("\n  --- Remove Book ---");
        handleListBooks(); // show current books first
        int bookId = readInt("  Enter Book ID to remove: ");
        libraryManager.removeBook(bookId);
    }

    private static void handleSearchBook() {
        System.out.println("\n  --- Search Book ---");
        System.out.print("  Enter search keyword (title or author): ");
        String query = scanner.nextLine().trim();

        if (query.isEmpty()) {
            System.out.println("  >> Search query cannot be empty.");
            return;
        }

        List<Book> results = libraryManager.searchBook(query);

        if (results.isEmpty()) {
            System.out.println("  >> No books found matching: \"" + query + "\"");
        } else {
            System.out.println("  >> Found " + results.size() + " result(s):");
            for (Book book : results) {
                System.out.println("     " + book);
            }
        }
    }

    private static void handleListBooks() {
        System.out.println("\n  --- All Books in Library ---");
        List<Book> allBooks = libraryManager.getAllBooks();

        if (allBooks.isEmpty()) {
            System.out.println("  >> No books found in the library.");
        } else {
            for (Book book : allBooks) {
                System.out.println("     " + book);
            }
            System.out.println("  Total: " + allBooks.size() + " book(s).");
        }
    }

    // =============================================
    //              USER HANDLERS
    // =============================================

    private static void handleRegisterUser() {
        System.out.println("\n  --- Register New User ---");
        System.out.print("  Enter user name  : ");
        String name = scanner.nextLine().trim();

        System.out.print("  Enter email      : ");
        String email = scanner.nextLine().trim();

        System.out.print("  Enter phone      : ");
        String phone = scanner.nextLine().trim();

        if (name.isEmpty() || email.isEmpty()) {
            System.out.println("  >> Name and Email are required.");
            return;
        }

        libraryManager.registerUser(name, email, phone);
    }

    private static void handleListUsers() {
        System.out.println("\n  --- Registered Users ---");
        List<User> users = libraryManager.getAllUsers();

        if (users.isEmpty()) {
            System.out.println("  >> No users registered yet.");
        } else {
            for (User user : users) {
                System.out.println("     " + user);
            }
            System.out.println("  Total: " + users.size() + " user(s).");
        }
    }

    // =============================================
    //          ISSUE / RETURN HANDLERS
    // =============================================

    private static void handleIssueBook() {
        System.out.println("\n  --- Issue Book to User ---");

        // show available books and users to help the user pick IDs
        System.out.println("  Available Books:");
        List<Book> available = libraryManager.getAvailableBooks();
        if (available.isEmpty()) {
            System.out.println("  >> No books are currently available for issuing.");
            return;
        }
        for (Book book : available) {
            System.out.println("     " + book);
        }

        System.out.println("\n  Registered Users:");
        for (User user : libraryManager.getAllUsers()) {
            System.out.println("     " + user);
        }

        System.out.println();
        int bookId = readInt("  Enter Book ID to issue: ");
        int userId = readInt("  Enter User ID         : ");

        libraryManager.issueBook(bookId, userId);
    }

    private static void handleReturnBook() {
        System.out.println("\n  --- Return Book ---");
        int bookId = readInt("  Enter Book ID to return: ");
        int userId = readInt("  Enter your User ID     : ");
        libraryManager.returnBook(bookId, userId);
    }

    // =============================================
    //              UTILITY METHODS
    // =============================================

    /**
     * Reads an integer safely. Shows error if input is not a number.
     */
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("  >> Invalid input. Please enter a number.");
            }
        }
    }
}
