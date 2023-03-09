package com.cpp.tomatoes.courserecommender;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.google.gson.JsonArray;
import com.mongodb.client.MongoCollection;

public class MongoRepo {
    private MongoCollection<Document> _collection;

    public MongoRepo(String collection)
    {
        var connection = new MongoConnection();
        _collection = connection.getCollection("Class_Recommender", collection);
    }

    public String findCourseNum(int courseNum)
    {
        Bson eqComparision = eq("Course Number", courseNum);
        var document = _collection.find(eqComparision);
        JsonArray result = new JsonArray();
        document.forEach(doc -> {
            result.add(doc.toJson());
        });
        return result.toString();
    }

    public String findCourseNum(String[] tags)
    {
        //If recieve multiple tags return json that separates them both
        return "";
    }
    
}
