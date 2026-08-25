package nexus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/** Converts user commands into tasks. */
public class Parser {
    private static final DateTimeFormatter INPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /** Parses a task-creation command. */
    public Task parseTask(String command) throws NexusException {
        if (command.equals("todo") || command.startsWith("todo ")) {
            String description = command.substring("todo".length()).trim();
            if (description.isEmpty()) throw new NexusException("A todo needs a description.");
            return new Todo(description);
        }
        if (command.equals("deadline") || command.startsWith("deadline ")) {
            String body = command.substring("deadline".length()).trim();
            int marker = body.indexOf(" /by ");
            if (marker < 0 || body.substring(0, marker).trim().isEmpty()
                    || body.substring(marker + 5).trim().isEmpty()) {
                throw new NexusException("A deadline needs a description and a /by date.");
            }
            try {
                LocalDate date = LocalDate.parse(body.substring(marker + 5).trim(), INPUT_DATE_FORMAT);
                return new Deadline(body.substring(0, marker).trim(), date);
            } catch (DateTimeParseException exception) {
                throw new NexusException("Please enter the deadline date in yyyy-MM-dd format.");
            }
        }
        if (command.equals("event") || command.startsWith("event ")) {
            String body = command.substring("event".length()).trim();
            int fromMarker = body.indexOf(" /from ");
            int toMarker = body.indexOf(" /to ");
            if (fromMarker < 0 || toMarker <= fromMarker || body.substring(0, fromMarker).trim().isEmpty()
                    || body.substring(fromMarker + 7, toMarker).trim().isEmpty()
                    || body.substring(toMarker + 5).trim().isEmpty()) {
                throw new NexusException("An event needs a description, /from time, and /to time.");
            }
            return new Event(body.substring(0, fromMarker).trim(), body.substring(fromMarker + 7, toMarker).trim(),
                    body.substring(toMarker + 5).trim());
        }
        throw new NexusException("I'm sorry, but I don't know what that means.");
    }
}
