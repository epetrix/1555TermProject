import java.sql.*;
import java.util.Scanner;

public abstract class User {
  public final Connection connection;
  public final Scanner input;

  public User(Connection connection) {
    this.connection = connection;
    this.input = new Scanner(System.in);
  }

  public abstract void openMenu();
}
