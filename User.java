import java.sql.*;
import java.util.Scanner;

public abstract class User {
  public final Connection connection;
  public final Scanner input;
  public final String login;

  public User(Connection connection, String login) {
    this.connection = connection;
    this.login = login;
    this.input = new Scanner(System.in);
  }

  public boolean isAdmin() {
    return this instanceof Administrator;
  }

	public ResultSet getRowFromTable(
    String table,
    String pkName,
    int pk
  ) throws SQLException {
		String query = "SELECT * FROM " + table + " "
			+ "WHERE " + pkName + "=? "
			+ "FETCH FIRST 1 ROWS ONLY";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, pk);
		ResultSet resultSet = statement.executeQuery();
		return resultSet.next() ? resultSet : null;
	}

  public abstract void openMenu();
}
