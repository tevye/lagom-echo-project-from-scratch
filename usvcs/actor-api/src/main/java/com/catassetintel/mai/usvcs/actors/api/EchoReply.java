package com.catassetintel.mai.usvcs.actors.api;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Value.Immutable
@JsonDeserialize
public abstract class EchoReply {
	public abstract String said();
}
