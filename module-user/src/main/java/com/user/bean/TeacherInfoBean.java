package com.user.bean;

public class TeacherInfoBean {


    /**
     * code : 200
     * obj : {"user_id":29,"phone":"13540354597","photo_url":"http://ps70o9d2f.bkt.clouddn.com/FscPYo_fSZ6zHz1ExL6ONLe9l1jy","name":"教师13540354597","sex":null,"idcard_p":null,"idcard_n":null,"certificate":null,"self_video":null,"school_name":null,"work_year":null,"area":null,"work_exp":null,"is_check":0,"subject_id":2,"subject_name":"数学","opt_subject_id":null,"opt_subject_name":null,"status":0}
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
         * user_id : 29
         * phone : 13540354597
         * photo_url : http://ps70o9d2f.bkt.clouddn.com/FscPYo_fSZ6zHz1ExL6ONLe9l1jy
         * name : 教师13540354597
         * sex : null
         * idcard_p : null
         * idcard_n : null
         * certificate : null
         * self_video : null
         * school_name : null
         * work_year : null
         * area : null
         * work_exp : null
         * is_check : 0
         * subject_id : 2
         * subject_name : 数学
         * opt_subject_id : null
         * opt_subject_name : null
         * status : 0
         */

        private int user_id;
        private String phone="";
        private String photo_url="";
        private String name="";
        private String sex="";
        private String idcard_p="";
        private String idcard_n="";
        private String certificate="";
        private String self_video="";
        private String school_name="";
        private String work_year="";
        private String area="";
        private String work_exp="";
        private int is_check;
        private int subject_id;
        private String subject_name="";
        private String opt_subject_id="";
        private String opt_subject_name="";
        private int status;

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

        public String getIdcard_p() {
            return idcard_p;
        }

        public void setIdcard_p(String idcard_p) {
            this.idcard_p = idcard_p;
        }

        public String getIdcard_n() {
            return idcard_n;
        }

        public void setIdcard_n(String idcard_n) {
            this.idcard_n = idcard_n;
        }

        public String getCertificate() {
            return certificate;
        }

        public void setCertificate(String certificate) {
            this.certificate = certificate;
        }

        public String getSelf_video() {
            return self_video;
        }

        public void setSelf_video(String self_video) {
            this.self_video = self_video;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getWork_year() {
            return work_year;
        }

        public void setWork_year(String work_year) {
            this.work_year = work_year;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getWork_exp() {
            return work_exp;
        }

        public void setWork_exp(String work_exp) {
            this.work_exp = work_exp;
        }

        public int getIs_check() {
            return is_check;
        }

        public void setIs_check(int is_check) {
            this.is_check = is_check;
        }

        public int getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(int subject_id) {
            this.subject_id = subject_id;
        }

        public String getSubject_name() {
            return subject_name;
        }

        public void setSubject_name(String subject_name) {
            this.subject_name = subject_name;
        }

        public String getOpt_subject_id() {
            return opt_subject_id;
        }

        public void setOpt_subject_id(String opt_subject_id) {
            this.opt_subject_id = opt_subject_id;
        }

        public String getOpt_subject_name() {
            return opt_subject_name;
        }

        public void setOpt_subject_name(String opt_subject_name) {
            this.opt_subject_name = opt_subject_name;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
