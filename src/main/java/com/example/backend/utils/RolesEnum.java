package com.example.backend.utils;

public enum RolesEnum {
    CUSTOMER("CUSTOMER"),
    EMPLOEEY("EMPLOEEY"),
    ADMIN("ADMIN");

    private final String roleName;

    RolesEnum(String roleName){
        this.roleName = roleName;
    }

    public String getRoleName(){
        return roleName;
    }
}
