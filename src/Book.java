public class Book extends LibraryItem {
    private String isbn;
    private int publishedYear;

    public Book(String itemId, String title, String author, String isbn, int publishedYear) throws ValidationException {
        super(itemId, title, author);
        if (!isbn.matches("^[0-9]{10,13}$")) {
            throw new ValidationException("Invalid ISBN. Must be 10 to 13 digits only.");
        }
        this.isbn = isbn;
        this.publishedYear = publishedYear;
    }

    public String getIsbn() { return isbn; }
    public int getPublishedYear() { return publishedYear; }

    @Override
    public String getDetails() {
        return "Physical Book - ISBN: " + isbn + ", Published: " + publishedYear;
    }
}