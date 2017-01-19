package com.catassetintel.mai.usvcs.actor.impl;

import java.util.Optional;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.serialization.Jsonable;

@SuppressWarnings("serial")
@Immutable
@JsonDeserialize
public final class ActorState implements Jsonable {

  public final Optional<String> uttered;

  @JsonCreator
  public ActorState(Optional<String> said) {
    uttered = Preconditions.checkNotNull(said, "said");
  }

  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another)
      return true;
    return another instanceof ActorState && equalTo((ActorState) another);
  }

  private boolean equalTo(ActorState another) {
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
    return uttered.get();
  }
}
