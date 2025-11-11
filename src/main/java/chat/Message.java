package chat;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

/** Value object for chat messages. */
public class Message {
    private final User sender;
    private final List<User> recipients;
    private final Instant timestamp;
    private final String content;

    public Message(User sender, List<User> recipients, Instant timestamp, String content) {
        this.sender = Objects.requireNonNull(sender);
        this.recipients = Objects.requireNonNull(recipients);
        this.timestamp = Objects.requireNonNull(timestamp);
        this.content = Objects.requireNonNull(content);
    }

    public User getSender() {
        return sender;
    }

    public List<User> getRecipients() {
        return recipients;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getContent() {
        return content;
    }

    public MessageMemento createMemento() {
        return new MessageMemento(timestamp, content);
    }

    public Message restore(MessageMemento memento) {
        Objects.requireNonNull(memento, "memento");
        return new Message(sender, recipients, memento.getTimestamp(), memento.getContent());
    }
}
