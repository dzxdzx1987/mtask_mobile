package com.example.mtask_mobile.vo;

public class LoginUserInfo {

    private String companyId;
    private String branchId;
    private String id;
    private String name;
    private String email;
    private String password;

    public LoginUserInfo() {

    }

    public LoginUserInfo(String companyId, String branchId, String id, String name, String email, String password) {
        this.companyId = companyId;
        this.branchId = branchId;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
