package com.cpp.tomatoes.courserecommender;

import java.io.IOException;

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

    // @GetMapping(path = "/mongoSearch")
    // public String mongoSearch()
    // {
    //     MongoConnection repo = new MongoConnection();
    //     return repo.test();
    // }

    @GetMapping(path = "/mongoSearch")
    public String mongoSearch(@RequestParam Integer courseNum)
    {
        if(courseNum == null)
        {
            return "Missing Params";
        }
        MongoRepo repo = new MongoRepo("classes");
        return repo.findCourseNum(courseNum);
    }
}
