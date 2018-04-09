package com.example.reqres.reqressample.models;

/**
 * Created by sandeep on 05/04/18.
 */

public class User {

    private int id;

    private String first_name;

    private String last_name;

    private String avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String firstName) {
        this.first_name = firstName;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String lastName) {
        this.last_name = lastName;
    }

    public String getImageUrl() {
        return avatar;
    }

    public void setImageUrl(String imageUrl) {
        this.avatar = imageUrl;
    }
}
