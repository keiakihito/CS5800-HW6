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
        validateNotNull(sender, "sender");
        validateNotNull(recipients, "recipients");
        validateNotNull(timestamp, "timestamp");
        validateNotNull(content, "content");
        
        this.sender = sender;
        this.recipients = recipients;
        this.timestamp = timestamp;
        this.content = content;
    }

    private void validateNotNull(Object value, String parameterName) {
        if (value == null) {
            throw new IllegalArgumentException(parameterName + " cannot be null");
        }
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
        validateNotNull(memento, "memento");
        return new Message(sender, recipients, memento.getTimestamp(), memento.getContent());
    }
}
