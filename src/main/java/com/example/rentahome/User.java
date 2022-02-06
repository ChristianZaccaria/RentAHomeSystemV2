/*Developed by Christian Zaccaria (20092351)
 * CA 1 - Rent-A-Home system
 */

package com.example.rentahome;

import java.util.ArrayList;

public class User {

    private static int agentId = 0001;

    private int userId;
    private String userType;
    private boolean userEnabled;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userPhone;


    public User(String userType, boolean userEnabled, String userName, String userEmail, String userPassword, String userPhone) {
        userId=++agentId;
        this.userType = userType;
        this.userEnabled = userEnabled;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userPhone = userPhone;
    }



    @Override
    public String toString() {
        return "User{" + "Id=" + userId +
                "userType='" + userType + '\'' +
                ", userEnabled=" + userEnabled +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPhone='" + userPhone + '\'' +
                '}';
    }

    public static int getAgentId() {
        return agentId;
    }

    public static void setAgentId(int agentId) {
        User.agentId = agentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public boolean isUserEnabled() {
        return userEnabled;
    }

    public void setUserEnabled(boolean userEnabled) {
        this.userEnabled = userEnabled;
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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public boolean setUserEnabled() {
        return true;
    }


}


