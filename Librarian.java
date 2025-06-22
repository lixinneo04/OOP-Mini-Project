class Librarian {
   private String id;
   private String name;
   private String email;

   public Librarian(String var1, String var2, String var3) {
      this.id = var1;
      this.name = var2;
      this.email = var3;
   }

   public void addBook(Library var1, Book var2) {
      var1.addBook(var2);
   }

   public boolean removeBook(Library var1, String var2) {
      return var1.removeBook(var2);
   }
}
