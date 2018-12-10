import java.sql.*;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public final class Administrator extends User {
  public Administrator(Connection connection, String login) {
    super(connection, login);
  }

  private void displayMenu() {
    System.out.println("***************************");
    System.out.println("* Administrator Interface *");
    System.out.println("***************************");
    System.out.println("1. New customer registration");
    System.out.println("2. Update system date");
    System.out.println("3. Product statistics");
    System.out.println("4. Other statistics");
    System.out.println("5. Exit menu");
    System.out.println();
  }

  private void displayStatsMenu() {
    System.out.println("********************");
    System.out.println("* Other Statistics *");
    System.out.println("********************");
    System.out.println("1. Highest volume leaf categories");
    System.out.println("2. Highest volume root categories");
    System.out.println("3. Most active bidders");
    System.out.println("4. Most active buyers");
    System.out.println("5. Exit menu");
    System.out.println();
  }

  public void openMenu() {
    char answer;
    char quit = '5';
    do {
      displayMenu();
      answer = Prompter.getChoice('1', quit);
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
        getStatistics();
        System.out.println("Exiting stats menu...");
        break;
      }
      System.out.println();
    } while(answer != quit);
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

  public boolean addUser(
    String login,
    String password,
    String name,
    String address,
    String email,
    boolean admin
  ) {
    String table = admin ? "Administrator" : "Customer";
    String query = "INSERT INTO " + table + " VALUES(?,?,?,?,?)";
    Map<String,String> adminMap = MyAuction.getAdminMap(connection);
    Map<String,String> customerMap = MyAuction.getCustomerMap(connection);

    if(adminMap.containsKey(login) || customerMap.containsKey(login)) {
      return false;
    }

    try {
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, login);
      statement.setString(2, password);
      statement.setString(3, name);
      statement.setString(4, address);
      statement.setString(5, email);
      statement.executeUpdate();
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

  public boolean setDate(String dateStr, String format) {
    SimpleDateFormat df = new SimpleDateFormat(format);
    try {
      java.util.Date date = df.parse(dateStr);
      java.sql.Date sysTime = new java.sql.Date(date.getTime());

      String query = "UPDATE ourSysDATE SET c_date = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setDate(1, sysTime);
      statement.executeUpdate();

      return true;
    } catch(ParseException ex) {
      return false;
    } catch(SQLException ex) {
      return false;
    }
  }

  private void getProductStats() {
    System.out.println("Getting product statistics...");
    System.out.print("Get products from specific customer? (Y/n): ");
    boolean isAll = !Prompter.getBoolean();

    String login = null;
    if(!isAll) {
      System.out.print("login: ");
      login = input.nextLine();
    }

    getProductStats(isAll, login);
  }

  public void getProductStats(boolean isAll, String login) {
    String query = "SELECT name,status,amount,buyer FROM Product";
    if(!isAll) query += " WHERE seller = '" + login + "'";
    query += " ORDER BY "
      + "CASE WHEN status='sold' "
      + "THEN 0 ELSE 1 "
      + "END ASC,amount DESC,name ASC";

    System.out.println("Result:");
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(query);
      if(!resultSet.next()) {
        System.out.println("No products found.");
        return;
      }

      String format = "| %-20s | %-8s | %-8d | %-10s |";
      String titleFormat = format.replace('d', 's');
      System.out.println("+----------------------+----------+----------+------------+");
      System.out.println(String.format(titleFormat, "Name", "Status", "High Bid", "Bidder"));
      System.out.println("+----------------------+----------+----------+------------+");
      do {
        printProduct(resultSet, format);
      } while(resultSet.next());
      System.out.println("+----------------------+----------+----------+------------+");
    } catch(SQLException ex) {
      System.out.println("Getting product statistics failed!");
    }
  }

  private void printProduct(ResultSet resultSet, String format) throws SQLException {
    String name = resultSet.getString(1);
    String status = resultSet.getString(2);
    int bid = resultSet.getInt(3);
    String bidder = resultSet.getString(4);
    if(bidder == null) bidder = "";
    System.out.println(String.format(format, name, status, bid, bidder));
  }

  private void getStatistics() {
    int months = Prompter.getInt("Number of months back: ");
    int total =  Prompter.getInt("Total from top: ");
    System.out.println();

    char answer;
    char quit = '5';
    do {
      System.out.println("Months: " + months);
      System.out.println("Total: " + total);
      displayStatsMenu();

      answer = Prompter.getChoice('1', quit);
      switch(answer) {
        case '1':
        getBestLeafCategories(months, total);
        break;

        case '2':
        getBestRootCategories(months, total);
        break;

        case '3':
        getActiveBidders(months, total);
        break;

        case '4':
        getActiveBuyers(months, total);
        break;
      }
      System.out.println();
    } while(answer != quit);
  }

  public void getBestLeafCategories(int months, int total) {
    String whereClause = "WHERE NOT EXISTS ("
      + "SELECT 1 FROM Category C2 "
      + "WHERE C1.name = C2.parent_category"
      + ")";
    System.out.println("Getting top " + total + " highest volume leaf categories...");
    getBestCategories(whereClause, months, total);
  }

  public void getBestRootCategories(int months, int total) {
    String whereClause = "WHERE parent_category IS NULL";
    System.out.println("Getting top " + total + " highest volume root categories...");
    getBestCategories(whereClause, months, total);
  }

  private void getBestCategories(String whereClause, int months, int total) {
    String query = "SELECT name,func_productCount(name,?) as count "
      + "FROM Category C1 "
      + whereClause + " "
      + "ORDER BY count DESC,name ASC "
      + "FETCH FIRST ? ROWS ONLY";

    System.out.println("Result:");
    try {
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, months);
      statement.setInt(2, total);
      ResultSet resultSet = statement.executeQuery();

      if(!resultSet.next()) {
        System.out.println("No categories found.");
        return;
      }

      String format = "| %-20s | %-13d |";
      String titleFormat = format.replace('d', 's');
      System.out.println("+----------------------+---------------+");
      System.out.println(String.format(titleFormat, "Category", "Products Sold"));
      System.out.println("+----------------------+---------------+");
      do {
        String name = resultSet.getString(1);
        int count = resultSet.getInt(2);
        System.out.println(String.format(format, name, count));
      } while(resultSet.next());
      System.out.println("+----------------------+---------------+");
    } catch(SQLException ex) {
      System.out.println("Fetching categories failed!");
    }
  }

  public void getActiveBidders(int months, int total) {
    getAciveCustomers("Bidder", "Bids", "func_bidCount", months, total);
  }

  public void getActiveBuyers(int months, int total) {
    getAciveCustomers("Buyer", "Amount", "func_buyingAmount", months, total);
  }

  private void getAciveCustomers(String row1, String row2, String func, int months, int total) {
    String pluralTerm = row1.toLowerCase() + "s";
    System.out.println("Getting top " + total + " most active " + pluralTerm + "...");

    String query = "SELECT login," + func + "(login,?) as count "
      + "FROM Customer "
      + "ORDER BY count DESC,login ASC "
      + "FETCH FIRST ? ROWS ONLY";

    System.out.println("Result:");
    try {
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, months);
      statement.setInt(2, total);
      ResultSet resultSet = statement.executeQuery();

      if(!resultSet.next()) {
        System.out.println("No " + pluralTerm + " found.");
        return;
      }

      String format = "| %-10s | %-8d |";
      String titleFormat = format.replace('d', 's');
      System.out.println("+------------+----------+");
      System.out.println(String.format(titleFormat, row1, row2));
      System.out.println("+------------+----------+");
      do {
        String login = resultSet.getString(1);
        int count = resultSet.getInt(2);
        System.out.println(String.format(format, login, count));
      } while(resultSet.next());
      System.out.println("+------------+----------+");
    } catch(SQLException ex) {
      System.out.println("Fetching " + pluralTerm + " failed!");
    }
  }
}
