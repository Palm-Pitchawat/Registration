package com.playground.app.registration.ui.model;

import android.net.Uri;

public class UserPersonalInfo {
    UserInput citizenId;
    UserInput firstName;
    UserInput lastName;
    UserInput phoneNumber;
    UserInput email;
    Uri picture;
    public UserPersonalInfo(UserInput citizenId, UserInput firstName, UserInput lastName, UserInput phoneNumber, UserInput email) {
        this.citizenId = citizenId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    public UserInput getCitizenId() {
        return citizenId;
    }
    public void setCitizenId(UserInput citizenId) {
        this.citizenId = citizenId;
    }
    public UserInput getFirstName() {
        return firstName;
    }
    public void setFirstName(UserInput firstName) {
        this.firstName = firstName;
    }
    public UserInput getLastName() {
        return lastName;
    }
    public void setLastName(UserInput lastName) {
        this.lastName = lastName;
    }
    public UserInput getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(UserInput phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public UserInput getEmail() {
        return email;
    }
    public void setEmail(UserInput email) {
        this.email = email;
    }
    public Uri getPictureUri() {
        return picture;
    }
    public void setPictureUri(Uri picture) {
        this.picture = picture;
    }
}
