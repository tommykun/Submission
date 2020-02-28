package com.example.submission2.ui.Tv;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.submission2.DetailMovieActivity;
import com.example.submission2.DetailTvActivity;
import com.example.submission2.Model.TvDataRes;
import com.example.submission2.Model.TvModel;
import com.example.submission2.Network.RetrofitHelper;
import com.example.submission2.R;

import java.util.List;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.ViewHolder> {

    Context context;
    private List<TvDataRes> listTv;

    public TvAdapter(Context context) {
        this.context = context;
    }

    public TvAdapter(Context context, List<TvDataRes> listTv) {
        this.context = context;
        this.listTv = listTv;
    }

    public List<TvDataRes> getListTv() {
        return listTv;
    }

    public void setListTv(List<TvDataRes> listTv) {
        this.listTv = listTv;
    }

    public void setData(List<TvDataRes> listModel) {
        listTv.clear();
        listTv.addAll(listModel);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TvDataRes TvData = listTv.get(position);
        holder.title.setText(TvData.getOriginalName());
        holder.deskripsi.setText(TvData.getOverview());

        String imgApi = RetrofitHelper.BASE_URL_IMAGE + listTv.get(position).getPosterPath();

        Glide.with(holder.itemView.getContext())
                .load(imgApi)
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        if (listTv != null){
            return listTv.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title,deskripsi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.IvTv);
            title = itemView.findViewById(R.id.TvTvTitle);
            deskripsi = itemView.findViewById(R.id.TvTvDesc);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = getAdapterPosition();
                    TvDataRes item = listTv.get(i);
                    Intent intent = new Intent(context, DetailTvActivity.class);
                    intent.putExtra("tv", item);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

        }
    }
}
