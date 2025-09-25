package org.skypro.skyshop.model.search;

import java.util.UUID;

public interface Searchable {

    String getSearchTerm();

    String getTypeContent();

    String getNameObject();

    UUID getId();

    default String getStringRepresentation() {
        return String.format("%s - %s",
                getNameObject(),
                getTypeContent());
    }
}
