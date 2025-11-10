package chat;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

/** Stores messages for a user. */
public class ChatHistory implements IterableByUser {
    private final List<Message> entries = new ArrayList<>();
    private final Deque<MessageMemento> sentSnapshots = new ArrayDeque<>();

    public void append(Message message) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Message lastMessage() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void saveSnapshot(MessageMemento memento) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public MessageMemento lastSentSnapshot() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Iterator<Message> iterator(User userToSearchWith) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
