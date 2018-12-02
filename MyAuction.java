import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class MyAuction 
{
	private static Connection connection;	//used to hold the jdbc connection to the DB
	private Statement statement;	//used to create an instance of the connection
	private PreparedStatement prepStatement;	//used to create a prepared statement. that will be later reused
	private ResultSet resultSet;	//used to hold the result of your query 
	private String query;	//this will hold the query we are using
	private String username;
	private String password;
	
	public MyAuction(String user, String pass)
	{
		username = "adk67";
		password = "4039547";
		try
		{
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass";
			connection = DriverManager.getConnection(url,username,password);
		}
		catch(Exception Ex)
		{
			System.out.println("Error connection to database. Machine Error: " + Ex.toString());
			Ex.printStackTrace();
		}
		
		//getting login information, checking validity
		while(!login(user, pass))
		{
			System.out.println("Wrong username or password! Try again");
		}
		
		//checking if user is admin or customer, directs them to the correct menu interface
		Set<String> adminSet = getAdminSet();
		Set<String> userSet = getUserSet();
		if(adminSet.contains(user))
		{
			Administrator admin = new Administrator();
		}
		else if(userSet.contains(user))
		{
			Customer customer = new Customer();
		}
		
	}
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("Hello welcome to our Electronic Auctioning System! Please log in.");
		System.out.print("username: ");
		String user = scanner.next();
		System.out.println("\npassword: ");
		String pass = scanner.next();
		MyAuction auction = new MyAuction(user, pass);
	}
	
	public boolean login(String user, String pass)
	{
		try
		{
			Set<String> adminSet = getAdminSet();
			Set<String> userSet = getUserSet();
			if(adminSet.contains(user) || userSet.contains(user))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception Ex)
		{
			System.out.println("Error connection to database. Machine Error: " + Ex.toString());
			return false;
		}
	}
	
	public Set<String> getAdminSet()
	{
		try
		{
			statement = connection.createStatement();
			query = "SELECT * FROM Administrator";
			resultSet = statement.executeQuery(query);
			Set<String> adminSet = new HashSet<String>();
			while(resultSet.next())
			{
				adminSet.add(resultSet.getString(1));
			}
			return adminSet;
		}
		catch(Exception Ex)
		{
			System.out.println("Error running the sample queries. Machine Error: " + Ex.toString());
			return null;
		}
	}
	
	public Set<String> getUserSet()
	{
		try
		{
			statement = connection.createStatement();
			query = "SELECT * FROM Administrator";
			resultSet = statement.executeQuery(query);
			Set<String> userSet = new HashSet<String>();
			while(resultSet.next())
			{
				userSet.add(resultSet.getString(1));
			}
			return userSet;
		}
		catch(Exception Ex)
		{
			System.out.println("Error running the sample queries. Machine Error: " + Ex.toString());
			return null;
		}
	}
}
