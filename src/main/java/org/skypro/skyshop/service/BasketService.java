package org.skypro.skyshop.service;

import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BasketService {
    private final ProductBasket productBasket;
    private final StorageService storageService;

    public BasketService(ProductBasket productBasket, StorageService storageService) {
        this.productBasket = productBasket;
        this.storageService = storageService;
    }

    public void addInBasket(UUID uuid) throws IllegalArgumentException {
        if (storageService.getProductById(uuid).isEmpty())
            throw new IllegalArgumentException();
        productBasket.add(uuid);
    }

    public UserBasket getUserBasket() {
        List<BasketItem> contents = productBasket.getProductsOfBasket().entrySet().stream()
                .map(i -> new BasketItem(storageService.getProductById(i.getKey()).get(), i.getValue()))
                .toList();
        return new UserBasket(contents);
    }
}
