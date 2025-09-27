package org.skypro.skyshop.model.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

public final class Article implements Searchable {
    private final String headline;
    private final String text;
    private final UUID id;

    public Article(String headline, String text, UUID id) {
        this.headline = headline;
        this.text = text;
        this.id = id;
    }

    public String getHeadline() {
        return headline;
    }

    public String getText() {
        return text;
    }

    @JsonIgnore
    @Override
    public String getSearchTerm() {
        return toString();
    }

    @JsonIgnore
    @Override
    public String getTypeContent() {
        return "ARTICLE";
    }

    @JsonIgnore
    @Override
    public String getNameObject() {
        return headline;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("%s - %s",
                headline, text);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(headline, article.headline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headline);
    }
}
