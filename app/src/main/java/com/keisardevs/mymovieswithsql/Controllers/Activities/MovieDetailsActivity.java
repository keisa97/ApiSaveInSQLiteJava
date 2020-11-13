package com.keisardevs.mymovieswithsql.Controllers.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.keisardevs.mymovieswithsql.R;
import com.keisardevs.mymovieswithsql.model.Movie;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;


public class MovieDetailsActivity extends AppCompatActivity {

    private Movie movie;
    private RatingBar ratingBar;
    private ImageView movieImage;
    private TextView title;
    private TextView releaseYear;
    private TextView genre;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Bundle extras = getIntent().getExtras();

        movie = new Movie();
        movieImage = findViewById(R.id.movie_imageView);
        ratingBar = findViewById(R.id.ratingBar);
        title =  findViewById(R.id.tv_details_name);
        releaseYear = findViewById(R.id.tv_details_year);
        genre = findViewById(R.id.tv_details_genre);


            String titleIntent = (getIntent().getStringExtra("title"));
            String imageIntent = (getIntent().getStringExtra("image"));
            int releaseYearIntent = (getIntent().getIntExtra("release", 2012));
            Double ratingIntent = (getIntent().getDoubleExtra("rating", 5.5));
            List<String>  genreList = (getIntent().getStringArrayListExtra("genre"));

            movie.setTitle(titleIntent);
            movie.setImage(imageIntent);
            movie.setReleaseYear(releaseYearIntent);
            movie.setRating(ratingIntent);
            movie.setGenre(genreList);

        System.out.println(movie);


        if (movie!= null){
            title.setText(movie.getTitle().toString());
            releaseYear.setText(String.valueOf(movie.getReleaseYear()));
            genre.setText(String.valueOf(movie.getGenre()));
            genre.setText( String.join(" ", movie.getGenre()));

            if (!movie.getImage().equals("https://www.imdb.com/title/tt0068646/mediaviewer/rm746868224")){
                Glide.with(getApplicationContext())
                        .load(movie.getImage())
                        .placeholder(R.drawable.my_logo)
                        .into(movieImage);

            }else {
                Glide.with(getApplicationContext())
                        .load(R.drawable.god_father)
                        .placeholder(R.drawable.my_logo)
                        .into(movieImage);
            }

            ratingBar.setMax(5);
            ratingBar.setNumStars(5);
            ratingBar.setRating(movie.getRating().floatValue()/2);
        }

        System.out.println(movie);


    }


}