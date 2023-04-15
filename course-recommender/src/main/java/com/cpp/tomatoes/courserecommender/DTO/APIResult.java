package com.cpp.tomatoes.courserecommender.DTO;

import com.google.gson.annotations.SerializedName;

public class APIResult {
    public static final int EXCEPTION = 0;
    public static final int SUCCESS = 1;
    public static final int EMPTY = 2;

    @SerializedName("status")
    private int _status;
    @SerializedName("errorMessage")
    private String _errorMessage;

    public void setStatus(int status){_status = status;}
    public void setErrorMessages(String msg){_errorMessage = msg;}

    public int getStatus(){return _status;}
    public String getErrorMessage(){return _errorMessage;}
}
