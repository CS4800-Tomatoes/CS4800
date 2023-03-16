package com.cpp.tomatoes.courserecommender.DTO;

import com.cpp.tomatoes.courserecommender.Models.Class;

public class ClassJsonResult {
    private int _status;
    private String _errorMessage;
    private Class[] _classList;

    public void setStatus(int status){_status = status;}
    public void setErrorMessages(String msg){_errorMessage = msg;}
    public void setClassList(Class[] classList){_classList = classList;}

    public int getStatus(){return _status;}
    public String getErrorMessage(){return _errorMessage;}
    public Class[] getClassList(){return _classList.clone();}
}
