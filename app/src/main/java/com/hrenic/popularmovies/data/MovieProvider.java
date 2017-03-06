package com.hrenic.popularmovies.data;

import android.net.Uri;

import com.raizlabs.android.dbflow.annotation.provider.ContentProvider;
import com.raizlabs.android.dbflow.annotation.provider.ContentUri;
import com.raizlabs.android.dbflow.annotation.provider.TableEndpoint;

/**
 * Movie Content provider
 */
@ContentProvider(
        authority = MovieProvider.AUTHORITY,
        database = MovieDatabase.class,
        baseContentUri = MovieProvider.BASE_CONTENT_URI
)
public class MovieProvider {

    static final String AUTHORITY = "com.hrenic.popularmovies";

    static final String BASE_CONTENT_URI = "content://";

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = Uri.parse(BASE_CONTENT_URI + AUTHORITY).buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(name = MovieProviderModel.ENDPOINT, contentProvider = MovieProvider.class)
    static class MovieProviderModel {

        static final String ENDPOINT = "Movie";

        @ContentUri(
                path = ENDPOINT,
                type = ContentUri.ContentType.VND_MULTIPLE
        )
        public static final Uri CONTENT_URI = buildUri(ENDPOINT);

        @ContentUri(
                path = ENDPOINT + "/#",
                type = ContentUri.ContentType.VND_SINGLE,
                segments = {@ContentUri.PathSegment(segment = 1, column = "id")}
        )
        public static Uri withID(int id) {
            return buildUri(Integer.toString(id));
        }
    }

}
