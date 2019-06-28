package com.user.bean;

import java.util.List;

public class ClassBean {


    /**
     * code : 200
     * obj : {"total":1,"limit":10,"size":0,"rows":[{"id":33,"name":"三年级数学","type":2,"consume_class":0,"live_room":100,"live_status":0,"pay_status":0}]}
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
         * limit : 10
         * size : 0
         * rows : [{"id":33,"name":"三年级数学","type":2,"consume_class":0,"live_room":100,"live_status":0,"pay_status":0}]
         */

        private int total;
        private int limit;
        private int size;
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

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            /**
             * id : 33
             * name : 三年级数学
             * type : 2
             * consume_class : 0
             * live_room : 100
             * live_status : 0
             * pay_status : 0
             */

            private int id;
            private String name;
            private int type;
            private int consume_class;
            private int live_room;
            private int live_status;
            private int pay_status;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getConsume_class() {
                return consume_class;
            }

            public void setConsume_class(int consume_class) {
                this.consume_class = consume_class;
            }

            public int getLive_room() {
                return live_room;
            }

            public void setLive_room(int live_room) {
                this.live_room = live_room;
            }

            public int getLive_status() {
                return live_status;
            }

            public void setLive_status(int live_status) {
                this.live_status = live_status;
            }

            public int getPay_status() {
                return pay_status;
            }

            public void setPay_status(int pay_status) {
                this.pay_status = pay_status;
            }
        }
    }
}
