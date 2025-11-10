package chat;

import java.time.Instant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MessageMementoTest {
    @Test
    void summaryIsNotImplemented() {
        MessageMemento memento = new MessageMemento(Instant.now(), "hello");
        assertThrows(UnsupportedOperationException.class, memento::getSummary);
    }
}
