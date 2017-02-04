package com.apollographql.android.converter.github.fragment;

import java.net.URI;

import javax.annotation.Nonnull;

public class OrganizationFragmentImpl implements OrganizationFragment {
  public static final Creator CREATOR = new Creator() {
    @Override public OrganizationFragment create(@Nonnull String id, @Nonnull String name, @Nonnull URI projectsUrl) {
      return new OrganizationFragmentImpl(id, name, projectsUrl);
    }
  };

  public static final Factory FACTORY = new Factory() {
    @Override public Creator creator() {
      return CREATOR;
    }
  };

  private final String id;
  private final String name;
  private final URI projectsUrl;

  public OrganizationFragmentImpl(String id, String name, URI projectsUrl) {
    this.id = id;
    this.name = name;
    this.projectsUrl = projectsUrl;
  }

  @Nonnull @Override public String id() {
    return id;
  }

  @Nonnull @Override public String name() {
    return name;
  }

  @Nonnull @Override public URI projectsUrl() {
    return projectsUrl;
  }
}
