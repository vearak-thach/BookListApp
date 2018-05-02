package com.example.android.booklistapp;

/**
 * Created by vearak.thach on 4/2/2018.
 */

public class Book {
    private String title;
    private String author;
    private String image;

    /**
     * Constructs a new {@link Book} object
     * @param title
     * @param author
     * @param image
     */

    public Book(String title, String author, String image){
        this.title = title;
        this.author = author;
        this.image = image;
    }

    public String getTitle(){return title;}

    public String getAuthor(){return author;}

    public String getImage(){return image; }
}
