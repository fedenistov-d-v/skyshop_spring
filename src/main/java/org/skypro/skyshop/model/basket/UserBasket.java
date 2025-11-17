package org.skypro.skyshop.model.basket;

import java.util.List;
import java.util.Objects;

public class UserBasket {
    private final List<BasketItem> contents;
    private final Integer total;

    public UserBasket(List<BasketItem> contents) {
        this.contents = contents;
        this.total = this.contents.stream()
                .mapToInt(i -> i.getProduct().getPrice() * i.getNumber())
                .sum();
    }

    public List<BasketItem> getContents() {
        return contents;
    }

    public Integer getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "UserBasket " +
                "contents=" + contents +
                ", total=" + total +
                ' ';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserBasket that = (UserBasket) o;
        return Objects.equals(contents, that.contents) && Objects.equals(total, that.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contents, total);
    }
}
