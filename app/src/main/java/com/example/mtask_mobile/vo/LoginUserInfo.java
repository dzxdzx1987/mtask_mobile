package com.example.mtask_mobile.vo;

import org.litepal.crud.LitePalSupport;

public class LoginUserInfo extends LitePalSupport {

    private String companyId;
    private String branchId;
    private String uuid;
    private String name;
    private String email;
    private String password;

    public LoginUserInfo() {

    }

    public LoginUserInfo(String companyId, String branchId, String id, String name, String email, String password) {
        this.companyId = companyId;
        this.branchId = branchId;
        this.uuid = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
