package com.example.unique_type_name;

import com.apollographql.apollo.api.Field;
import com.apollographql.apollo.api.FragmentResponseFieldMapper;
import com.apollographql.apollo.api.Operation;
import com.apollographql.apollo.api.Query;
import com.apollographql.apollo.api.ResponseFieldMapper;
import com.apollographql.apollo.api.ResponseReader;
import com.apollographql.apollo.api.internal.Optional;
import com.example.unique_type_name.fragment.HeroDetails;
import com.example.unique_type_name.type.Episode;
import java.io.IOException;
import java.lang.Double;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Generated("Apollo GraphQL")
public final class HeroDetailQuery implements Query<HeroDetailQuery.Data, Optional<HeroDetailQuery.Data>, Operation.Variables> {
  public static final String OPERATION_DEFINITION = "query HeroDetailQuery {\n"
      + "  heroDetailQuery {\n"
      + "    __typename\n"
      + "    name\n"
      + "    friends {\n"
      + "      __typename\n"
      + "      name\n"
      + "    }\n"
      + "    ... on Human {\n"
      + "      __typename\n"
      + "      height\n"
      + "      friends {\n"
      + "        __typename\n"
      + "        appearsIn\n"
      + "        friends {\n"
      + "          __typename\n"
      + "          ...HeroDetails\n"
      + "        }\n"
      + "      }\n"
      + "    }\n"
      + "  }\n"
      + "}";

  public static final String QUERY_DOCUMENT = OPERATION_DEFINITION + "\n"
   + HeroDetails.FRAGMENT_DEFINITION;

  private final Operation.Variables variables;

  public HeroDetailQuery() {
    this.variables = Operation.EMPTY_VARIABLES;
  }

  @Override
  public String queryDocument() {
    return QUERY_DOCUMENT;
  }

  @Override
  public Optional<HeroDetailQuery.Data> wrapData(HeroDetailQuery.Data data) {
    return Optional.fromNullable(data);
  }

  @Override
  public Operation.Variables variables() {
    return variables;
  }

  @Override
  public ResponseFieldMapper<HeroDetailQuery.Data> responseFieldMapper() {
    return new Data.Mapper();
  }

  public static class Data implements Operation.Data {
    private final Optional<HeroDetailQuery1> heroDetailQuery;

    public Data(@Nullable HeroDetailQuery1 heroDetailQuery) {
      this.heroDetailQuery = Optional.fromNullable(heroDetailQuery);
    }

    public Optional<HeroDetailQuery1> heroDetailQuery() {
      return this.heroDetailQuery;
    }

    @Override
    public String toString() {
      return "Data{"
        + "heroDetailQuery=" + heroDetailQuery
        + "}";
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof Data) {
        Data that = (Data) o;
        return ((this.heroDetailQuery == null) ? (that.heroDetailQuery == null) : this.heroDetailQuery.equals(that.heroDetailQuery));
      }
      return false;
    }

    @Override
    public int hashCode() {
      int h = 1;
      h *= 1000003;
      h ^= (heroDetailQuery == null) ? 0 : heroDetailQuery.hashCode();
      return h;
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final HeroDetailQuery1.Mapper heroDetailQuery1FieldMapper = new HeroDetailQuery1.Mapper();

      final Field[] fields = {
        Field.forObject("heroDetailQuery", "heroDetailQuery", null, true, new Field.ObjectReader<HeroDetailQuery1>() {
          @Override public HeroDetailQuery1 read(final ResponseReader reader) throws IOException {
            return heroDetailQuery1FieldMapper.map(reader);
          }
        })
      };

      @Override
      public Data map(ResponseReader reader) throws IOException {
        final HeroDetailQuery1 heroDetailQuery = reader.read(fields[0]);
        return new Data(heroDetailQuery);
      }
    }

    public static class Friend1 {
      private final @Nonnull Fragments fragments;

      public Friend1(@Nonnull Fragments fragments) {
        this.fragments = fragments;
      }

      public @Nonnull Fragments fragments() {
        return this.fragments;
      }

      @Override
      public String toString() {
        return "Friend1{"
          + "fragments=" + fragments
          + "}";
      }

      @Override
      public boolean equals(Object o) {
        if (o == this) {
          return true;
        }
        if (o instanceof Friend1) {
          Friend1 that = (Friend1) o;
          return ((this.fragments == null) ? (that.fragments == null) : this.fragments.equals(that.fragments));
        }
        return false;
      }

      @Override
      public int hashCode() {
        int h = 1;
        h *= 1000003;
        h ^= (fragments == null) ? 0 : fragments.hashCode();
        return h;
      }

