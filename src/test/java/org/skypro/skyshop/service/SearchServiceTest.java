package org.skypro.skyshop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    private final List<UUID> idProduct = new ArrayList<>();
    private final Map<UUID, Product> products = new HashMap<>();
    private final Map<UUID, Article> articles = new HashMap<>();

    @Mock
    private StorageService storageService;
    @InjectMocks
    private SearchService searchService;

    @BeforeEach
    void storageServiceMock() {
        idProduct.add(UUID.randomUUID());
        Product product = new SimpleProduct("Сливы стандарт", 249, idProduct.get(0));
        products.put(product.getId(), product);
        idProduct.add(UUID.randomUUID());
        product = new DiscountedProduct("Сливы большие", 349, 15, idProduct.get(1));
        products.put(product.getId(), product);
        idProduct.add(UUID.randomUUID());
        product = new FixPriceProduct("Сливы маленькие", idProduct.get(2));
        products.put(product.getId(), product);

        Article article = new Article("Стандарт",
                "Сливы стандартные, вкусные, сладкие, среднего размера.", UUID.randomUUID());
        articles.put(article.getId(), article);
        article = new Article("Большие",
                "Сливы очень сладкие, огромного размера", UUID.randomUUID());
        articles.put(article.getId(), article);
        article = new Article("Сливы маленькие", "Разные, не очень свежие.", UUID.randomUUID());
        articles.put(article.getId(), article);
    }

    @Test
    public void givenNonSearchable_whenSearch_thenAbsent() {
        String term = "Сливы";

        List<SearchResult> result = searchService.search(term);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"яблоки", "груши", "слива", "сливу"})
    public void givenSearchable_whenSearch_thenNotFound(String term) {
        Mockito.when(storageService.getAllSearchable()).thenReturn(getSearchableAll());

        List<SearchResult> result = searchService.search(term);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());

    }

    @ParameterizedTest
    @ValueSource(strings = {"сливы", "сли", "лив", "ивы", "СЛИВЫ", "СЛИВ"})
    public void givenSearchable_whenSearch_thenFound(String term) {
        Mockito.when(storageService.getAllSearchable()).thenReturn(getSearchableAll());

        List<SearchResult> result = searchService.search(term);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(6, result.size());

    }

    @ParameterizedTest
    @ValueSource(strings = {"сливы", "сли", "лив", "ивы", "СЛИВЫ", "СЛИВ"})
    public void givenProduct_whenSearch_thenFound(String term) {
        Mockito.when(storageService.getAllSearchable()).thenReturn(new ArrayList<Searchable>(products.values()));

        List<SearchResult> result = searchService.search(term);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.size());

    }

    @ParameterizedTest
    @ValueSource(strings = {"сливы", "сли", "лив", "ивы", "СЛИВЫ", "СЛИВ"})
    public void givenArticle_whenSearch_thenFound(String term) {
        Mockito.when(storageService.getAllSearchable()).thenReturn(new ArrayList<Searchable>(articles.values()));

        List<SearchResult> result = searchService.search(term);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.size());

    }

    public ArrayList<Searchable> getSearchableAll() {
        ArrayList<Searchable> searchables = new ArrayList<>();
        searchables.addAll(products.values());
        searchables.addAll(articles.values());
        return searchables;
    }
}
