package org.skypro.skyshop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.exceptions.NoSuchProductException;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.service.util.Fixture;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {

    @Mock
    private ProductBasket productBasket;
    @Mock
    private StorageService storageService;
    @InjectMocks
    private BasketService basketService;

    @Test
    public void givenNonExistentProduct_whenAddInBasket_thenException() {

        Exception thrownException = null;

        try {
            basketService.addInBasket(UUID.randomUUID());
        } catch (Exception e) {
            thrownException = e;
        }

        assertThat(thrownException)
                .isNotNull()
                .isExactlyInstanceOf(NoSuchProductException.class)
                .hasNoCause()
                .hasMessageContaining("Такого продукта нет");
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
                .thenReturn(Fixture.getProductsOfBasket());
        when(storageService.getProductById(any()))
                .thenReturn(Fixture.getProductById(Fixture.getIdProductByIndex(0)));

        UserBasket userBasket = basketService.getUserBasket();

        int sum = Fixture.getProductById(Fixture.getIdProductByIndex(0)).get().getPrice()
                * Fixture.getNumberProductInBasket(Fixture.getIdProductByIndex(0));
        Assertions.assertNotNull(userBasket);
        Assertions.assertEquals(sum, userBasket.getTotal());
        Assertions.assertEquals("Сливы стандарт", userBasket.getContents().get(0).getProduct().getName());
    }
}
