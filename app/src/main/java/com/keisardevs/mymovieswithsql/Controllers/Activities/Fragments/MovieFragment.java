package com.keisardevs.mymovieswithsql.Controllers.Activities.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.zxing.Result;
import com.keisardevs.mymovieswithsql.Controllers.Activities.MoviesPageActivity;
import com.keisardevs.mymovieswithsql.Common;
import com.keisardevs.mymovieswithsql.View.MyMovieRecyclerViewAdapter;
import com.keisardevs.mymovieswithsql.R;
import com.keisardevs.mymovieswithsql.model.database.SQLite;
import com.keisardevs.mymovieswithsql.model.Movie;
import com.keisardevs.mymovieswithsql.model.retrofit.RetrofitClient;
import com.keisardevs.mymovieswithsql.model.service.MovieApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A fragment representing a list of Items.
 */
public class MovieFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private View moviesView;

    private MyMovieRecyclerViewAdapter mAdapter;

    private RecyclerView mRecyclerView;

    private List<Movie> movieList;

    private SwipeRefreshLayout swipeRefreshLayout;


    private SharedPreferences sharedPref ;
    //for SQLite


    private SwipeRefreshLayout swipeContainer;
    private SQLite myDatabase;


    private Button addButton;
    //QR Code
    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;



    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MovieFragment newInstance(int columnCount) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        moviesView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        mRecyclerView = moviesView.findViewById(R.id.movies_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        myDatabase = new SQLite(getContext());

        addButton = moviesView.findViewById(R.id.add_movie_btn);



        addButton.setOnClickListener((view -> {
            if (hasCameraPermission()){
                showDialog();
            }else{
                cameraPermission();

            }


        }));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            checkIfThereIsDatabase();
        }



        return moviesView;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void checkIfThereIsDatabase(){

        myDatabase = new SQLite(getContext());

        movieList = new ArrayList<>();
        getMoviesFromDatabase(myDatabase);
        if (movieList.isEmpty()){
            loadJSON(myDatabase);
            snackBarMessage("No Database, Download Started!", moviesView);

        }else {
            initRecycleView(movieList);
            snackBarMessage("getting movies from database", moviesView);

        }


    }


    public void loadJSON(SQLite myDatabase){


        MovieApi api = RetrofitClient.getClient().create(MovieApi.class);

        Call<List<Movie>> call = api.getMovies();

        call.enqueue(new Callback<List<Movie>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                List<Movie> movies = response.body();

                initRecycleView(movies);

                    for (Movie m : movies){
                        myDatabase.addMovie(m);
                    }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void checkSortOrder(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = preferences.getString(
                "sortOrder",
                "releaseYear");
        if (sortOrder.equals(("releaseYear"))){
            checkIfThereIsDatabase();
        }

    }


    public void  getMoviesFromDatabase(SQLite database){

        Cursor cursor = database.readAllMovies();


        if (cursor.getCount() == 0) {//if there is no data

            Toast.makeText(getContext(), "Database is empty, began downloading procces", Toast.LENGTH_LONG).show();
        }else {
            System.out.println(cursor.getCount());
            while (cursor.moveToNext()){
                Movie movie = new Movie();
                movie.setTitle(cursor.getString(1));
                movie.setImage(cursor.getString(2));
                movie.setRating(cursor.getDouble(3));
                movie.setReleaseYear(cursor.getInt(4));
                movie.setGenre(Common.convertStringToList(cursor.getString(5)) );
                movieList.add(movie);
            }

        }

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean containsName(final List<Movie> list, final String name){
        return list.stream().map(Movie::getTitle).anyMatch(name::equals);
    }

    public void cameraPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(requireActivity(),Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.CAMERA}, 1);
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
                switch (requestCode) {
                    case 1:
                        showDialog();
                            }
    }
    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void showDialog(){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View mView = getLayoutInflater().inflate(R.layout.add_movie_dialog,null);
        mBuilder.setTitle("Add Movie");


        scannerView = mView.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(requireActivity(), scannerView);

        mCodeScanner.setDecodeCallback(new DecodeCallback() {

            @Override
            public void onDecoded(@NonNull final Result result) {
                requireActivity().runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void run() {
                        mCodeScanner.startPreview();

                        System.out.println(result.getText());

                        Intent refresh = new Intent(getContext(), MoviesPageActivity.class);
                        Timer obRunTime = new Timer();

                        new Movie();
                        Movie movie = deserialize(result.getText());

                        if (containsName(movieList, movie.getTitle())){
                            System.out.println("movie exist");
                            mCodeScanner.stopPreview();
                            snackBarMessage("Current movie already exist in the Database", scannerView);
                            //new Timer().schedule(startActivity(refresh), );
                            obRunTime.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    startActivity(refresh);
                                }
                            },2000);

                        }else {
                            myDatabase.addMovie(movie);
                            mCodeScanner.stopPreview();

                            System.out.println("movie don't exist");
                            snackBarMessage("Movie added successfully!", scannerView);
                            obRunTime.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    startActivity(refresh);
                                }
                            },2000);

                        }

                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
        if (scannerView.isAutoFocusButtonVisible()){
            mCodeScanner.startPreview();
        }


        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }


    public static Movie deserialize(String jsonString){
        Movie movie = new Gson().fromJson(jsonString, Movie.class);
        new Gson().fromJson(jsonString, Movie.class);
        return movie;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void initRecycleView(List<Movie> movies){
        Collections.sort(movies, Comparator.comparingInt(Movie::getReleaseYear).reversed());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        mAdapter = new MyMovieRecyclerViewAdapter(getContext(), movies);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }


    public void snackBarMessage(String message, View view ){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                .show();
    }




}