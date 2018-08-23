package xyz.rifafauzi.id.favoritemovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import xyz.rifafauzi.id.favoritemovie.DetailFavoriteActivity;
import xyz.rifafauzi.id.favoritemovie.R;

import static xyz.rifafauzi.id.favoritemovie.db.DatabaseContract.FavoriteColumns.DESCRIPTION;
import static xyz.rifafauzi.id.favoritemovie.db.DatabaseContract.FavoriteColumns.NAME;
import static xyz.rifafauzi.id.favoritemovie.db.DatabaseContract.FavoriteColumns.POSTER;
import static xyz.rifafauzi.id.favoritemovie.db.DatabaseContract.FavoriteColumns.RELEASE_DATE;
import static xyz.rifafauzi.id.favoritemovie.db.DatabaseContract.getColumnString;

public class MovieAdapter extends CursorAdapter {

    public MovieAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_favorite, parent, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        if (cursor != null){
            TextView textViewTitle, textViewOverview, textViewRelease;
            ImageView imgPoster;

            final String loadPoster = "http://image.tmdb.org/t/p/w185" + getColumnString(cursor, POSTER);

            textViewTitle = view.findViewById(R.id.movie_name);
            textViewOverview = view.findViewById(R.id.movie_desc);
            textViewRelease = view.findViewById(R.id.movie_date);
            imgPoster = view.findViewById(R.id.movie_poster);
            CardView cv_listMovie = view.findViewById(R.id.card_view);
            cv_listMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DetailFavoriteActivity.class);
                    i.putExtra("title", getColumnString(cursor,NAME));
                    i.putExtra("poster_path", loadPoster);
                    i.putExtra("overview", getColumnString(cursor,DESCRIPTION));
                    i.putExtra("release_date", getColumnString(cursor,RELEASE_DATE));
                    context.startActivity(i);
                }
            });

            textViewTitle.setText(getColumnString(cursor,NAME));
            textViewOverview.setText(getColumnString(cursor,DESCRIPTION));
            textViewRelease.setText(getColumnString(cursor,RELEASE_DATE));
            Picasso.with(context).load(loadPoster)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(imgPoster);
        }
    }
}