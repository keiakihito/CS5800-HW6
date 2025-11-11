package chat;

import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MessageMementoTest {

    @Test
    void summaryIncludesTimestampAndContent() {
        Instant timestamp = Instant.parse("2024-01-01T00:00:00Z");
        MessageMemento memento = new MessageMemento(timestamp, "hello");

        String summary = memento.getSummary();

        assertTrue(summary.contains("hello"));
        assertTrue(summary.contains("2024-01-01"));
    }
}
