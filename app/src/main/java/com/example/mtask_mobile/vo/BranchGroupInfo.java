package com.example.mtask_mobile.vo;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.LitePalSupport;

public class BranchGroupInfo extends LitePalSupport implements Parcelable {
    private String branchId;
    private String parentId;
    private long _id;
    private String name;
    private String orderNo;
    private String isDelete;
    private String createdAt;
    private String updatedAt;

    public BranchGroupInfo() {

    }

    public BranchGroupInfo(String branchId, String parentId, long _id, String name, String orderNo, String isDelete, String createdAt, String updatedAt) {
        this.branchId = branchId;
        this.parentId = parentId;
        this._id = _id;
        this.name = name;
        this.orderNo = orderNo;
        this.isDelete = isDelete;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String isDelete() {
        return isDelete;
    }

    public void setDelete(String delete) {
        isDelete = delete;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(branchId);
        dest.writeString(parentId);
        dest.writeLong(_id);
        dest.writeString(name);
        dest.writeString(orderNo);
        dest.writeString(isDelete);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
    }

    public static final Parcelable.Creator<BranchGroupInfo> CREATOR = new Creator<BranchGroupInfo>() {
        @Override
        public BranchGroupInfo createFromParcel(Parcel source) {
            BranchGroupInfo groupInfo = new BranchGroupInfo();
            groupInfo.branchId = source.readString();
            groupInfo.parentId = source.readString();
            groupInfo._id = source.readLong();
            groupInfo.name = source.readString();
            groupInfo.orderNo = source.readString();
            groupInfo.isDelete = source.readString();
            groupInfo.createdAt = source.readString();
            groupInfo.updatedAt = source.readString();


            return groupInfo;
        }

        @Override
        public BranchGroupInfo[] newArray(int size) {
            return new BranchGroupInfo[size];
        }
    };
}
