import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class GreenLeafLibraryManager implements LibraryManager {
    private ArrayList<LibraryItem> items;
    private ArrayList<Member> members;
    private final String DATA_FILE = "library_data.dat";

    public GreenLeafLibraryManager() {
        items = new ArrayList<>();
        members = new ArrayList<>();
        loadData(); // Load existing data upon startup
    }

    @Override
    public void addItem(LibraryItem item) throws DuplicateIdException {
        for (LibraryItem i : items) {
            if (i.getItemId().equals(item.getItemId())) {
                throw new DuplicateIdException("Item ID already exists: " + item.getItemId());
            }
        }
        items.add(item);
        saveData();
    }

    @Override
    public void removeItem(String id) {
        items.removeIf(item -> item.getItemId().equals(id));
        saveData();
    }

    @Override
    public void listItems() {
        System.out.println("--- Library Items ---");
        for (LibraryItem item : items) {
            System.out.println(item.toString() + " | " + item.getDetails());
        }
    }

    @Override
    public void addMember(Member m) throws DuplicateIdException {
        for (Member member : members) {
            if (member.getMemberId().equals(m.getMemberId())) {
                throw new DuplicateIdException("Member ID already exists: " + m.getMemberId());
            }
        }
        members.add(m);
        saveData();
    }

    @Override
    public void borrowBook(String mid, String id) throws ItemUnavailableException {
        Member member = findMember(mid);
        LibraryItem item = findItem(id);

        if (member != null && item != null) {
            if (!item.isAvailable()) {
                throw new ItemUnavailableException("The book is currently unavailable.");
            }
            item.setAvailable(false); // Mark as unavailable [cite: 38]
            member.borrowBook(item);
            saveData();
            System.out.println(member.getName() + " successfully borrowed " + item.getTitle());
        } else {
            System.out.println("Member or Item not found.");
        }
    }

    @Override
    public void returnBook(String mid, String id) {
        Member member = findMember(mid);
        LibraryItem item = findItem(id);

        if (member != null && item != null && !item.isAvailable()) {
            item.setAvailable(true); // Update availability [cite: 39]
            member.returnBook(item);
            saveData();
            System.out.println(member.getName() + " successfully returned " + item.getTitle());
        }
    }

    private Member findMember(String mid) {
        for (Member m : members) if (m.getMemberId().equals(mid)) return m;
        return null;
    }

    private LibraryItem findItem(String id) {
        for (LibraryItem i : items) if (i.getItemId().equals(id)) return i;
        return null;
    }

    // --- File Handling [cite: 51, 52] ---
    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(items);
            oos.writeObject(members);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            items = (ArrayList<LibraryItem>) ois.readObject();
            members = (ArrayList<Member>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    // --- Console Menu ---
    @Override
    public void runMenu() {
        // Implementation for simple console terminal testing would go here
        System.out.println("System running in GUI mode instead.");
        runGUI();
    }

    // --- GUI Interface [cite: 47, 48, 49, 50] ---
    @Override
    public void runGUI() {
        JFrame frame = new JFrame("GreenLeaf Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);

        String[] columns = {"ID", "Title", "Author", "Type", "Available"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        Runnable updateTable = () -> {
            model.setRowCount(0);
            for (LibraryItem item : items) {
                String type = item instanceof Book ? "Physical" : "E-Book";
                model.addRow(new Object[]{item.getItemId(), item.getTitle(), item.getAuthor(), type, item.isAvailable() ? "Yes" : "No"});
            }
        };
        updateTable.run();

        JPanel panel = new JPanel();
        JButton btnAddBook = new JButton("Add Book");
        JButton btnBorrow = new JButton("Borrow Book");
        JButton btnReturn = new JButton("Return Book");

        btnAddBook.addActionListener(e -> {
            try {
                String id = JOptionPane.showInputDialog("Enter Item ID:");
                String title = JOptionPane.showInputDialog("Enter Title:");
                String author = JOptionPane.showInputDialog("Enter Author:");
                String isbn = JOptionPane.showInputDialog("Enter ISBN (10-13 digits):");
                Book b = new Book(id, title, author, isbn, 2023);
                addItem(b);
                updateTable.run();
                JOptionPane.showMessageDialog(frame, "Book added successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnBorrow.addActionListener(e -> {
            try {
                String mid = JOptionPane.showInputDialog("Enter Member ID:");
                String bid = JOptionPane.showInputDialog("Enter Book ID:");
                borrowBook(mid, bid);
                updateTable.run();
                JOptionPane.showMessageDialog(frame, "Transaction completed!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });

        panel.add(btnAddBook);
        panel.add(btnBorrow);
        panel.add(btnReturn);

        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}