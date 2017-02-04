package com.apollographql.android.converter.github.fragment;

import com.apollographql.android.api.graphql.Field;
import com.apollographql.android.api.graphql.ResponseFieldMapper;
import com.apollographql.android.api.graphql.ResponseReader;
import com.apollographql.android.converter.github.type.CustomType;

import java.io.IOException;
import java.lang.String;
import java.net.URI;
import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("Apollo GraphQL")
public interface OrganizationFragment {
  String FRAGMENT_DEFINITION = "fragment OrganizationFragment on Organization {\n"
      + "  id\n"
      + "  name\n"
      + "  projectsUrl\n"
      + "}";

  String TYPE_CONDITION = "Organization";

  @Nonnull String id();

  @Nonnull String name();

  @Nonnull URI projectsUrl();

  interface Factory {
    Creator creator();
  }

  interface Creator {
    OrganizationFragment create(@Nonnull String id, @Nonnull String name, @Nonnull URI projectsUrl);
  }

  public static final class Mapper implements ResponseFieldMapper<OrganizationFragment> {
    final Factory factory;

    final Field[] fields = {
      Field.forString("id", "id", null, false),
      Field.forString("name", "name", null, false),
      Field.forCustomType("projectsUrl", "projectsUrl", null, false, CustomType.URI)
    };

    public Mapper(@Nonnull Factory factory) {
      this.factory = factory;
    }

    @Override
    public OrganizationFragment map(ResponseReader reader) throws IOException {
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
              contentValues.projectsUrl = (URI) value;
              break;
            }
          }
        }
      }, fields);
      return factory.creator().create(contentValues.id, contentValues.name, contentValues.projectsUrl);
    }

    static final class __ContentValues {
      String id;

      String name;

      URI projectsUrl;
    }
  }
}
