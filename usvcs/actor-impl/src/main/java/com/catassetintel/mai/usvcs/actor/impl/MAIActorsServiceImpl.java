package com.catassetintel.mai.usvcs.actor.impl;

import javax.inject.Inject;

import com.catassetintel.mai.usvcs.actor.impl.ActorCommand.EchoCommand;
import com.catassetintel.mai.usvcs.actors.api.EchoReply;
import com.catassetintel.mai.usvcs.actors.api.ImmutableEchoReply;
import com.catassetintel.mai.usvcs.actors.api.MAIActorsService;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import com.lightbend.lagom.javadsl.persistence.ReadSide;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;

import akka.NotUsed;

public class MAIActorsServiceImpl implements MAIActorsService {

	private final PersistentEntityRegistry persistentEntities;
	private final CassandraSession db;

	@Inject
	public MAIActorsServiceImpl(PersistentEntityRegistry persistentEntities, ReadSide readSide,
		      CassandraSession db) {
	    this.persistentEntities = persistentEntities;
	    this.db = db;

	    persistentEntities.register(ActorEntity.class);
	    readSide.register(ActorEventProcessor.class);
	}

	@Override
	public ServiceCall<NotUsed, EchoReply> echo(String s) {
	    return request -> {
	    	return actorEntityRef(s).ask(new EchoCommand(s)).thenApply(reply -> {
	    		return ImmutableEchoReply.builder().said(s).build();
	    	});
//	    	EchoReply echo = ImmutableEchoReply.builder().said(s).build();
//	    	CompletableFuture<EchoReply> alreadyCompleted = CompletableFuture.completedFuture(echo);
//	    	try {
//				alreadyCompleted.get();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//	    	return alreadyCompleted;
	      };
	}

	private PersistentEntityRef<ActorCommand> actorEntityRef(String echo) {
	    PersistentEntityRef<ActorCommand> ref = persistentEntities.refFor(ActorEntity.class, echo);
	    return ref;
	}
/*
  @Override
  public ServiceCall<NotUsed, User> getUser(String userId) {
    return request -> {
      return friendEntityRef(userId).ask(new GetUser()).thenApply(reply -> {
        if (reply.user.isPresent())
          return reply.user.get();
        else
          throw new NotFound("user " + userId + " not found");
      });
    };
  }

 */
}
