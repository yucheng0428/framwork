package com.qyai.watch_app.xiaqu;

import com.lib.common.baseUtils.baseModle.BaseResult;

import java.io.Serializable;
import java.util.List;

public class PersonDetailResult extends BaseResult implements Serializable {

    /**
     * msg : 查询成功!
     * code : 000000
     * data : {"personDTO":{"personId":400,"jobNumber":"","name":"aday","sex":"1","company":"","duty":"","personType":13,"phone":"15927387000","idcard":"420982199308220038","status":"1","ownerCompany":"","skillLevel":"1","jobStatus":"1","permanentAddress":"","currentAddress":"","politicalStatus":"2","nation":"1","emergencyMan":"","emergencyPhone":"","img":"","updateTime":"2021-02-17 10:55:57","updateUser":null,"createTime":"2021-02-05 10:36:50","createUser":null,"job":"","age":0,"ygxz":"1","ygxzName":"企内","sexName":"男","personTypeName":"医生","statusName":"有效","skillLevelName":"初级工","jobStatusName":"在岗","politicalStatusName":"群众","nationName":"汉族","pProvinceName":null,"pCityName":null,"pDistrictName":null,"cProvinceName":null,"cCityName":null,"cDistrictName":null,"ccity":"","cdistrict":"","cprovince":"","pcity":"","pprovince":"","pdistrict":""},"type":"1","startDate":null,"endDate":null,"tagType":"1","deviceId":"42489","fenceDTOList":[],"areaLimitDTOS":[],"trainDTOList":[],"personDetailDTO":{"id":158,"personId":400,"alarmTemperature":"36.1,37.2","alarmHeartRate":"60,100","alarmOxygen":"90,100","alarmBreathingRate":"12,24","alarmBloodPressureHigh":"90,139","alarmBloodPressureLow":"60,89","operateId":"00","operateTime":"2021-02-17 10:55:57"},"personDiseasesDTOS":[],"tagTypeName":"标签"}
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
         * personDTO : {"personId":400,"jobNumber":"","name":"aday","sex":"1","company":"","duty":"","personType":13,"phone":"15927387000","idcard":"420982199308220038","status":"1","ownerCompany":"","skillLevel":"1","jobStatus":"1","permanentAddress":"","currentAddress":"","politicalStatus":"2","nation":"1","emergencyMan":"","emergencyPhone":"","img":"","updateTime":"2021-02-17 10:55:57","updateUser":null,"createTime":"2021-02-05 10:36:50","createUser":null,"job":"","age":0,"ygxz":"1","ygxzName":"企内","sexName":"男","personTypeName":"医生","statusName":"有效","skillLevelName":"初级工","jobStatusName":"在岗","politicalStatusName":"群众","nationName":"汉族","pProvinceName":null,"pCityName":null,"pDistrictName":null,"cProvinceName":null,"cCityName":null,"cDistrictName":null,"ccity":"","cdistrict":"","cprovince":"","pcity":"","pprovince":"","pdistrict":""}
         * type : 1
         * startDate : null
         * endDate : null
         * tagType : 1
         * deviceId : 42489
         * fenceDTOList : []
         * areaLimitDTOS : []
         * trainDTOList : []
         * personDetailDTO : {"id":158,"personId":400,"alarmTemperature":"36.1,37.2","alarmHeartRate":"60,100","alarmOxygen":"90,100","alarmBreathingRate":"12,24","alarmBloodPressureHigh":"90,139","alarmBloodPressureLow":"60,89","operateId":"00","operateTime":"2021-02-17 10:55:57"}
         * personDiseasesDTOS : []
         * tagTypeName : 标签
         */

        private PersonDTOBean personDTO;
        private String type;
        private Object startDate;
        private Object endDate;
        private String tagType;
        private String deviceId;
        private PersonDetailDTOBean personDetailDTO;
        private String tagTypeName;
        private List<?> fenceDTOList;
        private List<?> areaLimitDTOS;
        private List<?> trainDTOList;
        private List<?> personDiseasesDTOS;
        private SignNowDTO signNowDTO;

