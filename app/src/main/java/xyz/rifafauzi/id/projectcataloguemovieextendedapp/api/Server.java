package xyz.rifafauzi.id.projectcataloguemovieextendedapp.api;

public class Server {

    public static final String BASE_URL_API = "https://api.themoviedb.org/";

    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }

}