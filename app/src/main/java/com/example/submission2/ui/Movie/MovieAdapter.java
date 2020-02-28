package com.example.submission2.ui.Movie;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.submission2.DetailMovieActivity;
import com.example.submission2.Model.MovieDataRes;
import com.example.submission2.Network.RetrofitHelper;
import com.example.submission2.R;

import java.util.List;
import java.util.logging.Logger;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {


    Context context;
    private List<MovieDataRes> listMovie;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public MovieAdapter(Context context, List<MovieDataRes> listMovie) {
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final MovieDataRes MovieData = listMovie.get(position);
        holder.title.setText(MovieData.getOriginalTitle());
        holder.deskripsi.setText(MovieData.getOverview());

        Log.d("imageData","Data : "+listMovie.get(position).getPosterPath());

        String imgApi = RetrofitHelper.BASE_URL_IMAGE + listMovie.get(position).getPosterPath();
        Glide.with(context)
                .load(imgApi)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        if (listMovie != null) {
            return listMovie.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, deskripsi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.IvMovie);
            title = itemView.findViewById(R.id.TvMovieTitle);
            deskripsi = itemView.findViewById(R.id.TvMovieDesc);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = getAdapterPosition();
                    MovieDataRes item = listMovie.get(i);
                    Intent intent = new Intent(context, DetailMovieActivity.class);
                    intent.putExtra("movie", item);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

        }
    }
}
