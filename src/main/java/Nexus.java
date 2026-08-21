import java.util.ArrayList;
import java.util.Scanner;

public class Nexus {
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
        ArrayList<Task> tasks = new ArrayList<>();

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
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + "." + tasks.get(i));
                }
            } else if (command.startsWith("mark ")) {
                markTask(command, tasks);
            } else if (command.startsWith("unmark ")) {
                unmarkTask(command, tasks);
            } else if (command.startsWith("delete ")) {
                deleteTask(command, tasks);
            } else {
                try {
                    Task newTask = parseTask(command);
                    tasks.add(newTask);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + newTask);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                } catch (NexusException exception) {
                    System.out.println("OOPS!!! " + exception.getMessage());
                }
            }
            System.out.println(LINE);
        }
        scanner.close();
    }

    /** Creates the task subtype represented by a command. */
    private static Task parseTask(String command) throws NexusException {
        if (command.equals("todo") || command.startsWith("todo ")) {
            String description = command.substring("todo".length()).trim();
            if (description.isEmpty()) {
                throw new NexusException("A todo needs a description.");
            }
            return new Todo(description);
        }
        if (command.equals("deadline") || command.startsWith("deadline ")) {
            String body = command.substring("deadline".length()).trim();
            int marker = body.indexOf(" /by ");
            if (marker < 0 || body.substring(0, marker).trim().isEmpty()
                    || body.substring(marker + 5).trim().isEmpty()) {
                throw new NexusException("A deadline needs a description and a /by date.");
            }
            return new Deadline(body.substring(0, marker).trim(), body.substring(marker + 5).trim());
        }
        if (command.equals("event") || command.startsWith("event ")) {
            String body = command.substring("event".length()).trim();
            int fromMarker = body.indexOf(" /from ");
            int toMarker = body.indexOf(" /to ");
            if (fromMarker < 0 || toMarker <= fromMarker
                    || body.substring(0, fromMarker).trim().isEmpty()
                    || body.substring(fromMarker + 7, toMarker).trim().isEmpty()
                    || body.substring(toMarker + 5).trim().isEmpty()) {
                throw new NexusException("An event needs a description, /from time, and /to time.");
            }
            return new Event(body.substring(0, fromMarker).trim(),
                    body.substring(fromMarker + 7, toMarker).trim(),
                    body.substring(toMarker + 5).trim());
        }
        throw new NexusException("I'm sorry, but I don't know what that means.");
    }

    /** Marks the task identified by a one-based number as done. */
    private static void markTask(String command, ArrayList<Task> tasks) {
        String taskNumber = command.substring("mark ".length()).trim();
        try {
            int taskIndex = Integer.parseInt(taskNumber) - 1;
            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                System.out.println("There is no task with that number.");
                return;
            }

            tasks.get(taskIndex).markAsDone();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println("  " + tasks.get(taskIndex));
        } catch (NumberFormatException exception) {
            System.out.println("Please specify a valid task number.");
        }
    }

    /** Marks the task identified by a one-based number as not done. */
    private static void unmarkTask(String command, ArrayList<Task> tasks) {
        String taskNumber = command.substring("unmark ".length()).trim();
        try {
            int taskIndex = Integer.parseInt(taskNumber) - 1;
            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                System.out.println("There is no task with that number.");
                return;
            }

            tasks.get(taskIndex).markAsNotDone();
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println("  " + tasks.get(taskIndex));
        } catch (NumberFormatException exception) {
            System.out.println("Please specify a valid task number.");
        }
    }

    /** Deletes the task identified by a one-based number. */
    private static void deleteTask(String command, ArrayList<Task> tasks) {
        String taskNumber = command.substring("delete ".length()).trim();
        try {
            int taskIndex = Integer.parseInt(taskNumber) - 1;
            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                System.out.println("There is no task with that number.");
                return;
            }

            Task deletedTask = tasks.remove(taskIndex);
            System.out.println("Noted. I've removed this task:");
            System.out.println("  " + deletedTask);
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        } catch (NumberFormatException exception) {
            System.out.println("Please specify a valid task number.");
        }
    }
}
