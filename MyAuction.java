import java.sql.*;
import java.util.Scanner;
import java.io.Console;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

public class MyAuction
{
	private static Connection connection;	//used to hold the jdbc connection to the DB
	private static Statement statement;	//used to create an instance of the connection
	private static PreparedStatement prepStatement;	//used to create a prepared statement. that will be later reused
	private static ResultSet resultSet;	//used to hold the result of your query
	private static String query;	//this will hold the query we are using
	private static Scanner scanner;

	public static void main(String[] args)
	{
		scanner = new Scanner(System.in);
		connection = serverLogin(scanner);
		boolean done;
		do {
			auctionLogin(connection).openMenu();
			System.out.println("Logging out...");
			System.out.print("Log in again? (Y/n): ");
			done = !Prompter.getBoolean();
		} while(!done);
		System.out.println("Goodbye!");
	}

	public static Connection serverLogin(Scanner scanner) {
		System.out.println("Please enter your user credentials for sqlplus.");

		Connection connection;
		while(true) {
			System.out.print("username: ");
			String username = scanner.nextLine();

			System.out.print("password: ");
			String password = Prompter.getSecret();

			try {
				DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
				String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass";
				connection = DriverManager.getConnection(url, username, password);
				System.out.println();
				return connection;
			} catch(SQLException ex) {
				System.out.print("Wrong username or password. Try again!");
			}
		}
	}

	public static User auctionLogin(Connection connection) {
		System.out.print("Hello welcome to our Electronic Auctioning System! ");
		System.out.println("Please log in.");

		Map<String,String> adminMap = getAdminMap(connection);
		Map<String,String> customerMap = getCustomerMap(connection);
		while(true) {
			System.out.print("username: ");
			String username = scanner.nextLine();

			System.out.print("password: ");
			String password = Prompter.getSecret();

			//checking if user is admin or customer
			//getting login information, checking validity
			if(password.equals(adminMap.get(username))) {
				return new Administrator(connection, username);
			}
			if(password.equals(customerMap.get(username))) {
				return new Customer(connection, username);
			}
			System.out.println("Wrong username or password! Try again.");
		}
	}

	public static Map<String,String> getAdminMap(Connection connection)
	{
		return getUserMap(connection, "Administrator");
	}

	public static Map<String,String> getCustomerMap(Connection connection)
	{
		return getUserMap(connection, "Customer");
	}

	private static Map<String,String> getUserMap(Connection connection, String table)
	{
		Map<String,String> userSet = new HashMap<String,String>();

		try {
			statement = connection.createStatement();
			query = "SELECT login, password FROM " + table;
			resultSet = statement.executeQuery(query);

			while(resultSet.next()) {
				String username = resultSet.getString(1);
				String password = resultSet.getString(2);
				userSet.put(username, password);
			}
		} catch(SQLException ex) {
			System.err.print("Fetching users failed! Machine Error: ");
			System.err.println(ex);
			System.exit(1);
		}

		return userSet;
	}
}
