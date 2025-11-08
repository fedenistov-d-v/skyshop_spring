package org.skypro.skyshop.service.util;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;

import java.util.*;

public class Fixture {
    private static List<UUID> idProduct = new ArrayList<>();
    static Map<UUID, Product> products = new HashMap<>();
    static Map<UUID, Article> articles = new HashMap<>();
    static Map<UUID, Integer> basket = new HashMap<>();

    static {
        fixtureProduct();
        fixtureArticle();
        fixtureBacket();
    }

    private static void fixtureBacket() {
        basket.put(getIdProductByIndex(0), 5);
//        basket.put(idProduct.get(1), 7);
//        basket.put(idProduct.get(2), 1);
    }

    private static void fixtureArticle() {
        Article article = new Article("Стандарт",
                "Сливы стандартные, вкусные, сладкие, среднего размера.", UUID.randomUUID());
        articles.put(article.getId(), article);
        article = new Article("Большие",
                "Сливы очень сладкие, огромного размера", UUID.randomUUID());
        articles.put(article.getId(), article);
        article = new Article("Сливы маленькие", "Разные, не очень свежие.", UUID.randomUUID());
        articles.put(article.getId(), article);
    }

    private static void fixtureProduct() {
        idProduct.add(UUID.randomUUID());
        Product product = new SimpleProduct("Сливы стандарт", 249, idProduct.get(0));
        products.put(product.getId(), product);
        idProduct.add(UUID.randomUUID());
        product = new DiscountedProduct("Сливы большие", 349, 15, idProduct.get(1));
        products.put(product.getId(), product);
        idProduct.add(UUID.randomUUID());
        product = new FixPriceProduct("Сливы маленькие", idProduct.get(2));
        products.put(product.getId(), product);
    }

    public static UUID getIdProductByIndex(int index) {
        return idProduct.get(index);
    }

    public static int getNumberProductInBasket(UUID key) {
        return basket.get(key);
    }

    public static ArrayList<Searchable> getSearchableAll() {
        ArrayList<Searchable> searchables = new ArrayList<>();
        searchables.addAll(getProducts());
        searchables.addAll(getArticles());
        return searchables;
    }

    public static ArrayList<Searchable> getSearchableProducts() {
        return new ArrayList<>(getProducts());
    }

    public static ArrayList<Searchable> getSearchableArticles() {
        return new ArrayList<>(getArticles());
    }

    private static Collection<Product> getProducts() {
        return products.values();
    }

    private static Collection<Article> getArticles() {
        return articles.values();
    }

    public static Map<UUID, Integer> getProductsOfBasket() {
        return Collections.unmodifiableMap(basket);
    }

    public static Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }


}
