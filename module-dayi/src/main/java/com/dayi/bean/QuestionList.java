package com.dayi.bean;

import java.util.List;

public class QuestionList {


    /**
     * code : 200
     * obj : {"history_list":[{"divide":2,"file":["http://pu00k0ssj.bkt.clouddn.com/FtyynoxXZwekhKCC7IVnVIHvk5i-"],"question_id":3},{"divide":5,"file":["http://pu00k0ssj.bkt.clouddn.com/Fm6Frf6_xGlRfAU0qyjkWpXp6qsF"],"question_id":5}],"question_List":[{"remaining":8,"file":["http://pu00k0ssj.bkt.clouddn.com/FtyynoxXZwekhKCC7IVnVIHvk5i-"],"question_id":3},{"remaining":10,"file":["http://pu00k0ssj.bkt.clouddn.com/FttbETY71vECHeB6Ysrc8n0oOLTR"],"question_id":4},{"remaining":0,"file":["http://pu00k0ssj.bkt.clouddn.com/Fm6Frf6_xGlRfAU0qyjkWpXp6qsF"],"question_id":5},{"remaining":10,"file":["http://pu00k0ssj.bkt.clouddn.com/Frr3N-lgxgc7SmG06a4wvjWx2zgy"],"question_id":6}]}
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
             * divide : 2
             * file : ["http://pu00k0ssj.bkt.clouddn.com/FtyynoxXZwekhKCC7IVnVIHvk5i-"]
             * question_id : 3
             */

            private int divide;
            private int question_id;
            private List<String> file;

            public int getDivide() {
                return divide;
            }

            public void setDivide(int divide) {
                this.divide = divide;
            }

            public int getQuestion_id() {
                return question_id;
            }

            public void setQuestion_id(int question_id) {
                this.question_id = question_id;
            }

            public List<String> getFile() {
                return file;
            }

            public void setFile(List<String> file) {
                this.file = file;
            }
        }

        public static class QuestionListBean {
            /**
             * remaining : 8
             * file : ["http://pu00k0ssj.bkt.clouddn.com/FtyynoxXZwekhKCC7IVnVIHvk5i-"]
             * question_id : 3
             */

            private int remaining;
            private int question_id;
            private List<String> file;

            public int getRemaining() {
                return remaining;
            }

            public void setRemaining(int remaining) {
                this.remaining = remaining;
            }

            public int getQuestion_id() {
                return question_id;
            }

            public void setQuestion_id(int question_id) {
                this.question_id = question_id;
            }

            public List<String> getFile() {
                return file;
            }

            public void setFile(List<String> file) {
                this.file = file;
            }
        }
    }
}
