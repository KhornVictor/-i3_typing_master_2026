package pages;

import auth.AuthService;
import database.service.UserService;
import java.util.Scanner;
import typing.TypingTestResult;
import typing.TypingTestService;
import util.ConsoleUtil;

public class TypingMasterApp {

    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {
    ConsoleUtil console = new ConsoleUtil();
    UserService userService = new UserService();
    AuthService authService = new AuthService(userService);
    TypingTestService typingTestService = new TypingTestService();
        Scanner scanner = new Scanner(System.in);
        String currentUser = null;
        int choice;
        boolean running = true;

    console.clearScreen();
        while (running) {
            System.out.println("------------ Welcome to I3 Typing Master v2026 -----------");
            System.out.println("To begin, please select one of the following options:");
            System.out.println("  1. Login");
            System.out.println("  2. Register");
            System.out.println("  3. Typing Test");
            System.out.println("  4. Exit");
            System.out.print("Choose an option: ");

            String rawChoice = scanner.nextLine().trim();
            try {
                choice = Integer.parseInt(rawChoice);
            } catch (NumberFormatException e) {
                choice = -1;
            }

            System.out.println();

            console.clearScreen();
            switch (choice) {
                case 1 -> {
                    currentUser = loginScreen(scanner, authService, console);
                }
                case 2 -> {
                    registerScreen(scanner, authService, console);
                }
                case 3 -> {
                    if (currentUser == null) {
                        System.out.println("Please login first before starting the typing test.");
                        console.pause();
                    } else {
                        int resultAction = typingTestLevel1(scanner, console, typingTestService, currentUser);
                        if (resultAction == 2) {
                            currentUser = null;
                        } else if (resultAction == 3) {
                            running = false;
                        }
                    }
                }
                case 4 -> {
                    System.out.println("Exiting program... Goodbye!");
                    running = false;
                }
                default -> {
                    System.out.println("Invalid choice. Please try again.");
                    console.pause();
                }
            }

            System.out.println();
            console.clearScreen();
        }

        scanner.close();
    }

    private static void registerScreen(Scanner scanner, AuthService authService, ConsoleUtil console) {
        System.out.println("------------ New User Registration ------------");
        System.out.print("Input a unique username (left blank to cancel): ");
        String username = scanner.nextLine().trim();

        System.out.print("Input password: ");
        String password = scanner.nextLine();
        System.out.print("Input confirm password: ");
        String confirmPassword = scanner.nextLine();

        AuthService.RegistrationStatus status = authService.register(username, password, confirmPassword);
        switch (status) {
            case SUCCESS -> System.out.println("Registration successful.");
            case CANCELLED -> System.out.println("Registration cancelled.");
            case USERNAME_EXISTS -> System.out.println("Username already exists. Please try another username.");
            case PASSWORD_EMPTY -> System.out.println("Password cannot be empty.");
            case PASSWORD_MISMATCH -> System.out.println("Passwords do not match. Registration failed.");
        }

        console.pause();
    }

    private static String loginScreen(Scanner scanner, AuthService authService, ConsoleUtil console) {
        System.out.println("-------------- Authentication --------------");
        System.out.print("Username (left blank to cancel): ");
        String username = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        AuthService.LoginStatus status = authService.login(username, password);
        switch (status) {
            case SUCCESS -> {
                System.out.println("Login successful. Welcome, " + username + "!");
                console.pause();
                return username;
            }
            case CANCELLED -> System.out.println("Login cancelled.");
            case INVALID_CREDENTIALS -> System.out.println("Invalid username or password.");
        }

        console.pause();
        return null;
    }

    private static int typingTestLevel1(Scanner scanner, ConsoleUtil console, TypingTestService typingTestService,
            String username) {
        console.clearScreen();
        System.out.println("------------ Typing Test Level 1 ------------");
        System.out.println("Type the following paragraph(s) to test your typing speed and quality (press");
        System.out.println("ENTER to end the test and display the result):");
        System.out.println();
        System.out.println(typingTestService.getLevel1Text());
        System.out.println();
        System.out.println("------------------------------------------------------------");
        System.out.println("Start typing below. Press ENTER on a blank line to finish.");

        StringBuilder typed = new StringBuilder();
        long startTimeMillis = System.currentTimeMillis();

        while (true) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            }

            if (!typed.isEmpty()) {
                typed.append(' ');
            }
            typed.append(line);
        }

        long endTimeMillis = System.currentTimeMillis();
        long elapsedMillis = Math.max(1, endTimeMillis - startTimeMillis);

        String typedText = typed.toString();
        TypingTestResult result = typingTestService.evaluateLevel1(typedText, elapsedMillis);

        console.clearScreen();
        System.out.println("------------ Typing Test Level 1 Result ------------");
        System.out.println("User: " + username);
        System.out.printf("Speed (characters/min): %d%n", result.getSpeedCharsPerMin());
        System.out.printf("      (words/min): %d%n", result.getSpeedWordsPerMin());
        System.out.printf("Total input characters: %d%n", result.getTotalInputChars());
        System.out.printf("      Total mistakes: %d%n", result.getTotalMistakes());
        System.out.println("----------------------------------------------------");
        System.out.println("  1. Next level");
        System.out.println("  2. Logout");
        System.out.println("  3. Exit");
        System.out.print("Choose an option: ");

        String rawChoice = scanner.nextLine().trim();
        int choice;
        try {
            choice = Integer.parseInt(rawChoice);
        } catch (NumberFormatException e) {
            choice = 1;
        }

        if (choice == 1) {
            System.out.println("Next level is not implemented yet. Returning to main menu.");
            console.pause();
            return 1;
        }

        if (choice == 2) {
            System.out.println("You are now logged out.");
            console.pause();
            return 2;
        }

        if (choice == 3) {
            System.out.println("Exiting program... Goodbye!");
            return 3;
        }

        System.out.println("Invalid option. Returning to main menu.");
        console.pause();
        return 1;
    }
}