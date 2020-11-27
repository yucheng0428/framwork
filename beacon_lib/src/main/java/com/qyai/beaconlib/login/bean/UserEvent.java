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

        /**
         * UserInDeptDTO : {"userId":"00","name":"超级管理员","sex":"1","number":"1001","policeNumber":"1001","idCard":"420981111111111111","contactNumber":"027-8560811","mobilePhone":"13666666666","address":"测试地址","icon":"","userJob":"0001                                              ","jobLevel":"1","userType":"1","remark":null,"deptId":"0001","deptFullName":"华科实验室","deptName":"华科","status":"1","level":"0","supDept":"000","token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkZXB0TmFtZSI6IuWNjuenkSIsImxvZ2luVHlwZSI6InBob25lIiwidXNlckpvYiI6IjAwMDEgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIiwiZGVwdElkIjoiMDAwMSIsInVzZXJJZCI6IjAwIiwic3VwRGVwdCI6IjAwMCIsImpvYkxldmVsIjoiMSIsImlwQWRkciI6IjE5Mi4xNjguMTAuMTgyIiwiZGVwdEZ1bGxOYW1lIjoi5Y2O56eR5a6e6aqM5a6kIiwiYXVkIjoiMSIsIm5hbWUiOiLotoXnuqfnrqHnkIblkZgiLCJ1c2VyVHlwZSI6IjEiLCJleHAiOjE2MDY3MTk0NjIsImFjY291bnQiOiJTSCJ9.hNSkuE2zXt1hfg5Nn5faMcVS6VCymBdEmIk6JHRdFic"}
         */

        private UserInDeptDTOBean UserInDeptDTO;

        public UserInDeptDTOBean getUserInDeptDTO() {
            return UserInDeptDTO;
        }

        public void setUserInDeptDTO(UserInDeptDTOBean UserInDeptDTO) {
            this.UserInDeptDTO = UserInDeptDTO;
        }

        public static class UserInDeptDTOBean {
            /**
             * userId : 00
             * name : 超级管理员
             * sex : 1
             * number : 1001
             * policeNumber : 1001
             * idCard : 420981111111111111
             * contactNumber : 027-8560811
             * mobilePhone : 13666666666
             * address : 测试地址
             * icon :
             * userJob : 0001
             * jobLevel : 1
             * userType : 1
             * remark : null
             * deptId : 0001
             * deptFullName : 华科实验室
             * deptName : 华科
             * status : 1
             * level : 0
             * supDept : 000
             * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkZXB0TmFtZSI6IuWNjuenkSIsImxvZ2luVHlwZSI6InBob25lIiwidXNlckpvYiI6IjAwMDEgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIiwiZGVwdElkIjoiMDAwMSIsInVzZXJJZCI6IjAwIiwic3VwRGVwdCI6IjAwMCIsImpvYkxldmVsIjoiMSIsImlwQWRkciI6IjE5Mi4xNjguMTAuMTgyIiwiZGVwdEZ1bGxOYW1lIjoi5Y2O56eR5a6e6aqM5a6kIiwiYXVkIjoiMSIsIm5hbWUiOiLotoXnuqfnrqHnkIblkZgiLCJ1c2VyVHlwZSI6IjEiLCJleHAiOjE2MDY3MTk0NjIsImFjY291bnQiOiJTSCJ9.hNSkuE2zXt1hfg5Nn5faMcVS6VCymBdEmIk6JHRdFic
             */

            private String userId;
            private String name;
            private String sex;
            private String number;
            private String policeNumber;
            private String idCard;
            private String contactNumber;
            private String mobilePhone;
            private String address;
            private String icon;
            private String userJob;
            private String jobLevel;
            private String userType;
            private Object remark;
            private String deptId;
            private String deptFullName;
            private String deptName;
            private String status;
            private String level;
            private String supDept;
            private String token;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

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

            public String getPoliceNumber() {
                return policeNumber;
            }

            public void setPoliceNumber(String policeNumber) {
                this.policeNumber = policeNumber;
            }

            public String getIdCard() {
                return idCard;
            }

            public void setIdCard(String idCard) {
                this.idCard = idCard;
            }

            public String getContactNumber() {
                return contactNumber;
            }

            public void setContactNumber(String contactNumber) {
                this.contactNumber = contactNumber;
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

            public String getUserType() {
                return userType;
            }

            public void setUserType(String userType) {
                this.userType = userType;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
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

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }
        }
    }
}
