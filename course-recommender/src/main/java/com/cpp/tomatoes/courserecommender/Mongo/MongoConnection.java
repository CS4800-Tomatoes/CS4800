package com.cpp.tomatoes.courserecommender.Mongo;


import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;

public class MongoConnection {
    private MongoClient _client;

    public MongoConnection()
    {
        if(_client == null)
        {
            String url = "mongodb+srv://msarmiento1621:XK3624rsFYymvhY2@cluster0.qnobfqx.mongodb.net/?retryWrites=true&w=majority";
            ConnectionString connectionString = new ConnectionString(url);
            MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .serverApi(ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build())
            .build();
            
            _client = MongoClients.create(settings);
        }
    }

    public MongoDatabase getDatabase(String db)
    {
        return _client.getDatabase(db);
    }

    public MongoCollection<Document> getCollection(String db, String collection)
    {
        return _client.getDatabase(db).getCollection(collection);
    }

    public void close()
    {
        if(_client != null)
        {
            _client.close();
            _client = null;
        }
    }
}
