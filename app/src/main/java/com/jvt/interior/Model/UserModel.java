package com.jvt.interior.Model;

public class UserModel {
    String profilePic, userName, userEmail, userMobile, uid, userAddress;

    public UserModel() {
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public UserModel(String profilePic, String userName, String userEmail, String userMobile, String uid, String userAddress) {
        this.profilePic = profilePic;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userMobile = userMobile;
        this.uid = uid;
        this.userAddress = userAddress;
    }
}
