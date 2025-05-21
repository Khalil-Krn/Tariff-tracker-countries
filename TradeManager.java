package tariffManagementSystem;

import java.util.*;
import tariffManagementSystem.TariffList.TariffNode;
import java.io.*;

public class TradeManager {

	// ---------------------------------------------------------------------------------------
	// Assignment 3
	// Question: Tariff war part 1 & 2, driver class.
	// Written by: Abderraouf El Khalil Karoun ID:40315753
	// ---------------------------------------------------------------------------------------

	/*
	 * Assignment 3 – COMP 249 – Winter 2025 Part 1 & Part 2 Combined Written by:
	 * Abderraouf El Khalil Karoun
	 * 
	 * This Java program has a Tariff Management System that simulates international
	 * trade policies and evaluates trade requests according to tariff regulations.
	 * 
	 * In Part 1, the program reads product trade data from an input file:
	 * "TradeData.txt". Each line contains a product's name, origin, category, and
	 * initial price. The system applies tariff adjustments based on both the
	 * country and the product category. For example, all the products from China
	 * receive a 25% tariff increase no matter the category. After prices are
	 * adjusted, all products are sorted alphabetically using the Comparable
	 * interface and the updated data is written to "UpdatedTradeData.txt".
	 * 
	 * Part 2 is a tariff evaluation system using a custom singly linked list
	 * (TariffList), which stores Tariff objects. Each Tariff defines a trade rule
	 * between two countries for a specific product category with a minimum required
	 * tariff. Trade requests are read from "TradeRequests.txt", and each request is
	 * evaluated based on the defined policy. A trade can be accepted, conditionally
	 * accepted (if within 20% of the required tariff, applying a surcharge), or
	 * rejected. The program prints the outcome of each request.
	 * 
	 * Additionally, the system allows a user to manually search the TariffList for
	 * a specific trade policy by entering an origin country, a destination country,
	 * and a category. The program displays how many iterations it took to find the
	 * correct trade. It also tests the deep copy constructor and equals() method to
	 * verify that the list is properly cloned.
	 */

