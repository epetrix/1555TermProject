import java.sql.*;
import java.util.Scanner;

public class Driver {
  public static void main(String[] args) {
    Connection connection = MyAuction.serverLogin(new Scanner(System.in));
    AdministratorTest.test(new Administrator(connection, "admin"));
    CustomerTest.test(new Customer(connection, "adk67"));
  }
}
