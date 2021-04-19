package com.qyai.baidumap.postion.bean;

import com.lib.common.baseUtils.baseModle.BaseResult;

public class DevBean extends BaseResult {

    /**
     * data : {"page":null,"limit":null,"sort":null,"order":null,"keyQuery":null,"id":null,"deviceId":"359202391952074","deviceType":"1","authorId":"547","watchType":null,"authorType":"1","authorName":"张三","locX":114.537831,"locY":30.491629,"locZ":0,"direction":null,"speed":0,"createTime":"2021-04-14 14:22:00","createUser":null,"areaId":null,"onlineStatus":null,"admin":false,"adminDo":null,"sceneId":null,"buildId":null,"floorId":null,"person":{"personId":547,"jobNumber":"","name":"张三","sex":"1","company":"","duty":"","personType":"C308","phone":"15927387000","idcard":"110101199003076237","status":"1","ownerCompany":"","skillLevel":"1","jobStatus":"1","permanentAddress":"啊","currentAddress":"啊","politicalStatus":"2","nation":"1","emergencyMan":"旭哥","emergencyPhone":"18682163062","img":"","updateTime":"2021-04-14 14:21:46","updateUser":"U19","createTime":"2021-03-25 18:28:04","createUser":null,"job":"","age":31,"ygxz":"1","ygxzName":"企内","sexName":"男","personTypeName":"测试10","statusName":"有效","skillLevelName":"初级工","jobStatusName":"在岗","politicalStatusName":"群众","nationName":"汉族","pProvinceName":"黑龙江省","pCityName":"齐齐哈尔市","pDistrictName":"龙沙区","pAddressName":"黑龙江省齐齐哈尔市龙沙区","cProvinceName":"青海省","cCityName":"黄南藏族自治州","cDistrictName":"泽库县","cAddressName":"青海省黄南藏族自治州泽库县","cprovince":"630000","ccity":"632300","cdistrict":"632323","pprovince":"230000","pcity":"230200","pdistrict":"230202"},"car":null,"classType":"C308","carNum":null,"time":1618381320534,"cameras":null,"positionAddress":null,"lineLimit":null}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * page : null
         * limit : null
         * sort : null
         * order : null
         * keyQuery : null
         * id : null
         * deviceId : 359202391952074
         * deviceType : 1
         * authorId : 547
         * watchType : null
         * authorType : 1
         * authorName : 张三
         * locX : 114.537831
         * locY : 30.491629
         * locZ : 0.0
         * direction : null
         * speed : 0.0
         * createTime : 2021-04-14 14:22:00
         * createUser : null
         * areaId : null
         * onlineStatus : null
         * admin : false
         * adminDo : null
         * sceneId : null
         * buildId : null
         * floorId : null
         * person : {"personId":547,"jobNumber":"","name":"张三","sex":"1","company":"","duty":"","personType":"C308","phone":"15927387000","idcard":"110101199003076237","status":"1","ownerCompany":"","skillLevel":"1","jobStatus":"1","permanentAddress":"啊","currentAddress":"啊","politicalStatus":"2","nation":"1","emergencyMan":"旭哥","emergencyPhone":"18682163062","img":"","updateTime":"2021-04-14 14:21:46","updateUser":"U19","createTime":"2021-03-25 18:28:04","createUser":null,"job":"","age":31,"ygxz":"1","ygxzName":"企内","sexName":"男","personTypeName":"测试10","statusName":"有效","skillLevelName":"初级工","jobStatusName":"在岗","politicalStatusName":"群众","nationName":"汉族","pProvinceName":"黑龙江省","pCityName":"齐齐哈尔市","pDistrictName":"龙沙区","pAddressName":"黑龙江省齐齐哈尔市龙沙区","cProvinceName":"青海省","cCityName":"黄南藏族自治州","cDistrictName":"泽库县","cAddressName":"青海省黄南藏族自治州泽库县","cprovince":"630000","ccity":"632300","cdistrict":"632323","pprovince":"230000","pcity":"230200","pdistrict":"230202"}
         * car : null
         * classType : C308
         * carNum : null
         * time : 1618381320534
         * cameras : null
         * positionAddress : null
         * lineLimit : null
         */

