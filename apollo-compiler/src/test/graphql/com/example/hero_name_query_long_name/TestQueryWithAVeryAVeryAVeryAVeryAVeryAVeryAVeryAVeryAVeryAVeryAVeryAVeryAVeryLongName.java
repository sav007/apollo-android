// AUTO-GENERATED FILE. DO NOT MODIFY.
//
// This class was automatically generated by Apollo GraphQL plugin from the GraphQL queries it found.
// It should not be modified by hand.
//
package com.example.hero_name_query_long_name;

import com.apollographql.apollo.api.Input;
import com.apollographql.apollo.api.Operation;
import com.apollographql.apollo.api.OperationName;
import com.apollographql.apollo.api.Query;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.api.ResponseField;
import com.apollographql.apollo.api.ScalarTypeAdapters;
import com.apollographql.apollo.api.internal.InputFieldMarshaller;
import com.apollographql.apollo.api.internal.InputFieldWriter;
import com.apollographql.apollo.api.internal.Optional;
import com.apollographql.apollo.api.internal.QueryDocumentMinifier;
import com.apollographql.apollo.api.internal.ResponseFieldMapper;
import com.apollographql.apollo.api.internal.ResponseFieldMarshaller;
import com.apollographql.apollo.api.internal.ResponseReader;
import com.apollographql.apollo.api.internal.ResponseWriter;
import com.apollographql.apollo.api.internal.SimpleOperationResponseParser;
import com.apollographql.apollo.api.internal.UnmodifiableMapBuilder;
import com.apollographql.apollo.api.internal.Utils;
import com.example.hero_name_query_long_name.type.Episode;
import java.io.IOException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import okio.BufferedSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TestQueryWithAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName implements Query<TestQueryWithAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName.Data, Optional<TestQueryWithAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName.Data>, TestQueryWithAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName.Variables> {
  public static final String OPERATION_ID = "559f93954a812b8951d51c0a999e5d05fedc65a77f8a9fb9a0acb48378f2984c";

  public static final String QUERY_DOCUMENT = QueryDocumentMinifier.minify(
    "query TestQueryWithAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName($episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName:Episode) {\n"
        + "  heroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName: hero(episode: $episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName) {\n"
        + "    __typename\n"
        + "    nameAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName: name\n"
        + "  }\n"
        + "}"
  );

  public static final OperationName OPERATION_NAME = new OperationName() {
    @Override
    public String name() {
      return "TestQueryWithAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName";
    }
  };

  private final TestQueryWithAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName.Variables variables;

  public TestQueryWithAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName(@NotNull Input<Episode> episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName) {
    Utils.checkNotNull(episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName, "episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName == null");
    variables = new TestQueryWithAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName.Variables(episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName);
  }

  @Override
  public String operationId() {
    return OPERATION_ID;
  }

  @Override
  public String queryDocument() {
    return QUERY_DOCUMENT;
  }

  @Override
  public Optional<TestQueryWithAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName.Data> wrapData(TestQueryWithAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName.Data data) {
    return Optional.fromNullable(data);
  }

  @Override
  public TestQueryWithAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName.Variables variables() {
    return variables;
  }

  @Override
  public ResponseFieldMapper<TestQueryWithAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName.Data> responseFieldMapper() {
    return new Data.Mapper();
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  public OperationName name() {
    return OPERATION_NAME;
  }

  @Override
  @NotNull
  public Response<Optional<TestQueryWithAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName.Data>> parse(@NotNull final BufferedSource source,
      @NotNull final ScalarTypeAdapters scalarTypeAdapters) throws IOException {
    return SimpleOperationResponseParser.parse(source, this, scalarTypeAdapters);
  }

  @Override
  @NotNull
  public Response<Optional<TestQueryWithAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName.Data>> parse(@NotNull final BufferedSource source)
      throws IOException {
    return parse(source, ScalarTypeAdapters.DEFAULT);
  }

  public static final class Builder {
    private Input<Episode> episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName = Input.absent();

    Builder() {
    }

    public Builder episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName(@Nullable Episode episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName) {
      this.episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName = Input.fromNullable(episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName);
      return this;
    }

    public Builder episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongNameInput(@NotNull Input<Episode> episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName) {
      this.episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName = Utils.checkNotNull(episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName, "episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName == null");
      return this;
    }

    public TestQueryWithAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName build() {
      return new TestQueryWithAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName(episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName);
    }
  }

  public static final class Variables extends Operation.Variables {
    private final Input<Episode> episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName;

    private final transient Map<String, Object> valueMap = new LinkedHashMap<>();

    Variables(Input<Episode> episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName) {
      this.episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName = episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName;
      if (episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName.defined) {
        this.valueMap.put("episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName", episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName.value);
      }
    }

    public Input<Episode> episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName() {
      return episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName;
    }

    @Override
    public Map<String, Object> valueMap() {
      return Collections.unmodifiableMap(valueMap);
    }

    @Override
    public InputFieldMarshaller marshaller() {
      return new InputFieldMarshaller() {
        @Override
        public void marshal(InputFieldWriter writer) throws IOException {
          if (episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName.defined) {
            writer.writeString("episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName", episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName.value != null ? episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName.value.rawValue() : null);
          }
        }
      };
    }
  }

  public static class Data implements Operation.Data {
    static final ResponseField[] $responseFields = {
      ResponseField.forObject("heroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName", "hero", new UnmodifiableMapBuilder<String, Object>(1)
      .put("episode", new UnmodifiableMapBuilder<String, Object>(2)
        .put("kind", "Variable")
        .put("variableName", "episodeAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName")
        .build())
      .build(), true, Collections.<ResponseField.Condition>emptyList())
    };

    final Optional<HeroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName> heroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName;

    private transient volatile String $toString;

    private transient volatile int $hashCode;

    private transient volatile boolean $hashCodeMemoized;

    public Data(@Nullable HeroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName heroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName) {
      this.heroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName = Optional.fromNullable(heroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName);
    }

    public Optional<HeroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName> heroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName() {
      return this.heroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName;
    }

    @SuppressWarnings("unchecked")
    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeObject($responseFields[0], heroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName.isPresent() ? heroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName.get().marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Data{"
          + "heroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName=" + heroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof Data) {
        Data that = (Data) o;
        return this.heroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName.equals(that.heroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName);
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= heroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final HeroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName.Mapper heroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongNameFieldMapper = new HeroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName.Mapper();

      @Override
      public Data map(ResponseReader reader) {
        final HeroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName heroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName = reader.readObject($responseFields[0], new ResponseReader.ObjectReader<HeroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName>() {
          @Override
          public HeroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName read(ResponseReader reader) {
            return heroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongNameFieldMapper.map(reader);
          }
        });
        return new Data(heroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName);
      }
    }
  }

  public static class HeroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("nameAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName", "name", null, false, Collections.<ResponseField.Condition>emptyList())
    };

    final @NotNull String __typename;

    final @NotNull String nameAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName;

    private transient volatile String $toString;

    private transient volatile int $hashCode;

    private transient volatile boolean $hashCodeMemoized;

    public HeroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName(@NotNull String __typename,
        @NotNull String nameAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.nameAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName = Utils.checkNotNull(nameAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName, "nameAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName == null");
    }

    public @NotNull String __typename() {
      return this.__typename;
    }

    /**
     * The name of the character
     */
    public @NotNull String nameAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName() {
      return this.nameAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName;
    }

    @SuppressWarnings("unchecked")
    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], nameAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "HeroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName{"
          + "__typename=" + __typename + ", "
          + "nameAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName=" + nameAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof HeroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName) {
        HeroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName that = (HeroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName) o;
        return this.__typename.equals(that.__typename)
         && this.nameAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName.equals(that.nameAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName);
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= __typename.hashCode();
        h *= 1000003;
        h ^= nameAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public static final class Mapper implements ResponseFieldMapper<HeroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName> {
      @Override
      public HeroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String nameAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName = reader.readString($responseFields[1]);
        return new HeroAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName(__typename, nameAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryAVeryLongName);
      }
    }
  }
}
