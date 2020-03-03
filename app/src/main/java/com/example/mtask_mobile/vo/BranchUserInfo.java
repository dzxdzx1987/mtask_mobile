package com.example.mtask_mobile.vo;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class BranchUserInfo extends LitePalSupport implements Parcelable {

    private long _id;
    @Column(unique = true)
    private String uuid;
    private String branchId;
    private String branchName;
    private String name;
    private String photoPath;
    private String employeeNo;
    private String positionCode;
    private String positionName;
    private String email;
    private String email2;
    private String mobilePhone;
    private String officePhone;
    private String birthday;


    public BranchUserInfo(String branchId, String branchName, long id, String uuid, String name, String photoPath, String employeeNo, String positionCode, String positionName, String email, String email2, String mobilePhone, String officePhone, String birthday) {
        this.branchId = branchId;
        this.branchName = branchName;
        this._id = id;
        this.uuid = uuid;
        this.name = name;
        this.photoPath = photoPath;
        this.employeeNo = employeeNo;
        this.positionCode = positionCode;
        this.positionName = positionName;
        this.email = email;
        this.email2 = email2;
        this.mobilePhone = mobilePhone;
        this.officePhone = officePhone;
        this.birthday = birthday;
    }

    public BranchUserInfo () {

    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public long getId() {
        return _id;
    }

    public void setId(long id) {
        this._id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(_id);
        dest.writeString(uuid);
        dest.writeString(branchId);
        dest.writeString(branchName);
        dest.writeString(name);
        dest.writeString(photoPath);
        dest.writeString(employeeNo);
        dest.writeString(positionCode);
        dest.writeString(positionName);
        dest.writeString(email);
        dest.writeString(email2);
        dest.writeString(mobilePhone);
        dest.writeString(officePhone);
        dest.writeString(birthday);
    }

    public static final Parcelable.Creator<BranchUserInfo> CREATOR = new Creator<BranchUserInfo>() {
        @Override
        public BranchUserInfo createFromParcel(Parcel source) {

            BranchUserInfo user = new BranchUserInfo();
            user._id = source.readLong();
            user.uuid = source.readString();
            user.branchId = source.readString();
            user.branchName = source.readString();
            user.name = source.readString();
            user.photoPath = source.readString();
            user.employeeNo = source.readString();
            user.positionCode = source.readString();
            user.positionName = source.readString();
            user.email = source.readString();
            user.email2 = source.readString();
            user.mobilePhone = source.readString();
            user.officePhone = source.readString();
            user.birthday = source.readString();

            return user;
        }

        @Override
        public BranchUserInfo[] newArray(int size) {
            return new BranchUserInfo[size];
        }
    };
}
