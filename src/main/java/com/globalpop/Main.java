
package com.globalpop;

import com.globalpop.sprint1_features.*;
import com.globalpop.sprint2.capital.*;
import com.globalpop.sprint2.region.*;
import com.globalpop.sprint2.world.*;

public class Main {

    // Hard-coded values for features that require input.
    private static final String REGION_NAME = "Southeast Asia";
    private static final int TOP_LIMIT = 10;

    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println("          WELCOME TO GLOBALPOP");
        System.out.println("==============================================");

        // Run all features if no command-line argument is supplied.
        if (args.length == 0) {
            System.out.println("Running all features (1-22)...\n");

            for (int featureNumber = 1; featureNumber <= 22; featureNumber++) {
                executeFeature(featureNumber);
            }

        } else {
            // Run the feature numbers supplied as command-line arguments.
            for (String arg : args) {
                if (arg.equalsIgnoreCase("all")) {
                    for (int featureNumber = 1; featureNumber <= 22; featureNumber++) {
                        executeFeature(featureNumber);
                    }
                    break;
                }

                try {
                    int featureNumber = Integer.parseInt(arg);
                    executeFeature(featureNumber);

                } catch (NumberFormatException e) {
                    System.err.println("Invalid argument: " + arg);
                    System.err.println("Use a feature number from 1 to 22, or 'all'.");
                }
            }
        }

        System.out.println("\n==============================================");
        System.out.println("       GLOBALPOP EXECUTION COMPLETED");
        System.out.println("==============================================");
    }

    private static void executeFeature(int featureNumber) {

        System.out.println("\n----------------------------------------------");
        System.out.println("Starting Feature " + featureNumber + "...");
        System.out.println("----------------------------------------------");

        try {
            runFeature(featureNumber);

        } catch (Exception e) {
            System.err.println("Error running Feature " + featureNumber);
            System.err.println("Details: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Calls the existing class and method for each feature.
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
                Feature3.generateReport(REGION_NAME);
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
                new Feature14().generateAndDisplayReport(
                        REGION_NAME,
                        TOP_LIMIT
                );
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
                new Feature20().generateAndDisplayReport(TOP_LIMIT);
                break;

            case 21:
                new Feature21().run();
                break;

            case 22:
                new Feature22().run();
                break;

            default:
                System.err.println("Unknown feature number: " + featureNumber);
        }
    }
}
