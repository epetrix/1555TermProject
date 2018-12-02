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
			
			switch (task) {
				case 1: 
					BrowseProd(); 
					break;
				case 2:
					Search(); 
					break;
				case 3:
					Auction(); 
					break;
				case 4: 
					Bid(); 
					break;
				case 5:
					Sell();
					break;
				case 6: 
					Suggestion(); 
					break;
				case 7: 
					break;
				default: 
					System.out.println("Not a valid option."); 
					break;
				}
		
			} (while task != 7); 

	}

	public void BrowseProd() {
		
	}

	public void Search() {
		
		System.out.println("Search first keyword: "); 
		String keyword = sc.Next(); 

		query = "SELECT * FROM Products WHERE DESCRIPTION ; 

		resultSet = statement.executeQuery(query);
	}

	public void Auction() {

	}

	public void Bid() {
		
	}

	public void Sell() {

	}

	public void Suggestion() {

	}
}
