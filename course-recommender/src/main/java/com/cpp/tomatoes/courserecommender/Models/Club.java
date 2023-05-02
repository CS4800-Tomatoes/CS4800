package com.cpp.tomatoes.courserecommender.Models;

import com.google.gson.annotations.SerializedName;

public class Club {
    private ObjectId _id;
    public ObjectId get_id() {
        return _id;
    }
    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    @SerializedName("name")
    private String _clubName;
    @SerializedName("description")
    private String _description;
    @SerializedName("tag")
    private int[] _tagList;
    @SerializedName("website")
    private String _websiteUrl;
    @SerializedName("image")
    private String _imageUrl;

    public void setClubName(String name){_clubName = name;}
    public void setDescription(String description){_description = description;}
    public void setTagList(int[] tagList){_tagList = tagList;}
    public void setWebsiteUrl(String url){_websiteUrl = url;}
    public void setImageUrl(String url){_imageUrl = url;}

    public String getClubName(){return _clubName;}
    public String getDescription(){return _description;}
    public int[] getTagList(){return _tagList.clone();}
    public String getWebsiteUrl(){return _websiteUrl;}
    public String getImageUrl(){return _imageUrl;}
}
