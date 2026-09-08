package nexus;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/** Tests the shared command execution used by both Nexus user interfaces. */
class NexusTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void executeCommand_addThenList_returnsUpdatedTaskOutput() {
        Nexus nexus = new Nexus(temporaryDirectory.resolve("nexus.txt").toString());

        String addResponse = nexus.executeCommand("todo read JavaFX tutorial");
        String listResponse = nexus.executeCommand("list");

        assertTrue(addResponse.contains("I've added this task"));
        assertTrue(listResponse.contains("read JavaFX tutorial"));
    }

    @Test
    void executeCommand_snoozeTask_hidesItFromList() {
        Nexus nexus = new Nexus(temporaryDirectory.resolve("nexus.txt").toString());

        nexus.executeCommand("todo prepare presentation");
        String snoozeResponse = nexus.executeCommand("snooze 1 /until 2099-01-01");
        String listResponse = nexus.executeCommand("list");
        String findResponse = nexus.executeCommand("find presentation");

        assertTrue(snoozeResponse.contains("Snoozed this task until 2099-01-01"));
        assertTrue(!listResponse.contains("prepare presentation"));
        assertTrue(findResponse.contains("prepare presentation"));
    }
}
