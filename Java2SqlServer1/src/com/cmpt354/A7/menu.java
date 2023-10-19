package com.cmpt354.A7;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random.*;
import java.util.Scanner;

import com.cmpt354.A7.*;

public class menu {

	public static void main(String[] args)  throws SQLException {

		// TODO Auto-generated method stub
		int choice = 0;
		boolean login = true;
		String current_user_id = "";
		userLogin L = new userLogin();
		searchBusiness B = new searchBusiness();
		searchUsers U = new searchUsers();
		makeFriend F = new makeFriend();
		Review R = new Review();

		do {

			current_user_id= L.userLogin_m();
			login = true;
			choice = getNum();

			while (choice >= 1 && choice <= 5 && login == true) {

				switch (choice) {
				case 1: // search business
					B.searchBusiness_m();
					choice = getNum();
					break;
				case 2: // searcg users
					U.searchUsers_m();
					choice = getNum();
					break;
				case 3: // make friend
					F.makeFriend_m(current_user_id);
					choice = getNum();
					break;
				case 4: // write review
					R.Review_m(current_user_id);
					choice = getNum();
					break;
				case 5: // done and logout
					System.out.println("\n\nEnd of operations. Returning to login page.\n\n");
					choice = 0;
					login = false;
					break;

				}// end of cases
			} // end of while (choice >= 1 && choice <= 5 && login == true)

		} while (true); // end of do while(true)

	}// end of main

	
	
	
	public static int getNum() {
		int temp = -1;

		Scanner input = new Scanner(System.in);

		while (!(temp >= 1 && temp <= 5)) {

			System.out.println("\n+--------SELECT AN VALID OPTION---------+");
			System.out.println("|                                         |");
			System.out.println("|            1) Search business           |");
			System.out.println("|            2) Search users              |");
			System.out.println("|            3) Make friend               |");
			System.out.println("|            4) Write a review            |");
			System.out.println("|            5) Done and leave            |");
			System.out.println("|                                         |");
			System.out.println("+-----------------------------------------+");

			while (!input.hasNextInt()) {
				System.out.println("\n+--------SELECT AN VALID OPTION---------+");
				System.out.println("|                                         |");
				System.out.println("|            1) Search business           |");
				System.out.println("|            2) Search users              |");
				System.out.println("|            3) Make friend               |");
				System.out.println("|            4) Write a review            |");
				System.out.println("|            5) Done and leave            |");
				System.out.println("|                                         |");
				System.out.println("+-----------------------------------------+");
				input.next();
			}
			temp = input.nextInt();
		}

		return temp;
	}

}
