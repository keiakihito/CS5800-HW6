package chat;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/** Iterator iterating through messages matching a specific user. */
public class SearchMessagesByUser implements Iterator<Message> {
    private final List<Message> history;
    private final User target;
    private int cursor = 0;
    private Message nextMatch;

    public SearchMessagesByUser(List<Message> history, User target) {
        this.history = history;
        this.target = target;
        advance();
    }

    @Override
    public boolean hasNext() {
        return nextMatch != null;
    }

    @Override
    public Message next() {
        if (nextMatch == null) {
            throw new NoSuchElementException("No more matching messages");
        }
        Message current = nextMatch;
        advance();
        return current;
    }

    private void advance() {
        nextMatch = null;
        while (cursor < history.size()) {
            Message candidate = history.get(cursor++);
            if (candidate.getSender().equals(target) || candidate.getRecipients().contains(target)) {
                nextMatch = candidate;
                break;
            }
        }
    }
}
