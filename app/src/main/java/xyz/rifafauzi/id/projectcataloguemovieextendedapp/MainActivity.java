package xyz.rifafauzi.id.projectcataloguemovieextendedapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;

import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.adapter.MovieAdapter;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.api.BaseApiService;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.api.Server;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.category.FavoriteActivity;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.category.MostPopularActivity;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.category.NowPlayingActivity;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.category.TopRatedActivity;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.category.UpComingActivity;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.db.DatabaseContract;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.entity.Movies;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.entity.ResponseMovies;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView rvMovies;
    private MovieAdapter adapter;
    List<Movies> listMovies = new ArrayList<>();
    ProgressDialog loading;
    BaseApiService apiService;

    private final String sort_by = "popularity.desc";
    private final String include_adult = "false";
    private final String include_video = "false";
    private final String page = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setVariable();
        if(savedInstanceState!=null){
            ArrayList<Movies> list;
            list = savedInstanceState.getParcelableArrayList("list_movie");
            adapter = new MovieAdapter(getApplicationContext(), list);
            rvMovies.setAdapter(adapter);
        }else{
            refresh();
        }

//        refresh();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list_movie", new ArrayList<>(listMovies));
    }

    private void setVariable () {
        rvMovies = findViewById(R.id.rv_movies);
        apiService = Server.getAPIService();
        adapter = new MovieAdapter(getApplicationContext(), listMovies);
        rvMovies.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvMovies.setHasFixedSize(true);
        rvMovies.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setQueryHint(getResources().getString(R.string.search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMovie(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)){
                    refresh();
                }
                return false;
            }
        });

        return true;
    }

    public void searchMovie(String keyword){
        apiService.getSearchMovie(DatabaseContract.API_KEY, DatabaseContract.LANG, keyword, page, include_adult).enqueue(new Callback<ResponseMovies>() {
            @Override
            public void onResponse(Call<ResponseMovies> call, Response<ResponseMovies> response) {
                if (response.isSuccessful()){
                    listMovies = response.body().getMovies();

                    rvMovies.setAdapter(new MovieAdapter(getApplicationContext(), listMovies));
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Failed to Fetch Data !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMovies> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed to Connect Internet !", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void refresh(){
        loading = ProgressDialog.show(this, null, "Please wait...", true, false);

        apiService.getAllMovies(DatabaseContract.API_KEY, DatabaseContract.LANG, sort_by, include_adult, include_video, page).enqueue(new Callback<ResponseMovies>() {
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent most = new Intent(getApplicationContext(), SettingActivity.class);
            startActivity(most);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.most_popular) {
            Intent most = new Intent(getApplicationContext(), MostPopularActivity.class);
            startActivity(most);
            Toast.makeText(getApplicationContext(), "Show Most Popular Movies", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.top_rated) {
            Intent top = new Intent(getApplicationContext(), TopRatedActivity.class);
            startActivity(top);
            Toast.makeText(getApplicationContext(), "Show Top Rated Movies", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.now_playing) {
            Intent now = new Intent(getApplicationContext(), NowPlayingActivity.class);
            startActivity(now);
            Toast.makeText(getApplicationContext(), "Show Now Playing Movies", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.upcoming) {
            Intent up = new Intent(getApplicationContext(), UpComingActivity.class);
            startActivity(up);
            Toast.makeText(getApplicationContext(), "Show Up Coming Movies", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.favorite) {
            Intent fav = new Intent(getApplicationContext(), FavoriteActivity.class);
            startActivity(fav);
            Toast.makeText(getApplicationContext(), "Show Favorite Movies", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
