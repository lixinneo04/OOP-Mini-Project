import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class LibraryManagementSystem {
    private static Library library = new Library("Perpustakaan RAJA ZARITH SOFIAH", "123 UTM");
    private static Scanner scanner = new Scanner(System.in);
    private static final String LIBRARIAN_PASSWORD = "lib123";

    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println("      Welcome to the Library System  ");
        System.out.println("====================================");
        // Chapter 5: add() for book management
        library.addBook(new Book("B001", "Harry Potter Series", "J.K. Rowling", 2000, 5));
        library.addBook(new Book("B002", "The Lord of the Rings", "J.R.R. Tolkien", 2001, 4));
        library.addBook(new Book("B003", "The Alchemist", "Paulo Coelho", 1988, 6));
        
        // Main loop for user login and menu selection
        while (true) {
            System.out.println("\n====================================");
            System.out.println("Are you a member or librarian? (Enter your ID, or 0 to exit)");
            System.out.println("(Librarian ID: L001, Member ID: A... or S...)");
            System.out.println("====================================");
            String inputId = scanner.nextLine();
            if (inputId.equals("0")) {
                System.out.println("Exiting system...");
                return;
            }
            if (inputId.equals("L001")) {
                librarianMenu(); // Librarian menu
            } else if (inputId.startsWith("A") || inputId.startsWith("S")) {
                Member member = library.findMember(inputId);
                if (member == null) {
                    member = registerMember(inputId); // Register new member if not found
                    if (member == null) continue;
                } else {
                    System.out.println("You successfully logged in.");
                }
                memberMenu(member); // Member menu
            } else {
                System.out.println("Invalid ID format. Librarian ID is 'L001', Student starts with 'A', Staff with 'S'.");
            }
        }
    }

    // Chapter 9: Exception Handling for invalid input
    private static int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    private static void librarianMenu() {
        System.out.print("Enter librarian password: ");
        String password = scanner.nextLine();
        if (!password.equals(LIBRARIAN_PASSWORD)) {
            System.out.println("Incorrect password. Access denied.");
            return;
        }
        Librarian librarian = new Librarian("L001", "Admin Librarian", "admin@library.com");
        while (true) {
            System.out.println("\n========== Librarian Menu ==========");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Check Book Availability");
            System.out.println("4. Edit Book Details");
            System.out.println("5. View All Books");
            System.out.println("6. Check Member Details");
            System.out.println("7. View All Member Details");
            System.out.println("0. Logout");
            System.out.println("====================================");
            int choice = getIntInput();
            switch (choice) {
                case 1:
                    addBook(librarian);
                    break;
                case 2:
                    removeBook(librarian);
                    break;
                case 3:
                    checkAvailability();
                    break;
                case 4:
                    editBook(librarian);
                    break;
                case 5:
                    library.displayAllBooks();
                    break;
                case 6:
                    checkMember(librarian);
                    break;
                case 7:
                    viewAllMemberDetails();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void checkMember(Librarian librarian) {
        System.out.print("Enter Member ID to check: ");
        String memberId = scanner.nextLine();
        Member member = library.findMember(memberId);
        if (member != null) {
            System.out.println("\n--- Member Details ---");
            System.out.println("ID: " + member.getId());
            System.out.println("Name: " + member.getName());
            System.out.println("Email: " + member.getEmail());
            if (member instanceof StudentMember) {
                System.out.println("Faculty: " + ((StudentMember) member).getFaculty());
            } else if(member instanceof StaffMember) {
                System.out.println("Department: " + ((StaffMember) member).getDepartment());
            }
            System.out.println("Type: " + member.getMemberType());
            System.out.println("Max Books Allowed: " + member.getMaxBooks());
            List<BorrowRecord> records = library.getBorrowRecords(member);
            if (records.isEmpty()) {
                System.out.println("No books currently borrowed.");
            } else {
                System.out.println("Borrowed Books:");
                for (BorrowRecord record : records) {
                    System.out.println(record);
                }
            }
        } else {
            System.out.println("Member not found.");
        }
    }

    private static Member registerMember(String id) {
        System.out.println("\n=== Member Registration ===");
        if (!(id.startsWith("A") || id.startsWith("S"))) {
            System.out.println("Invalid ID. Student ID must start with 'A', Staff with 'S'.");
            return null;
        }
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        String faculty = null;
        String department = null;
        if (id.startsWith("A")) {
            System.out.print("Enter Faculty: ");
            faculty = scanner.nextLine();
            StudentMember student = new StudentMember(id, name, email, id, faculty);
            library.registerMember(student);
            System.out.println("Student member registered successfully!");
            return student;
        } else {
            System.out.print("Enter Department: ");
            department = scanner.nextLine();
            StaffMember staff = new StaffMember(id, name, email, department);
            library.registerMember(staff);
            System.out.println("Staff member registered successfully!");
            return staff;
        }
    }

    private static void addBook(Librarian librarian) {
        System.out.println("\n=== Add New Book ===");
        System.out.print("Enter Book ID (start with 'B'): ");
        String id = scanner.nextLine();
        if (!id.startsWith("B")) {
            System.out.println("Book ID must start with 'B'.");
            return;
        }
        if (library.findBook(id) != null) {
            System.out.println("A book with this ID already exists. Please use a unique Book ID.");
            return;
        }
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Author: ");
        String author = scanner.nextLine();
        System.out.print("Enter Publication Year: ");
        int year = getIntInput();
        System.out.print("Enter Number of Copies: ");
        int copies = getIntInput();
        librarian.addBook(library, new Book(id, title, author, year, copies));
        System.out.println("Book added successfully!");
    }

    private static void memberMenu(Member member) {
        while (true) {
            System.out.println("\n========== Member Menu =============");
            System.out.println("1. Check Account Status");
            System.out.println("2. Search Books");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. View All Books");
            System.out.println("0. Logout");
            System.out.println("====================================");
            int choice = getIntInput();
            try {
                switch (choice) {
                    case 1:
                        checkAccountStatus(member); // Show member's account status
                        break;
                    case 2:
                        searchBooks(); // Search for books
                        break;
                    case 3:
                        borrowBook(member); // Borrow a book
                        break;
                    case 4:
                        returnBook(member); // Return a book
                        break;
                    case 5:
                        library.displayAllBooks(); // Display all books in the library
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (LibraryException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void borrowBook(Member member) throws LibraryException {
        List<BorrowRecord> records = library.getBorrowRecords(member);
        int maxCanBorrow = member.getMaxBooks() - records.size();
        if (maxCanBorrow <= 0) {
            System.out.println("You have reached your book limit. Please return a book before borrowing another.");
            return;
        }
        System.out.print("\nEnter book title or author to borrow: ");
        String query = scanner.nextLine();
        List<Book> results = library.searchBooks(query);
        if (results.isEmpty()) {
            System.out.println("No books found matching your search.");
            return;
        }
        System.out.println("\nSearch Results:");
        for (int i = 0; i < results.size(); i++) {
            Book book = results.get(i);
            System.out.println((i+1) + ". " + book.getId() + ": " + book.getTitle() + " by " + book.getAuthor() + " (" + book.getAvailableCopies() + " available)");
        }
        System.out.print("Select which book to borrow(Enter the number): ");
        int choice = getIntInput();
        if (choice < 1 || choice > results.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        Book selectedBook = results.get(choice - 1);
        int available = selectedBook.getAvailableCopies();
        int maxCopies = Math.min(available, maxCanBorrow);
        if (maxCopies <= 0) {
            System.out.println("Invalid number of copies.");
            return;
        }
        System.out.print("How many copies would you like to borrow? (1-" + maxCopies + "): ");
        int numCopies = getIntInput();
        if (numCopies < 1 || numCopies > maxCopies) {
            System.out.println("You have reached your book limit.");
            return;
        }
        int borrowed = 0;
        for (int i = 0; i < numCopies; i++) {
            try {
                member.borrowBook(library, selectedBook.getId());
                borrowed++;
            } catch (LibraryException e) {
                if (e.getMessage().contains("book limit")) {
                    System.out.println("You have reached your book limit. Please return a book before borrowing another.");
                } else {
                    System.out.println("Error borrowing copy: " + e.getMessage());
                }
                break;
            }
        }
        if (borrowed > 0) {
            System.out.println("Successfully borrowed " + borrowed + " copy/copies of '" + selectedBook.getTitle() + "'. Enjoy your reading.");
        }
    }

    private static void returnBook(Member member) throws LibraryException {
        List<BorrowRecord> records = library.getBorrowRecords(member);
        if (records.isEmpty()) {
            System.out.println("You have no books to return.");
            return;
        }
        // Group borrowed books by Book ID and count copies
        Map<String, List<BorrowRecord>> bookToRecords = new LinkedHashMap<>();
        for (BorrowRecord record : records) {
            String bookId = record.getBook().getId();
            bookToRecords.computeIfAbsent(bookId, k -> new ArrayList<>()).add(record);
        }
        List<String> bookIds = new ArrayList<>(bookToRecords.keySet());
        System.out.println("\nYour Borrowed Books:");
        for (int i = 0; i < bookIds.size(); i++) {
            Book book = bookToRecords.get(bookIds.get(i)).get(0).getBook();
            int count = bookToRecords.get(bookIds.get(i)).size();
            System.out.println((i + 1) + ". " + book.getId() + ": " + book.getTitle() + " by " + book.getAuthor() + " (Borrowed: " + count + ")");
        }
        System.out.print("Select which book to return (Enter the number): ");
        int choice = getIntInput();
        if (choice < 1 || choice > bookIds.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        String selectedBookId = bookIds.get(choice - 1);
        List<BorrowRecord> selectedRecords = bookToRecords.get(selectedBookId);
        int borrowedCount = selectedRecords.size();
        System.out.print("How many copies would you like to return? (1-" + borrowedCount + "): ");
        int numCopies = getIntInput();
        if (numCopies < 1 || numCopies > borrowedCount) {
            System.out.println("You can only return up to " + borrowedCount + " copy/copies of the selected book.");
            return;
        }
        int returned = 0;
        for (int i = 0; i < numCopies; i++) {
            BorrowRecord recordToReturn = selectedRecords.get(i);
            if (LocalDate.now().isAfter(recordToReturn.getDueDate())) {
                long daysLate = ChronoUnit.DAYS.between(recordToReturn.getDueDate(), LocalDate.now());
                double penalty = daysLate * 0.50; // $0.50 per day late
                System.out.printf("This book is %d days late. Penalty: $%.2f%n", daysLate, penalty);
                System.out.print("Pay penalty now? (Y/N): ");
                String response = scanner.nextLine();
                if (!response.equalsIgnoreCase("Y")) {
                    System.out.println("Cannot return book without paying penalty.");
                    break;
                }
                System.out.printf("Penalty of $%.2f paid.%n", penalty);
            }
            member.returnBook(library, selectedBookId);
            returned++;
        }
        if (returned > 0) {
            Book book = selectedRecords.get(0).getBook();
            System.out.println("Successfully returned " + returned + " copy/copies of '" + book.getTitle() + "'. Thank you.");
        }
    }

    private static void removeBook(Librarian librarian) {
        System.out.print("\nEnter Book ID to remove: ");
        String bookId = scanner.nextLine();
        if (librarian.removeBook(library, bookId)) {
            System.out.println("Book removed successfully!");
        } else {
            System.out.println("Book not found or could not be removed.");
        }
    }

    private static void checkAvailability() {
        System.out.print("\nEnter Book ID to check: ");
        String bookId = scanner.nextLine();
        Book book = library.findBook(bookId);
        if (book != null) {
            System.out.println("Book: " + book.getTitle());
            System.out.println("Available Copies: " + book.getAvailableCopies());
        } else {
            System.out.println("Book not found.");
        }
    }

    private static void editBook(Librarian librarian) {
        System.out.print("\nEnter Book ID to edit: ");
        String bookId = scanner.nextLine();
        Book book = library.findBook(bookId);
        
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        
        System.out.println("\nCurrent Details:");
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Year: " + book.getYear());
        System.out.println("Copies: " + book.getTotalCopies());
        
        System.out.println("\nEnter new details (leave blank to keep current):");
        System.out.print("New Title: ");
        String title = scanner.nextLine();
        System.out.print("New Author: ");
        String author = scanner.nextLine();
        System.out.print("New Year: ");
        String yearStr = scanner.nextLine();
        System.out.print("New Copies: ");
        String copiesStr = scanner.nextLine();
        
        if (!title.isEmpty()) book.setTitle(title);
        if (!author.isEmpty()) book.setAuthor(author);
        if (!yearStr.isEmpty()) book.setYear(Integer.parseInt(yearStr));
        if (!copiesStr.isEmpty()) {
            int newCopies = Integer.parseInt(copiesStr);
            int difference = newCopies - book.getTotalCopies();
            book.setTotalCopies(newCopies);
            book.setAvailableCopies(book.getAvailableCopies() + difference);
        }
        
        System.out.println("Book details updated successfully!");
    }

    private static void checkAccountStatus(Member member) {
        System.out.println("\n=== Account Status ===");
        System.out.println("Member ID: " + member.getId());
        System.out.println("Name: " + member.getName());
        System.out.println("Type: " + member.getMemberType());
        System.out.println("Max Books Allowed: " + member.getMaxBooks());
        List<BorrowRecord> records = library.getBorrowRecords(member);
        if (records.isEmpty()) {
            System.out.println("No books currently borrowed.");
        } else {
            System.out.println("\nBorrowed Books:");
            int i = 1;
            for (BorrowRecord record : records) {
                Book book = record.getBook();
                System.out.println(i + ". Title: " + book.getTitle() + ", Author: " + book.getAuthor() + ", Due Date: " + record.getDueDate());
                i++;
            }
        }
    }

    private static void searchBooks() {
        System.out.print("\nEnter search term (title or author): ");
        String query = scanner.nextLine();
        List<Book> results = library.searchBooks(query);
        
        if (results.isEmpty()) {
            System.out.println("No books found matching your search.");
        } else {
            System.out.println("\nSearch Results:");
            for (Book book : results) {
                System.out.println(book.getId() + ": " + book.getTitle() + 
                                 " by " + book.getAuthor() + 
                                 " (" + book.getAvailableCopies() + " available)");
            }
        }
    }

    private static void viewAllMemberDetails() {
        ArrayList<Member> members = library.getMembers();
        if (members.isEmpty()) {
            System.out.println("No members registered.");
            return;
        }
        System.out.println("\n=== All Member Details ===");
        for (Member member : members) {
            System.out.println("ID: " + member.getId());
            System.out.println("Name: " + member.getName());
            System.out.println("Email: " + member.getEmail());
            if (member instanceof StudentMember) {
                System.out.println("Faculty: " + ((StudentMember) member).getFaculty());
            } else if (member instanceof StaffMember) {
                System.out.println("Department: " + ((StaffMember) member).getDepartment());
            }
            System.out.println("Type: " + member.getMemberType());
            System.out.println("Max Books Allowed: " + member.getMaxBooks());
            
            List<BorrowRecord> records = library.getBorrowRecords(member);
            if (records.isEmpty()) {
                System.out.println("Borrowed Books: None");
            } else {
                System.out.println("Borrowed Books:");
                int i = 1;
                for (BorrowRecord record : records) {
                    Book book = record.getBook();
                    System.out.println("  " + i + ". " + book.getTitle() + " by " + book.getAuthor() + " (Due: " + record.getDueDate() + ")");
                    i++;
                }
            }
            System.out.println("-----------------------------");
        }
    }
}

class LibraryException extends Exception {
    public LibraryException(String message) {
        super(message);
    }
}

// Chapter 6: Association - Library has Books and Members
class Library {
    private String name;
    private String address;
    // Chapter 5: ArrayList<Book> for books
    private ArrayList<Book> books;
    // Chapter 5: ArrayList<Member> for members
    private ArrayList<Member> members;
    // Chapter 5: HashMap for tracking borrow records
    private HashMap<String, List<BorrowRecord>> borrowRecords;
    
    public Library(String name, String address) {
        this.name = name;
        this.address = address;
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        this.borrowRecords = new HashMap<>();
    }
    
    // Chapter 5: add()/remove() for book management
    public void addBook(Book book) {
        if (!books.contains(book)) {
            books.add(book);
        }
    }
    public boolean removeBook(String bookId) {
        return books.removeIf(book -> book.getId().equals(bookId));
    }
    public Book findBook(String bookId) {
        for (Book book : books) {
            if (book.getId().equals(bookId)) {
                return book;
            }
        }
        return null;
    }
    public List<Book> searchBooks(String query) {
        List<Book> results = new ArrayList<>();
        String searchTerm = query.toLowerCase();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(searchTerm) || 
                book.getAuthor().toLowerCase().contains(searchTerm)) {
                results.add(book);
            }
        }
        return results;
    }
    
    // Member management methods
    public void registerMember(Member member) {
        if (!members.contains(member)) {
            members.add(member);
        }
    }
    
    public Member findMember(String memberId) {
        for (Member member : members) {
            if (member.getId().equals(memberId)) {
                return member;
            }
        }
        return null;
    }
    
    // Borrow/return operations
    public void borrowBook(Member member, String bookId) throws LibraryException {
        Book book = findBook(bookId);
        if (book == null) {
            throw new LibraryException("Book not found");
        }
        if (book.getAvailableCopies() <= 0) {
            throw new LibraryException("No available copies");
        }
        String recordKey = member.getId() + bookId;
        List<BorrowRecord> recordList = borrowRecords.getOrDefault(recordKey, new ArrayList<>());
        int totalBorrowed = 0;
        for (List<BorrowRecord> list : borrowRecords.values()) {
            for (BorrowRecord r : list) {
                if (r.getMember().getId().equals(member.getId())) {
                    totalBorrowed++;
                }
            }
        }
        if (totalBorrowed >= member.getMaxBooks()) {
            throw new LibraryException("You have reached your book limit. Please return a book before borrowing another.");
        }
        BorrowRecord record = new BorrowRecord(member, book);
        recordList.add(record);
        borrowRecords.put(recordKey, recordList);
        book.decreaseCopies();
    }
    
    public void returnBook(Member member, String bookId) throws LibraryException {
        String recordKey = member.getId() + bookId;
        List<BorrowRecord> recordList = borrowRecords.get(recordKey);
        if (recordList == null || recordList.isEmpty()) {
            throw new LibraryException("No active borrow record found");
        }
        BorrowRecord record = recordList.remove(0);
        Book book = record.getBook();
        book.increaseCopies();
        if (recordList.isEmpty()) {
            borrowRecords.remove(recordKey);
        } else {
            borrowRecords.put(recordKey, recordList);
        }
    }
    
    // Record management methods
    public List<BorrowRecord> getBorrowRecords(Member member) {
        List<BorrowRecord> records = new ArrayList<>();
        for (Map.Entry<String, List<BorrowRecord>> entry : borrowRecords.entrySet()) {
            if (entry.getKey().startsWith(member.getId())) {
                records.addAll(entry.getValue());
            }
        }
        return records;
    }
    
    // Display methods
    public void displayAllBooks() {
        System.out.println("\n=== All Books in " + name + " ===");
        System.out.printf("%-10s %-30s %-20s %-6s %s\n", 
                         "ID", "Title", "Author", "Year", "Availability");
        System.out.println("----------------------------------------------------------------");
        
        for (Book book : books) {
            System.out.printf("%-10s %-30s %-20s %-6d %d/%d\n",
                            book.getId(),
                            book.getTitle(),
                            book.getAuthor(),
                            book.getYear(),
                            book.getAvailableCopies(),
                            book.getTotalCopies());
        }
    }
    
    public void displayLibraryStatus() {
        System.out.println("\n=== Library Status ===");
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("\nTotal Books: " + books.size());
        System.out.println("Total Members: " + members.size());
        System.out.println("Active Loans: " + borrowRecords.size());
    }
    
    // Getters
    public String getName() { return name; }
    public String getAddress() { return address; }
    public ArrayList<Book> getBooks() { return new ArrayList<>(books); }
    public ArrayList<Member> getMembers() { return new ArrayList<>(members); }
}

// Chapter 6: Aggregation - Librarian manages Books
class Librarian {
    private String id;
    private String name;
    private String email;
    public Librarian(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    public void addBook(Library library, Book book) {
        library.addBook(book);
    }
    public boolean removeBook(Library library, String bookId) {
        return library.removeBook(bookId);
    }
}

// Chapter 6: Composition - BorrowRecord composed of Member and Book
class BorrowRecord {
    private Member member;
    private Book book;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    
    public BorrowRecord(Member member, Book book) {
        this.member = member;
        this.book = book;
        this.borrowDate = LocalDate.now();
        this.dueDate = borrowDate.plusDays(member.getLoanPeriodDays());
    }
    
    // Getters
    public Member getMember() { return member; }
    public Book getBook() { return book; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    
    @Override
    public String toString() {
        return book.getTitle() + " (Due: " + dueDate + ")";
    }
}

class Book {
    private String id;
    private String title;
    private String author;
    private int year;
    private int totalCopies;
    private int availableCopies;

    public Book(String id, String title, String author, int year, int copies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.totalCopies = copies;
        this.availableCopies = copies;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public int getTotalCopies() { return totalCopies; }
    public int getAvailableCopies() { return availableCopies; }

    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setYear(int year) { this.year = year; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }

    // Methods to manage copies
    public void decreaseCopies() {
        if (availableCopies > 0) {
            availableCopies--;
        }
    }

    public void increaseCopies() {
        if (availableCopies < totalCopies) {
            availableCopies++;
        }
    }

    @Override
    public String toString() {
        return title + " by " + author + " (" + year + ") - " +
               availableCopies + "/" + totalCopies + " available";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id.equals(book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

// Chapter 7: Inheritance
// Member
// ├── StudentMember
// └── StaffMember
abstract class Member {
    private String id;
    private String name;
    private String email;
    public Member(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    // Chapter 7: Abstract methods
    public abstract String getMemberType();
    public abstract int getMaxBooks();
    public abstract int getLoanPeriodDays();
    public void borrowBook(Library library, String bookId) throws LibraryException {
        library.borrowBook(this, bookId);
    }
    public void returnBook(Library library, String bookId) throws LibraryException {
        library.returnBook(this, bookId);
    }
}

class StudentMember extends Member {
    private String studentId;
    private String faculty;
    public StudentMember(String id, String name, String email, String studentId, String faculty) {
        super(id, name, email);
        this.studentId = studentId;
        this.faculty = faculty;
    }
    @Override
    public String getMemberType() { return "Student"; }
    @Override
    public int getMaxBooks() { return 3; }
    @Override
    public int getLoanPeriodDays() { return 14; }
    public String getFaculty() { return faculty; }
}

class StaffMember extends Member {
    private String department;
    public StaffMember(String id, String name, String email, String department) {
        super(id, name, email);
        this.department = department;
    }
    @Override
    public String getMemberType() { return "Staff"; }
    @Override
    public int getMaxBooks() { return 5; }
    @Override
    public int getLoanPeriodDays() { return 28; }
    public String getDepartment() { return department; }
}