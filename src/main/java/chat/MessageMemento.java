package chat;

import java.time.Instant;

/** Snapshot placeholder. */
public class MessageMemento {
    private final Instant timestamp;
    private final String content;

    public MessageMemento(Instant timestamp, String content) {
        this.timestamp = timestamp;
        this.content = content;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getContent() {
        return content;
    }

    public String getSummary() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
