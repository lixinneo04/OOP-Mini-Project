import java.time.LocalDate;

class BorrowRecord {
   private Member member;
   private Book book;
   private LocalDate borrowDate;
   private LocalDate dueDate;

   public BorrowRecord(Member var1, Book var2) {
      this.member = var1;
      this.book = var2;
      this.borrowDate = LocalDate.now();
      this.dueDate = this.borrowDate.plusDays((long)var1.getLoanPeriodDays());
   }

   public Member getMember() {
      return this.member;
   }

   public Book getBook() {
      return this.book;
   }

   public LocalDate getBorrowDate() {
      return this.borrowDate;
   }

   public LocalDate getDueDate() {
      return this.dueDate;
   }

   public String toString() {
      return this.book.getTitle() + " (Due: " + this.dueDate + ")";
   }
}
    
