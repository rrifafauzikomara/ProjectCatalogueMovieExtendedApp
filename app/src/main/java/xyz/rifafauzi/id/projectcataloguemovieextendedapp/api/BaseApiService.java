package xyz.rifafauzi.id.projectcataloguemovieextendedapp.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.entity.ResponseMovies;

public interface BaseApiService {

    @GET("3/discover/movie")
    Call<ResponseMovies> getAllMovies(@Query("api_key") String api_key,
                                      @Query("language") String language,
                                      @Query("sort_by") String sort_by,
                                      @Query("include_adult") String include_adult,
                                      @Query("include_video") String include_video,
                                      @Query("page") String page );
    @GET("/3/search/movie")
    Call<ResponseMovies> getSearchMovie(@Query("api_key") String api_key,
                                        @Query("language") String language,
                                        @Query("query") String query,
                                        @Query("page") String page,
                                        @Query("include_adult") String include_adult);

    @GET("/3/movie/now_playing")
    Call<ResponseMovies> getNowPlayingMovie(@Query("api_key") String api_key,
                                            @Query("language") String language);

    @GET("/3/movie/upcoming")
    Call<ResponseMovies> getUpComingMovie(@Query("api_key") String api_key,
                                          @Query("language") String language);

    @GET("/3/movie/popular")
    Call<ResponseMovies> getPopularMovies(@Query("api_key") String api_key,
                                          @Query("language") String language);

    @GET("/3/movie/top_rated")
    Call<ResponseMovies> getTopRatedMovies(@Query("api_key") String api_key,
                                           @Query("language") String language);

}