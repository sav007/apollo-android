package com.apollographql.android.converter.github.fragment;


import com.google.auto.value.AutoValue;

import java.net.URI;

import javax.annotation.Nonnull;

@AutoValue
public abstract class OrganizationFragmentModel implements OrganizationFragment {

  public static final Creator CREATOR = new Creator() {
    @Override public OrganizationFragment create(@Nonnull String id, @Nonnull String name, @Nonnull URI projectsUrl) {
      return new AutoValue_OrganizationFragmentModel(id, name, projectsUrl);
    }
  };

  public static final Factory FACTORY = new Factory() {
    @Override public Creator creator() {
      return CREATOR;
    }
  };

}
