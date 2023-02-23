package com.cpp.tomatoes.courserecommender;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@RestController
public class WebController {
    
    @GetMapping(path = "/welcome")
    public String welcome()
    {
        return "Welcome!";
    }

    @GetMapping(path = "/jsoupT")
    public void jsoupTrial() {

        Document doc;
        try {

            // need http protocol
            doc = Jsoup.connect("https://www.chess.com/").get();

            // get page title
            String title = doc.title();
            System.out.println("title : " + title);

            // get all links
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                // get the value from href attribute
                System.out.println("\nlink : " + link.attr("href"));
                System.out.println("text : " + link.text());
            }

            

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
