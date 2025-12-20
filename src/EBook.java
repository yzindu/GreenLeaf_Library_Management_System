public class EBook extends LibraryItem {
    private String downloadLink;
    private double fileSizeMB;

    public EBook(String itemId, String title, String author, String downloadLink, double fileSizeMB) {
        super(itemId, title, author);
        this.downloadLink = downloadLink;
        this.fileSizeMB = fileSizeMB;
    }

    public String getDownloadLink() { return downloadLink; }
    public double getFileSizeMB() { return fileSizeMB; }

    @Override
    public String getDetails() {
        return "E-Book - Size: " + fileSizeMB + "MB, Link: " + downloadLink;
    }
}