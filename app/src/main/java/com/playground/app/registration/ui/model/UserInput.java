package com.playground.app.registration.ui.model;

public class UserInput {
    String text = "";
    Boolean isValid;
    String error = "";

    public UserInput(String text, Boolean isValid, String error) {
        this.text = text;
        this.isValid = isValid;
        this.error = error;
    }

    public UserInput() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
