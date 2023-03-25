package com.cpp.tomatoes.courserecommender;

import java.util.ArrayList;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cpp.tomatoes.courserecommender.DTO.ClassJsonResult;
import com.cpp.tomatoes.courserecommender.Models.Class;
import com.cpp.tomatoes.courserecommender.Models.SuccessCode;
import com.cpp.tomatoes.courserecommender.Models.Tag;
import com.cpp.tomatoes.courserecommender.Mongo.MongoRepo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ch.qos.logback.core.joran.conditional.ElseAction;


@RestController
public class WebController {
    private MongoRepo _repo;
    private Tag[] _tagList;
    private Gson _gson;

    public WebController()
    {
        _repo = new MongoRepo();
        _tagList = _repo.getAllTags();
        _gson = new Gson();
    }

    @GetMapping(path = "/welcome")
    public String welcome()
    {
        return "Hi Mady";
    }

    @GetMapping(path = "/mongoSearchCourseNum")
    public String mongoSearch(@RequestParam Integer courseNum)
    {
        if(courseNum == null)
        {
            return "Missing Params";
        }
        MongoRepo repo = new MongoRepo();
        return repo.findCourses(courseNum);
    }

    @GetMapping(path = "/mongoSearch")
    public String mongoSearch(@RequestParam String searchString)
    {
        ClassJsonResult result = new ClassJsonResult();
        //Make this return a json
        if(searchString == null)
        {
            result.setStatus(SuccessCode.EMPTY);
            result.setErrorMessages("Missing Search String");
            return _gson.toJson(result);
        }
        
        int[] tagIdList = findTagIdsFromSearchString(searchString);

        if(tagIdList.length == 0)
        {
            result.setStatus(SuccessCode.EMPTY);
            result.setErrorMessages("No Results Found");
            return _gson.toJson(result);
        }

        JsonArray jsonResult = _repo.findCoursesByTagIds(tagIdList);
        //If tags are valid, then there should be a result.
        //Since we get no results even with tags existing, something is wrong
        if(jsonResult.size() == 0)
        {
            result.setStatus(SuccessCode.EXCEPTION);
            result.setErrorMessages("Something went wrong");
            return _gson.toJson(result);
        }
        ArrayList<Class> classList = new ArrayList<Class>();
        for(JsonElement json: jsonResult)
        {
            try {
                Class classObj = _gson.fromJson(json, Class.class);
                classList.add(classObj);
            } catch (Exception e) {
                return e.getMessage() + "|Padding|" + json.getAsString();
            }
        }

        result.setStatus(SuccessCode.SUCCESS);
        result.setClassList(classList.toArray(new Class[classList.size()]));

        return _gson.toJson(result);
    }

    private int[] findTagIdsFromSearchString(String searchString)
    {
        //turn string into arry
        //check if any of these words are keywords
        //use map to turn keywords to tags
        //return arry of tags
        String delimiter = " ";

        String[] splitSearchString = searchString.split(delimiter);
        ArrayList<Integer> list = new ArrayList<Integer>();
        
        for(int i = 0; i < splitSearchString.length; i++)
        {
            for(Tag tag: _tagList)
            {
                String[] tagName = tag.getTagName().split(delimiter);
                for(int j = 0; j < tagName.length && j+i < splitSearchString.length; j++)
                {
                    String token = sanitizeString(splitSearchString[i+j]).toLowerCase();
                    if(!token.equals(tagName[j].toLowerCase()))
                        break;
                    else if(tagName.length == j+1)
                        list.add(tag.getTagId());
                }
            }
        }
        return list.stream().mapToInt(i -> i).toArray();
    }

    private String sanitizeString(String str)
    {
        return str.replaceAll("[^a-zA-Z]", "");
    }
}
