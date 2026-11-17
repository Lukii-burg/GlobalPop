
package com.globalpop;

import com.globalpop.sprint1_features.*;
import com.globalpop.sprint2.capital.*;
import com.globalpop.sprint2.region.*;
import com.globalpop.sprint2.world.*;

import com.globalpop.sprint3.Language_Demographics.*;
import com.globalpop.sprint3.General_Population_Totals.*;
import com.globalpop.sprint3.Urban_vs_Rural_Population.*;

public class Main {

    private static final String REGION_NAME = "Southeast Asia";
    private static final int TOP_LIMIT = 10;

    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println("          WELCOME TO GLOBALPOP");
        System.out.println("==============================================");

        if (args.length == 0 ||
                (args.length == 1 && args[0].equalsIgnoreCase("all"))) {

            System.out.println("Running all connected features...\n");

            for (int featureNumber = 1; featureNumber <= 32; featureNumber++) {
                executeFeature(featureNumber);
            }

        } else {

            for (String arg : args) {
                try {
                    int featureNumber = Integer.parseInt(arg);
                    executeFeature(featureNumber);

                } catch (NumberFormatException e) {
                    System.err.println("Invalid argument: " + arg);
                    System.err.println("Enter a feature number from 1 to 32, or 'all'.");
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

            case 23:
                new Feature23().generateAndDisplayReport();
                break;

            case 24:
                new Feature24().generateAndDisplayReport();
                break;

            case 25:
                new Feature25().generateAndDisplayReport();
                break;

            case 26:
                new Feature26().generateAndDisplayReport();
                break;

            case 27:
                new Feature27().generateAndDisplayReport("Asia");
                break;

            case 28:
                new Feature28().run();
                break;

            case 29:
                new Feature29().run();
                break;

            case 30:
                new Feature30().generateAndDisplayReport();
                break;

            case 31:
                new Feature31().generateAndDisplayReport();
                break;

            case 32:
                new Feature32().generateAndDisplayReport();
                break;


            default:
                System.err.println("Unknown feature number: " + featureNumber);
        }
    }
}
