package com.qyai.watch_app.xiaqu;

import com.lib.common.baseUtils.baseModle.BaseResult;

import java.io.Serializable;
import java.util.List;

public class XiaQuResult extends BaseResult implements Serializable {


    /**
     * msg : 操作成功
     * code : 000000
     * data : {"total":14,"list":[{"personId":395,"name":"时间测试123","sex":"1","personType":13,"personTypeName":"医生","phone":"1234567890","emergencyMan":"","emergencyPhone":"","sexName":"男"},{"personId":396,"name":"测试码看么么么么么123","sex":"1","personType":13,"personTypeName":"医生","phone":"1234567890","emergencyMan":"","emergencyPhone":"","sexName":"男"},{"personId":391,"name":"测试123","sex":"1","personType":12,"personTypeName":"医疗","phone":"12345678901","emergencyMan":"","emergencyPhone":"","sexName":"男"},{"personId":385,"name":"请问111111","sex":"1","personType":12,"personTypeName":"医疗","phone":"15527951341","emergencyMan":"","emergencyPhone":"","sexName":"男"},{"personId":378,"name":"医疗医疗132131","sex":"1","personType":12,"personTypeName":"医疗","phone":"","emergencyMan":"唐某史","emergencyPhone":"10086","sexName":"男"},{"personId":380,"name":"医疗胸卡123","sex":"1","personType":12,"personTypeName":"医疗","phone":"","emergencyMan":"","emergencyPhone":"","sexName":"男"},{"personId":376,"name":"樱桃小丸子手表测试","sex":"1","personType":14,"personTypeName":"护士","phone":"19522913080","emergencyMan":"","emergencyPhone":"","sexName":"男"},{"personId":304,"name":"护国神牛","sex":"1","personType":13,"personTypeName":"医生","phone":"1527313","emergencyMan":"驱蚊器,阿发","emergencyPhone":"123124142421,124124234244","sexName":"男"},{"personId":400,"name":"aday","sex":"1","personType":13,"personTypeName":"医生","phone":"15927387000","emergencyMan":"","emergencyPhone":"","sexName":"男"},{"personId":399,"name":"凄凄切切群群群群群群群","sex":"1","personType":13,"personTypeName":"医生","phone":"","emergencyMan":"","emergencyPhone":"","sexName":"男"}],"pageNum":1,"pageSize":10,"size":10,"startRow":0,"endRow":9,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1}
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
         * total : 14
         * list : [{"personId":395,"name":"时间测试123","sex":"1","personType":13,"personTypeName":"医生","phone":"1234567890","emergencyMan":"","emergencyPhone":"","sexName":"男"},{"personId":396,"name":"测试码看么么么么么123","sex":"1","personType":13,"personTypeName":"医生","phone":"1234567890","emergencyMan":"","emergencyPhone":"","sexName":"男"},{"personId":391,"name":"测试123","sex":"1","personType":12,"personTypeName":"医疗","phone":"12345678901","emergencyMan":"","emergencyPhone":"","sexName":"男"},{"personId":385,"name":"请问111111","sex":"1","personType":12,"personTypeName":"医疗","phone":"15527951341","emergencyMan":"","emergencyPhone":"","sexName":"男"},{"personId":378,"name":"医疗医疗132131","sex":"1","personType":12,"personTypeName":"医疗","phone":"","emergencyMan":"唐某史","emergencyPhone":"10086","sexName":"男"},{"personId":380,"name":"医疗胸卡123","sex":"1","personType":12,"personTypeName":"医疗","phone":"","emergencyMan":"","emergencyPhone":"","sexName":"男"},{"personId":376,"name":"樱桃小丸子手表测试","sex":"1","personType":14,"personTypeName":"护士","phone":"19522913080","emergencyMan":"","emergencyPhone":"","sexName":"男"},{"personId":304,"name":"护国神牛","sex":"1","personType":13,"personTypeName":"医生","phone":"1527313","emergencyMan":"驱蚊器,阿发","emergencyPhone":"123124142421,124124234244","sexName":"男"},{"personId":400,"name":"aday","sex":"1","personType":13,"personTypeName":"医生","phone":"15927387000","emergencyMan":"","emergencyPhone":"","sexName":"男"},{"personId":399,"name":"凄凄切切群群群群群群群","sex":"1","personType":13,"personTypeName":"医生","phone":"","emergencyMan":"","emergencyPhone":"","sexName":"男"}]
         * pageNum : 1
         * pageSize : 10
         * size : 10
         * startRow : 0
         * endRow : 9
         * pages : 1
         * prePage : 0
         * nextPage : 0
         * isFirstPage : true
         * isLastPage : true
         * hasPreviousPage : false
         * hasNextPage : false
         * navigatePages : 8
         * navigatepageNums : [1]
         * navigateFirstPage : 1
         * navigateLastPage : 1
         */

