public class Main {
    public static void main(String[] args) {
        GreenLeafLibraryManager manager = new GreenLeafLibraryManager();

        // Optionally populate with dummy data if empty for testing
        try {
            manager.addMember(new Member("M001", "John Doe", "john@email.com"));
        } catch (Exception e) {
            // Member likely already exists
        }

        // Run the Graphical User Interface [cite: 20]
        manager.runGUI();
    }
}