package com.hello.screen.repository;

import com.hello.screen.model.News;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface NewsRepository extends ReactiveCrudRepository<News, String> {
}
