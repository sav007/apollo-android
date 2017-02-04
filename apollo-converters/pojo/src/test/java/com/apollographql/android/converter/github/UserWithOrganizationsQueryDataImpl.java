package com.apollographql.android.converter.github;

import com.apollographql.android.converter.github.fragment.OrganizationFragment;
import com.apollographql.android.converter.github.fragment.OrganizationFragmentImpl;

import java.util.Date;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class UserWithOrganizationsQueryDataImpl implements UserWithOrganizationsQuery.Data {

  public static final Creator CREATOR = new Creator() {
    @Override public UserWithOrganizationsQuery.Data create(@Nullable User user) {
      return new UserWithOrganizationsQueryDataImpl(user);
    }
  };

  public static final Factory FACTORY = new Factory() {
    @Override public Creator creator() {
      return CREATOR;
    }

    @Override public User.Factory userFactory() {
      return UserImpl.FACTORY;
    }
  };

  private final User user;

  public UserWithOrganizationsQueryDataImpl(User user) {
    this.user = user;
  }

  @Nullable @Override public User user() {
    return user;
  }

  public static class UserImpl implements User {
    public static final Creator CREATOR = new Creator() {
      @Override
      public User create(@Nonnull String id, @Nullable String name, @Nonnull Date createdAt, @Nullable String company, @Nonnull Organization organizations) {
        return new UserImpl(id, name, createdAt, company, organizations);
      }
    };

    public static final Factory FACTORY = new Factory() {
      @Override public Creator creator() {
        return CREATOR;
      }

      @Override public Organization.Factory organizationFactory() {
        return OrganizationImpl.FACTORY;
      }
    };

    private final String id;
    private final String name;
    private final Date createdAt;
    private final String company;
    private final Organization organization;

    public UserImpl(String id, String name, Date createdAt, String company, Organization organization) {
      this.id = id;
      this.name = name;
      this.createdAt = createdAt;
      this.company = company;
      this.organization = organization;
    }

    @Nonnull @Override public String id() {
      return id;
    }

    @Nullable @Override public String name() {
      return name;
    }

    @Nonnull @Override public Date createdAt() {
      return createdAt;
    }

    @Nullable @Override public String company() {
      return company;
    }

    @Nonnull @Override public Organization organizations() {
      return organization;
    }

    public static class OrganizationImpl implements Organization {
      public static final Creator CREATOR = new Creator() {
        @Override
        public Organization create(@Nullable List<? extends Edge> edges) {
          return new OrganizationImpl(edges);
        }
      };

      public static final Factory FACTORY = new Factory() {
        @Override public Creator creator() {
          return CREATOR;
        }

        @Override public Edge.Factory edgeFactory() {
          return EdgeImpl.FACTORY;
        }
      };

      private final List<? extends Edge> edges;

      public OrganizationImpl(List<? extends Edge> edges) {
        this.edges = edges;
      }

      @Nullable @Override public List<? extends Edge> edges() {
        return edges;
      }

      public static class EdgeImpl implements Edge {
        public static final Creator CREATOR = new Creator() {
          @Override
          public Edge create(@Nonnull String cursor, @Nullable Node node) {
            return new EdgeImpl(cursor, node);
          }
        };

        public static final Factory FACTORY = new Factory() {
          @Override public Creator creator() {
            return CREATOR;
          }

          @Override public Node.Factory nodeFactory() {
            return NodeImpl.FACTORY;
          }
        };

        private final String cursor;
        private final Node node;

        public EdgeImpl(String cursor, Node node) {
          this.cursor = cursor;
          this.node = node;
        }

        @Nonnull @Override public String cursor() {
          return cursor;
        }

        @Nullable @Override public Node node() {
          return node;
        }

        public static class NodeImpl implements Node {
          public static final Creator CREATOR = new Creator() {
            @Override
            public Node create(Fragments fragments) {
              return new NodeImpl(fragments);
            }
          };

          public static final Factory FACTORY = new Factory() {
            @Override public Creator creator() {
              return CREATOR;
            }

            @Override public Fragments.Factory fragmentsFactory() {
              return FragmentsImpl.FACTORY;
            }
          };

          private final Fragments fragments;

          public NodeImpl(Fragments fragments) {
            this.fragments = fragments;
          }

          @Override public Fragments fragments() {
            return fragments;
          }

          public static class FragmentsImpl implements Fragments {
            public static final Creator CREATOR = new Creator() {
              @Override
              public Fragments create(OrganizationFragment organizationFragment) {
                return new FragmentsImpl(organizationFragment);
              }
            };

            public static final Factory FACTORY = new Factory() {
              @Override public Creator creator() {
                return CREATOR;
              }

              @Override public OrganizationFragment.Factory organizationFragmentFactory() {
                return OrganizationFragmentImpl.FACTORY;
              }
            };

            private final OrganizationFragment organizationFragment;

            public FragmentsImpl(OrganizationFragment organizationFragment) {
              this.organizationFragment = organizationFragment;
            }

            @Override public OrganizationFragment organizationFragment() {
              return organizationFragment;
            }
          }
        }
      }
    }
  }
}
