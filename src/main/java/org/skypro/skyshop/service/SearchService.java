package org.skypro.skyshop.service;

import org.skypro.skyshop.model.search.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    private final StorageService storageService;

//    @Autowired
    public SearchService(StorageService storageService) {
        this.storageService = storageService;
    }

    public List<SearchResult> search(String term) {
        return storageService.getAllSearchable().stream()
                .filter(i -> i.getSearchTerm().toLowerCase().contains(term.toLowerCase()))
                .map(SearchResult::fromSearchable)
                .toList();
    }
}
