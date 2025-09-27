package org.skypro.skyshop.model.basket;

import java.util.List;

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
}
