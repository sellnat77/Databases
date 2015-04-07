package jdbcProject;
import java.sql.*;
//import java.util.Properties;
import java.util.Scanner;

public class jdbcProject {
	private static String dbURL = "jdbc:mysql://192.168.70.144:3306/lab61DB";
	private static Connection connection=null;
	private static String userName="cecs323";
	private static String password="csulb.edu";
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
		System.out.println("1- List all team information");
		System.out.println("2- List all meeting information");
		System.out.println("3- List all conference rooms and statuses on a date");
		System.out.println("4- Reserve a room for a meeting");
		System.out.println("5- Insert a conference room");
		System.out.println("6- List meetings for 1 of the four purposes between 1 date to another");
		System.out.println("7- Exit program");
		
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
				listTeams();
				displayAll();
				break;
			case 2: // insert a record
				listMeetings();
				insertRecord();
				break;
			case 3: // delete a record
				listConferenceRooms();
				deleteRecord();
				break;
			case 4: // delete a record
				reserveRoom();
				deleteRecord();
				break;
			case 5: // delete a record
				insertRoom();
				deleteRecord();
				break;
			case 6: // delete a record
				listMeetingsSpecial();
				deleteRecord();
				break;
			default: // exit
				return;
			}

		} while (true); // use a break statement to exit the loop
	}
	private static void listMeetingsSpecial() {
		// TODO Auto-generated method stub
		
	}

	private static void insertRoom() {
		try {
			String Room_Number , Room_Name , Building_Name , Room_Phone , Projector_Type ;
			System.out.println("Enter Room_Number : ");
			Room_Number  =inScan.nextLine();
			System.out.println("Enter Room_Name ");
			Room_Name  =inScan.nextLine();
			System.out.println("Enter Building_Name ");
			Building_Name  =inScan.nextLine();
			System.out.println("Enter Room_Phone");
			Room_Phone  =inScan.nextLine();
			System.out.println("Enter Projector_Type");
			Projector_Type  =inScan.nextLine();
			stmt = connection.createStatement();

			stmt.executeUpdate("INSERT INTO `Conference_Rooms`(`au_id`, `au_fname`, `au_lname`, `phone`,`Projector_Type`) VALUES ('"
					+Room_Number +"','"+Room_Name +"','"+Building_Name +"','"+Room_Phone+"','"+ Projector_Type+"');");
			System.out.println("Insert completed!");
			stmt.close();
		}
		catch (SQLException sqlExcept)
		{
			sqlExcept.printStackTrace();
		}
		
	}

	private static void reserveRoom() {
		// TODO Auto-generated method stub
		
	}

	private static void listConferenceRooms() {
		try {
			stmt = connection.createStatement();
			ResultSet results = stmt.executeQuery("select * from `Conference_Rooms`;");
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
				String Room_Number   = results.getString(1);               
				String Room_Name   = results.getString(2);
				String Building_Name    = results.getString(3);
				String Room_Phone    = results.getString(4);
				String Projector_Type  = results.getString(5);

				System.out.format("%4s %-15s %-15s %-12s %-12s\n", Room_Number , Room_Name , Building_Name  , Room_Phone,Projector_Type   );

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

	private static void listMeetings() {
		try {
			stmt = connection.createStatement();
			ResultSet results = stmt.executeQuery("select * from `Meetings`;");
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
				String Software_Team_Name  = results.getString(1);               
				String Room_Name   = results.getString(2);
				String Date   = results.getString(3);
				String Purpose_of_Meeting   = results.getString(4);

				System.out.format("%4s %-15s %-15s %-12s\n", Software_Team_Name, Room_Name , Date , Purpose_of_Meeting  );

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

	private static void listTeams() {
		try {
			stmt = connection.createStatement();
			ResultSet results = stmt.executeQuery("select * from `Software_Teams`;");
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
				String Software_Team_Name  = results.getString(1);               
				String Team_Leader  = results.getString(2);
				String Team_formed_date  = results.getString(3);
				String Project_Name  = results.getString(4);

				System.out.format("%4s %-15s %-15s %-12s\n", Software_Team_Name, Team_Leader, Team_formed_date, Project_Name );

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
