package com.hello.screen.datacollectors;


import io.vavr.collection.List;

public interface DataCollector<T> {

    void collectAndSave();

    default List<T> collect() {
        return List.empty();
    }

}
