import java.util.Scanner;

public class Nexus {
    private static final int MAX_TASKS = 100;
    private static final String LINE = "____________________________________________________________";

    public static void main(String[] args) {
        String banner = " _   _  _____  __  __  _   _  _____\n"
                + "| \\ | || ____| \\ \\/ / | | | ||  ___|\n"
                + "|  \\| ||  _|    \\  /  | | | || |___ \n"
                + "| |\\  || |___   /  \\  | |_| | ___| |\n"
                + "|_| \\_||_____| /_/\\_\\  \\___/ |____/ ";
        System.out.println(banner);
        System.out.println("Hello! I'm Nexus.");
        System.out.println("What can I do for you?");
        System.out.println(LINE);

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();

            if (command.equals("bye")) {
                System.out.println(LINE);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(LINE);
                break;
            }

            System.out.println(LINE);
            if (command.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
            } else if (command.startsWith("mark ")) {
                markTask(command, tasks, taskCount);
            } else if (command.startsWith("unmark ")) {
                unmarkTask(command, tasks, taskCount);
            } else if (taskCount < MAX_TASKS) {
                Task newTask = parseTask(command);
                tasks[taskCount] = newTask;
                taskCount++;
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + newTask);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
            } else {
                System.out.println("Sorry, your task list is full.");
            }
            System.out.println(LINE);
        }
        scanner.close();
    }

    /** Creates the task subtype represented by a command. */
    private static Task parseTask(String command) {
        if (command.startsWith("todo ")) {
            return new Todo(command.substring("todo ".length()).trim());
        }
        if (command.startsWith("deadline ")) {
            String body = command.substring("deadline ".length());
            int marker = body.indexOf(" /by ");
            if (marker >= 0) {
                return new Deadline(body.substring(0, marker).trim(), body.substring(marker + 5).trim());
            }
        }
        if (command.startsWith("event ")) {
            String body = command.substring("event ".length());
            int fromMarker = body.indexOf(" /from ");
            int toMarker = body.indexOf(" /to ");
            if (fromMarker >= 0 && toMarker > fromMarker) {
                return new Event(body.substring(0, fromMarker).trim(),
                        body.substring(fromMarker + 7, toMarker).trim(),
                        body.substring(toMarker + 5).trim());
            }
        }
        return new Todo(command);
    }

    /** Marks the task identified by a one-based number as done. */
    private static void markTask(String command, Task[] tasks, int taskCount) {
        String taskNumber = command.substring("mark ".length()).trim();
        try {
            int taskIndex = Integer.parseInt(taskNumber) - 1;
            if (taskIndex < 0 || taskIndex >= taskCount) {
                System.out.println("There is no task with that number.");
                return;
            }

            tasks[taskIndex].markAsDone();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println("  " + tasks[taskIndex]);
        } catch (NumberFormatException exception) {
            System.out.println("Please specify a valid task number.");
        }
    }

    /** Marks the task identified by a one-based number as not done. */
    private static void unmarkTask(String command, Task[] tasks, int taskCount) {
        String taskNumber = command.substring("unmark ".length()).trim();
        try {
            int taskIndex = Integer.parseInt(taskNumber) - 1;
            if (taskIndex < 0 || taskIndex >= taskCount) {
                System.out.println("There is no task with that number.");
                return;
            }

            tasks[taskIndex].markAsNotDone();
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println("  " + tasks[taskIndex]);
        } catch (NumberFormatException exception) {
            System.out.println("Please specify a valid task number.");
        }
    }
}
