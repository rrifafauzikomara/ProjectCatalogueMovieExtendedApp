package xyz.rifafauzi.id.projectcataloguemovieextendedapp.category;

import android.app.ProgressDialog;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.R;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.adapter.MovieAdapter;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.api.BaseApiService;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.api.Server;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.db.DatabaseContract;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.entity.Movies;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.entity.ResponseMovies;

public class NowPlayingActivity extends AppCompatActivity {

    RecyclerView rvMovies;
    private MovieAdapter adapter;
    List<Movies> listMovies = new ArrayList<>();
    ProgressDialog loading;
    BaseApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        setVariable();
        if(savedInstanceState!=null){
            ArrayList<Movies> list;
            list = savedInstanceState.getParcelableArrayList("list_movie");
            adapter = new MovieAdapter(getApplicationContext(), list);
            rvMovies.setAdapter(adapter);
        }else{
            refresh();
        }

        //membuat back button toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list_movie", new ArrayList<>(listMovies));
    }

    private void setVariable() {
        rvMovies = findViewById(R.id.rv_movies);
        apiService = Server.getAPIService();
        adapter = new MovieAdapter(getApplicationContext(), listMovies);
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvMovies.setAdapter(adapter);
    }

    private void refresh(){
        loading = ProgressDialog.show(this, null, "Please wait...", true, false);
        apiService.getNowPlayingMovie(DatabaseContract.API_KEY, DatabaseContract.LANG).enqueue(new Callback<ResponseMovies>() {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home : {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}