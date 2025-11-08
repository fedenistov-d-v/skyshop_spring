package org.skypro.skyshop.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StorageService {
    private final Map<UUID, Product> products;
    private final Map<UUID, Article> articles;

    public StorageService() {
        this.products = new HashMap<>();
        mapOfProduct();
        this.articles = new HashMap<>();
        mapOfArticles();
    }

    public Collection<Product> getAllProducts() {
        return products.values();
    }

    public Collection<Article> getAllArticles() {
        return articles.values();
    }

    public ArrayList<Searchable> getAllSearchable() {
        ArrayList<Searchable> allSearchable = new ArrayList<>();
        allSearchable.addAll(getAllProducts());
        allSearchable.addAll(getAllArticles());
        return allSearchable;
    }

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    private void mapOfProduct() {
        Product apples = new SimpleProduct("Яблок_9", 149, UUID.randomUUID());
        products.put(apples.getId(), apples);
        Product oranges = new DiscountedProduct("Апельсины", 300, 15, UUID.randomUUID());
        products.put(oranges.getId(), oranges);
        Product peaches = new DiscountedProduct("Персики", 500, 22, UUID.randomUUID());
        products.put(peaches.getId(), peaches);
        Product bananas = new FixPriceProduct("Бананы", UUID.randomUUID());
        products.put(bananas.getId(), bananas);
        Product pears = new SimpleProduct("Груши", 249, UUID.randomUUID());
        products.put(pears.getId(), pears);
        Product watermelons = new SimpleProduct("Арбузы", 25, UUID.randomUUID());
        products.put(watermelons.getId(), watermelons);
        Product applesDiscounted = new DiscountedProduct("Яблоки со скидкой", 149, 35, UUID.randomUUID());
        products.put(applesDiscounted.getId(), applesDiscounted);
        Product melons = new SimpleProduct("Дыни", 25, UUID.randomUUID());
        products.put(melons.getId(), melons);
        Product plums = new SimpleProduct("Сливы", 300, UUID.randomUUID());
        products.put(plums.getId(), plums);
        Product strawberry = new DiscountedProduct("Клубника", 800, 50, UUID.randomUUID());
        products.put(strawberry.getId(), strawberry);
    }

    private void mapOfArticles() {
        Article articleApples = new Article("Яблок_1",
                "Из яблок можно приготовить самое большое количество блюд, " +
                        "чем из других фруктов.", UUID.randomUUID());
        articles.put(articleApples.getId(), articleApples);
        Article articleOrange = new Article("Апельсин",
                "Свежевыжатый апельсиновый сок самый востребованный сок в мире", UUID.randomUUID());
        articles.put(articleOrange.getId(), articleOrange);
        Article articleCherry = new Article("Вишня",
                "Лучшие соки из нашей вишни. И самые вкусные пироги", UUID.randomUUID());
        articles.put(articleCherry.getId(), articleCherry);
        Article articleLemon = new Article("Лимон",
                "Для лимонада в жаркий день", UUID.randomUUID());
        articles.put(articleLemon.getId(), articleLemon);
    }
}
