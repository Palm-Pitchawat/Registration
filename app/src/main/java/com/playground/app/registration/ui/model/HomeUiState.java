package com.playground.app.registration.ui.model;

public class HomeUiState {
    Boolean isInitial;
    UserDisplayInfo userDisplayInfo;
    String error;
    public HomeUiState(Boolean isInitial, UserDisplayInfo userDisplayInfo, String error) {
        this.isInitial = isInitial;
        this.userDisplayInfo = userDisplayInfo;
        this.error = error;
    }
    public UserDisplayInfo getUserDisplayInfo() {
        return userDisplayInfo;
    }
    public String getError() { return error; }
    public Boolean getIsInitial() { return isInitial; }
}
