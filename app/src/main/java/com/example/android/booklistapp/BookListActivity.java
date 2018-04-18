package com.example.android.booklistapp;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static String GOOGLE_BOOK_URL = " https://www.googleapis.com/books/v1/volumes?q=android&maxResults=15";

    public static final String LOG_TAG = BookListActivity.class.getName();

    private TextView mEmptyStateTextView;

    private TextView mNoInternetConnection;

    private BookListAdapter mBookListAdapter;

    private String mQuerySearch = "";

    /**
     * Constant value for the book loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int BOOK_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        //Find a reference to the {@link ListView} in the layout
        final ListView bookListView = (ListView) findViewById(R.id.list_item);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(mEmptyStateTextView);

        mNoInternetConnection = (TextView) findViewById(R.id.no_internet_connection);
        bookListView.setEmptyView(mNoInternetConnection);

        //Create a new adapter that take the list of books as input
        mBookListAdapter = new BookListAdapter(this,new ArrayList<Book>());


        //set the adapter on the {@link ListView} so the list can be populated in the user interface
        bookListView.setAdapter(mBookListAdapter);

        final EditText searchEditText = (EditText) findViewById(R.id.search_edit_text);

        final Button searchButton = (Button) findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQuerySearch = searchEditText.getText().toString().replaceAll(" ", "+");
                if(mQuerySearch.isEmpty()){
                    Toast.makeText(BookListActivity.this, "Can't find book.", Toast.LENGTH_SHORT).show();
                }

                GOOGLE_BOOK_URL = GOOGLE_BOOK_URL + mQuerySearch;
                // Restart the loader.
                getLoaderManager().restartLoader(1, null, BookListActivity.this);
                GOOGLE_BOOK_URL = "https://www.googleapis.com/books/v1/volumes?q=";
                mBookListAdapter = new BookListAdapter(BookListActivity.this, new ArrayList<Book>());
                bookListView.setAdapter(mBookListAdapter);

            }
        });

        //Get a reference to the ConnectivityManager to check the state of network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        //Get details on the currently active default data network
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        //If there is a network fetch data
        if(networkInfo != null && networkInfo.isConnected()){
            //Get a reference to the LoaderManager, in order to interact with loaders
            LoaderManager loaderManager = getLoaderManager();

            //Initialize the loader. Pass in the int ID constant defined above and pass in null for
            //the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            //because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        }else{
            //Otherwise, display error
            //First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.progress_bar);
            loadingIndicator.setVisibility(View.GONE);

            //Update empty state with no connection error message
            mEmptyStateTextView.setText("No Internet Connection");
        }

        //Set an item click listener on the ListView, which sends an intent to a web browser
        //to open a website with more information about the selected book.
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle){
        return new BookLoader(this, GOOGLE_BOOK_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data){
        //Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.progress_bar);
        loadingIndicator.setVisibility(View.GONE);

        //Set empty state text to display "No books found."
        mEmptyStateTextView.setText("No books found");

        //clear the adapter of previous data
        mBookListAdapter.clear();

        //If there is a valid list of {@link Book}, then add them to the adapter's
        //data set. This will trigger the ListView to update.
        if(data != null && !data.isEmpty()){
            mBookListAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader){
        //Loader reset, so we can clear out our existing data.
        mBookListAdapter.clear();
    }
}
