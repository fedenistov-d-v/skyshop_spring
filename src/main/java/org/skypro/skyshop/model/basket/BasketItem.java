package org.skypro.skyshop.model.basket;

import org.skypro.skyshop.model.product.Product;

public class BasketItem {
    private final Product product;
    private final Integer number;

    public BasketItem(Product product, Integer number) {
        this.product = product;
        this.number = number;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "BasketItem " +
                "product=" + product +
                ", number=" + number +
                ' ';
    }
}
