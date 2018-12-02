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
		System.out.println("2. search for products");
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
			task = getChoice('1', '7');
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
			}
		} while (task != '7');
	}

	private void browseProd() {

	}

	private void search() {
		try {
			System.out.println("Search first keyword: ");
			String keyword = input.nextLine();
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
		} catch(Exception e) {
			System.err.println("Uh dang, exception");
			System.err.println(e.getMessage());
		}
	}

	private void auction() {

	}

	private void bid() {

	}

	private void sell() {

	}

	private void suggestion() {

	}
}
