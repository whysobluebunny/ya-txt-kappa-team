package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.views;

import java.io.File;

public class BookView {
    final String Title;

    final String Author;

    final String Genre;

    final String Description;

    final String Cover;

    final String Name;

    final String Id;

    public Number[] Ratings;

    public BookView(String title, String author, String genre, String description, String cover, String name, String id) {
        Title = title;
        Author = author;
        Genre = genre;
        Description = description;
        Cover = cover;
        Id = id;
        Name = name;
    }
}
