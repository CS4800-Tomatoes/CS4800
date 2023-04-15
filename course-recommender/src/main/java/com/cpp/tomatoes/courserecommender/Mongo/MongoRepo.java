package com.cpp.tomatoes.courserecommender.Mongo;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import com.cpp.tomatoes.courserecommender.Models.Class;
import com.cpp.tomatoes.courserecommender.Models.Tag;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

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

    public JsonArray findCoursesByTagId(int tagId)
    {
        //If recieve multiple tags return json that separates them both
        MongoCollection<Document> classesCollection = _connection.getCollection(DATABASE, "classes");
        
        JsonArray result = new JsonArray();
        Bson filter = Filters.in("Tag", tagId);
        var documents = classesCollection.find(filter);
        //.projection(Projections.exclude("_id"));
        documents.forEach(doc -> {
            JsonObject jsonDoc = JsonParser.parseString(doc.toJson()).getAsJsonObject();
            result.add(jsonDoc);
        });

        return result;
    }

    public JsonArray findCoursesByTagIds(int[] tagIds)
    {
        Gson _gson = new Gson();
        JsonArray jsonArray = new JsonArray();
        ArrayList<Integer> list = new ArrayList<Integer>();
        try {
            for(int tagId : tagIds)
            {
                JsonArray courses = findCoursesByTagId(tagId);
                for(JsonElement el: courses)
                {
                    Class classObj = _gson.fromJson(el, Class.class);
                    int courseNum = classObj.getCourseNum();
                    if(!list.contains(courseNum))
                    {
                        list.add(courseNum);
                        jsonArray.add(el);
                    }
                }
            }
        } catch (Exception e) {
            return new JsonArray();
        }
        
        return jsonArray;
    }


    // public String findCourses(int[] tagIdList)
    // {
    //     //If recieve multiple tags return json that separates them both
    //     MongoCollection<Document> classesCollection = _connection.getCollection(DATABASE, "classes");
        
    //     JsonArray result = new JsonArray();
    //     for(int tagId: tagIdList)
    //     {
    //         Bson filter = Filters.in("Tag", tagId);
    //         var documents = classesCollection.find(filter);
    //         documents.forEach(doc -> {
    //             result.add(doc.toJson());
    //         });
    //         //Currently we just take the first tagId in tagIdList and return its results
    //         break;
    //     }
    //     if(result.size() == 0)
    //         return "Nothing here";
    //     return result.toString();
        
    // }
    
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

    public Class[] getAllClasses()
    {
        MongoCollection<Document> classesCollection = _connection.getCollection(DATABASE, "classes");
        ArrayList<Class> classes = new ArrayList<Class>();
        classesCollection.find().forEach(doc -> {
            Class class1 = new Gson().fromJson(doc.toJson(), Class.class);
            classes.add(class1);
        });
        return classes.toArray(new Class[classes.size()]);
    }

    public boolean addTagToClass(String classId, int tagId)
    {
        MongoCollection<Document> classesCollection = _connection.getCollection(DATABASE, "classes");
        
        UpdateResult result = classesCollection.updateOne(eq("_id", new org.bson.types.ObjectId(classId)),
            Updates.addToSet("Tag", tagId));

        if(result.getUpsertedId() != null)
            return true;
        else
            return false;
    }
}
