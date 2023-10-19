package com.cmpt354.A7;

import java.sql.*;
import java.util.Scanner;
import java.util.Random;

class Review {
	private static Connection con;
	private static String space = "                                           ";

	public void Review_m(String current_user_id) throws SQLException {

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
		int business_id_match = 0;
		int star_rating = 0;
		int int_random = 0;
		int success = 0;
		int star = 0;
		String review_id = "";
		String temp2 = "";
		String business_id = "";
		Random r = new Random();
		Scanner input = new Scanner(System.in);
		String sql_check_business_exist;
		String sql_insert;
		String sql_check_business_id = "";

		System.out.println("+------------------------------------------+");
		System.out.println("|            4) Write a review             |");
		System.out.println("+------------------------------------------+");
		// getting business id
		while (business_id_match != 1) {
			System.out.print("Enter the business ID: ");
			business_id = input.nextLine();
			while( business_id.contains("'")) {
				System.out.print("BusinessId cannot contains single quotation mark. Try again: ");
				business_id = input.next();
			}
			
			sql_check_business_exist = "SELECT COUNT(*) FROM business WHERE business_id= '" + business_id + "';";
			Statement ps1 = con.createStatement();
			Statement ps2 = con.createStatement();
			rs = ps1.executeQuery(sql_check_business_exist);
			while (rs.next()) {
				business_id_match = rs.getInt(1); // 1 if friend_id exist
			}

			if (business_id_match != 1) {
				System.out.print("Incorrect Id.");
			}

		}

		String randString = business_id.substring(0, 7) + current_user_id.substring(0, 7);
		int review_id_match = 1;
		String sql_check_review_exist = "";

		while (review_id_match == 1) {
			temp2 = String.valueOf(r.nextInt(899999) + 100000);
			review_id = (randString + temp2);
			sql_check_review_exist = "SELECT COUNT(*) FROM review WHERE review_id= '" + review_id + "';";
			Statement ps4 = con.createStatement();
			// Statement ps2 = con.createStatement();
			rs = ps4.executeQuery(sql_check_review_exist);
			while (rs.next()) {
				review_id_match = rs.getInt(1); // review_id repeated
			}
	

		}

		star_rating = getNum("Enter star rating (1~5): ", 1, 5);
		sql_insert = "INSERT INTO review VALUES (?,?,?,?,0,0,0,GETDATE())";
		PreparedStatement ps3 = con.prepareStatement(sql_insert);
		ps3.clearParameters();
		ps3.setString(1, review_id);
		ps3.setString(2, current_user_id);
		ps3.setString(3, business_id);
		ps3.setInt(4, star_rating);

		success = ps3.executeUpdate();

		if (success == 1) {
			System.out.println("Review(id:" + review_id + ") is uploaded.");
		}

	}

	public static int getNum(String a, int min, int max) {
		int temp = 0;
		// System.out.print(a);
		Scanner input = new Scanner(System.in);

		while (!(temp >= min && temp <= max)) {
			System.out.print(a);
			while (!input.hasNextInt()) {
				input.next();
			}
			temp = input.nextInt();
		}
		return temp;
	}
	// end of main
}