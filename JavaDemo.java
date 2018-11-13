/*
    Here is an example of connecting to a database using jdbc

    The table we will use in the example is
    Table Test(
       name     varchar(30),
       ssn      number(10),
       bday     date
    );
    
    For demostratration purpose, insert two records into this table:
    ( 'Mike', 123456789, '09/Nov/03' )
    ( 'Amy', 987654321, '10/Nov/03' )

    Written by: Jonathan Beaver, modified by Thao Pham
    Purpose: Demo JDBC for CS1555 Class

    IMPORTANT (otherwise, your code may not compile)	
    Same as using sqlplus, you need to set oracle environment variables by 
    sourcing bash.env or tcsh.env
*/

import java.sql.*;  //import the file containing definitions for the parts
                    //needed by java for database connection and manipulation

public class JavaDemo
{
  private Connection connection; //used to hold the jdbc connection to the DB
  private Statement statement; //used to create an instance of the connection
  private ResultSet resultSet; //used to hold the result of your query (if one
                               // exists)
  private String query;  //this will hold the query we are using
  private String username, password;

  public JavaDemo()
  {
    /*Making a connection to a DB causes certian exceptions.  In order to handle
    these, you either put the DB stuff in a try block or have your function
    throw the Execptions and handle them later.  For this demo I will use the
    try blocks*/
    username = "your_pittID"; //This is your username in oracle
    password = "yourPeopleSoftNum"; //This is your password in oracle
    try{
      //Register the oracle driver.  This needs the oracle files provided
      //in the oracle.zip file, unzipped into the local directory and 
      //the class path set to include the local directory
      DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
      //This is the location of the database.  This is the database in oracle
      //provided to the class
      String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass"; 
      
      connection = DriverManager.getConnection(url, username, password); 
      //create a connection to DB on class3.cs.pitt.edu
    }
    catch(Exception Ex)  //What to do with any exceptions
    {
      System.out.println("Error connecting to database.  Machine Error: " +
            Ex.toString());
	Ex.printStackTrace();
    }

    int counter = 1;
    /*We will now perform a simple query to the database, asking it for all the
    records it has.  For your project, performing queries will be similar*/
    try{
      statement = connection.createStatement(); //create an instance
      query = "SELECT * FROM Test"; //sample query one

      resultSet = statement.executeQuery(query); //run the query on the DB table
      /*the results in resultSet have an odd quality.  The first row in result
      set is not relevant data, but rather a place holder.  This enables us to
      use a while loop to go through all the records.  We must move the pointer
      forward once using resultSet.next() or you will get errors*/

      while(resultSet.next()) //this not only keeps track of if another record
                              //exists but moves us forward to the first record
      {
        System.out.println("Record " + counter + ": " +
             resultSet.getString(1) + ", " + //since the first item was of type
                                            //string, we use getString of the
                                            //resultSet class to access it.
                                            //Notice the one, that is the
                                            //position of the answer in the
                                            //resulting table
             resultSet.getLong(2) + ", " +   //since second item was number(10),
                                          //we use getLong to access it
             resultSet.getDate(3)); //since type date, getDate.
		counter++;
      }


      /*Now, we show an insert, using preparedStatement. Of course for this you can also write the query directly as the above case with select, and vice versa. */
      
      String name = "tester 2";
      long ssn = 111111113;

      
      java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
     
      java.sql.Date bday = new java.sql.Date (df.parse("1990-01-20").getTime());

      query = "insert into Test values (?,?,?)";
     
      PreparedStatement updateStatement = connection.prepareStatement(query);
      updateStatement.setString(1, name);
      updateStatement.setLong(2,ssn);
      updateStatement.setDate(3,bday);
    
      updateStatement.executeUpdate();
    	
      
      /* We can also so the insert statement directly as follows:

       query = "INSERT INTO Test VALUES ('Tester', 111111112, '1/Nov/03')";
      int result = statement.executeUpdate(query); //executing update returns 
      //either the row count for INSERT, UPDATE or DELETE or 0 for SQL
      //statements that return nothing

      */	

      //I will show the insert worked by selecting the content of the table again
      //statement = connection.createStatement();
      query = "SELECT * FROM Test";
      resultSet = statement.executeQuery(query);
      System.out.println("\nAfter the insert, data is...\n");
	counter=1;
      while(resultSet.next())
      {
        System.out.println("Record " + counter + ": " +
             resultSet.getString(1) + ", " +
             resultSet.getLong(2) + ", " +
             resultSet.getDate(3));
	  counter ++;
      }

      connection.close();
    
    }
    catch(Exception Ex)
    {
      System.out.println("Error running the sample queries.  Machine Error: " +
            Ex.toString());
    }

    System.out.println("Good Luck");
  }

  public static void main(String args[])
  {
    JavaDemo demo = new JavaDemo();
  }
}
