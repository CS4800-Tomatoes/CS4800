package com.cpp.tomatoes.courserecommender.Models;

public class Class {
    
    private ObjectId _id;
    public ObjectId get_id() {
        return _id;
    }
    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    private int _courseNumber;
    private String _className;
    private String _description;

    public void setCourseNumber(int num){_courseNumber = num;}
    public void setClassName(String name){_className = name;}
    public void setDescription(String description){_description = description;}

    public int getCourseNum(){return _courseNumber;}
    public String getClassName(){return _className;}
    public String getDescription(){return _description;}
}
