package chat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ChatHistoryTest {
    @Test
    void appendIsNotImplemented() {
        ChatHistory history = new ChatHistory();
        Message dummy = new Message(new User("alpha"), java.util.List.of(), java.time.Instant.now(), "hi");
        assertThrows(UnsupportedOperationException.class, () -> history.append(dummy));
    }

    @Test
    void iteratorIsNotImplemented() {
        ChatHistory history = new ChatHistory();
        assertThrows(UnsupportedOperationException.class,
                () -> history.iterator(new User("beta")));
    }
}
