package com.user.bean;

public class PersonInfoBean {


    /**
     * code : 200
     * obj : {"area":"","subject_id":1,"self_video_screenshot":"http://pvjdparam.bkt.clouddn.com/Fpm4jnNUwVO8JrzU4l4g-RYzS7UT?vframe/png/offset/0","sex":1,"certificate":"http://pvjdparam.bkt.clouddn.com/FijYZPEZKwG7BsbaC77jMAsIS4zs","opt_subject_name":"","school_name":"成都大学","work_year":10,"self_video":"http://pvjdparam.bkt.clouddn.com/Fpm4jnNUwVO8JrzU4l4g-RYzS7UT","token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWJqZWN0X2lkIjoiMSIsInVzZXJfaWQiOiIzMCIsInBob25lIjoiMTM5ODAwMDQ1ODAiLCJncmFkZV9pZCI6IjQiLCJpZGVudGl0eSI6IjEiLCJpc3MiOiJzeXNfdXNlciIsIm5hbWUiOiJTaXRlclp6eiIsInBob3RvX3VybCI6Imh0dHA6Ly9wdmpkcGFyYW0uYmt0LmNsb3VkZG4uY29tL0ZwZzN4ZW1nV19RZnJZN3pTOVV6REhOOGh1XzUiLCJleHAiOjE1NjUxNzI5OTN9.O5WJ1uo1f_QhVFrndPDm2zABODPD9h5VXj91430UlgQ","opt_subject_id":"","user_id":30,"phone":"13980004580","identity":1,"work_exp":"开始学法律突突突","name":"SiterZzz","subject_name":"语文","idcard_p":"http://pvjdparam.bkt.clouddn.com/Fm8qYEDOA57C057v1ERuapt27qnE","photo_url":"http://pvjdparam.bkt.clouddn.com/Fpg3xemgW_QfrY7zS9UzDHN8hu_5","is_check":1,"idcard_n":"http://pvjdparam.bkt.clouddn.com/Fh2JNzKNfgEgtGxx0VRAV8jRPQtN","status":0}
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
         * area :
         * subject_id : 1
         * self_video_screenshot : http://pvjdparam.bkt.clouddn.com/Fpm4jnNUwVO8JrzU4l4g-RYzS7UT?vframe/png/offset/0
         * sex : 1
         * certificate : http://pvjdparam.bkt.clouddn.com/FijYZPEZKwG7BsbaC77jMAsIS4zs
         * opt_subject_name :
         * school_name : 成都大学
         * work_year : 10
         * self_video : http://pvjdparam.bkt.clouddn.com/Fpm4jnNUwVO8JrzU4l4g-RYzS7UT
         * token : eyJhbGciOiJIUzI1NiJ9.eyJzdWJqZWN0X2lkIjoiMSIsInVzZXJfaWQiOiIzMCIsInBob25lIjoiMTM5ODAwMDQ1ODAiLCJncmFkZV9pZCI6IjQiLCJpZGVudGl0eSI6IjEiLCJpc3MiOiJzeXNfdXNlciIsIm5hbWUiOiJTaXRlclp6eiIsInBob3RvX3VybCI6Imh0dHA6Ly9wdmpkcGFyYW0uYmt0LmNsb3VkZG4uY29tL0ZwZzN4ZW1nV19RZnJZN3pTOVV6REhOOGh1XzUiLCJleHAiOjE1NjUxNzI5OTN9.O5WJ1uo1f_QhVFrndPDm2zABODPD9h5VXj91430UlgQ
         * opt_subject_id :
         * user_id : 30
         * phone : 13980004580
         * identity : 1
         * work_exp : 开始学法律突突突
         * name : SiterZzz
         * subject_name : 语文
         * idcard_p : http://pvjdparam.bkt.clouddn.com/Fm8qYEDOA57C057v1ERuapt27qnE
         * photo_url : http://pvjdparam.bkt.clouddn.com/Fpg3xemgW_QfrY7zS9UzDHN8hu_5
         * is_check : 1
         * idcard_n : http://pvjdparam.bkt.clouddn.com/Fh2JNzKNfgEgtGxx0VRAV8jRPQtN
         * status : 0
         */

        private String area;
        private int subject_id;
        private String self_video_screenshot;
        private int sex;
        private String certificate;
        private String opt_subject_name;
        private String school_name;
        private int work_year;
        private String self_video;
        private String token;
        private String opt_subject_id;
        private int user_id;
        private String phone;
        private int identity;
        private String work_exp;
        private String name;
        private String subject_name;
        private String idcard_p;
        private String photo_url;
        private int is_check;
        private String idcard_n;
        private int status;

        private float total;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public int getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(int subject_id) {
            this.subject_id = subject_id;
        }

        public String getSelf_video_screenshot() {
            return self_video_screenshot;
        }

        public void setSelf_video_screenshot(String self_video_screenshot) {
            this.self_video_screenshot = self_video_screenshot;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getCertificate() {
            return certificate;
        }

        public void setCertificate(String certificate) {
            this.certificate = certificate;
        }

        public String getOpt_subject_name() {
            return opt_subject_name;
        }

        public void setOpt_subject_name(String opt_subject_name) {
            this.opt_subject_name = opt_subject_name;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public int getWork_year() {
            return work_year;
        }

        public void setWork_year(int work_year) {
            this.work_year = work_year;
        }

        public String getSelf_video() {
            return self_video;
        }

        public void setSelf_video(String self_video) {
            this.self_video = self_video;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getOpt_subject_id() {
            return opt_subject_id;
        }

        public void setOpt_subject_id(String opt_subject_id) {
            this.opt_subject_id = opt_subject_id;
        }

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

        public int getIdentity() {
            return identity;
        }

        public void setIdentity(int identity) {
            this.identity = identity;
        }

        public String getWork_exp() {
            return work_exp;
        }

        public void setWork_exp(String work_exp) {
            this.work_exp = work_exp;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSubject_name() {
            return subject_name;
        }

        public void setSubject_name(String subject_name) {
            this.subject_name = subject_name;
        }

        public String getIdcard_p() {
            return idcard_p;
        }

        public void setIdcard_p(String idcard_p) {
            this.idcard_p = idcard_p;
        }

        public String getPhoto_url() {
            return photo_url;
        }

        public void setPhoto_url(String photo_url) {
            this.photo_url = photo_url;
        }

        public int getIs_check() {
            return is_check;
        }

        public void setIs_check(int is_check) {
            this.is_check = is_check;
        }

        public String getIdcard_n() {
            return idcard_n;
        }

        public void setIdcard_n(String idcard_n) {
            this.idcard_n = idcard_n;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public float getTotal() {
            return total;
        }

        public void setTotal(float total) {
            this.total = total;
        }
    }
}
