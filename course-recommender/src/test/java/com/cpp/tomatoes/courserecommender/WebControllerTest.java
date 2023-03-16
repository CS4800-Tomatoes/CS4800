package com.cpp.tomatoes.courserecommender;

import com.cpp.tomatoes.courserecommender.WebController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.junit.*;
import java.lang.Integer;

public class WebControllerTest{
    WebController wb = new WebController();

    @Test
    public void testWebController(){
        Integer courseNum = new Integer(4200);
        Assert.assertEquals(
            "[\"{\\\"_id\\\": {\\\"$oid\\\": \\\"63f3d09c53536d5016712d9f\\\"}, "+
            "\\\"Course Number\\\": 4200, \\\"Class Name\\\": " + 
            "\\\"Artificial Intelligence\\\", \\\"Tag\\\": " + 
            "[11, 18, 8, 9, 12, 19]}\"]", wb.mongoSearch(courseNum));
    }
}
