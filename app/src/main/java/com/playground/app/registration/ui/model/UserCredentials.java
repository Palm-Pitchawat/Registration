package com.playground.app.registration.ui.model;

public class UserCredentials {
    UserInput username = null;
    UserInput password = null;
    public UserCredentials(UserInput username, UserInput password) {
        this.username = username;
        this.password = password;
    }
    public UserInput getUsername() {
        return username;
    }
    public void setUsername(UserInput username) {
        this.username = username;
    }
    public UserInput getPassword() {
        return password;
    }
    public void setPassword(UserInput password) {
        this.password = password;
    }
}
