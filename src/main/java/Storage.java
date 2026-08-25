import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Saves the current task list to a file on disk. */
public class Storage {
    private final Path filePath;

    /** Creates storage using a path relative to the project root. */
    public Storage(Path filePath) {
        this.filePath = filePath;
    }

    /** Loads tasks from disk; an absent file represents an empty task list. */
    public List<Task> load() {
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }
        // Reading existing task records will be added once the file format is formalised.
        return new ArrayList<>();
    }

    /**
     * Writes all tasks to disk, creating the parent directory and file when
     * they do not exist yet.
     */
    public void save(List<Task> tasks) {
        try {
            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            String content = tasks.stream()
                    .map(Task::toString)
                    .reduce((first, second) -> first + System.lineSeparator() + second)
                    .orElse("");
            Files.writeString(filePath, content);
        } catch (IOException exception) {
            System.out.println("OOPS!!! I couldn't save your tasks.");
        }
    }
}
