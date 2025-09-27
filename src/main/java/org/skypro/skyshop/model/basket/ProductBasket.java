package org.skypro.skyshop.model.basket;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.*;

@Component
@SessionScope
public class ProductBasket {
    private final Map<UUID, Integer> basket;

    public ProductBasket() {
        this.basket = new HashMap<>();
    }

    public void add(UUID uuid) {
        if (basket.containsKey(uuid)) {
            basket.put(uuid, basket.get(uuid) + 1);
        } else {
            basket.put(uuid, 1);
        }
    }

    public Map<UUID, Integer> getProductsOfBasket() {
        return Collections.unmodifiableMap(basket);
    }
}