        private Object page;
        private Object limit;
        private Object sort;
        private Object order;
        private Object keyQuery;
        private Object id;
        private String deviceId;
        private String deviceType;
        private String authorId;
        private Object watchType;
        private String authorType;
        private String authorName;
        private double locX;
        private double locY;
        private double locZ;
        private Object direction;
        private double speed;
        private String createTime;
        private Object createUser;
        private Object areaId;
        private Object onlineStatus;
        private boolean admin;
        private Object adminDo;
        private Object sceneId;
        private Object buildId;
        private Object floorId;
        private PersonBean person;
        private Object car;
        private String classType;
        private Object carNum;
        private long time;
        private Object cameras;
        private Object positionAddress;
        private Object lineLimit;

        public Object getPage() {
            return page;
        }

        public void setPage(Object page) {
            this.page = page;
        }

        public Object getLimit() {
            return limit;
        }

        public void setLimit(Object limit) {
            this.limit = limit;
        }

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }

        public Object getOrder() {
            return order;
        }

        public void setOrder(Object order) {
            this.order = order;
        }

        public Object getKeyQuery() {
            return keyQuery;
        }

        public void setKeyQuery(Object keyQuery) {
            this.keyQuery = keyQuery;
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getAuthorId() {
            return authorId;
        }

        public void setAuthorId(String authorId) {
            this.authorId = authorId;
        }

        public Object getWatchType() {
            return watchType;
        }

        public void setWatchType(Object watchType) {
            this.watchType = watchType;
        }

        public String getAuthorType() {
            return authorType;
        }

        public void setAuthorType(String authorType) {
            this.authorType = authorType;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public double getLocX() {
            return locX;
        }

        public void setLocX(double locX) {
            this.locX = locX;
        }

        public double getLocY() {
            return locY;
        }

        public void setLocY(double locY) {
            this.locY = locY;
        }

        public double getLocZ() {
            return locZ;
        }

        public void setLocZ(double locZ) {
            this.locZ = locZ;
        }

        public Object getDirection() {
            return direction;
        }

        public void setDirection(Object direction) {
            this.direction = direction;
        }

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getCreateUser() {
            return createUser;
        }

        public void setCreateUser(Object createUser) {
            this.createUser = createUser;
        }

        public Object getAreaId() {
            return areaId;
        }

        public void setAreaId(Object areaId) {
            this.areaId = areaId;
        }

        public Object getOnlineStatus() {
            return onlineStatus;
        }

        public void setOnlineStatus(Object onlineStatus) {
            this.onlineStatus = onlineStatus;
        }

        public boolean isAdmin() {
            return admin;
        }

        public void setAdmin(boolean admin) {
            this.admin = admin;
        }

        public Object getAdminDo() {
            return adminDo;
        }

        public void setAdminDo(Object adminDo) {
            this.adminDo = adminDo;
        }

        public Object getSceneId() {
            return sceneId;
        }

        public void setSceneId(Object sceneId) {
            this.sceneId = sceneId;
        }

        public Object getBuildId() {
            return buildId;
        }

        public void setBuildId(Object buildId) {
            this.buildId = buildId;
        }

        public Object getFloorId() {
            return floorId;
        }

        public void setFloorId(Object floorId) {
            this.floorId = floorId;
        }

        public PersonBean getPerson() {
            return person;
        }

        public void setPerson(PersonBean person) {
            this.person = person;
        }

        public Object getCar() {
            return car;
        }

        public void setCar(Object car) {
            this.car = car;
        }

        public String getClassType() {
            return classType;
        }

        public void setClassType(String classType) {
            this.classType = classType;
        }

        public Object getCarNum() {
            return carNum;
        }

        public void setCarNum(Object carNum) {
            this.carNum = carNum;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public Object getCameras() {
            return cameras;
        }

        public void setCameras(Object cameras) {
            this.cameras = cameras;
        }

        public Object getPositionAddress() {
            return positionAddress;
        }

        public void setPositionAddress(Object positionAddress) {
            this.positionAddress = positionAddress;
        }

        public Object getLineLimit() {
            return lineLimit;
        }

        public void setLineLimit(Object lineLimit) {
            this.lineLimit = lineLimit;
        }

        public static class PersonBean {
            /**
             * personId : 547
             * jobNumber :
             * name : 张三
             * sex : 1
             * company :
             * duty :
             * personType : C308
             * phone : 15927387000
             * idcard : 110101199003076237
             * status : 1
             * ownerCompany :
             * skillLevel : 1
             * jobStatus : 1
             * permanentAddress : 啊
             * currentAddress : 啊
             * politicalStatus : 2
             * nation : 1
             * emergencyMan : 旭哥
             * emergencyPhone : 18682163062
             * img :
             * updateTime : 2021-04-14 14:21:46
             * updateUser : U19
             * createTime : 2021-03-25 18:28:04
             * createUser : null
             * job :
             * age : 31
             * ygxz : 1
             * ygxzName : 企内
             * sexName : 男
             * personTypeName : 测试10
             * statusName : 有效
             * skillLevelName : 初级工
             * jobStatusName : 在岗
             * politicalStatusName : 群众
             * nationName : 汉族
             * pProvinceName : 黑龙江省
             * pCityName : 齐齐哈尔市
             * pDistrictName : 龙沙区
             * pAddressName : 黑龙江省齐齐哈尔市龙沙区
             * cProvinceName : 青海省
             * cCityName : 黄南藏族自治州
             * cDistrictName : 泽库县
             * cAddressName : 青海省黄南藏族自治州泽库县
             * cprovince : 630000
             * ccity : 632300
             * cdistrict : 632323
             * pprovince : 230000
             * pcity : 230200
             * pdistrict : 230202
             */

            private int personId;
            private String jobNumber;
            private String name;
            private String sex;
            private String company;
            private String duty;
            private String personType;
            private String phone;
            private String idcard;
            private String status;
            private String ownerCompany;
            private String skillLevel;
            private String jobStatus;
            private String permanentAddress;
            private String currentAddress;
            private String politicalStatus;
            private String nation;
            private String emergencyMan;
            private String emergencyPhone;
            private String img;
            private String updateTime;
            private String updateUser;
            private String createTime;
            private Object createUser;
            private String job;
            private int age;
            private String ygxz;
            private String ygxzName;
            private String sexName;
            private String personTypeName;
            private String statusName;
            private String skillLevelName;
            private String jobStatusName;
            private String politicalStatusName;
            private String nationName;
            private String pProvinceName;
            private String pCityName;
            private String pDistrictName;
            private String pAddressName;
            private String cProvinceName;
            private String cCityName;
            private String cDistrictName;
            private String cAddressName;
            private String cprovince;
            private String ccity;
            private String cdistrict;
            private String pprovince;
            private String pcity;
            private String pdistrict;

            public int getPersonId() {
                return personId;
            }

            public void setPersonId(int personId) {
                this.personId = personId;
            }

            public String getJobNumber() {
                return jobNumber;
            }

            public void setJobNumber(String jobNumber) {
                this.jobNumber = jobNumber;
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

            public String getCompany() {
                return company;
            }

            public void setCompany(String company) {
                this.company = company;
            }

            public String getDuty() {
                return duty;
            }

            public void setDuty(String duty) {
                this.duty = duty;
            }

            public String getPersonType() {
                return personType;
            }

            public void setPersonType(String personType) {
                this.personType = personType;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getIdcard() {
                return idcard;
            }

            public void setIdcard(String idcard) {
                this.idcard = idcard;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getOwnerCompany() {
                return ownerCompany;
            }

            public void setOwnerCompany(String ownerCompany) {
                this.ownerCompany = ownerCompany;
            }

            public String getSkillLevel() {
                return skillLevel;
            }

            public void setSkillLevel(String skillLevel) {
                this.skillLevel = skillLevel;
            }

            public String getJobStatus() {
                return jobStatus;
            }

            public void setJobStatus(String jobStatus) {
                this.jobStatus = jobStatus;
            }

            public String getPermanentAddress() {
                return permanentAddress;
            }

            public void setPermanentAddress(String permanentAddress) {
                this.permanentAddress = permanentAddress;
            }

            public String getCurrentAddress() {
                return currentAddress;
            }

            public void setCurrentAddress(String currentAddress) {
                this.currentAddress = currentAddress;
            }

            public String getPoliticalStatus() {
                return politicalStatus;
            }

            public void setPoliticalStatus(String politicalStatus) {
                this.politicalStatus = politicalStatus;
            }

            public String getNation() {
                return nation;
            }

            public void setNation(String nation) {
                this.nation = nation;
            }

            public String getEmergencyMan() {
                return emergencyMan;
            }

            public void setEmergencyMan(String emergencyMan) {
                this.emergencyMan = emergencyMan;
            }

            public String getEmergencyPhone() {
                return emergencyPhone;
            }

            public void setEmergencyPhone(String emergencyPhone) {
                this.emergencyPhone = emergencyPhone;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getUpdateUser() {
                return updateUser;
            }

            public void setUpdateUser(String updateUser) {
                this.updateUser = updateUser;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public Object getCreateUser() {
                return createUser;
            }

            public void setCreateUser(Object createUser) {
                this.createUser = createUser;
            }

            public String getJob() {
                return job;
            }

            public void setJob(String job) {
                this.job = job;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public String getYgxz() {
                return ygxz;
            }

            public void setYgxz(String ygxz) {
                this.ygxz = ygxz;
            }

            public String getYgxzName() {
                return ygxzName;
            }

            public void setYgxzName(String ygxzName) {
                this.ygxzName = ygxzName;
            }

            public String getSexName() {
                return sexName;
            }

            public void setSexName(String sexName) {
                this.sexName = sexName;
            }

            public String getPersonTypeName() {
                return personTypeName;
            }

            public void setPersonTypeName(String personTypeName) {
                this.personTypeName = personTypeName;
            }

            public String getStatusName() {
                return statusName;
            }

            public void setStatusName(String statusName) {
                this.statusName = statusName;
            }

            public String getSkillLevelName() {
                return skillLevelName;
            }

            public void setSkillLevelName(String skillLevelName) {
                this.skillLevelName = skillLevelName;
            }

            public String getJobStatusName() {
                return jobStatusName;
            }

            public void setJobStatusName(String jobStatusName) {
                this.jobStatusName = jobStatusName;
            }

            public String getPoliticalStatusName() {
                return politicalStatusName;
            }

            public void setPoliticalStatusName(String politicalStatusName) {
                this.politicalStatusName = politicalStatusName;
            }

            public String getNationName() {
                return nationName;
            }

            public void setNationName(String nationName) {
                this.nationName = nationName;
            }

            public String getPProvinceName() {
                return pProvinceName;
            }

            public void setPProvinceName(String pProvinceName) {
                this.pProvinceName = pProvinceName;
            }

            public String getPCityName() {
                return pCityName;
            }

            public void setPCityName(String pCityName) {
                this.pCityName = pCityName;
            }

            public String getPDistrictName() {
                return pDistrictName;
            }

            public void setPDistrictName(String pDistrictName) {
                this.pDistrictName = pDistrictName;
            }

            public String getPAddressName() {
                return pAddressName;
            }

            public void setPAddressName(String pAddressName) {
                this.pAddressName = pAddressName;
            }

            public String getCProvinceName() {
                return cProvinceName;
            }

            public void setCProvinceName(String cProvinceName) {
                this.cProvinceName = cProvinceName;
            }

            public String getCCityName() {
                return cCityName;
            }

            public void setCCityName(String cCityName) {
                this.cCityName = cCityName;
            }

            public String getCDistrictName() {
                return cDistrictName;
            }

            public void setCDistrictName(String cDistrictName) {
                this.cDistrictName = cDistrictName;
            }

            public String getCAddressName() {
                return cAddressName;
            }

            public void setCAddressName(String cAddressName) {
                this.cAddressName = cAddressName;
            }

            public String getCprovince() {
                return cprovince;
            }

            public void setCprovince(String cprovince) {
                this.cprovince = cprovince;
            }

            public String getCcity() {
                return ccity;
            }

            public void setCcity(String ccity) {
                this.ccity = ccity;
            }

            public String getCdistrict() {
                return cdistrict;
            }

            public void setCdistrict(String cdistrict) {
                this.cdistrict = cdistrict;
            }

            public String getPprovince() {
                return pprovince;
            }

            public void setPprovince(String pprovince) {
                this.pprovince = pprovince;
            }

            public String getPcity() {
                return pcity;
            }

            public void setPcity(String pcity) {
                this.pcity = pcity;
            }

            public String getPdistrict() {
                return pdistrict;
            }

            public void setPdistrict(String pdistrict) {
                this.pdistrict = pdistrict;
            }
        }
    }
}