        public SignNowDTO getSignNowDTO() {
            return signNowDTO;
        }

        public void setSignNowDTO(SignNowDTO signNowDTO) {
            this.signNowDTO = signNowDTO;
        }

        public PersonDTOBean getPersonDTO() {
            return personDTO;
        }

        public void setPersonDTO(PersonDTOBean personDTO) {
            this.personDTO = personDTO;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getStartDate() {
            return startDate;
        }

        public void setStartDate(Object startDate) {
            this.startDate = startDate;
        }

        public Object getEndDate() {
            return endDate;
        }

        public void setEndDate(Object endDate) {
            this.endDate = endDate;
        }

        public String getTagType() {
            return tagType;
        }

        public void setTagType(String tagType) {
            this.tagType = tagType;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public PersonDetailDTOBean getPersonDetailDTO() {
            return personDetailDTO;
        }

        public void setPersonDetailDTO(PersonDetailDTOBean personDetailDTO) {
            this.personDetailDTO = personDetailDTO;
        }

        public String getTagTypeName() {
            return tagTypeName;
        }

        public void setTagTypeName(String tagTypeName) {
            this.tagTypeName = tagTypeName;
        }

        public List<?> getFenceDTOList() {
            return fenceDTOList;
        }

        public void setFenceDTOList(List<?> fenceDTOList) {
            this.fenceDTOList = fenceDTOList;
        }

        public List<?> getAreaLimitDTOS() {
            return areaLimitDTOS;
        }

        public void setAreaLimitDTOS(List<?> areaLimitDTOS) {
            this.areaLimitDTOS = areaLimitDTOS;
        }

        public List<?> getTrainDTOList() {
            return trainDTOList;
        }

        public void setTrainDTOList(List<?> trainDTOList) {
            this.trainDTOList = trainDTOList;
        }

        public List<?> getPersonDiseasesDTOS() {
            return personDiseasesDTOS;
        }

        public void setPersonDiseasesDTOS(List<?> personDiseasesDTOS) {
            this.personDiseasesDTOS = personDiseasesDTOS;
        }

        public static class PersonDTOBean implements Serializable{
            /**
             * personId : 400
             * jobNumber :
             * name : aday
             * sex : 1
             * company :
             * duty :
             * personType : 13
             * phone : 15927387000
             * idcard : 420982199308220038
             * status : 1
             * ownerCompany :
             * skillLevel : 1
             * jobStatus : 1
             * permanentAddress :
             * currentAddress :
             * politicalStatus : 2
             * nation : 1
             * emergencyMan :
             * emergencyPhone :
             * img :
             * updateTime : 2021-02-17 10:55:57
             * updateUser : null
             * createTime : 2021-02-05 10:36:50
             * createUser : null
             * job :
             * age : 0
             * ygxz : 1
             * ygxzName : 企内
             * sexName : 男
             * personTypeName : 医生
             * statusName : 有效
             * skillLevelName : 初级工
             * jobStatusName : 在岗
             * politicalStatusName : 群众
             * nationName : 汉族
             * pProvinceName : null
             * pCityName : null
             * pDistrictName : null
             * cProvinceName : null
             * cCityName : null
             * cDistrictName : null
             * ccity :
             * cdistrict :
             * cprovince :
             * pcity :
             * pprovince :
             * pdistrict :
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
            private Object updateUser;
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
            private String cProvinceName;
            private String cCityName;
            private String cDistrictName;
            private String cAddressName;
            private String pAddressName;
            private String ccity;
            private String cdistrict;
            private String cprovince;
            private String pcity;
            private String pprovince;
            private String pdistrict;

            public String getpProvinceName() {
                return pProvinceName;
            }

            public void setpProvinceName(String pProvinceName) {
                this.pProvinceName = pProvinceName;
            }

            public String getpCityName() {
                return pCityName;
            }

            public void setpCityName(String pCityName) {
                this.pCityName = pCityName;
            }

            public String getpDistrictName() {
                return pDistrictName;
            }

            public void setpDistrictName(String pDistrictName) {
                this.pDistrictName = pDistrictName;
            }

            public String getcProvinceName() {
                return cProvinceName;
            }

            public void setcProvinceName(String cProvinceName) {
                this.cProvinceName = cProvinceName;
            }

            public String getcCityName() {
                return cCityName;
            }

            public void setcCityName(String cCityName) {
                this.cCityName = cCityName;
            }

            public String getcDistrictName() {
                return cDistrictName;
            }

            public void setcDistrictName(String cDistrictName) {
                this.cDistrictName = cDistrictName;
            }

            public String getcAddressName() {
                return cAddressName;
            }

            public void setcAddressName(String cAddressName) {
                this.cAddressName = cAddressName;
            }

            public String getpAddressName() {
                return pAddressName;
            }

            public void setpAddressName(String pAddressName) {
                this.pAddressName = pAddressName;
            }

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

            public Object getUpdateUser() {
                return updateUser;
            }

            public void setUpdateUser(Object updateUser) {
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

            public String getCprovince() {
                return cprovince;
            }

            public void setCprovince(String cprovince) {
                this.cprovince = cprovince;
            }

            public String getPcity() {
                return pcity;
            }

            public void setPcity(String pcity) {
                this.pcity = pcity;
            }

            public String getPprovince() {
                return pprovince;
            }

            public void setPprovince(String pprovince) {
                this.pprovince = pprovince;
            }

            public String getPdistrict() {
                return pdistrict;
            }

            public void setPdistrict(String pdistrict) {
                this.pdistrict = pdistrict;
            }
        }

        public static class PersonDetailDTOBean {
            /**
             * id : 158
             * personId : 400
             * alarmTemperature : 36.1,37.2
             * alarmHeartRate : 60,100
             * alarmOxygen : 90,100
             * alarmBreathingRate : 12,24
             * alarmBloodPressureHigh : 90,139
             * alarmBloodPressureLow : 60,89
             * operateId : 00
             * operateTime : 2021-02-17 10:55:57
             */

            private int id;
            private int personId;
            private String alarmTemperature;
            private String alarmHeartRate;
            private String alarmOxygen;
            private String alarmBreathingRate;
            private String alarmBloodPressureHigh;
            private String alarmBloodPressureLow;
            private String operateId;
            private String operateTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPersonId() {
                return personId;
            }

            public void setPersonId(int personId) {
                this.personId = personId;
            }

            public String getAlarmTemperature() {
                return alarmTemperature;
            }

            public void setAlarmTemperature(String alarmTemperature) {
                this.alarmTemperature = alarmTemperature;
            }

            public String getAlarmHeartRate() {
                return alarmHeartRate;
            }

            public void setAlarmHeartRate(String alarmHeartRate) {
                this.alarmHeartRate = alarmHeartRate;
            }

            public String getAlarmOxygen() {
                return alarmOxygen;
            }

            public void setAlarmOxygen(String alarmOxygen) {
                this.alarmOxygen = alarmOxygen;
            }

            public String getAlarmBreathingRate() {
                return alarmBreathingRate;
            }

            public void setAlarmBreathingRate(String alarmBreathingRate) {
                this.alarmBreathingRate = alarmBreathingRate;
            }

            public String getAlarmBloodPressureHigh() {
                return alarmBloodPressureHigh;
            }

            public void setAlarmBloodPressureHigh(String alarmBloodPressureHigh) {
                this.alarmBloodPressureHigh = alarmBloodPressureHigh;
            }

            public String getAlarmBloodPressureLow() {
                return alarmBloodPressureLow;
            }

            public void setAlarmBloodPressureLow(String alarmBloodPressureLow) {
                this.alarmBloodPressureLow = alarmBloodPressureLow;
            }

            public String getOperateId() {
                return operateId;
            }

            public void setOperateId(String operateId) {
                this.operateId = operateId;
            }

            public String getOperateTime() {
                return operateTime;
            }

            public void setOperateTime(String operateTime) {
                this.operateTime = operateTime;
            }
        }

        public static class SignNowDTO{

            /**
             * id : 524
             * watchId : 359202439514743
             * watchType : null
             * personId : 399
             * status : null
             * watchMac : null
             * temperature : 100
             * heartRate :
             * oxygen : 0
             * breathingRate : null
             * bloodPressureHigh : 90
             * bloodPressureLow : 8
             * createTime : 2021-01-29
             * temperatureState : 偏高
             * heartRateState : null
             * oxygenState : null
             * breathingRateState : null
             * bloodPressureState : 血压异常
             * healthInfo : false
             */

            private String id;
            private String watchId;
            private String watchType;
            private String personId;
            private String status;
            private String watchMac;
            private String temperature;
            private String heartRate;
            private String oxygen;
            private String breathingRate;
            private String bloodPressureHigh;
            private String bloodPressureLow;
            private String createTime;
            private String temperatureState;
            private String heartRateState;
            private String oxygenState;
            private String breathingRateState;
            private String bloodPressureState;
            private boolean healthInfo;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getWatchId() {
                return watchId;
            }

            public void setWatchId(String watchId) {
                this.watchId = watchId;
            }

            public Object getWatchType() {
                return watchType;
            }

            public void setWatchType(String watchType) {
                this.watchType = watchType;
            }

            public String getPersonId() {
                return personId;
            }

            public void setPersonId(String personId) {
                this.personId = personId;
            }

            public Object getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getWatchMac() {
                return watchMac;
            }

            public void setWatchMac(String watchMac) {
                this.watchMac = watchMac;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getHeartRate() {
                return heartRate;
            }

            public void setHeartRate(String heartRate) {
                this.heartRate = heartRate;
            }

            public String getOxygen() {
                return oxygen;
            }

            public void setOxygen(String oxygen) {
                this.oxygen = oxygen;
            }

            public String getBreathingRate() {
                return breathingRate;
            }

            public void setBreathingRate(String breathingRate) {
                this.breathingRate = breathingRate;
            }

            public String getBloodPressureHigh() {
                return bloodPressureHigh;
            }

            public void setBloodPressureHigh(String bloodPressureHigh) {
                this.bloodPressureHigh = bloodPressureHigh;
            }

            public String getBloodPressureLow() {
                return bloodPressureLow;
            }

            public void setBloodPressureLow(String bloodPressureLow) {
                this.bloodPressureLow = bloodPressureLow;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getTemperatureState() {
                return temperatureState;
            }

            public void setTemperatureState(String temperatureState) {
                this.temperatureState = temperatureState;
            }

            public String getHeartRateState() {
                return heartRateState;
            }

            public void setHeartRateState(String heartRateState) {
                this.heartRateState = heartRateState;
            }

            public String getOxygenState() {
                return oxygenState;
            }

            public void setOxygenState(String oxygenState) {
                this.oxygenState = oxygenState;
            }

            public String getBreathingRateState() {
                return breathingRateState;
            }

            public void setBreathingRateState(String breathingRateState) {
                this.breathingRateState = breathingRateState;
            }

            public String getBloodPressureState() {
                return bloodPressureState;
            }

            public void setBloodPressureState(String bloodPressureState) {
                this.bloodPressureState = bloodPressureState;
            }

            public boolean isHealthInfo() {
                return healthInfo;
            }

            public void setHealthInfo(boolean healthInfo) {
                this.healthInfo = healthInfo;
            }
        }
    }
}
