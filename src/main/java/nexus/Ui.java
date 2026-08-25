package nexus;

import java.util.Scanner;

/** Handles user input and console output for Nexus. */
public class Ui {
    private static final String LINE = "____________________________________________________________";
    private final Scanner scanner = new Scanner(System.in);

    /** Shows the welcome message. */
    public void showWelcome() {
        System.out.println(" _   _  _____  __  __  _   _  _____\n| \\ | || ____| \\ \\/ / | | | ||  ___|\n|  \\| ||  _|    \\  /  | | | || |___ \n| |\\  || |___   /  \\  | |_| | ___| |\n|_| \\_||_____| /_/\\_\\  \\___/ |____/ ");
        System.out.println("Hello! I'm Nexus.");
        System.out.println("What can I do for you?");
        showLine();
    }

    /** Returns whether another command is available. */
    public boolean hasNextCommand() { return scanner.hasNextLine(); }

    /** Reads the next command. */
    public String readCommand() { return scanner.nextLine(); }

    /** Displays the separator line. */
    public void showLine() { System.out.println(LINE); }

    /** Displays the goodbye message. */
    public void showGoodbye() {
        showLine();
        System.out.println("Bye. Hope to see you again soon!");
        showLine();
    }

    /** Displays all tasks. */
    public void showTasks(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /** Closes the input source. */
    public void close() { scanner.close(); }
}
