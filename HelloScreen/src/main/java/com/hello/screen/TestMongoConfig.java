package com.hello.screen;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

public class TestMongoConfig {



	  public MongoClient mongoClient() {
	    return MongoClients.create("mongodb://localhost:27017/tests");
	  }
}
