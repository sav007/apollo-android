package com.apollographql.android.converter.github;

import com.google.auto.value.AutoValue;

import com.apollographql.android.converter.github.fragment.OrganizationFragment;
import com.apollographql.android.converter.github.fragment.OrganizationFragmentModel;

import java.util.Date;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@AutoValue
public abstract class UserWithOrganizationsQueryDataModel implements UserWithOrganizationsQuery.Data {

  public static final Creator CREATOR = new Creator() {
    @Override public UserWithOrganizationsQuery.Data create(@Nullable User user) {
      return new AutoValue_UserWithOrganizationsQueryDataModel(user);
    }
  };

  public static final Factory FACTORY = new Factory() {
    @Override public Creator creator() {
      return CREATOR;
    }

    @Override public User.Factory userFactory() {
      return UserModel.FACTORY;
    }
  };

  @AutoValue
  public static abstract class UserModel implements User {

    public static final Creator CREATOR = new Creator() {
      @Override
      public User create(@Nonnull String id, @Nullable String name, @Nonnull Date createdAt, @Nullable String company, @Nonnull Organization organizations) {
        return new AutoValue_UserWithOrganizationsQueryDataModel_UserModel(id, name, createdAt, company, organizations);
      }
    };

    public static final Factory FACTORY = new Factory() {
      @Override public Creator creator() {
        return CREATOR;
      }

      @Override public Organization.Factory organizationFactory() {
        return OrganizationModel.FACTORY;
      }
    };

    @AutoValue
    public static abstract class OrganizationModel implements Organization {

      public static final Creator CREATOR = new Creator() {
        @Override
        public Organization create(@Nullable List<? extends Edge> edges) {
          return new AutoValue_UserWithOrganizationsQueryDataModel_UserModel_OrganizationModel(edges);
        }
      };

      public static final Factory FACTORY = new Factory() {
        @Override public Creator creator() {
          return CREATOR;
        }

        @Override public Edge.Factory edgeFactory() {
          return EdgeModel.FACTORY;
        }
      };

      @AutoValue
      public static abstract class EdgeModel implements Edge {

        public static final Creator CREATOR = new Creator() {
          @Override
          public Edge create(@Nonnull String cursor, @Nullable Node node) {
            return new AutoValue_UserWithOrganizationsQueryDataModel_UserModel_OrganizationModel_EdgeModel(cursor, node);
          }
        };

        public static final Factory FACTORY = new Factory() {
          @Override public Creator creator() {
            return CREATOR;
          }

          @Override public Node.Factory nodeFactory() {
            return NodeModel.FACTORY;
          }
        };

        @AutoValue
        public static abstract class NodeModel implements Node {
          public static final Creator CREATOR = new Creator() {
            @Override
            public Node create(Fragments fragments) {
              return new AutoValue_UserWithOrganizationsQueryDataModel_UserModel_OrganizationModel_EdgeModel_NodeModel(fragments);
            }
          };

          public static final Factory FACTORY = new Factory() {
            @Override public Creator creator() {
              return CREATOR;
            }

            @Override public Fragments.Factory fragmentsFactory() {
              return FragmentsModel.FACTORY;
            }
          };

          @AutoValue
          public static abstract class FragmentsModel implements Fragments {
            public static final Creator CREATOR = new Creator() {
              @Override
              public Fragments create(OrganizationFragment organizationFragment) {
                return new AutoValue_UserWithOrganizationsQueryDataModel_UserModel_OrganizationModel_EdgeModel_NodeModel_FragmentsModel(organizationFragment);
              }
            };

            public static final Factory FACTORY = new Factory() {
              @Override public Creator creator() {
                return CREATOR;
              }

              @Override public OrganizationFragment.Factory organizationFragmentFactory() {
                return OrganizationFragmentModel.FACTORY;
              }
            };
          }
        }
      }
    }
  }
}
