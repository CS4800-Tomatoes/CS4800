package com.cpp.tomatoes.courserecommender.DTO;

import com.cpp.tomatoes.courserecommender.Models.Club;
import com.google.gson.annotations.SerializedName;

public class ClubJsonResult {
    @SerializedName("status")
    private int _status;
    @SerializedName("errorMessage")
    private String _errorMessage;
    @SerializedName("clubData")
    private Club[] _clubList;

    public void setStatus(int status){_status = status;}
    public void setErrorMessages(String msg){_errorMessage = msg;}
    public void setClubList(Club[] clubList){_clubList = clubList;}

    public int getStatus(){return _status;}
    public String getErrorMessage(){return _errorMessage;}
    public Club[] getClubList(){return _clubList.clone();}
}
