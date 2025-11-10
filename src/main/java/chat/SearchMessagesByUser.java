package chat;

import java.util.Iterator;
import java.util.List;

/** Iterator placeholder. */
public class SearchMessagesByUser implements Iterator<Message> {
    private final List<Message> history;
    private final User target;

    public SearchMessagesByUser(List<Message> history, User target) {
        this.history = history;
        this.target = target;
    }

    @Override
    public boolean hasNext() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Message next() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
