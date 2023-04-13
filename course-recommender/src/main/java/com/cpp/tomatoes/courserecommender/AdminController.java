package com.cpp.tomatoes.courserecommender;

import java.util.Random;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

@RestController
public class AdminController {

    private String _currentKey;

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

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
