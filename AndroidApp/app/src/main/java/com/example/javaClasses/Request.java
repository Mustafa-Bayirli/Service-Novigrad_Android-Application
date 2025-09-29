package com.example.javaClasses;

import android.graphics.Bitmap;
import android.media.Image;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;

    public RequestMaster master;

    //Always Necessary Information (FORM)
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String address;

    //Types of Image that Might be Needed (DOCUMENTS)
    private Bitmap proofOfResidence;
    private Bitmap proofOfStatus;
    private Bitmap photoOfUser;

    //In case more information is needed these exist
    String otherInformationRequired;

    public Request(RequestMaster master, String firstName, String lastName, String dateOfBirth, String address) {
        this.master = master;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        proofOfResidence = null;
        proofOfStatus = null;
        photoOfUser = null;
    }

    public void setProofOfResidence(Bitmap proofOfResidence) {
        this.proofOfResidence = proofOfResidence;
    }

    public void setProofOfStatus(Bitmap proofOfStatus) {
        this.proofOfStatus = proofOfStatus;
    }

    public void setPhotoOfUser(Bitmap photoOfUser) {
        this.photoOfUser = photoOfUser;
    }

    public void setOtherInformationRequired(String otherInformationRequired) {
        this.otherInformationRequired = otherInformationRequired;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public RequestMaster getMaster() {
        return master;
    }

    public void setMaster(RequestMaster master) {
        this.master = master;
    }

    public Bitmap getPhotoOfUser() {
        return photoOfUser;
    }

    public Bitmap getProofOfResidence() {
        return proofOfResidence;
    }

    public Bitmap getProofOfStatus() {
        return proofOfStatus;
    }

    public String getOtherInformationRequired() {
        return otherInformationRequired;
    }

    public String getAddress() {
        return address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}