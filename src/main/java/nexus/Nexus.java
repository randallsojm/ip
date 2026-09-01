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
            System.out.println(executeCommand(command));
            ui.showLine();
        }
        ui.close();
    }

    /** Executes a command and returns the response for a user interface to display. */
    public String executeCommand(String command) {
        StringBuilder response = new StringBuilder();
        if (command.equals("list")) {
            appendTasks(response, "Here are the tasks in your list:", tasks.asList());
        } else if (command.equals("find") || command.startsWith("find ")) {
            findTask(command, response);
        } else if (command.startsWith("mark ")) {
            markTask(command, response);
        } else if (command.startsWith("unmark ")) {
            unmarkTask(command, response);
        } else if (command.startsWith("delete ")) {
            deleteTask(command, response);
        } else {
            addTask(command, response);
        }
        return response.toString();
    }

    /** Parses, stores, and reports a newly created task. */
    private void addTask(String command, StringBuilder response) {
        try {
            Task newTask = parser.parseTask(command);
            tasks.add(newTask);
            storage.save(tasks.asList());
            response.append("Got it. I've added this task:\n  ").append(newTask)
                    .append("\nNow you have ").append(tasks.size()).append(" tasks in the list.");
        } catch (NexusException exception) {
            response.append("OOPS!!! ").append(exception.getMessage());
        }
    }

    /** Marks the task selected by a user command as complete. */
    private void markTask(String command, StringBuilder response) {
        Integer index = getTaskIndex(command, "mark ", response);
        if (index == null) {
            return;
        }
        if (!tasks.hasIndex(index)) {
            response.append("There is no task with that number.");
            return;
        }
        tasks.mark(index);
        storage.save(tasks.asList());
        response.append("Nice! I've marked this task as done:\n  ").append(tasks.get(index));
    }

    /** Marks the task selected by a user command as incomplete. */
    private void unmarkTask(String command, StringBuilder response) {
        Integer index = getTaskIndex(command, "unmark ", response);
        if (index == null) {
            return;
        }
        if (!tasks.hasIndex(index)) {
            response.append("There is no task with that number.");
            return;
        }
        tasks.unmark(index);
        storage.save(tasks.asList());
        response.append("OK, I've marked this task as not done yet:\n  ").append(tasks.get(index));
    }

    /** Deletes the task selected by a user command and reports the result. */
    private void deleteTask(String command, StringBuilder response) {
        Integer index = getTaskIndex(command, "delete ", response);
        if (index == null) {
            return;
        }
        if (!tasks.hasIndex(index)) {
            response.append("There is no task with that number.");
            return;
        }
        Task deletedTask = tasks.delete(index);
        storage.save(tasks.asList());
        response.append("Noted. I've removed this task:\n  ").append(deletedTask)
                .append("\nNow you have ").append(tasks.size()).append(" tasks in the list.");
    }

    /** Finds and displays tasks whose descriptions contain the requested keyword. */
    private void findTask(String command, StringBuilder response) {
        String keyword = command.substring("find".length()).trim();
        if (keyword.isEmpty()) {
            response.append("Please provide a keyword to search for.");
            return;
        }
        appendTasks(response, "Here are the matching tasks in your list:", tasks.find(keyword));
    }

    /** Converts a one-based task number in a command into a zero-based index. */
    private Integer getTaskIndex(String command, String prefix, StringBuilder response) {
        try {
            return Integer.parseInt(command.substring(prefix.length()).trim()) - 1;
        } catch (NumberFormatException exception) {
            response.append("Please specify a valid task number.");
            return null;
        }
    }

    /** Appends a task collection to a response. */
    private void appendTasks(StringBuilder response, String heading, java.util.List<Task> taskList) {
        response.append(heading);
        for (int i = 0; i < taskList.size(); i++) {
            response.append("\n").append(i + 1).append(".").append(taskList.get(i));
        }
    }

    /** Starts Nexus with its default relative storage path. */
    public static void main(String[] args) {
        new Nexus("data/nexus.txt").run();
    }
}
