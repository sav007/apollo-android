package com.apollographql.android.converter.github;

import com.apollographql.android.api.graphql.Field;
import com.apollographql.android.api.graphql.Operation;
import com.apollographql.android.api.graphql.Query;
import com.apollographql.android.api.graphql.ResponseFieldMapper;
import com.apollographql.android.api.graphql.ResponseReader;
import com.apollographql.android.converter.github.fragment.OrganizationFragment;
import com.apollographql.android.converter.github.type.CustomType;

import java.io.IOException;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.util.Date;
import java.util.List;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Generated("Apollo GraphQL")
public final class UserWithOrganizationsQuery implements Query<UserWithOrganizationsQuery.Variables> {
  public static final String OPERATION_DEFINITION = "query TestQuery($login: String!) {\n"
      + "  user(login: $login) {\n"
      + "    id\n"
      + "    name\n"
      + "    createdAt\n"
      + "    company\n"
      + "    organizations(first: 10) {\n"
      + "      edges {\n"
      + "        cursor\n"
      + "        node {\n"
      + "          ...OrganizationFragment\n"
      + "        }\n"
      + "      }\n"
      + "    }\n"
      + "  }\n"
      + "}";

  public static final String QUERY_DOCUMENT = OPERATION_DEFINITION + "\n"
   + OrganizationFragment.FRAGMENT_DEFINITION;

  private final Variables variables;

  public UserWithOrganizationsQuery(Variables variables) {
    this.variables = variables;
  }

  @Override
  public String queryDocument() {
    return QUERY_DOCUMENT;
  }

  @Override
  public Variables variables() {
    return variables;
  }

  public static final class Variables extends Operation.Variables {
    private final @Nonnull String login;

    Variables(@Nonnull String login) {
      this.login = login;
    }

    public @Nonnull String login() {
      return login;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Builder {
      private @Nonnull String login;

      Builder() {
      }

      public Builder login(@Nonnull String login) {
        this.login = login;
        return this;
      }

      public Variables build() {
        if (login == null) throw new IllegalStateException("login can't be null");
        return new Variables(login);
      }
    }
  }

  public interface Data extends Operation.Data {
    @Nullable User user();

    interface User {
      @Nonnull String id();

      @Nullable String name();

      @Nonnull Date createdAt();

      @Nullable String company();

      @Nonnull Organization organizations();

      interface Organization {
        @Nullable List<? extends Edge> edges();

        interface Edge {
          @Nonnull String cursor();

          @Nullable Node node();

          interface Node {
            Fragments fragments();

            interface Fragments {
              OrganizationFragment organizationFragment();

              interface Factory {
                Creator creator();

                OrganizationFragment.Factory organizationFragmentFactory();
              }

              interface Creator {
                Fragments create(OrganizationFragment organizationFragment);
              }

              public static final class Mapper implements ResponseFieldMapper<Fragments> {
                final Factory factory;

                String conditionalType;

                public Mapper(@Nonnull Factory factory, @Nonnull String conditionalType) {
                  this.factory = factory;
                  this.conditionalType = conditionalType;
                }

                @Override
                public Fragments map(ResponseReader reader) throws IOException {
                  OrganizationFragment organizationFragment = null;
                  if (conditionalType.equals(OrganizationFragment.TYPE_CONDITION)) {
                    organizationFragment = new OrganizationFragment.Mapper(factory.organizationFragmentFactory()).map(reader);
                  }
                  return factory.creator().create(organizationFragment);
                }
              }
            }

            interface Factory {
              Creator creator();

              Fragments.Factory fragmentsFactory();
            }

            interface Creator {
              Node create(Fragments fragments);
            }

            public static final class Mapper implements ResponseFieldMapper<Node> {
              final Factory factory;

              final Field[] fields = {
                Field.forConditionalType("__typename", "__typename", new Field.ConditionalTypeReader<Fragments>() {
                  @Override
                  public Fragments read(String conditionalType, ResponseReader reader) throws
                      IOException {
                    return new Fragments.Mapper(factory.fragmentsFactory(), conditionalType).map(reader);
                  }
                })
              };

              public Mapper(@Nonnull Factory factory) {
                this.factory = factory;
              }

              @Override
              public Node map(ResponseReader reader) throws IOException {
                final __ContentValues contentValues = new __ContentValues();
                reader.toBufferedReader().read(new ResponseReader.ValueHandler() {
                  @Override
                  public void handle(final int fieldIndex, final Object value) throws IOException {
                    switch (fieldIndex) {
                      case 0: {
                        contentValues.fragments = (Fragments) value;
                        break;
                      }
                    }
                  }
                }, fields);
                return factory.creator().create(contentValues.fragments);
              }

              static final class __ContentValues {
                Fragments fragments;
              }
            }
          }

          interface Factory {
            Creator creator();

            Node.Factory nodeFactory();
          }

          interface Creator {
            Edge create(@Nonnull String cursor, @Nullable Node node);
          }

          public static final class Mapper implements ResponseFieldMapper<Edge> {
            final Factory factory;

