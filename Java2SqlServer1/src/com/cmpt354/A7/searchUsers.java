package com.cmpt354.A7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class searchUsers {

	private static Connection con;
	private static String space = "                                           ";

	public void searchUsers_m() throws SQLException {
		// TODO Auto-generated method stub

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

		// update the login status
		int numTuples = 0;
		int ans_useful = 0;
		int ans_funny = 0;
		int ans_cool = 0;
		int ans_name = 0;
		String user_name = "";
		Scanner input = new Scanner(System.in);
		String useful_yes_sql = " u1.useful >0";
		String funny_yes_sql = " u1.funny >0";
		String cool_yes_sql = " u1.cool >0";
		String useful_no_sql = " u1.useful = 0";
		String funny_no_sql = " u1.funny = 0";
		String cool_no_sql = " u1.cool = 0";
		String sql_query = "SELECT u1.user_id, u1.name, u1.useful, u1.funny, u1.cool,  u1.yelping_since "
				+ "FROM user_yelp u1 WHERE ";
		String header = String.format("%1$25s %2$15s %3$8s %4$8s %5$8s %6$30s", "user_id", "name", "useful", "funny",
				"cool", "yelping_since");

		System.out.println("+------------------------------------------+");
		System.out.println("|             2) Search users              |");
		System.out.println("+------------------------------------------+");

		ans_useful = getNum("Would you like to set filter for 'useful'? 1)no, 2)yes: ", 1, 2);
		ans_funny = getNum("\nWould you like to set filter for 'funny'?  1)no, 2)yes: ", 1, 2);
		ans_cool = getNum("\nWould you like to set filter for 'cool'?  1)no, 2)yes: ", 1, 2);
		ans_name = getNum("\nWould you like to search (part of) the username?  1)no, 2)yes: ", 1, 2);

		while ((ans_name == 2 && user_name.isEmpty())) {

			System.out.print("Enter the username: ");
			user_name = input.nextLine().trim();// .trim();

		}
		String username_sql = " LOWER(u1.name) LIKE LOWER(?)";

		if (ans_funny == 2) {
			sql_query = sql_query + funny_yes_sql;
		} else {
			sql_query = sql_query + funny_no_sql;
		}

		if (ans_cool == 2) {

			sql_query = sql_query + " AND " + cool_yes_sql;
		} else {
			sql_query = sql_query + " AND " + cool_no_sql;
		}

		if (ans_useful == 2) {

			sql_query = sql_query + " AND " + useful_yes_sql;
		} else {
			sql_query = sql_query + " AND " + useful_no_sql;

		}

		if (ans_name == 2) {

			sql_query = sql_query + " AND " + username_sql;
		}

		sql_query = sql_query + " ORDER BY u1.name ";
		PreparedStatement ps = con.prepareStatement(sql_query);// Statement stmt = con.createStatement();
		ps.setString(1, "%" + user_name + "%");
		rs = ps.executeQuery();

		int count = 0;
		while (rs.next()) {
			if (count == 0) {
				System.out.println(header);
			}
			String tuple = String.format("%1$25s %2$15s %3$8s %4$8s %5$8s %6$30s", rs.getString(1), rs.getString(2),
					rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
			System.out.println(tuple);
			count++;
		}
		// input.close();
		if (count == 0) {
			System.out.println("No result found...");
		}
	}

	public static int getNum(String a, int min, int max) {
		int temp = -1;

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

}
