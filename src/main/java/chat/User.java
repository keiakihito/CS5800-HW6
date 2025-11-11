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
        Objects.requireNonNull(server, "server");
        this.mediator = server;
        server.register(this);
    }

    public void sendTo(User recipient, String content) {
        sendToMany(Collections.singletonList(recipient), content);
    }

    public void sendToMany(List<User> recipients, String content) {
        if (recipients == null || recipients.isEmpty()) {
            throw new IllegalArgumentException("Recipients required");
        }
        ensureMediator().deliver(this, List.copyOf(recipients), content);
    }

    public void receive(Message message) {
        history.append(message);
    }

    public void block(User target) {
        ensureMediator().block(this, target);
    }

    public void undoLastMessage() {
        ensureMediator().undoLast(this);
    }

    public Iterator<Message> historyFor(User target) {
        return history.iterator(target);
    }

    private ChatServer ensureMediator() {
        if (mediator == null) {
            throw new IllegalStateException("User is not connected to a chat server");
        }
        return mediator;
    }
}
