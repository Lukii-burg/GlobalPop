package com.globalpop;
import java.util.Scanner;

/**
 * Provides one shared Scanner for the entire console application.
 * Features and the main menu must use the same input stream reader.
 */
public final class ConsoleInput {

    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Returns the shared console Scanner.
     *
     * @return the application-wide Scanner
     */
    public static Scanner getScanner() {
        return SCANNER;
    }
    /**
     * Prevents this utility class from being instantiated.
     */
    private ConsoleInput() {
    }
}