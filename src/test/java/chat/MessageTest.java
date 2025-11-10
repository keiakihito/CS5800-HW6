package chat;

import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MessageTest {
    @Test
    void createMementoIsNotImplemented() {
        Message message = new Message(new User("alpha"), List.of(new User("beta")), Instant.now(), "hello");
        assertThrows(UnsupportedOperationException.class, message::createMemento);
    }
}
