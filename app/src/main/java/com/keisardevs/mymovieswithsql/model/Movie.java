package com.keisardevs.mymovieswithsql.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Movie {
    @SerializedName("title")
    private String title;

    @SerializedName("image")
    private String image;

    @SerializedName("rating")
    private Double rating;

    @SerializedName("releaseYear")
    private int releaseYear;

    @SerializedName("genre")
    private List<String> genre = new ArrayList<String>();

//    public static final Comparator<Movie> BY_NAME_ALPHABETICAL = new Comparator<Movie>() {
//        @Override
//        public int compare(Movie movie, Movie t1) {
//
//            return movie.title.compareTo(t1.title);
//        }
//    };

    public Movie() {
    }

    public Movie(String title, String image, Double rating, int releaseYear, List<String> genre) {
        this.title = title;
        this.image = image;
        this.rating = rating;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }

    protected Movie(Parcel in) {
        this.title = in.readString();
        this.image = in.readString();
        this.rating = in.readDouble();
        this.releaseYear = in.readInt();
        //in.readList(this.genre, String.class.getClassLoader());
        this.genre = in.createStringArrayList();
    }

//    @Override
//    public void writeToParcel(Parcel dest, int i) {
//        dest.writeString(this.title);
//        dest.writeString(this.image);
//        dest.writeValue(this.rating);
//        dest.writeValue((this.releaseYear));
//        dest.writeList(this.genre);
//    }

//    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
//        @Override
//        public Movie createFromParcel(Parcel in) {
//            return new Movie(in);
//        }
//
//        @Override
//        public Movie[] newArray(int size) {
//            return new Movie[size];
//        }
//    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", rating='" + rating + '\'' +
                ", releaseYear='" + releaseYear + '\'' +
                ", genre=" + genre +
                '}';
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }



    public static final class MovieEntry implements BaseColumns {

        public static Gson gson = new Gson();

        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_RATING = "rating";
        public static final String RELEASE_YEAR = "releaseYear";

        @SerializedName("genre")
        //public static final List<String> COLUMN_GENERES = new ArrayList<String>();
        public static final String COLUMN_GENRES = "genre";
    }
}


