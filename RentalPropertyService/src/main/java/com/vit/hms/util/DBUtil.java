package com.vit.hms.util;
import com.mongodb.client.*;
import com.mongodb.MongoException;
public class DBUtil {
    public static MongoClient getDBConnection() throws MongoException {
        MongoClient mongoClient = MongoClients.create();
        return mongoClient;
    }
}
