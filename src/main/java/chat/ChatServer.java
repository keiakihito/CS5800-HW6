package chat;

import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Mediator implementation coordinating chat users. */
public class ChatServer {

    private final Map<String, User> users = new HashMap<>();
    private final Map<User, Set<User>> blockedByUser = new HashMap<>();

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
            if (!isBlocked(recipient, sender)) {
                recipient.receive(message);
            }
        }
    }


    private void ensureRegistered(User user) {
        if (user == null || !hasUser(user.getName())) {
            throw new IllegalArgumentException("User not registered: " + (user == null ? "null" : user.getName()));
        }
    }

    public void block(User blocker, User target) {
        ensureBothUsersRegistered(blocker, target);
        validateNotSelfBlock(blocker, target);
        Set<User> blockedUsers = getBlockedUsersSet(blocker);
        blockedUsers.add(target);
    }

    private Set<User> getBlockedUsersSet(User blocker) {
        Set<User> blockedUsers = blockedByUser.get(blocker);
        if (blockedUsers == null) {
            blockedUsers = new HashSet<>();
            blockedByUser.put(blocker, blockedUsers);
        }
        return blockedUsers;
    }

    private void ensureBothUsersRegistered(User blocker, User target) {
        ensureRegistered(blocker);
        ensureRegistered(target);
    }

    private void validateNotSelfBlock(User blocker, User target) {
        if (blocker.equals(target)) {
            throw new IllegalArgumentException("Cannot block yourself");
        }
    }

    public void undoLast(User user) {
        ensureRegistered(user);
        ChatHistory history = user.getHistory();
        Message lastMessage = requireLastMessage(history);
        ensureSnapshotAvailable(history);
        removeMessageEverywhere(history, lastMessage);
    }

    private Message requireLastMessage(ChatHistory history) {
        Message lastMessage = history.lastMessage();
        if (lastMessage == null) {
            throw new IllegalStateException("No message available to undo");
        }
        return lastMessage;
    }

    private void ensureSnapshotAvailable(ChatHistory history) {
        if (history.lastSentSnapshot() == null) {
            throw new IllegalStateException("Undo requested without snapshot");
        }
    }

    private void removeMessageEverywhere(ChatHistory history, Message lastMessage) {
        history.removeLastSnapshot();
        history.removeMessage(lastMessage);
        removeMessageFromRecipients(lastMessage);
    }
    
    private void removeMessageFromRecipients(Message message) {
        for (User recipient : message.getRecipients()) {
            ChatHistory recipientHistory = recipient.getHistory();
            recipientHistory.removeMessage(message);
        }
    }

    private boolean isBlocked(User recipient, User sender) {
        return blockedByUser.getOrDefault(recipient, Set.of()).contains(sender);
    }
}
