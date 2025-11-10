package chat;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ChatServerTest {
    @Test
    void registerIsNotImplemented() {
        ChatServer server = new ChatServer();
        User user = new User("alpha");
        assertThrows(UnsupportedOperationException.class, () -> server.register(user));
    }

    @Test
    void deliverIsNotImplemented() {
        ChatServer server = new ChatServer();
        User sender = new User("alpha");
        User recipient = new User("beta");
        assertThrows(UnsupportedOperationException.class,
                () -> server.deliver(sender, List.of(recipient), "hi"));
    }
}
