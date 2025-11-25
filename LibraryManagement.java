/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagementsystem1;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author saleh
 */
public class LibraryManagement {
    static class Book {
        private String bookId;
        private String title;
        private String author;
        private boolean issued;

        public Book(String bookId, String title, String author){
            this.bookId = bookId;
            this.title = title;
            this.author = author;
            this.issued = false;
        }

        public String getBookId(){ return bookId; }
        public String getTitle(){ return title; }
        public String getAuthor(){ return author; }
        public boolean isIssued(){ return issued; }

        public void issueBook(){ issued = true; }
        public void returnBook(){ issued = false; }

        @Override
        public String toString(){
            String status = issued ? "Issued" : "Available";
            return bookId + ": " + title + " by " + author + " (" + status + ")";
        }
    }

    // -------------------- Library Class --------------------
    static class Library {
        private ArrayList<Book> books = new ArrayList<>();

        public void addBook(Book book){
            books.add(book);
        }

        public Book searchById(String id){
            for(Book b : books){
                if(b.getBookId().equals(id)) return b;
            }
            return null;
        }

        public String issueBook(String id){
            Book b = searchById(id);
            if(b == null) return "Sorry, book not found!";
            if(b.isIssued()) return "Book is already issued!";
            b.issueBook();
            return "Book issued successfully!";
        }

        public String returnBook(String id){
            Book b = searchById(id);
            if(b == null) return "Sorry, book not found!";
            if(!b.isIssued()) return "This book was not issued!";
            b.returnBook();
            return "Book returned successfully!";
        }

        public ArrayList<Book> getAllBooks(){
            return books;
        }
    }

    // -------------------- Main Interface --------------------
    static class LibraryInterface extends JFrame {
        private Library library;

        public LibraryInterface(){
            library = new Library();

            setTitle("Library Management System");
            setSize(500, 500);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            JPanel panel = new JPanel(new GridLayout(5,1,10,10));
            panel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));

            JButton addBtn = new JButton("Add Book");
            JButton showBtn = new JButton("Show All Books");
            JButton searchBtn = new JButton("Search Book");
            JButton issueBtn = new JButton("Issue Book");
            JButton returnBtn = new JButton("Return Book");

            panel.add(addBtn);
            panel.add(showBtn);
            panel.add(searchBtn);
            panel.add(issueBtn);
            panel.add(returnBtn);

            add(panel);
            setVisible(true);

            addBtn.addActionListener(e -> addBook());
            showBtn.addActionListener(e -> showBooks());
            searchBtn.addActionListener(e -> searchBook());
            issueBtn.addActionListener(e -> issueBook());
            returnBtn.addActionListener(e -> returnBook());
        }

        private void addBook(){
            String id = JOptionPane.showInputDialog(this, "Enter Book ID:");
            if(id == null || id.trim().isEmpty()) return;

            String title = JOptionPane.showInputDialog(this, "Enter Book Title:");
            if(title == null || title.trim().isEmpty()) return;

            String author = JOptionPane.showInputDialog(this, "Enter Author:");
            if(author == null || author.trim().isEmpty()) return;

            library.addBook(new Book(id.trim(), title.trim(), author.trim()));
            JOptionPane.showMessageDialog(this, "Book Added Successfully!");
        }

        private void showBooks(){
            StringBuilder sb = new StringBuilder();
            for(Book b : library.getAllBooks()){
                sb.append(b.toString()).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.length() == 0 ? "No books available." : sb.toString());
        }

        private void searchBook(){
            String id = JOptionPane.showInputDialog(this, "Enter Book ID to Search:");
            if(id == null || id.trim().isEmpty()) return;

            Book b = library.searchById(id.trim());
            if(b == null){
                JOptionPane.showMessageDialog(this, "Book not found!");
            } else {
                JOptionPane.showMessageDialog(this, b.toString());
            }
        }

        private void issueBook(){
            String id = JOptionPane.showInputDialog(this, "Enter Book ID to Issue:");
            if(id == null || id.trim().isEmpty()) return;

            String result = library.issueBook(id.trim());
            JOptionPane.showMessageDialog(this, result);
        }

        private void returnBook(){
            String id = JOptionPane.showInputDialog(this, "Enter Book ID to Return:");
            if(id == null || id.trim().isEmpty()) return;

            String result = library.returnBook(id.trim());
            JOptionPane.showMessageDialog(this, result);
        }
    }

} // END of main class



// -------------------- LOGIN FRAME --------------------
class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Login");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3,2,10,10));

        JLabel uLabel = new JLabel("Username:");
        JLabel pLabel = new JLabel("Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginBtn = new JButton("Login");

        add(uLabel); add(usernameField);
        add(pLabel); add(passwordField);
        add(new JLabel());
        add(loginBtn);

        loginBtn.addActionListener(e -> checkLogin());

        setVisible(true);
    }

    private void checkLogin() {
        String user = usernameField.getText().trim();
        String pass = new String(passwordField.getPassword());

        if(user.equals("admin") && pass.equals("12345")) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            this.dispose();
            new LibraryManagement.LibraryInterface();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password!");
        }
    }
}


// -------------------- MAIN --------------------
class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame();   // First show Login
        });
    }
}

