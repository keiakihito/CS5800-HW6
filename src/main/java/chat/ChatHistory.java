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
        if (message == null) {
            throw new IllegalArgumentException("message");
        }
        entries.add(message);
    }

    public Message lastMessage() {
        if (entries.isEmpty()) {
            return null;
        }
        return entries.get(entries.size() - 1);
    }

    public void saveSnapshot(MessageMemento memento) {
        sentSnapshots.push(memento);
    }

    public MessageMemento lastSentSnapshot() {
        return sentSnapshots.peek();
    }

    @Override
    public Iterator<Message> iterator(User userToSearchWith) {
        return entries.stream()
                .filter(message -> message.getSender().equals(userToSearchWith)
                        || message.getRecipients().contains(userToSearchWith))
                .iterator();
    }
}
