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
        User alpha = new User("alpha");
        User beta = new User("beta");
        User gamma = new User("gamma");
        Message m1 = new Message(alpha, List.of(beta), Instant.EPOCH, "alpha->beta");
        Message m2 = new Message(beta, List.of(gamma), Instant.EPOCH.plusSeconds(1), "beta->gamma");
        Message m3 = new Message(gamma, List.of(alpha, beta), Instant.EPOCH.plusSeconds(2), "group");

        SearchMessagesByUser iterator = new SearchMessagesByUser(List.of(m1, m2, m3), beta);

        assertTrue(iterator.hasNext());
        assertEquals("alpha->beta", iterator.next().getContent());
        assertTrue(iterator.hasNext());
        assertEquals("group", iterator.next().getContent());
        assertFalse(iterator.hasNext());
    }

    @Test
    void iteratorHandlesNoMatches() {
        User alpha = new User("alpha");
        User beta = new User("beta");
        Message m1 = new Message(alpha, List.of(alpha), Instant.EPOCH, "self");

        SearchMessagesByUser iterator = new SearchMessagesByUser(List.of(m1), beta);

        assertFalse(iterator.hasNext());
    }
}
