package chat;

import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchMessagesByUserTest {

    @Test
    void iteratorReturnsOnlyMessagesWithTargetUser() {
        User aiko = new User("Aiko Tokumoto");
        User taro = new User("Taro Yamada");
        User jiro = new User("Jiro Yoshida");
        Message m1 = new Message(aiko, List.of(taro), Instant.EPOCH, "aiko->taro");
        Message m2 = new Message(taro, List.of(jiro), Instant.EPOCH.plusSeconds(1), "taro->jiro");
        Message m3 = new Message(jiro, List.of(aiko, taro), Instant.EPOCH.plusSeconds(2), "group");

        SearchMessagesByUser iterator = new SearchMessagesByUser(List.of(m1, m2, m3), jiro);

        assertTrue(iterator.hasNext());
        assertEquals("taro->jiro", iterator.next().getContent());
        assertTrue(iterator.hasNext());
        assertEquals("group", iterator.next().getContent());
        assertFalse(iterator.hasNext());
    }

    @Test
    void iteratorHandlesNoMatches() {
        User aiko = new User("Aiko Tokumoto");
        User taro = new User("Taro Yamada");
        User jiro = new User("Jiro Yoshida");
        Message m1 = new Message(aiko, List.of(taro), Instant.EPOCH, "aiko->taro");

        SearchMessagesByUser iterator = new SearchMessagesByUser(List.of(m1), jiro);

        assertFalse(iterator.hasNext());
    }
}
