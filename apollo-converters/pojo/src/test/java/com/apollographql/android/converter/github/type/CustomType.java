package com.apollographql.android.converter.github.type;

import com.apollographql.android.api.graphql.ScalarType;

import javax.annotation.Generated;

@Generated("Apollo GraphQL")
public enum CustomType implements ScalarType {
  DATETIME {
    public String typeName() {
      return "DateTime";
    }
  },

  URI {
    public String typeName() {
      return "URI";
    }
  }
}
