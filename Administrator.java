import java.sql.*;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Administrator extends User {
  public Administrator(Connection connection) {
    super(connection);
  }

  private void displayMenu() {
    System.out.println("***************************");
    System.out.println("* Administrator Interface *");
    System.out.println("***************************");
    System.out.println("1. New customer registration");
    System.out.println("2. Update system date");
    System.out.println("3. Product statistics");
    System.out.println("4. Exit menu");
    System.out.println();
  }

  public void openMenu() {
    char answer;
    do {
      displayMenu();
      answer = Prompter.getChoice('1', '4');
      switch(answer) {
        case '1':
        registerCustomer();
        break;

        case '2':
        updateSysDate();
        break;

        case '3':
        getProductStats();
        break;

        case '4':
        break;
      }
      System.out.println();
    } while(answer != '4');
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

    String format = "yyyy-MM-dd HH:mm:ss";
    System.out.print("Enter new system time (" + format + "): ");
    String dateStr = input.nextLine();

    boolean success = setDate(dateStr, format);
    System.out.println();
    if(success) {
      System.out.println("Succesfully updated system time.");
    } else {
      System.out.println("Updating system time failed!");
    }
  }

  private boolean setDate(String dateStr, String format) {
    SimpleDateFormat df = new SimpleDateFormat(format);
    try {
      java.util.Date date = df.parse(dateStr);
      java.sql.Date sysTime = new java.sql.Date(date.getTime());

      String query = "UPDATE ourSysDATE SET c_date = ?";
      PreparedStatement stmt = connection.prepareStatement(query);
      stmt.setDate(1, sysTime);
      stmt.executeUpdate();

      return true;
    } catch(ParseException ex) {
      return false;
    } catch(SQLException ex) {
      return false;
    }
  }

  private void getProductStats() {
    System.out.println("Getting product statistics...");
  }
}
