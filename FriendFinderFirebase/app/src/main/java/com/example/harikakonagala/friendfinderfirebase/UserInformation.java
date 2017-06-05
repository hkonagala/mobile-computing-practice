package com.example.harikakonagala.friendfinderfirebase;

/**
 * Created by Harika Konagala on 5/21/2017.
 */

class UserInformation {
    public String userId;
    public String email;
    public String username;
    public Long timestamp;
    public double latitude;
    public double longitude;


    public UserInformation(String userId, String email, String username, Long timestamp, double latitude, double longitude) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public UserInformation() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
