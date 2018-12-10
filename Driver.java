public class Driver {
  public static void main(String[] args) {
    Connection connection = MyAuction.serverLogin();
    User user = MyAuction.auctionLogin(connection);
  }
}
