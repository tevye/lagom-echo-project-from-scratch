package com.catassetintel.mai.usvcs.actor.impl;

import static com.lightbend.lagom.javadsl.persistence.cassandra.CassandraReadSide.completedStatement;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletionStage;

import org.pcollections.PSequence;
import org.pcollections.TreePVector;

import com.catassetintel.mai.usvcs.actor.impl.ActorEvent.EchoEvent;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.google.inject.Inject;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.ReadSideProcessor;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraReadSide;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;

import akka.Done;

public class ActorEventProcessor extends ReadSideProcessor<ActorEvent> {

	  private final CassandraSession session;
	  private final CassandraReadSide readSide;

	  private PreparedStatement writeEcho = null; // initialized in prepare

	  private void setWriteEcho(PreparedStatement writeEcho) {
	    this.writeEcho = writeEcho;
	  }

	  @Inject
	  public ActorEventProcessor(CassandraSession session, CassandraReadSide readSide) {
	    this.session = session;
	    this.readSide = readSide;
	  }


	  @Override
	  public PSequence<AggregateEventTag<ActorEvent>> aggregateTags() {
	    return TreePVector.singleton(ActorEventTag.INSTANCE);
	  }

	  @Override
	  public ReadSideHandler<ActorEvent> buildHandler() {
	    return readSide.<ActorEvent>builder("salt_offset")
	            .setGlobalPrepare(this::prepareCreateTables)
	            .setPrepare((ignored) -> prepareWriteEcho())
	            .setEventHandler(EchoEvent.class, this::processEcho)
	            .build();
	  }

	  private CompletionStage<Done> prepareCreateTables() {
	    // @formatter:off
	    return session.executeCreateTable(
	        "CREATE TABLE IF NOT EXISTS echoes ("
	          + "said text, epochSecond bigint, nano int "
	          + "PRIMARY KEY (userId, epochSecond, nano))");
	    // @formatter:on
	  }

	  private CompletionStage<Done> prepareWriteEcho() {
	    return session.prepare("INSERT INTO echoes (said, epochSecond, nano) VALUES (?, ?, ?)").thenApply(ps -> {
	    	setWriteEcho(ps);
	      return Done.getInstance();
	    });
	  }

		private CompletionStage<List<BoundStatement>> processEcho(EchoEvent event) {
		    BoundStatement bindWriteSalts = writeEcho.bind();
		    bindWriteSalts.setString("said", event.uttered);
		    Instant now = Instant.now();
		    bindWriteSalts.setLong("epochSecond", now.getEpochSecond());
		    bindWriteSalts.setInt("nano", now.getNano());
		    return completedStatement(bindWriteSalts);
		}
}
