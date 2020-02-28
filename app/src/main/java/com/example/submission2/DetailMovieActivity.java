package com.example.submission2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.submission2.Model.MovieDataRes;
import com.example.submission2.Model.MovieModel;
import com.example.submission2.Network.Database.FavHelperDb;
import com.example.submission2.Network.RetrofitHelper;

import static com.example.submission2.Network.Database.DatabaseHelper.DB_NAME;

public class DetailMovieActivity extends AppCompatActivity {

    ImageView coverImg, posterImg;
    ImageButton btnFav;
    TextView judul,deskripsi;
    FavHelperDb favOps;
    SQLiteDatabase mDatabase;
    private boolean fav = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        initVar();

        Intent i = getIntent();
        MovieDataRes movieModel = i.getParcelableExtra("movie");

        String imgApi = movieModel.getPosterPath();
        String dataPoster = RetrofitHelper.BASE_URL_IMAGE + imgApi;

        Glide.with(this)
                .load(dataPoster)
                .into(coverImg);
        Glide.with(this)
                .load(dataPoster)
                .into(posterImg);

        judul.setText(movieModel.getOriginalTitle());
        deskripsi.setText(movieModel.getOverview());
        if (fav){
            btnFav.getResources().getDrawable(R.drawable.ic_favorite_black_24dp);
        }else{
            btnFav.getResources().getDrawable(R.drawable.ic_favorite_white_24dp);
        }
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fav) {
                    simpanFavorite();
                    fav = true;
                    Toast.makeText(DetailMovieActivity.this, "Simpan Favorite", Toast.LENGTH_SHORT).show();

                } else {
                    favOps.delFavMovie(movieModel.getId());
                    fav = false;
                    Toast.makeText(DetailMovieActivity.this, "Hapus Favorie", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Log.d("DataBool","Data : "+fav);
    }

    private void simpanFavorite() {
        Intent i = getIntent();
        MovieDataRes movieModel = i.getParcelableExtra("movie");
        String id = String.valueOf(movieModel.getId());
        String TYPE = "Movie";
        String JUDUL = movieModel.getOriginalTitle();
        String DESC = movieModel.getOverview();

        String imgApi = movieModel.getPosterPath();

        Log.d("DataSave","Data : "+id+" | "+TYPE+" | "+JUDUL+" | "+DESC+" | "+imgApi);

        favOps.addFavMovie(id,TYPE,JUDUL,DESC,imgApi);

    }

    private void initVar() {

        coverImg = findViewById(R.id.coverImg);
        posterImg = findViewById(R.id.imageView);
        judul = findViewById(R.id.tvJudul);
        deskripsi = findViewById(R.id.tvDeskripsi);
        btnFav = findViewById(R.id.btnFav);

        favOps = new FavHelperDb(this);

        mDatabase = openOrCreateDatabase(DB_NAME,MODE_PRIVATE,null);
    }
}
