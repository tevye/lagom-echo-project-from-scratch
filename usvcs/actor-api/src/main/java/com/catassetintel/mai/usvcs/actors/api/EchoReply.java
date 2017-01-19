package com.catassetintel.mai.usvcs.actors.api;

import org.immutables.value.Value;

@Value.Immutable
public abstract class EchoReply {
	public abstract String said();
}
