import java.sql.*;

public class Customer extends User {
	
	private Statement statement;
	private ResultSet resultSet;
	private String query;

	public Customer(Connection connection) {
		super(connection);
	}

	private void displayMenu() {
		System.out.println("**********MAIN MENU**********");
		System.out.println();
		System.out.println("1. Browse products");
		System.out.println("2. Search for products");
		System.out.println("3. Put products up for auction");
		System.out.println("4. Bid on products");
		System.out.println("5. Sell products");
		System.out.println("6. Suggestions");
		System.out.println("7. Quit");
		System.out.println("What do you want to do? ");
	}

	public void openMenu() {
		char task;
		do {
			displayMenu();
			task = Prompter.getChoice('1', '7');
			
			switch (task) {
				case '1':
					browseProd();
					break;
				case '2':
					search();
					break;
				case '3':
					auction();
					break;
				case '4':
					bid();
					break;
				case '5':
					sell();
					break;
				case '6':
					suggestion();
					break;
				case '7':
					break;
				default: 
					System.out.print("Not a valid option."); 
					break;
			}
			System.out.println();
		} while (task != '7');
	}

	private void browseProd() {

		//list all root categories 

		System.out.println("List of Categories: "); 
		
		query = "SELECT * "
		+ "FROM Category"; 
		
		resultSet = statement.excecuteQuery(query); 

		String name = resultSet.getString("name"); 

		while(resultSet.next()) 
			printf("%s\n", name);

		//need while loop for cateogory heirarchy 

		System.out.println("Select a category: "); 
		String cat = input.nextLine().toLowerCase(); 
		
		System.out.println("Sort products (a)lphabetically or by (h)ighest bid amount? (Enter to ignore): "); 
		char sort = input.nextLine().toLowerCase(); 

		switch (sort) {
			case 'a': 
				query = "SELECT auction_id, name, description "
					+ "FROM Product"
					+ "WHERE EXISTS (SELECT auction_id "
					+ "FROM BelongsTo "
					+ "WHERE Category LIKE '%" + cat + "%')"
					+ "ORDER BY name ASC"; 

					try {
						statement = connection.createStatement(); 
						resultSet = statement.executeQuery(query); 

						System.out.println("Returned result(s):");
						if(!resultSet.next()) {
							System.out.println("No items matched the given keywords.");
							return;
						}

						int i = 1;
						do {
							System.out.print(i + ") "); 
							System.out.print(resultSet.getInt(1) + ", "); 
							System.out.print(resultSet.getString(2) + "\", ");
							System.out.print(resultSet.getString(3) + "\"");
							i++;  
						} while(resultSet.next());
					} catch(SQLException ex) {
						System.err.println("Error retrieving entries from database: " + ex);
					}
				}
				break; 
			case 'h':
				 

				break; 
			default: 

				break;
		} 

	}

	private void search() {
		System.out.println("Search up to two keywords: ");
		String[] keywords = input.nextLine().toLowerCase().split("\\s+", 3);
		System.out.println();

		query = "SELECT auction_id, name, description "
			+ "FROM Product "
			+ "WHERE LOWER(description) LIKE '%" + keywords[0] + "%'";
		if(keywords.length >= 2) {
			 query += " AND LOWER(description) LIKE '%" + keywords[1] + "%'";
		}

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			System.out.println("Returned result(s):");
			if(!resultSet.next()) {
				System.out.println("No items matched the given keywords.");
				return;
			}

			int i = 1;
			do {
				System.out.print(i + ") ");
				System.out.print(resultSet.getInt(1) + ", ");
				System.out.print("\"" + resultSet.getString(2) + "\", ");
				System.out.println("\"" + resultSet.getString(3) + "\"");
				i++;
			} while(resultSet.next());
		} catch(SQLException ex) {
			System.err.println("Error retrieving entries from database: " + ex);
		}
	}

	private void auction() {

	}

	private void bid() {}

	private void sell() {}

	private void suggestion() {}
}
