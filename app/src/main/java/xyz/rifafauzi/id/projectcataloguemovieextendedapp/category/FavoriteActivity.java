package xyz.rifafauzi.id.projectcataloguemovieextendedapp.category;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Objects;

import xyz.rifafauzi.id.projectcataloguemovieextendedapp.MainActivity;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.R;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.adapter.FavoriteAdapter;

import static xyz.rifafauzi.id.projectcataloguemovieextendedapp.db.DatabaseContract.FavoriteColumns.CONTENT_URI;

public class FavoriteActivity extends AppCompatActivity {

    RecyclerView rvMovies;
    ProgressBar progressBar;

    private FavoriteAdapter adapter;
    private Cursor listFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        progressBar = findViewById(R.id.progressbar);
        rvMovies = findViewById(R.id.rv_movies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setHasFixedSize(true);

        adapter = new FavoriteAdapter(this);
        adapter.setListFavorite(listFavorite);
        rvMovies.setAdapter(adapter);

        new LoadNoteAsync().execute();

        //membuat back button toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private class LoadNoteAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor favorite) {
            super.onPostExecute(favorite);
            progressBar.setVisibility(View.GONE);

            listFavorite = favorite;
            adapter.setListFavorite(listFavorite);
            adapter.notifyDataSetChanged();

            if (listFavorite.getCount() == 0) {
                showSnackbarMessage("Tidak ada data saat ini");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Tampilkan snackbar
     *
     * @param message inputan message
     */
    private void showSnackbarMessage(String message) {
        Snackbar.make(rvMovies, message, Snackbar.LENGTH_SHORT).show();
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