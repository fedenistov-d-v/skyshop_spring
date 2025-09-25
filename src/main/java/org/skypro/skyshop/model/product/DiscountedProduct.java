package org.skypro.skyshop.model.product;

import java.util.UUID;

public class DiscountedProduct extends Product {

    protected final int price;
    protected final int discountInPercent;

    public DiscountedProduct(String name, int price, int discountInPercent, UUID id) {
        super(name, id);
        if (price <= 0) {
            throw new IllegalArgumentException("Продукт не создан. Некоректная стоимость продукта.");
        }
        if (discountInPercent < 0 || discountInPercent > 100) {
            throw new IllegalArgumentException("Продукт не создан. Некоректно указан процент скидки.");
        }
        this.price = price;
        this.discountInPercent = discountInPercent;
    }

    @Override
    public int getPrice() {
        return (int) (price * (1.0 - discountInPercent / 100.0));
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s: %d (%d%%)",
                name, getPrice(), discountInPercent);
    }
}
