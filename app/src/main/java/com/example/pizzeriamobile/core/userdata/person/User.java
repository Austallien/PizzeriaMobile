package com.example.pizzeriamobile.core.userdata.person;

import android.graphics.Bitmap;

public class User {
    private int Id;
    private String FirstName;
    private String MiddleName;
    private String LastName;
    private String Role;
    private String Login;
    private Bitmap Image;

    public User() {

    }

    public User(int Id, String FirstName, String MiddleName, String LastName, String Login, String Role, Bitmap Image) {
        this.Id = Id;
        this.FirstName = FirstName;
        this.MiddleName = MiddleName;
        this.LastName = LastName;
        this.Login = Login;
        this.Role = Role;

        if(Image == null){
            Image = Bitmap.createBitmap(100,100, Bitmap.Config.RGB_565);
        }
        this.Image = Image;
    }

    public int getId() {
        return Id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getRole() {
        return Role;
    }

    public String getLogin() {
        return Login;
    }

    public Bitmap getImage() {
        return Image;
    }
}
