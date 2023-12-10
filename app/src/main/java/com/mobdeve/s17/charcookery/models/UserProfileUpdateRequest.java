package com.mobdeve.s17.charcookery.models;

import com.mobdeve.s17.charcookery.api.models.CreateAccountBody;

public class UserProfileUpdateRequest extends CreateAccountBody {
    private String name;
    private String diet;
    private String profilePic;

    public UserProfileUpdateRequest(String name, String diet, String profilePic) {
        super(name, diet); // Call the constructor of the superclass with name and diet
        this.name = name;
        this.diet = diet;
        this.profilePic = profilePic;
    }

    public String getName() {
        return name;
    }

    public String getDiet() {
        return diet;
    }

    public String getProfilePic() {
        return profilePic;
    }
}
