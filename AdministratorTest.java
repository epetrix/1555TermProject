public class AdministratorTest {
  public static void test(Administrator admin) {
    testNewUser(admin);
    testSysDate(admin);
  }

  private static void testNewUser(Administrator admin) {
    System.out.println("Testing register customer...");
    admin.addUser(
      "user1",          // login
      "password1",      // password
      "Mr. User",       // name
      "123 User St.",   // address
      "user@gmail.com", // email
      false             // admin
    );
  }

  private static void testSysDate(Administrator admin) {
    System.out.println("Testing update system date...");
    admin.setDate("2019-01-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
  }

  private static void testProductStats(Administrator admin) {
    admin.getProductStats(false, null);
  }
}
