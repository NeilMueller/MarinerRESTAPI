package com.example.MarinerUserREST;

import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
public class Permission {

    private int permissionType;
    private Date permissionGrantedDate;

    public static final int PERMISSIONLEVELZERO = 0;
    public static final int PERMISSIONLEVELONE = 1;

    Permission(){}

    public Permission(int permissionType){
        this.permissionType = permissionType;
        long millis = System.currentTimeMillis();
        permissionGrantedDate = new Date(millis);
    }

    public void grantPermission(){
        permissionType = PERMISSIONLEVELONE;
        long millis = System.currentTimeMillis();
        permissionGrantedDate = new Date(millis);
    }

    public void revokePermission(){
        permissionType = PERMISSIONLEVELZERO;
        long millis = System.currentTimeMillis();
        permissionGrantedDate = new Date(millis);
    }

    @Override
    public String toString() {
        return "Permission{" +
                "permissionType=" + permissionType +
                ", permissionGrantedDate=" + permissionGrantedDate +
                '}';
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
