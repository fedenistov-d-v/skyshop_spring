package org.skypro.skyshop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.service.util.Fixture;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @Mock
    private StorageService storageService;
    @InjectMocks
    private SearchService searchService;

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
        Mockito.when(storageService.getAllSearchable()).thenReturn(Fixture.getSearchableAll());

        List<SearchResult> result = searchService.search(term);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());

    }

    @ParameterizedTest
    @ValueSource(strings = {"сливы", "сли", "лив", "ивы", "СЛИВЫ", "СЛИВ"})
    public void givenSearchable_whenSearch_thenFound(String term) {
        Mockito.when(storageService.getAllSearchable()).thenReturn(Fixture.getSearchableAll());

        List<SearchResult> result = searchService.search(term);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(6, result.size());

    }

    @ParameterizedTest
    @ValueSource(strings = {"сливы", "сли", "лив", "ивы", "СЛИВЫ", "СЛИВ"})
    public void givenProduct_whenSearch_thenFound(String term) {
        Mockito.when(storageService.getAllSearchable()).thenReturn(Fixture.getSearchableProducts());

        List<SearchResult> result = searchService.search(term);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.size());

    }

    @ParameterizedTest
    @ValueSource(strings = {"сливы", "сли", "лив", "ивы", "СЛИВЫ", "СЛИВ"})
    public void givenArticle_whenSearch_thenFound(String term) {
        Mockito.when(storageService.getAllSearchable()).thenReturn(Fixture.getSearchableArticles());

        List<SearchResult> result = searchService.search(term);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.size());

    }
}
