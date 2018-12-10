import java.sql.*;
import java.util.Scanner;

public class Driver {
  public static void main(String[] args) {
    Connection connection = MyAuction.serverLogin(new Scanner(System.in));
    testAdmin(connection);
    testCustomer(connection);
  }

  private static void testCustomer(Connection connection) {
    try {
      connection.setAutoCommit(false);
      Savepoint save = connection.setSavepoint();
      CustomerTest.test(new Customer(connection, "adk67"));
      connection.rollback(save);
    } catch(SQLException ex) {
      System.out.println("Could not do customer tests!");
    } finally {
      try {
        connection.setAutoCommit(true);
      } catch(SQLException ex) {}
    }
  }

  private static void testAdmin(Connection connection) {
    try {
      connection.setAutoCommit(false);
      Savepoint save = connection.setSavepoint();
      AdministratorTest.test(new Administrator(connection, "admin"));
      connection.rollback(save);
    } catch(SQLException ex) {
      System.out.println("Could not do admin tests!");
    } finally {
      try {
        connection.setAutoCommit(true);
      } catch(SQLException ex) {}
    }
  }
}
