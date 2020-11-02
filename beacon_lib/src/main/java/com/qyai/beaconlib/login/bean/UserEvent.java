package com.qyai.beaconlib.login.bean;

import java.io.Serializable;

public class UserEvent implements Serializable {
    private String code;
    private String msg;
    private UserData data;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }

    public static class UserData implements Serializable {
        private String userName;
        private String idCard;//身份证号
        private String policeNumber;//警号
        private String contactNumber;//手机号
        private String organName;//单位
        private String userIcon;//头像
        private String userId;
        private String userType;
        private String userStatusName;
        private String politicalOutlook;
        private String famousRace;
        private String domicilePlace;//户口所在地
        private String currentResidence;//当前居住地
        private String mailBox;
        private String token;
        private String name;
        private String sex;
        private String number;
        private String mobilePhone;
        private String address;
        private String icon;
        private String userJob;
        private String jobLevel;
        private String remark;
        private String deptId;
        private String deptFullName;
        private String deptName;
        private String status;
        private String level;//1分局 2派出所 3警务区
        private String supDept;
        private String sexName;
        private String userJobName;
        private String userTypeName;
        private String statusName;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getMobilePhone() {
            return mobilePhone;
        }

        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getUserJob() {
            return userJob;
        }

        public void setUserJob(String userJob) {
            this.userJob = userJob;
        }

        public String getJobLevel() {
            return jobLevel;
        }

        public void setJobLevel(String jobLevel) {
            this.jobLevel = jobLevel;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getDeptId() {
            return deptId;
        }

        public void setDeptId(String deptId) {
            this.deptId = deptId;
        }

        public String getDeptFullName() {
            return deptFullName;
        }

        public void setDeptFullName(String deptFullName) {
            this.deptFullName = deptFullName;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getSupDept() {
            return supDept;
        }

        public void setSupDept(String supDept) {
            this.supDept = supDept;
        }

        public String getSexName() {
            return sexName;
        }

        public void setSexName(String sexName) {
            this.sexName = sexName;
        }

        public String getUserJobName() {
            return userJobName;
        }

        public void setUserJobName(String userJobName) {
            this.userJobName = userJobName;
        }

        public String getUserTypeName() {
            return userTypeName;
        }

        public void setUserTypeName(String userTypeName) {
            this.userTypeName = userTypeName;
        }

        public String getStatusName() {
            return statusName;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }

        public String getUserStatusName() {
            return userStatusName;
        }

        public void setUserStatusName(String userStatusName) {
            this.userStatusName = userStatusName;
        }

        public String getPoliticalOutlook() {
            return politicalOutlook;
        }

        public void setPoliticalOutlook(String politicalOutlook) {
            this.politicalOutlook = politicalOutlook;
        }

        public String getFamousRace() {
            return famousRace;
        }

        public void setFamousRace(String famousRace) {
            this.famousRace = famousRace;
        }

        public String getDomicilePlace() {
            return domicilePlace;
        }

        public void setDomicilePlace(String domicilePlace) {
            this.domicilePlace = domicilePlace;
        }

        public String getCurrentResidence() {
            return currentResidence;
        }

        public void setCurrentResidence(String currentResidence) {
            this.currentResidence = currentResidence;
        }

        public String getMailBox() {
            return mailBox;
        }

        public void setMailBox(String mailBox) {
            this.mailBox = mailBox;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getUserIcon() {
            return userIcon;
        }

        public void setUserIcon(String userIcon) {
            this.userIcon = userIcon;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getPoliceNumber() {
            return policeNumber;
        }

        public void setPoliceNumber(String policeNumber) {
            this.policeNumber = policeNumber;
        }

        public String getContactNumber() {
            return contactNumber;
        }

        public void setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
        }

        public String getOrganName() {
            return organName;
        }

        public void setOrganName(String organName) {
            this.organName = organName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
