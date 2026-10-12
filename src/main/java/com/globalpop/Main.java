
package com.globalpop;

import com.globalpop.sprint1_features.*;
import com.globalpop.sprint2.capital.*;
import com.globalpop.sprint2.region.*;
import com.globalpop.sprint2.world.*;

import java.util.Scanner;

/**
 * Main entry point for the GlobalPop population reporting application.
 * Users can select and run any feature from 1 to 22.
 */
public class Main {

    private static final Scanner SCANNER = ConsoleInput.getScanner();

    /**
     * Starts the application and keeps displaying the menu
     * until the user confirms that they want to exit.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("          WELCOME TO GLOBALPOP");
        System.out.println("==============================================");

        boolean running = true;

        while (running) {
            printMenu();

            System.out.print(
                    "\nSelect a feature (1-22), H for help, or 0 to exit: "
            );

            if (!SCANNER.hasNextLine()) {
                System.out.println("\nNo console input available. Closing GlobalPop.");
                break;
            }

            String choice = SCANNER.nextLine().trim();

            if (choice.equalsIgnoreCase("H")) {
                printHelp();
                continue;
            }

            if (choice.equals("0")) {
                if (confirmExit()) {
                    running = false;
                }
                continue;
            }

            int featureNumber;

            try {
                featureNumber = Integer.parseInt(choice);
            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid choice. Enter a number from 1 to 22, H, or 0."
                );
                continue;
            }

            if (featureNumber < 1 || featureNumber > 22) {
                System.out.println(
                        "Feature number must be between 1 and 22."
                );
                continue;
            }

            System.out.println(
                    "\nStarting Feature " + featureNumber + "..."
            );

            try {
                runFeature(featureNumber);
            } catch (Exception e) {
                System.err.println(
                        "\nAn unexpected error occurred while running Feature "
                                + featureNumber + "."
                );
                System.err.println("Details: " + e.getMessage());
                System.err.println(
                        "You can return to the menu and try another feature."
                );
            }

            System.out.println("\n----------------------------------------------");
            System.out.print("Press Enter to return to the main menu...");

            if (!SCANNER.hasNextLine()) {
                System.out.println("\nNo further console input available.");
                break;
            }

            SCANNER.nextLine();
        }

        System.out.println("\nThank you for using GlobalPop. Goodbye!");
    }

    /**
     * Displays all 22 features and the available console commands.
     */
    private static void printMenu() {
        System.out.println("\n==============================================");
        System.out.println("             GLOBALPOP MAIN MENU");
        System.out.println("==============================================");

        System.out.println("\nCOUNTRY REPORTS");
        System.out.println(" 1. All countries in the world");
        System.out.println(" 2. All countries in a continent");
        System.out.println(" 3. All countries in a region");
        System.out.println(" 4. Top N populated countries in the world");
        System.out.println(" 5. Top N populated countries in a continent");
        System.out.println(" 6. Top N populated countries in a region");

        System.out.println("\nCITY REPORTS");
        System.out.println(" 7. All cities in the world");
        System.out.println(" 8. All cities in a continent");
        System.out.println(" 9. All cities in a region");
        System.out.println("10. All cities in a country");
        System.out.println("11. All cities in a district");
        System.out.println("12. Top N populated cities in the world");
        System.out.println("13. Top N populated cities in a continent");
        System.out.println("14. Top N populated cities in a region");
        System.out.println("15. Top N populated cities in a country");
        System.out.println("16. Top N populated cities in a district");

        System.out.println("\nCAPITAL CITY REPORTS");
        System.out.println("17. All capital cities in the world");
        System.out.println("18. All capital cities in a continent");
        System.out.println("19. All capital cities in a region");
        System.out.println("20. Top N populated capital cities in the world");
        System.out.println("21. Top N populated capital cities in a continent");
        System.out.println("22. Top N populated capital cities in a region");

        System.out.println("\nH. Help");
        System.out.println("0. Exit");
        System.out.println("==============================================");
    }

    /**
     * Explains the available menu commands.
     */
    private static void printHelp() {
        System.out.println("\n============== GLOBALPOP HELP ==============");
        System.out.println("1-22 : Run the selected population report.");
        System.out.println("H    : Display this help information.");
        System.out.println("0    : Ask for confirmation before exiting.");
        System.out.println(
                "N    : In reports that ask for N, enter a positive whole number."
        );
        System.out.println(
                "Input: Follow the prompts shown by the selected feature."
        );
        System.out.println(
                "Database: Start the project's configured MySQL service before running reports."
        );
        System.out.println(
                "After a report, press Enter to return to the main menu."
        );
        System.out.println("============================================");
    }

    /**
     * Requests confirmation before closing the application.
     *
     * @return true if the user confirms exit; otherwise false
     */
    private static boolean confirmExit() {
        while (true) {
            System.out.print("Are you sure you want to exit? (Y/N): ");

            if (!SCANNER.hasNextLine()) {
                return true;
            }

            String answer = SCANNER.nextLine().trim();

            if (answer.equalsIgnoreCase("Y")) {
                return true;
            }

            if (answer.equalsIgnoreCase("N")) {
                System.out.println("Returning to the main menu.");
                return false;
            }

            System.out.println("Invalid response. Enter Y or N.");
        }
    }

    /**
     * Calls the existing class and method for the selected feature.
     *
     * @param featureNumber the selected feature number
     */
    private static void runFeature(int featureNumber) {
        switch (featureNumber) {
            case 1:
                new Feature1().generateAndDisplayReport();
                break;

            case 2:
                new Feature2().run();
                break;

            case 3:
                System.out.print(
                        "Enter region name (e.g., Western Europe, Caribbean): "
                );

                if (!SCANNER.hasNextLine()) {
                    System.out.println("No region input available.");
                    return;
                }

                String region = SCANNER.nextLine().trim();

                if (region.isEmpty()) {
                    System.out.println("Region name cannot be empty.");
                    return;
                }

                Feature3.generateReport(region);
                break;

            case 4:
                Feature4.run();
                break;

            case 5:
                new Feature5().run();
                break;

            case 6:
                new Feature6().execute();
                break;

            case 7:
                new Feature7().generateAndDisplayReport();
                break;

            case 8:
                new Feature8().run();
                break;

            case 9:
                new Feature9().run();
                break;

            case 10:
                new Feature10().generateAndDisplayReport();
                break;

            case 11:
                new Feature11().run();
                break;

            case 12:
                new Feature12().run();
                break;

            case 13:
                new Feature13().run();
                break;

            case 14:
                new Feature14().generateAndDisplayReport();
                break;

            case 15:
                new Feature15().run();
                break;

            case 16:
                new Feature16().run();
                break;

            case 17:
                new Feature17().generateAndDisplayReport();
                break;

            case 18:
                new Feature18().run();
                break;

            case 19:
                new Feature19().run();
                break;

            case 20:
                new Feature20().generateAndDisplayReport();
                break;

            case 21:
                new Feature21().run();
                break;

            case 22:
                new Feature22().run();
                break;

            default:
                System.out.println("Unknown feature number.");
        }
    }
}