	public static void main(String[] args) {
		System.out.println("Welcome to the tariff system management by Abderraouf El Khalil Karoun - Part 1");
		ArrayList<Product> list = new ArrayList<>();

		// Part 1: Reading and parsing product data from TradeData.txt
		Scanner reader0 = null;

		try {
			reader0 = new Scanner(new File("TradeData.txt"));
			while (reader0.hasNextLine()) {
				String line = reader0.nextLine();
				String[] lineToTrim = line.trim().split(",");
				String name;
				String country;
				String category;
				double price;
				try {
					// Validates and parses the 4 expected elements per line
					if (lineToTrim.length != 4) {
						throw new IllegalArgumentException("Line does not contain exactly 4 elements.");
					}
					name = lineToTrim[0].trim();
					country = lineToTrim[1].trim();
					category = lineToTrim[2].trim();
					price = Double.parseDouble(lineToTrim[3].trim());

					// Creates and stores a Product object
					Product product = new Product(name, country, category, price);
					list.add(product);

					// Handles number format issues in the trade value or proposed tariff fields
				} catch (NumberFormatException nfex) {
					System.out.println("Error found: " + nfex.getMessage());
				} catch (IllegalArgumentException iae) {
					System.out.println("Error found: " + iae.getMessage());
				} catch (InputMismatchException ime) {
					System.out.println("Error found: " + ime.getMessage());
				} catch (Exception e) {
					System.out.println("Error found: " + e.getMessage());
				}

			}
			// Handles error if TradeData.txt file is missing
		} catch (FileNotFoundException fnfex) {
			System.out.println("Please try again, the file could not be found: " + fnfex.getLocalizedMessage());
			System.exit(0);
			// Handles unexpected I/O issues while reading TradeData.txt
		} catch (IOException e) {
			System.out.println("Error reading to file: " + e.getMessage());
		} finally {
			if (reader0 != null)
				reader0.close();
		}

		// Applies tariff logic to each product
		for (Product product : list) {
			product.applyTariff();
		}
		// Sorts products alphabetically by name
		Collections.sort(list);

		// Writes updated product list to UpdatedTradeData.txt
		PrintWriter writer = null;

		try {
			writer = new PrintWriter("UpdatedTradeData.txt");
			for (Product product : list) {
				writer.println(product.toString());
			}
			// Handles unexpected I/O issues while reading TradeData.txt
		} catch (IOException e) {
			System.out.println("Error writing to UpdatedTradeData.txt.");
		} finally {
			if (reader0 != null)
				writer.close();
			System.out.println("Processing complete. Output written to UpdatedTradeData.txt.");

			// ---------------------- PART 2 STARTS ----------------------
			System.out.println("Welcome to the Tariff Management System - Part 2");
			System.out.println("Developed by Abderraouf El Khalil Karoun");
			System.out.println("==============================================");

			// Custom linked list of Tariff rules
			TariffList tariffList = new TariffList(); // Deep copy for testing equals()
			TariffList tariffList2;// Deep copy for testing equals()

			ArrayList<String[]> tradeRequests = new ArrayList<>();

			// Step 1: Reading tariffs from Tariffs.txt and storing in list
			Scanner reader = null;
			try {
				reader = new Scanner(new File("Tariffs.txt"));
				while (reader.hasNextLine()) {
					String[] line = reader.nextLine().trim().split("\\s+");

					if (line.length != 4) {
						continue;
					}

					String destination = line[0];
					String origin = line[1];
					String category = line[2];
					double minimumTariff = Double.parseDouble(line[3]);

					Tariff t = new Tariff(destination, origin, category, minimumTariff);
					if (!tariffList.contains(origin, destination, category)) {
						tariffList.addToStart(t);
					}
				}
				// Handles error if Tariff.txt file is missing
			} catch (FileNotFoundException e) {
				System.out.println("Error: Could not find Tariffs.txt");
				System.out.println("Program will terminate.");
				return;
			} catch (Exception e) {
				System.out.println("Error while reading Tariffs.txt: " + e.getMessage());
				System.out.println("Program will terminate.");
				return;
			} finally {
				System.out.println("Successfully read tariff data from Tariffs.txt");
				reader.close();
			}

			// Creates a deep copy of the list for equality testing
			tariffList2 = new TariffList(tariffList);
			System.out.println("Created a copy of the tariff list (size: " + tariffList2.getSize() + ")");

			// --------- Step 2: Read TradeRequests.txt ---------
			Scanner reader2 = null;
			try {
				reader2 = new Scanner(new File("TradeRequests.txt"));
				while (reader2.hasNextLine()) {
					String[] line = reader2.nextLine().trim().split("\\s+");
					if (line.length == 6) {
						tradeRequests.add(line);
					}
				}
				// Handles error if TradeData.txt file is missing
			} catch (FileNotFoundException e) {
				System.out.println("Error: Could not find TradeRequests.txt");
				return;
				// Handles unexpected I/O issues while reading TradeData.txt
			} catch (NumberFormatException e) {
				System.out.println("Invalid number format in TradeRequests.txt: " + e.getMessage());
				return;
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Line missing elements in TradeRequests.txt: " + e.getMessage());
				return;
			} catch (Exception e) {
				System.out.println("Unexpected error while reading TradeRequests.txt: " + e.getMessage());
				return;
			} finally {
				System.out.println(
						"Successfully read " + tradeRequests.size() + " trade requests from TradeRequests.txt");
				reader2.close();
			}

			// Creates a deep copy of the list for equality testing
			System.out.println("\nProcessing trade requests...");
			System.out.println("==============================================");

			for (String[] request : tradeRequests) {
				String id = request[0];
				String origin = request[1];
				String dest = request[2];
				String category = request[3];
				double tradeValue = Double.parseDouble(request[4]);
				double proposedTariff = Double.parseDouble(request[5]);

				// Finds the matching tariff rule
				TariffNode node = tariffList.find(origin, dest, category, false);

				if (node != null) {
					Tariff tariff = node.getData();
					double minTariff = tariff.getMinimumTariff();

					// Evaluates trade request using tariff policy
					String result = tariffList.evaluateTrade(proposedTariff, minTariff);
					System.out.println(id + " - " + result);

					switch (result) {
					case "Accepted":
						System.out.println("Proposed tariff meets or exceeds the minimum requirement.");
						break;
					case "Conditionally Accepted":
						double surcharge = tradeValue * ((minTariff - proposedTariff) / 100.0);
						System.out.printf("Proposed tariff %.2f%% is within 20%% of the required %.2f%%.%n",
								proposedTariff, minTariff);
						System.out.printf("A surcharge of $%.2f is applied.%n", surcharge);
						break;
					case "Rejected":
						System.out.println("Proposed tariff is more than 20% below the required minimum.");
						break;
					}
				} else {
					System.out.println(id + " - Rejected (No matching tariff policy found)");
				}

				System.out.println("--------------------------------------------------");
			}

			// Step 4: Manual search interaction with user
			Scanner ww = new Scanner(System.in);
			System.out.print("Search TariffList - Enter origin, destination, category: ");
			String origin = ww.next();
			String dest = ww.next();
			String category = ww.next();

			// Prints iteration count if found/not found
			tariffList.find(origin, dest, category, true);

			// Step 5: Verifies deep copy using equals() method
			System.out.println("Copied list equals original? " + tariffList2.equals(tariffList));

			System.out.println("\nProgram finished.");

			ww.close();
		}
	}
}