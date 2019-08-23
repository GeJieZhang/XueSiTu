package com.lib.bean;

public class ConfigBean {


    /**
     * code : 200
     * obj : {"learning_process_url":"http://192.168.0.10:88/personal/learning_process.html","teacher_main_url":"http://192.168.0.115:8080/xuesitu/viewAction?requestType=mainTeacher","evaluation_record_url":"http://192.168.0.10:88/personal/evaluation_record.html","sign_in_url":"http://192.168.0.10:88/personal/sign_in.html","stu_assisiant_url":"http://192.168.0.10:88/assistant/stu_assisiant.html","teaching_assistant_url":"http://192.168.0.10:88/assistant/teaching_assistant.html","token":"3MREyUAjTV-fOSdRtNpsO3DbNMQVnSdbEyhoNp9q:IEu9L8U6lw6_Pq2Otq3D9uL-LW0=:eyJzY29wZSI6Inh1ZXNpdHVfdjMiLCJkZWFkbGluZSI6MTU2NjU0OTk5NH0=","student_list_url":"","baseurl":"http://pvjdparam.bkt.clouddn.com/","stu_videocourse_url":"http://192.168.0.10:88/videocourse/stu_videocourse.html","cash_withdrawal_url":"http://192.168.0.10:88/personal/cash_withdrawal.html","teacher_list_url":"http://192.168.0.10:88/personal/teacher_list.html","teaching_videocourse_url":"http://192.168.0.10:88/videocourse/teaching_videocourse.html"}
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
         * learning_process_url : http://192.168.0.10:88/personal/learning_process.html
         * teacher_main_url : http://192.168.0.115:8080/xuesitu/viewAction?requestType=mainTeacher
         * evaluation_record_url : http://192.168.0.10:88/personal/evaluation_record.html
         * sign_in_url : http://192.168.0.10:88/personal/sign_in.html
         * stu_assisiant_url : http://192.168.0.10:88/assistant/stu_assisiant.html
         * teaching_assistant_url : http://192.168.0.10:88/assistant/teaching_assistant.html
         * token : 3MREyUAjTV-fOSdRtNpsO3DbNMQVnSdbEyhoNp9q:IEu9L8U6lw6_Pq2Otq3D9uL-LW0=:eyJzY29wZSI6Inh1ZXNpdHVfdjMiLCJkZWFkbGluZSI6MTU2NjU0OTk5NH0=
         * student_list_url :
         * baseurl : http://pvjdparam.bkt.clouddn.com/
         * stu_videocourse_url : http://192.168.0.10:88/videocourse/stu_videocourse.html
         * cash_withdrawal_url : http://192.168.0.10:88/personal/cash_withdrawal.html
         * teacher_list_url : http://192.168.0.10:88/personal/teacher_list.html
         * teaching_videocourse_url : http://192.168.0.10:88/videocourse/teaching_videocourse.html
         */

        private String learning_process_url;
        private String teacher_main_url;
        private String evaluation_record_url;
        private String sign_in_url;
        private String stu_assisiant_url;
        private String teaching_assistant_url;
        private String token;
        private String student_list_url;
        private String baseurl;
        private String stu_videocourse_url;
        private String cash_withdrawal_url;
        private String teacher_list_url;
        private String teaching_videocourse_url;

        public String getLearning_process_url() {
            return learning_process_url;
        }

        public void setLearning_process_url(String learning_process_url) {
            this.learning_process_url = learning_process_url;
        }

        public String getTeacher_main_url() {
            return teacher_main_url;
        }

        public void setTeacher_main_url(String teacher_main_url) {
            this.teacher_main_url = teacher_main_url;
        }

        public String getEvaluation_record_url() {
            return evaluation_record_url;
        }

        public void setEvaluation_record_url(String evaluation_record_url) {
            this.evaluation_record_url = evaluation_record_url;
        }

        public String getSign_in_url() {
            return sign_in_url;
        }

        public void setSign_in_url(String sign_in_url) {
            this.sign_in_url = sign_in_url;
        }

        public String getStu_assisiant_url() {
            return stu_assisiant_url;
        }

        public void setStu_assisiant_url(String stu_assisiant_url) {
            this.stu_assisiant_url = stu_assisiant_url;
        }

        public String getTeaching_assistant_url() {
            return teaching_assistant_url;
        }

        public void setTeaching_assistant_url(String teaching_assistant_url) {
            this.teaching_assistant_url = teaching_assistant_url;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getStudent_list_url() {
            return student_list_url;
        }

        public void setStudent_list_url(String student_list_url) {
            this.student_list_url = student_list_url;
        }

        public String getBaseurl() {
            return baseurl;
        }

        public void setBaseurl(String baseurl) {
            this.baseurl = baseurl;
        }

        public String getStu_videocourse_url() {
            return stu_videocourse_url;
        }

        public void setStu_videocourse_url(String stu_videocourse_url) {
            this.stu_videocourse_url = stu_videocourse_url;
        }

        public String getCash_withdrawal_url() {
            return cash_withdrawal_url;
        }

        public void setCash_withdrawal_url(String cash_withdrawal_url) {
            this.cash_withdrawal_url = cash_withdrawal_url;
        }

        public String getTeacher_list_url() {
            return teacher_list_url;
        }

        public void setTeacher_list_url(String teacher_list_url) {
            this.teacher_list_url = teacher_list_url;
        }

        public String getTeaching_videocourse_url() {
            return teaching_videocourse_url;
        }

        public void setTeaching_videocourse_url(String teaching_videocourse_url) {
            this.teaching_videocourse_url = teaching_videocourse_url;
        }
    }
}
