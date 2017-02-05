package com.apollographql.android.converter.github;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.io.Files;

import com.apollographql.android.api.graphql.Error;
import com.apollographql.android.api.graphql.Response;
import com.apollographql.android.converter.github.type.CustomType;
import com.apollographql.android.converter.pojo.ApolloConverterFactory;
import com.apollographql.android.converter.pojo.CustomTypeAdapter;
import com.apollographql.android.converter.pojo.OperationRequest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

import static com.google.common.truth.Truth.assertThat;

public class IntegrationTest {

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);

  private Service service;

  interface Service {
    @POST("graphql")
    Call<Response<UserWithOrganizationsQuery.Data>> heroDetails(@Body OperationRequest<UserWithOrganizationsQuery.Variables> query);
  }

  @Rule public final MockWebServer server = new MockWebServer();

  @Before public void setUp() {
    CustomTypeAdapter<Date> dateCustomTypeAdapter = new CustomTypeAdapter<Date>() {
      @Override public Date decode(String value) {
        try {
          return DATE_FORMAT.parse(value);
        } catch (ParseException e) {
          throw new RuntimeException(e);
        }
      }

      @Override public String encode(Date value) {
        return null;
      }
    };

    CustomTypeAdapter<URI> uriCustomTypeAdapter = new CustomTypeAdapter<URI>() {
      @Override public URI decode(String value) {
        try {
          return URI.create(value);
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }

      @Override public String encode(URI value) {
        return null;
      }
    };

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(server.url("/"))
        .addConverterFactory(new ApolloConverterFactory.Builder()
            .withResponseFieldMapper(UserWithOrganizationsQuery.Data.class,
                new UserWithOrganizationsQuery.Data.Mapper(UserWithOrganizationsQueryDataModel.FACTORY))
            .withCustomTypeAdapter(CustomType.DATETIME, dateCustomTypeAdapter)
            .withCustomTypeAdapter(CustomType.URI, uriCustomTypeAdapter)
            .build())
        .addConverterFactory(MoshiConverterFactory.create())
        .build();
    service = retrofit.create(Service.class);
  }

  @SuppressWarnings("ConstantConditions") @Test public void githubUserWithOrganization() throws Exception {
    server.enqueue(mockResponse("src/test/graphql/FelipeGithubUserWithOrganizationsResponse.json"));

    UserWithOrganizationsQuery query = new UserWithOrganizationsQuery(
        UserWithOrganizationsQuery.Variables.builder()
            .login("felipecsl")
            .build());

    Call<Response<UserWithOrganizationsQuery.Data>> call = service.heroDetails(new OperationRequest<>(query));
    Response<UserWithOrganizationsQuery.Data> body = call.execute().body();
    assertThat(body.isSuccessful()).isTrue();

//    assertThat(server.takeRequest().getBody().readString(Charsets.UTF_8))
//        .isEqualTo("{\"query\":\"query TestQuery {  "
//            + "allPlanets(first: 300) {"
//            + "    planets {"
//            + "      ...PlanetFragment"
//            + "      filmConnection {"
//            + "        totalCount"
//            + "        films {"
//            + "          title"
//            + "          ...FilmFragment"
//            + "        }"
//            + "      }"
//            + "    }"
//            + "  }"
//            + "}"
//            + "fragment PlanetFragment on Planet {"
//            + "  name"
//            + "  climates"
//            + "  surfaceWater"
//            + "}"
//            + "fragment FilmFragment on Film {"
//            + "  title"
//            + "  producers"
//            + "}\",\"variables\":{}}");
//
//    AllPlanets.Data data = body.data();
//    assertThat(data.allPlanets().planets().size()).isEqualTo(60);
//
//    List<String> planets = FluentIterable.from(data.allPlanets().planets())
//        .transform(new Function<AllPlanets.Data.AllPlanet.Planet, String>() {
//          @Override public String apply(AllPlanets.Data.AllPlanet.Planet planet) {
//            return planet.fragments().planetFargment().name();
//          }
//        }).toList();
//    assertThat(planets).isEqualTo(Arrays.asList(("Tatooine, Alderaan, Yavin IV, Hoth, Dagobah, Bespin, Endor, Naboo, "
//        + "Coruscant, Kamino, Geonosis, Utapau, Mustafar, Kashyyyk, Polis Massa, Mygeeto, Felucia, Cato Neimoidia, "
//        + "Saleucami, Stewjon, Eriadu, Corellia, Rodia, Nal Hutta, Dantooine, Bestine IV, Ord Mantell, unknown, "
//        + "Trandosha, Socorro, Mon Cala, Chandrila, Sullust, Toydaria, Malastare, Dathomir, Ryloth, Aleen Minor, "
//        + "Vulpter, Troiken, Tund, Haruun Kal, Cerea, Glee Anselm, Iridonia, Tholoth, Iktotch, Quermia, Dorin, "
//        + "Champala, Mirial, Serenno, Concord Dawn, Zolan, Ojom, Skako, Muunilinst, Shili, Kalee, Umbara")
//        .split("\\s*,\\s*")
//    ));
//
//    AllPlanets.Data.AllPlanet.Planet firstPlanet = data.allPlanets().planets().get(0);
//    assertThat(firstPlanet.fragments().planetFargment().climates()).isEqualTo(Collections.singletonList("arid"));
//    assertThat(firstPlanet.fragments().planetFargment().surfaceWater()).isWithin(1d);
//    assertThat(firstPlanet.filmConnection().totalCount()).isEqualTo(5);
//    assertThat(firstPlanet.filmConnection().films().size()).isEqualTo(5);
//    assertThat(firstPlanet.filmConnection().films().get(0).fragments().filmFragment().title()).isEqualTo("A New Hope");
//    assertThat(firstPlanet.filmConnection().films().get(0).fragments().filmFragment().producers()).isEqualTo(Arrays
//        .asList("Gary Kurtz", "Rick McCallum"));
  }

  private static MockResponse mockResponse(String fileName) throws IOException {
    return new MockResponse().setBody(Files.toString(new File(fileName), Charsets.UTF_8));
  }
}
