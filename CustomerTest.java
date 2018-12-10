public class CustomerTest {
  public static void test(Customer user) {
    testBrowse(user);
    testSearch(user);
    testAuction(user);
    testBid(user);
    testSell(user);
    testSuggestion(user);
  }

  private static void log(Customer user, String message) {
    System.out.print("Customer \"" + user.login + "\": ");
    System.out.println(message);
  }

  private static void testBrowse(Customer user) {
    log(user, "Testing product browser...");
    user.browseProd("Phones", 'a');
    System.out.println();
  }

  private static void testSearch(Customer user) {
    log(user, "Testing product search...");
    user.search(new String[] {"brand", "new"});
    System.out.println();
  }

  private static void testAuction(Customer user) {
    log(user, "Testing auctioning product...");
    String login = user.login;
    String name = "Avocado";
    String desc = "It tastes good!";
    String[] cats = new String[] {"Phones", "Cooking Supplies"};
    int days = 20;
    user.auction(login, name, desc, cats, days);
    System.out.println();
  }

  private static void testBid(Customer user) {
    log(user, "Testing bid on products...");
    user.bid(1, 50);
    System.out.println();
  }

  private static void testSell(Customer user) {
    log(user, "Testing selling product...");
    user.sellProduct(1, "abc456", 30);
    System.out.println();
  }

  private static void testSuggestion(Customer user) {
    log(user, "Testing suggestions...");
    user.suggestion();
    System.out.println();
  }
}
