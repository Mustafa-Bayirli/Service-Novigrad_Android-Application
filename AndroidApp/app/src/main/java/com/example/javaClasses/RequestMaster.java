package com.example.javaClasses;

import java.io.Serializable;

public class RequestMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    //Request Class Data
    int requestID;
    String requestName;

    boolean needProofOfResidence;
    boolean needProofOfStatus;
    boolean needPhotoOfUser;

    //In case more information is needed these exist
    String otherInformation;

    public RequestMaster(int ID, String name, boolean needProofOfResidence, boolean needProofOfStatus, boolean needPhotoOfUser, String otherData) {
        requestID = ID;
        requestName = name;
        this.needProofOfResidence = needProofOfResidence;
        this.needProofOfStatus = needProofOfStatus;
        this.needPhotoOfUser = needPhotoOfUser;
        otherInformation = otherData;
    }
    public RequestMaster(int ID, String name, boolean needProofOfResidence, boolean needProofOfStatus, boolean needPhotoOfUser) {
        requestID = ID;
        requestName = name;
        this.needProofOfResidence = needProofOfResidence;
        this.needProofOfStatus = needProofOfStatus;
        this.needPhotoOfUser = needPhotoOfUser;
        otherInformation = "";
    }

    public int getID() {
        return requestID;
    }

    public String getName() {
        return requestName;
    }

    public boolean getPOR() {
        return needProofOfResidence;
    }

    public boolean getPOS() {
        return needProofOfStatus;
    }

    public boolean getPOU() {
        return needPhotoOfUser;
    }

    public String getOtherInformation() {
        return otherInformation;
    }
}
