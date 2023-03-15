package com.cpp.tomatoes.courserecommender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RestController
public class WebController {
    private MongoRepo _repo;
    private Tag[] _tagList;

    public WebController()
    {
        _repo = new MongoRepo();
        _tagList = _repo.getAllTags();
    }

    @GetMapping(path = "/welcome")
    public String welcome()
    {
        return "Hi Mady";
    }

    @GetMapping(path = "/jsoupExample")
    public String jsoupExample()
    {
        JsonObject jsonObject = new JsonObject();
        Document doc;
        try {

            // need http protocol
            doc = Jsoup.connect("http://google.com").get();

            // get page title
            String title = doc.title();
            jsonObject.addProperty("title", title);

            JsonArray jsonArray = new JsonArray();
            // get all links
            Elements links = doc.select("a[href]");
            for (Element link : links) {

                JsonObject innerObject = new JsonObject();
                innerObject.addProperty("link", link.attr("href"));
                innerObject.addProperty("text", link.text());
                jsonArray.add(innerObject);
            }
            jsonObject.add("ArrayOfLinks", jsonArray);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
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
        //Make this return a json
        if(searchString == null)
        {
            return "Missing Params";
        }
        
        int[] tagIdList = findTagIdsFromSearchString(searchString);
        if(tagIdList.length == 0)
        {
            return "Nothing";
        }

        String result = _repo.findCourses(tagIdList);

        return result;
    }

    private int[] findTagIdsFromSearchString(String searchString)
    {
        //turn string into arry
        //check if any of these words are keywords
        //use map to turn keywords to tags
        //return arry of tags

        String[] splitSearchString = searchString.split(" ");
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(String token: splitSearchString)
        {
            for(Tag tag: _tagList)
            {
                String tagName = tag.getTagName();
                if(token.toLowerCase().equals(tagName.toLowerCase()))
                {
                    list.add(tag.getTagId());
                }
            }
        }
        return list.stream().mapToInt(i -> i).toArray();
    }

    @GetMapping(path = "/test")
    public String test()
    {
        return "Hi Test Complete";
    }
}
