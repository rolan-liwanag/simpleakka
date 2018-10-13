package com.rolan.liwanag.simpleakka;

import akka.actor.AbstractActor;
import akka.actor.AbstractActor.Receive;
import akka.actor.ActorRef;
import akka.actor.Props;

import com.rolan.liwanag.simpleakka.LogActor.Greeting;

public class AnotherLogActor extends AbstractActor {

	static public Props props(String message, ActorRef actorRef) {
		return Props.create(AnotherLogActor.class, () -> new AnotherLogActor(
				message, actorRef));
	}

	static public class WhoToGreet {
		public final String who;

		public WhoToGreet(String who) {
			this.who = who;
		}
	}

	private final String message;
	private final ActorRef actorRef;
	private String greeting = "";

	public AnotherLogActor(String message, ActorRef actorRef) {
		this.message = message;
		this.actorRef = actorRef;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(WhoToGreet.class, wtg -> {
			this.greeting = message + ", " + wtg.who;
			actorRef.tell(new Greeting(greeting), getSelf());
		}).build();
	}
}
