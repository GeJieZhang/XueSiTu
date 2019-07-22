package com.dayi.bean;

import java.util.List;

public class QuestionList {


    /**
     * code : 200
     * obj : {"history_list":[{"divide":3,"file":"FtyynoxXZwekhKCC7IVnVIHvk5i-","question_id":3,"image":["http://pu00k0ssj.bkt.clouddn.com/FtyynoxXZwekhKCC7IVnVIHvk5i-"]}],"question_List":[{"remaining":5,"file":"FtyynoxXZwekhKCC7IVnVIHvk5i-","question_id":3,"image":["http://pu00k0ssj.bkt.clouddn.com/FtyynoxXZwekhKCC7IVnVIHvk5i-"]}]}
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
        private List<HistoryListBean> history_list;
        private List<QuestionListBean> question_List;

        public List<HistoryListBean> getHistory_list() {
            return history_list;
        }

        public void setHistory_list(List<HistoryListBean> history_list) {
            this.history_list = history_list;
        }

        public List<QuestionListBean> getQuestion_List() {
            return question_List;
        }

        public void setQuestion_List(List<QuestionListBean> question_List) {
            this.question_List = question_List;
        }

        public static class HistoryListBean {
            /**
             * divide : 3
             * file : FtyynoxXZwekhKCC7IVnVIHvk5i-
             * question_id : 3
             * image : ["http://pu00k0ssj.bkt.clouddn.com/FtyynoxXZwekhKCC7IVnVIHvk5i-"]
             */

            private int divide;
            private String file;
            private int question_id;
            private List<String> image;

            public int getDivide() {
                return divide;
            }

            public void setDivide(int divide) {
                this.divide = divide;
            }

            public String getFile() {
                return file;
            }

            public void setFile(String file) {
                this.file = file;
            }

            public int getQuestion_id() {
                return question_id;
            }

            public void setQuestion_id(int question_id) {
                this.question_id = question_id;
            }

            public List<String> getImage() {
                return image;
            }

            public void setImage(List<String> image) {
                this.image = image;
            }
        }

        public static class QuestionListBean {
            /**
             * remaining : 5
             * file : FtyynoxXZwekhKCC7IVnVIHvk5i-
             * question_id : 3
             * image : ["http://pu00k0ssj.bkt.clouddn.com/FtyynoxXZwekhKCC7IVnVIHvk5i-"]
             */

            private int remaining;
            private String file;
            private int question_id;
            private List<String> image;

            public int getRemaining() {
                return remaining;
            }

            public void setRemaining(int remaining) {
                this.remaining = remaining;
            }

            public String getFile() {
                return file;
            }

            public void setFile(String file) {
                this.file = file;
            }

            public int getQuestion_id() {
                return question_id;
            }

            public void setQuestion_id(int question_id) {
                this.question_id = question_id;
            }

            public List<String> getImage() {
                return image;
            }

            public void setImage(List<String> image) {
                this.image = image;
            }
        }
    }
}
