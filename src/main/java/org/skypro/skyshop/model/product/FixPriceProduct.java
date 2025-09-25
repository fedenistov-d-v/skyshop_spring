package org.skypro.skyshop.model.product;

import java.util.UUID;

public class FixPriceProduct extends Product {

    private static final int FIX_PRICE = 100;

    public FixPriceProduct(String name, UUID id) {
        super(name, id);
    }

    @Override
    public int getPrice() {
        return FIX_PRICE;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s: Фиксированая цена %d",
                name, FIX_PRICE);
    }

}
