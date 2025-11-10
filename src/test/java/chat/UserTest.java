package chat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {
    @Test
    void sendToManyIsNotImplemented() {
        User sender = new User("alpha");
        User recipient = new User("beta");
        assertThrows(UnsupportedOperationException.class,
                () -> sender.sendTo(recipient, "hi"));
    }

    @Test
    void undoIsNotImplemented() {
        User user = new User("alpha");
        assertThrows(UnsupportedOperationException.class, user::undoLastMessage);
    }
}
