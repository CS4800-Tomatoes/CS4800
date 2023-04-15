package com.cpp.tomatoes.courserecommender;

import java.util.ArrayList;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.cpp.tomatoes.courserecommender.Mongo.MongoRepo;
import com.cpp.tomatoes.courserecommender.DTO.ClassSearchResult;
import com.cpp.tomatoes.courserecommender.DTO.ClassTagUpdateResult;
import com.cpp.tomatoes.courserecommender.Models.Class;
import com.cpp.tomatoes.courserecommender.Models.Tag;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@RestController
public class AdminController {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private String _currentKey;
    private MongoRepo _repo;
    private Gson _gson;
    
    public AdminController()
    {
        _repo = new MongoRepo();
        _gson = new Gson();
    }

    @PostMapping("/Admin/SignIn")
    public String SignIn(String username, String password)
    {
        boolean successfulUsername = username.equals("test");
        boolean successfulPassword = password.equals("tomatoes");

        //make it so that if both successful, then generate a token and return it.

        if(successfulPassword && successfulUsername)
        {
            Random rand = new Random();
            int length = rand.nextInt(100)+25;
            _currentKey = generateRandomString(length);

            JsonObject obj = new JsonObject();
            obj.addProperty("status", "success");
            obj.addProperty("token", _currentKey);

            return obj.toString();
        }
        else
        {
            JsonObject obj = new JsonObject();
            obj.addProperty("status", "fail");
            return obj.toString();
        }
    }

    @GetMapping("/Admin/GetClasses")
    public String GetAllClasses()
    {
        Class[] classes = _repo.getAllClasses();
        return _gson.toJson(classes);
    }

    @GetMapping("/Admin/GetTaggedClasses")
    public String GetTaggedClasses(@RequestParam int tagId)
    {
        ClassSearchResult result = new ClassSearchResult();
        try {
            JsonArray classListAsJsonArray = _repo.findCoursesByTagId(tagId);
            if(classListAsJsonArray.size() != 0)
            {
                result.setStatus(ClassSearchResult.SUCCESS);
                //Get Classes from Array
                ArrayList<Class> classList = new ArrayList<Class>();
                for(JsonElement el: classListAsJsonArray)
                {
                    Class oneClass = _gson.fromJson(el, Class.class);
                    classList.add(oneClass);
                }
                result.setClassList(classList.toArray(new Class[classList.size()]));
            }
            else
            {
                result.setStatus(ClassSearchResult.EMPTY);
            }
        } 
        catch (Exception e) 
        {
            result.setStatus(ClassSearchResult.EXCEPTION);
            result.setErrorMessages(e.getMessage());
        }
        return _gson.toJson(result);
    }

    @PatchMapping("/Admin/AddTagToClass")
    public String AddTagToClass(String classId, int tagId)
    {
        ClassTagUpdateResult result = new ClassTagUpdateResult();
        try 
        {
            boolean updateResult = _repo.addTagToClass(classId, tagId);
            if(updateResult == true)
                result.setStatus(ClassTagUpdateResult.SUCCESS);
            else
                result.setStatus(ClassTagUpdateResult.EXCEPTION);
        } catch (Exception e) {
                result.setStatus(ClassTagUpdateResult.EXCEPTION);
        }
        return _gson.toJson(result);
    }

    @GetMapping("/Admin/GetTags")
    public String GetAllTags()
    {
        Tag[] tags = _repo.getAllTags();
        return _gson.toJson(tags);
    }

    private static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
