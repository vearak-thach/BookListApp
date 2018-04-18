package com.example.android.booklistapp;

/**
 * Created by vearak.thach on 4/2/2018.
 */

public class Book {
    private String title;
    private String authors;
    private String image;

    /**
     * Constructs a new {@link Book} object
     * @param title
     * @param authors
     * @param image
     */

    public Book(String title, String authors, String image){
        this.title = title;
        this.authors = authors;
        this.image = image;
    }

    public String getTitle(){return title;}

    public String getAuthors(){return authors;}

    public String getImage(){return image; }
}
