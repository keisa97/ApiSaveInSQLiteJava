package com.keisardevs.mymovieswithsql.Controllers.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.keisardevs.mymovieswithsql.Controllers.Activities.Fragments.MovieFragment;
import com.keisardevs.mymovieswithsql.R;
import com.keisardevs.mymovieswithsql.View.ViewPagerAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MoviesPageActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_page);




        viewPager = findViewById(R.id.viewpager_movie_to_show);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(new MovieFragment(), "movies");
        viewPager.setAdapter(viewPagerAdapter);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        try {
            super.onRestoreInstanceState(savedInstanceState);
        }catch (Exception e){
            e.printStackTrace();
            Intent intent=new Intent(this,MoviesPageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}