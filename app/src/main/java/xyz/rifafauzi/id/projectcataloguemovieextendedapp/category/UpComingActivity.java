package xyz.rifafauzi.id.projectcataloguemovieextendedapp.category;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.BuildConfig;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.MainActivity;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.R;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.adapter.MovieAdapter;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.api.BaseApiService;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.api.Server;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.db.DatabaseContract;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.entity.Movies;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.entity.ResponseMovies;

public class UpComingActivity extends AppCompatActivity {

    RecyclerView rvMovies;
    private MovieAdapter adapter;
    List<Movies> listMovies = new ArrayList<>();
    ProgressDialog loading;
    BaseApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_coming);

        rvMovies = findViewById(R.id.rv_movies);

        apiService = Server.getAPIService();

        adapter = new MovieAdapter(getApplicationContext(), listMovies);

        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvMovies.setAdapter(adapter);

        refresh();

        //membuat back button toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void refresh(){
        loading = ProgressDialog.show(this, null, "Please wait...", true, false);
        apiService.getUpComingMovie(DatabaseContract.API_KEY, DatabaseContract.LANG).enqueue(new Callback<ResponseMovies>() {
            @Override
            public void onResponse(Call<ResponseMovies> call, Response<ResponseMovies> response) {
                if (response.isSuccessful()){
                    loading.dismiss();
                    listMovies = response.body().getMovies();
                    rvMovies.setAdapter(new MovieAdapter(getApplicationContext(), listMovies));
                    adapter.notifyDataSetChanged();
                }
                else {
                    loading.dismiss();
                    Toast.makeText(getApplicationContext(), "Failed to Fetch Data !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMovies> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Failed to Connect Internet !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}