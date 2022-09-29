package com.example.MarinerUserREST;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.sql.Date;
import java.util.Objects;

@Entity
public class UserObject {
    private @Id @GeneratedValue Long id;
    private String familyName, givenName, email;
    private Date birthDate;
    private int permissionType;
    private Date permissionGrantedDate;


    UserObject() {}

    public UserObject(String familyName, String givenName, String email, Date birthDate, int permissionType) {
        this.familyName = familyName;
        this.givenName = givenName;
        this.email = email;
        this.birthDate = birthDate;
        this.permissionType = permissionType;
        long millis = System.currentTimeMillis();
        this.permissionGrantedDate = new Date(millis);
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof UserObject)) return false;

        UserObject user = (UserObject) o;
        return  Objects.equals(this.id, user.id) &&
                Objects.equals(this.familyName, user.familyName) &&
                Objects.equals(this.givenName, user.givenName) &&
                Objects.equals(this.email, user.email) &&
                Objects.equals(this.birthDate, user.birthDate);
    }

    @Override
    public String toString() {
        return "com.example.MarinerUserREST.User{" +
                "id=" + id +
                ", familyName='" + familyName + '\'' +
                ", givenName='" + givenName + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", permissionType=" + permissionType +
                ", permissionDateGranted" + permissionGrantedDate +
                '}';
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(int permissionType) {
        this.permissionType = permissionType;
    }

    public Date getPermissionGrantedDate() {
        return permissionGrantedDate;
    }

    public void setPermissionGrantedDate(Date permissionGrantedDate) {
        this.permissionGrantedDate = permissionGrantedDate;
    }
}