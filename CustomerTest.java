public class CustomerTest {
  public static test(Customer user) {
    testBrowse(user);
  }

  private static void log(Customer user, String message) {
    System.out.print("Customer \"" + user.login + "\": ");
    System.out.println(message);
  }

  private static void testBrowse(Customer user) {
    log("Testing product browser...");
  }
}
