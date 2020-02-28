package com.example.submission2.Fragments;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.submission2.Model.MovieDataRes;
import com.example.submission2.Network.Database.FavHelperDb;
import com.example.submission2.Network.RetrofitHelper;
import com.example.submission2.R;

import java.util.List;

public class FavFragMovieAdapter extends RecyclerView.Adapter<FavFragMovieAdapter.MyViewHolder> {

    Context context;
    private List<MovieDataRes> listMovie;
    FavHelperDb favHelperDb;

    public FavFragMovieAdapter(Context context) {
        this.context = context;
        favHelperDb = new FavHelperDb(context);
        favHelperDb.open();
    }

    public FavFragMovieAdapter(Context context, List<MovieDataRes> listMovie) {
        this.context = context;
        this.listMovie = listMovie;
    }

    public List<MovieDataRes> getListMovie() {
        return listMovie;
    }

    public void setListMovie(List<MovieDataRes> listMovie) {
        this.listMovie = listMovie;
    }


    public void setData(List<MovieDataRes> listModel) {
        listMovie.clear();
        listMovie.addAll(listModel);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_favorite, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final MovieDataRes MovieData = listMovie.get(position);

        holder.tvjudul.setText(MovieData.getOriginalTitle());
        holder.tvDeskripsi.setText(MovieData.getOverview());

        Log.d("imageData","Data : "+listMovie.get(position).getPosterPath());

        String imgApi = RetrofitHelper.BASE_URL_IMAGE + listMovie.get(position).getPosterPath();
        Glide.with(context)
                .load(imgApi)
                .into(holder.img);

        holder.btnHapusMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt(MovieData.getID());
                favHelperDb.delFavMovie(a);
                Toast.makeText(context, "Data Telah DiHapus", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listMovie != null) {
            return listMovie.size();
        }
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView tvjudul,tvDeskripsi;
        Button btnHapusMovie;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.IvMovieFavorite);
            tvjudul = itemView.findViewById(R.id.TvMovieTitleFavorite);
            tvDeskripsi = itemView.findViewById(R.id.TvMovieDescFavorite);

            btnHapusMovie = itemView.findViewById(R.id.btnHapusMovie);
        }
    }
}
