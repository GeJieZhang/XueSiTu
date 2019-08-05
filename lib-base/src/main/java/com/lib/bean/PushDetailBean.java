package com.lib.bean;

public class PushDetailBean {


    /**
     * obj : {"subject_name":"语文","expected_value":5,"question_id":101,"grade_name":"四年级"}
     * type : 1
     */

    private ObjBean obj;
    private int type;

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class ObjBean {
        /**
         * subject_name : 语文
         * expected_value : 5
         * question_id : 101
         * grade_name : 四年级
         */

        private String subject_name;
        private int expected_value;
        private int question_id;
        private String grade_name;

        public String getSubject_name() {
            return subject_name;
        }

        public void setSubject_name(String subject_name) {
            this.subject_name = subject_name;
        }

        public int getExpected_value() {
            return expected_value;
        }

        public void setExpected_value(int expected_value) {
            this.expected_value = expected_value;
        }

        public int getQuestion_id() {
            return question_id;
        }

        public void setQuestion_id(int question_id) {
            this.question_id = question_id;
        }

        public String getGrade_name() {
            return grade_name;
        }

        public void setGrade_name(String grade_name) {
            this.grade_name = grade_name;
        }
    }
}
