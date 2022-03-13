package com.example.pizzeriamobile.logic.user;

public class User {
    private int Id;
    private String FirstName;
    private String MiddleName;
    private String LastName;
    private String Role;
    private String Login;

    public User(){

    }

    public User(int Id, String FirstName, String MiddleName, String LastName,String Login, String Role){
        this.Id = Id;
        this.FirstName = FirstName;
        this.MiddleName = MiddleName;
        this.LastName = LastName;
        this.Login = Login;
        this.Role = Role;
    }

    public int GetId(){
        return  Id;
    }

    public String GetFirstName(){
        return FirstName;
    }

    public String GetMiddleName(){
        return MiddleName;
    }

    public String GetLastName(){
        return LastName;
    }

    public String GetRole(){
        return Role;
    }

    public String GetLogin(){
        return Login;
    }
}
