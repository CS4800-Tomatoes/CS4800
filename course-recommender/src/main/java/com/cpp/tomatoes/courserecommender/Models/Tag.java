package com.cpp.tomatoes.courserecommender.Models;

import com.google.gson.JsonObject;

public class Tag {
    private JsonObject _id;
    private String tagName;
    private int tagId;

    public JsonObject getOId(){ return _id;}
    public String getTagName(){return tagName;}
    public int getTagId(){return tagId;}

    public void setOId(JsonObject oid){_id = oid;}
    public void setTagName(String tagName){this.tagName = tagName;}
    public void setTagId(int tagId){this.tagId = tagId;}

    public Tag(){}
    public Tag(JsonObject _id, String tagName, int tagId)
    {
        this._id = _id;
        this.tagName = tagName;
        this.tagId = tagId;
    }
}
