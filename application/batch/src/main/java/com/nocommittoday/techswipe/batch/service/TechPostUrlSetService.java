package com.nocommittoday.techswipe.batch.service;

import com.nocommittoday.techswipe.domain.rds.content.repository.TechPostRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TechPostUrlSetService {

    private final TechPostRepository techPostRepository;

    private final Set<String> urlSet = Collections.synchronizedSet(new HashSet<>());

    @PostConstruct
    public void init() {
        urlSet.addAll(techPostRepository.findAllUrl());
    }

    public boolean add(final String url) {
        return urlSet.add(url);
    }

    public boolean contains(final String url) {
        return urlSet.contains(url);
    }

}
