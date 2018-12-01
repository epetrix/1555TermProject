import java.sql.*;
import java.util.*; 

public class Customer {
	
	Scanner sc = new Scanner(System.in); 
	private Statement statement; 
	private ResultSet resultSet; 
	private String query; 
	private int task; 

	public Customer {
		
		do {
			System.out.println("**********MAIN MENU**********");
			System.out.println("\n1. Browse products \n2. search for products \n3. Put products up for auction \n4. Bid on products \n5. Sell products \n6. Suggestions \n7. Quit"); 
			System.out.println("What do you want to do? "); 
			task = sc.nextInt(); 

		} (while task != 7); 

	}

	public void BrowseProd() {
		
	}

	public void Search() {
		
		System.out.println("Search up to two keywords: ); 
		String keywords = sc.NextLine();
		String[] keywords = keywords.split("\\s+"); 
		
		query = "SELECT * FROM Products WHERE "; 

		resultSet = statement.executeQuery(query); 




	}
