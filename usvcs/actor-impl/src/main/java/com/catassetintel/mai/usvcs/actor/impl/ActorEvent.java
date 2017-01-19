package com.catassetintel.mai.usvcs.actor.impl;

import java.util.Optional;

import javax.annotation.Nullable;

import javax.annotation.concurrent.Immutable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.serialization.Jsonable;

public interface ActorEvent extends Jsonable, AggregateEvent<ActorEvent> {

	  @Override
	  default public AggregateEventTag<ActorEvent> aggregateTag() {
	    return ActorEventTag.INSTANCE;
	  }

	  @SuppressWarnings("serial")
	  @Immutable
	  @JsonDeserialize
	  public class EchoEvent implements ActorEvent {
	    public final String  uttered;
	    public final long   epochSecond;
	    public final int    nano;

	    public EchoEvent(String said) {
	    	this(said, Optional.empty());
	    }
	    @JsonCreator
	    private EchoEvent(String said, Optional<Instant> when) {
	      uttered     = said;
	      Instant ts  = when.orElse(Instant.now());
	      epochSecond = ts.getEpochSecond();
	      nano        = ts.getNano();
	    }

	    @Override
	    public boolean equals(@Nullable Object another) {
	      if (this == another)
	        return true;
	      return another instanceof EchoEvent && equalTo((EchoEvent) another);
	    }

	    private boolean equalTo(EchoEvent another) {
	      return uttered.equals(another.uttered);
	    }

	    @Override
	    public int hashCode() {
	      int h = 31;
	      h = h * 17 + uttered.hashCode();
	      return h;
	    }

	    @Override
	    public String toString() {
	      return uttered;
	    }
	  }


}
