package com.catassetintel.mai.usvcs.actors.api;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.restCall;

//import org.pcollections.PSequence;

import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

import akka.NotUsed;

public interface MAIActorsService extends Service {
	  ServiceCall<NotUsed, EchoReply> echo(String s);
	  @Override
	  default Descriptor descriptor() {
	    // @formatter:off
	    return named("cmai_actors").withCalls(
		        restCall(Method.GET, "/echo/:s", this::echo)
	      ).withAutoAcl(true);
	    // @formatter:on
	  }
}
