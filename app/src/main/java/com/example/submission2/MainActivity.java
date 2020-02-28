package com.example.submission2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.amitshekhar.DebugDB;
import com.example.submission2.ui.Movie.MovieFragment;
import com.example.submission2.ui.Tv.TvFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.sql.Statement;

import static com.example.submission2.Network.Database.DatabaseHelper.DB_NAME;

public class MainActivity extends AppCompatActivity {

    Fragment fragment;
    private final String TAG_FRAG = "MyFrag";

    SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        if(savedInstanceState == null){
            navView.setSelectedItemId(R.id.navigation_home);
            fragment = (MovieFragment) getSupportFragmentManager().findFragmentByTag(TAG_FRAG);
        }

        mDatabase = openOrCreateDatabase(DB_NAME,MODE_PRIVATE,null);
        DebugDB.getAddressLog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.setting,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.setting){
            Intent i = new Intent(this,ReminderActivity.class);
            startActivity(i);
        }else if(item.getItemId() == R.id.favorite){
            startActivity(new Intent(this,FavoriteActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
