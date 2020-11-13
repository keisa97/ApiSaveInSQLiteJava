package com.keisardevs.mymovieswithsql.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.keisardevs.mymovieswithsql.Common;
import com.keisardevs.mymovieswithsql.model.Movie;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import static com.keisardevs.mymovieswithsql.model.Movie.*;

public class SQLite  extends SQLiteOpenHelper {


    private static SQLite mInstance = null;


    private Context context;


        private static final String DATABASE_NAME = "database.db";
        private static final String TABLE_NAME = "my_database";


        private static final int DATABASE_VERSION = 1;

        public static final String LOGTAG = "DATABASE";
        private static final String COLUMN_ID = "id";


        SQLiteOpenHelper dbhandler;
        SQLiteDatabase db;

    public static SQLite getInstance(Context context) {
        /**
         * use the application context as suggested by CommonsWare.
         * this will ensure that you dont accidentally leak an Activitys
         * context (see this article for more information:
         * http://android-developers.blogspot.nl/2009/01/avoiding-memory-leaks.html)
         */
        if (mInstance == null) {
            mInstance = new SQLite(context.getApplicationContext());
        }

        return mInstance;
    }

        public SQLite(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }



        public void open(){
            Log.i(LOGTAG, "Database Opened");
            db = dbhandler.getWritableDatabase();
        }

        public void close(){
            Log.i(LOGTAG, "Database Closed");
            dbhandler.close();
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            String query = "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                    MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                    MovieEntry.COLUMN_IMAGE + " TEXT NOT NULL, " +
                    MovieEntry.COLUMN_RATING + " TEXT NOT NULL, " +
                    MovieEntry.RELEASE_YEAR + " INTEGER, " +
                    Common.convertStringToList(MovieEntry.COLUMN_GENRES) +  " TEXT NOT NULL " +
                    "); ";

            sqLiteDatabase.execSQL(query);

        }

        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
            onCreate(sqLiteDatabase);
        }


        @RequiresApi(api = Build.VERSION_CODES.O)
        public void addMovie(Movie movie){
            System.out.println("!!!!!!!!!!adding movies!!!!!!!!!!adding movies");

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cValues = new ContentValues();
            System.out.println(movie);
            cValues.put(MovieEntry.COLUMN_TITLE, movie.getTitle());
            cValues.put(MovieEntry.COLUMN_IMAGE, movie.getImage());
            cValues.put(MovieEntry.COLUMN_RATING, movie.getRating());
            cValues.put(MovieEntry.RELEASE_YEAR, movie.getReleaseYear());
//            System.out.println("The Crash"+(Common.convertListToString(MovieEntry.COLUMN_GENERES) + Common.convertListToString(movie.getGenre())));
            //cValues.put(Common.convertListToString(MovieEntry.COLUMN_GENERES), Common.convertListToString(movie.getGenre()));
            cValues.put(String.join(", ",MovieEntry.COLUMN_GENRES), (String.join(", ", movie.getGenre())));
            //long result = db.insert(MovieEntry.TABLE_NAME,null,cValues);
            //db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);


            //long result = db.insertWithOnConflict(MovieEntry.TABLE_NAME,null,cValues,SQLiteDatabase.CONFLICT_REPLACE);
//            if(result == -1){
////                Toast.makeText(context, "Can't add movie", Toast.LENGTH_SHORT).show();
//                System.out.println("Failed!!!");
//            }else{
////                Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
//                System.out.println("Success!!!");
//            }

            db.insert(MovieEntry.TABLE_NAME, null, cValues);
            db.close();
        }


        public Cursor readAllMovies(){

            String query = "SELECT * FROM " + MovieEntry.TABLE_NAME;
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = null;

            // if we have data:
            if (db != null ){
                cursor = db.rawQuery(query,null);

            }


            return cursor;
        }


        @RequiresApi(api = Build.VERSION_CODES.O)
        public void deleteMovie( Movie movie){

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cValues = new ContentValues();
            System.out.println(movie);
            cValues.put(MovieEntry.COLUMN_TITLE, movie.getTitle());
            cValues.put(MovieEntry.COLUMN_IMAGE, movie.getImage());
            cValues.put(MovieEntry.COLUMN_RATING, movie.getRating());
            cValues.put(MovieEntry.RELEASE_YEAR, movie.getReleaseYear());

            cValues.put(String.join("",MovieEntry.COLUMN_GENRES), (String.join("", movie.getGenre())));
            long result = db.update(MovieEntry.TABLE_NAME,cValues,"title=?", new String[]{movie.getTitle()});
            if(result == -1){
//                Toast.makeText(context, "Can't add movie", Toast.LENGTH_SHORT).show();
                System.out.println("Failed!!!");
            }else{
//                Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
                System.out.println("Success!!!");
            }

            db.close();
        }


}


