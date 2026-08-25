import java.util.ArrayList;
import java.util.List;

/** Owns and manages Nexus's collection of tasks. */
public class TaskList {
    private final ArrayList<Task> tasks;

    /** Creates an empty task list. */
    public TaskList() { tasks = new ArrayList<>(); }

    /** Creates a task list containing the supplied tasks. */
    public TaskList(List<Task> tasks) { this.tasks = new ArrayList<>(tasks); }

    /** Adds a task. */
    public void add(Task task) { tasks.add(task); }

    /** Returns a task by zero-based index. */
    public Task get(int index) { return tasks.get(index); }

    /** Returns the number of tasks. */
    public int size() { return tasks.size(); }

    /** Returns whether an index identifies a task. */
    public boolean hasIndex(int index) { return index >= 0 && index < tasks.size(); }

    /** Marks a task as done. */
    public void mark(int index) { tasks.get(index).markAsDone(); }

    /** Marks a task as not done. */
    public void unmark(int index) { tasks.get(index).markAsNotDone(); }

    /** Deletes and returns a task. */
    public Task delete(int index) { return tasks.remove(index); }

    /** Returns a snapshot of the tasks. */
    public List<Task> asList() { return List.copyOf(tasks); }
}
