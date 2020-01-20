package xyz.rifafauzi.id.projectcataloguemovieextendedapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import xyz.rifafauzi.id.projectcataloguemovieextendedapp.DetailMovieActivity;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.R;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.db.DatabaseContract;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.entity.Favorite;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Context context;
    private Cursor listFavorite;

    public FavoriteAdapter(Context context){
        this.context = context;
    }

    public void setListFavorite(Cursor listFavorite) {
        this.listFavorite = listFavorite;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movie, null, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(layoutParams);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Favorite favorite = getItem(position);
        Glide.with(context)
                .load(DatabaseContract.LINK_IMAGE+favorite.getPoster())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.gmb);
        holder.judul.setText(favorite.getName());
        holder.desc.setText(favorite.getDescription());
        holder.tgl.setText(favorite.getDate());
        holder.cv_listMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailMovieActivity.class);
                i.putExtra("title", favorite.getName());
                i.putExtra("poster_path", favorite.getPoster());
                i.putExtra("overview", favorite.getDescription());
                i.putExtra("release_date", favorite.getDate());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        Log.e("DATE", ""+favorite.getDate());
    }

    @Override
    public int getItemCount() {
        if (listFavorite == null) return 0;
        return listFavorite.getCount();
    }

    private Favorite getItem(int position){
        if (!listFavorite.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Favorite(listFavorite);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView gmb;
        TextView judul, tgl, desc;
        CardView cv_listMovie;

        ViewHolder(View itemView) {
            super(itemView);

            gmb = itemView.findViewById(R.id.movie_poster);
            judul = itemView.findViewById(R.id.movie_name);
            desc = itemView.findViewById(R.id.movie_desc);
            tgl = itemView.findViewById(R.id.movie_date);
            cv_listMovie = itemView.findViewById(R.id.card_view);
        }
    }

}