package com.nocommittoday.techswipe.collection.application.port.out;

import com.nocommittoday.techswipe.collection.domain.enums.CollectionCategory;

import java.util.List;

public interface CategorizePort {
    List<CollectionCategory> categorize(String prompt, String content);
}