            final Field[] fields = {
              Field.forString("cursor", "cursor", null, false),
              Field.forObject("node", "node", null, true, new Field.ObjectReader<Node>() {
                @Override public Node read(final ResponseReader reader) throws IOException {
                  return new Node.Mapper(factory.nodeFactory()).map(reader);
                }
              })
            };

            public Mapper(@Nonnull Factory factory) {
              this.factory = factory;
            }

            @Override
            public Edge map(ResponseReader reader) throws IOException {
              final __ContentValues contentValues = new __ContentValues();
              reader.read(new ResponseReader.ValueHandler() {
                @Override
                public void handle(final int fieldIndex, final Object value) throws IOException {
                  switch (fieldIndex) {
                    case 0: {
                      contentValues.cursor = (String) value;
                      break;
                    }
                    case 1: {
                      contentValues.node = (Node) value;
                      break;
                    }
                  }
                }
              }, fields);
              return factory.creator().create(contentValues.cursor, contentValues.node);
            }

            static final class __ContentValues {
              String cursor;

              Node node;
            }
          }
        }

        interface Factory {
          Creator creator();

          Edge.Factory edgeFactory();
        }

        interface Creator {
          Organization create(@Nullable List<? extends Edge> edges);
        }

        public static final class Mapper implements ResponseFieldMapper<Organization> {
          final Factory factory;

          final Field[] fields = {
            Field.forList("edges", "edges", null, true, new Field.ObjectReader<Edge>() {
              @Override public Edge read(final ResponseReader reader) throws IOException {
                return new Edge.Mapper(factory.edgeFactory()).map(reader);
              }
            })
          };

          public Mapper(@Nonnull Factory factory) {
            this.factory = factory;
          }

          @Override
          public Organization map(ResponseReader reader) throws IOException {
            final __ContentValues contentValues = new __ContentValues();
            reader.read(new ResponseReader.ValueHandler() {
              @Override
              public void handle(final int fieldIndex, final Object value) throws IOException {
                switch (fieldIndex) {
                  case 0: {
                    contentValues.edges = (List<? extends Edge>) value;
                    break;
                  }
                }
              }
            }, fields);
            return factory.creator().create(contentValues.edges);
          }

          static final class __ContentValues {
            List<? extends Edge> edges;
          }
        }
      }

      interface Factory {
        Creator creator();

        Organization.Factory organizationFactory();
      }

      interface Creator {
        User create(@Nonnull String id, @Nullable String name, @Nonnull Date createdAt,
            @Nullable String company, @Nonnull Organization organizations);
      }

      public static final class Mapper implements ResponseFieldMapper<User> {
        final Factory factory;

        final Field[] fields = {
          Field.forString("id", "id", null, false),
          Field.forString("name", "name", null, true),
          Field.forCustomType("createdAt", "createdAt", null, false, CustomType.DATETIME),
          Field.forString("company", "company", null, true),
          Field.forObject("organizations", "organizations", null, false, new Field.ObjectReader<Organization>() {
            @Override public Organization read(final ResponseReader reader) throws IOException {
              return new Organization.Mapper(factory.organizationFactory()).map(reader);
            }
          })
        };

        public Mapper(@Nonnull Factory factory) {
          this.factory = factory;
        }

        @Override
        public User map(ResponseReader reader) throws IOException {
          final __ContentValues contentValues = new __ContentValues();
          reader.read(new ResponseReader.ValueHandler() {
            @Override
            public void handle(final int fieldIndex, final Object value) throws IOException {
              switch (fieldIndex) {
                case 0: {
                  contentValues.id = (String) value;
                  break;
                }
                case 1: {
                  contentValues.name = (String) value;
                  break;
                }
                case 2: {
                  contentValues.createdAt = (Date) value;
                  break;
                }
                case 3: {
                  contentValues.company = (String) value;
                  break;
                }
                case 4: {
                  contentValues.organizations = (Organization) value;
                  break;
                }
              }
            }
          }, fields);
          return factory.creator().create(contentValues.id, contentValues.name, contentValues.createdAt, contentValues.company, contentValues.organizations);
        }

        static final class __ContentValues {
          String id;

          String name;

          Date createdAt;

          String company;

          Organization organizations;
        }
      }
    }

    interface Factory {
      Creator creator();

      User.Factory userFactory();
    }

    interface Creator {
      Data create(@Nullable User user);
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final Factory factory;

      final Field[] fields = {
        Field.forObject("user", "user", null, true, new Field.ObjectReader<User>() {
          @Override public User read(final ResponseReader reader) throws IOException {
            return new User.Mapper(factory.userFactory()).map(reader);
          }
        })
      };

      public Mapper(@Nonnull Factory factory) {
        this.factory = factory;
      }

      @Override
      public Data map(ResponseReader reader) throws IOException {
        final __ContentValues contentValues = new __ContentValues();
        reader.read(new ResponseReader.ValueHandler() {
          @Override
          public void handle(final int fieldIndex, final Object value) throws IOException {
            switch (fieldIndex) {
              case 0: {
                contentValues.user = (User) value;
                break;
              }
            }
          }
        }, fields);
        return factory.creator().create(contentValues.user);
      }

      static final class __ContentValues {
        User user;
      }
    }
  }
}
