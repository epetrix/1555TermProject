public class AdministratorTest {
  public static void test(Administrator admin) {
    testNewUser(admin);
    testSysDate(admin);
    testProductStats(admin);
    testOtherStats(admin);
  }

  private void log(Administrator admin, String message) {
    System.out.print("Admin \"" + admin.login + "\": ");
    System.out.println(message);
  }

  private static void testNewUser(Administrator admin) {
    log(admin, "Testing register customer...");
    String login = "user1";
    String password = "password1";
    String name = "Mr. User";
    String address = "123 User St.";
    String email = "user@gmail.com";
    boolean admin = false;
    admin.addUser(login, password, name, address, email, admin);
  }

  private static void testSysDate(Administrator admin) {
    testSysDate(admin, "2019-01-01 00:00:00");
  }

  private static void testSysDate(Administrator admin, String date) {
    log(admin, "Testing update system date...");
    admin.setDate(date, "yyyy-MM-dd HH:mm:ss");
  }

  private static void testProductStats(Administrator admin) {
    testProductStats(admin, false, null);
  }

  private static void testProductStats(Administrator admin, boolean isAll, String login) {
    log(admin, "Testing product statistics...");
    admin.getProductStats(isAll, login);
  }

  private static void testOtherStats(Administrator admin) {
    testHighLeaves(admin);
    testHighRoots(admin);
    testActiveBidders(admin);
    testActiveBuyers(admin);
  }

  private static void testHighLeaves(Administrator admin, int i, int j) {
    log(admin, "Testing high volume leaf categories..."); 
    admit.getBestLeafCategories(i, j); 
  }

  private static void testHighLeaves(Administrator admin) {
    log(admin, "Testing high volume leaf categories...");
    admin.getBestLeafCategories(5, 10);
  }

  private static void testHighRoots(Administrator admin) {
    log(admin, "Testing high volume root categories...");
    admin.getBestRootCategories(5, 10);
  }

  private static void testActiveBidders(Administrator admin) {
    log(admin, "Testing most active bidders...");
    admin.getActiveBidders(5, 10);
  }

  private static void testActiveBuyers(Administrator admin) {
    log(admin, "Testing most active buyers...");
    admin.getActiveBuyers(5, 10);
  }
}
