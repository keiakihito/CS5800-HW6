package chat;

import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class MessageTest {

    @Test
    void createMementoCapturesContentAndTimestamp() {
        Instant now = Instant.now();
        Message message = new Message(new User("Aiko Tokumoto"), List.of(new User("Taro Yamada")), now, "hello");

        MessageMemento memento = message.createMemento();

        assertEquals(now, memento.getTimestamp());
        assertEquals("hello", memento.getContent());
    }

    @Test
    void restoreProducesCopyFromSnapshot() {
        User sender = new User("Aiko Tokumoto");
        List<User> recipients = List.of(new User("Taro Yamada"));
        Message message = new Message(sender, recipients, Instant.EPOCH, "hello");
        MessageMemento snapshot = new MessageMemento(Instant.EPOCH.plusSeconds(5), "updated");

        Message restored = message.restore(snapshot);

        assertNotSame(message, restored, "restore should create a new instance");
        assertEquals("updated", restored.getContent());
        assertEquals(Instant.EPOCH.plusSeconds(5), restored.getTimestamp());
        assertEquals(sender, restored.getSender());
        assertEquals(recipients, restored.getRecipients());
    }
}
