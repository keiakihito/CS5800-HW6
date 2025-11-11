package chat;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    @Test
    void joinRegistersUserWithServer() {
        ChatServer server = new ChatServer();
        User user = new User("Aiko Tokumoto");

        user.join(server);

        assertTrue(server.hasUser("Aiko Tokumoto"));
    }

    @Test
    void sendWithoutServerThrowsIllegalState() {
        User sender = new User("Aiko Tokumoto");
        User recipient = new User("Taro Yamada");

        assertThrows(IllegalStateException.class, () -> sender.sendTo(recipient, "hi"));
    }

    @Test
    void undoWithoutServerThrowsIllegalState() {
        User user = new User("Aiko Tokumoto");

        assertThrows(IllegalStateException.class, user::undoLastMessage);
    }

    @Test
    void sendRoutesThroughMediator() {
        ChatServer server = new ChatServer();
        User sender = new User("Aiko Tokumoto");
        User recipient = new User("Taro Yamada");
        sender.join(server);
        recipient.join(server);

        sender.sendTo(recipient, "hello");

        assertEquals("hello", recipient.getHistory().lastMessage().getContent());
        assertEquals("hello", sender.getHistory().lastMessage().getContent());
    }
}
