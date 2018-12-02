import java.sql.*;

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

  private void registerCustomer() {
    System.out.println("Registering new customer...");

    System.out.print("New administrator? (Y/n): ");
    boolean admin = input.nextLine().toLowerCase().equals("y");

    System.out.print("Enter login: ");
    String login = input.nextLine();

    System.out.print("Enter password: ");
    String password = MyAuction.getPassword(input);

    System.out.print("Enter name: ");
    String name = input.nextLine();

    System.out.print("Enter address: ");
    String address = input.nextLine();

    System.out.print("Enter email: ");
    String email = input.nextLine();

    boolean success = addUser(login, password, name, address, email, admin);
    password = null;
    if(!success) System.out.println("User registration failed!");
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

    try {
      PreparedStatement stmt = connection.prepareStatement(query);
      stmt.setString(1, login);
      stmt.setString(2, password);
      stmt.setString(3, name);
      stmt.setString(4, address);
      stmt.setString(5, email);

      stmt.executeUpdate();
      System.out.println("Registered user successfully.");
      return true;
    } catch(SQLException ex) {
      return false;
    } finally {
      password = null;
    }
  }

  private void updateSysDate() {
    System.out.println("Updating system date...");
  }

  private void getProductStats() {
    System.out.println("Getting product statistics...");
  }
}
