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
        //String token = "a819c07b800bfbcd961308a97ae90c882fb93a84";//天气scanner.nextLine();

        String token = "d3ab3c3741f48ebdf11f475e847dee8319aba594";//中译英
        //scanner.close();

        // Creating Akka Actor system: soup where Bots live
        ActorSystem system = ActorSystem.create();

        // Creating Actor of our Hello Bot
        // Information about creating bot
        // 1) Bot Class 2) Bot Token 3) Server Endpoint
        //Props botProps = Props.create(HelloBot.class, token, RemoteBot.DefaultEndpoint());//天气
        Props botProps = Props.create(C2EBot.class, token, RemoteBot.DefaultEndpoint());
        // Create Bot in ActorSystem
        system.actorOf(botProps);

        // Infinite loop
        system.awaitTermination();
    }
}
