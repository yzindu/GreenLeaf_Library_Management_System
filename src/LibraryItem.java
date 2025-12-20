public abstract class LibraryItem implements java.io.Serializable {
    protected String itemId;
    protected String title;
    protected String author;
    protected boolean available;

    public LibraryItem(String itemId, String title, String author) {
        this.itemId = itemId;
        this.title = title;
        this.author = author;
        this.available = true; // Items are available by default
    }

    public String getItemId() { return itemId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public abstract String getDetails();

    @Override
    public String toString() {
        return "ID: " + itemId + " | Title: " + title + " | Author: " + author +
                " | Available: " + (available ? "Yes" : "No");
    }
}