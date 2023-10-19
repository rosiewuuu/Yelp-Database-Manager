package com.cmpt354.A7;

//package com.cmpt354.A7;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class searchBusiness {

	private static Connection con;
	private static String space = "                                           ";

	public void searchBusiness_m() throws SQLException {
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

		int numTuples = 0;
		int ans_star = 0;
		int ans_city = 0;
		int ans_bus_name = 0;
		int count = 0;
		String city = "";
		String bus_name = "";
		String min_star_sql = " b1.stars = (SELECT MIN(b2.stars) FROM business b2)";
		String max_star_sql = " b1.stars = (SELECT MAX(b2.stars) FROM business b2)";
		String sql_query = "SELECT b1.business_id, b1.name, b1.address, b1.city, b1.stars FROM business b1 WHERE";
		String header = String.format("%1$20s %2$50s %3$60s %4$16s %5$8s", "Business_Id", "Name", "Address", "city",
				"stars");
		Scanner input = new Scanner(System.in);

		System.out.println("+------------------------------------------+");
		System.out.println("|            1) Search business            |");
		System.out.println("+------------------------------------------+");

		ans_star = getNum("Would you like to set filter for business star? 1)no, 2)min, 3)max: ", 1, 3);
		ans_city = getNum("\nWould you like to set filter for city? 1)no, 2)yes", 1, 2);
		while ((ans_city == 2 && city.isEmpty()) || city.contains("'")) {
			System.out.print("Enter the city name: ");
			city = input.nextLine().trim();
		}

		ans_bus_name = getNum("\nWould you like to set filter for business name? 1)no, 2)yes", 1, 2);

		while ((ans_bus_name == 2 && bus_name.isEmpty())) {
			System.out.print("Enter (part of) the  business name: ");
			bus_name = input.nextLine().trim();
		}

		if (ans_star == 2) {
			sql_query = sql_query + min_star_sql + " AND ";
		}

		if (ans_star == 3) {
			sql_query = sql_query + max_star_sql + " AND ";
		}

		sql_query = sql_query + " LOWER(b1.city)  LIKE LOWER(?) AND LOWER(b1.name) LIKE LOWER(?)  "
				+ " ORDER BY b1.name ";

		//System.out.println(sql_query);
		PreparedStatement pst = con.prepareStatement(sql_query);
		pst.setString(1, "%" + city + "%");
		pst.setString(2, "%" + bus_name + "%");

		rs = pst.executeQuery();

		while (rs.next()) {
			if (count == 0) {
				System.out.println(header);
			}
			String tuple = String.format("%1$20s %2$50s %3$60s %4$16s %5$8s ", rs.getString(1), rs.getString(2),
					rs.getString(3), rs.getString(4), rs.getString(5));
			System.out.println(tuple);
			count++;
		}

		if (count == 0) {
			System.out.println("No result found...");
		}
		// input.close();

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
