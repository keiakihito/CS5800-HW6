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
        updateNextMatch();
    }

    @Override
    public boolean hasNext() {
        return nextMatch != null;
    }

    @Override
    public Message next() {
        ensureNextMatchAvailable();
        Message current = nextMatch;
        updateNextMatch();
        return current;
    }

    private void ensureNextMatchAvailable() {
        if (nextMatch == null) {
            throw new NoSuchElementException("No more matching messages");
        }
    }

    private void updateNextMatch() {
        nextMatch = findNextMatchingMessage();
    }

    private Message findNextMatchingMessage() {
        while (cursor < history.size()) {
            Message candidate = history.get(cursor++);
            if (matchesTarget(candidate)) {
                return candidate;
            }
        }
        return null;
    }

    private boolean matchesTarget(Message candidate) {
        return candidate.getSender().equals(target)
                || candidate.getRecipients().contains(target);
    }
}

