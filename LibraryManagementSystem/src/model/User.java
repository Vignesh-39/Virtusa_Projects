package model;

/**
 * Represents a library member/user.
 * Stores basic info about registered users.
 */
public class User {

    private int userId;
    private String name;
    private String email;
    private String phone;

    // Constructor to register a new user
    public User(int userId, String name, String email, String phone) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // --- Getters ---

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    // Print user info in a clean format
    @Override
    public String toString() {
        return String.format(
            "User[ID: %d | Name: %-20s | Email: %-25s | Phone: %s]",
            userId, name, email, phone
        );
    }
}