      public static class Fragments {
        private final @Nonnull HeroDetails heroDetails;

        public Fragments(@Nonnull HeroDetails heroDetails) {
          this.heroDetails = heroDetails;
        }

        public @Nonnull HeroDetails heroDetails() {
          return this.heroDetails;
        }

        @Override
        public String toString() {
          return "Fragments{"
            + "heroDetails=" + heroDetails
            + "}";
        }

        @Override
        public boolean equals(Object o) {
          if (o == this) {
            return true;
          }
          if (o instanceof Fragments) {
            Fragments that = (Fragments) o;
            return ((this.heroDetails == null) ? (that.heroDetails == null) : this.heroDetails.equals(that.heroDetails));
          }
          return false;
        }

        @Override
        public int hashCode() {
          int h = 1;
          h *= 1000003;
          h ^= (heroDetails == null) ? 0 : heroDetails.hashCode();
          return h;
        }

        public static final class Mapper implements FragmentResponseFieldMapper<Fragments> {
          final HeroDetails.Mapper heroDetailsFieldMapper = new HeroDetails.Mapper();

          @Override
          public @Nonnull Fragments map(ResponseReader reader, @Nonnull String conditionalType)
              throws IOException {
            HeroDetails heroDetails = null;
            if (HeroDetails.POSSIBLE_TYPES.contains(conditionalType)) {
              heroDetails = heroDetailsFieldMapper.map(reader);
            }
            return new Fragments(heroDetails);
          }
        }
      }

      public static final class Mapper implements ResponseFieldMapper<Friend1> {
        final Fragments.Mapper fragmentsFieldMapper = new Fragments.Mapper();

        final Field[] fields = {
          Field.forConditionalType("__typename", "__typename", new Field.ConditionalTypeReader<Fragments>() {
            @Override
            public Fragments read(String conditionalType, ResponseReader reader) throws
                IOException {
              return fragmentsFieldMapper.map(reader, conditionalType);
            }
          })
        };

        @Override
        public Friend1 map(ResponseReader reader) throws IOException {
          final Fragments fragments = reader.read(fields[0]);
          return new Friend1(fragments);
        }
      }
    }

    public static class Friend {
      private final @Nonnull String name;

      private final @Nonnull List<Episode> appearsIn;

      private final Optional<List<Friend1>> friends;

      public Friend(@Nonnull String name, @Nonnull List<Episode> appearsIn,
          @Nullable List<Friend1> friends) {
        this.name = name;
        this.appearsIn = appearsIn;
        this.friends = Optional.fromNullable(friends);
      }

      public @Nonnull String name() {
        return this.name;
      }

      public @Nonnull List<Episode> appearsIn() {
        return this.appearsIn;
      }

      public Optional<List<Friend1>> friends() {
        return this.friends;
      }

      @Override
      public String toString() {
        return "Friend{"
          + "name=" + name + ", "
          + "appearsIn=" + appearsIn + ", "
          + "friends=" + friends
          + "}";
      }

      @Override
      public boolean equals(Object o) {
        if (o == this) {
          return true;
        }
        if (o instanceof Friend) {
          Friend that = (Friend) o;
          return ((this.name == null) ? (that.name == null) : this.name.equals(that.name))
           && ((this.appearsIn == null) ? (that.appearsIn == null) : this.appearsIn.equals(that.appearsIn))
           && ((this.friends == null) ? (that.friends == null) : this.friends.equals(that.friends));
        }
        return false;
      }

      @Override
      public int hashCode() {
        int h = 1;
        h *= 1000003;
        h ^= (name == null) ? 0 : name.hashCode();
        h *= 1000003;
        h ^= (appearsIn == null) ? 0 : appearsIn.hashCode();
        h *= 1000003;
        h ^= (friends == null) ? 0 : friends.hashCode();
        return h;
      }

      public static final class Mapper implements ResponseFieldMapper<Friend> {
        final Friend1.Mapper friend1FieldMapper = new Friend1.Mapper();

        final Field[] fields = {
          Field.forString("name", "name", null, false),
          Field.forList("appearsIn", "appearsIn", null, false, new Field.ListReader<Episode>() {
            @Override public Episode read(final Field.ListItemReader reader) throws IOException {
              return Episode.valueOf(reader.readString());
            }
          }),
          Field.forList("friends", "friends", null, true, new Field.ObjectReader<Friend1>() {
            @Override public Friend1 read(final ResponseReader reader) throws IOException {
              return friend1FieldMapper.map(reader);
            }
          })
        };

