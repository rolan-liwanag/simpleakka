package com.rolan.liwanag.simpleakka;

import java.io.IOException;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import com.rolan.liwanag.simpleakka.AnotherLogActor.WhoToGreet;

/**
 * 
 * @author rolan liwanag
 * 
 * This class can be ran by running this class directly via the main meethod
 * or using the maven command below...
 * 
 * mvn compile exec:exec
 *
 */
public class SimpleAkkaMain {
    public static void main( String[] args )
    {
    	final ActorSystem system = ActorSystem.create("helloakka");
        try {
          final ActorRef printerActor = 
            system.actorOf(LogActor.props(), "printerActor");
          final ActorRef howdyGreeter = 
            system.actorOf(AnotherLogActor.props("Howdy", printerActor), "howdyGreeter");
          final ActorRef helloGreeter = 
            system.actorOf(AnotherLogActor.props("Hello", printerActor), "helloGreeter");

          howdyGreeter.tell(new WhoToGreet("Akka"), ActorRef.noSender());

          howdyGreeter.tell(new WhoToGreet("Rolan"), ActorRef.noSender());

          helloGreeter.tell(new WhoToGreet("Java"), ActorRef.noSender());

          System.out.println(">>> Press ENTER to exit <<<");
          System.in.read();
        } catch (IOException ioe) {
        } finally {
          system.terminate();
        }
    }
}
