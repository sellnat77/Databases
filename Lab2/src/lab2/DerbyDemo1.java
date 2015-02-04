package lab2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Properties;

public class DerbyDemo1 
{	private static String connString = "jdbc:derby:/home/russell/squirrel-sql-3.6/Lab2;create=false";
	private static Connection conn = null;
	private static Statement statement = null;

	public static void main(String[] args)
	{
		
		connectToDB();
		SelectRecipes();
		shutdown();

	}
	public static void connectToDB()
	{
		try
		{
			conn = DriverManager.getConnection(connString);			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public static void SelectRecipes()
	{
		try
		{
			System.out.println("\n____________________________________________________________");
			int k;
			String tableName = "recipes";
			statement = conn.createStatement();
			ResultSet results = statement.executeQuery("select * from "+tableName);
			ResultSetMetaData metaData = results.getMetaData();
			
			for(k = 0; k < metaData.getColumnCount(); k++)
			{
				System.out.print(metaData.getColumnLabel(k+1)+"\t\t");
			}
			
			System.out.println("\n____________________________________________________________");
			
			while(results.next())
			{
				int id = results.getInt(1);
				String recipeName = results.getString(2);
				System.out.println("|"+id + "\t\t\t|" + recipeName);
			}
			results.close();
			statement.close();
			conn.close();
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			
		}
	}
	
	public static void shutdown()
	{
		try
		{
			if(statement != null)
			{
				statement.close();
			}
			
			if(conn != null)
			{
				DriverManager.getConnection(connString + ";shutdown=true");
				conn.close();
			}
		}
		catch(SQLException e)
		{
			
		}
	}

}
