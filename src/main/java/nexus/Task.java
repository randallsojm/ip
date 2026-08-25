package nexus;

/**
 * Represents a task and its completion status.
 */
public class Task {
    private final String description;
    private boolean done;

    /** Creates a new task that is initially not done. */
    public Task(String description) {
        this.description = description;
        this.done = false;
    }

    /** Returns the task description. */
    public String getDescription() {
        return description;
    }

    /** Returns X for a completed task and a blank space otherwise. */
    public String getStatusIcon() {
        return done ? "X" : " ";
    }

    /** Marks this task as done. */
    public void markAsDone() {
        done = true;
    }

    /** Marks this task as not done. */
    public void markAsNotDone() {
        done = false;
    }

    /** Returns the category of this task. */
    public TaskType getTaskType() {
        return TaskType.TODO;
    }

    /** Returns the complete display representation of this task. */
    @Override
    public String toString() {
        return "[" + getTaskType().getIcon() + "][" + getStatusIcon() + "] " + description;
    }
}
