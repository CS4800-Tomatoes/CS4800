package com.cpp.tomatoes.courserecommender;

import javax.swing.text.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoRepo {
    private MongoClient _client;
    private MongoDatabase _database;

    public MongoRepo()
    {
        if(_client == null)
        {
            String url = "mongodb+srv://msarmiento1621:tXGN4XFKuOcyse19@cluster0.qnobfqx.mongodb.net/?retryWrites=true&w=majority";
            ConnectionString connectionString = new ConnectionString(url);
            MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .serverApi(ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build())
            .build();
            
            _client = MongoClients.create(settings);
            _database = _client.getDatabase("Class_Recommender");
        }
    }

    public String test()
    {
        String temp = "";
        for (String name : _database.listCollectionNames()) {
            temp += name + " ";
        }
        return temp;
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
