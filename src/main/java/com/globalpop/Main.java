package com.globalpop;

public class Main {

    public static void main(String[] args) {

        // Feature 1
        Feature1 feature1 = new Feature1();
        feature1.generateAndDisplayReport();

        // Feature 2
        Feature2 feature2 = new Feature2();
        feature2.run();

        // Feature 3
        Feature3 feature3 = new Feature3();
        feature3.generateReport("Western Europe");

        // Feature 4
        Feature4 feature4 = new Feature4();
        feature4.run();

        // Feature 5
        Feature5 feature5 = new Feature5();
        feature5.run();

        Feature6 feature6 = new Feature6();
        feature6.execute();
    }
}
