package chat;

import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
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

    @Test
    void deliverNotifiesAllRecipients() {
        ChatServer server = new ChatServer();
        TestSinkUser sender = new TestSinkUser("Aiko Tokumoto");
        TestSinkUser receiver1 = new TestSinkUser("Receiver1");
        TestSinkUser receiver2 = new TestSinkUser("Receiver2");
        server.register(sender);
        server.register(receiver1);
        server.register(receiver2);

        server.deliver(sender, List.of(receiver1, receiver2), "Hi team");

        assertEquals("Hi team", receiver1.lastMessageContent);
        assertEquals(sender, receiver1.lastSender);
        assertEquals("Hi team", receiver2.lastMessageContent);
        assertEquals(sender, receiver2.lastSender);
    }

    @Test
    void deliverFailsIfRecipientNotRegistered() {
        ChatServer server = new ChatServer();
        TestSinkUser sender = new TestSinkUser("sender");
        TestSinkUser receiver1 = new TestSinkUser("Receiver1"); // not registered
        server.register(sender);

        assertThrows(IllegalArgumentException.class,
                () -> server.deliver(sender, List.of(receiver1), "Hi"),
                "unregistered recipient should cause failure");
    }

    @Test
    void undoLastRemovesMessageFromSenderAndRecipients() {
        ChatServer server = new ChatServer();
        HistoryUser sender = new HistoryUser("sender");
        HistoryUser receiver = new HistoryUser("receiver");
        server.register(sender);
        server.register(receiver);

        Message message = new Message(sender, List.of(receiver), Instant.EPOCH, "oops");
        sender.getHistory().append(message);
        sender.getHistory().saveSnapshot(message.createMemento());
        receiver.getHistory().append(message);

        server.undoLast(sender);

        assertNull(sender.getHistory().lastMessage());
        assertNull(receiver.getHistory().lastMessage());
    }

    @Test
    void undoLastWithoutSnapshotFails() {
        ChatServer server = new ChatServer();
        HistoryUser sender = new HistoryUser("sender");
        server.register(sender);

        assertThrows(IllegalStateException.class, () -> server.undoLast(sender));
    }

    /**
     * Message sink: dummy user that only absorbs mediator deliveries so tests can inspect sender/content.
     * Not related to sockets or concurrency—it's just a bucket to capture what ChatServer.deliver(...) emits.
     */
    private static class TestSinkUser extends User {
        private String lastMessageContent;
        private User lastSender;

        private TestSinkUser(String name) {
            super(name);
        }

        @Override
        public void receive(Message message) {
            this.lastMessageContent = message.getContent();
            this.lastSender = message.getSender();
        }
    }

    /**
     * Simple user variant that records messages into its history for undo tests.
     */
    private static class HistoryUser extends User {
        private HistoryUser(String name) {
            super(name);
        }

        @Override
        public void receive(Message message) {
            getHistory().append(message);
        }
    }
}
