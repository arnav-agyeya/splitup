package com.example.splitup.entities;

public class SignUpWrapper {
    private UserSplit userSplit;
    private LoginUserInfo loginInfo;

    public SignUpWrapper withUser(UserSplit userSplit) {
        this.userSplit = userSplit;
        return this;
    }

    public SignUpWrapper withLoginInfo(LoginUserInfo loginInfo) {
        this.loginInfo = loginInfo;
        return this;
    }

    public UserSplit getUser() {
        return userSplit;
    }

    public LoginUserInfo getLoginInfo(){
        return loginInfo;
    }
}
