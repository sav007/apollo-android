package com.apollographql.android.api.graphql;

import java.io.IOException;

/** TODO */
public interface ResponseFieldMapper<T> {
  T map(final ResponseReader responseReader) throws IOException;
}
