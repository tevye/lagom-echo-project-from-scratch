package com.catassetintel.mai.usvcs.actor.impl;

import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;

public class ActorEventTag {

	  public static final AggregateEventTag<ActorEvent> INSTANCE = 
	    AggregateEventTag.of(ActorEvent.class);

}