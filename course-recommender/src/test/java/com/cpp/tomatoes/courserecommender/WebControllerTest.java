
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
        Integer courseNum = new Integer(4800);
        Assert.assertEquals(
            "[\"{\\\"_id\\\": {\\\"$oid\\\": \\\"63f3d06753536d5016709a67\\\"}, "+
            "\\\"Course Number\\\": 4800, \\\"Class Name\\\": " + 
            "\\\"Software Engineering\\\", \\\"Tag\\\": " + 
            "[1, 2, 3, 4]}\"]", wb.mongoSearch(courseNum));
    }
}
