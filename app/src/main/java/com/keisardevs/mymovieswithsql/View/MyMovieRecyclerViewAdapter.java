package com.keisardevs.mymovieswithsql.View;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.keisardevs.mymovieswithsql.Controllers.Activities.MovieDetailsActivity;
import com.keisardevs.mymovieswithsql.R;
import com.keisardevs.mymovieswithsql.model.Movie;


import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class MyMovieRecyclerViewAdapter extends RecyclerView.Adapter<MyMovieRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<Movie> movieList;

    public MyMovieRecyclerViewAdapter(Context mContext, List<Movie> items) {
        this.mContext = mContext;
        this.movieList = items;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_movie, parent, false);
        return new MyMovieRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Movie movie = movieList.get(position);

        holder.title.setText(movie.getTitle());

        ArrayList<String> arrlistofOptions = new ArrayList<String>(movie.getGenre());

        holder.itemView.setOnClickListener((view -> {
            Intent intent = new Intent(mContext, MovieDetailsActivity.class);
            //intent.putExtra("movie",  Parcels.wrap(movie));

            intent.putExtra("title", movie.getTitle());
            intent.putExtra("image", movie.getImage());
            intent.putExtra("release", movie.getReleaseYear());
            intent.putExtra("rating", movie.getRating());
            intent.putStringArrayListExtra("genre", arrlistofOptions);
            System.out.println(movie);
            mContext.startActivity(intent);
        }));
        String poster = movie.getImage().toString();

        if (!poster.equals("https://www.imdb.com/title/tt0068646/mediaviewer/rm746868224")){
        Glide.with(mContext)
                .load(poster)
                .placeholder(R.drawable.my_logo)
                .into(holder.movieImage);

        }else {
            Glide.with(mContext)
                    .load(R.drawable.god_father)
                    .placeholder(R.drawable.my_logo)
                    .into(holder.movieImage);
        }

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView movieImage;



        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            movieImage = (ImageView) view.findViewById(R.id.movie_image);

            view.setOnClickListener((v) -> {
                //ToDO: viewPager for details
            } );


        }


    }
}