package chat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Mediator placeholder. */
public class ChatServer {

    private final Map<String, User> users = new HashMap<>();

    public void register(User user) {
        if (shouldSkipRegister(user)) {
            return;
        }
        String username = user.getName();
        validateUserNotRegistered(username);
        users.put(username, user);
    }

    private boolean shouldSkipRegister(User user) {
        if (user == null) {
            System.out.println("User is null, skipping registration");
            return true;
        }
        return false;
    }

    private void validateUserNotRegistered(String username) {
        if (users.containsKey(username)) {
            throw new IllegalArgumentException("User already registered: " + username);
        }
    }

    public void unregister(User user) {
        if (shouldSkipUnregister(user)) {
            return;
        }
        users.remove(user.getName());
    }

    private boolean shouldSkipUnregister(User user) {
        if (user == null) {
            System.out.println("User is null, skipping unregistration");
            return true;
        }
        return false;
    }

    public boolean hasUser(String username) {
        return users.containsKey(username);
    }

    public void deliver(User sender, List<User> recipients, String content) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void block(User blocker, User target) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void undoLast(User user) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
