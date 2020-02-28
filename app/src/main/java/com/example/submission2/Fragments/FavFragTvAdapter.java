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
import com.example.submission2.Model.TvDataRes;
import com.example.submission2.Network.Database.FavHelperDb;
import com.example.submission2.Network.RetrofitHelper;
import com.example.submission2.R;

import java.util.List;

public class FavFragTvAdapter extends RecyclerView.Adapter<FavFragTvAdapter.MyViewHolder> {

    Context context;
    private List<TvDataRes> listMovie;
    FavHelperDb favHelperDb;

    public FavFragTvAdapter(Context context) {
        this.context = context;
        favHelperDb = new FavHelperDb(context);
        favHelperDb.open();
    }

    public FavFragTvAdapter(Context context, List<TvDataRes> listMovie) {
        this.context = context;
        this.listMovie = listMovie;
    }

    public List<TvDataRes> getListMovie() {
        return listMovie;
    }

    public void setListMovie(List<TvDataRes> listMovie) {
        this.listMovie = listMovie;
    }


    public void setData(List<TvDataRes> listModel) {
        listMovie.clear();
        listMovie.addAll(listModel);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv_favorite, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final TvDataRes MovieData = listMovie.get(position);

        holder.tvjudul.setText(MovieData.getOriginalName());
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

            img = itemView.findViewById(R.id.IvTvFavorite);
            tvjudul = itemView.findViewById(R.id.tvTvTitleFavorite);
            tvDeskripsi = itemView.findViewById(R.id.tvTvDescFavorite);
            btnHapusMovie = itemView.findViewById(R.id.btnHapusTv);
        }
    }
}
