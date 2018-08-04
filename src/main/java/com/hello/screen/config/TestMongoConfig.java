package com.hello.screen.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

public class TestMongoConfig {


    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://localhost:27017/tests");
    }
}
