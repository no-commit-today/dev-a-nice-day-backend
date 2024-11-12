package com.devniceday.reader;

import java.util.List;

public interface PagingItemReadStrategy<T> {
    List<T> readPage(int page, int size);
}
