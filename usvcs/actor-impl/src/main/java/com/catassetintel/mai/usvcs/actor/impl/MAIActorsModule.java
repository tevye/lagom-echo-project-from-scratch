package com.catassetintel.mai.usvcs.actor.impl;

import com.catassetintel.mai.usvcs.actors.api.MAIActorsService;
import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

public class MAIActorsModule extends AbstractModule implements ServiceGuiceSupport {
	  @Override
	  protected void configure() {
	    bindServices(serviceBinding(MAIActorsService.class, MAIActorsServiceImpl.class));
	  }
}