        @Override
        public Friend map(ResponseReader reader) throws IOException {
          final String name = reader.read(fields[0]);
          final List<Episode> appearsIn = reader.read(fields[1]);
          final List<Friend1> friends = reader.read(fields[2]);
          return new Friend(name, appearsIn, friends);
        }
      }
    }

    public static class AsHuman extends HeroDetailQuery1 {
      private final @Nonnull String name;

      private final Optional<List<Friend>> friends;

      private final Optional<Double> height;

      public AsHuman(@Nonnull String name, @Nullable List<Friend> friends,
          @Nullable Double height) {
        this.name = name;
        this.friends = Optional.fromNullable(friends);
        this.height = Optional.fromNullable(height);
      }

      public @Nonnull String name() {
        return this.name;
      }

      public Optional<List<Friend>> friends() {
        return this.friends;
      }

      public Optional<Double> height() {
        return this.height;
      }

      @Override
      public String toString() {
        return "AsHuman{"
          + "name=" + name + ", "
          + "friends=" + friends + ", "
          + "height=" + height
          + "}";
      }

      @Override
      public boolean equals(Object o) {
        if (o == this) {
          return true;
        }
        if (o instanceof AsHuman) {
          AsHuman that = (AsHuman) o;
          return ((this.name == null) ? (that.name == null) : this.name.equals(that.name))
           && ((this.friends == null) ? (that.friends == null) : this.friends.equals(that.friends))
           && ((this.height == null) ? (that.height == null) : this.height.equals(that.height));
        }
        return false;
      }

      @Override
      public int hashCode() {
        int h = 1;
        h *= 1000003;
        h ^= (name == null) ? 0 : name.hashCode();
        h *= 1000003;
        h ^= (friends == null) ? 0 : friends.hashCode();
        h *= 1000003;
        h ^= (height == null) ? 0 : height.hashCode();
        return h;
      }

      public static final class Mapper implements ResponseFieldMapper<AsHuman> {
        final Friend.Mapper friendFieldMapper = new Friend.Mapper();

        final Field[] fields = {
          Field.forString("name", "name", null, false),
          Field.forList("friends", "friends", null, true, new Field.ObjectReader<Friend>() {
            @Override public Friend read(final ResponseReader reader) throws IOException {
              return friendFieldMapper.map(reader);
            }
          }),
          Field.forDouble("height", "height", null, true)
        };

        @Override
        public AsHuman map(ResponseReader reader) throws IOException {
          final String name = reader.read(fields[0]);
          final List<Friend> friends = reader.read(fields[1]);
          final Double height = reader.read(fields[2]);
          return new AsHuman(name, friends, height);
        }
      }
    }

    public static class HeroDetailQuery1 {
      private final Optional<AsHuman> asHuman;

      public HeroDetailQuery1() {
        if (this instanceof AsHuman) {
          asHuman = Optional.fromNullable((AsHuman) this);
        } else {
          asHuman = Optional.absent();
        }
      }

      @Override
      public String toString() {
        return "HeroDetailQuery1{"
          + "asHuman=" + asHuman
          + "}";
      }

      @Override
      public boolean equals(Object o) {
        if (o == this) {
          return true;
        }
        if (o instanceof HeroDetailQuery1) {
          HeroDetailQuery1 that = (HeroDetailQuery1) o;
          return ((this.asHuman == null) ? (that.asHuman == null) : this.asHuman.equals(that.asHuman));
        }
        return false;
      }

      @Override
      public int hashCode() {
        int h = 1;
        h *= 1000003;
        h ^= (asHuman == null) ? 0 : asHuman.hashCode();
        return h;
      }

      public static final class Mapper implements ResponseFieldMapper<HeroDetailQuery1> {
        final AsHuman.Mapper asHumanFieldMapper = new AsHuman.Mapper();

        final Field[] fields = {
          Field.forConditionalType("__typename", "__typename", new Field.ConditionalTypeReader<AsHuman>() {
            @Override
            public AsHuman read(String conditionalType, ResponseReader reader) throws IOException {
              if (conditionalType.equals("Human")) {
                return asHumanFieldMapper.map(reader);
              } else {
                return null;
              }
            }
          })
        };

        @Override
        public HeroDetailQuery1 map(ResponseReader reader) throws IOException {
          final AsHuman asHuman = reader.read(fields[0]);
          if (asHuman != null) {
            return asHuman;
          }
          return new HeroDetailQuery1();
        }
      }
    }
  }
}
