package com.apollostack.converter.pojo;

import com.apollostack.api.graphql.Field;
import com.apollostack.api.graphql.ResponseFieldMapper;
import com.apollostack.api.graphql.ResponseReader;
import java.io.IOException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.annotation.Nullable;

public class FilmFragment {
  private static final ResponseFieldMapper<FilmFragment> MAPPER = new ResponseFieldMapper<FilmFragment>() {
    private final Field[] FIELDS = {
      Field.forString("title", "title", null, true),
      Field.forList("producers", "producers", null, true, new Field.ListReader<String>() {
        @Override public String read(final Field.ListItemReader reader) throws IOException {
          return reader.readString();
        }
      })
    };

    @Override
    public void map(final ResponseReader reader, final FilmFragment instance) throws IOException {
      reader.read(new ResponseReader.ValueHandler() {
        @Override
        public void handle(final int fieldIndex, final Object value) throws IOException {
          switch (fieldIndex) {
            case 0: {
              instance.title = (String) value;
              break;
            }
            case 1: {
              instance.producers = (List<? extends String>) value;
              break;
            }
          }
        }
      }, FIELDS);
    }
  };

  public static final String FRAGMENT_DEFINITION = "fragment FilmFragment on Film {\n"
      + "  title\n"
      + "  producers\n"
      + "}";

  public static final String TYPE_CONDITION = "Film";

  private @Nullable String title;

  private @Nullable List<? extends String> producers;

  public FilmFragment(ResponseReader reader) throws IOException {
    MAPPER.map(reader, this);
  }

  public @Nullable String title() {
    return this.title;
  }

  public @Nullable List<? extends String> producers() {
    return this.producers;
  }
}
