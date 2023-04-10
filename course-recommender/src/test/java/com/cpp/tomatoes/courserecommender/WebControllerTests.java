package com.cpp.tomatoes.courserecommender;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WebControllerTests {

    // @Test
    // void getAiTagTagId(){
    //     WebController _controller = new WebController();
    //     assertNotNull(_controller);
    //     //Need to fix Hello AI!. The ! messes with the keyword searching
    //     String returnedJson = _controller.mongoSearch("Hello AI");
    //     JsonArray jsonArray = JsonParser.parseString(returnedJson).getAsJsonArray();
    //     JsonObject firstItem = JsonParser.parseString(jsonArray.get(0).getAsString()).getAsJsonObject();
    //     String className = firstItem.get("Class Name").getAsString();
    //     Assertions.assertTrue(className.equals("Artificial Intelligence"));
    // }
}
