import java.util.Objects;

class Book {
   private String id;
   private String title;
   private String author;
   private int year;
   private int totalCopies;
   private int availableCopies;

   public Book(String var1, String var2, String var3, int var4, int var5) {
      this.id = var1;
      this.title = var2;
      this.author = var3;
      this.year = var4;
      this.totalCopies = var5;
      this.availableCopies = var5;
   }

   public String getId() {
      return this.id;
   }

   public String getTitle() {
      return this.title;
   }

   public String getAuthor() {
      return this.author;
   }

   public int getYear() {
      return this.year;
   }

   public int getTotalCopies() {
      return this.totalCopies;
   }

   public int getAvailableCopies() {
      return this.availableCopies;
   }

   public void setTitle(String var1) {
      this.title = var1;
   }

   public void setAuthor(String var1) {
      this.author = var1;
   }

   public void setYear(int var1) {
      this.year = var1;
   }

   public void setTotalCopies(int var1) {
      this.totalCopies = var1;
   }

   public void setAvailableCopies(int var1) {
      this.availableCopies = var1;
   }

   public void decreaseCopies() {
      if (this.availableCopies > 0) {
         --this.availableCopies;
      }

   }

   public void increaseCopies() {
      if (this.availableCopies < this.totalCopies) {
         ++this.availableCopies;
      }

   }

   public String toString() {
      return this.title + " by " + this.author + " (" + this.year + ") - " + this.availableCopies + "/" + this.totalCopies + " available";
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         Book var2 = (Book)var1;
         return this.id.equals(var2.id);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.id});
   }
}
