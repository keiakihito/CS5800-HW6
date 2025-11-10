package chat;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/** Chat user placeholder. */
public class User {
    private final String name;
    private final ChatHistory history;
    private ChatServer mediator;

    public User(String name) {
        this.name = Objects.requireNonNull(name);
        this.history = new ChatHistory();
    }

    public String getName() {
        return name;
    }

    public ChatHistory getHistory() {
        return history;
    }

    public void join(ChatServer server) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void sendTo(User recipient, String content) {
        sendToMany(Collections.singletonList(recipient), content);
    }

    public void sendToMany(List<User> recipients, String content) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void receive(Message message) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void block(User target) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void undoLastMessage() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Iterator<Message> historyFor(User target) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
