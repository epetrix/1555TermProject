import java.sql.*;
import java.util.Map;

public class Administrator extends User {
  public Administrator(Connection connection) {
    super(connection);
  }

  private void displayMenu() {
    System.out.println("***************************");
    System.out.println("* Administrator Interface *");
    System.out.println("***************************");
    System.out.println("a) New customer registration");
    System.out.println("b) Update system date");
    System.out.println("c) Product statistics");
    System.out.println("d) Exit menu");
    System.out.println();
  }

  public void openMenu() {
    char answer;
    do {
      displayMenu();
      answer = Prompter.getChoice('a', 'd');
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
        break;
      }
      System.out.println();
    } while(answer != 'd');
  }

  private void registerCustomer() {
    System.out.println("Registering new customer...");

    System.out.print("New administrator? (Y/n): ");
    boolean admin = Prompter.getBoolean();

    System.out.print("Enter login: ");
    String login = input.nextLine();

    System.out.print("Enter password: ");
    String password = Prompter.getSecret();

    System.out.print("Enter name: ");
    String name = input.nextLine();

    System.out.print("Enter address: ");
    String address = input.nextLine();

    System.out.print("Enter email: ");
    String email = input.nextLine();

    boolean success = addUser(login, password, name, address, email, admin);
    System.out.println();

    if(success)  {
      System.out.println("Registered user successfully.");
    } else {
      System.out.println("User registration failed!");
    }
  }

  private boolean addUser(
    String login,
    String password,
    String name,
    String address,
    String email,
    boolean admin
  ) {
    String table = admin ? "Administrator" : "Customer";
    String query = "INSERT INTO " + table + " VALUES(?,?,?,?,?)";
    Map<String,String> adminMap = MyAuction.getAdminMap();
    Map<String,String> customerMap = MyAuction.getCustomerMap();

    if(adminMap.containsKey(login) || customerMap.containsKey(login)) {
      return false;
    }

    try {
      PreparedStatement stmt = connection.prepareStatement(query);
      stmt.setString(1, login);
      stmt.setString(2, password);
      stmt.setString(3, name);
      stmt.setString(4, address);
      stmt.setString(5, email);

      stmt.executeUpdate();
      return true;
    } catch(SQLException ex) {
      return false;
    }
  }

  private void updateSysDate() {
    System.out.println("Updating system date...");
  }

  private void getProductStats() {
    System.out.println("Getting product statistics...");
  }
}
