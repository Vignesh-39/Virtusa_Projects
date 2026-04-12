# 📚 Library Management System

A console-based Library Management System built with **Core Java** and **Object-Oriented Programming** principles. Developed as part of the Virtusa Java Training program.

---

## 🚀 Features

- **Book Management** — Add, remove, search, and list all books
- **User Management** — Register and list library members
- **Issue & Return** — Issue books to users with due date tracking
- **Fine Calculation** — Automatic Rs. 5/day fine for late returns
- **Search** — Case-insensitive search by title or author
- **Sample Data** — Pre-loaded demo books and users on startup

---

## 🗂️ Project Structure

```
LibraryManagementSystem/
├── src/
│   ├── Main.java                  ← Entry point, menu-driven console UI
│   ├── model/
│   │   ├── Book.java              ← Book entity
│   │   ├── User.java              ← Library member entity
│   │   └── Transaction.java       ← Issue/return record with fine logic
│   └── service/
│       └── LibraryManager.java    ← Core business logic
└── README.md
```

---

## 🛠️ Tools & Technologies

| Tool         | Usage                          |
|--------------|-------------------------------|
| Java 11+     | Core language                  |
| OOP Concepts | Encapsulation, Abstraction     |
| ArrayList    | In-memory data storage         |
| Scanner      | Console input handling         |
| LocalDate    | Date-based fine calculation    |

---

## ▶️ How to Run

### Prerequisites
- Java JDK 11 or higher installed
- `javac` and `java` available in PATH

### Steps

```bash
# 1. Navigate to the src folder
cd LibraryManagementSystem/src

# 2. Compile all Java files
javac -d ../out model/Book.java model/User.java model/Transaction.java service/LibraryManager.java Main.java

# 3. Run the application
java -cp ../out Main
```

---

## 📋 Menu Options

```
1. Add Book
2. Remove Book
3. Search Book (by title or author)
4. List All Books
5. Register New User
6. List All Users
7. Issue Book to User
8. Return Book
9. Exit
```

---

## 💡 OOP Concepts Used

- **Encapsulation** — All fields are private with getters/setters
- **Abstraction** — `LibraryManager` hides internal logic from `Main`
- **Single Responsibility** — Each class handles one concern (Book, User, Transaction, Service)

---

## 📝 Fine Policy

- Loan period: **14 days**
- Fine rate: **Rs. 5 per day** for late returns
- Fine is automatically calculated on book return

---

*Developed by Vignesh | Virtusa Java Training*
