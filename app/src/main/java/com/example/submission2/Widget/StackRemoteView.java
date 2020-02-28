package com.example.submission2.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.submission2.Model.MovieDataRes;
import com.example.submission2.Network.Database.FavHelperDb;
import com.example.submission2.Network.RetrofitHelper;
import com.example.submission2.R;

import java.util.ArrayList;
import java.util.List;


public class StackRemoteView implements RemoteViewsService.RemoteViewsFactory {


    private List<MovieDataRes> listMovie = new ArrayList<>();
    private FavHelperDb favHelperDb;
    private Context context;
    private int appWidgetId;

    public StackRemoteView(Context mContext, Intent intent) {

        context = mContext;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        SystemClock.sleep(3000);
    }

    @Override
    public void onDataSetChanged() {
        favHelperDb = new FavHelperDb(context);
        favHelperDb.open();
        listMovie.addAll(favHelperDb.getAllMovie());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listMovie.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        String img_poster = RetrofitHelper.BASE_URL_IMAGE +listMovie.get(position).getPosterPath();
        try {

            Bitmap bitmap = Glide.with(context)
                    .asBitmap()
                    .load(img_poster)
                    .submit(256,256)
                    .get();
            remoteViews.setImageViewBitmap(R.id.imageView,bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }
        remoteViews.setTextViewText(R.id.banner_text, listMovie.get(position).getTitle());

        Bundle extras = new Bundle();
        extras.putInt(FavoriteWidget.EXTRA_ITEM, position);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);

        remoteViews.setOnClickFillInIntent(R.id.imageView, fillIntent);
        SystemClock.sleep(3000);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
