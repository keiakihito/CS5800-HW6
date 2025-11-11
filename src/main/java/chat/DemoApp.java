package chat;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/** Simple driver showcasing mediator + memento behavior. */
public class DemoApp {
    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        User aiko = new User("Aiko");
        User taro = new User("Taro");
        User jiro = new User("Jiro");

        // Register users with the mediator
        aiko.join(server);
        taro.join(server);
        jiro.join(server);

        System.out.println("== Basic messaging through mediator ==");
        aiko.sendToMany(List.of(taro, jiro), "Hi everyone!");
        taro.sendTo(aiko, "Hey Aiko");

        printHistory("Taro history", taro);
        printHistory("Jiro history", jiro);

        System.out.println("\n== Blocking demo ==");
        jiro.block(taro);
        taro.sendToMany(List.of(jiro, aiko), "Lunch?");
        printHistory("Jiro history after blocking Taro", jiro);

        System.out.println("\n== Undo last message (Memento)==");
        aiko.sendTo(jiro, "Oops, wrong person");
        printHistory("Aiko history before undo", aiko);
        aiko.undoLastMessage();
        printHistory("Aiko history after undo", aiko);

        System.out.println("\n== Iterator filtered view ==");
        Iterator<Message> aikoWithTaro = aiko.historyFor(taro);
        while (aikoWithTaro.hasNext()) {
            Message message = aikoWithTaro.next();
            System.out.printf("%s -> %s : %s%n",
                    message.getSender().getName(),
                    message.getRecipients().stream().map(User::getName).collect(Collectors.toList()),
                    message.getContent());
        }
    }

    private static void printHistory(String label, User user) {
        System.out.println(label + ":");
        user.getHistory().iterator(user).forEachRemaining(message ->
                System.out.printf("    %s (%s) -> %s%n",
                        message.getSender().getName(),
                        message.getTimestamp(),
                        message.getContent()));
    }
}
