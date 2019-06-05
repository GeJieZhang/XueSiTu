package com.user.bean;

public class StudentInfoBean {


    /**
     * code : 200
     * obj : {"user_id":31,"phone":"13540354598","photo_url":"http://ps70o9d2f.bkt.clouddn.com/FgOLkuCYx3tqsJQAIwmXK3PHwTmG","name":"学员13540354598","sex":"","birthday_date":"","school_name":"","area":"","ass_person":"","ass_phone":"","opt_grade_name":"","grade_name":"初三","opt_grade_id":"","grade_id":11,"status":0,"invite_code":791941}
     * msg : 请求成功
     * seccess : true
     */

    private int code;
    private ObjBean obj;
    private String msg;
    private boolean seccess;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSeccess() {
        return seccess;
    }

    public void setSeccess(boolean seccess) {
        this.seccess = seccess;
    }

    public static class ObjBean {
        /**
         * user_id : 31
         * phone : 13540354598
         * photo_url : http://ps70o9d2f.bkt.clouddn.com/FgOLkuCYx3tqsJQAIwmXK3PHwTmG
         * name : 学员13540354598
         * sex :
         * birthday_date :
         * school_name :
         * area :
         * ass_person :
         * ass_phone :
         * opt_grade_name :
         * grade_name : 初三
         * opt_grade_id :
         * grade_id : 11
         * status : 0
         * invite_code : 791941
         */

        private int user_id;
        private String phone;
        private String photo_url;
        private String name;
        private String sex;
        private String birthday_date;
        private String school_name;
        private String area;
        private String ass_person;
        private String ass_phone;
        private String opt_grade_name;
        private String grade_name;
        private String opt_grade_id;
        private int grade_id;
        private int status;
        private int invite_code;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhoto_url() {
            return photo_url;
        }

        public void setPhoto_url(String photo_url) {
            this.photo_url = photo_url;
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

        public String getBirthday_date() {
            return birthday_date;
        }

        public void setBirthday_date(String birthday_date) {
            this.birthday_date = birthday_date;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAss_person() {
            return ass_person;
        }

        public void setAss_person(String ass_person) {
            this.ass_person = ass_person;
        }

        public String getAss_phone() {
            return ass_phone;
        }

        public void setAss_phone(String ass_phone) {
            this.ass_phone = ass_phone;
        }

        public String getOpt_grade_name() {
            return opt_grade_name;
        }

        public void setOpt_grade_name(String opt_grade_name) {
            this.opt_grade_name = opt_grade_name;
        }

        public String getGrade_name() {
            return grade_name;
        }

        public void setGrade_name(String grade_name) {
            this.grade_name = grade_name;
        }

        public String getOpt_grade_id() {
            return opt_grade_id;
        }

        public void setOpt_grade_id(String opt_grade_id) {
            this.opt_grade_id = opt_grade_id;
        }

        public int getGrade_id() {
            return grade_id;
        }

        public void setGrade_id(int grade_id) {
            this.grade_id = grade_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getInvite_code() {
            return invite_code;
        }

        public void setInvite_code(int invite_code) {
            this.invite_code = invite_code;
        }
    }
}
