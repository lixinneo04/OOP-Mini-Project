class StudentMember extends Member {
   private String studentId;

   public StudentMember(String var1, String var2, String var3, String var4) {
      super(var1, var2, var3);
      this.studentId = var4;
   }

   public String getMemberType() {
      return "Student";
   }

   public int getMaxBooks() {
      return 3;
   }

   public int getLoanPeriodDays() {
      return 14;
   }
}
    
