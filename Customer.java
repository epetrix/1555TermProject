import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public final class Customer extends User {
	private Statement statement;
	private ResultSet resultSet;
	private String query;

	public Customer(Connection connection, String login) {
		super(connection, login);
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
					openSellMenu();
					System.out.println("Exiting sell menu...");
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

		System.out.println("\nList of Categories: ");

		query = "SELECT name "
		+ "FROM Category "
		+ "WHERE parent_category IS NULL";

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			while(resultSet.next()) {
				String name = resultSet.getString("name");
				System.out.println(name);
			}

			System.out.print("\nSelect a category: ");
			String cat = input.nextLine().trim().toLowerCase();

			query = "SELECT name "
			+ "FROM Category "
			+ "WHERE LOWER(parent_category) LIKE '" + cat + "'";

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			System.out.println("\nList of Subcategories: ");
			while(resultSet.next()) {
				String name = resultSet.getString("name");
				System.out.println(name);
			}
		} catch (Exception ex) {
			System.out.println("Getting categories failed!");
		}

		System.out.print("\nSelect a subcategory: ");
		String cat = input.nextLine();

		System.out.println("\nSort products (a)lphabetically or by (h)ighest bid amount? (Enter to ignore): ");
		char sort = input.nextLine().trim().charAt(0);

		browseProd(cat, sort);
	}

	public void browseProd(String cat, char sort) {
		String params = null;
		String ordering = null;
		switch (Character.toLowerCase(sort)) {
			case 'a':
			params = "auction_id,name,description";
			ordering = "name ASC";
			break;

			case 'h':
			params = "auction_id,name,amount";
			ordering = "amount DESC";
			break;

			default: return;
		}
		query = "SELECT " + params + " "
			+ "FROM Product NATURAL JOIN BelongsTo "
			+ "WHERE LOWER(category) LIKE '" + cat.trim().toLowerCase() + "' "
			+ "ORDER BY " + ordering;

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
				if(sort == 'a') {
					printProductByName(resultSet);
				} else {
					printProductByAmount(resultSet);
				}
				i++;
			} while(resultSet.next());
		} catch (Exception ex) {
			System.out.println("Getting products failed!");
		}
	}

	private void printProductByName(ResultSet resultSet) throws SQLException {
		System.out.print(resultSet.getInt(1) + ", ");
		System.out.print("\"" + resultSet.getString(2) + "\", ");
		System.out.println("\"" + resultSet.getString(3) + "\"");
	}

	private void printProductByAmount(ResultSet resultSet) throws SQLException {
		System.out.print(resultSet.getInt(1) + ", ");
		System.out.print("\"" + resultSet.getString(2) + "\", ");
		System.out.println(resultSet.getInt(3));
	}

	private void search() {
		String[] keywords = Prompter.getStrings("Search up to two keywords: ", 2);
		System.out.println();
		search(keywords);
	}

	public void search(String[] keywords) {
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
			System.out.println("Getting entries from database failed!");
		}
	}

	private void auction() {
		System.out.print("Enter name of product: ");
    String name = input.nextLine();

    System.out.print("Enter (optional) description: ");
    String description = input.nextLine();
    try {
			String[] cats = getCategories();
			int days = Prompter.getInt("Enter number of days for auction: ");
			auction(login, name, description, cats, days);
		} catch (SQLException ex) {
    	ex.printStackTrace();
    }
	}

	public void auction(
		String login,
		String name,
		String desc,
		String[] cats,
		int days
	) {
		String qmarks = "?";
		for(int i = 1; i < cats.length; i++) {
			qmarks += ",?";
		}

    query = String.format("DECLARE "
    	+ "id number; "
    	+ "BEGIN "
    	+ "proc_putProduct(?,?,?,?,categories_t(%s),id); "
    	+ "END;", qmarks);
		try {
    	PreparedStatement statement = connection.prepareStatement(query);
    	statement.setString(1, name);
    	statement.setString(2, desc);
    	statement.setString(3, login);
    	statement.setInt(4, days);
			for(int i = 0; i < cats.length; i++) {
				statement.setString(5 + i, cats[i]);
			}
    	statement.execute();
    } catch (SQLException ex) {
    	ex.printStackTrace();
    }
	}

	private String[] getCategories() throws SQLException {
		int len = Prompter.getInt("Enter number of categories: ", 1, 128);
		String[] cats = new String[len];

		for(int i = 0; i < len; i++) {
			System.out.print("Enter category #" + (i + 1) + ": ");
			cats[i] = input.nextLine().trim();
		}

		return cats;
	}

	private void bid() {
		int id = Prompter.getInt("Auction ID of product you want to bid on: ");
		int price = Prompter.getInt("Bid amount: ");
		bid(id, price);
	}

	public void bid(int id, int price) {
		query = "SELECT amount "
		+ "FROM Product "
		+ "WHERE auction_id=?";

		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			if(!resultSet.next() || price <= resultSet.getInt(1)) {
				System.out.println("Bid too low!");
				return;
			}

			query = "INSERT INTO Bidlog VALUES (?,?,?,?,?)";
			statement = connection.prepareStatement(query);
			statement.setInt(1, getNextBidId());
			statement.setInt(2, id);
			statement.setString(3, login);
			statement.setDate(4, getSysDate());
			statement.setInt(5, price);
			statement.execute();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	private int getNextBidId() {
		String query = "SELECT NVL(max(bidsn),0)+1 as id FROM Bidlog";
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			resultSet.next();
			return resultSet.getInt(1);
		} catch(SQLException ex) {
			return 0;
		}
	}

	private java.sql.Date getSysDate() throws SQLException {
		query = "SELECT c_date FROM ourSysDATE";
		statement = connection.createStatement();
		resultSet = statement.executeQuery(query);
		resultSet.next();
		return resultSet.getDate(1);
	}

	private void openSellMenu() {
		System.out.println();
		int auction_id = getProductId();
		if(auction_id < 0) return;

		String name = getProductName(auction_id);
		if(name == null) {
			System.out.println("Error fetching product #" + auction_id);
			return;
		}

		System.out.println("Selling product \"" + name + "\"...");

		System.out.print("Second highest bid: ");
		int bidsn = getProductBidSN(auction_id);

		if(bidsn < 0) {
			System.out.println("Fetching bid failed!");
			return;
		}

		openSellOrRemoveMenu(name, auction_id, bidsn);
	}

	private void openSellOrRemoveMenu(String name, int auction_id, int bidsn) {
		int i = 1;
		int amount = 0;
		if(bidsn > 0) {
			amount = getBidlogAmount(bidsn);
			if(amount < 0) {
				System.out.println("Fetching bid amount failed!");
				return;
			}
			System.out.println("Selling price: " + amount);
			System.out.println(i + ". Sell \"" + name + "\"");
			i++;
		} else {
			System.out.println("No bids.");
		}

		System.out.println(i + ". Remove \"" + name + "\"");
		System.out.println("0. Cancel");

		char answer = Prompter.getChoice('0', Character.forDigit(i, 10));
		switch(answer) {
			case '1':
			if(bidsn > 0) {
				String buyer = getBidlogBidder(bidsn);
				sellProduct(auction_id, buyer, amount);
				break;
			}
			// fall through

			case '2':
			removeProduct(auction_id);
			break;

			default: break;
		}
	}

	public void sellProduct(int auction_id, String buyer, int amount) {
		String query = "UPDATE Product "
			+ "SET status='sold',buyer=?,amount=?"
			+ "WHERE auction_id=? AND status='under auction'";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, buyer);
			statement.setInt(2, amount);
			statement.setInt(3, auction_id);
			statement.executeUpdate();
		} catch(SQLException ex) {
			System.out.println("Selling product failed!");
		}
	}

	private void removeProduct(int auction_id) {
		String query = "UPDATE Product WHERE auction_id=? SET status='withdrawn'";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, auction_id);
			statement.executeUpdate();
		} catch(SQLException ex) {
			System.out.println("Removing product failed!");
		}
	}

	private int getProductBidSN(int auction_id) {
		String query = "SELECT bidsn FROM BidLog "
			+ "WHERE auction_id=? "
			+ "ORDER BY amount DESC "
			+ "FETCH FIRST 2 ROWS ONLY";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, auction_id);
			ResultSet resultSet = statement.executeQuery();

			// first highest bid
			if(!resultSet.next()) return 0;
			int bidsn = resultSet.getInt(1);

			// second highest bid
			if(!resultSet.next()) return bidsn;
			return resultSet.getInt(1);
		} catch(SQLException ex) {
			return -1;
		}
	}

	private int getBidlogAmount(int bidsn) {
		try {
			ResultSet resultSet = getRowFromTable("BidLog", "bidsn", bidsn);
			return resultSet != null ? resultSet.getInt("amount") : 0;
		} catch(SQLException ex) {
			return -1;
		}
	}

	private String getBidlogBidder(int bidsn) {
		try {
			ResultSet resultSet = getRowFromTable("BidLog", "bidsn", bidsn);
			return resultSet != null ? resultSet.getString("bidder") : null;
		} catch(SQLException ex) {
			return null;
		}
	}

	private String getProductName(int auction_id) {
		try {
			ResultSet resultSet = getRowFromTable("Product", "auction_id", auction_id);
			return resultSet != null ? resultSet.getString("name") : null;
		} catch(SQLException ex) {
			return null;
		}
	}

	private int getProductId() {
		System.out.println("Listed Products:");

		int i = 0;
		List<Integer> ids = new ArrayList<Integer>();
		String query = "SELECT auction_id,name FROM Product WHERE seller=?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, login);
      ResultSet resultSet = statement.executeQuery();

			if(!resultSet.next()) {
				System.out.println("No products listed for sale.");
				return -1;
			}

			do {
				i++;
				ids.add(resultSet.getInt(1));
				System.out.print(i + ") ");
				System.out.println("\"" + resultSet.getString(2) + "\"");
			} while(resultSet.next());
			System.out.println("0) Cancel");
		} catch(SQLException ex) {
			System.out.println("Retrieving products from database failed!");
			return -1;
		}

		int answer = Prompter.getInt("Sell no.: ", 0, i);
		if(answer == 0) return -1;
		return ids.get(i);
	}

	public void suggestion() {
		//get every product that the user has bid on
		query = "SELECT auction_id,count(*) as total,name FROM ("
			+ "SELECT distinct B1.auction_id,B1.bidder as bidder1 "
			+ "FROM Bidlog B1 "
			+ "WHERE B1.bidder=?) "
			+ "NATURAL JOIN ("
			+ "SELECT distinct B2.auction_id,B2.bidder as bidder2 "
			+ "FROM Bidlog B2) R2 "
			+ "NATURAL JOIN Product "
			+ "WHERE bidder1 != bidder2 "
			+ "GROUP BY auction_id,name "
			+ "ORDER BY total DESC";

		try
		{
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, login);
			resultSet = statement.executeQuery();

			if(!resultSet.next()) {
				System.out.println("No freinds, sad!");
				return;
			}

			System.out.println("Result(s):");
			int i = 1;
			do {
				System.out.println(i + ") " + resultSet.getString("name"));
				i++;
			} while(resultSet.next());
		} catch(SQLException ex) {
			System.out.println("Getting suggestions failed!");
		}
	}
}
