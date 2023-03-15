package com.cpp.tomatoes.courserecommender;

import static com.mongodb.client.model.Filters.elemMatch;
import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Arrays;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.boot.json.GsonJsonParser;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

public class MongoRepo {
    private static String DATABASE = "Class_Recommender";
    private MongoConnection _connection;

    public MongoRepo()
    {
        _connection = new MongoConnection();
    }

    public String findCourses(int courseNum)
    {
        MongoCollection<Document> classesCollection = _connection.getCollection(DATABASE, "classes");
        Bson eqComparision = eq("Course Number", courseNum);
        var document = classesCollection.find(eqComparision);
        JsonArray result = new JsonArray();
        document.forEach(doc -> {
            result.add(doc.toJson());
        });
        return result.toString();
    }

    public String findCourses(int[] tagIdList)
    {
        //If recieve multiple tags return json that separates them both
        MongoCollection<Document> classesCollection = _connection.getCollection(DATABASE, "classes");
        
        JsonArray result = new JsonArray();
        for(int tagId: tagIdList)
        {
            Bson filter = Filters.in("Tag", tagId);
            var documents = classesCollection.find(filter);
            documents.forEach(doc -> {
                result.add(doc.toJson());
            });
            //Currently we just take the first tagId in tagIdList and return its results
            break;
        }
        if(result.size() == 0)
            return "Nothing here";
        return result.toString();
        
    }
    
    public Tag[] getAllTags()
    {
        MongoCollection<Document> tagCollection = _connection.getCollection(DATABASE, "tags");
        ArrayList<Tag> tags = new ArrayList<Tag>();
        tagCollection.find().forEach(doc -> {
            Tag tag = new Gson().fromJson(doc.toJson(), Tag.class);
            tags.add(tag);
        });
        return tags.toArray(new Tag[tags.size()]);
    }
}
