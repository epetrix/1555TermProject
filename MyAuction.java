import java.sql.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.io.Console;

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
		System.out.println("Hello welcome to our Electronic Auctioning System! Please log in.");
		serverLogin();
		auctionLogin();
	}

	private static void serverLogin() {
		boolean success = false;
		do {
			System.out.print("username: ");
			String user = scanner.nextLine();

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
	}

	private static String getPassword(Scanner input) {
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

	public static void auctionLogin()
	{
		String username = "admin";
		String password = "pass";

		//getting login information, checking validity
		if(!auctionLogin(username, password)) {
			System.out.println("Wrong username or password! Try again");
			System.exit(1);
		}

		//checking if user is admin or customer, directs them to the correct menu interface
		User user = getAdminSet().contains(auctionUser)
			? new Administrator(connection)
			: new Customer(connection);
		user.openMenu();
	}

	public static Set<String> getAdminSet()
	{
		return getUserSet("Administrator");
	}

	public static Set<String> getCustomerSet()
	{
		return getUserSet("Customer");
	}

	private static Set<String> getUserSet(String table) {
		try
		{
			statement = connection.createStatement();
			query = "SELECT * FROM " + table;
			resultSet = statement.executeQuery(query);
			Set<String> userSet = new HashSet<String>();
			while(resultSet.next())
			{
				userSet.add(resultSet.getString(1));
			}
			return userSet;
		}
		catch(Exception ex)
		{
			System.out.println("Error running the sample queries. Machine Error: " + ex.toString());
			return null;
		}
	}
}
