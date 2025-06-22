import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class LibraryManagementSystem {
   private static Library library = new Library("City Central Library", "123 Library Lane");
   private static Scanner scanner;
   private static final String LIBRARIAN_PASSWORD = "lib123";

   public static void main(String[] var0) {
      JFrame var1 = new JFrame("Library Management System");
      var1.setSize(400, 200);
      var1.setDefaultCloseOperation(3);
      JLabel var2 = new JLabel("Welcome to the Library!", 0);
      var1.add(var2);
      var1.setVisible(true);
      System.out.println("====================================");
      System.out.println("      Welcome to the Library System  ");
      System.out.println("====================================");
      library.addBook(new Book("B001", "Harry Potter Series", "J.K. Rowling", 2000, 5));
      library.addBook(new Book("B002", "The Lord of the Rings", "J.R.R. Tolkien", 2001, 4));
      library.addBook(new Book("B003", "The Alchemist", "Paulo Coelho", 1988, 6));

      while(true) {
         while(true) {
            System.out.println("\n====================================");
            System.out.println("Are you a member or librarian? (Enter your ID, or 0 to exit)");
            System.out.println("(Librarian ID: L001, Member ID: A... or S...)");
            System.out.println("====================================");
            String var3 = scanner.nextLine();
            if (var3.equals("0")) {
               System.out.println("Exiting system...");
               return;
            }

            if (var3.equals("L001")) {
               librarianMenu();
            } else if (!var3.startsWith("A") && !var3.startsWith("S")) {
               System.out.println("Invalid ID format. Librarian ID is 'L001', Student starts with 'A', Staff with 'S'.");
            } else {
               Member var4 = library.findMember(var3);
               if (var4 == null) {
                  var4 = registerMember(var3);
                  if (var4 == null) {
                     continue;
                  }
               } else {
                  System.out.println("You successfully logged in.");
               }

               memberMenu(var4);
            }
         }
      }
   }

   private static int getIntInput() {
      while(true) {
         try {
            return Integer.parseInt(scanner.nextLine());
         } catch (NumberFormatException var1) {
            System.out.print("Invalid input. Please enter a number: ");
         }
      }
   }

   private static void librarianMenu() {
      System.out.print("Enter librarian password: ");
      String var0 = scanner.nextLine();
      if (!var0.equals("lib123")) {
         System.out.println("Incorrect password. Access denied.");
      } else {
         Librarian var1 = new Librarian("L001", "Admin Librarian", "admin@library.com");

         while(true) {
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
            int var2 = getIntInput();
            switch(var2) {
            case 0:
               return;
            case 1:
               addBook(var1);
               break;
            case 2:
               removeBook(var1);
               break;
            case 3:
               checkAvailability();
               break;
            case 4:
               editBook(var1);
               break;
            case 5:
               library.displayAllBooks();
               break;
            case 6:
               checkMember(var1);
               break;
            case 7:
               viewAllMemberDetails();
               break;
            default:
               System.out.println("Invalid choice. Please try again.");
            }
         }
      }
   }

   private static void checkMember(Librarian var0) {
      System.out.print("Enter Member ID to check: ");
      String var1 = scanner.nextLine();
      Member var2 = library.findMember(var1);
      if (var2 != null) {
         System.out.println("\n--- Member Details ---");
         System.out.println("ID: " + var2.getId());
         System.out.println("Name: " + var2.getName());
         System.out.println("Email: " + var2.getEmail());
         System.out.println("Type: " + var2.getMemberType());
         System.out.println("Max Books Allowed: " + var2.getMaxBooks());
         List var3 = library.getBorrowRecords(var2);
         if (var3.isEmpty()) {
            System.out.println("No books currently borrowed.");
         } else {
            System.out.println("Borrowed Books:");
            Iterator var4 = var3.iterator();

            while(var4.hasNext()) {
               BorrowRecord var5 = (BorrowRecord)var4.next();
               System.out.println(var5);
            }
         }
      } else {
         System.out.println("Member not found.");
      }

   }

   private static Member registerMember(String var0) {
      System.out.println("\n=== Member Registration ===");
      if (!var0.startsWith("A") && !var0.startsWith("S")) {
         System.out.println("Invalid ID. Student ID must start with 'A', Staff with 'S'.");
         return null;
      } else {
         System.out.print("Enter Name: ");
         String var1 = scanner.nextLine();
         System.out.print("Enter Email: ");
         String var2 = scanner.nextLine();
         if (var0.startsWith("A")) {
            StudentMember var4 = new StudentMember(var0, var1, var2, var0);
            library.registerMember(var4);
            System.out.println("Student member registered successfully!");
            return var4;
         } else {
            StaffMember var3 = new StaffMember(var0, var1, var2, "");
            library.registerMember(var3);
            System.out.println("Staff member registered successfully!");
            return var3;
         }
      }
   }

   private static void addBook(Librarian var0) {
      System.out.println("\n=== Add New Book ===");
      System.out.print("Enter Book ID (start with 'B'): ");
      String var1 = scanner.nextLine();
      if (!var1.startsWith("B")) {
         System.out.println("Book ID must start with 'B'.");
      } else if (library.findBook(var1) != null) {
         System.out.println("A book with this ID already exists. Please use a unique Book ID.");
      } else {
         System.out.print("Enter Title: ");
         String var2 = scanner.nextLine();
         System.out.print("Enter Author: ");
         String var3 = scanner.nextLine();
         System.out.print("Enter Publication Year: ");
         int var4 = getIntInput();
         System.out.print("Enter Number of Copies: ");
         int var5 = getIntInput();
         var0.addBook(library, new Book(var1, var2, var3, var4, var5));
         System.out.println("Book added successfully!");
      }
   }

   private static void memberMenu(Member var0) {
      while(true) {
         System.out.println("\n========== Member Menu =============");
         System.out.println("1. Check Account Status");
         System.out.println("2. Search Books");
         System.out.println("3. Borrow Book");
         System.out.println("4. Return Book");
         System.out.println("5. View All Books");
         System.out.println("0. Logout");
         System.out.println("====================================");
         int var1 = getIntInput();

         try {
            switch(var1) {
            case 0:
               return;
            case 1:
               checkAccountStatus(var0);
               break;
            case 2:
               searchBooks();
               break;
            case 3:
               borrowBook(var0);
               break;
            case 4:
               returnBook(var0);
               break;
            case 5:
               library.displayAllBooks();
               break;
            default:
               System.out.println("Invalid choice. Please try again.");
            }
         } catch (LibraryException var3) {
            System.out.println("Error: " + var3.getMessage());
         }
      }
   }

   private static void borrowBook(Member var0) throws LibraryException {
      List var1 = library.getBorrowRecords(var0);
      int var2 = var0.getMaxBooks() - var1.size();
      if (var2 <= 0) {
         System.out.println("You have reached your book limit. Please return a book before borrowing another.");
      } else {
         System.out.print("\nEnter book title or author to borrow: ");
         String var3 = scanner.nextLine();
         List var4 = library.searchBooks(var3);
         if (var4.isEmpty()) {
            System.out.println("No books found matching your search.");
         } else {
            System.out.println("\nSearch Results:");

            int var5;
            Book var6;
            for(var5 = 0; var5 < var4.size(); ++var5) {
               var6 = (Book)var4.get(var5);
               System.out.println(var5 + 1 + ". " + var6.getId() + ": " + var6.getTitle() + " by " + var6.getAuthor() + " (" + var6.getAvailableCopies() + " available)");
            }

            System.out.print("Select which book to borrow(Enter the number): ");
            var5 = getIntInput();
            if (var5 >= 1 && var5 <= var4.size()) {
               var6 = (Book)var4.get(var5 - 1);
               int var7 = var6.getAvailableCopies();
               int var8 = Math.min(var7, var2);
               if (var8 <= 0) {
                  System.out.println("Invalid number of copies.");
               } else {
                  System.out.print("How many copies would you like to borrow? (1-" + var8 + "): ");
                  int var9 = getIntInput();
                  if (var9 >= 1 && var9 <= var8) {
                     int var10 = 0;

                     for(int var11 = 0; var11 < var9; ++var11) {
                        try {
                           var0.borrowBook(library, var6.getId());
                           ++var10;
                        } catch (LibraryException var13) {
                           if (var13.getMessage().contains("book limit")) {
                              System.out.println("You have reached your book limit. Please return a book before borrowing another.");
                           } else {
                              System.out.println("Error borrowing copy: " + var13.getMessage());
                           }
                           break;
                        }
                     }

                     if (var10 > 0) {
                        System.out.println("Successfully borrowed " + var10 + " copy/copies of '" + var6.getTitle() + "'. Enjoy your reading.");
                     }

                  } else {
                     System.out.println("You have reached your book limit.");
                  }
               }
            } else {
               System.out.println("Invalid selection.");
            }
         }
      }
   }

   private static void returnBook(Member var0) throws LibraryException {
      List var1 = library.getBorrowRecords(var0);
      if (var1.isEmpty()) {
         System.out.println("You have no books to return.");
      } else {
         LinkedHashMap var2 = new LinkedHashMap();
         Iterator var3 = var1.iterator();

         String var5;
         while(var3.hasNext()) {
            BorrowRecord var4 = (BorrowRecord)var3.next();
            var5 = var4.getBook().getId();
            ((List)var2.computeIfAbsent(var5, (var0x) -> {
               return new ArrayList();
            })).add(var4);
         }

         ArrayList var17 = new ArrayList(var2.keySet());
         System.out.println("\nYour Borrowed Books:");

         int var18;
         for(var18 = 0; var18 < var17.size(); ++var18) {
            Book var19 = ((BorrowRecord)((List)var2.get(var17.get(var18))).get(0)).getBook();
            int var6 = ((List)var2.get(var17.get(var18))).size();
            System.out.println(var18 + 1 + ". " + var19.getId() + ": " + var19.getTitle() + " by " + var19.getAuthor() + " (Borrowed: " + var6 + ")");
         }

         System.out.print("Select which book to return (Enter the number): ");
         var18 = getIntInput();
         if (var18 >= 1 && var18 <= var17.size()) {
            var5 = (String)var17.get(var18 - 1);
            List var20 = (List)var2.get(var5);
            int var7 = var20.size();
            System.out.print("How many copies would you like to return? (1-" + var7 + "): ");
            int var8 = getIntInput();
            if (var8 >= 1 && var8 <= var7) {
               int var9 = 0;

               for(int var10 = 0; var10 < var8; ++var10) {
                  BorrowRecord var11 = (BorrowRecord)var20.get(var10);
                  if (LocalDate.now().isAfter(var11.getDueDate())) {
                     long var12 = ChronoUnit.DAYS.between(var11.getDueDate(), LocalDate.now());
                     double var14 = (double)var12 * 0.5D;
                     System.out.printf("This book is %d days late. Penalty: $%.2f%n", var12, var14);
                     System.out.print("Pay penalty now? (Y/N): ");
                     String var16 = scanner.nextLine();
                     if (!var16.equalsIgnoreCase("Y")) {
                        System.out.println("Cannot return book without paying penalty.");
                        break;
                     }

                     System.out.printf("Penalty of $%.2f paid.%n", var14);
                  }

                  var0.returnBook(library, var5);
                  ++var9;
               }

               if (var9 > 0) {
                  Book var21 = ((BorrowRecord)var20.get(0)).getBook();
                  System.out.println("Successfully returned " + var9 + " copy/copies of '" + var21.getTitle() + "'. Thank you.");
               }

            } else {
               System.out.println("You can only return up to " + var7 + " copy/copies of the selected book.");
            }
         } else {
            System.out.println("Invalid selection.");
         }
      }
   }

   private static void removeBook(Librarian var0) {
      System.out.print("\nEnter Book ID to remove: ");
      String var1 = scanner.nextLine();
      if (var0.removeBook(library, var1)) {
         System.out.println("Book removed successfully!");
      } else {
         System.out.println("Book not found or could not be removed.");
      }

   }

   private static void checkAvailability() {
      System.out.print("\nEnter Book ID to check: ");
      String var0 = scanner.nextLine();
      Book var1 = library.findBook(var0);
      if (var1 != null) {
         System.out.println("Book: " + var1.getTitle());
         System.out.println("Available Copies: " + var1.getAvailableCopies());
      } else {
         System.out.println("Book not found.");
      }

   }

   private static void editBook(Librarian var0) {
      System.out.print("\nEnter Book ID to edit: ");
      String var1 = scanner.nextLine();
      Book var2 = library.findBook(var1);
      if (var2 == null) {
         System.out.println("Book not found.");
      } else {
         System.out.println("\nCurrent Details:");
         System.out.println("Title: " + var2.getTitle());
         System.out.println("Author: " + var2.getAuthor());
         System.out.println("Year: " + var2.getYear());
         System.out.println("Copies: " + var2.getTotalCopies());
         System.out.println("\nEnter new details (leave blank to keep current):");
         System.out.print("New Title: ");
         String var3 = scanner.nextLine();
         System.out.print("New Author: ");
         String var4 = scanner.nextLine();
         System.out.print("New Year: ");
         String var5 = scanner.nextLine();
         System.out.print("New Copies: ");
         String var6 = scanner.nextLine();
         if (!var3.isEmpty()) {
            var2.setTitle(var3);
         }

         if (!var4.isEmpty()) {
            var2.setAuthor(var4);
         }

         if (!var5.isEmpty()) {
            var2.setYear(Integer.parseInt(var5));
         }

         if (!var6.isEmpty()) {
            int var7 = Integer.parseInt(var6);
            int var8 = var7 - var2.getTotalCopies();
            var2.setTotalCopies(var7);
            var2.setAvailableCopies(var2.getAvailableCopies() + var8);
         }

         System.out.println("Book details updated successfully!");
      }
   }

   private static void checkAccountStatus(Member var0) {
      System.out.println("\n=== Account Status ===");
      System.out.println("Member ID: " + var0.getId());
      System.out.println("Name: " + var0.getName());
      System.out.println("Type: " + var0.getMemberType());
      System.out.println("Max Books Allowed: " + var0.getMaxBooks());
      List var1 = library.getBorrowRecords(var0);
      if (var1.isEmpty()) {
         System.out.println("No books currently borrowed.");
      } else {
         System.out.println("\nBorrowed Books:");
         int var2 = 1;

         for(Iterator var3 = var1.iterator(); var3.hasNext(); ++var2) {
            BorrowRecord var4 = (BorrowRecord)var3.next();
            Book var5 = var4.getBook();
            System.out.println(var2 + ". Title: " + var5.getTitle() + ", Author: " + var5.getAuthor() + ", Due Date: " + var4.getDueDate());
         }
      }

   }

   private static void searchBooks() {
      System.out.print("\nEnter search term (title or author): ");
      String var0 = scanner.nextLine();
      List var1 = library.searchBooks(var0);
      if (var1.isEmpty()) {
         System.out.println("No books found matching your search.");
      } else {
         System.out.println("\nSearch Results:");
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            Book var3 = (Book)var2.next();
            System.out.println(var3.getId() + ": " + var3.getTitle() + " by " + var3.getAuthor() + " (" + var3.getAvailableCopies() + " available)");
         }
      }

   }

   private static void viewAllMemberDetails() {
      ArrayList var0 = library.getMembers();
      if (var0.isEmpty()) {
         System.out.println("No members registered.");
      } else {
         System.out.println("\n=== All Member Details ===");

         for(Iterator var1 = var0.iterator(); var1.hasNext(); System.out.println("-----------------------------")) {
            Member var2 = (Member)var1.next();
            System.out.println("ID: " + var2.getId());
            System.out.println("Name: " + var2.getName());
            System.out.println("Email: " + var2.getEmail());
            System.out.println("Type: " + var2.getMemberType());
            System.out.println("Max Books Allowed: " + var2.getMaxBooks());
            List var3 = library.getBorrowRecords(var2);
            if (var3.isEmpty()) {
               System.out.println("Borrowed Books: None");
            } else {
               System.out.println("Borrowed Books:");
               int var4 = 1;

               for(Iterator var5 = var3.iterator(); var5.hasNext(); ++var4) {
                  BorrowRecord var6 = (BorrowRecord)var5.next();
                  Book var7 = var6.getBook();
                  System.out.println("  " + var4 + ". " + var7.getTitle() + " by " + var7.getAuthor() + " (Due: " + var6.getDueDate() + ")");
               }
            }
         }

      }
   }

   static {
      scanner = new Scanner(System.in);
   }
}
