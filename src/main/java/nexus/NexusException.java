/** Represents an input error that Nexus can explain to the user. */
package nexus;

public class NexusException extends Exception {
    /** Creates an exception with a user-facing explanation of the input error. */
    public NexusException(String message) {
        super(message);
    }
}
