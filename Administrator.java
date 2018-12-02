import java.sql.*;
import java.util.Scanner;

public class Administrator extends User {
  public Administrator(Connection connection) {
    super(connection);
  }

  private static void displayMenu() {
    System.out.println("***************************");
    System.out.println("* Administrator Interface *");
    System.out.println("***************************");
    System.out.println("a) New customer registration");
    System.out.println("b) Update system date");
    System.out.println("c) Product statistics");
    System.out.println("d) Exit menu");
    System.out.println();
  }

  public static void openMenu() {
    char answer;
    do {
      displayMenu();
      answer = getChoice('a', 'd');
      switch(answer) {
        case 'a':
        registerCustomer();
        break;

        case 'b':
        updateSysDate();
        break;

        case 'c':
        getProductStats();
        break;

        case 'd':
        System.out.println("Exiting admin interface");
        break;
      }
      System.out.println();
    } while(answer != 'd');
  }

  private static void registerCustomer() {
    System.out.println("Registering new customer...");

    System.out.print("New administrator? (Y/n): ");
    boolean admin = input.nextLine().toLowerCase().equals("y");

    System.out.print("Enter login: ");
    String login = input.nextLine();

    System.out.print("Enter password: ");
    String password = input.nextLine();

    System.out.print("Enter name: ");
    String name = input.nextLine();

    System.out.print("Enter address: ");
    String address = input.nextLine();

    System.out.print("Enter email: ");
    String email = input.nextLine();

    addUser(login, password, name, address, email, admin);
  }

  private static void addUser(
    String login,
    String password,
    String name,
    String address,
    String email,
    boolean admin
  ) {
    String table = admin ? "Administrator" : "Customer";
    String query = "INSERT INTO ? VALUES(?,?,?,?,?)";

    PreparedStatement stmt = connection.prepareStatement(query);
    stmt.setString(1, table);
    stmt.setString(2, login);
    stmt.setString(3, password);
    stmt.setString(4, name);
    stmt.setString(5, address);
    stmt.setString(6, user);

    stmt.executeUpdate();
  }

  private static void updateSysDate() {
    System.out.println("Updating system date...");
  }

  private static void getProductStats() {
    System.out.println("Getting product statistics...");
  }
}
