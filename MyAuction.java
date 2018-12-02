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
		serverLogin();
		String answer;
		do {
			auctionLogin();
			System.out.println("Log in again? (Y/n): ");
			answer = scanner.nextLine().toLowerCase();
		} while(answer.equals("y"));
		System.out.println("Logging out...");
	}

	private static void serverLogin() {
		System.out.println("Please enter your user credentials for sqlplus.");
		boolean success = false;
		do {
			System.out.print("username: ");
			String username = scanner.nextLine();

			System.out.print("password: ");
			String password = getPassword(scanner);

			try {
				DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
				String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass";
				connection = DriverManager.getConnection(url, username, password);
				success = true;
			} catch(SQLException ex) {
				System.out.print("Wrong username or password. Try again!");
				System.out.println(ex);
				System.exit(1);
			} finally {
				password = null;
			}
		} while(!success);
		System.out.println();
	}

	public static String getPassword(Scanner input) {
		Console console = System.console();

		String passwordStr = null;
		if(console != null) {
			char[] password = console.readPassword();
			passwordStr = new String(password);
			java.util.Arrays.fill(password, '\0');
			password = null;
		} else {
			passwordStr = input.nextLine();
		}

		return passwordStr;
	}

	public static void auctionLogin() {
		System.out.print("Hello welcome to our Electronic Auctioning System! ");
		System.out.println("Please log in.");

		User user;
		while(true) {
			System.out.print("username: ");
			String username = scanner.nextLine();

			System.out.print("password: ");
			String password = getPassword(scanner);

			//checking if user is admin or customer
			//getting login information, checking validity
			user = getUser(username, password);
			if(user != null) break;

			System.out.println("Wrong username or password! Try again.");
		}

		user.openMenu();
	}

	private static User getUser(String username, String password) {
		if(password.equals(getAdminMap().get(username))) {
			return new Administrator(connection);
		} else if(password.equals(getCustomerMap().get(username))) {
			return new Customer(connection);
		} else {
			return null;
		}
	}

	public static Map<String,String> getAdminMap()
	{
		return getUserMap("Administrator");
	}

	public static Map<String,String> getCustomerMap()
	{
		return getUserMap("Customer");
	}

	private static Map<String,String> getUserMap(String table)
	{
		Map<String,String> userSet = new HashMap<String,String>();

		try {
			statement = connection.createStatement();
			query = "SELECT * FROM " + table;
			resultSet = statement.executeQuery(query);

			while(resultSet.next()) {
				String username = resultSet.getString(1);
				String password = resultSet.getString(2);
				userSet.put(username, password);
			}
		} catch(SQLException ex) {
			System.err.print("Error fetching users. Machine Error: ");
			System.err.println(ex);
			System.exit(1);
		}

		return userSet;
	}
}
