package me.lenglet;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookTest {

    private final EncryptionService encryptionService = new EncryptionService() {
        @Override
        public String encrypt(String string) {
            return Base64.getEncoder().encodeToString(string.getBytes(StandardCharsets.UTF_8));
        }

        @Override
        public String decrypt(String string) {
            return new String(Base64.getDecoder().decode(string), StandardCharsets.UTF_8);
        }
    };

    private Book book;

    @BeforeEach
    public void beforeEach() {
        this.book = new Book(
                "345678909",
                this.encryptionService.encrypt("Jim Morrison"),
                this.encryptionService.encrypt("The Lost Writings of Jim Morrison - volume I - Wilderness")
        );

        Stream.of(book.getAuthor(), book.getTitle())
                .map(this.encryptionService::decrypt)
                .map(String::toLowerCase)
                .map(BookTest::computeAllSubstring)
                .flatMap(Collection::stream)
                .map(s -> s.getBytes(StandardCharsets.UTF_8))
                .map(s -> DigestUtils.getSha256Digest().digest(s))
                .forEach(hash -> book.addKeyword(new String(hash, StandardCharsets.UTF_8)));
    }

    @Test
    void testFullTextSearchByTitle() {
        assertTrue(match("writing"));
        assertFalse(match("something"));
    }

    private boolean match(String fullTextSearchQuery) {
        final String hash = new String(DigestUtils.getSha256Digest().digest(fullTextSearchQuery.toLowerCase().getBytes(StandardCharsets.UTF_8)));
        return book.getKeywords().contains(hash);
    }

    private static Set<String> computeAllSubstring(String string) {
        final var result = new HashSet<String>();
        for (int i = 0; i < string.length(); i++) {
            for (int j = 1; j <= string.length() - i; j++) {
                result.add(string.substring(i, j + i));
            }
        }
        return result;
    }
}