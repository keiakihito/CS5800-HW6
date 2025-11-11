package chat;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/** Stores messages for a user. */
public class ChatHistory implements IterableByUser {
    private final List<Message> entries = new ArrayList<>();
    private final Deque<MessageMemento> sentSnapshots = new ArrayDeque<>();

    public void append(Message message) {
        if (shouldSkipAppend(message)) {
            return;
        }
        entries.add(message);
    }

    private boolean shouldSkipAppend(Message message) {
        if (message == null) {
            System.out.println("Message is null, skipping append");
            return true;
        }
        return false;
    }

    public Message lastMessage() {
        if (hasNoMessages()) {
            return null;
        }
        return getLastEntry();
    }

    private Message getLastEntry() {
        return entries.get(entries.size() - 1);
    }

    private boolean hasNoMessages() {
        return entries.isEmpty();
    }

    public void saveSnapshot(MessageMemento memento) {
        if (memento == null) {
            System.out.println("Memento is null, skipping snapshot save");
            return;
        }
        sentSnapshots.push(memento);
    }

    public MessageMemento lastSentSnapshot() {
        return sentSnapshots.peek();
    }

    public MessageMemento removeLastSnapshot() {
        if (sentSnapshots.isEmpty()) {
            return null;
        }
        return sentSnapshots.pop();
    }

    public boolean removeMessage(Message target) {
        if (target == null) {
            System.out.println("Target message is null, skipping removal");
            return false;
        }
        Iterator<Message> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Message entry = iterator.next();
            if (messagesMatch(entry, target)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    private boolean messagesMatch(Message first, Message second) {
        return Objects.equals(first.getSender(), second.getSender())
                && Objects.equals(first.getRecipients(), second.getRecipients())
                && Objects.equals(first.getTimestamp(), second.getTimestamp())
                && Objects.equals(first.getContent(), second.getContent());
    }

    @Override
    public Iterator<Message> iterator(User userToSearchWith) {
        List<Message> filteredMessages = filterMessagesForUser(userToSearchWith);
        return filteredMessages.iterator();
    }

    private List<Message> filterMessagesForUser(User userToSearchWith) {
        List<Message> filteredMessages = new ArrayList<>();
        for (Message message : entries) {
            if (isMessageRelatedToUser(message, userToSearchWith)) {
                filteredMessages.add(message);
            }
        }
        return filteredMessages;
    }

    private boolean isMessageRelatedToUser(Message message, User userToSearchWith) {
        return message.getSender().equals(userToSearchWith)
                || message.getRecipients().contains(userToSearchWith);
    }
}