        private int total;
        private int pageNum;
        private int pageSize;
        private int size;
        private int startRow;
        private int endRow;
        private int pages;
        private int prePage;
        private int nextPage;
        private boolean isFirstPage;
        private boolean isLastPage;
        private boolean hasPreviousPage;
        private boolean hasNextPage;
        private int navigatePages;
        private int navigateFirstPage;
        private int navigateLastPage;
        private List<ListBean> list;
        private List<Integer> navigatepageNums;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getStartRow() {
            return startRow;
        }

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public int getEndRow() {
            return endRow;
        }

        public void setEndRow(int endRow) {
            this.endRow = endRow;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getPrePage() {
            return prePage;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public boolean isIsFirstPage() {
            return isFirstPage;
        }

        public void setIsFirstPage(boolean isFirstPage) {
            this.isFirstPage = isFirstPage;
        }

        public boolean isIsLastPage() {
            return isLastPage;
        }

        public void setIsLastPage(boolean isLastPage) {
            this.isLastPage = isLastPage;
        }

        public boolean isHasPreviousPage() {
            return hasPreviousPage;
        }

        public void setHasPreviousPage(boolean hasPreviousPage) {
            this.hasPreviousPage = hasPreviousPage;
        }

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public int getNavigatePages() {
            return navigatePages;
        }

        public void setNavigatePages(int navigatePages) {
            this.navigatePages = navigatePages;
        }

        public int getNavigateFirstPage() {
            return navigateFirstPage;
        }

        public void setNavigateFirstPage(int navigateFirstPage) {
            this.navigateFirstPage = navigateFirstPage;
        }

        public int getNavigateLastPage() {
            return navigateLastPage;
        }

        public void setNavigateLastPage(int navigateLastPage) {
            this.navigateLastPage = navigateLastPage;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<Integer> getNavigatepageNums() {
            return navigatepageNums;
        }

        public void setNavigatepageNums(List<Integer> navigatepageNums) {
            this.navigatepageNums = navigatepageNums;
        }

        public static class ListBean implements Serializable{
            /**
             * personId : 395
             * name : 时间测试123
             * sex : 1
             * personType : 13
             * personTypeName : 医生
             * phone : 1234567890
             * emergencyMan :
             * emergencyPhone :
             * sexName : 男
             *
             */

            private String img;
            private int personId;
            private String name;
            private String sex;
            private int personType;
            private String personTypeName;
            private String phone;
            private String emergencyMan;
            private String emergencyPhone;
            private String sexName;
            private String healthInfo;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getHealthInfo() {
                return healthInfo;
            }

            public void setHealthInfo(String healthInfo) {
                this.healthInfo = healthInfo;
            }

            public int getPersonId() {
                return personId;
            }

            public void setPersonId(int personId) {
                this.personId = personId;
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

            public int getPersonType() {
                return personType;
            }

            public void setPersonType(int personType) {
                this.personType = personType;
            }

            public String getPersonTypeName() {
                return personTypeName;
            }

            public void setPersonTypeName(String personTypeName) {
                this.personTypeName = personTypeName;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
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

            public String getSexName() {
                return sexName;
            }

            public void setSexName(String sexName) {
                this.sexName = sexName;
            }
        }
    }
}
