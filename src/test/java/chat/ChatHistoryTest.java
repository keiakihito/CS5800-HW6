package chat;

import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChatHistoryTest {

    @Test
    void appendStoresMessagesInOrder() {
        ChatHistory history = new ChatHistory();
        User aiko = new User("Aiko Tokumoto");
        User taro = new User("Taro Yamada");
        Message firstMsg = new Message(aiko, List.of(taro), Instant.EPOCH, "hi");
        Message secondMsg = new Message(aiko, List.of(taro), Instant.EPOCH.plusSeconds(1), "yo");

        history.append(firstMsg);
        history.append(secondMsg);

        assertEquals("yo", history.lastMessage().getContent());
    }

    @Test
    void iteratorFiltersByUser() {
        ChatHistory history = new ChatHistory();
        User aiko = new User("Aiko Tokumoto");
        User taro = new User("Taro Yamada");
        Message firstMsg = new Message(aiko, List.of(taro), Instant.EPOCH, "hello");
        Message secondMsg = new Message(aiko, List.of(aiko), Instant.EPOCH, "self");
        history.append(firstMsg);
        history.append(secondMsg);

        var iterator = history.iterator(taro);

        assertTrue(iterator.hasNext());
        assertEquals("hello", iterator.next().getContent());
        assertFalse(iterator.hasNext());
    }
}
