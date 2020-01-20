package xyz.rifafauzi.id.favoritemovie;

import android.database.Cursor;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.Objects;

import xyz.rifafauzi.id.favoritemovie.adapter.MovieAdapter;

import static xyz.rifafauzi.id.favoritemovie.db.DatabaseContract.FavoriteColumns.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private MovieAdapter adapterFavorite;
    ListView lvMovies;

    private final int LOAD_NOTES_ID = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setTitle("List Favorite Movies");

        lvMovies = findViewById(R.id.lv_movies);
        adapterFavorite = new MovieAdapter(this, null, true);
        lvMovies.setAdapter(adapterFavorite);

        getSupportLoaderManager().initLoader(LOAD_NOTES_ID, null, this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOAD_NOTES_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, CONTENT_URI, null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapterFavorite.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapterFavorite.swapCursor(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOAD_NOTES_ID);
    }

}