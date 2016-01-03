package im.actor;

import akka.actor.ActorSystem;
import akka.actor.Props;
import im.actor.botkit.RemoteBot;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Reading Bot's token from console
        //Scanner scanner = new Scanner(System.in);
        //System.out.print("Enter bot token: ");
        //System.out.flush();
        String token = "537e323d381c21b83528f793fa5dab7f52e67d48";//scanner.nextLine();
        //scanner.close();

        // Creating Akka Actor system: soup where Bots live
        ActorSystem system = ActorSystem.create();

        // Creating Actor of our Hello Bot
        // Information about creating bot
        // 1) Bot Class 2) Bot Token 3) Server Endpoint
        Props botProps = Props.create(HelloBot.class, token, "ws://54.223.103.148:9090");
        // Create Bot in ActorSystem
        system.actorOf(botProps);

        // Infinite loop
        system.awaitTermination();
    }
}
