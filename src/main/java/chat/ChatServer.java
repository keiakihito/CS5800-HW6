package chat;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Mediator implementation coordinating chat users. */
public class ChatServer {

    private final Map<String, User> users = new HashMap<>();

    public void register(User user) {
        if (shouldSkipOperation(user, "registration")) {
            return;
        }
        String username = user.getName();
        ensureNotRegistered(username);
        users.put(username, user);
    }

    private boolean shouldSkipOperation(User user, String operationName) {
        if (user == null) {
            System.out.println("User is null, skipping " + operationName);
            return true;
        }
        return false;
    }

    private void ensureNotRegistered(String username) {
        if (users.containsKey(username)) {
            throw new IllegalArgumentException("User already registered: " + username);
        }
    }

    public void unregister(User user) {
        if (shouldSkipOperation(user, "unregistration")) {
            return;
        }
        users.remove(user.getName());
    }

    public boolean hasUser(String username) {
        return users.containsKey(username);
    }

    public void deliver(User sender, List<User> recipients, String content) {
        if (shouldSkipDelivery(sender, recipients, content)) {
            return;
        }
        ensureRegistered(sender);
        validateRecipients(recipients);
        sendMessageToRecipients(sender, recipients, content);
    }

    private boolean shouldSkipDelivery(User sender, List<User> recipients, String content) {
        if (sender == null) {
            System.out.println("Sender is null, skipping message delivery");
            return true;
        }
        if (recipients == null) {
            System.out.println("Recipients is null, skipping message delivery");
            return true;
        }
        if (content == null) {
            System.out.println("Content is null, skipping message delivery");
            return true;
        }
        return false;
    }

    private void validateRecipients(List<User> recipients) {
        if (recipients.isEmpty()) {
            throw new IllegalArgumentException("No recipients specified");
        }

        for (User recipient : recipients) {
            ensureRegistered(recipient);
        }
    }

    private void sendMessageToRecipients(User sender, List<User> recipients, String content) {
        Message message = new Message(sender, List.copyOf(recipients), Instant.now(), content);
        for (User recipient : recipients) {
            recipient.receive(message);
        }
    }


    private void ensureRegistered(User user) {
        if (user == null || !hasUser(user.getName())) {
            throw new IllegalArgumentException("User not registered: " + (user == null ? "null" : user.getName()));
        }
    }

    public void block(User blocker, User target) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void undoLast(User user) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
