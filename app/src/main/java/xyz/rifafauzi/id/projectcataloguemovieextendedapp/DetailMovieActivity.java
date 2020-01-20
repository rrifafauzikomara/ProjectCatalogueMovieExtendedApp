package xyz.rifafauzi.id.projectcataloguemovieextendedapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import xyz.rifafauzi.id.projectcataloguemovieextendedapp.db.DatabaseContract;

import static xyz.rifafauzi.id.projectcataloguemovieextendedapp.db.DatabaseContract.FavoriteColumns.CONTENT_URI;

public class DetailMovieActivity extends AppCompatActivity {

    String img, judul, desc, tgl;
    ImageView tvImg;
    FloatingActionButton fvFav;
    TextView tvJudul, tvDesc, tvTgl;
    CoordinatorLayout coordinatorLayout;

    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        tvImg = findViewById(R.id.poster);
        tvJudul = findViewById(R.id.judul);
        tvDesc = findViewById(R.id.desc);
        tvTgl = findViewById(R.id.tgl);
        fvFav = findViewById(R.id.love);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        setMovie();
        setFavorite();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Detail Movie");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void setMovie(){
        img = getIntent().getStringExtra("poster_path");
        judul = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("overview");
        tgl = getIntent().getStringExtra("release_date");

        Glide.with(getApplicationContext())
                .load(DatabaseContract.LINK_IMAGE+img)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(tvImg);
        tvJudul.setText(judul);
        tvDesc.setText(desc);
        tvTgl.setText(tgl);
        fvFav.setImageResource(R.drawable.ic_star_unchecked);
    }

    public boolean setFavorite(){
        Uri uri = Uri.parse(CONTENT_URI+"");
        boolean favorite = false;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        String getTitle;
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getLong(0);
                getTitle = cursor.getString(1);
                if (getTitle.equals(getIntent().getStringExtra("title"))){
                    fvFav.setImageResource(R.drawable.ic_star_checked);
                    favorite = true;
                }
            } while (cursor.moveToNext());

        }

        return favorite;

    }

    public void favorite (View view) {
        if(setFavorite()){
            Uri uri = Uri.parse(CONTENT_URI+"/"+id);
            getContentResolver().delete(uri, null, null);
            fvFav.setImageResource(R.drawable.ic_star_unchecked);
        }
        else{
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.FavoriteColumns.NAME, judul);
            values.put(DatabaseContract.FavoriteColumns.POSTER, img);
            values.put(DatabaseContract.FavoriteColumns.RELEASE_DATE, tgl);
            values.put(DatabaseContract.FavoriteColumns.DESCRIPTION, desc);

            getContentResolver().insert(CONTENT_URI, values);
            setResult(101);

            fvFav.setImageResource(R.drawable.ic_star_checked);
        }
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