import java.sql.*;
import java.util.Scanner; 

public class Customer {
	
	private static final Scanner sc = new Scanner(System.in); 
	private static Statement statement; 
	private static ResultSet resultSet; 
	private static String query;  

	private static void displayMenu() {
		int task; 
		
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
		
			} while (task != 7); 

	}

	private static void BrowseProd() {
		
	}

	private static void Search() {
		try {	
		System.out.println("Search first keyword: "); 
		String keyword = sc.nextLine(); 
		String[] keywords = keyword.split("\\s+"); 

		query = "SELECT * FROM Products WHERE description LIKE %"+keywords[0]+"% AND desription LIKE %"+keywords[1]+"%"; 
		resultSet = statement.executeQuery(query);

		while(resultSet.next()) {
			int id = resultSet.getInt("auction_id");
			String name = resultSet.getString("name"); 
			String description = resultSet.getString("description"); 
			String seller = resultSet.getString("seller"); 
			Date start_date = resultSet.getDate("start_date");
			int min = resultSet.getInt("min_price"); 
			int numDays = resultSet.getInt("number_of_days");
			String status = resultSet.getString("status");
			String buyer = resultSet.getString("buyer"); 
			Date sell_date = resultSet.getDate("sell_date"); 
			int amount = resultSet.getInt("amount"); 
			
			System.out.format("%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s\n", id, name, description, seller, start_date, min, numDays, status, buyer, sell_date, amount); 
		}
		
		}catch(Exception e) {
			System.err.println("Uh dang, exception"); 
			System.err.println(e.getMessage()); 
		}
	}

	private static void Auction() {

	}

	private static void Bid() {
		
	}

	private static void Sell() {

	}

	private static void Suggestion() {

	}
}
