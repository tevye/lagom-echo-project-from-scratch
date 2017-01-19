package com.catassetintel.mai.usvcs.actor.impl;

import java.time.Instant;
import java.util.Optional;

import javax.annotation.Nullable;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.Jsonable;

import akka.Done;

public interface ActorCommand extends Jsonable {

		  @SuppressWarnings("serial")
		  @Immutable
		  @JsonDeserialize
		  public  class EchoCommand implements ActorCommand, PersistentEntity.ReplyType<Done> {
		    public final String uttered;
		    public final long   epochSecond;
		    public final int    nano;

		    public EchoCommand(String said) {
		    	this(said, Optional.empty());
		    }
		    @JsonCreator
		    private EchoCommand(String said, Optional<Instant> when) {
		      Preconditions.checkNotNull(said, "Said");
		      uttered = said;
		      Instant ts  = when.orElse(Instant.now());
		      epochSecond = ts.getEpochSecond();
		      nano        = ts.getNano();
		    }

		    @Override
		    public boolean equals(@Nullable Object another) {
		      if (this == another)
		        return true;
		      return another instanceof EchoCommand && equalTo((EchoCommand) another);
		    }

		    private boolean equalTo(EchoCommand another) {
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
