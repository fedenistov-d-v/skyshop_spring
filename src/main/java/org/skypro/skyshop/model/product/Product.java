package org.skypro.skyshop.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

public abstract class Product implements Searchable {
    protected final String name;
    private final UUID id;

    public Product(String name, UUID id) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Продукт не создан. Некоректное имя продукта.");
        }
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    @Override
    public String getSearchTerm() {
        return name;
    }

    @JsonIgnore
    @Override
    public String getTypeContent() {
        return "PRODUCT";
    }

    @JsonIgnore
    @Override
    public String getNameObject() {
        return name;
    }

    public abstract int getPrice();

    public abstract boolean isSpecial();

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("%s: ", name);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
