abstract class Member {
   private String id;
   private String name;
   private String email;

   public Member(String var1, String var2, String var3) {
      this.id = var1;
      this.name = var2;
      this.email = var3;
   }

   public String getId() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public String getEmail() {
      return this.email;
   }

   public abstract String getMemberType();

   public abstract int getMaxBooks();

   public abstract int getLoanPeriodDays();

   public void borrowBook(Library var1, String var2) throws LibraryException {
      var1.borrowBook(this, var2);
   }

   public void returnBook(Library var1, String var2) throws LibraryException {
      var1.returnBook(this, var2);
   }
}
