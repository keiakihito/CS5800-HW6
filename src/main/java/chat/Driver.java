package chat;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/** Simple driver showcasing mediator + memento behavior. */
public class Driver {
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


/*

Sample output:

 == Basic messaging through mediator ==
Taro history:
    Aiko (2025-11-11T07:16:23.546543Z) -> Hi everyone!
    Taro (2025-11-11T07:16:23.546720Z) -> Hey Aiko
Jiro history:
    Aiko (2025-11-11T07:16:23.546543Z) -> Hi everyone!

== Blocking demo ==
Jiro history after blocking Taro:
    Aiko (2025-11-11T07:16:23.546543Z) -> Hi everyone!

== Undo last message (Memento)==
Aiko history before undo:
    Aiko (2025-11-11T07:16:23.546543Z) -> Hi everyone!
    Taro (2025-11-11T07:16:23.546720Z) -> Hey Aiko
    Taro (2025-11-11T07:16:23.548557Z) -> Lunch?
    Aiko (2025-11-11T07:16:23.548687Z) -> Oops, wrong person
Aiko history after undo:
    Aiko (2025-11-11T07:16:23.546543Z) -> Hi everyone!
    Taro (2025-11-11T07:16:23.546720Z) -> Hey Aiko
    Taro (2025-11-11T07:16:23.548557Z) -> Lunch?

== Iterator filtered view ==
Aiko -> [Taro, Jiro] : Hi everyone!
Taro -> [Aiko] : Hey Aiko
Taro -> [Jiro, Aiko] : Lunch?
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.227 s
[INFO] Finished at: 2025-11-10T23:16:23-08:00
[INFO] ------------------------------------------------------------------------


 */