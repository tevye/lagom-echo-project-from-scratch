package com.catassetintel.mai.usvcs.actor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.catassetintel.mai.usvcs.actor.impl.ActorCommand.EchoCommand;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import akka.Done;

public class ActorEntity extends PersistentEntity<ActorCommand, ActorEvent, ActorState> {

	  @Override
	  public Behavior initialBehavior(Optional<ActorState> snapshotState) {

		   BehaviorBuilder b = newBehaviorBuilder(snapshotState.orElse(
				      new ActorState(Optional.empty())));

		    b.setCommandHandler(EchoCommand.class, (cmd, ctx) -> {
		        String said = cmd.uttered;
		        List<ActorEvent> events = new ArrayList<ActorEvent>();
		        events.add(new ActorEvent.EchoEvent(said));
		        return ctx.thenPersistAll(events, () -> ctx.reply(Done.getInstance()));
		    });
		    
		    b.setEventHandler(ActorEvent.EchoEvent.class,  evt -> new ActorState(Optional.of(evt.uttered)));
			return b.build();
	  }
}
