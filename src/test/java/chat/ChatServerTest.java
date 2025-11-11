package chat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChatServerTest {

    @Test
    void registerAddsUserToMediator() {
        ChatServer server = new ChatServer();
        User user = new User("Aiko Tokumoto");

        server.register(user);

        assertTrue(server.hasUser("Aiko Tokumoto"), "registered user should be discoverable");
    }

    @Test
    void unregisterRemovesUserFromMediator() {
        ChatServer server = new ChatServer();
        User user = new User("Aiko Tokumoto");

        server.register(user);
        server.unregister(user);

        assertFalse(server.hasUser("Aiko Tokumoto"), "user should not remain after unregister");
    }

    @Test
    void duplicateRegisterThrows() {
        ChatServer server = new ChatServer();
        User user = new User("Aiko Tokumoto");

        server.register(user);

        assertThrows(IllegalArgumentException.class, () -> server.register(user));
    }
}
