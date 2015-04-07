
	import java.sql.*;
	//import java.util.Properties;
	import java.util.Scanner;

	public class JDBCMySQLDemo {
		private static String dbURL = "jdbc:mysql://192.168.230.133:3306/lab61DB";
		private static Connection connection=null;
		private static String userName="russell";
		private static String password="teleport77";
		private static String tableName = "authors";
		private static Statement stmt = null;
		private static Scanner inScan = new Scanner(System.in);

		public static void main(String[] args) {
			// TODO Auto-generated method stub
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("Where is your MySQL JDBC Driver?");
				e.printStackTrace();
				return;
			}
			System.out.println("MySQL JDBC Driver Registered!");
			CreateConnection();

			if (connection != null) {
				doCRD();
			} else {
				System.out.println("Failed to make connection!");
			}
			if (connection != null) {
				try {
					connection.close();
				}
				catch (SQLException sqlExcept)
				{
					sqlExcept.printStackTrace();
				}
			}
			System.out.println("Thank you for using the program");
		}

		public static int getAChoice() {
			String choice=null;
			int intChoice=0;
			System.out.println("Please select one of the following choice");
			System.out.println("1- to show all records");
			System.out.println("2- to insert a new record");
			System.out.println("3- to delete a record");
			System.out.println("4- to exit the program");
			do {
				choice = inScan.nextLine();
				try {
					intChoice = Integer.parseInt(choice);
					if (intChoice >0 && intChoice<5) break;
				} catch (NumberFormatException e) {
					System.out.println("Invalid input! please enter again:");
				}
			} while ( intChoice<1 && intChoice>4 );
			return intChoice;
		}
		/**
		 * doCRD: create, read, and delete
		 */
		public static void doCRD() {
			int choice;
			do {
				choice = getAChoice();
				switch (choice) {
				case 1: //display all records
					displayAll();
					break;
				case 2: // insert a record
					insertRecord();
					break;
				case 3: // delete a record
					deleteRecord();
					break;
				case 4: // exit
					return;
				}

			} while (true); // use a break statement to exit the loop
		}
		/**
		 * displayAll: display all records of authors table
		 */
		public static void displayAll() {
			try {
				stmt = connection.createStatement();
				ResultSet results = stmt.executeQuery("select * from `" + tableName+"`;");
				ResultSetMetaData rsmd = results.getMetaData();
				int numberCols = rsmd.getColumnCount();
				
				for (int i=1; i<=numberCols; i++)
				{
					//print Column Names
					System.out.print(rsmd.getColumnLabel(i)+"\t");  
				}

				System.out.println("\n-------------------------------------------------");
				while(results.next())
				{
					String aID = results.getString(1);               
					String aFname = results.getString(2);
					String aLname = results.getString(3);
					String phone = results.getString(4);

					System.out.format("%4s %-15s %-15s %-12s\n", aID, aFname, aLname, phone );

				}
				System.out.println("");
				results.close();
				rsmd=null;
				stmt.close();
			}
			catch (SQLException sqlExcept)
			{
				sqlExcept.printStackTrace();
			}
		}

		public static void CreateConnection() {
			try {
				// connect 
				connection = DriverManager
						.getConnection(dbURL,userName, password); 

			} catch (SQLException ex) {
				System.out.println("Connection Failed! Check output console");
				ex.printStackTrace();
			}
		}
		/**
		 * insertRecord: add a row to authors table
		 */
		public static void insertRecord() {
			try {
				String au_ID, au_fname, au_lname, phone;
				System.out.println("Enter author ID: ");
				au_ID =inScan.nextLine();
				System.out.println("Enter firstname");
				au_fname =inScan.nextLine();
				System.out.println("Enter lastname");
				au_lname =inScan.nextLine();
				System.out.println("Enter phone");
				phone =inScan.nextLine();
				stmt = connection.createStatement();

				stmt.executeUpdate("INSERT INTO `authors`(`au_id`, `au_fname`, `au_lname`, `phone`) VALUES ('"
						+au_ID+"','"+au_fname+"','"+au_lname+"','"+phone+"');");
				System.out.println("Insert completed!");
				stmt.close();
			}
			catch (SQLException sqlExcept)
			{
				sqlExcept.printStackTrace();
			}
		}
		public static void deleteRecord() {
			// accept a value of id to delete
			try {
				String au_ID;
				System.out.println("Enter the author ID that you want to delete");
				au_ID =inScan.nextLine();
				stmt = connection.createStatement();
				stmt.executeUpdate("DELETE FROM `"+tableName+"` WHERE `au_id`= '" + au_ID +"';");
				System.out.println("Deletion is completed");
				stmt.close();
			}
			catch (SQLException sqlExcept)
			{
				sqlExcept.printStackTrace();
			}	
		}
	}