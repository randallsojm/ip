package nexus;

import java.nio.file.Path;

/** Coordinates Nexus's user interface, parser, task list, and storage. */
public class Nexus {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;
    private final Parser parser;

    /** Creates Nexus using the supplied relative data path. */
    public Nexus(String filePath) {
        ui = new Ui();
        storage = new Storage(Path.of(filePath));
        tasks = new TaskList(storage.load());
        parser = new Parser();
    }

    /** Runs the command loop until the user enters bye. */
    public void run() {
        ui.showWelcome();
        while (ui.hasNextCommand()) {
            String command = ui.readCommand();
            if (command.equals("bye")) {
                ui.showGoodbye();
                break;
            }
            ui.showLine();
            handleCommand(command);
            ui.showLine();
        }
        ui.close();
    }

    /** Dispatches a command to the appropriate task or display operation. */
    private void handleCommand(String command) {
        if (command.equals("list")) {
            ui.showTasks(tasks);
        } else if (command.equals("find") || command.startsWith("find ")) {
            findTask(command);
        } else if (command.startsWith("mark ")) {
            markTask(command);
        } else if (command.startsWith("unmark ")) {
            unmarkTask(command);
        } else if (command.startsWith("delete ")) {
            deleteTask(command);
        } else {
            addTask(command);
        }
    }

    /** Parses, stores, and reports a newly created task. */
    private void addTask(String command) {
        try {
            Task newTask = parser.parseTask(command);
            tasks.add(newTask);
            storage.save(tasks.asList());
            System.out.println("Got it. I've added this task:");
            System.out.println("  " + newTask);
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        } catch (NexusException exception) {
            System.out.println("OOPS!!! " + exception.getMessage());
        }
    }

    /** Marks the task selected by a user command as complete. */
    private void markTask(String command) {
        Integer index = getTaskIndex(command, "mark ");
        if (index == null) {
            return;
        }
        if (!tasks.hasIndex(index)) {
            System.out.println("There is no task with that number.");
            return;
        }
        tasks.mark(index);
        storage.save(tasks.asList());
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + tasks.get(index));
    }

    /** Marks the task selected by a user command as incomplete. */
    private void unmarkTask(String command) {
        Integer index = getTaskIndex(command, "unmark ");
        if (index == null) {
            return;
        }
        if (!tasks.hasIndex(index)) {
            System.out.println("There is no task with that number.");
            return;
        }
        tasks.unmark(index);
        storage.save(tasks.asList());
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + tasks.get(index));
    }

    /** Deletes the task selected by a user command and reports the result. */
    private void deleteTask(String command) {
        Integer index = getTaskIndex(command, "delete ");
        if (index == null) {
            return;
        }
        if (!tasks.hasIndex(index)) {
            System.out.println("There is no task with that number.");
            return;
        }
        Task deletedTask = tasks.delete(index);
        storage.save(tasks.asList());
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + deletedTask);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
    }

    /** Finds and displays tasks whose descriptions contain the requested keyword. */
    private void findTask(String command) {
        String keyword = command.substring("find".length()).trim();
        if (keyword.isEmpty()) {
            System.out.println("Please provide a keyword to search for.");
            return;
        }
        ui.showMatchingTasks(tasks.find(keyword));
    }

    /** Converts a one-based task number in a command into a zero-based index. */
    private Integer getTaskIndex(String command, String prefix) {
        try {
            return Integer.parseInt(command.substring(prefix.length()).trim()) - 1;
        } catch (NumberFormatException exception) {
            System.out.println("Please specify a valid task number.");
            return null;
        }
    }

    /** Starts Nexus with its default relative storage path. */
    public static void main(String[] args) {
        new Nexus("data/nexus.txt").run();
    }
}
