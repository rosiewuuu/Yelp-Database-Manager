package com.cmpt354.A7;


import java.sql.*;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
import java.util.Scanner;

public class userLogin {
	private static Connection con;
	private static String space = "                                           ";

	public String userLogin_m() throws SQLException
	{
		PreparedStatement pstmt = null;
		ResultSet rs;
		String sSQL= "select * from helpdesk";	//the table was created by helpdesk
		String temp="";
		
		String sUsername = "s_lwa120";
		String sPassphrase = "dH2GndAYYhFP4eJ2";
		// ^^^ modify these 2 lines before compiling this program
		// please replace the username with your SFU Comuting ID
		// please get the passphrase from table 'helpdesk' of your course database
		
        String connectionUrl = "jdbc:sqlserver://cypress.csil.sfu.ca;" +
			        "user = " + sUsername + ";" +
			        "password =" + sPassphrase;
			
        
		try
		{
			con = DriverManager.getConnection ( connectionUrl );
		} catch ( SQLException se )
			{
				System.out.println ( "\n\nFail to connect to CSIL SQL Server; exit now.\n\n" );
				return null;
			}
		
		try
		{
			pstmt = con.prepareStatement(sSQL);
			rs = pstmt.executeQuery();
			
			//System.out.println("\nThe table 'helpdesk' contains:\n\n");
			
			while (rs.next()) {
				temp= rs.getString("username");	//the table has a field 'username'
				//System.out.println(temp);
			}
			rs.close();
			//System.out.println("\nSuccessfully connected to CSIL SQL Server!\n\n");
		}catch (SQLException se)
			{
				System.out.println("\nSQL Exception occurred, the state : "+
								se.getSQLState()+"\nMessage:\n"+se.getMessage()+"\n");
				return null;
			}
		
	
		
		//update the login status
		int numTuples = 0;
        Scanner input = new Scanner(System.in);
        String sql_check_user_id = "SELECT COUNT(*) FROM user_yelp WHERE user_id= '";
        String sql_user_id = "";
        Statement stmt =con.createStatement();
        System.out.println("+------------------------------------------+");	
        System.out.println("|                User Login                |");		
        System.out.println("+------------------------------------------+");			
		while(numTuples!=1) {
	
			System.out.print("Enter your UserId: ");
			sql_user_id = input.next();
			
			while(sql_user_id.contains("'")) {
				System.out.print("Invalid user_id. Enter your UserId: ");
				sql_user_id = input.next();
			}
	        sql_check_user_id = "SELECT COUNT(*) FROM user_yelp WHERE user_id= '" + sql_user_id+ "';";
	        rs = stmt.executeQuery(sql_check_user_id);
	        while (rs.next()) {
	        	numTuples = rs.getInt(1); //1 if user_id matched
	        }
	        
	        if (numTuples==1) {

				System.out.printf("You're logged in", sql_user_id);

				} else { //numTuples==0
				System.out.print("Invalid user_id. ");
			}
	        
	        
		}
		        
        
		//input.close();
		return sql_user_id; 
	}
	//end of main
}