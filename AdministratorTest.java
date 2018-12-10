public class AdministratorTest {
  public static void test(Administrator admin) {
    testNewUser(admin);
    testSysDate(admin);
    testProductStats(admin);
    testOtherStats(admin);
  }

  private void log(String message) {
    System.out.print("Admin \"" + admin.login + "\": ");
    System.out.println(message);
  }

  private static void testNewUser(Administrator admin) {
    log("Testing register customer...");
    String login = "user1";
    String password = "password1";
    String name = "Mr. User";
    String address = "123 User St.";
    String email = "user@gmail.com";
    boolean admin = false;
    admin.addUser(login, password, name, address, email, admin);
  }

  private static void testSysDate(Administrator admin) {
    log("Testing update system date...");
    admin.setDate("2019-01-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
  }

  private static void testProductStats(Administrator admin) {
    log("Testing product statistics...");
    admin.getProductStats(false, null);
  }

  private static void testOtherStats(Administrator admin) {
    testHighLeaves(admin);
    testHighRoots(admin);
    testActiveBidders(admin);
    testActiveBuyers(admin);
  }

  private static void testHighLeaves(Administrator admin) {
    log("Testing high volume leaf categories...");
    admin.getBestLeafCategories(5, 10);
  }

  private static void testHighRoots(Administrator admin) {
    log("Testing high volume root categories...");
    admin.getBestRootCategories(5, 10);
  }

  private static void testActiveBidders(Administrator admin) {
    log("Testing most active bidders...");
    admin.getActiveBidders(5, 10);
  }

  private static void testActiveBuyers(Administrator admin) {
    log("Testing most active buyers...");
    admin.getActiveBuyers(5, 10);
  }
}
