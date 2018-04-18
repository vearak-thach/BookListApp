package com.example.android.booklistapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by vearak.thach on 4/4/2018.
 */

public class BookListAdapter extends ArrayAdapter<Book> {

    public BookListAdapter(Activity context, ArrayList<Book> books){
        //Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        //the second argument is used when the ArrayAdapter is populating a single TextView.
        //Because this is a custom adapter for two TextViews, the adapter is not
        //going to use this second argument, so it an be any value. Here, we used 0.
        super(context, 0, books);
    }

    //Method to format the author display

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.book_per_list_item, parent, false);
        }

        //Get the {@link Book} object located at this position in the list
        Book currentBook = getItem(position);

        //Find the TextView in the book_per_list_item.xml layout with the ID title
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title);
        //Get the title from the current Book object and set this text on the title TextView
        titleTextView.setText(currentBook.getTitle());

        //Find the TextView in the book_per_list_item.xml layout with the ID author
        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author);
        authorTextView.setText(currentBook.getTitle());

        //Find the TextView in the book_per_list_item.xml layout with the ID image
        //Add picasso for the image parsing
        ImageView bookImage = (ImageView) listItemView.findViewById(R.id.bookImage);
        Picasso.with(getContext()).load(currentBook.getImage()).into(bookImage);

        return listItemView;
    }
}
