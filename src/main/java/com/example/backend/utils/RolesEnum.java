package com.example.backend.utils;

public enum RolesEnum {
    WAITER("WAITER"),
    ADMIN("ADMIN");

    private final String roleName;

    RolesEnum(String roleName){
        this.roleName = roleName;
    }

    public String getRoleName(){
        return roleName;
    }
}
