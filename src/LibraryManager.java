public interface LibraryManager {
    void addItem(LibraryItem item) throws DuplicateIdException;
    void removeItem(String id);
    void listItems();
    void addMember(Member m) throws DuplicateIdException;
    void borrowBook(String mid, String id) throws ItemUnavailableException;
    void returnBook(String mid, String id);
    void runMenu();
    void runGUI();
}