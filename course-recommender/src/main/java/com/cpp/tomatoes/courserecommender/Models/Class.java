package com.cpp.tomatoes.courserecommender.Models;

import com.google.gson.annotations.SerializedName;

public class Class {
    private ObjectId _id;
    public ObjectId get_id() {
        return _id;
    }
    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    @SerializedName("Course Number")
    private int _courseNumber;
    @SerializedName("Class Name")
    private String _className;
    @SerializedName("Description")
    private String _description;
    @SerializedName("Tag")
    private int[] _tagList;

    @SerializedName("Image")
    private String _imageUrl;

    public void setCourseNumber(int num){_courseNumber = num;}
    public void setClassName(String name){_className = name;}
    public void setDescription(String description){_description = description;}
    public void setTagList(int[] tagList){_tagList = tagList;}
    public void setImageUrl(String url){_imageUrl = url;}

    public int getCourseNum(){return _courseNumber;}
    public String getClassName(){return _className;}
    public String getDescription(){return _description;}
    public int[] getTagList(){return _tagList.clone();}
    public String getImageUrl(){return _imageUrl;}
}
