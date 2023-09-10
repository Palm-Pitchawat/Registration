package com.playground.app.registration.ui.model;

import android.net.Uri;

public class UserDisplayInfo {
    String citizenId;
    String firstName;
    String lastName;
    String phoneNumber;
    String email;
    Uri picture;
    public UserDisplayInfo(String citizenId, String firstName, String lastName, String phoneNumber, String email, Uri picture) {
        this.citizenId = citizenId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.picture = picture;
    }
    public String getCitizenId() {
        return citizenId;
    }
    public void setCitizenId(String citizenId) {
        this.citizenId = citizenId;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Uri getPictureUri() {
        return picture;
    }
    public void setPictureUri(Uri picture) {
        this.picture = picture;
    }
}
