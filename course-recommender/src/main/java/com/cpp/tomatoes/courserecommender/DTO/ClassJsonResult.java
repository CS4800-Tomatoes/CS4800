package com.cpp.tomatoes.courserecommender.DTO;

import com.cpp.tomatoes.courserecommender.Models.Class;
import com.google.gson.annotations.SerializedName;

public class ClassJsonResult {
    //0 means exception, 1 means has content, 2 means no content/no string given
    @SerializedName("status")
    private int _status;
    @SerializedName("errorMessage")
    private String _errorMessage;
    @SerializedName("classData")
    private Class[] _classList;

    public void setStatus(int status){_status = status;}
    public void setErrorMessages(String msg){_errorMessage = msg;}
    public void setClassList(Class[] classList){_classList = classList;}

    public int getStatus(){return _status;}
    public String getErrorMessage(){return _errorMessage;}
    public Class[] getClassList(){return _classList.clone();}
}
