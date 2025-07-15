package javArena;
import java.util.*;

interface Loanable {
    void issue(Member member);
    void returnBook(Member member);
}

interface Displayable {
    void display();
}

class Book implements Loanable, Displayable {
    String id, title, author;
    boolean isIssued = false;

    Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public void issue(Member member) {
        if (!isIssued && member.booksIssued < member.maxBooks) {
            isIssued = true;
            member.booksIssued++;
            System.out.println("Book issued.");
        } else if (isIssued) {
            System.out.println("Book already issued.");
        } else {
            System.out.println("Member has reached issue limit.");
        }
    }

    public void returnBook(Member member) {
        if (isIssued) {
            isIssued = false;
            member.booksIssued--;
            System.out.println("Book returned.");
        } else {
            System.out.println("This book was not issued.");
        }
    }

    public void display() {
        System.out.println(this);
    }

    public String toString() {
        return id + " - " + title + " by " + author + (isIssued ? " (Issued)" : "");
    }
}

class Member implements Displayable {
    String id, name;
    int maxBooks;
    int booksIssued = 0;

    Member(String id, String name, int maxBooks) {
        this.id = id;
        this.name = name;
        this.maxBooks = maxBooks;
    }

    Member(String id, String name) {
        this(id, name, 5);
    }

    public void display() {
        System.out.println(this);
    }

    public String toString() {
        return id + " - " + name + " (Issued: " + booksIssued + "/" + maxBooks + ")";
    }
}

public class main2 {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Book> books = new ArrayList<>();
    static ArrayList<Member> members = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            try {
                System.out.println("\n1. Add Book\n2. Register Member\n3. Issue Book\n4. Return Book\n5. Show All Books\n6. Exit");
                System.out.print("Choose: ");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1 -> addBook();
                    case 2 -> registerMember();
                    case 3 -> issueBook();
                    case 4 -> returnBook();
                    case 5 -> showBooks();
                    case 6 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input!");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Something went wrong: " + e.getMessage());
            }
        }
    }

    static void addBook() {
        try {
            System.out.print("Enter Book ID: ");
            String id = sc.nextLine();
            System.out.print("Enter Title: ");
            String title = sc.nextLine();
            System.out.print("Enter Author: ");
            String author = sc.nextLine();
            books.add(new Book(id, title, author));
            System.out.println("Book added.");
        } catch (Exception e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    static void registerMember() {
        try {
            System.out.print("Enter Member ID: ");
            String id = sc.nextLine();
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            members.add(new Member(id, name));
            System.out.println("Member registered with default limit of 5 books.");
        } catch (Exception e) {
            System.out.println("Error in registering member: " + e.getMessage());
        }
    }

    static void issueBook() {
        try {
            System.out.print("Enter Book ID: ");
            String bid = sc.nextLine();
            System.out.print("Enter Member ID: ");
            String mid = sc.nextLine();
            Book book = null;
            for (Book b : books) {
                if (b.id.equals(bid)) {
                    book = b;
                    break;
                }
            }
            Member member = null;
            for (Member m : members) {
                if (m.id.equals(mid)) {
                    member = m;
                    break;
                }
            }
            if (book == null || member == null) {
                System.out.println("Book or Member not found.");
                return;
            }
            book.issue(member);
        } catch (Exception e) {
            System.out.println("Error in issuing book: " + e.getMessage());
        }
    }

    static void returnBook() {
        try {
            System.out.print("Enter Book ID: ");
            String bid = sc.nextLine();
            Book book = null;
            for (Book b : books) {
                if (b.id.equals(bid)) {
                    book = b;
                    break;
                }
            }
            if (book == null || !book.isIssued) {
                System.out.println("Invalid return.");
                return;
            }
            for (Member m : members) {
                if (m.booksIssued > 0) {
                    book.returnBook(m);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error in returning book: " + e.getMessage());
        }
    }

    static void showBooks() {
        try {
            System.out.println("\n--- All Books ---");
            for (Book b : books) {
                b.display();
            }
        } catch (Exception e) {
            System.out.println("Error in showing all books: " + e.getMessage());
        }
    }
}
