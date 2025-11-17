package org.skypro.skyshop.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.exceptions.NoSuchProductException;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {

    private final List<UUID> idProduct = new ArrayList<>();
    private final Map<UUID, Product> products = new HashMap<>();
    private final Map<UUID, Integer> basket = new HashMap<>();

    @Mock
    private ProductBasket productBasket;
    @Mock
    private StorageService storageService;
    @InjectMocks
    private BasketService basketService;

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

        basket.put(idProduct.get(0), 5);
//        basket.put(idProduct.get(2), 7);
//        basket.put(idProduct.get(1), 1);
    }

    @Test
    public void givenNonExistentProduct_whenAddInBasket_thenException() {
        NoSuchProductException thrown = assertThrows(NoSuchProductException.class
                , () -> basketService.addInBasket(UUID.randomUUID()));
        assertTrue(thrown.getMessage().contains("Такого продукта нет"));
    }

    @Test
    public void givenExistentProduct_whenAddInBasket_thenCallAdd() {
        UUID id = UUID.randomUUID();
        Product product = new SimpleProduct("Яблок_9", 149, id);
        when(storageService.getProductById(id)).thenReturn(Optional.of(product));

        basketService.addInBasket(id);

        verify(productBasket, Mockito.only()).add(any(UUID.class));
    }

    @Test
    public void givenEmptyBasket_whenGetUserBasket_thenBasketIsEmpty() {
        UserBasket userBasket = basketService.getUserBasket();

        Assertions.assertEquals(0, userBasket.getTotal());
        Assertions.assertTrue(userBasket.getContents().isEmpty());
    }

    @Test
    public void givenNotEmptyBasket_whenGetUserBasket_thenBasketIsEmpty() {
        when(productBasket.getProductsOfBasket())
                .thenReturn(Collections.unmodifiableMap(basket));
        when(storageService.getProductById(idProduct.get(0)))
                .thenReturn(Optional.ofNullable(products.get(idProduct.get(0))));

        UserBasket userBasket = basketService.getUserBasket();

        int sum = products.get(idProduct.get(0)).getPrice() * basket.get(idProduct.get(0));
        Assertions.assertNotNull(userBasket);
        Assertions.assertEquals(sum, userBasket.getTotal());
        Assertions.assertEquals("Сливы стандарт", userBasket.getContents().get(0).getProduct().getName());
    }
}
