public class CustomerTest {
  public static test(Customer user) {
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
    log("Testing product browser...");
    user.browseProd("Phones", 'a');
  }

  private static void testSearch(Customer user) {
    log("Testing product search...");
    user.search({"brand", "new"});
  }

  private static void testAuction(Customer user) {
    log("Testing auctioning product...");
    String login = user.login;
    String name = "Avocado";
    String desc = "It tastes good!";
    String[] cats = {"Phones", "Cooking Supplies"};
    int days = 20;
    user.auction(login, name, desc, cats, days);
  }

  private static void testBid(Customer user) {
    log("Testing bid on products...");
    user.bid(1, 50);
  }

  private static void testSell(Customer user) {
    log("Testing selling product...");
    user.sellProduct(1, "abc456", 30);
  }

  private static void testSuggestion(Customer user) {
    log("Testing suggestions...");
    user.suggestion();
  }
}
