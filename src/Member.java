import java.util.ArrayList;

public class Member implements java.io.Serializable {
    private String memberId;
    private String name;
    private String email;
    private ArrayList<LibraryItem> borrowedBooks;

    public Member(String memberId, String name, String email) throws ValidationException {
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ValidationException("Invalid email format.");
        }
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public ArrayList<LibraryItem> getBorrowedBooks() { return borrowedBooks; }

    public void borrowBook(LibraryItem item) {
        borrowedBooks.add(item);
    }

    public void returnBook(LibraryItem item) {
        borrowedBooks.remove(item);
    }

    @Override
    public String toString() {
        return "Member ID: " + memberId + " | Name: " + name + " | Borrowed Items: " + borrowedBooks.size();
    }
}