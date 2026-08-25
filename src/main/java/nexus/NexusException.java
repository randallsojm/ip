/** Represents an input error that Nexus can explain to the user. */
package nexus;

public class NexusException extends Exception {
    public NexusException(String message) {
        super(message);
    }
}
