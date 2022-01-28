package me.lenglet;

import java.util.HashSet;
import java.util.Set;

public class Book {

    private String isbn;
    private String author;
    private String title;

    private Set<String> keywords;

    public void computeKeywords() {
        this.keywords = new HashSet<>();
        this.keywords.addAll(computeAllSubstring(this.author));
        this.keywords.addAll(computeAllSubstring(this.title));
    }

    private Set<String> computeAllSubstring(String string) {
        final var result = new HashSet<String>();
        for (int i = 0; i < string.length(); i++) {
            for (int j = 1; j <= string.length() - i; j++) {
                result.add(string.substring(i, j + i));
            }
        }
        return result;
    }
}
