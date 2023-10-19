package com.cmpt354.A7;

import java.sql.*;
import java.util.Scanner;

class makeFriend {
	private static Connection con;
	private static String space = "                                           ";

	public void makeFriend_m(String current_user_id) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs;
		String sSQL = "select * from helpdesk"; // the table was created by helpdesk
		String temp = "";

		String sUsername = "s_lwa120";
		String sPassphrase = "dH2GndAYYhFP4eJ2";
		// ^^^ modify these 2 lines before compiling this program
		// please replace the username with your SFU Comuting ID
		// please get the passphrase from table 'helpdesk' of your course database

		String connectionUrl = "jdbc:sqlserver://cypress.csil.sfu.ca;" + "user = " + sUsername + ";" + "password ="
				+ sPassphrase;

		try {
			con = DriverManager.getConnection(connectionUrl);
		} catch (SQLException se) {
			System.out.println("\n\nFail to connect to CSIL SQL Server; exit now.\n\n");
			return;
		}

		try {
			pstmt = con.prepareStatement(sSQL);
			rs = pstmt.executeQuery();

			// System.out.println("\nThe table 'helpdesk' contains:\n\n");

			while (rs.next()) {
				temp = rs.getString("username"); // the table has a field 'username'
				// System.out.println(temp);
			}
			rs.close();
			// System.out.println("\nSuccessfully connected to CSIL SQL Server!\n\n");
		} catch (SQLException se) {
			System.out.println("\nSQL Exception occurred, the state : " + se.getSQLState() + "\nMessage:\n"
					+ se.getMessage() + "\n");
			return;
		}

		int numTuples = 0;
		int friend_id_match = 0;
		int friendship_match = 0;
		String friend_id = "";
		Scanner input = new Scanner(System.in);

		System.out.println("+------------------------------------------+");
		System.out.println("|            3) Make friend                |");
		System.out.println("+------------------------------------------+");

		String sql_check_friend_exist;
		String sql_check_friendship_exist;
		String sql_insert;

		// no such person or friendship exists
		while (friend_id_match != 1 || friendship_match == 1 || current_user_id.equals(friend_id)) {
			System.out.print("Enter the UserId of desired friend: ");
			friend_id = input.next();
			
			while( friend_id.contains("'")) {
				System.out.print("Incorrect user_id. Enter the UserId of desired friend: ");
				friend_id = input.next();
			}
			
			
			

			sql_check_friend_exist = "SELECT COUNT(*) FROM user_yelp WHERE user_id= '" + friend_id + "';";
			sql_check_friendship_exist = "SELECT COUNT(*) from friendship WHERE (user_id= '" + current_user_id
					+ "' and friend = '" + friend_id + "' ) OR (friend= '" + current_user_id + "' and user_id = '"
					+ friend_id + "')";

			Statement ps1 = con.createStatement();
			Statement ps2 = con.createStatement();

			rs = ps1.executeQuery(sql_check_friend_exist);
			while (rs.next()) {
				friend_id_match = rs.getInt(1); // 1 if friend_id exist
			}
			//System.out.println(sql_check_friendship_exist);
			rs = ps2.executeQuery(sql_check_friendship_exist);
			while (rs.next()) {
				friendship_match = rs.getInt(1); // 0 if friendship doesn't exist
			}

			if (friend_id_match != 1) {
				System.out.print("Incorrect user_id. ");
			}
			if (current_user_id.equals(friend_id)) {
				System.out.print("You can not add yourself. ");

			}
			if (friendship_match ==1 ) {
				System.out.println("Friendship already exists. Taking you back to the menu.");
				return;
			}

		}

		sql_insert = "INSERT INTO friendship VALUES (?,?)";
		PreparedStatement ps3 = con.prepareStatement(sql_insert);
		ps3.clearParameters();
		ps3.setString(1, current_user_id);
		ps3.setString(2, friend_id);
		int success = ps3.executeUpdate();
		if (success == 1) {
			System.out.println("You and user(" + friend_id + ") are friends now.");

		}

		// input.close();
	}
	// end of main
}