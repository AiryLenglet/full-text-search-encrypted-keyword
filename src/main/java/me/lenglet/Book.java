package me.lenglet;

import java.util.HashSet;
import java.util.Set;

public class Book {

    private String isbn;
    private String author;
    private String title;

    private Set<String> keywords;

    public Book(
            String isbn,
            String author,
            String title
    ) {
        this.isbn = isbn;
        this.author = author;
        this.title = title;
        this.keywords = new HashSet<>();
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public void addKeyword(String keyword) {
        this.keywords.add(keyword);
    }

}
