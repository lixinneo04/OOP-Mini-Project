class StaffMember extends Member {
   private String department;

   public StaffMember(String var1, String var2, String var3, String var4) {
      super(var1, var2, var3);
      this.department = var4;
   }

   public String getMemberType() {
      return "Staff";
   }

   public int getMaxBooks() {
      return 5;
   }

   public int getLoanPeriodDays() {
      return 28;
   }
}
