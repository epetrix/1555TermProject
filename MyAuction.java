import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
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
	
	public MyAuction()
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
		
		try
		{
			statement = connection.createStatement();
			query = "SELECT * FROM Administrator";
			resultSet = statement.executeQuery(query);
			while(resultSet.next())
			{
				ArrayList<String> adminList = new ArrayList<String>();
				adminList.add(resultSet.getString(1));
			}
		}
		catch(Exception Ex)
		{
			System.out.println("Error running the sample queries. Machine Error: " + Ex.toString());

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