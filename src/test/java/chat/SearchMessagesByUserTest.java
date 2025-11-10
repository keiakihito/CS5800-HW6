package chat;

import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SearchMessagesByUserTest {
    @Test
    void iteratorMethodsAreNotImplemented() {
        Message message = new Message(new User("alpha"), List.of(new User("beta")), Instant.now(), "hello");
        SearchMessagesByUser iterator = new SearchMessagesByUser(List.of(message), new User("beta"));
        assertThrows(UnsupportedOperationException.class, iterator::hasNext);
        assertThrows(UnsupportedOperationException.class, iterator::next);
    }
}
