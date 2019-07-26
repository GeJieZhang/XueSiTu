package com.dayi.bean;

import java.util.List;

public class PrivateAskBean {


    /**
     * code : 200
     * obj : {"total":1,"limit":1,"page":0,"rows":[{"file":["http://pu00k0ssj.bkt.clouddn.com/null"],"question_id":7,"current_value":10,"type":0}]}
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
         * total : 1
         * limit : 1
         * page : 0
         * rows : [{"file":["http://pu00k0ssj.bkt.clouddn.com/null"],"question_id":7,"current_value":10,"type":0}]
         */

        private int total;
        private int limit;
        private int page;
        private List<RowsBean> rows;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            /**
             * file : ["http://pu00k0ssj.bkt.clouddn.com/null"]
             * question_id : 7
             * current_value : 10
             * type : 0
             */

            private int question_id;
            private int current_value;
            private int type;
            private List<String> file;

            public int getQuestion_id() {
                return question_id;
            }

            public void setQuestion_id(int question_id) {
                this.question_id = question_id;
            }

            public int getCurrent_value() {
                return current_value;
            }

            public void setCurrent_value(int current_value) {
                this.current_value = current_value;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
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
