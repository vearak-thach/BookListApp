package com.example.android.booklistapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by vearak.thach on 4/8/2018
 * Loads list of books by using an AsyncTask to perform the network request to the given URL
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {
    /**Tag for log messages*/
    private static final String LOG_TAG = BookListAdapter.class.getName();

    /**Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link BookLoader}
     *
     * @param context of the activity
     * @param url to load data from
     */
    public BookLoader(Context context, String url){
        super(context);
        mUrl = url;
    }


    /**
     * Handles a request to start the Loader.
     */
    @Override
    protected void onStartLoading(){forceLoad();}

    /**
     * Load in the background thread.
     */
    @Override
    public List<Book> loadInBackground(){
        if(mUrl == null){
            return null;
        }

        //Perform the network request, parse the response, and extract a list of books.
        List<Book> books = QueryUtils.fetchBooklistingData(mUrl);
        return books;
    }

}
