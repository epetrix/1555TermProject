public class Driver {
  public static void main(String[] args) {
    Connection connection = MyAuction.serverLogin();
    AdministratorTest.test(new Administrator(connection, "admin"));
    CustomerTest.test(new Customer(connection, "adk67"));
  }
}
