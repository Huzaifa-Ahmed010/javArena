package javArena;
import java.util.*;

// Loanable interface defines the contract for issuing and returning books
interface Loanable {
    void issue(Member member);  // issue method will be used to issue a book to a member
    void returnBook(Member member);  // returnBook method will be used when a member returns a book
}

// Displayable interface defines the contract for displaying book/member details
interface Displayable {
    void display(); // used to display the details of object (Book or Member)
}

// Book class implements Loanable and Displayable interfaces
// Yeh class ek book ka pura data store karti hai jaise id, title, author and issue status
class Book implements Loanable, Displayable {
    String id, title, author;
    boolean isIssued = false;

    // Parameterized constructor to initialize book details
    Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    // Issue method: checks if book is not issued and member has limit
    // Agar member ki max limit se kam books issued hain aur book available hai to issue karte hain
    public void issue(Member member) {
        if (!isIssued && member.booksIssued < member.maxBooks) {
            isIssued = true;
            member.booksIssued++;
            System.out.println("Book issued.");
        } else if (isIssued) {
            System.out.println("Book already issued."); // agar pehle se issue hai to ye message aayega
        } else {
            System.out.println("Member has reached issue limit."); // member limit exceed kar gaya
        }
    }

    // Return method: checks if book is issued, then returns and updates member's issued count
    public void returnBook(Member member) {
        if (isIssued) {
            isIssued = false;
            member.booksIssued--;
            System.out.println("Book returned.");
        } else {
            System.out.println("This book was not issued."); // agar book issue nahi thi to ye dikhayega
        }
    }

    // Display book information using overridden display() method
    public void display() {
        System.out.println(this);
    }

    // toString() method for printing book information in a formatted way
    public String toString() {
        return id + " - " + title + " by " + author + (isIssued ? " (Issued)" : "");
    }
}

// Member class which stores each member’s details like id, name, max books, and how many issued
// Yeh track karta hai ki ek member ne kitni books issue ki hui hain
class Member implements Displayable {
    String id, name;
    int maxBooks;
    int booksIssued = 0;

    // Parameterized constructor to initialize member details
    Member(String id, String name, int maxBooks) {
        this.id = id;
        this.name = name;
        this.maxBooks = maxBooks;
    }

    // Display member information
    public void display() {
        System.out.println(this);
    }

    // toString() method for printing member information in a readable format
    public String toString() {
        return id + " - " + name + " (Issued: " + booksIssued + "/" + maxBooks + ")";
    }
}

// Main class jisme ArrayList ka use karke books aur members store kiye jaate hain
public class main2 {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Book> books = new ArrayList<>();
    static ArrayList<Member> members = new ArrayList<>();

    public static void main(String[] args) {
        // Infinite loop: repeatedly shows menu to user until they choose Exit
        while (true) {
            try {
                System.out.println("\n1. Add Book\n2. Register Member\n3. Issue Book\n4. Return Book\n5. Show All Books\n6. Exit");
                System.out.print("Choose: ");
                int choice = sc.nextInt();
                sc.nextLine(); // newline consume

                // Switch case se user se option choose karwa rahe hain
                switch (choice) {
                    case 1 -> addBook(); // Book add karne ka method call
                    case 2 -> registerMember(); // Member register karne ka method
                    case 3 -> issueBook(); // Book issue karne ka method
                    case 4 -> returnBook(); // Book return karne ka method
                    case 5 -> showBooks(); // Saari books display karne ka method
                    case 6 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input!"); // Agar user ne number ki jagah kuch aur likha
                sc.nextLine(); // scanner reset
            } catch (Exception e) {
                System.out.println("Something went wrong: " + e.getMessage()); // Any general error
            }
        }
    }

    // Book add karne ke liye static method
    static void addBook() {
        try {
            System.out.print("Enter Book ID: ");
            String id = sc.nextLine();
            System.out.print("Enter Title: ");
            String title = sc.nextLine();
            System.out.print("Enter Author: ");
            String author = sc.nextLine();
            books.add(new Book(id, title, author)); // New book add to list
            System.out.println("Book added.");
        } catch (Exception e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    // Member register karne ke liye method
    static void registerMember() {
        try {
            System.out.print("Enter Member ID: ");
            String id = sc.nextLine();
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter max books they can issue: ");
            int limit = sc.nextInt();
            sc.nextLine();
            members.add(new Member(id, name, limit));
            System.out.println("Member registered.");
        } catch (InputMismatchException e) {
            System.out.println("please enter a valid number for max books.");
            sc.nextLine(); // scanner reset
        } catch (Exception e) {
            System.out.println("Error in registering member: " + e.getMessage());
        }
    }

    // Book issue karne ka method
    static void issueBook() {
        try {
            System.out.print("Enter Book ID: ");
            String bid = sc.nextLine();
            System.out.print("Enter Member ID: ");
            String mid = sc.nextLine();

            // Book ID search karte hain list se
            Book book = null;
            for (Book b : books) {
                if (b.id.equals(bid)) {
                    book = b;
                    break;
                }
            }

            // Member ID search karte hain list se
            Member member = null;
            for (Member m : members) {
                if (m.id.equals(mid)) {
                    member = m;
                    break;
                }
            }

            // Agar book ya member nahi mila to error print hoga
            if (book == null || member == null) {
                System.out.println("Book or Member not found.");
                return;
            }

            // Book issue karte hain (Book class ka method call)
            book.issue(member);
        } catch (Exception e) {
            System.out.println("Error in issuing book: " + e.getMessage());
        }
    }

    // Book return karne ka method
    static void returnBook() {
        try {
            System.out.print("Enter Book ID: ");
            String bid = sc.nextLine();

            // Book search karte hain list se
            Book book = null;
            for (Book b : books) {
                if (b.id.equals(bid)) {
                    book = b;
                    break;
                }
            }

            // Agar book nahi mili ya already return ho chuki ho to error
            if (book == null || !book.isIssued) {
                System.out.println("Invalid return.");
                return;
            }

            // Pehle member ko dhoondhna padega jiske paas ye book issue thi
            for (Member m : members) {
                if (m.booksIssued > 0) {
                    book.returnBook(m); // Book class ka return method call
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error in returning book: " + e.getMessage());
        }
    }

    // Saari books display karne ka method
    static void showBooks() {
        try {
            System.out.println("\n--- All Books ---");
            for (Book b : books) {
                b.display(); // Book ka display() method call
            }
        } catch (Exception e) {
            System.out.println("Error in showing all books: " + e.getMessage());
        }
    }
}
