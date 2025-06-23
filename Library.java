import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

class Library {
   private String name;
   private String address;
   private ArrayList<Book> books;
   private ArrayList<Member> members;
   private HashMap<String, List<BorrowRecord>> borrowRecords;

   public Library(String var1, String var2) {
      this.name = var1;
      this.address = var2;
      this.books = new ArrayList();
      this.members = new ArrayList();
      this.borrowRecords = new HashMap();
   }

   public void addBook(Book var1) {
      if (!this.books.contains(var1)) {
         this.books.add(var1);
      }

   }

   public boolean removeBook(String var1) {
      return this.books.removeIf((var1x) -> {
         return var1x.getId().equals(var1);
      });
   }

   public Book findBook(String var1) {
      Iterator var2 = this.books.iterator();

      Book var3;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         var3 = (Book)var2.next();
      } while(!var3.getId().equals(var1));

      return var3;
   }

   public List<Book> searchBooks(String var1) {
      ArrayList var2 = new ArrayList();
      String var3 = var1.toLowerCase();
      Iterator var4 = this.books.iterator();

      while(true) {
         Book var5;
         do {
            if (!var4.hasNext()) {
               return var2;
            }

            var5 = (Book)var4.next();
         } while(!var5.getTitle().toLowerCase().contains(var3) && !var5.getAuthor().toLowerCase().contains(var3));

         var2.add(var5);
      }
   }

   public void registerMember(Member var1) {
      if (!this.members.contains(var1)) {
         this.members.add(var1);
      }

   }

   public Member findMember(String var1) {
      Iterator var2 = this.members.iterator();

      Member var3;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         var3 = (Member)var2.next();
      } while(!var3.getId().equals(var1));

      return var3;
   }

   public void borrowBook(Member var1, String var2) throws LibraryException {
      Book var3 = this.findBook(var2);
      if (var3 == null) {
         throw new LibraryException("Book not found");
      } else if (var3.getAvailableCopies() <= 0) {
         throw new LibraryException("No available copies");
      } else {
         String var4 = var1.getId() + var2;
         List var5 = (List)this.borrowRecords.getOrDefault(var4, new ArrayList());
         int var6 = 0;
         Iterator var7 = this.borrowRecords.values().iterator();

         while(var7.hasNext()) {
            List var8 = (List)var7.next();
            Iterator var9 = var8.iterator();

            while(var9.hasNext()) {
               BorrowRecord var10 = (BorrowRecord)var9.next();
               if (var10.getMember().getId().equals(var1.getId())) {
                  ++var6;
               }
            }
         }

         if (var6 >= var1.getMaxBooks()) {
            throw new LibraryException("You have reached your book limit. Please return a book before borrowing another.");
         } else {
            BorrowRecord var11 = new BorrowRecord(var1, var3);
            var5.add(var11);
            this.borrowRecords.put(var4, var5);
            var3.decreaseCopies();
         }
      }
   }

   public void returnBook(Member var1, String var2) throws LibraryException {
      String var3 = var1.getId() + var2;
      List var4 = (List)this.borrowRecords.get(var3);
      if (var4 != null && !var4.isEmpty()) {
         BorrowRecord var5 = (BorrowRecord)var4.remove(0);
         Book var6 = var5.getBook();
         var6.increaseCopies();
         if (var4.isEmpty()) {
            this.borrowRecords.remove(var3);
         } else {
            this.borrowRecords.put(var3, var4);
         }

      } else {
         throw new LibraryException("No active borrow record found");
      }
   }

   public List<BorrowRecord> getBorrowRecords(Member var1) {
      ArrayList var2 = new ArrayList();
      Iterator var3 = this.borrowRecords.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         if (((String)var4.getKey()).startsWith(var1.getId())) {
            var2.addAll((Collection)var4.getValue());
         }
      }

      return var2;
   }

   public void displayAllBooks() {
      System.out.println("\n=== All Books in " + this.name + " ===");
      System.out.printf("%-10s %-30s %-20s %-6s %s\n", "ID", "Title", "Author", "Year", "Availability");
      System.out.println("----------------------------------------------------------------");
      Iterator var1 = this.books.iterator();

      while(var1.hasNext()) {
         Book var2 = (Book)var1.next();
         System.out.printf("%-10s %-30s %-20s %-6d %d/%d\n", var2.getId(), var2.getTitle(), var2.getAuthor(), var2.getYear(), var2.getAvailableCopies(), var2.getTotalCopies());
      }

   }

   public void displayLibraryStatus() {
      System.out.println("\n=== Library Status ===");
      System.out.println("Name: " + this.name);
      System.out.println("Address: " + this.address);
      System.out.println("\nTotal Books: " + this.books.size());
      System.out.println("Total Members: " + this.members.size());
      System.out.println("Active Loans: " + this.borrowRecords.size());
   }

   public String getName() {
      return this.name;
   }

   public String getAddress() {
      return this.address;
   }

   public ArrayList<Book> getBooks() {
      return new ArrayList(this.books);
   }

   public ArrayList<Member> getMembers() {
      return new ArrayList(this.members);
   }
}
    
