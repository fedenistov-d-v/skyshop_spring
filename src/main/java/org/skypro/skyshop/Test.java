package org.skypro.skyshop;

import org.skypro.skyshop.controller.ShopController;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;
import org.skypro.skyshop.service.BasketService;
import org.skypro.skyshop.service.SearchService;
import org.skypro.skyshop.service.StorageService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        System.out.println("Test.main");
        String pattern = "блок";
        StorageService storageService = new StorageService();
        SearchService searchService = new SearchService(storageService);
        ProductBasket productBasket = new ProductBasket();
        BasketService basketService = new BasketService(productBasket, storageService);
        ShopController shopController = new ShopController(storageService, searchService, basketService);
        List<Product> storageList = storageService.getAllProducts().stream().toList();
        System.out.println(shopController.getAllProducts());
        System.out.println(storageList);
        System.out.print(storageList.get(0) + " - ");
        System.out.println(storageList.get(0).getId().toString());
        shopController.addProduct(storageList.get(0).getId());
        shopController.addProduct(storageList.get(0).getId());
        shopController.addProduct(storageList.get(1).getId());
        System.out.println(shopController.getUserBasket());
        System.out.println(shopController.getAllArticles());
        System.out.println(shopController.getAllSearch(pattern));
        System.out.println();
    }
}
